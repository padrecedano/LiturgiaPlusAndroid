package org.deiverbum.app.feature.tts


// ... otras importaciones ...
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.Timeline
import androidx.media3.common.util.Clock
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.util.Constants
import timber.log.Timber
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@UnstableApi
@Singleton
class TtsPlayerOld @Inject constructor(
    @Named("MainLooper") private val mainLooper: Looper, // <--- Hilt inyectará Looper.getMainLooper() aquí
    @ApplicationContext private val applicationContext: Context
) : SimpleBasePlayer(mainLooper, Clock.DEFAULT), TextToSpeech.OnInitListener {

    private val mainHandler: Handler =
        Handler(mainLooper) // Ahora puedes crear el Handler directamente

    private val playWhenReadyChangeReason: Int = Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST
    private var internalPlaybackState: @Player.State Int = Player.STATE_IDLE
    private var internalPlayerError: PlaybackException? = null

    // Callbacks para el progreso
    //internal var segmentProgressListener: (progress: Float, currentPosition: String, totalDuration: String) -> Unit = { _, _, _ -> }


    private val textToSpeechEngine: TextToSpeech
    private var ttsInitialized = false

    // private var localSegmentTexts: List<String> = emptyList()
    private var localSegmentTexts: MutableList<String> = mutableListOf()
    private var pendingTextToSet: String? = ""

    private var textSegments: List<String> = emptyList()
    private var conceptualMediaItems: List<MediaItem> = emptyList()

    private var reportedPlayerState: State = getDefaultPlayerInitialState()

    //private val mainHandler = Handler(looper)
    private var currentUtteranceId: String? = null
    private var intendedPlayWhenReady = false

    // Para estimar la duración total del segmento actual (ya que onRangeStart no la da)
    private var currentSegmentEstimatedDurationMs: Long = 0L
    private var currentSeekBackIncrementMs: Long = C.TIME_UNSET // O un valor predeterminado
    private var currentSeekForwardIncrementMs: Long = C.TIME_UNSET // O un valor predeterminado
    private var currentMaxSeekToPreviousPositionMs: Long = DEFAULT_MAX_SEEK_TO_PREVIOUS_MS_CUSTOM
    private var currentTimeline: Timeline = Timeline.EMPTY // Inicialízala con Timeline.EMPTY

    private var fullTextToSpeak: String = "" // Para almacenar el texto completo

    private var isTtsInitialized: Boolean = false
    private var pendingPlayOnTtsInit: Boolean =
        false // Si se llamó a play() antes de que TTS estuviera listo
    private var currentLanguage: Locale = Locale("spa", "ESP")
// O un idioma específico que quieras

    private var state = State.Builder()

        // 1. Copia las propiedades que SimpleBasePlayer gestiona y que no modificamos directamente aquí.
        //    Estas se tomarán del 'this' (SimpleBasePlayer) cuando se acceda al estado.
        //.setAvailableCommands(this.availableCommands)
        .setAvailableCommands(
            getDefaultPlayerCommands()
        )
        .setPlaylistMetadata(this.playlistMetadata)
        .setSeekBackIncrementMs(this.currentSeekBackIncrementMs)
        .setSeekForwardIncrementMs(this.currentSeekForwardIncrementMs)
        .setMaxSeekToPreviousPositionMs(this.currentMaxSeekToPreviousPositionMs)
        // NO establecemos Timeline ni CurrentMediaItemIndex en este builder.
        // Estas propiedades se leerán directamente de 'this' (SimpleBasePlayer)
        // cuando se llame a getState() o cuando SBP notifique a sus listeners.
        .build()


    //private var internalPlaybackState: Int = Player.STATE_IDLE

    // Variable para almacenar el error interno de tu lógica TTS
    //private var internalPlayerError: PlaybackException? = null

    // Variable para almacenar la intención de playWhenReady
    //private var intendedPlayWhenReady: Boolean = false

    // Constante para la razón del cambio de playWhenReady (simplificación)
    // private val playWhenReadyChangeReason: Int = Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST

    // Variable para el índice del MediaItem actual que se está reproduciendo o se va a reproducir
// Inicializado a C.INDEX_UNSET hasta que se establezcan los items y se seleccione uno.
    private var currentMediaItemIndex: Int = C.INDEX_UNSET

    // Variable para la posición actual dentro del segmento actual (en milisegundos)
    private var currentPositionMs: Long = 0L

    // Variable para la duración estimada del segmento actual (en milisegundos)
    //private var currentSegmentEstimatedDurationMs: Long = 0L

// (Opcional) Si necesitas una referencia explícita a la Timeline que SBP usa,
// aunque para TTS la gestión es más sobre los segmentos.
// private var playerTimeline: Timeline = Timeline.EMPTY

    // Variable para mantener el estado que reportas a SBP
// Debe inicializarse con un estado base.


    // TTS Engine y su estado de inicialización
    //private lateinit var textToSpeechEngine: TextToSpeech
    //private var isTtsInitialized = false
    //private var pendingTextToSet: String? = null // Para texto que llega antes de que TTS esté listo


    // Constantes internas


    companion object {
        private const val DEFAULT_MAX_SEEK_TO_PREVIOUS_MS_CUSTOM = 3000L // 3 segundos
        private const val TAG = "TtsPlayerForApi26"

        // Valores predeterminados si no se establecen explícitamente
        private const val DEFAULT_SEEK_INCREMENT_MS = 10000L // 10 segundos
        private const val MAX_TTS_SEGMENT_LENGTH_INTERNAL = 3999 // Límite para el motor TTS

        // Constante para estimar la duración total del segmento.
        // Aunque el progreso se basa en onRangeStart, la duración total mostrada aún puede ser una estimación.
        private const val AVG_CHARS_PER_SECOND_FOR_DURATION_ESTIMATE = 12
    }


    init {
        Timber.d("TtsPlayerForApi26: Initialized instance: $this")
        currentSeekBackIncrementMs = DEFAULT_SEEK_INCREMENT_MS
        currentSeekForwardIncrementMs = DEFAULT_SEEK_INCREMENT_MS
        Timber.tag(TAG).d("Initializing TtsPlayer (API 26+ for progress)...")
        textToSpeechEngine = TextToSpeech(applicationContext, this)
        addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                val newSbpIndex = currentMediaItemIndex
                Timber.d("SBP Listener: onMediaItemTransition - New SBP Index: $newSbpIndex, Reason: $reason")
                if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_SEEK ||
                    reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO ||
                    reason == Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED
                ) {
                    if (newSbpIndex != C.INDEX_UNSET && intendedPlayWhenReady) {
                        speakSegmentAtIndex(newSbpIndex)
                    } else if (newSbpIndex != C.INDEX_UNSET) {
                        // Si no estamos reproduciendo, al menos actualiza la estimación de duración
                        // y resetea el progreso del slider para el nuevo segmento.
                        estimateDurationAndResetProgressForSegment(newSbpIndex)
                    }
                }
                //overallProgressListener.invoke(newSbpIndex.coerceAtLeast(0), mediaItemCount)
            }

            //Método para obtener los comandos por defecto (ejemplo)

// En TtsPlayerForApi26.kt
// Dentro de addListener(object : Player.Listener { ... override fun onPlayWhenReadyChanged(...) { ...

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                Timber.d("SBP Listener: onPlayWhenReadyChanged - playWhenReady: $playWhenReady, Reason: $reason")
                intendedPlayWhenReady = playWhenReady
                if (playWhenReady) {
                    Timber.d("TtsPlayerForApi26.onPlayWhenReadyChanged(true): SBP state is ${this@TtsPlayerOld.playbackState}")
                    // Si SBP está listo o bufferizando, y ahora debemos reproducir, iniciamos el habla.
                    if (this@TtsPlayerOld.playbackState == Player.STATE_READY || this@TtsPlayerOld.playbackState == Player.STATE_BUFFERING) {
                        speakSegmentAtIndex(currentMediaItemIndex)
                    } else if (this@TtsPlayerOld.playbackState == Player.STATE_ENDED) {
                        // Si estábamos en ENDED y se pide reproducir, vamos al inicio.
                        seekToDefaultPosition()
                        // onMediaItemTransition se encargará de llamar a speakSegmentAtIndex si intendedPlayWhenReady es true
                    }
                } else { // playWhenReady es false (pausa)
                    textToSpeechEngine.stop()
                    // Cuando se pausa, el estado de reproducción de SBP sigue siendo Player.STATE_READY.
                    // Su propiedad playWhenReady es ahora false.
                    // Nuestro reportedPlayerState debería reflejar esto.
                    // La llamada a updateReportedPlayerState() en el listener onPlaybackStateChanged
                    // o una llamada explícita aquí asegura la sincronización.
                    // Si el estado ya es READY, no es necesario cambiarlo.
                    // Si por alguna razón nuestro reportedPlayerState.playbackState no fuera Player.STATE_READY,
                    // aquí sería un buen lugar para corregirlo.
                    // Pero generalmente, SBP maneja esto bien.
                    // Lo importante es que el TTS se detenga.
                    // No es necesario un if (reportedPlayerState.playbackState == Player.STATE_PLAYING)
                    // porque no existe tal estado.
                    // Si el estado era READY y playWhenReady era true, ahora es READY y playWhenReady es false.
                    // updateReportedPlayerState() se llamará de todas formas por SBP al cambiar playWhenReady,
                    // y nuestro método updateReportedPlayerState cogerá el this.playbackState y el nuevo intendedPlayWhenReady.
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                Timber.d("SBP Listener: onPlaybackStateChanged - New SBP state: $playbackState")
                updateReportedPlayerState(playbackStateUpdate = playbackState)
                if (playbackState == Player.STATE_ENDED || playbackState == Player.STATE_IDLE) {
                    // Resetear el progreso del slider si la reproducción termina o se detiene.
                    /*segmentProgressListener.invoke(
                        0f,
                        formatMillis(0),
                        formatMillis(currentSegmentEstimatedDurationMs)
                    )*/
                }
                if (playbackState == Player.STATE_IDLE && reportedPlayerState.playerError == null) {
                    textToSpeechEngine.stop()
                }
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                Timber.d("SBP Listener: onTimelineChanged - WindowCount: ${timeline.windowCount}, Reason: $reason")
                /*overallProgressListener.invoke(
                    currentMediaItemIndex.coerceAtLeast(0),
                    mediaItemCount
                )*/
                if (timeline.windowCount > 0 && currentMediaItemIndex != C.INDEX_UNSET) {
                    estimateDurationAndResetProgressForSegment(currentMediaItemIndex)
                } else {
                    //segmentProgressListener.invoke(0f, formatMillis(0), formatMillis(0))
                }
                updateReportedPlayerState()
            }
        })
    }


    // Método personalizado para establecer los ítems y el texto asociado
    fun setTextAndMediaItems(
        newConceptualMediaItems: List<MediaItem>,
        newTextSegments: String,
        resetPosition: Boolean
    ) {
        this.fullTextToSpeak = newTextSegments
        // Vuelve a segmentar el texto aquí para asegurar la consistencia
        // o asume que los mediaItems ya se crearon a partir de una segmentación idéntica.
        // Es más seguro re-segmentar o pasar los segmentos directamente.
        this.localSegmentTexts =
            createSegmentsFromTextInternal(newTextSegments) // Usa una función de segmentación interna


        // Valida que el número de mediaItems coincida con el número de segmentos
        if (newConceptualMediaItems.size != localSegmentTexts.size && localSegmentTexts.isNotEmpty()) {
            Timber.w("Mismatch between mediaItems count (${newConceptualMediaItems.size}) and created text segments count (${localSegmentTexts.size}). This might lead to issues.")
            // Podrías ajustar mediaItems o localSegmentTexts, o lanzar un error.
            // Por simplicidad, aquí podríamos truncar o rellenar, pero es mejor asegurar que coincidan desde el origen.
        }

        Timber.d("processSetMediaItemsInternal: Estado ANTES de super.setMediaItems: SBP PlaybackState=${this.playbackState}, SBP PlayerError=${this.playerError}, internalPlaybackState=${this.internalPlaybackState}, internalPlayerError=${this.internalPlayerError}")
        Timber.tag(TAG)
            .d("processSetMediaItemsInternal: Llamando a super.setMediaItems con ${newConceptualMediaItems.size} items. Reset: $resetPosition")

        // Llama al método de la clase base SimpleBasePlayer
        super.setMediaItems(newConceptualMediaItems, resetPosition)
        Timber.tag(TAG)
            .d("processSetMediaItemsInternal: Después de super.setMediaItems. Nuevo mediaItemCount de SBP: ${this.mediaItemCount}, Timeline isEmpty:${this.currentTimeline.isEmpty}")

        // Después de que SBP actualice el timeline, podrías querer
        // actualizar la estimación de duración para el primer ítem si resetPosition es true.
        if (resetPosition && localSegmentTexts.isNotEmpty()) {
            estimateDurationAndResetProgressForSegment(0) // Asume que el índice se resetea a 0
        }
    }

    // Función de segmentación interna (puede ser la misma que usas externamente)
    private fun createSegmentsFromTextInternal(
        text: String,
        maxLength: Int = 4000
    ): MutableList<String> {
        val segments = mutableListOf<String>()
        var remainingText = text.trim()
        // ... (misma lógica de createSegmentsFromTextByMaxLength que discutimos antes) ...
        while (remainingText.isNotEmpty()) {
            if (remainingText.length <= maxLength) {
                segments.add(remainingText)
                break
            } else {
                var splitPoint = remainingText.lastIndexOf(' ', maxLength)
                if (splitPoint == -1 || splitPoint == 0) {
                    splitPoint = maxLength
                }
                segments.add(remainingText.substring(0, splitPoint).trim())
                remainingText = remainingText.substring(splitPoint).trim()
            }
        }
        return segments.filter { it.isNotBlank() }.toMutableList()
    }

    private fun getDefaultPlayerInitialState(): State { /* ... (igual que antes) ... */
        return State.Builder()
            .setAvailableCommands(getDefaultPlayerCommands())
            //.setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .setPlaybackState(Player.STATE_IDLE)
            /*.setPlaylistMetadata(
                MediaMetadata.Builder()
                    .setTitle("TTS Player")
                    .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                    .build()
            )
            .setCurrentMediaItemIndex(0)*/
            .build()
    }

    private fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean) {
        // val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            state = state.buildUpon()
                .setPlaybackState(playbackState)
                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                .build()
            // invalidateState()
        }
    }

    override fun getState(): State { /* ... (igual que antes) ... */
        return reportedPlayerState
    }

    // (Continuación de la clase TtsPlayer desde la Parte 1)

    // En TtsPlayerForApi26.kt

    private fun updateReportedPlayerState(
        playbackStateUpdate: Int? = null,
        playWhenReadyUpdate: Boolean? = null,
        playerErrorUpdate: PlaybackException? = null
    ) {
        val oldReportedState = reportedPlayerState
        val newStateBuilder = State.Builder()
        val oldInternalState = this.internalPlaybackState
        var significantChange = false
        val oldPlayWhenReady =
            this.intendedPlayWhenReady // O this.playWhenReady si lees directamente de SBP

        // 1. Actualiza tus variables internas PRIMERO
        playbackStateUpdate?.let {
            if (this.internalPlaybackState != it) {
                this.internalPlaybackState = it
                Timber.d("updateReportedPlayerState: internalPlaybackState cambiado de $oldInternalState a $it")
                significantChange = true
            }
        }
        playWhenReadyUpdate?.let {
            // 'intendedPlayWhenReady' se actualiza directamente en handleSetPlayWhenReady.
            // Aquí podrías estar reflejando un cambio que SBP debería conocer.
            // O si 'intendedPlayWhenReady' es tu única fuente de verdad para playWhenReady:
            if (this.intendedPlayWhenReady != it) {
                this.intendedPlayWhenReady =
                    it // Asegúrate de que esto sea consistente con handleSetPlayWhenReady
                Timber.d("updateReportedPlayerState: intendedPlayWhenReady cambiado a $it")
                significantChange = true
            }
        }

        // Actualiza tu variable interna de error
        if (playerErrorUpdate != this.internalPlayerError) { // Compara si es diferente
            this.internalPlayerError = playerErrorUpdate
            Timber.d("updateReportedPlayerState: internalPlayerError actualizado.")
            significantChange = true
            // Si hay un error, el estado de reproducción a menudo debería ser IDLE
            if (playerErrorUpdate != null && this.internalPlaybackState != Player.STATE_IDLE) {
                Timber.d("updateReportedPlayerState: Error presente, forzando internalPlaybackState a IDLE.")
                this.internalPlaybackState = Player.STATE_IDLE
                // No necesitas 'significantChange = true' aquí de nuevo si ya se estableció por el error.
            }
        } else if (playerErrorUpdate == null && this.internalPlayerError != null) {
            // Si el error se está limpiando
            this.internalPlayerError = null
            Timber.d("updateReportedPlayerState: internalPlayerError limpiado.")
            significantChange = true
        }
        // 1. Copia las propiedades que SimpleBasePlayer gestiona y que no modificamos directamente aquí.
        //    Estas se tomarán del 'this' (SimpleBasePlayer) cuando se acceda al estado.
        newStateBuilder
            //.setAvailableCommands(this.availableCommands)
            .setAvailableCommands(getDefaultPlayerCommands())
            .setPlaylistMetadata(this.playlistMetadata)
            .setSeekBackIncrementMs(this.currentSeekBackIncrementMs)
            .setSeekForwardIncrementMs(this.currentSeekForwardIncrementMs)
            .setMaxSeekToPreviousPositionMs(this.currentMaxSeekToPreviousPositionMs)

            .setPlayWhenReady(
                this.intendedPlayWhenReady,
                if (this.intendedPlayWhenReady != oldPlayWhenReady) PLAY_WHEN_READY_CHANGE_REASON_AUDIO_BECOMING_NOISY else this.playWhenReadyChangeReason
            )
            .setPlaybackState(this.internalPlaybackState)
            .setPlayerError(this.internalPlayerError)
        // NO establecemos Timeline ni CurrentMediaItemIndex en este builder.
        // Estas propiedades se leerán directamente de 'this' (SimpleBasePlayer)
        // cuando se llame a getState() o cuando SBP notifique a sus listeners.

        // 2. Aplica las actualizaciones específicas de tu lógica TTS.
        val finalPlaybackState =
            playbackStateUpdate ?: this.playbackState // Usa el estado actual de SBP como base
        newStateBuilder.setPlaybackState(finalPlaybackState)

        val finalPlayWhenReady = playWhenReadyUpdate ?: intendedPlayWhenReady // Usa tu intención
        val reason =
            if (playWhenReadyUpdate != null && playWhenReadyUpdate != oldReportedState.playWhenReady) {
                Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST
            } else if (finalPlayWhenReady != oldReportedState.playWhenReady) {
                Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST
            } else {
                oldReportedState.playWhenReadyChangeReason
            }
        newStateBuilder.setPlayWhenReady(finalPlayWhenReady, reason)

        if (playerErrorUpdate != null) {
            newStateBuilder.setPlayerError(playerErrorUpdate)
            if (playbackStateUpdate == null) {
                newStateBuilder.setPlaybackState(Player.STATE_IDLE)
            }
        } else if (oldReportedState.playerError != null && finalPlaybackState != Player.STATE_IDLE) {
            newStateBuilder.setPlayerError(null)
        } else {
            newStateBuilder.setPlayerError(oldReportedState.playerError)
        }

        // 3. Construye tu 'reportedPlayerState' solo con las propiedades que has modificado
        //    o que quieres que SBP use de este builder.
        //    El Timeline y el currentMediaItemIndex se tomarán del 'this' (SimpleBasePlayer)
        //    automáticamente cuando SBP construya el estado final para sus listeners.
        reportedPlayerState = newStateBuilder.build()

        // 4. Notifica a SimpleBasePlayer.
        //    Cuando SBP notifique a sus listeners, usará el 'reportedPlayerState' que acabas de
        //    construir para las propiedades que estableciste en el builder, y sus propias
        //    propiedades internas (this.currentTimeline, this.currentMediaItemIndex) para el resto.
        invalidateState()

        Timber.tag(TAG).d(
            "updateReportedPlayerState: New reported - PlaybackState=${reportedPlayerState.playbackState}, " +
                    "PlayWhenReady=${reportedPlayerState.playWhenReady}, Error=${reportedPlayerState.playerError != null}. " +
                    "SBP internal state for timeline/index: TimelineWindows=${this.currentTimeline.windowCount}, " +
                    "CurrentItemIndex=${this.currentMediaItemIndex}"
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)

    // En TtsPlayerForApi26.kt

// ... (otras propiedades de la clase como isTtsInitialized, currentLanguage, etc.)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Timber.tag(TAG).i("TTS Engine Initialized successfully.")
            isTtsInitialized = true

            // --- Sección 1: Configurar el Idioma ---
            // Es crucial hacerlo después de una inicialización exitosa.
            // Asumimos que 'currentLanguage' es una propiedad de tu clase, ej: Locale.US o Locale("es", "ES")
            val langResult = textToSpeechEngine.setLanguage(currentLanguage)

            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Timber.tag(TAG).e(
                    "TTS Language (Locale: $currentLanguage) is missing data or not supported. Result: $langResult"
                )
                // Informar un error crítico al reproductor. El TTS no funcionará.
                updateReportedPlayerState(
                    playerErrorUpdate = PlaybackException(
                        "TTS Language (Locale: $currentLanguage) not supported. Result: $langResult",
                        null,
                        PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                    ),
                    playbackStateUpdate = Player.STATE_IDLE // El reproductor no puede operar
                )
                // Limpiar cualquier estado pendiente ya que no se podrá procesar
                pendingTextToSet = null
                pendingPlayOnTtsInit = false
                return // Salir temprano, no se puede continuar sin un idioma válido
            } else {
                Timber.tag(TAG)
                    .i("TTS Language set successfully to: $currentLanguage. Result: $langResult (LANG_AVAILABLE=$langResult)")
                // LANG_AVAILABLE (0), LANG_COUNTRY_AVAILABLE (1), LANG_COUNTRY_VAR_AVAILABLE (2) son todos éxitos.

                //updateReportedPlayerState(playbackStateUpdate = Player.STATE_BUFFERING)

            }
            // --- Fin Sección 1 ---


            textToSpeechEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String) {
                    //updatePlaybackState(STATE_READY, true)

                    mainHandler.post { // Asegurar ejecución en el hilo del Looper de SimpleBasePlayer
                        // Timber.tag(TAG).i("TTS onStart: $utteranceId, currentSbpIndex: $currentMediaItemIndex, SBP State: ${this@TtsPlayer.playbackState}")
                        if (utteranceId == currentUtteranceId && this@TtsPlayerOld.playbackState == Player.STATE_BUFFERING) {
                            // Si estábamos en BUFFERING (porque llamamos a speakSegmentAtIndex), ahora estamos listos.
                            updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
                        } else if (utteranceId == currentUtteranceId && this@TtsPlayerOld.playbackState == Player.STATE_IDLE && intendedPlayWhenReady) {
                            // Caso raro: si estábamos en IDLE pero la intención era reproducir y TTS comenzó, pasar a READY.
                            updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
                        }
                    }
                }

                override fun onDone(utteranceId: String) {
                    mainHandler.post {
                        // Timber.tag(TAG).i("TTS onDone: $utteranceId, currentSbpIndex: $currentMediaItemIndex")
                        if (utteranceId == currentUtteranceId) {

                            //seekToNextMediaItem()
                            // SimpleBasePlayer se encargará de la transición al siguiente MediaItem
                            // si playWhenReady es true y hay un siguiente ítem.
                            // No es necesario llamar a seekToNextMediaItem() aquí explícitamente.
                            // El estado ya debería ser READY. Si no hay más ítems, SBP irá a ENDED.
                        }
                    }
                }

                @Deprecated("deprecated in API level 21")
                override fun onError(utteranceId: String) {
                    mainHandler.post {
                        Timber.tag(TAG)
                            .e("TTS onError (deprecated) for utteranceId: $utteranceId. Forwarding to new onError.")
                        this.onError(
                            utteranceId,
                            TextToSpeech.ERROR
                        ) // Redirigir a la versión más nueva
                    }
                }

                override fun onError(utteranceId: String?, errorCode: Int) {
                    mainHandler.post {
                        Timber.tag(TAG)
                            .e("TTS onError (with code) for utteranceId: $utteranceId, errorCode: $errorCode")

                        updateReportedPlayerState(
                            playerErrorUpdate = PlaybackException(
                                "TTS playback error. Utterance: $utteranceId, Code: $errorCode",
                                null,
                                PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED // O un código más específico
                            ),
                            playbackStateUpdate = Player.STATE_IDLE // Error usualmente significa que no podemos continuar
                        )
                    }
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                    // Solo procesar si el utteranceId coincide con el que estamos reproduciendo
                    if (utteranceId == currentUtteranceId) {
                        val currentIndex = currentMediaItemIndex
                        // Asegurar que el índice es válido y tenemos segmentos de texto
                        if (currentIndex != C.INDEX_UNSET && currentIndex < localSegmentTexts.size) {
                            val segmentText = localSegmentTexts.getOrNull(currentIndex)
                            if (segmentText == null || segmentText.isEmpty()) { // Comprobar si el texto del segmento es nulo o vacío
                                return // No se puede calcular el progreso si no hay texto
                            }
                            val segmentLength = segmentText.length
                            if (segmentLength > 0 && currentSegmentEstimatedDurationMs > 0) {
                                val progressPercent = start.toFloat() / segmentLength.toFloat()
                                val progressTimeMs =
                                    (progressPercent * currentSegmentEstimatedDurationMs).toLong()

                                mainHandler.post {

                                }
                            }
                        }
                    }
                }
            })
            Timber.tag(TAG).d("TTS UtteranceProgressListener configured.")

            pendingTextToSet?.let { text ->
                Timber.tag(TAG).i("TTS Init: Processing pending text: \"${text.take(50)}...\"")
                // Asumimos que tienes un método interno para establecer el contenido
                // y crear los MediaItems y segmentos de texto.
                // Este método NO debería llamar a prepare() ni a play() directamente,
                // solo preparar los datos internos.
                //setContentInternal(text) // Este método poblaría localSegmentTexts y llamaría a super.setMediaItems
                pendingTextToSet = null
            }

            // Ahora que el TTS está inicializado y el contenido (si había pendiente) está procesado:
            // Si se intentó reproducir ('playWhenReady' era true) antes de que el TTS estuviera listo,
            // y ahora tenemos contenido, intentamos iniciar la reproducción.
            if (pendingPlayOnTtsInit && intendedPlayWhenReady) {
                pendingPlayOnTtsInit = false // Reseteamos la bandera

                if (this.mediaItemCount > 0) { // mediaItemCount es de SimpleBasePlayer
                    Timber.tag(TAG)
                        .i("TTS Init: Pending play was true. Attempting to speak segment $currentMediaItemIndex.")
                    // El estado de SBP podría ser IDLE si prepare() no se ha llamado o si se llamó
                    // antes de que el contenido estuviera listo.
                    // O podría ser BUFFERING/READY si prepare() se llamó después de setContentInternal.

                    // Si SimpleBasePlayer está en IDLE, significa que prepare() aún no ha sido efectivo
                    // o no se ha llamado después de que los MediaItems estuvieran disponibles.
                    // En este caso, speakSegmentAtIndex podría ponerlo en BUFFERING.
                    // Si ya está en READY (porque prepare() se llamó después de setContentInternal y tuvo éxito),
                    // speakSegmentAtIndex también es apropiado.
                    speakSegmentAtIndex(currentMediaItemIndex)
                } else {
                    Timber.tag(TAG)
                        .w("TTS Init: Pending play was true, but no media items to play.")
                    // No hay nada que reproducir, así que nos aseguramos de estar en IDLE
                    // si no hay error. Si hubo un error de idioma, ya estamos en IDLE.
                    if (reportedPlayerState.playerError == null) {
                        updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
                    }
                }
            } else if (mediaItemCount > 0 && reportedPlayerState.playbackState == Player.STATE_IDLE && reportedPlayerState.playerError == null) {
                // Si no había una reproducción pendiente, pero hay contenido y estamos en IDLE sin error,
                // esto significa que el contenido se estableció, el TTS está listo,
                // pero `prepare()` o `play()` aún no se han llamado o no han tenido efecto.
                // No hacemos nada aquí; esperamos a que `prepare()` y `setPlayWhenReady(true)` sean llamados
                // desde el exterior (ej. TtsServiceHandler).
                // El reproductor está listo para ser preparado.
                Timber.tag(TAG)
                    .d("TTS Init: TTS ready, content available, player is IDLE. Waiting for prepare/play command.")
            } else if (mediaItemCount == 0 && reportedPlayerState.playbackState == Player.STATE_IDLE && reportedPlayerState.playerError == null) {
                // TTS listo, sin contenido, en IDLE y sin error. Estado normal de espera.
                Timber.tag(TAG)
                    .d("TTS Init: TTS ready, no content, player is IDLE. Waiting for content and prepare/play command.")
            }

            // --- Fin Sección 2 ---

            // Continuará con el procesamiento de contenido pendiente y la reproducción...
// } // Cierre del if (status == TextToSpeech.SUCCESS)


            // Continuará con la configuración del UtteranceProgressListener...
        } else {
            Timber.tag(TAG).e("TTS Engine Initialization FAILED! Status: $status")
            isTtsInitialized = false
            // Informar un error crítico al reproductor.
            updateReportedPlayerState(
                playerErrorUpdate = PlaybackException(
                    "TTS Engine Initialization Failed. Status: $status",
                    null,
                    PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                ),
                playbackStateUpdate = Player.STATE_IDLE
            )
            // Limpiar cualquier estado pendiente
            pendingTextToSet = null
            pendingPlayOnTtsInit = false
        }

        //DELETE
        Timber.tag(TAG).i("onInit called. Status: $status")
        if (status == TextToSpeech.SUCCESS) {
            isTtsInitialized = true
            Timber.tag(TAG).i("TextToSpeech engine initialized successfully.")
            // Configurar idioma, velocidad del motor TTS aquí si es necesario
            // textToSpeechEngine.language = Locale.getDefault()
            // textToSpeechEngine.setSpeechRate(1.0f)

            if (pendingPlayOnTtsInit) {
                Timber.tag(TAG).i("onInit: Handling pending play on TTS initialization.")
                pendingPlayOnTtsInit = false
                val currentSbpState = getState()

                // Solo intentar reproducir si la intención era reproducir y hay contenido.
                if (currentSbpState.playWhenReady && mediaItemCount > 0) {
                    var targetIndex = this.currentMediaItemIndex
                    if (targetIndex == C.INDEX_UNSET) { // Si no hay índice, empezar por el primero.
                        targetIndex = 0
                    }

                    // Validar que el índice esté dentro de los límites.
                    if (targetIndex < 0 || targetIndex >= mediaItemCount) {
                        Timber.tag(TAG)
                            .w("onInit: Invalid targetIndex $targetIndex for $mediaItemCount items. Cannot start pending play.")
                        // Opcional: ir a IDLE si el índice es inválido.
                        // updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
                        return
                    }
                    this.currentMediaItemIndex = targetIndex // Actualizar el índice de la clase

                    // Si no está reproduciendo ni en proceso de bufferización, iniciar.
                    if (!this.isPlaying && currentSbpState.playbackState != Player.STATE_BUFFERING) {
                        Timber.tag(TAG)
                            .d("onInit: Player not playing/buffering. Intended to play. Starting segment $targetIndex.")
                        updateReportedPlayerState(
                            playbackStateUpdate = Player.STATE_BUFFERING,
                            //currentMediaItemIndexUpdate = this.currentMediaItemIndex // Asegurar que el estado refleje el índice
                        )
                        speakSegmentAtIndex(this.currentMediaItemIndex)
                    } else if (this.isPlaying) {
                        Timber.tag(TAG)
                            .d("onInit: Player is already playing. No action for pending play.")
                    } else if (currentSbpState.playbackState == Player.STATE_BUFFERING) {
                        Timber.tag(TAG)
                            .d("onInit: Player already buffering. Assuming playback will proceed.")
                        // Opcionalmente, podrías llamar a speakSegmentAtIndex aquí si quieres asegurarte,
                        // pero ten cuidado con múltiples llamadas si handleSetPlayWhenReady también lo hace.
                        // speakSegmentAtIndex(this.currentMediaItemIndex, true)
                    }
                } else {
                    Timber.tag(TAG)
                        .i("onInit: Pending play was true, but conditions (playWhenReady or mediaItemCount) not met now.")
                }
            } else {
                // TTS listo, no había reproducción pendiente. Verificar si hay contenido y estamos IDLE.
                // Esto es para el caso donde setContent se llamó, TTS se inicializó, pero play() aún no.
                if (mediaItemCount > 0 && playbackState == Player.STATE_IDLE && playerError == null) {
                    Timber.tag(TAG)
                        .d("TTS Init: TTS ready, content available, player is IDLE. Waiting for prepare/play command.")
                } else if (mediaItemCount == 0 && playbackState == Player.STATE_IDLE && playerError == null) {
                    Timber.tag(TAG)
                        .d("TTS Init: TTS ready, no content, player is IDLE. Waiting for content and prepare/play command.")
                }
            }
        } else { // TextToSpeech.SUCCESS != status
            isTtsInitialized = false
            Timber.tag(TAG).e("TTS Engine Initialization FAILED! Status: $status")
            updateReportedPlayerState(
                playerErrorUpdate = PlaybackException(
                    "TTS Engine Initialization Failed. Status: $status",
                    null,
                    PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED // O un código de error TTS más específico
                ),
                playbackStateUpdate = Player.STATE_IDLE
            )
            pendingTextToSet = null // Limpiar cualquier texto pendiente
            pendingPlayOnTtsInit = false // Limpiar la bandera de reproducción pendiente
        }
    }

    // En TtsPlayerForApi26.kt

// ... (otras propiedades y métodos) ...

    @SuppressLint("MissingSuperCall") // SimpleBasePlayer no tiene un super.handlePrepare() que necesitemos llamar
    override fun handlePrepare(): ListenableFuture<*> {
        Timber.tag(TAG)
            .i("handlePrepare: Entered. Current SBP state: ${this.playbackState}, isTtsInitialized: $isTtsInitialized, mediaItemCount: $mediaItemCount")

        // 1. Verificar si el TTS está inicializado.
        //    Si no lo está, la preparación no puede completarse ahora.
        //    Podríamos devolver un Future que se complete más tarde, o simplemente
        //    confiar en que onInit() actualizará el estado cuando TTS esté listo.
        //    Por simplicidad, si TTS no está listo, no pasaremos a BUFFERING/READY desde aquí.
        //    onInit() se encargará de actualizar el estado si la preparación estaba "pendiente".
        if (!isTtsInitialized) {
            Timber.tag(TAG)
                .w("handlePrepare: TTS not initialized yet. Preparation will fully complete in onInit(). Current SBP state: ${this.playbackState}")
            // No cambies el estado a BUFFERING/READY aquí.
            // Si el estado es IDLE, se quedará así hasta que onInit lo mueva.
            // Si ya hay un error, se mantendrá.
            // Devolvemos un Future inmediato porque la acción de "registrar que se intentó preparar" es inmediata.
            return Futures.immediateVoidFuture()
        }

        // 2. Verificar si hay contenido (MediaItems).
        //    Si no hay MediaItems, no hay nada para lo que prepararse.
        if (mediaItemCount == 0) {
            Timber.tag(TAG)
                .w("handlePrepare: No media items in the playlist. Cannot prepare for playback.")
            // Mantener en IDLE o ir a ENDED si una lista vacía significa el final.
            // Si ya hay un error, no lo sobrescribas a menos que sea apropiado.
            if (reportedPlayerState.playerError == null) {
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE) // O Player.STATE_ENDED
            }
            return Futures.immediateVoidFuture()
        }

        // 3. Si TTS está listo y hay contenido, podemos proceder con la preparación.
        Timber.tag(TAG)
            .d("handlePrepare: TTS is initialized and media items are present. Transitioning to BUFFERING.")
        updateReportedPlayerState(playbackStateUpdate = Player.STATE_BUFFERING)

        // 4. "Cargar" o preparar el primer segmento.
        //    Para TTS, esto podría implicar estimar su duración si aún no se ha hecho.
        //    Asegúrate de que currentMediaItemIndex sea válido.
        val currentIndex = currentMediaItemIndex
        if (currentIndex != C.INDEX_UNSET && currentIndex < localSegmentTexts.size) {
            estimateDurationAndResetProgressForSegment(currentIndex)
        } else if (currentIndex == C.INDEX_UNSET && mediaItemCount > 0) {
            // Si el índice es UNSET pero hay items, probablemente deberíamos preparar el primero (índice 0)
            // SimpleBasePlayer podría ya haber establecido currentMediaItemIndex a 0 si la lista no está vacía.
            // Si no, podrías necesitar llamar a seekToDefaultPosition() o seekTo(0,0) internamente
            // o simplemente asumir que el índice 0 es el objetivo.
            // Por ahora, confiamos en que SBP maneja el índice inicial.
            // Si currentMediaItemIndex sigue siendo UNSET aquí, es un problema.
            Timber.tag(TAG)
                .w("handlePrepare: currentMediaItemIndex is UNSET but mediaItemCount > 0. This might be an issue.")
        }


        // 5. Transición a READY.
        //    Para TTS, la "bufferización" es usualmente muy rápida o inexistente una vez que el texto está disponible.
        //    Podemos pasar a READY casi inmediatamente.
        //    Es buena práctica hacerlo en un post al Handler para asegurar que el cambio de BUFFERING
        //    se propague antes de cambiar a READY, y para simular el comportamiento asíncrono
        //    de la mayoría de los reproductores.
        mainHandler.post {
            // Solo transicionar a READY si aún estamos en BUFFERING y no ha ocurrido un error mientras tanto.
            if (this.playbackState == Player.STATE_BUFFERING && reportedPlayerState.playerError == null) {
                Timber.tag(TAG)
                    .i("handlePrepare (mainHandler.post): Transitioning from BUFFERING to READY.")
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
            } else {
                Timber.tag(TAG)
                    .w("handlePrepare (mainHandler.post): Not transitioning to READY. Current state: ${this.playbackState}, Error: ${reportedPlayerState.playerError}")
            }
        }

        // La operación de preparación en sí (configurar el estado inicial) se considera completada.
        return Futures.immediateVoidFuture()
    }

    private fun estimateDurationAndResetProgressForSegment(segmentIndex: Int) {
        if (segmentIndex >= 0 && segmentIndex < localSegmentTexts.size) {
            val segmentText = localSegmentTexts[segmentIndex]
            currentSegmentEstimatedDurationMs =
                ((segmentText.length.toFloat() / AVG_CHARS_PER_SECOND_FOR_DURATION_ESTIMATE) * 1000).toLong()
        } else {
            currentSegmentEstimatedDurationMs = 0L
        }
        // Resetear el progreso del slider para el nuevo segmento

    }


    private fun speakSegmentAtIndex(index: Int) {
        if (!isTtsInitialized) {
            Timber.w("TTS not initialized, cannot speak segment $index.")
            updateReportedPlayerState(
                playerErrorUpdate = PlaybackException(
                    "TTS not initialized",
                    null,
                    PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                ),
                playbackStateUpdate = Player.STATE_IDLE
            )
            return
        }
        if (index < 0 || index >= localSegmentTexts.size) {
            Timber.w("Invalid segment index $index for speaking. Total segments: ${localSegmentTexts.size}")
            updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
            return
        }

        // Estimar duración y resetear progreso ANTES de hablar
        estimateDurationAndResetProgressForSegment(index)

        val textToSpeak = localSegmentTexts[index]
        currentUtteranceId = "tts_segment_${UUID.randomUUID()}"

        Timber.i("Requesting TTS speak for index $index, utteranceId: $currentUtteranceId, text: \"$textToSpeak\"")

        val params = Bundle()
        params.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)

        val result = textToSpeechEngine.speak(
            textToSpeak,
            TextToSpeech.QUEUE_FLUSH,
            params,
            currentUtteranceId
        )

        if (result == TextToSpeech.ERROR) {
            Timber.e("TTS speak() returned ERROR for segment $index.")
            updateReportedPlayerState(
                playerErrorUpdate = PlaybackException(
                    "TTS speak() failed",
                    null,
                    PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED
                ),
                playbackStateUpdate = Player.STATE_IDLE
            )
        } else {
            updateReportedPlayerState(playbackStateUpdate = Player.STATE_BUFFERING)
        }
    }

    // --- Métodos para manejar comandos de SimpleBasePlayer ---

    fun handleSetPlayWhenReadyOld(playWhenReady: Boolean): ListenableFuture<*> {
        Timber.d("handleSetPlayWhenReady: $playWhenReady")
        intendedPlayWhenReady = playWhenReady
        updateReportedPlayerState(playWhenReadyUpdate = playWhenReady) // SBP se encarga del resto
        return Futures.immediateVoidFuture()
    }

    fun handleSetPlayWhenReadyy(playWhenReady: Boolean): ListenableFuture<*> {
        this.intendedPlayWhenReady = playWhenReady
        updateReportedPlayerState(playWhenReadyUpdate = playWhenReady)
        speakSegmentAtIndex(currentMediaItemIndex)
        return Futures.immediateVoidFuture()
    }

    // En TtsPlayerForApi26.kt
    // En TtsPlayer.kt

// ... otras importaciones ...

    @SuppressLint("RestrictedApi") // Si usas Futures.immediateFuture {} directamente
    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<() -> Unit> {
        this.intendedPlayWhenReady = playWhenReady // Guarda la intención del usuario/aplicación

        Timber.tag(TAG).d(
            "handleSetPlayWhenReady (Future): playWhenReady=$playWhenReady. " +
                    "Current SBP State: ${playbackState}, isTtsInitialized: $isTtsInitialized, " +
                    "SBP mediaItemCount: $mediaItemCount, currentMediaItemIndex: $currentMediaItemIndex"
        )

        // La lógica de iniciar/detener TTS y actualizar el estado de SBP
        // se ejecuta en el Handler principal para asegurar el orden y el hilo correcto.
        mainHandler.post {
            Timber.tag(TAG)
                .d("handleSetPlayWhenReady (Future - mainHandler.post): Executing logic for playWhenReady=$playWhenReady")

            // Actualizar el estado de SBP con la nueva intención de playWhenReady.
            // SimpleBasePlayer se encarga de notificar este cambio a sus listeners.
            // Hacemos esto ANTES de la lógica de play/pause para que el estado base sea correcto.
            updateReportedPlayerState(playWhenReadyUpdate = this.intendedPlayWhenReady)


            if (this.intendedPlayWhenReady) { // Usar la variable de clase que acabamos de establecer
                // --- Condición 1: TTS no inicializado ---
                if (!isTtsInitialized) {
                    Timber.tag(TAG)
                        .w("handleSetPlayWhenReady (Future): TTS not initialized. Setting pendingPlayOnTtsInit=true.")
                    pendingPlayOnTtsInit = true
                    // No se cambia el playbackState aquí; onInit lo manejará.
                    return@post
                }

                // --- Condición 2: No hay MediaItems ---
                if (mediaItemCount == 0) { // mediaItemCount es de SimpleBasePlayer
                    Timber.tag(TAG).w("handleSetPlayWhenReady (Future): No media items to play.")
                    updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
                    return@post
                }

                // --- Determinar y validar el índice del segmento a reproducir ---
                var targetIndex = this.currentMediaItemIndex
                if (targetIndex == C.INDEX_UNSET && mediaItemCount > 0) {
                    targetIndex = 0
                }

                if (targetIndex < 0 || targetIndex >= mediaItemCount) {
                    Timber.tag(TAG)
                        .e("handleSetPlayWhenReady (Future): Invalid targetIndex $targetIndex for $mediaItemCount items. Cannot play.")
                    updateReportedPlayerState(
                        playbackStateUpdate = Player.STATE_IDLE,
                        playerErrorUpdate = PlaybackException(
                            "Invalid media item index for playback",
                            null,
                            PlaybackException.ERROR_CODE_BAD_VALUE
                        )
                    )
                    return@post
                }
                this.currentMediaItemIndex = targetIndex // Actualizar el índice de la clase

                // --- Lógica principal basada en el estado actual del reproductor ---
                // Obtener el estado MÁS RECIENTE después de haber actualizado playWhenReady
                val currentPlaybackState = playbackState

                when (currentPlaybackState) {
                    Player.STATE_IDLE, Player.STATE_ENDED -> {
                        Timber.tag(TAG)
                            .i("handleSetPlayWhenReady (Future): Play requested from IDLE/ENDED for index $currentMediaItemIndex.")
                        updateReportedPlayerState(
                            playbackStateUpdate = Player.STATE_BUFFERING,
                            //currentMediaItemIndexUpdate = this.currentMediaItemIndex
                        )
                        speakSegmentAtIndex(this.currentMediaItemIndex)
                    }

                    Player.STATE_READY -> {
                        Timber.tag(TAG)
                            .i("handleSetPlayWhenReady (Future): Play requested from READY for index $currentMediaItemIndex.")
                        // Si textToSpeechEngine no está disponible, isTtsInitialized debería ser false y no llegaríamos aquí.
                        if (!textToSpeechEngine.isSpeaking) {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (Future): TTS not speaking, requesting new synthesis from READY.")
                            updateReportedPlayerState(
                                playbackStateUpdate = Player.STATE_BUFFERING,
                            )
                            speakSegmentAtIndex(this.currentMediaItemIndex)
                        } else {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (Future): TTS is already speaking. No new action from READY.")
                            // Si ya está hablando y en READY, y playWhenReady es true, está correcto.
                            // No es necesario cambiar el estado de reproducción si ya es READY.
                        }
                    }

                    Player.STATE_BUFFERING -> {
                        Timber.tag(TAG)
                            .d("handleSetPlayWhenReady (Future): Play requested, but already in BUFFERING state.")
                        // Si ya está bufferizando, es posible que la llamada a speakSegmentAtIndex ya esté en curso
                        // o pendiente desde una acción anterior. Podrías re-llamar a speakSegmentAtIndex
                        // si temes que la llamada anterior se perdió, pero con cuidado de no causar múltiples síntesis.
                        // Por ahora, asumimos que el flujo de bufferización existente es suficiente.
                        // Si `speakSegmentAtIndex` no se llamó, este es un lugar para considerarlo.
                        // if (!textToSpeechEngine.isSpeaking) { // Podría ser una condición adicional
                        //    speakSegmentAtIndex(this.currentMediaItemIndex, forceNewSynthesis = true)
                        // }
                    }
                }
            } else { // playWhenReady es false (se solicitó PAUSA)
                Timber.tag(TAG)
                    .i("handleSetPlayWhenReady (Future): Pause requested (playWhenReady=false).")
                if (isTtsInitialized && textToSpeechEngine.isSpeaking) {
                    Timber.tag(TAG)
                        .d("handleSetPlayWhenReady (Future): TTS is speaking, stopping TTS engine.")
                    textToSpeechEngine.stop() // Esto debería ser asíncrono.
                    // El estado se actualizará a READY en onStop/onDone del UtteranceProgressListener.
                    // No actualices a READY aquí directamente, ya que stop() es asíncrono.
                    // Si el stop fuera síncrono y supieras que se detuvo, podrías hacerlo.
                } else {
                    Timber.tag(TAG)
                        .d("handleSetPlayWhenReady (Future): Pause requested, but TTS not initialized or not speaking.")
                    // Si no está hablando, pero el estado era READY o BUFFERING, y ahora playWhenReady es false,
                    // el estado debería ser READY (pausado).
                    val currentSbpPlaybackState = playbackState
                    if (currentSbpPlaybackState == Player.STATE_BUFFERING || currentSbpPlaybackState == Player.STATE_READY) {
                        updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
                    }
                    // Si estaba IDLE o ENDED, se queda así. El updateReportedPlayerState(playWhenReadyUpdate=false)
                    // al inicio del post ya manejó la intención.
                }
                pendingPlayOnTtsInit =
                    false // Si se pausa, cancelar cualquier reproducción pendiente en onInit
            }
        } // Fin de mainHandler.post

        // SimpleBasePlayer espera un Future. La función dentro del Future se ejecuta
        // después de que SBP procesa el cambio de playWhenReady.
        // Para la mayoría de los casos, una función vacía es suficiente si toda tu lógica
        // ya se ha despachado o ejecutado.
        Timber.tag(TAG).d("handleSetPlayWhenReady (Future): Returning immediateFuture.")
        return Futures.immediateFuture {
            // Este bloque se ejecuta después de que SimpleBasePlayer ha actualizado su
            // propio estado interno de playWhenReady y notificado a los listeners sobre ese cambio.
            // Puedes poner aquí lógica que dependa de que SBP haya procesado el cambio.
            // Por ejemplo, un log final o una acción de limpieza muy específica.
            Timber.tag(TAG)
                .d("handleSetPlayWhenReady (Future - runnable executed): SBP has processed playWhenReady=$intendedPlayWhenReady. Current SBP state: ${playbackState}, isPlaying: ${isPlaying}")
        }
    }

// No olvides tu método updateReportedPlayerState y la

    @SuppressLint("RestrictedApi")
    fun handleSetPlayWhenReadyA(playWhenReady: Boolean): ListenableFuture<() -> Unit> {
        Timber.d("TtsPlayerForApi26: handleSetPlayWhenReady - playWhenReady: $playWhenReady")
        this.intendedPlayWhenReady = playWhenReady

        mainHandler.post { // Toda la lógica de efecto secundario en el Handler
            Timber.d("mainHandler.post: Ejecutando lógica para playWhenReady = $playWhenReady")
            if (playWhenReady) {
                //speakSegmentAtIndex(currentMediaItemIndex)

                Timber.d("mainHandler.post: playWhenReady es TRUE.")
                // Lógica para iniciar la reproducción (speakSegmentAtIndex, etc.)
                // ...
                if (this.internalPlaybackState == Player.STATE_READY || this.internalPlaybackState == Player.STATE_BUFFERING) {
                    Timber.d("mainHandler.postA: Ejecutando lógica para playWhenReady = $playWhenReady")

                    if (currentMediaItemIndex != C.INDEX_UNSET /*...*/) {
                        Timber.d("mainHandler.postB: Ejecutando lógica para playWhenReady = $playWhenReady")

                        speakSegmentAtIndex(currentMediaItemIndex)
                    } else {
                        Timber.d("mainHandler.postC: Ejecutando lógica para playWhenReady = $playWhenReady")

                        /*...*/
                    }
                } else if (this.internalPlaybackState == Player.STATE_IDLE) {
                    Timber.d("mainHandler.postD: Ejecutando lógica para playWhenReady = $playWhenReady")
                    if (mediaItemCount > 0 || localSegmentTexts.isNotEmpty()) { // Asegúrate de que esta condición sea la correcta
                        Timber.d("currentTimeline.windowCount Total: ${currentTimeline.periodCount} Window: ${currentTimeline.windowCount}")
                        Timber.d("handleSetPlayWhenReady (PLAY): Estado IDLE y hay items. mediaItemCount=$mediaItemCount, localSegmentTexts.size=${localSegmentTexts.size}")
                    }
                    // ...
                } else if (this.internalPlaybackState == Player.STATE_ENDED) {
                    Timber.d("mainHandler.postE: Ejecutando lógica para playWhenReady = $playWhenReady")

                    // ...
                }
            } else {
                Timber.d("mainHandler.postF: playWhenReady es FALSE.")
                // Lógica para detener la reproducción (textToSpeechEngine.stop())
                textToSpeechEngine.stop()
                // ...
            }
        }

        // SBP actualiza su 'this.playWhenReady' y notifica el cambio de estado.
        // Tu 'updateReportedPlayerState' podría ser necesario si necesitas forzar
        // una reevaluación de 'getState()' o si tu 'internalPlaybackState' cambia
        // como resultado directo de la pausa (ej. de BUFFERING a READY).
        //updateReportedPlayerState(playWhenReadyUpdate = playWhenReady)
        updateReportedPlayerState(
            playbackStateUpdate = this.internalPlaybackState, // El nuevo estado después de play/pause
            playWhenReadyUpdate = this.intendedPlayWhenReady  // La nueva intención de playWhenReady
        )
        Timber.d("handleSetPlayWhenReady: Retornando Future.")
        //speakSegmentAtIndex(currentMediaItemIndex)

        return Futures.immediateFuture {}
    }

    fun handleSetPlayWhenReadyyy(playWhenReady: Boolean): ListenableFuture<() -> Unit> {
        Timber.d("TtsPlayerForApi26: handleSetPlayWhenReady - playWhenReady: $playWhenReady. Current SBP State: $playbackState")
        this.intendedPlayWhenReady = playWhenReady

        mainHandler.post {
            if (playWhenReady) {
                if (playbackState == Player.STATE_READY || playbackState == Player.STATE_BUFFERING) {
                    if (currentMediaItemIndex != C.INDEX_UNSET) {
                        speakSegmentAtIndex(currentMediaItemIndex)
                    } else if (mediaItemCount > 0) {
                        Timber.d(TAG, "a")

                        //seekToDefaultPositionInternal()
                    }
                } else if (playbackState == Player.STATE_ENDED) {
                    Timber.d(TAG, "a")
                    //seekToDefaultPositionInternal()
                }
            } else {
                textToSpeechEngine.stop()
            }
        }
        return Futures.immediateFuture {}
    }


    fun handleSeekToOld(mediaItemIndex: Int, positionMs: Long): ListenableFuture<*> {
        Timber.d("handleSeekTo: mediaItemIndex=$mediaItemIndex, positionMs=$positionMs (pos no usada por TTS)")
        textToSpeechEngine.stop() // Detener habla actual antes de buscar
        // SBP actualizará el índice. onMediaItemTransition y onPlayWhenReadyChanged se encargarán
        // de iniciar el habla si es necesario y de resetear el progreso del slider.
        // No necesitamos llamar a estimateDurationAndResetProgressForSegment aquí directamente,
        // onMediaItemTransition lo hará si el índice cambia y no estamos reproduciendo,
        // o speakSegmentAtIndex lo hará si empezamos a reproducir.
        return Futures.immediateVoidFuture()
    }

    // En TtsPlayerForApi26.kt

    fun handleSeekTo(mediaItemIndex: Int, positionMs: Long): ListenableFuture<*> {
        Timber.tag(TAG).d(
            "handleSeekTo: Target mediaItemIndex=$mediaItemIndex, positionMs=$positionMs " +
                    "(positionMs es ignorada para la búsqueda de segmentos TTS, se va al inicio del segmento)"
        )

        // 1. Detener cualquier habla actual del TTS.
        //    Es fundamental porque estamos cambiando de contexto de lectura.
        textToSpeechEngine.stop()

        // 2. SimpleBasePlayer se encargará de:
        //    a) Actualizar su 'currentMediaItemIndex' interno al 'mediaItemIndex' solicitado.
        //    b) Invocar el listener onMediaItemTransition() si el índice realmente cambió,
        //       o onPlaybackStateChanged() y onPositionDiscontinuity() si el índice no cambió
        //       pero la posición sí (aunque para TTS esto último es menos común si solo buscamos
        //       entre segmentos).
        //
        //    La lógica para iniciar el habla del nuevo segmento (si playWhenReady es true)
        //    o para actualizar la UI del progreso para el nuevo segmento (si playWhenReady es false)
        //    residirá en onMediaItemTransition() y/o en onPlayWhenReadyChanged().

        // 3. No es necesario llamar a estimateDurationAndResetProgressForSegment aquí directamente.
        //    - Si el índice cambia, onMediaItemTransition() (que tú implementas) debería llamarlo
        //      o llamar a speakSegmentAtIndex() (que a su vez llama a estimate...).
        //    - Si el índice no cambia (buscar dentro del mismo segmento, lo cual no aplica mucho para TTS),
        //      la lógica de onRangeStart seguiría funcionando si el TTS se reiniciara en ese segmento.
        //      Pero como hacemos stop(), el reinicio vendrá de onPlayWhenReadyChanged si aplica.

        // 4. Devuelve un Future inmediato. La acción de detener el TTS y permitir que SBP
        //    actualice su índice es síncrona desde la perspectiva de este manejador.
        return Futures.immediateVoidFuture()
    }

    override fun handleStop(): ListenableFuture<*> {
        Timber.d("handleStop")
        textToSpeechEngine.stop()
        intendedPlayWhenReady = false
        // SBP irá a IDLE y reseteará la posición.
        updateReportedPlayerState(
            playbackStateUpdate = Player.STATE_IDLE,
            playWhenReadyUpdate = false
        )
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        Timber.d("handleRelease: Releasing TtsPlayer resources.")
        textToSpeechEngine.stop()
        textToSpeechEngine.shutdown()
        return Futures.immediateVoidFuture()
    }

    // --- Métodos para establecer el contenido de texto ---

    fun setContent(text: String) {
        Timber.d("setContent called with text: \"$text\"")
        if (ttsInitialized) {
            setContentInternal(text)
        } else {
            Timber.w("TTS not initialized yet. Storing text as pending.")
            pendingTextToSet = text
            if (mediaItemCount == 0) {
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
            }
        }
    }

    private fun setContentInternal(text: String) {
        Timber.d("setContentInternal processing text.")
        textToSpeechEngine.stop()

        val segmentsFromText = text.split(Regex("(?<=[.!?])\\s+|${Constants.SEPARADOR}"))
            .map { it.trim() }
            .filter { it.isNotBlank() }

        localSegmentTexts = segmentsFromText.toMutableList()
        Timber.i("Text processed into ${localSegmentTexts.size} segments.")

        val mediaItems = localSegmentTexts.mapIndexed { index, segmentText ->
            MediaItem.Builder()
                .setMediaId("tts_segment_$index")
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Segment ${index + 1}")
                        .build()
                )
                .build()
        }

        //setMediaItems(mediaItems, true) // Resetea a index 0, estado IDLE

        if (localSegmentTexts.isNotEmpty()) {
            // SBP está en IDLE, índice 0.
            // estimateDurationAndResetProgressForSegment se llamará desde onTimelineChanged
            // o desde el primer speakSegmentAtIndex.
            // El estado se actualizará a READY cuando sea apropiado.
            updateReportedPlayerState(
                playbackStateUpdate = Player.STATE_READY, // Listo para el primer segmento
                playWhenReadyUpdate = intendedPlayWhenReady
            )
            // Asegurar que el progreso del primer segmento se muestre correctamente si no se reproduce de inmediato
            if (!intendedPlayWhenReady) {
                estimateDurationAndResetProgressForSegment(0)
            }
        } else {
            updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
            // Sin segmentos, progreso 0
        }
    }

    private fun createSegmentsFromTextInternalNew(
        text: String,
        maxLength: Int = 4000
    ): List<String> {
        val segments = mutableListOf<String>()
        if (text.isBlank()) return segments
        var remainingText = text.trim()
        while (remainingText.isNotEmpty()) {
            if (remainingText.length <= maxLength) {
                segments.add(remainingText)
                break
            }
            // Intenta dividir por frases o pausas naturales primero
            var splitPoint = -1
            val sentenceEnders = charArrayOf('.', '!', '?')
            val pauseChars = charArrayOf(',', ';', ':')

            // Buscar fin de frase dentro del límite
            for (i in minOf(maxLength - 1, remainingText.length - 1) downTo 0) {
                if (remainingText[i] in sentenceEnders) {
                    splitPoint = i + 1
                    break
                }
            }
            // Si no hay fin de frase, buscar pausas
            if (splitPoint == -1) {
                for (i in minOf(maxLength - 1, remainingText.length - 1) downTo 0) {
                    if (remainingText[i] in pauseChars) {
                        splitPoint = i + 1
                        break
                    }
                }
            }
            // Si no hay pausas, buscar último espacio
            if (splitPoint == -1) {
                splitPoint = remainingText.lastIndexOf(' ', maxLength)
                if (splitPoint != -1) splitPoint += 1 // Incluir el espacio para que el trim lo quite luego
            }

            // Si no se encontró ningún punto de división natural o espacio, cortar en maxLength
            if (splitPoint == -1 || splitPoint == 0) {
                splitPoint = maxLength
            }
            segments.add(remainingText.substring(0, splitPoint).trim())
            remainingText = remainingText.substring(splitPoint).trim()
        }
        return segments.filter { it.isNotBlank() }
    }

    // Dentro de TtsPlayerForApi26.kt

    fun setTextAndMediaItems(
        newFullText: String,
        resetPosition: Boolean // Indica si se debe resetear la posición de reproducción al inicio
    ) {
        Timber.d("setTextAndMediaItems: Texto recibido (primeros 50 chars): \"${newFullText.take(50)}...\". Reset: $resetPosition")

        // 1. Segmenta el texto completo en partes manejables para el TTS.
        val segments = createSegmentsFromTextInternal(newFullText, MAX_TTS_SEGMENT_LENGTH_INTERNAL)
        Timber.d("Texto segmentado en ${segments.size} partes.")

        mainHandler.post { // Asegura que las actualizaciones de estado y SBP se hagan en el hilo principal.
            // 2. Actualiza tu lista interna de segmentos de texto.
            localSegmentTexts.clear()
            localSegmentTexts.addAll(segments)
            Timber.d("localSegmentTexts actualizado con ${localSegmentTexts.size} segmentos.")

            // 3. Crea una lista de `MediaItem` conceptuales para `SimpleBasePlayer`.
            //    SBP usa esto para construir su `Timeline` interna y actualizar `mediaItemCount`.
            val mediaItemsForPlayer = localSegmentTexts.mapIndexed { index, segmentText ->
                MediaItem.Builder()
                    .setMediaId("tts_segment_$index") // ID único para cada segmento.
                    .setUri("https://mediaurl".toUri())

                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle("Segment ${index + 1}") // Título simple para el segmento.
                            // Podrías añadir más metadatos si los necesitas (ej. el propio texto del segmento).
                            .setSubtitle(segmentText.take(100))


                            .build()
                    )
                    .build()
            }
            Timber.d("Creados ${mediaItemsForPlayer.size} MediaItems para SimpleBasePlayer.")

            // 4. Llama al método `setMediaItems` de `SimpleBasePlayer` (o `super.setMediaItems`).
            //    Esto es CRUCIAL para que SBP actualice su `Timeline` interna y, por lo tanto,
            //    su propiedad `this.mediaItemCount`.
            //    El segundo parámetro 'resetPosition' aquí se refiere al reseteo interno de SBP.
            super.setMediaItems(
                mediaItemsForPlayer,
                resetPosition
            ) // ESTA ES LA LLAMADA IMPORTANTE A SBP

            Timber.tag(TAG)
                .d("processSetMediaItemsInternal: Después de super.setMediaItems. Nuevo mediaItemCount de SBP: ${this.mediaItemCount}, Timeline isEmpty:${this.currentTimeline.isEmpty}")

            invalidateState()
            Timber.tag(TAG)
                .d("processSetMediaItemsInternal: Después de super.setMediaItems. Nuevo mediaItemCount de SBP: ${this.mediaItemCount}, Timeline isEmpty:${this.currentTimeline.isEmpty}")

            // En este punto, SBP debería haber actualizado this.mediaItemCount.
            // Puedes verificarlo con un log si es necesario:
            // Timber.d("SBP mediaItemCount después de SBP.setMediaItems: ${this.mediaItemCount}")

            // 5. Maneja el reseteo de posición para TU lógica interna si se solicitó.
            if (resetPosition) {
                currentMediaItemIndex = if (localSegmentTexts.isEmpty()) C.INDEX_UNSET else 0
                currentPositionMs = 0L
                /*segmentProgressListener.invoke(
                    0f,
                    formatMillis(0),
                    formatMillis(0)
                ) */// Resetea progreso del segmento.
                if (localSegmentTexts.isNotEmpty()) {
                    estimateDurationAndResetProgressForSegment(currentMediaItemIndex) // Estima duración del nuevo primer segmento.
                }
                Timber.d("Posición de reproducción interna reseteada. currentMediaItemIndex: $currentMediaItemIndex mediaItems $mediaItemCount")
            }

            // 6. Actualiza el estado reportado del reproductor.
            val newState = if (localSegmentTexts.isEmpty() || resetPosition) {
                Player.STATE_IDLE // Si no hay segmentos o se reseteó, el estado es IDLE.
            } else {
                // Si no se reseteó y había items, y el reproductor ya estaba en un estado activo,
                // podría permanecer así. Si estaba IDLE, sigue IDLE hasta que se inicie la reproducción.
                if (this.internalPlaybackState == Player.STATE_IDLE) Player.STATE_IDLE else Player.STATE_READY
            }


            // Notifica el progreso general (número de segmento actual y total).
            /*overallProgressListener.invoke(
                currentMediaItemIndex.coerceAtLeast(0),
                localSegmentTexts.size
            )*/

            updateReportedPlayerState(
                playbackStateUpdate = newState
                // No es necesario currentMediaItemIndexUpdate aquí si resetPosition ya lo manejó
                // y SBP se entera del índice a través de su propia lógica de seek/reset si es necesario.
            )
            Timber.d("setTextAndMediaItems finalizado. Estado interno: ${this.internalPlaybackState}, SBP mediaItemCount: ${this.mediaItemCount}")
        }
    }

// (Asegúrate de que createSegmentsFromTextInternal y otras funciones de ayuda estén definidas en tu clase)

    fun releasePlayer() {
        Timber.d("TtsPlayer releasePlayer() called.")
        /*if (!isReleased) {
            release()
        }*/
    }


    private fun updatePlayerTimeline() {
        if (textSegments.isEmpty()) {
            Timber.tag(TAG)
                .d("updatePlayerTimeline: No hay segmentos, estableciendo Timeline.EMPTY.")
            this.currentTimeline = Timeline.EMPTY
        } else {
            Timber.tag(TAG)
                .d("updatePlayerTimeline: Creando SegmentedTtsTimeline con ${conceptualMediaItems.size} items.")

            // Aquí creas la lista de duraciones. Inicialmente podrían ser todas C.TIME_UNSET
            // o podrías tener una estimación inicial si lo deseas.
            // A medida que el TTS habla y obtienes duraciones reales (o mejores estimaciones),
            // podrías necesitar actualizar esta Timeline y llamar a invalidateTimeline() de nuevo.
            val currentSegmentDurations = conceptualMediaItems.map {
                // Aquí deberías tener una forma de obtener la duración estimada/real para cada mediaItem/segmento.
                // Por ahora, si no tienes las duraciones al crear la timeline, usa C.TIME_UNSET.
                // Tu método `estimateDurationAndResetProgressForSegment` calcula `currentSegmentEstimatedDurationMs`
                // para el *segmento actual*. Necesitarías una forma de almacenar/recuperar estas
                // estimaciones para *todos* los segmentos si quieres que la Timeline las refleje.
                // Una solución simple es que TtsPlayerForApi26 mantenga una lista de duraciones estimadas.
                C.TIME_UNSET // Placeholder: Reemplaza con la duración real o estimada del segmento
            }

            this.currentTimeline =
                SegmentedTtsTimeline(this.conceptualMediaItems, currentSegmentDurations)
        }
        invalidateState() // Notifica a SimpleBasePlayer
        Timber.tag(TAG).d("updatePlayerTimeline: invalidateTimeline() llamado.")


        // Crea una Timeline personalizada para tus segmentos de TTS.
        // Cada segmento es un "Period" en la Timeline.
        // Inicialmente, las duraciones son desconocidas (C.TIME_UNSET).
        // Necesitarás una implementación de androidx.media3.common.Timeline.
        // Aquí un ejemplo muy simplificado de cómo podría ser una Timeline para esto.
        // En la práctica, podrías necesitar una clase más robusta.

        val newTimeline = 1//SegmentedTtsTimeline(conceptualMediaItems, textSegments)
        // SegmentedTtsTimeline sería tu propia clase que implementa androidx.media3.common.Timeline

        // (CRUCIAL) Anuncia la nueva Timeline a SimpleBasePlayer.
        // SimpleBasePlayer tiene un método `invalidateTimeline()` que debes llamar
        // después de haber preparado la nueva estructura de la timeline para que él la recoja.
        // O, si SimpleBasePlayer tiene un método setTimeline(Timeline newTimeline), úsalo.
        // La forma más común es tener una variable de instancia para tu timeline actual
        // y luego llamar a invalidateTimeline().
        //this.currentTimeline = newTimeline // Suponiendo que tienes una var currentTimeline: Timeline
        //invalidateTimeline() // ¡ESTO ES CLAVE! Notifica a SimpleBasePlayer.

        //Timber.d("updatePlayerTimeline: Nueva timeline con ${newTimeline.windowCount} ventanas/segmentos invalidada.")
    }

    // En tu clase TtsPlayerForApi26.kt

    fun handleSetMediaItemss(
        mediaItems: MutableList<MediaItem>, // Los MediaItems que SBP quiere que establezcas.
        startIndex: Int,                   // El índice del ítem desde el cual empezar.
        startPositionMs: Long              // La posición en ms dentro de ese ítem.
    ): ListenableFuture<*> {

        Timber.d("handleSetMediaItems: Recibidos ${mediaItems.size} mediaItems. startIndex: $startIndex, startPositionMs: $startPositionMs")

        // Este método es llamado por SBP cuando se invoca SBP.setMediaItems(...),
        // SBP.addMediaItems(...), etc.
        // Tu responsabilidad es tomar estos 'mediaItems' conceptuales y configurar
        // tu lógica interna de TTS (principalmente 'localSegmentTexts') y tu estado
        // de reproducción ('currentMediaItemIndex', 'currentPositionMs').

        // Estrategia Principal:
        // 1. Convertir/Usar 'mediaItems' para poblar 'localSegmentTexts'.
        //    Dado que tu 'setTextAndMediaItems' ya hace esto y luego llama a SBP.setMediaItems,
        //    este 'handleSetMediaItems' se vuelve un punto de sincronización.
        //    Si este método es llamado externamente (ej. por MediaSession), SÍ necesitarías
        //    extraer el texto de 'mediaItems' aquí.
        //
        // 2. Usar 'startIndex' y 'startPositionMs' para actualizar tu
        //    'currentMediaItemIndex' y 'currentPositionMs'.

        mainHandler.post {
            // A. Actualizar localSegmentTexts si es necesario (escenario de llamada externa)
            //    Si asumes que 'setTextAndMediaItems' es la única forma de poner contenido,
            //    'localSegmentTexts' ya estaría sincronizado con los 'mediaItems' que SBP tiene.
            //    Si no, necesitarías una lógica como esta:
            /*
            val newTextSegments = mediaItems.mapNotNull { mediaItem ->
                // Extrae el texto de mediaItem.mediaMetadata.title, subtitle, o como lo almacenes.
                // Esto es crucial si este método puede ser llamado independientemente de tu setTextAndMediaItems.
                // Por ahora, asumiremos que localSegmentTexts ya está poblado si la llamada
                // vino de tu propio setTextAndMediaItems. Si no, esta parte es vital.
                mediaItem.mediaMetadata?.title?.toString() // Ejemplo
            }
            if (localSegmentTexts.map { it.hashCode() } != newTextSegments.map { it.hashCode() }) { // Comprobación simple de cambio
                localSegmentTexts.clear()
                localSegmentTexts.addAll(newTextSegments)
                Timber.d("handleSetMediaItems: localSegmentTexts actualizados desde mediaItems con ${localSegmentTexts.size} segmentos.")
            }
            */
            // Por ahora, vamos a asumir que `localSegmentTexts` ya fue correctamente
            // poblado por tu método `setTextAndMediaItems` justo antes de que SBP llamara a esto.
            // Si `mediaItems.size` no coincide con `localSegmentTexts.size` aquí, hay un problema de sincronización.
            // SBP ya habrá actualizado su `this.mediaItemCount` basado en `mediaItems.size`.

            Timber.d("handleSetMediaItems: SBP.mediaItemCount=${this.mediaItemCount}, localSegmentTexts.size=${localSegmentTexts.size}")

            // B. Actualizar el índice y la posición de reproducción interna.
            if (this.mediaItemCount > 0) { // Usa el conteo de SBP como referencia
                // Valida startIndex
                val validStartIndex = if (startIndex >= 0 && startIndex < this.mediaItemCount) {
                    startIndex
                } else {
                    Timber.w("handleSetMediaItems: startIndex $startIndex inválido para mediaItemCount ${this.mediaItemCount}. Usando 0.")
                    0 // O manejar como error / no hacer nada si es preferible
                }
                currentMediaItemIndex = validStartIndex
                currentPositionMs =
                    startPositionMs // Asumimos que startPositionMs es para tu segmento TTS

                Timber.d("handleSetMediaItems: Posición interna actualizada. currentMediaItemIndex: $currentMediaItemIndex, currentPositionMs: $currentPositionMs ms")

                // Prepara el segmento para la nueva posición
                /*segmentProgressListener.invoke(
                    0f,
                    formatMillis(0),
                    formatMillis(0)
                )*/ // Resetea progreso visual del segmento
                estimateDurationAndResetProgressForSegment(currentMediaItemIndex) // Estima duración y prepara el segmento actual
            } else {
                // No hay ítems según SBP
                currentMediaItemIndex = C.INDEX_UNSET
                currentPositionMs = 0L
                Timber.d("handleSetMediaItems: No hay media items según SBP. Posición interna reseteada.")
            }

            // C. Actualizar el estado reportado del reproductor.
            //    Si SBP nos dio ítems, deberíamos estar en READY (o IDLE si startPositionMs implica un reset completo).
            //    Si no hay ítems, el estado es IDLE.
            val newState = if (this.mediaItemCount == 0) {
                Player.STATE_IDLE
            } else {
                // Si tenemos ítems, y SBP nos da un start index/position,
                // implica que estamos listos para reproducir desde ahí (o ya reproduciendo si playWhenReady es true).
                // Si playWhenReady es false, el estado es READY. Si es true, podría ser BUFFERING o READY.
                // Por simplicidad, si hay items, vamos a READY. La reproducción real se manejará
                // en handleSetPlayWhenReady.
                Player.STATE_READY
            }

            // D. Notificar el progreso general.
            /*overallProgressListener.invoke(
                currentMediaItemIndex.coerceAtLeast(0),
                this.mediaItemCount
            )*/
            updateReportedPlayerState(playbackStateUpdate = newState)

            Timber.d("handleSetMediaItems finalizado. Estado interno: ${this.internalPlaybackState}, SBP mediaItemCount: ${this.mediaItemCount}")
        }

        // Devuelve un Future que se completa cuando la operación ha sido manejada.
        return Futures.immediateVoidFuture()
    }

    // --- Helper para formatear tiempo ---
    private fun formatMillis(millis: Long): String {
        val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(millis)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }


    private fun getDefaultPlayerCommands(): Player.Commands {
        return Player.Commands.Builder()
            .addAll(
                Player.COMMAND_CHANGE_MEDIA_ITEMS,
                Player.COMMAND_GET_CURRENT_MEDIA_ITEM,
                Player.COMMAND_GET_METADATA,
                Player.COMMAND_GET_TIMELINE,
                Player.COMMAND_PLAY_PAUSE,
                Player.COMMAND_PREPARE,
                Player.COMMAND_SEEK_BACK,
                Player.COMMAND_SEEK_FORWARD,
                Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
                Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM,
                Player.COMMAND_SEEK_TO_MEDIA_ITEM,
                Player.COMMAND_SET_SHUFFLE_MODE,
                Player.COMMAND_STOP,
                // Añade otros comandos que soportes
            ).build()
    }

    @OptIn(UnstableApi::class) // MediaSession.MediaItemsWithStartPosition
    fun onSetMediaItems(
        mediaSession: MediaSession,
        browser: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>,
        startIndex: Int,
        startPositionMs: Long,
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        if (mediaItems.size == 1) {
            // Try to expand a single item to a playlist.

        }
        return Futures.immediateFuture(
            MediaSession.MediaItemsWithStartPosition(
                resolveMediaItems(mediaItems),
                startIndex,
                startPositionMs
            )
        )
    }

    fun handleSetMediaItemsss(
        mediaItems: MutableList<MediaItem>,
        startIndex: Int,
        startPositionMs: Long
    ): ListenableFuture<*> {
        val result = Futures.immediateFuture(resolveMediaItems(mediaItems))
        return result
    }

    /**
     * Este es el método que SimpleBasePlayer llama cuando Player.setMediaItems(...)
     * o Player.addMediaItems(...) etc., son invocados en la instancia del Player.
     * Tu responsabilidad es actualizar tu estado interno y preparar el Timeline.
     */

    override fun handleSetMediaItems(
        mediaItems: MutableList<MediaItem>,
        startIndex: Int,
        startPositionMs: Long
    ): ListenableFuture<*> {
        Timber.tag(TAG).i(
            "handleSetMediaItems: Entrando. Recibidos ${mediaItems.size} items. " +
                    "Target startIndex: $startIndex, startPositionMs: $startPositionMs"
        )
        Timber.tag(TAG)
            .d("handleSetMediaItems: SBP mediaItemCount ANTES de procesar: ${this.mediaItemCount}") // this.mediaItemCount es el de SBP

        this.localSegmentTexts.clear()
        mediaItems.forEach { mediaItem ->
            mediaItem.mediaMetadata?.title?.toString()?.let {
                this.localSegmentTexts.add(it)
            }
        }
        Timber.tag(TAG)
            .d("handleSetMediaItems: localSegmentTexts poblado con ${this.localSegmentTexts.size} segmentos.")

        if (this.localSegmentTexts.isNotEmpty()) {
            val validStartIndex =
                if (startIndex >= 0 && startIndex < this.localSegmentTexts.size) startIndex else 0
            this.currentMediaItemIndex = validStartIndex
            Timber.tag(TAG)
                .d("handleSetMediaItems: currentMediaItemIndex establecido a: $currentMediaItemIndex")
        } else {
            this.currentMediaItemIndex = C.INDEX_UNSET
            Timber.tag(TAG)
                .d("handleSetMediaItems: No hay segmentos locales. currentMediaItemIndex establecido a UNSET.")
        }

        // SBP actualizará su Timeline y mediaItemCount DESPUÉS de que este método retorne,
        // como parte de su flujo interno de setMediaItems.
        // Llamar a invalidateState() aquí asegura que los listeners sean notificados
        // de los cambios una vez que SBP los haya aplicado.
        invalidateState()

        Timber.tag(TAG)
            .i("handleSetMediaItems: Saliendo. SBP mediaItemCount DESPUÉS de procesar (pero ANTES de que SBP actualice completamente): ${this.mediaItemCount}")
        return Futures.immediateVoidFuture()
    }

    fun handleSetMediaItemsX(
        mediaItems: MutableList<MediaItem>, // Los MediaItems que SBP quiere que establezcas.
        startIndex: Int,                   // El índice del ítem desde el cual empezar.
        startPositionMs: Long              // La posición en ms dentro de ese ítem.
    ): ListenableFuture<*> { // <--- TIPO DE RETORNO CORRECTO
        Timber.tag(TAG).i(
            "handleSetMediaItems: Entrando. Recibidos ${mediaItems.size} items. " +
                    "Target startIndex: $startIndex, startPositionMs: $startPositionMs"
        )
        Timber.tag(TAG)
            .d("handleSetMediaItems: SBP mediaItemCount ANTES de procesar: ${this.mediaItemCount}") // this.mediaItemCount es el de SBP


        // 1. Actualiza tu lógica interna y la lista de segmentos de texto.
        this.localSegmentTexts.clear()
        mediaItems.forEach { mediaItem ->
            mediaItem.mediaMetadata?.title?.toString()?.let {
                this.localSegmentTexts.add(it)
            }
        }
        Timber.tag(TAG)
            .d("handleSetMediaItems: localSegmentTexts populated with ${localSegmentTexts.size} segments.")

        // 2. Actualiza el Timeline del reproductor.
        //    SimpleBasePlayer espera que actualices la estructura de tu Timeline aquí
        //    y luego llames a invalidateTimeline() o invalidateState().
        //    SimpleBasePlayer NO actualiza su timeline automáticamente solo porque este método
        //    fue llamado. Eres responsable de construir y establecer el nuevo Timeline.

        //    Si tienes una clase `SegmentedTtsTimeline` como discutimos anteriormente:
        //    this.currentTtsTimeline = SegmentedTtsTimeline(this.localSegmentTexts, /* duraciones si las conoces */)
        //    O, si los MediaItems ya tienen la información suficiente para tu Timeline:
        //    this.currentTtsTimeline = TuImplementacionDeTimeline(mediaItems)

        //    Por ahora, asumamos que tu Timeline se basa en los `localSegmentTexts`
        //    y que las duraciones son desconocidas o estimadas.
        //    SimpleBasePlayer tiene una variable `timeline` protegida.
        //    La forma de actualizarla es construir tu nuevo Timeline y luego llamar a invalidateTimeline().
        //    SBP recogerá el nuevo timeline a través de `getTimeline()` que puede ser que tengas que
        //    sobrescribir si no usas `super.setTimeline()`.

        //    La forma más directa si no tienes una clase Timeline compleja es
        //    dejar que SBP cree un Timeline básico a partir de los mediaItems que él mismo
        //    almacenará después de esta llamada. SBP actualizará su `mediaItemCount`
        //    y su `Timeline` interno basado en la lista de `mediaItems` que se le pasa
        //    a su método `setMediaItemsInternal` (que es llamado por `setMediaItems`).

        //    **Importante**: SimpleBasePlayer SÍ actualiza su propia lista de mediaItems
        //    y su timeline básico internamente DESPUÉS de que este método handleSetMediaItems
        //    retorna, como parte de su propia implementación de setMediaItems/addMediaItems.
        //    Por lo tanto, no necesitas llamar a `super.setMediaItems` aquí.
        //    Tu principal responsabilidad aquí es actualizar TU lógica interna (localSegmentTexts)
        //    y prepararte para que SBP te pida reproducir desde startIndex.

        // 3. Actualiza el índice y la posición de reproducción interna de TU reproductor.
        if (this.localSegmentTexts.isNotEmpty()) {
            val validStartIndex = if (startIndex >= 0 && startIndex < this.localSegmentTexts.size) {
                startIndex
            } else {
                Timber.tag(TAG).w(
                    "handleSetMediaItems: startIndex $startIndex inválido para ${localSegmentTexts.size} segmentos. Usando 0."
                )
                0
            }
            this.currentMediaItemIndex = validStartIndex
            // this.currentPositionMs = startPositionMs // SBP maneja su propia posición.
            //                                          // Tú manejarás la posición DENTRO de tu segmento TTS.
            Timber.tag(TAG).d(
                "handleSetMediaItems: Posición interna actualizada. currentMediaItemIndex: $currentMediaItemIndex"
            )
            // Prepara el segmento para la nueva posición si es necesario
            // estimateDurationAndResetProgressForSegment(currentMediaItemIndex)
        } else {
            this.currentMediaItemIndex = C.INDEX_UNSET
            // this.currentPositionMs = 0L
            Timber.tag(TAG)
                .d("handleSetMediaItems: No hay segmentos locales. Posición interna reseteada.")
        }

        // 4. Notifica a SimpleBasePlayer que el estado y/o el timeline podrían haber cambiado.
        //    SBP actualizará su `mediaItemCount` y su `Timeline` después de que este método retorne.
        //    Llamar a invalidateState() aquí asegura que los listeners sean notificados
        //    de cualquier cambio resultante (como el nuevo mediaItemCount, timeline, y
        //    potencialmente el estado si pasa a IDLE o READY).
        invalidateState()
        // Opcionalmente, si has creado una instancia de Timeline completamente nueva y quieres
        // que SBP la use inmediatamente, podrías tener una variable de instancia para tu Timeline
        // y llamar a invalidateTimeline(). SBP entonces llamaría a tu getTimeline() sobrescrito.
        // Pero para muchos casos, dejar que SBP maneje su Timeline básico a partir de los items
        // que se le pasaron es suficiente, y tu lógica se centra en `localSegmentTexts` y `currentMediaItemIndex`.

        Timber.tag(TAG).d(
            "handleSetMediaItems finalizado. SBP mediaItemCount (después de esto será actualizado por SBP): ${this.mediaItemCount}"
        )

        // 5. Devuelve un Future.
        return Futures.immediateVoidFuture()
    }

    private fun resolveMediaItems(mediaItems: List<MediaItem>): List<MediaItem> {
        val playlist = mutableListOf<MediaItem>()
        mediaItems.forEach { mediaItem ->
            if (mediaItem.mediaId.isNotEmpty()) {
                playlist.add(mediaItem)
            } else if (mediaItem.requestMetadata.searchQuery != null) {
                //playlist.addAll(MediaItemTree.search(mediaItem.requestMetadata.searchQuery!!))
            }
        }
        return playlist
    }

    fun getLocalSegmentTextsCount(): Int = localSegmentTexts.size
    fun getCurrentTtsPlayerMediaItemIndex(): Int = this.currentMediaItemIndex
}