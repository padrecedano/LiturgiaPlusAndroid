package org.deiverbum.app.core.media.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.MainActivity
import org.deiverbum.app.R
import org.deiverbum.app.core.model.tts.TtsProgressData
import org.deiverbum.app.feature.tts.TtsPlaybackState
import java.util.Locale

// import javax.inject.Inject // No se necesita @Inject aquí si el servicio es @AndroidEntryPoint


@AndroidEntryPoint
class TextToSpeechService : Service(), TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "TtsService"
        private const val NOTIFICATION_CHANNEL_ID = "tts_service_channel"
        private const val NOTIFICATION_ID = 101
        private const val ACTION_STOP_SERVICE = "com.example.myapp.tts.ACTION_STOP_SERVICE"

        // Máximo de caracteres por segmento. Ajustar según necesidad.
        // TTS tiene límites en la longitud del texto que puede procesar en una llamada speak().
        private const val MAX_CHARS_PER_SEGMENT = 3500 // Límite de TTS suele ser ~4000
    }

    // --- TTS Engine and State ---
    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private var currentLanguage: Locale = Locale("spa", "ESP")
    private var currentSpeechRate: Float = 1.0f
    private var currentSpeechPitch: Float = 1.0f

    // --- Text Segmentation and Playback Tracking ---
    private var fullText: String = ""
    private var textSegments: List<String> = emptyList()
    private var currentSegmentIndexToPlay = 0 // El próximo segmento a enviar a TTS
    private var lastSpokenSegmentIndex =
        -1   // El último segmento que completó onDone (o el actual si está en onStart)
    private var isManuallyPaused = false      // Para distinguir pausa de fin de segmento
    private var isPlaybackActive =
        false      // Indica si la reproducción general está activa (play pulsado)

    // --- Coroutines ---
    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    // --- StateFlow for UI Updates ---
    private val _ttsProgressFlow = MutableStateFlow(TtsProgressData())
    val ttsProgressFlow = _ttsProgressFlow.asStateFlow()

    // --- Binder for Client Interaction ---
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): TextToSpeechService = this@TextToSpeechService
    }

    // --- Service Lifecycle Methods ---
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        _ttsProgressFlow.value =
            TtsProgressData(playbackState = TtsPlaybackState.BUFFERING, isBuffering = true)
        tts = TextToSpeech(this, this) // Inicia la inicialización de TTS
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand action: ${intent?.action}")
        if (intent?.action == ACTION_STOP_SERVICE) {
            // Este Intent se usa para detener el servicio desde la notificación
            stopPlaybackAndService()
            return START_NOT_STICKY
        }
        // El servicio se inicia explícitamente y se mantiene en ejecución
        // hasta que se detiene explícitamente o no hay clientes vinculados y no está activo.
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        // Si no está reproduciendo activamente y no hay clientes,
        // podríamos considerar detener el servicio después de un tiempo.
        // Por ahora, lo mantenemos simple y permitimos rebinding.
        // Si se requiere auto-stop, se necesitaría lógica más compleja.
        return true // Permitir rebinding
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        tts?.stop()
        tts?.shutdown()
        isTtsInitialized = false
        serviceJob.cancel() // Cancelar todas las coroutines
    }

    // Dentro de la clase TextToSpeechService

    // --- TextToSpeech.OnInitListener ---
    override fun onInit(status: Int) {
        serviceScope.launch { // Usar serviceScope para asegurar que se cancela con el servicio
            if (status == TextToSpeech.SUCCESS) {
                isTtsInitialized = true
                Log.d(
                    TAG,
                    "TTS Initialized successfully. Current language: ${currentLanguage.displayName}"
                )

                // Aplicar configuraciones iniciales
                tts?.language = currentLanguage
                tts?.setSpeechRate(currentSpeechRate)
                tts?.setPitch(currentSpeechPitch)

                // Configurar el listener de progreso de las utterances
                tts?.setOnUtteranceProgressListener(ttsProgressListener)

                // Comprobar si había una reproducción pendiente (playText llamado antes de onInit)
                if (_ttsProgressFlow.value.playbackState == TtsPlaybackState.BUFFERING && textSegments.isNotEmpty() && isPlaybackActive) {
                    Log.d(TAG, "onInit: Pending playback detected, starting playInternal.")
                    playInternal() // Iniciar la reproducción interna
                } else if (_ttsProgressFlow.value.playbackState == TtsPlaybackState.BUFFERING) {
                    // Si estaba en buffering pero no hay nada que reproducir, pasar a IDLE
                    Log.d(TAG, "onInit: Was buffering but no segments to play, moving to IDLE.")
                    _ttsProgressFlow.value = TtsProgressData(
                        playbackState = TtsPlaybackState.IDLE,
                        isBuffering = false
                    )
                }
                // Si ya estaba en otro estado (ej. IDLE por defecto), no hacer nada aquí.
            } else {
                Log.e(TAG, "TTS Initialization failed with status: $status")
                _ttsProgressFlow.value = TtsProgressData(
                    playbackState = TtsPlaybackState.ERROR,
                    error = "Falló la inicialización de TTS (código: $status)",
                    isBuffering = false
                )
                // No se puede continuar si TTS no inicializa
                stopPlaybackAndService()
            }
        }
    }

    private val ttsProgressListener = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            val startedSegmentIndex = utteranceId?.toIntOrNull()
            Log.d(TAG, "onStart utteranceId: $utteranceId (Index: $startedSegmentIndex)")

            serviceScope.launch {
                if (startedSegmentIndex != null && startedSegmentIndex >= 0 && startedSegmentIndex < textSegments.size) {
                    lastSpokenSegmentIndex = startedSegmentIndex
                    // ESTA ES LA LÍNEA CRUCIAL para el progreso del slider
                    _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                        currentSegment = startedSegmentIndex, // Asegúrate de que esto se actualiza
                        currentSegmentText = textSegments[startedSegmentIndex],
                        playbackState = TtsPlaybackState.PLAYING,
                        isBuffering = false,
                        error = null,
                        // No olvides speechRate y speechPitch si los tienes en TtsProgressData
                        speechRate = currentSpeechRate,
                        speechPitch = currentSpeechPitch
                    )
                    updateNotification("Leyendo segmento ${startedSegmentIndex + 1}/${textSegments.size}")
                }
            }
        }

        override fun onDone(utteranceId: String?) {
            val doneSegmentIndex = utteranceId?.toIntOrNull()
            Log.d(TAG, "onDone utteranceId: $utteranceId (Index: $doneSegmentIndex)")

            serviceScope.launch {
                if (doneSegmentIndex != null) {
                    lastSpokenSegmentIndex = doneSegmentIndex
                }
                // Si no estamos pausados manualmente y la reproducción está activa, reproducir el siguiente
                if (!isManuallyPaused && isPlaybackActive) {
                    currentSegmentIndexToPlay =
                        (doneSegmentIndex ?: (currentSegmentIndexToPlay - 1)) + 1
                    playInternal() // Intenta reproducir el siguiente segmento
                } else if (!isPlaybackActive && !isManuallyPaused) {
                    // Si la reproducción no está activa (ej. se detuvo por error o fin) y no fue pausa manual
                    Log.d(
                        TAG,
                        "onDone: Playback not active and not manually paused. Likely completed or error."
                    )
                    // El estado ya debería haberse actualizado en playInternal() o onError()
                }
            }
        }

        @Deprecated("Deprecated in Java", ReplaceWith("onError(utteranceId, errorCode)"))
        override fun onError(utteranceId: String?) {
            // Este método está obsoleto, pero se incluye por si alguna implementación antigua de TTS lo llama.
            val errorSegmentIndex = utteranceId?.toIntOrNull()
            Log.e(TAG, "onError (deprecated) utteranceId: $utteranceId (Index: $errorSegmentIndex)")
            handleTtsError("Error en TTS (deprecated) para segmento $errorSegmentIndex")
        }

        override fun onError(utteranceId: String?, errorCode: Int) {
            val errorSegmentIndex = utteranceId?.toIntOrNull()
            Log.e(
                TAG,
                "onError utteranceId: $utteranceId (Index: $errorSegmentIndex), errorCode: $errorCode"
            )
            handleTtsError("Error en TTS (código: $errorCode) para segmento $errorSegmentIndex")
        }

        override fun onStop(utteranceId: String?, interrupted: Boolean) {
            // Este callback es útil para saber si un `tts.stop()` interrumpió una utterance.
            // No siempre se llama de forma fiable en todas las implementaciones de TTS.
            Log.d(TAG, "onStop utteranceId: $utteranceId, interrupted: $interrupted")
            super.onStop(utteranceId, interrupted)
        }

        // Podríamos añadir onAudioAvailable si quisiéramos procesar el audio, pero no es necesario para este caso.
    }

    private fun handleTtsError(errorMessage: String) {
        serviceScope.launch {
            isPlaybackActive = false // Detener la reproducción activa en caso de error
            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                playbackState = TtsPlaybackState.ERROR,
                error = errorMessage,
                isBuffering = false
            )
            updateNotification("Error en TTS")
            // Considerar si se debe detener el servicio aquí o permitir reintentos.
            // stopSelf() // Podría ser una opción si el error es irrecuperable.
        }
    }

    fun playText(text: String, language: Locale = currentLanguage) {
        serviceScope.launch {
            if (text.isBlank()) {
                _ttsProgressFlow.value = TtsProgressData(
                    playbackState = TtsPlaybackState.ERROR,
                    error = "El texto está vacío"
                )
                return@launch
            }
            Log.d(TAG, "playText called. Current state: ${_ttsProgressFlow.value.playbackState}")

            // Detener cualquier reproducción anterior y limpiar segmentos
            stopCurrentPlaybackInternal(clearSegments = true)
            isManuallyPaused = false
            isPlaybackActive = true // Marcar la reproducción como activa
            fullText = text
            currentLanguage = language // Actualizar idioma si se proporciona uno nuevo

            // Si el TTS no está inicializado, onInit se encargará de llamar a playInternal después.
            if (!isTtsInitialized) {
                Log.w(TAG, "playText: TTS not initialized yet. Buffering.")
                // Dividir el texto ahora para que onInit pueda usar los segmentos
                textSegments = splitTextIntoSegments(fullText)
                if (textSegments.isEmpty()) {
                    _ttsProgressFlow.value = TtsProgressData(
                        playbackState = TtsPlaybackState.ERROR,
                        error = "No se pudieron generar segmentos de texto.",
                        isBuffering = false
                    )
                    isPlaybackActive = false
                    return@launch
                }
                _ttsProgressFlow.value = TtsProgressData(
                    totalSegments = textSegments.size,
                    playbackState = TtsPlaybackState.BUFFERING,
                    isBuffering = true
                )
                // Re-inicializar o verificar TTS si es necesario (onInit se llamará si tts.speak falla la primera vez)
                if (tts == null) tts =
                    TextToSpeech(this@TextToSpeechService, this@TextToSpeechService)
                else if (tts?.language != currentLanguage) tts?.language = currentLanguage

                return@launch // onInit se encargará del resto
            }

            // Si ya está inicializado, proceder
            if (tts?.language != currentLanguage) {
                val langResult = tts?.setLanguage(currentLanguage)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "TTS Language ${currentLanguage.displayName} not supported.")
                    _ttsProgressFlow.value = TtsProgressData(
                        playbackState = TtsPlaybackState.ERROR,
                        error = "Idioma no soportado: ${currentLanguage.displayName}",
                        isBuffering = false
                    )
                    isPlaybackActive = false
                    return@launch
                }
            }

            textSegments = splitTextIntoSegments(fullText)
            if (textSegments.isEmpty()) {
                _ttsProgressFlow.value = TtsProgressData(
                    playbackState = TtsPlaybackState.ERROR,
                    error = "No se pudieron generar segmentos de texto.",
                    isBuffering = false
                )
                isPlaybackActive = false
                return@launch
            }

            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                totalSegments = textSegments.size,
                currentSegment = 0,
                playbackState = TtsPlaybackState.BUFFERING, // Cambiará a PLAYING en onStart
                isBuffering = true,
                error = null
            )
            currentSegmentIndexToPlay = 0
            lastSpokenSegmentIndex = -1
            startForegroundServiceIfNeeded()
            playInternal()
        }
    }

    fun pausePlayback() {
        serviceScope.launch {
            if (_ttsProgressFlow.value.playbackState == TtsPlaybackState.PLAYING) {
                Log.d(TAG, "pausePlayback called")
                isManuallyPaused = true
                // isPlaybackActive sigue true, porque la "sesión" de reproducción está activa, solo pausada.
                val result = tts?.stop() // Detiene la emisión actual. onDone podría no llamarse.
                if (result == TextToSpeech.SUCCESS) {
                    // El estado se actualiza para reflejar la pausa.
                    // lastSpokenSegmentIndex ya debería estar actualizado por onStart del segmento actual.
                    // Si onStart no se llamó aún para el currentSegmentIndexToPlay,
                    // entonces lastSpokenSegmentIndex podría ser el anterior.
                    // Es importante que currentSegmentIndexToPlay no avance si pausamos antes de que onStart se dispare.
                    _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                        playbackState = TtsPlaybackState.PAUSED,
                        isBuffering = false // No estamos bufferizando si estamos pausados
                    )
                    updateNotification("Pausado")
                } else {
                    Log.e(TAG, "TTS stop failed during pause attempt.")
                    handleTtsError("Fallo al intentar pausar TTS")
                }
            } else {
                Log.w(
                    TAG,
                    "pausePlayback called when not playing. State: ${_ttsProgressFlow.value.playbackState}"
                )
            }
        }
    }

    fun resumePlayback() {
        serviceScope.launch {
            if (_ttsProgressFlow.value.playbackState == TtsPlaybackState.PAUSED && textSegments.isNotEmpty()) {
                Log.d(TAG, "resumePlayback called")
                isManuallyPaused = false
                isPlaybackActive = true // Asegurar que la reproducción general está activa

                // Si lastSpokenSegmentIndex es válido y no es el último segmento,
                // currentSegmentIndexToPlay debería ser el que estaba en curso o el siguiente.
                // Si pausamos justo cuando un segmento estaba por empezar (onStart no se llamó),
                // currentSegmentIndexToPlay ya apunta al correcto.
                // Si pausamos después de onStart, lastSpokenSegmentIndex es el actual.
                // Queremos reanudar desde lastSpokenSegmentIndex.
                if (lastSpokenSegmentIndex >= 0 && lastSpokenSegmentIndex < textSegments.size) {
                    currentSegmentIndexToPlay = lastSpokenSegmentIndex
                } else if (currentSegmentIndexToPlay < 0 || currentSegmentIndexToPlay >= textSegments.size) {
                    currentSegmentIndexToPlay = 0 // Fallback al inicio si el índice es inválido
                }


                _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                    playbackState = TtsPlaybackState.BUFFERING, // Cambiará a PLAYING en onStart
                    isBuffering = true,
                    error = null
                )
                startForegroundServiceIfNeeded()
                playInternal() // playInternal usará currentSegmentIndexToPlay
            } else {
                Log.w(
                    TAG,
                    "resumePlayback called when not paused or no segments. State: ${_ttsProgressFlow.value.playbackState}"
                )
            }
        }
    }

    fun stopPlayback() {
        serviceScope.launch {
            Log.d(TAG, "stopPlayback called")
            stopCurrentPlaybackInternal(clearSegments = false) // No limpiar segmentos, podrían reusarse
            isPlaybackActive = false
            isManuallyPaused = false
            currentSegmentIndexToPlay = 0 // Resetear al inicio para la próxima reproducción
            lastSpokenSegmentIndex = -1
            _ttsProgressFlow.value = TtsProgressData( // Resetear a un estado IDLE limpio
                totalSegments = textSegments.size, // Mantener el total por si se quiere reanudar la misma lista
                playbackState = TtsPlaybackState.STOPPED,
                isBuffering = false
            )
            updateNotification("Detenido")
            stopForegroundIfIdle() // Detener foreground si ya no se necesita
        }
    }

    fun seekToSegment(segmentIndex: Int) {
        serviceScope.launch {
            if (!isTtsInitialized || textSegments.isEmpty()) {
                Log.w(TAG, "seekToSegment called but TTS not ready or no segments.")
                // Podríamos cargar el texto aquí si es necesario y no está cargado.
                return@launch
            }
            if (segmentIndex < 0 || segmentIndex >= textSegments.size) {
                Log.e(
                    TAG,
                    "seekToSegment: Invalid segment index $segmentIndex. Total: ${textSegments.size}"
                )
                _ttsProgressFlow.value =
                    _ttsProgressFlow.value.copy(error = "Índice de segmento inválido para buscar.")
                return@launch
            }

            Log.d(TAG, "seekToSegment to index $segmentIndex")
            stopCurrentPlaybackInternal(clearSegments = false) // Detener lo actual, no limpiar segmentos

            isManuallyPaused = false // Si estaba pausado, el seek lo "reanuda" en nueva posición
            isPlaybackActive = true  // La reproducción se considera activa después de un seek
            currentSegmentIndexToPlay = segmentIndex
            lastSpokenSegmentIndex = segmentIndex - 1 // Para que onDone no se confunda

            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                currentSegment = segmentIndex, // Actualizar inmediatamente para la UI
                currentSegmentText = textSegments[segmentIndex],
                playbackState = TtsPlaybackState.BUFFERING,
                isBuffering = true,
                error = null
            )
            startForegroundServiceIfNeeded()
            playInternal()
        }
    }

    fun setSpeechRate(rate: Float) {
        serviceScope.launch {
            if (isTtsInitialized) {
                val result = tts?.setSpeechRate(rate)
                if (result == TextToSpeech.SUCCESS) {
                    currentSpeechRate = rate
                    Log.d(TAG, "Speech rate set to $rate")
                } else {
                    Log.e(TAG, "Failed to set speech rate to $rate")
                }
            } else {
                currentSpeechRate = rate // Guardar para cuando se inicialice
            }
        }
    }

    fun setSpeechPitch(pitch: Float) {
        serviceScope.launch {
            if (isTtsInitialized) {
                val result = tts?.setPitch(pitch)
                if (result == TextToSpeech.SUCCESS) {
                    currentSpeechPitch = pitch
                    Log.d(TAG, "Speech pitch set to $pitch")
                } else {
                    Log.e(TAG, "Failed to set speech pitch to $pitch")
                }
            } else {
                currentSpeechPitch = pitch // Guardar para cuando se inicialice
            }
        }
    }

    private fun playInternal() {
        // Este método es el corazón de la reproducción de segmentos
        if (!isTtsInitialized) {
            Log.w(TAG, "playInternal: TTS not initialized. Buffering.")
            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                playbackState = TtsPlaybackState.BUFFERING,
                isBuffering = true
            )
            // onInit se encargará de llamar a playInternal si es necesario
            // o si se llama a playText() y tts no está listo, se re-inicializará.
            if (tts == null) tts = TextToSpeech(this, this)
            return
        }

        if (textSegments.isEmpty()) {
            Log.d(TAG, "playInternal: No segments to play.")
            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                playbackState = TtsPlaybackState.IDLE,
                isBuffering = false,
                currentSegment = 0, // Reset
                totalSegments = 0,  // Reset
                currentSegmentText = "" // Reset
            )
            isPlaybackActive = false
            stopForegroundIfIdle()
            return
        }

        if (currentSegmentIndexToPlay < 0 || currentSegmentIndexToPlay >= textSegments.size) {
            // Todos los segmentos han sido reproducidos
            Log.d(
                TAG,
                "playInternal: All segments played. currentSegmentIndexToPlay: $currentSegmentIndexToPlay"
            )
            _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
                currentSegment = if (textSegments.isNotEmpty()) textSegments.size - 1 else 0, // Mantener el último segmento como actual
                playbackState = TtsPlaybackState.COMPLETED,
                isBuffering = false
            )
            isPlaybackActive = false
            updateNotification("Lectura completada")
            stopForegroundIfIdle()
            return
        }

        // Si estamos aquí, hay un segmento válido para reproducir
        val segmentToPlay = textSegments[currentSegmentIndexToPlay]
        val utteranceId = currentSegmentIndexToPlay.toString() // Usamos el índice como ID

        // Asegurar que el idioma, velocidad y tono están aplicados (podrían cambiar)
        tts?.language = currentLanguage
        tts?.setSpeechRate(currentSpeechRate)
        tts?.setPitch(currentSpeechPitch)

        Log.d(
            TAG,
            "playInternal: Speaking segment $currentSegmentIndexToPlay: '${segmentToPlay.take(50)}...'"
        )
        val params = Bundle() // Bundle para parámetros adicionales si fueran necesarios
        // params.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC.toString()) // Ejemplo

        // Actualizar estado a BUFFERING justo antes de llamar a speak
        // onStart del listener lo cambiará a PLAYING
        _ttsProgressFlow.value = _ttsProgressFlow.value.copy(
            currentSegment = currentSegmentIndexToPlay,
            totalSegments = textSegments.size,
            currentSegmentText = segmentToPlay,
            playbackState = TtsPlaybackState.BUFFERING,
            isBuffering = true,
            error = null
        )

        val result = tts?.speak(segmentToPlay, TextToSpeech.QUEUE_FLUSH, params, utteranceId)

        if (result == TextToSpeech.ERROR) {
            Log.e(TAG, "playInternal: TTS speak() failed for segment $currentSegmentIndexToPlay")
            handleTtsError("TTS speak() falló para el segmento $currentSegmentIndexToPlay")
        }
        // Si es SUCCESS, el UtteranceProgressListener se encargará del resto (onStart, onDone, onError)
    }

    private fun playNextSegment() {
        // Llamado desde onDone del UtteranceProgressListener
        if (isManuallyPaused || !isPlaybackActive) {
            // Si está pausado manualmente o la reproducción general se detuvo, no continuar.
            Log.d(
                TAG,
                "playNextSegment: Not playing next due to manual pause or inactive playback."
            )
            return
        }
        // currentSegmentIndexToPlay ya debería haber sido incrementado en onDone
        // o establecido por seekToSegment.
        playInternal()
    }

    private fun stopCurrentPlaybackInternal(clearSegments: Boolean) {
        Log.d(TAG, "stopCurrentPlaybackInternal called. Clear segments: $clearSegments")
        tts?.stop() // Detiene la síntesis actual
        // isPlaybackActive se manejará por el método público que llame a este.
        // isManuallyPaused también.

        if (clearSegments) {
            textSegments = emptyList()
            fullText = ""
            currentSegmentIndexToPlay = 0
            lastSpokenSegmentIndex = -1
            // No cambiar el estado en _ttsProgressFlow aquí directamente,
            // el método público (playText, stopPlayback) lo hará.
        }
    }

    private fun userStopPlayback() {
        // Este método es para cuando el usuario explícitamente detiene todo.
        serviceScope.launch {
            Log.d(TAG, "userStopPlayback called")
            stopCurrentPlaybackInternal(clearSegments = true) // Limpiar todo
            isPlaybackActive = false
            isManuallyPaused = false
            _ttsProgressFlow.value = TtsProgressData(playbackState = TtsPlaybackState.STOPPED)
            updateNotification("Servicio detenido")
            stopForeground(true) // Detener foreground y remover notificación
            stopSelf() // Detener el servicio
        }
    }

    private fun stopPlaybackAndService() {
        // Similar a userStopPlayback pero puede ser llamado internamente (ej. error irrecuperable)
        serviceScope.launch {
            Log.d(TAG, "stopPlaybackAndService called")
            stopCurrentPlaybackInternal(clearSegments = true)
            isPlaybackActive = false
            isManuallyPaused = false
            // El estado de error ya debería estar puesto si es por un error
            if (_ttsProgressFlow.value.playbackState != TtsPlaybackState.ERROR) {
                _ttsProgressFlow.value = TtsProgressData(playbackState = TtsPlaybackState.IDLE)
            }
            stopForeground(true)
            stopSelf()
        }
    }

    private fun splitTextIntoSegments(text: String): List<String> {
        if (text.isBlank()) return emptyList()

        val segments = mutableListOf<String>()
        // Estrategia simple: dividir por saltos de línea o por longitud máxima.
        // Una estrategia más avanzada podría usar frases (ej. con BreakIterator).
        val potentialSegments = text.split("\n\n", "\n") // Párrafos y luego saltos simples

        for (potentialSegment in potentialSegments) {
            var currentSubSegment = potentialSegment.trim()
            while (currentSubSegment.isNotEmpty()) {
                if (currentSubSegment.length <= MAX_CHARS_PER_SEGMENT) {
                    if (currentSubSegment.isNotBlank()) segments.add(currentSubSegment)
                    break
                } else {
                    // Intentar encontrar un buen punto de corte (punto, coma, espacio)
                    // cerca del MAX_CHARS_PER_SEGMENT desde el final.
                    var splitIndex = -1
                    for (delimiter in listOf(". ", ", ", "; ", " ")) {
                        val lastDelimiterIndex =
                            currentSubSegment.substring(0, MAX_CHARS_PER_SEGMENT)
                                .lastIndexOf(delimiter)
                        if (lastDelimiterIndex > splitIndex) {
                            splitIndex = lastDelimiterIndex + delimiter.length - 1
                        }
                    }

                    if (splitIndex == -1 || splitIndex == 0) { // No se encontró delimitador o está al inicio
                        splitIndex = MAX_CHARS_PER_SEGMENT - 1 // Cortar a la fuerza
                    }

                    val partToAdd = currentSubSegment.substring(0, splitIndex + 1).trim()
                    if (partToAdd.isNotBlank()) segments.add(partToAdd)
                    currentSubSegment = currentSubSegment.substring(splitIndex + 1).trim()
                }
            }
        }
        Log.d(TAG, "Split text into ${segments.size} segments.")
        return segments.filter { it.isNotBlank() }
    }

    // Dentro de la clase TextToSpeechService

    private fun startForegroundServiceIfNeeded() {
        if (isPlaybackActive) { // Solo iniciar foreground si la reproducción está activa
            Log.d(TAG, "Starting foreground service.")
            startForeground(NOTIFICATION_ID, createNotification("Iniciando lectura..."))
        }
    }

    private fun stopForegroundIfIdle() {
        // Detener el servicio en primer plano solo si no está reproduciendo activamente
        // y no está pausado (lo que implica que el usuario podría querer reanudar).
        val currentState = _ttsProgressFlow.value.playbackState
        if (currentState != TtsPlaybackState.PLAYING &&
            currentState != TtsPlaybackState.BUFFERING &&
            currentState != TtsPlaybackState.PAUSED
        ) {
            Log.d(TAG, "Stopping foreground service as playback is idle/completed/stopped/error.")
            stopForeground(false) // false para mantener la notificación si se desea, o true para quitarla.
            // Para este caso, si no está activo, podemos quitarla.
            // Pero si está STOPPED, la notificación ya dice "Detenido".
            // Si es COMPLETED, dice "Completado".
            // Si es IDLE, no debería haber notificación.
            if (currentState == TtsPlaybackState.IDLE || currentState == TtsPlaybackState.ERROR) {
                // Si está realmente inactivo o en error, quitar la notificación.
                val notificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(NOTIFICATION_ID)
            }
        } else {
            Log.d(
                TAG,
                "Not stopping foreground. State: $currentState, isPlaybackActive: $isPlaybackActive"
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "TTS Service Channel",
                NotificationManager.IMPORTANCE_LOW // Usar LOW para que no sea tan intrusivo
            )
            serviceChannel.description = "Channel for Text-to-Speech background service"
        getSystemService(NotificationManager::class.java)
        //manager?.createNotificationChannel(serviceChannel)

    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun createNotification(contentText: String): Notification {
        val notificationIntent =
            Intent(this, MainActivity::class.java) // Cambia MainActivity a tu actividad principal
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, pendingIntentFlags)

        // Acción para detener el servicio desde la notificación
        val stopServiceIntent = Intent(this, TextToSpeechService::class.java).apply {
            action = ACTION_STOP_SERVICE
        }
        val stopServicePendingIntent =
            PendingIntent.getService(this, 0, stopServiceIntent, pendingIntentFlags)

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Lector TTS")
            .setContentText(contentText.take(100)) // Tomar solo una parte para la notificación
            .setSmallIcon(R.drawable.ic_terms) // Reemplaza con tu icono
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_author,
                "Detener",
                stopServicePendingIntent
            ) // Reemplaza con tu icono de stop
            .setOngoing(true) // Hace que la notificación no se pueda descartar fácilmente mientras está en foreground
            .setSilent(true) // Para que no suene cada vez que se actualiza
            .build()
    }

    private fun updateNotification(contentText: String) {
        if (_ttsProgressFlow.value.playbackState == TtsPlaybackState.IDLE && !isPlaybackActive) {
            // Si estamos realmente IDLE y no hay reproducción activa, no debería haber notificación.
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(NOTIFICATION_ID)
            return
        }
        // Solo actualizar si el servicio está en modo foreground (lo que implica que hay una notificación)
        // o si estamos a punto de entrar en un estado que requiere notificación.
        if (isPlaybackActive || _ttsProgressFlow.value.playbackState != TtsPlaybackState.IDLE) {
            val notification = createNotification(contentText)
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}