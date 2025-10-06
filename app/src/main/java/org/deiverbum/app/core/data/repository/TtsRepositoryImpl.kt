package org.deiverbum.app.core.data.repository


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.media.service.TextToSpeechService
import org.deiverbum.app.core.model.tts.TtsProgressData
import org.deiverbum.app.feature.tts.TtsPlaybackState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // El repositorio será un Singleton, gestionando una única conexión al servicio
class TtsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TtsRepository {

    companion object {
        private const val TAG = "TtsRepositoryImpl"
    }

    private var ttsService: TextToSpeechService? = null
    private var isServiceBound = false
    private val repositoryScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private var progressCollectionJob: Job? = null

    // Un StateFlow local que refleja el del servicio o un estado por defecto si no está conectado.
    private val _repositoryTtsProgress = MutableStateFlow(TtsProgressData())
    override val ttsProgress: StateFlow<TtsProgressData> = _repositoryTtsProgress.asStateFlow()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "TextToSpeechService connected.")
            val binder = service as TextToSpeechService.LocalBinder
            ttsService = binder.getService()
            isServiceBound = true

            progressCollectionJob?.cancel()
            progressCollectionJob = repositoryScope.launch {
                // Empezar a observar el flow del servicio
                // Usar .value directamente si el servicio ya tiene un valor inicial
                // o si el StateFlow del servicio garantiza emitir inmediatamente.
                _repositoryTtsProgress.value = ttsService?.ttsProgressFlow?.value
                    ?: TtsProgressData(playbackState = TtsPlaybackState.IDLE) // Fallback

                ttsService?.ttsProgressFlow?.collect { progressData ->
                    _repositoryTtsProgress.value = progressData
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "TextToSpeechService disconnected unexpectedly.")
            isServiceBound = false
            ttsService = null
            progressCollectionJob?.cancel()
            _repositoryTtsProgress.value = TtsProgressData(
                playbackState = TtsPlaybackState.ERROR,
                error = "Servicio TTS desconectado"
            )
            // Opcional: Intentar re-vincular aquí.
            // bindToService()
        }

        override fun onBindingDied(name: ComponentName?) {
            Log.e(TAG, "Binding to TextToSpeechService died.")
            isServiceBound = false
            ttsService = null
            progressCollectionJob?.cancel()
            _repositoryTtsProgress.value = TtsProgressData(
                playbackState = TtsPlaybackState.ERROR,
                error = "Conexión con servicio TTS perdida (binding died)"
            )
            // Opcional: Intentar re-vincular aquí.
            // bindToService()
        }

        override fun onNullBinding(name: ComponentName?) {
            Log.e(TAG, "Null binding received from TextToSpeechService. This should not happen.")
            isServiceBound = false
            ttsService = null
            progressCollectionJob?.cancel()
            _repositoryTtsProgress.value = TtsProgressData(
                playbackState = TtsPlaybackState.ERROR,
                error = "Error de vinculación con servicio TTS (null binding)"
            )
        }
    }

    init {
        Log.d(TAG, "Initializing TtsRepositoryImpl. Attempting to bind to service.")
        bindToService()
    }

    private fun bindToService() {
        if (isServiceBound && ttsService != null) {
            Log.d(TAG, "Service already bound.")
            return
        }
        Log.d(TAG, "Attempting to bind to TextToSpeechService.")
        try {
            val serviceIntent = Intent(context, TextToSpeechService::class.java)
            // Iniciar el servicio primero para asegurar que se mantenga vivo
            context.startService(serviceIntent)
            val bound =
                context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (!bound) {
                Log.e(TAG, "bindService returned false. Could not bind to TextToSpeechService.")
                _repositoryTtsProgress.value = TtsProgressData(
                    playbackState = TtsPlaybackState.ERROR,
                    error = "No se pudo vincular al servicio TTS"
                )
            } else {
                Log.d(TAG, "bindService call successful. Waiting for onServiceConnected.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error binding to service", e)
            _repositoryTtsProgress.value = TtsProgressData(
                playbackState = TtsPlaybackState.ERROR,
                error = "Excepción al vincular al servicio TTS: ${e.message}"
            )
        }
    }

    override fun playText(text: String) {
        // Envolver la lógica de asegurar la conexión en una función reutilizable
        executeServiceCall(
            actionDescription = "playText",
            onServiceAvailable = { it.playText(text) },
            onServiceUnavailableError = "No se pudo conectar al servicio TTS para reproducir."
        )
    }

    override fun pause() {
        executeServiceCall(
            actionDescription = "pause",
            onServiceAvailable = { it.pausePlayback() },
            onServiceUnavailableError = "Servicio no disponible para pausar."
        )
    }

    override fun resume() {
        executeServiceCall(
            actionDescription = "resume",
            onServiceAvailable = { it.resumePlayback() },
            onServiceUnavailableError = "Servicio no disponible para reanudar."
        )
    }

    override fun stop() {
        executeServiceCall(
            actionDescription = "stop",
            onServiceAvailable = { it.stopPlayback() },
            onServiceUnavailableError = "Servicio no disponible para detener."
        )
    }

    override fun seekToSegment(segmentIndex: Int) {
        executeServiceCall(
            actionDescription = "seekToSegment",
            onServiceAvailable = { it.seekToSegment(segmentIndex) },
            onServiceUnavailableError = "Servicio no disponible para buscar."
        )
    }

    override fun setSpeechRate(rate: Float) {
        executeServiceCall(
            actionDescription = "setSpeechRate",
            onServiceAvailable = { it.setSpeechRate(rate) },
            onServiceUnavailableError = "Servicio no disponible para ajustar velocidad."
        )
    }

    override fun setSpeechPitch(pitch: Float) {
        executeServiceCall(
            actionDescription = "setSpeechPitch",
            onServiceAvailable = { it.setSpeechPitch(pitch) },
            onServiceUnavailableError = "Servicio no disponible para ajustar tono."
        )
    }

    /**
     * Método de ayuda para ejecutar una acción en el servicio TTS,
     * manejando la lógica de reintento de conexión si es necesario.
     */
    private fun executeServiceCall(
        actionDescription: String,
        onServiceAvailable: (TextToSpeechService) -> Unit,
        onServiceUnavailableError: String,
        maxRetries: Int = 3, // Número de reintentos para conectar
        retryDelayMillis: Long = 300 // Tiempo entre reintentos
    ) {
        repositoryScope.launch { // Lanzar en el scope del repositorio
            if (isServiceBound && ttsService != null) {
                Log.d(TAG, "Executing '$actionDescription' on service.")
                onServiceAvailable(ttsService!!)
            } else {
                Log.w(TAG, "$actionDescription: Service not bound. Attempting to bind and retry.")
                // Actualizar el estado para indicar que se está intentando conectar
                _repositoryTtsProgress.value = _repositoryTtsProgress.value.copy(
                    playbackState = if (_repositoryTtsProgress.value.playbackState == TtsPlaybackState.IDLE ||
                        _repositoryTtsProgress.value.playbackState == TtsPlaybackState.STOPPED ||
                        _repositoryTtsProgress.value.playbackState == TtsPlaybackState.COMPLETED
                    )
                        TtsPlaybackState.BUFFERING else _repositoryTtsProgress.value.playbackState,
                    isBuffering = true,
                    error = "Conectando al servicio TTS..."
                )
                bindToService() // Asegurar que se intente la vinculación

                var attempts = 0
                while ((!isServiceBound || ttsService == null) && attempts < maxRetries) {
                    delay(retryDelayMillis)
                    attempts++
                    Log.d(
                        TAG,
                        "$actionDescription: Retry attempt $attempts/$maxRetries for service connection."
                    )
                }

                if (isServiceBound && ttsService != null) {
                    Log.d(
                        TAG,
                        "Executing '$actionDescription' on service after successful bind/retry."
                    )
                    onServiceAvailable(ttsService!!)
                } else {
                    Log.e(
                        TAG,
                        "$actionDescription: Failed to connect to service after $maxRetries retries."
                    )
                    _repositoryTtsProgress.value = _repositoryTtsProgress.value.copy(
                        playbackState = TtsPlaybackState.ERROR,
                        error = onServiceUnavailableError,
                        isBuffering = false
                    )
                }
            }
        }
    }

    override fun release() {
        // Este método es importante para limpiar cuando el repositorio ya no se necesita,
        // por ejemplo, si el ViewModel que lo usa se destruye y el servicio no debe persistir.
        // Sin embargo, dado que el servicio puede estar en primer plano,
        // unbindService no necesariamente lo detiene si fue iniciado con startService.
        Log.d(TAG, "release() called. Unbinding from service if bound.")
        if (isServiceBound) {
            try {
                // No llamar a ttsService?.stopPlaybackAndService() directamente aquí,
                // ya que el servicio podría estar siendo usado por otra parte o
                // se espera que continúe en segundo plano.
                // El ViewModel debe decidir si detener el servicio explícitamente.
                context.unbindService(serviceConnection)
            } catch (e: IllegalArgumentException) {
                // Esto puede suceder si el servicio ya no estaba registrado (raro si isServiceBound es true)
                Log.w(TAG, "Error unbinding service: ${e.message}")
            }
            isServiceBound = false
            ttsService = null
            progressCollectionJob?.cancel()
            // Considerar si se debe resetear el _repositoryTtsProgress a IDLE
            // _repositoryTtsProgress.value = TtsProgressData()
        }
        // Cancelar el scope del repositorio si este repositorio se va a destruir completamente.
        // Como es un Singleton, su scope vivirá mientras viva la aplicación.
        // Si no fuera Singleton, aquí se podría llamar a repositoryScope.cancel().
    }

    // Opcional: Un método para que el ViewModel pueda forzar la detención del servicio
// si el repositorio es el único que lo gestiona.
    fun stopAndReleaseService() {
        repositoryScope.launch {
            if (isServiceBound && ttsService != null) {
                Log.d(TAG, "stopAndReleaseService: Telling service to stop itself.")
                // Aquí sí podríamos llamar a un método del servicio que lo detenga completamente
                // ttsService?.userStopPlayback() // Asumiendo que userStopPlayback detiene el servicio
            }
            release() // Luego desvincular
            // Y si el servicio fue iniciado con startService, también hay que detenerlo.
            val serviceIntent = Intent(context, TextToSpeechService::class.java)
            context.stopService(serviceIntent)
            _repositoryTtsProgress.value = TtsProgressData(playbackState = TtsPlaybackState.IDLE)
        }
    }
}