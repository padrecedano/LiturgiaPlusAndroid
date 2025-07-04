package org.deiverbum.app.feature.tts

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.RequiresApi
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.Clock
import androidx.media3.common.util.UnstableApi
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import timber.log.Timber
import java.util.Locale
import java.util.UUID

@UnstableApi
class TtsPlayerMedia
@RequiresApi(Build.VERSION_CODES.O)
constructor(
    looper: Looper, // Looper para SimpleBasePlayer y el Handler interno
    private val context: Context,
    // initialText ya no es necesario si siempre usamos setTextAndMediaItems
    private val overallProgressListener: (currentSegment: Int, totalSegments: Int) -> Unit,
    private val segmentProgressListener: (progressPercent: Float, progressString: String, durationString: String) -> Unit
) : SimpleBasePlayer(looper, Clock.DEFAULT), TextToSpeech.OnInitListener {
    // Implementa OnInitListener aquí

    companion object {
        private const val TAG = "TtsPlayerApi26"

        // Valores predeterminados si no se establecen explícitamente
        private const val DEFAULT_SEEK_INCREMENT_MS = 10000L // 10 segundos
        private const val AVG_CHARS_PER_SECOND_FOR_DURATION_ESTIMATE =
            12 // Ajusta según sea necesario
        private const val DEFAULT_MAX_SEEK_TO_PREVIOUS_MS_CUSTOM = 3000L // 3 segundos
    }

    // Propiedades del TTS
    private lateinit var textToSpeechEngine: TextToSpeech // lateinit porque se inicializa en init/constructor
    private var isTtsInitialized = false // Estado de inicialización del TTS
    private var currentLanguage: Locale =
        Locale("spa", "ESP") // Ejemplo, hazlo configurable si es necesario
    private var reportedPlayerState: State = getDefaultPlayerInitialState()

    // Propiedades para manejar contenido y estado
    internal var textSegments: List<String> = emptyList() // Lista de segmentos de texto a hablar
        private set // Solo modificable dentro de esta clase

    private var pendingSegmentsToSet: List<String>? = null
    private var pendingMediaItemsToSet: List<MediaItem>? = null
    private var pendingResetPosition: Boolean = false


    // Propiedades relacionadas con la reproducción actual
    private var currentUtteranceId: String? = null
    private var currentSegmentEstimatedDurationMs: Long = 0L
    internal var intendedPlayWhenReady: Boolean =
        false // Rastrea la intención del usuario de reproducir
        private set

    // Handler para postear acciones en el hilo del reproductor
    private val mainHandler = Handler(looper)

    private var internalPlayerError: PlaybackException? = null
    private var internalPlaybackState: @Player.State Int = Player.STATE_IDLE

    init {
        Timber.tag(TAG).d("TtsPlayerMedia (instance $this) CONSTRUCTOR.")
        // Inicializa el motor TTS. El resultado se recibe en onInit.
        textToSpeechEngine = TextToSpeech(context, this /* OnInitListener */)
        Timber.tag(TAG).d("TextToSpeech engine instantiation requested.")

        // Configuración inicial de SimpleBasePlayer (comandos disponibles)
        // Esto se puede hacer aquí o en un método que actualice el estado.
        // Por ahora, lo dejamos para cuando actualicemos el estado general.
        // updateReportedPlayerState() // Podrías llamar a una inicialización de estado aquí
    }

    // --- Implementación de TextToSpeech.OnInitListener ---
    override fun onInit(status: Int) {
        mainHandler.post { // Asegura que se ejecuta en el hilo del Looper del reproductor
            if (status == TextToSpeech.SUCCESS) {
                Timber.tag(TAG).i("TTS Engine Initialized successfully.")
                isTtsInitialized = true

                // Configurar idioma y UtteranceProgressListener
                val langResult = textToSpeechEngine.setLanguage(currentLanguage)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Timber.tag(TAG)
                        .e("TTS Language (Locale: $currentLanguage) is missing data or not supported. Result: $langResult")
                    // Error crítico: El reproductor no puede funcionar como se espera.
                    updateReportedPlayerState(
                        playerErrorUpdate = PlaybackException(
                            "TTS Language (Locale: $currentLanguage) not supported. Result: $langResult",
                            null, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                        ),
                        playbackStateUpdate = Player.STATE_IDLE // Permanece o vuelve a IDLE con error
                    )
                    // Limpiar pendientes ya que no se podrán procesar correctamente
                    pendingSegmentsToSet = null
                    pendingMediaItemsToSet = null
                    return@post
                } else {
                    Timber.tag(TAG).i("TTS Language set successfully to: $currentLanguage.")
                }

                textToSpeechEngine.setOnUtteranceProgressListener(ttsUtteranceListener)

                // Procesar contenido pendiente si existe
                pendingSegmentsToSet?.let { segments ->
                    pendingMediaItemsToSet?.let { mediaItems ->
                        Timber.tag(TAG)
                            .d("onInit: Procesando ${segments.size} segmentos pendientes (reset: $pendingResetPosition).")
                        processSetMediaItemsInternal(segments, mediaItems, pendingResetPosition)
                    }
                }
                pendingSegmentsToSet = null // Limpiar después de procesar
                pendingMediaItemsToSet = null
                pendingResetPosition = false

                // Si no había contenido pendiente, pero el reproductor ya estaba "preparado"
                // (es decir, en STATE_BUFFERING esperando al TTS), y tiene items,
                // ahora podría estar listo.
                if (pendingSegmentsToSet == null && // Ya se limpió o no había
                    mediaItemCount > 0 && playbackState == Player.STATE_BUFFERING
                ) {
                    updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
                    // Si la intención era reproducir, y ahora estamos listos, intenta hablar.
                    if (intendedPlayWhenReady && currentMediaItemIndex != C.INDEX_UNSET) {
                        speakSegmentAtIndex(currentMediaItemIndex)
                    }
                } else if (pendingSegmentsToSet == null && mediaItemCount == 0 && playbackState != Player.STATE_IDLE) {
                    // Si no hay items (ni pendientes ni cargados), y no estábamos ya en IDLE, ir a IDLE.
                    updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
                }
                // Si ya estábamos en READY (quizás por un `prepare` anterior que no dependía del TTS para estar "listo"),
                // no es necesario cambiar el estado aquí a menos que algo más lo requiera.

            } else {
                Timber.tag(TAG).e("TTS engine initialization FAILED! Status: $status")
                isTtsInitialized = false
                // Error crítico: El reproductor no puede funcionar.
                updateReportedPlayerState(
                    playerErrorUpdate = PlaybackException(
                        "TTS Engine Initialization Failed, status: $status",
                        null, PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                    ),
                    playbackStateUpdate = Player.STATE_IDLE // Permanece o vuelve a IDLE con error
                )
                pendingSegmentsToSet = null // Limpiar pendientes
                pendingMediaItemsToSet = null
                pendingResetPosition = false
            }
        }
    }


    // --- Método Público para Establecer Contenido ---
    fun setTextAndMediaItems(
        newTextSegments: List<String>,
        newConceptualMediaItems: List<MediaItem>,
        resetPosition: Boolean
    ) {
        mainHandler.post { // Asegura que se ejecuta en el hilo del Looper del reproductor
            Timber.tag(TAG).i(
                "setTextAndMediaItems: Recibidos ${newTextSegments.size} segmentos. " +
                        "TTS Initialized: $isTtsInitialized. Reset: $resetPosition"
            )

            if (isTtsInitialized) {
                // TTS está listo, procesar directamente
                processSetMediaItemsInternal(
                    newTextSegments,
                    newConceptualMediaItems,
                    resetPosition
                )
            } else {
                // TTS aún no está inicializado. Almacena el contenido para procesarlo en onInit.
                Timber.tag(TAG)
                    .w("setTextAndMediaItems: TTS no inicializado. Guardando contenido como pendiente.")
                pendingSegmentsToSet = newTextSegments
                pendingMediaItemsToSet = newConceptualMediaItems
                pendingResetPosition = resetPosition
                // El reproductor podría estar en IDLE. Si se llama a prepare() desde fuera
                // mientras el contenido está pendiente, handlePrepare() debería llevarlo a BUFFERING.
                // onInit se encargará de moverlo a READY si es necesario.
            }
        }
    }

    // --- Método Interno para Procesar el Establecimiento de MediaItems ---
    private fun processSetMediaItemsInternal(
        segmentsToProcess: List<String>,
        mediaItemsToProcess: List<MediaItem>,
        shouldResetPosition: Boolean
    ) {
        // Esta función asume que se llama desde el mainHandler y que isTtsInitialized es true (o se maneja antes)


        // Detener cualquier habla en curso del TTS antes de cambiar la lista de reproducción
        if (textToSpeechEngine.isSpeaking) {
            Timber.tag(TAG).d("processSetMediaItemsInternal: TTS está hablando, deteniéndolo.")
            textToSpeechEngine.stop() // Esto debería limpiar la cola del TTS
        }
        currentUtteranceId = null // Limpiar el ID del utterance actual

        // Actualizar nuestra lista interna de segmentos de texto
        this.textSegments = segmentsToProcess

        // Informar a SimpleBasePlayer sobre los nuevos MediaItems.
        // Esto reconstruirá la Timeline interna de SimpleBasePlayer.
        // Si shouldResetPosition es true, el índice actual se establecerá en el default (usualmente 0)
        // y la posición a 0.
        Timber.d("processSetMediaItemsInternal: Estado ANTES de super.setMediaItems: SBP PlaybackState=${this.playbackState}, SBP PlayerError=${this.playerError}, internalPlaybackState=${this.internalPlaybackState}, internalPlayerError=${this.internalPlayerError}")
        Timber.tag(TAG)
            .d("processSetMediaItemsInternal: Llamando a super.setMediaItems con ${mediaItemsToProcess.size} items. Reset: $shouldResetPosition")

        super.setMediaItems(mediaItemsToProcess, shouldResetPosition)
        Timber.tag(TAG)
            .d("processSetMediaItemsInternal: Después de super.setMediaItems. Nuevo mediaItemCount de SBP: ${this.mediaItemCount}, Timeline isEmpty:")


        // Si se resetea la posición y hay elementos, podríamos querer estimar la duración
        // del primer segmento para la UI, aunque `prepare()` se encargará de la preparación formal.
        // El estado del reproductor (IDLE, BUFFERING, READY) será manejado por SimpleBasePlayer
        // como resultado de setMediaItems() y la posterior llamada a prepare() desde TtsServiceHandler.
        if (shouldResetPosition && segmentsToProcess.isNotEmpty()) {
            // Opcional: estimar duración para el primer segmento si es necesario para la UI inmediatamente.
            // estimateDurationAndResetProgressForSegment(0)
            // Timber.tag(TAG).d("processSetMediaItemsInternal: Estimación de duración para el primer segmento (si es aplicable).")
        }

        // Después de setMediaItems, SimpleBasePlayer puede cambiar su estado.
        // Por ejemplo, si la nueva lista está vacía, podría ir a STATE_IDLE.
        // Si tiene items y resetPosition=true, currentMediaItemIndex se actualizará.
        // La llamada a player.prepare() desde TtsServiceHandler es la que
        // realmente moverá el estado a BUFFERING/READY si hay items.
    }


    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handlePrepare(): ListenableFuture<() -> Unit> {
        mainHandler.post { // Asegura que se ejecuta en el hilo del Looper del reproductor
            Timber.tag(TAG).d(
                "handlePrepare: TTS Initialized: $isTtsInitialized, " +
                        "MediaItems: $mediaItemCount, " +
                        "CurrentIndex: $currentMediaItemIndex, " +
                        "CurrentSBPState: $playbackState"
            )

            if (!isTtsInitialized) {
                // TTS no está listo todavía. Nos ponemos en BUFFERING y esperamos a que onInit
                // nos mueva a READY (o a IDLE con error si falla la inicialización del TTS).
                Timber.tag(TAG)
                    .w("handlePrepare: TTS no inicializado. Entrando en BUFFERING y esperando a onInit.")
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_BUFFERING)
                // SimpleBasePlayer se quedará en BUFFERING. onInit se encargará del resto.
                return@post
            }

            // TTS está inicializado en este punto.

            if (mediaItemCount == 0) {
                // No hay items para reproducir. SimpleBasePlayer espera READY después de prepare,
                // incluso con una Timeline vacía.
                Timber.tag(TAG)
                    .d("handlePrepare: No hay MediaItems. Transicionando a READY con Timeline vacía.")
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
                return@post
            }

            // Hay MediaItems y el TTS está listo.
            // El currentMediaItemIndex debería haber sido establecido por setMediaItems
            // (si resetPosition=true) o mantenido si es válido.
            // Si el índice es C.INDEX_UNSET pero hay items, SimpleBasePlayer
            // lo establecerá al índice por defecto (usualmente 0) como parte de setMediaItems o prepare.
            // Así que aquí, currentMediaItemIndex debería ser válido si mediaItemCount > 0.

            if (currentMediaItemIndex == C.INDEX_UNSET && mediaItemCount > 0) {
                // Esto es inesperado si setMediaItems funcionó, pero como salvaguarda.
                // SimpleBasePlayer debería haberlo puesto en 0 si resetPosition fue true.
                // No necesitamos hacer seek aquí, SBP lo maneja.
                Timber.tag(TAG)
                    .w("handlePrepare: currentMediaItemIndex es UNSET pero hay items. SBP debería manejarlo.")
            }

            // Estimamos la duración del segmento actual (o el primero si el índice es unset pero hay items).
            // SBP ya habrá establecido currentMediaItemIndex a 0 si era unset y resetPosition=true.
            val indexToPrepare =
                if (currentMediaItemIndex != C.INDEX_UNSET) currentMediaItemIndex else 0
            estimateDurationAndResetProgressForSegment(indexToPrepare)

            // Pasamos a READY.
            updateReportedPlayerState(playbackStateUpdate = Player.STATE_READY)
            Timber.tag(TAG)
                .d("handlePrepare: Transicionado a READY. currentMediaItemIndex: $currentMediaItemIndex")

            // Si la intención era reproducir, y ahora estamos listos, intenta hablar.
            // Esto es importante si play() fue llamado antes de prepare() o mientras estábamos en BUFFERING.
            if (intendedPlayWhenReady && currentMediaItemIndex != C.INDEX_UNSET) {
                Timber.tag(TAG)
                    .d("handlePrepare: intendedPlayWhenReady es true y estamos READY. Iniciando habla.")
                speakSegmentAtIndex(currentMediaItemIndex)
            }
        }
        // handlePrepare es síncrono desde la perspectiva de SBP si todas las operaciones
        // se postean al handler y no esperamos un resultado asíncrono *aquí*.
        return Futures.immediateFuture {}
    }

    fun getStateee(): State {
        TODO("Not yet implemented")
    }

    // En TtsPlayerMedia.kt
    override fun getState(): State { /* ... (igual que antes) ... */
        return reportedPlayerState
    }

    @OptIn(UnstableApi::class) // Para Player.State.Builder y otras APIs si son necesarias
    fun getStateB(): State {
        Timber.tag(TAG).v(
            "getState() (interpretación estricta del Builder): SBP.playWhenReady=${this.playWhenReady}, " +
                    "SBP.repeatMode=${this.repeatMode}, SBP.shuffleModeEnabled=${this.shuffleModeEnabled}, " +
                    "SBP.playlistMetadata=${this.playlistMetadata != null}, " + // Solo para ver si existe
                    "INTERNAL.playbackState=${this.internalPlaybackState}, INTERNAL.playerError=${this.internalPlayerError}"
        )

        val builder = State.Builder()

        // 1. Comandos que TU reproductor soporta.
        //    SimpleBasePlayer podría añadir/filtrar comandos adicionales basados en su estado.
        //    Estos son los que TÚ declaras que tu lógica puede manejar o que son relevantes.
        val commandsBuilder = Player.Commands.Builder()
            .add(Player.COMMAND_PLAY_PAUSE)
            .add(Player.COMMAND_STOP)
            // Los comandos GET son para que los clientes puedan solicitar esta información.
            // Aunque no los establezcas explícitamente en este builder (como la timeline),
            // SBP los proporcionará. Es bueno declararlos si los clientes los esperan.
            .add(Player.COMMAND_GET_CURRENT_MEDIA_ITEM)
            .add(Player.COMMAND_GET_METADATA)
            .add(Player.COMMAND_GET_TIMELINE)

        // Solo añade comandos de seek si SBP tiene una timeline con items.
        // Aunque no establezcamos la timeline aquí, SBP la tiene.
        if (!this.currentTimeline.isEmpty) { // this.currentTimeline es de SBP
            commandsBuilder.add(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            commandsBuilder.add(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            if (this.currentMediaItemIndex < this.currentTimeline.windowCount - 1) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            }
            if (this.currentMediaItemIndex > 0) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            }
        }
        builder.setAvailableCommands(commandsBuilder.build())

        // 2. PlayWhenReady: Refleja la propiedad de SBP.
        builder.setPlayWhenReady(
            this.playWhenReady,
            Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST /* Razón no crítica aquí */
        )

        // 3. PlaybackState: CRUCIAL - Este es el estado de TU motor TTS.
        builder.setPlaybackState(this.internalPlaybackState)

        // 4. PlayerError: CRUCIAL - Este es el error de TU motor TTS.
        builder.setPlayerError(this.internalPlayerError)

        // 5. IsLoading: Basado en TU lógica interna.
        //    Por ejemplo, si tu TTS está sintetizando o en un estado de buffering propio.
        val amIBufferingOrPreparing =
            (this.internalPlaybackState == Player.STATE_BUFFERING && this.internalPlayerError == null) ||
                    (this.internalPlaybackState == Player.STATE_IDLE && this.playWhenReady && !this.currentTimeline.isEmpty && this.internalPlayerError == null)
        builder.setIsLoading(amIBufferingOrPreparing)


        // 6. Modos de Repetición y Aleatorio: Reflejan las propiedades de SBP.
        builder.setRepeatMode(this.repeatMode)
        builder.setShuffleModeEnabled(this.shuffleModeEnabled)

        // 7. PlaylistMetadata: Refleja la propiedad de SBP.
        builder.setPlaylistMetadata(this.playlistMetadata)


        // NOTA: No establecemos explícitamente:
        // - Timeline
        // - currentMediaItemIndex
        // - currentPositionMs
        // - bufferedPositionMs
        // - totalBufferedDurationMs
        // - currentAdGroupIndex, currentAdIndexInAdGroup
        // Porque Player.State.Builder NO tiene métodos set para ellos.
        // La suposición es que SimpleBasePlayer tomará el State que devolvemos
        // y lo "completará" con su propia información de timeline/pista/posición
        // antes de usarlo o notificar a los listeners.

        val newState = builder.build()

        Timber.tag(TAG).v(
            "getState() FIN (interpretación estricta del Builder): Devolviendo State con " +
                    "playWhenReady=${newState.playWhenReady}, playbackState=${newState.playbackState}, " +
                    "isLoading=${newState.isLoading}, error=${newState.playerError}"
            // No podemos loguear timeline/posición del newState aquí porque no las establecimos.
        )

        return newState
    }

    fun getStateo(): State {
        // Log inicial para ver el estado de SBP y tus variables internas ANTES de construir el nuevo estado.

        val builder = State.Builder() // Crea un builder nuevo y vacío

        // 1. Configurar Comandos Disponibles
        val commandsBuilder = Player.Commands.Builder()
            .add(Player.COMMAND_PLAY_PAUSE)
            .add(Player.COMMAND_STOP)
            .add(Player.COMMAND_GET_CURRENT_MEDIA_ITEM) // Obtener el MediaItem actual
            .add(Player.COMMAND_GET_METADATA)           // Obtener metadatos
            .add(Player.COMMAND_GET_TIMELINE)           // Obtener la Timeline

        // Añadir comandos de seek solo si la timeline (de SBP) no está vacía
        if (!this.currentTimeline.isEmpty) {
            commandsBuilder.add(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            commandsBuilder.add(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            // Solo permitir seek next/previous si es aplicable según el índice actual
            if (this.currentMediaItemIndex < this.currentTimeline.windowCount - 1) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            }
            if (this.currentMediaItemIndex > 0) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            }
        }
        builder.setAvailableCommands(commandsBuilder.build())

        // 2. Configurar Modos de Repetición y Aleatorio (desde SBP)
        builder.setRepeatMode(this.repeatMode)
        builder.setShuffleModeEnabled(this.shuffleModeEnabled)

        // 3. Configurar el estado principal
        // PlayWhenReady: Refleja el estado de SBP (actualizado por player.setPlayWhenReady())
        // El segundo parámetro 'playWhenReadyChangeReason' no es crítico al construir el estado aquí,
        // SBP ya conoce la razón del último cambio. Usar 0 o una constante.
        builder.setPlayWhenReady(
            this.playWhenReady,
            Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST /* playWhenReadyChangeReason no es relevante aquí */
        )

        // PlaybackState: Usa tu variable interna que refleja el estado real del TTS.
        builder.setPlaybackState(this.internalPlaybackState)

        // PlayerError: Usa tu variable interna.
        builder.setPlayerError(this.internalPlayerError)

        // 4. Configurar isLoading
        // isLoading es true si estamos BUFFERING (y sin error)
        // o si estamos IDLE pero con intención de reproducir y tenemos items (preparándonos).
        val isLoading =
            (this.internalPlaybackState == Player.STATE_BUFFERING && this.internalPlayerError == null) ||
                    (this.internalPlaybackState == Player.STATE_IDLE && this.playWhenReady && !this.currentTimeline.isEmpty && this.internalPlayerError == null)
        builder.setIsLoading(isLoading)

        // 5. Configurar Timeline, MediaItem actual, Posición, Duración (desde SBP)
        // Es CRUCIAL establecer la timeline que SBP tiene.


        // PlaylistMetadata (si la usas, desde SBP)
        builder.setPlaylistMetadata(this.playlistMetadata)

        // Construir y devolver el nuevo estado
        val newState = builder.build()

        // Log final para ver el estado que se va a devolver.
        Timber.tag(TAG).v(
            "getState() FIN: Devolviendo State(playWhenReady=${newState.playWhenReady}, playbackState=${newState.playbackState}, " +
                    "timeline.isEmpty=${newState.timeline.isEmpty}, mediaItemCount=${newState.timeline.windowCount}, " + // mediaItemCount es windowCount
                    "currentMediaItemIndex=${newState.currentMediaItemIndex}, currentPosition=${newState.currentMediaItemIndex}, " +
                    "isLoading=${newState.isLoading}, error=${newState.playerError})"
        )
        return newState
    }

    fun getState1(): State {
        return State.Builder()
            .setAvailableCommands(
                Player.Commands.Builder().addAll(
                    COMMAND_PLAY_PAUSE,
                    COMMAND_STOP,
                    COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
                    COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM,
                    COMMAND_SEEK_TO_MEDIA_ITEM,
                    COMMAND_GET_CURRENT_MEDIA_ITEM,
                    COMMAND_GET_METADATA,
                    COMMAND_GET_TIMELINE
                ).build()
            )
            .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .setPlaybackState(Player.STATE_IDLE)
            .build()
    }

    @OptIn(UnstableApi::class)
    fun getStatee(): State {
        // ESTA ES LA FIRMA CORRECTA (o una muy similar que SBP espera)
        // SimpleBasePlayer ya ha inicializado 'builder' con el estado anterior (oldState).
        // Tú solo necesitas MODIFICAR el 'builder' que te pasan.
        val builder = State.Builder() // Crea un builder nuevo y vacío

        //Timber.tag(TAG).v("getState(builder): Modifying builder. Current SBP playWhenReady: ${this.playWhenReady}, SBP playbackState: ${this.playbackState}")

        // 1. Configurar Comandos Disponibles
        val commandsBuilder = Player.Commands.Builder()
            .add(Player.COMMAND_PLAY_PAUSE)
            .add(Player.COMMAND_STOP)
        if (mediaItemCount > 0) {
            commandsBuilder.add(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            commandsBuilder.add(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            if (currentMediaItemIndex < mediaItemCount - 1) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            }
            if (currentMediaItemIndex > 0) {
                commandsBuilder.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            }
        }
        builder.setAvailableCommands(commandsBuilder.build())

        // 2. Configurar Modos de Repetición y Aleatorio
        builder.setRepeatMode(this.repeatMode)
        builder.setShuffleModeEnabled(this.shuffleModeEnabled)

        // 3. Configurar el estado principal desde TUS variables internas
        //    El builder ya tiene el playWhenReady de SBP, no necesitas tocarlo aquí
        //    a menos que quieras anularlo explícitamente, lo cual no es usual.
        builder.setPlaybackState(this.internalPlaybackState)
        builder.setPlayerError(this.internalPlayerError)

        // 4. Configurar isLoading
        // Para acceder al 'playWhenReady' que SBP considera actual para esta construcción de estado,
        // puedes usar 'this.playWhenReady' (que SimpleBasePlayer actualiza)
        // o si el builder ya lo tiene, podrías leerlo de ahí si fuera necesario (pero usualmente no lo es).
        val isLoading =
            (this.internalPlaybackState == Player.STATE_BUFFERING && this.internalPlayerError == null) ||
                    (this.internalPlaybackState == Player.STATE_IDLE && this.playWhenReady && mediaItemCount > 0 && this.internalPlayerError == null)
        builder.setIsLoading(isLoading)

        // 5. Timeline, MediaItem actual, Posición, Duración:
        // SimpleBasePlayer ya habrá configurado el builder con la Timeline actual,
        // el currentMediaItemIndex, currentPositionMs, etc., basado en su estado interno.
        // Tú solo los modificas si tienes una razón muy específica para anular el comportamiento de SBP.
        // La duración de los MediaItems en la Timeline que estableciste con setMediaItems() es clave.

        // NO necesitas hacer 'return builder.build()' aquí.
        // SimpleBasePlayer toma el 'builder' que tú modificaste y llama a .build() internamente.

        val newState = builder.build()
        Timber.tag(TAG).v(
            "getState (no params): New state built: PlayWhenReady=${newState.playWhenReady}, " +
                    "PlaybackState=${newState.playbackState}, Error=${newState.playerError}, " +
                    "IsLoading=${newState.isLoading}, Timeline items: ${newState.timeline.windowCount}, "
            //"CurrentIndex: ${newState.currentMediaItemIndex}, CurrentPos: ${newState.currentPositionMs}"
        )
        return newState
    }

    // En TtsPlayerMedia.kt

    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<() -> Unit> {
        mainHandler.post { // Asegura que se ejecuta en el hilo del Looper del reproductor
            Timber.tag(TAG).d(
                "handleSetPlayWhenReady: playWhenReady=$playWhenReady, " +
                        "intendedPlayWhenReady (antes)=${this.intendedPlayWhenReady}, " + // Usar this. para claridad
                        "TTS Initialized: $isTtsInitialized, " +
                        "CurrentIndex: $currentMediaItemIndex, " +
                        "SBP State: $playbackState, " +
                        "MediaItemCount: $mediaItemCount"
            )
            this.intendedPlayWhenReady = playWhenReady // Actualiza la intención del usuario

            if (!isTtsInitialized) {
                Timber.tag(TAG)
                    .w("handleSetPlayWhenReady: TTS no inicializado. No se puede actuar sobre playWhenReady=$playWhenReady.")
                // Si playWhenReady es true, handlePrepare (o onInit) se encargará de iniciar
                // la reproducción una vez que el TTS esté listo y el reproductor preparado.
                // No es necesario hacer más aquí si el TTS no está listo.
                return@post
            }

            if (playWhenReady) {
                // --- Se solicita REPRODUCIR ---
                when (playbackState) {
                    Player.STATE_READY -> {
                        if (currentMediaItemIndex != C.INDEX_UNSET) {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado READY. Iniciando/reanudando habla para el índice $currentMediaItemIndex.")
                            speakSegmentAtIndex(currentMediaItemIndex)
                        } else if (mediaItemCount > 0) {
                            // No hay índice actual pero hay items (ej: después de stop() y luego play()).
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado READY, sin índice actual pero hay items. Buscando posición por defecto.")
                            seekToDefaultPositionInternal() // Esto debería llevar a onMediaItemTransition y luego a hablar.
                        } else {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado READY, pero no hay items ni índice. No se hace nada.")
                            // El reproductor está listo pero no tiene nada que reproducir.
                            // SimpleBasePlayer debería tener isPlaying = false.
                        }
                    }

                    Player.STATE_BUFFERING -> {
                        // Ya estamos en BUFFERING (quizás esperando a que onInit termine, o cargando algo).
                        // La intención de reproducir está registrada (this.intendedPlayWhenReady = true).
                        // handlePrepare u onInit se encargarán de iniciar el habla cuando se alcance READY.
                        Timber.tag(TAG)
                            .d("handleSetPlayWhenReady (PLAY): Estado BUFFERING. La intención de reproducir ($playWhenReady) está registrada.")
                    }

                    Player.STATE_ENDED -> {
                        // Se solicitó reproducir pero estábamos al final. Reinicia desde el principio.
                        Timber.tag(TAG)
                            .d("handleSetPlayWhenReady (PLAY): Estado ENDED. Buscando posición por defecto para reiniciar.")
                        if (mediaItemCount > 0) {
                            seekToDefaultPositionInternal() // Esto debería llevar a onMediaItemTransition y luego a hablar.
                        } else {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado ENDED, pero no hay items. No se puede reiniciar.")
                        }
                    }

                    Player.STATE_IDLE -> {
                        // Se solicitó reproducir pero estamos en IDLE.
                        // Esto podría suceder si se llama a play() antes de prepare() o después de stop().
                        if (mediaItemCount > 0) {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado IDLE con items. Llamando a this.prepare() para iniciar la reproducción.")
                            // Llamar a prepare() de la interfaz Player, que a su vez llamará a nuestro handlePrepare().
                            // SimpleBasePlayer se encargará de la transición de estado.
                            this@TtsPlayerMedia.prepare()
                        } else {
                            Timber.tag(TAG)
                                .d("handleSetPlayWhenReady (PLAY): Estado IDLE sin items. No se hace nada.")
                            // No se puede reproducir si no hay contenido y estamos en IDLE.
                            // SimpleBasePlayer debería tener isPlaying = false.
                        }
                    }

                    else -> {
                        Timber.tag(TAG)
                            .w("handleSetPlayWhenReady (PLAY): Estado de reproducción desconocido ($playbackState). No se toma acción.")
                    }
                }
            } else {
                // --- Se solicita PAUSAR (playWhenReady = false) ---
                Timber.tag(TAG)
                    .d("handleSetPlayWhenReady (PAUSE): Solicitud de PAUSA. Deteniendo el habla del TTS si está activo.")
                if (textToSpeechEngine.isSpeaking) {
                    val result = textToSpeechEngine.stop()
                    if (result == TextToSpeech.SUCCESS) {
                        Timber.tag(TAG)
                            .d("handleSetPlayWhenReady (PAUSE): textToSpeechEngine.stop() SUCCESS.")
                    } else {
                        Timber.tag(TAG)
                            .e("handleSetPlayWhenReady (PAUSE): textToSpeechEngine.stop() FAILED con código $result.")
                    }
                    // No es necesario cambiar el playbackState aquí a PAUSED manualmente.
                    // SimpleBasePlayer lo manejará basado en que playWhenReady es false
                    // y el motor TTS ya no está produciendo audio (o se espera que deje de hacerlo).
                    // El estado podría seguir siendo READY pero con isPlaying = false.
                    // Si estaba en BUFFERING, la intención de reproducir se cancela.
                } else {
                    Timber.tag(TAG)
                        .d("handleSetPlayWhenReady (PAUSE): TTS no estaba hablando. No se necesita stop().")
                }
                // Si estábamos en BUFFERING y se pausa, la intención de reproducir se cancela.
                // El estado de SimpleBasePlayer (BUFFERING o READY) no cambia solo por esto,
                // pero isPlaying se volverá false.
                // Si el reproductor estaba en IDLE o ENDED, playWhenReady=false no cambia mucho su estado fundamental.
            }
        }
        // SimpleBasePlayer espera un ListenableFuture.
        // Si tus operaciones son síncronas desde la perspectiva de este método
        // (aunque el TTS en sí es asíncrono), puedes retornar un futuro inmediato.
        return Futures.immediateFuture {}
    }

    // En TtsPlayerMedia.kt

    // --- Lógica Principal de Reproducción TTS ---
    private fun speakSegmentAtIndex(index: Int) {
        mainHandler.post { // Asegura que se ejecuta en el hilo del Looper del reproductor
            if (!isTtsInitialized) {
                Timber.tag(TAG)
                    .e("speakSegmentAtIndex: TTS no inicializado. No se puede hablar el segmento $index.")
                // Podrías querer reportar un error o cambiar el estado si esto ocurre inesperadamente.
                // Por ahora, si playWhenReady es true, onInit o handlePrepare deberían manejarlo.
                return@post
            }

            if (index < 0 || index >= textSegments.size) {
                Timber.tag(TAG)
                    .e("speakSegmentAtIndex: Índice de segmento inválido: $index. Total de segmentos: ${textSegments.size}.")
                // Esto podría indicar un problema de lógica.
                // Si llegamos aquí, podría ser el final de la reproducción o un error.
                // Si es el final, onDone del último utterance debería haberlo manejado.
                // Si es un error, el estado debería reflejarlo.
                // Considera cambiar a STATE_ENDED o IDLE con error si es apropiado.
                updateReportedPlayerState(
                    playbackStateUpdate = Player.STATE_ENDED, // O IDLE si es un error más fundamental
                    playerErrorUpdate = if (mediaItemCount > 0) null else PlaybackException( // Error si no hay items
                        "Invalid segment index $index with mediaItemCount $mediaItemCount", null,
                        PlaybackException.ERROR_CODE_REMOTE_ERROR // O un código más apropiado
                    )
                )
                return@post
            }

            val segmentText = textSegments[index]
            if (segmentText.isBlank()) {
                Timber.tag(TAG)
                    .w("speakSegmentAtIndex: El segmento $index está vacío o en blanco. Saltando.")
                // Considera este segmento como "completado" inmediatamente y avanza.
                // Esto es similar a lo que haría onDone.
                handleSegmentCompletion(true) // true indica que fue un "skip" exitoso
                return@post
            }

            // Actualizar el estado a BUFFERING mientras el TTS se prepara para hablar (opcional pero bueno para la UI)
            // SimpleBasePlayer podría ya estar en READY. Si el TTS tarda en empezar, BUFFERING es más preciso.
            // Sin embargo, el TTS suele ser rápido. Si el estado ya es READY, podríamos dejarlo.
            // Por ahora, no cambiaremos a BUFFERING aquí explícitamente a menos que veamos problemas.
            // updateReportedPlayerState(playbackStateUpdate = Player.STATE_BUFFERING) // Considerar

            currentUtteranceId = UUID.randomUUID().toString()
            Timber.tag(TAG).i(
                "speakSegmentAtIndex: Solicitando hablar segmento $index (Utterance ID: $currentUtteranceId): \"${
                    segmentText.take(50)
                }...\""
            )

            val params = Bundle()
            // params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f) // Ejemplo

            val result = textToSpeechEngine.speak(
                segmentText,
                TextToSpeech.QUEUE_FLUSH,
                params,
                currentUtteranceId
            )

            if (result == TextToSpeech.ERROR) {
                Timber.tag(TAG)
                    .e("speakSegmentAtIndex: textToSpeechEngine.speak() FAILED para el segmento $index.")
                // Error al intentar hablar.
                updateReportedPlayerState(
                    playerErrorUpdate = PlaybackException(
                        "TTS speak() failed for segment $index",
                        null,
                        PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED // O un código más apropiado
                    ),
                    playbackStateUpdate = Player.STATE_IDLE // O ENDED si no se puede recuperar
                )
                currentUtteranceId = null
            } else {
                Timber.tag(TAG)
                    .d("speakSegmentAtIndex: textToSpeechEngine.speak() SUCCESS para el segmento $index.")
                // El UtteranceProgressListener (onStart, onDone, onError) se encargará del resto.
                // No cambiamos el estado aquí inmediatamente a PLAYING.
                // SimpleBasePlayer lo hará cuando isPlaying pase a true,
                // lo cual debería ocurrir cuando el listener onPlaybackStateChanged
                // y onIsPlayingChanged sean notificados por SBP.
                // La llamada a updateReportedPlayerState() con isPlaying = true
                // se hará desde el listener de SBP (TtsServiceHandler)
                // cuando SBP determine que está reproduciendo.
            }
        }
    }

    // En TtsPlayerMedia.kt

    // --- Estimación de Duración y Manejo de Progreso de Segmento ---

    private fun estimateDurationAndResetProgressForSegment(index: Int) {
        mainHandler.post {
            if (index < 0 || index >= textSegments.size) {
                Timber.tag(TAG)
                    .w("estimateDuration: Índice inválido $index. No se puede estimar duración.")
                currentSegmentEstimatedDurationMs = 0L
                // No actualizamos el estado del reproductor aquí, solo la estimación.
                // Si esto causa problemas, el estado se manejará en otros lugares (speakSegmentAtIndex, onDone).
                return@post
            }

            val segmentText = textSegments[index]
            if (segmentText.isBlank()) {
                currentSegmentEstimatedDurationMs = 0L // Duración cero para segmentos vacíos
                Timber.tag(TAG).d("estimateDuration: Segmento $index vacío, duración estimada 0ms.")
            } else {
                // Estimación simple: longitud del texto / caracteres promedio por segundo
                // Esta es una estimación muy básica. Puedes mejorarla si es necesario.
                val estimatedDurationSeconds =
                    segmentText.length.toFloat() / AVG_CHARS_PER_SECOND_FOR_DURATION_ESTIMATE
                currentSegmentEstimatedDurationMs = (estimatedDurationSeconds * 1000).toLong()
                Timber.tag(TAG)
                    .d("estimateDuration: Segmento $index (\"${segmentText.take(20)}...\"), duración estimada: $currentSegmentEstimatedDurationMs ms.")
            }

            // Notificar a los listeners externos sobre el progreso (0% al inicio del segmento)
            // y la duración estimada.
            // Esto es útil para la UI incluso antes de que el TTS comience a hablar.
            segmentProgressListener(
                0f,
                formatDuration(0L),
                formatDuration(currentSegmentEstimatedDurationMs)
            )

            // SimpleBasePlayer no tiene un concepto directo de "duración del item actual" que
            // podamos actualizar dinámicamente de esta manera para su Timeline interna una vez
            // que ya está construida. La Timeline se basa en los MediaItems iniciales.
            // Esta estimación es principalmente para nuestra lógica de UI/progreso.
            // Si quisiéramos que la Timeline de SBP refleje esto, necesitaríamos una Timeline personalizada
            // y una forma de invalidarla, lo cual es más complejo.
            // Por ahora, esta estimación es para uso interno y callbacks de progreso.
        }
    }

    /**
     * Se llama cuando un segmento se completa (ya sea por TTS onDone o porque se saltó).
     * Decide si avanzar al siguiente segmento o finalizar la reproducción.
     */
    private fun handleSegmentCompletion(wasSkipped: Boolean) {
        mainHandler.post {
            Timber.tag(TAG)
                .d("handleSegmentCompletion: Segmento $currentMediaItemIndex completado/saltado (wasSkipped: $wasSkipped).")
            currentUtteranceId = null // Limpiar el ID del utterance que acaba de terminar

            val currentIdx = currentMediaItemIndex
            if (currentIdx == C.INDEX_UNSET) {
                Timber.tag(TAG)
                    .e("handleSegmentCompletion: currentMediaItemIndex es UNSET. No se puede proceder.")
                // Esto es inesperado. Podríamos ir a IDLE con error.
                updateReportedPlayerState(
                    playbackStateUpdate = Player.STATE_IDLE,
                    playerErrorUpdate = PlaybackException(
                        "Segment completion with unset index.",
                        null,
                        PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
                    )
                )
                return@post
            }

            // Notificar progreso del 100% para el segmento que acaba de terminar (si no fue un skip problemático)
            if (!wasSkipped || (wasSkipped && currentSegmentEstimatedDurationMs == 0L)) { // Si fue un skip de segmento vacío, está bien.
                segmentProgressListener(
                    1f,
                    formatDuration(currentSegmentEstimatedDurationMs),
                    formatDuration(currentSegmentEstimatedDurationMs)
                )
            }


            val nextMediaItemIndex = currentIdx + 1
            if (nextMediaItemIndex < mediaItemCount) {
                Timber.tag(TAG)
                    .d("handleSegmentCompletion: Avanzando al siguiente segmento $nextMediaItemIndex.")
                // SimpleBasePlayer no avanza automáticamente el currentMediaItemIndex aquí.
                // Necesitamos decirle que busque el siguiente item.
                // La llamada a seekTo() internamente debería actualizar el currentMediaItemIndex
                // y luego, si playWhenReady es true, debería desencadenar la reproducción del nuevo item.
                seekToInternal(
                    nextMediaItemIndex,
                    C.TIME_UNSET
                ) // C.TIME_UNSET para ir al inicio del item

                // 'seekToInternal' debería eventualmente llamar a onPositionDiscontinuity o
                // onMediaItemTransition. Si 'intendedPlayWhenReady' es true,
                // la lógica en handleSetPlayWhenReady o handlePrepare (si el estado cambia)
                // o un listener de onMediaItemTransition debería llamar a speakSegmentAtIndex.

                // Para ser más explícito, después de que SBP procese el seek y actualice el índice:
                // (Esto podría ser redundante si SBP ya lo hace, pero asegura el flujo)
                // mainHandler.post { // Postear de nuevo para asegurar que SBP haya procesado el seek
                // if (intendedPlayWhenReady && currentMediaItemIndex == nextMediaItemIndex) {
                // estimateDurationAndResetProgressForSegment(currentMediaItemIndex)
                // speakSegmentAtIndex(currentMediaItemIndex)
                // }
                // }
                // Por ahora, confiamos en que el flujo de SBP después de seekToInternal
                // y la lógica en handleSetPlayWhenReady/handlePrepare manejen el inicio del habla.
                // Si `seekToInternal` actualiza el índice y `intendedPlayWhenReady` es true,
                // `SimpleBasePlayer` debería intentar reproducir, lo que podría volver a llamar
                // a `handleSetPlayWhenReady` o similar.

            } else {
                Timber.tag(TAG)
                    .i("handleSegmentCompletion: Todos los segmentos completados. Finalizando reproducción.")
                updateReportedPlayerState(playbackStateUpdate = Player.STATE_ENDED)
                // No es necesario llamar a textToSpeechEngine.stop() aquí si onDone fue la causa,
                // ya que el habla para ese utterance ya terminó.
            }
        }
    }

    // En TtsPlayerMedia.kt

// ... (otro código de la clase, incluyendo updateReportedPlayerState) ...

    /**
     * Realiza una operación de búsqueda interna a un MediaItem y posición específicos.
     * Este método llama al `seekTo` de SimpleBasePlayer, que a su vez
     * desencadenará `handleSeekTo` si la posición o el ítem cambian significativamente.
     *
     * @param mediaItemIndex El índice del MediaItem (segmento) al que buscar.
     * @param positionMs La posición en milisegundos dentro de ese MediaItem.
     *                   Para TTS, esto es usualmente C.TIME_UNSET o 0, ya que
     *                   cada segmento se reproduce desde el inicio.
     */
    private fun seekToInternal(mediaItemIndex: Int, positionMs: Long) {
        mainHandler.post { // Asegura la ejecución en el hilo del reproductor
            Timber.tag(TAG).i(
                "seekToInternal: Solicitando búsqueda a mediaItemIndex=$mediaItemIndex, positionMs=$positionMs. " +
                        "Current SBP State: $playbackState, Current Index: $currentMediaItemIndex"
            )

            // Validaciones básicas (aunque SimpleBasePlayer también las hará)
            if (mediaItemIndex < 0 || mediaItemIndex >= mediaItemCount) {
                Timber.tag(TAG)
                    .e("seekToInternal: Intento de búsqueda a índice de MediaItem inválido: $mediaItemIndex. MediaItemCount: $mediaItemCount")
                // Podríamos reportar un error o simplemente no hacer nada y dejar que SBP lo maneje.
                // Si esto ocurre, es probable un error lógico en otra parte (ej. en handleSegmentCompletion).
                updateReportedPlayerState(
                    playerErrorUpdate = PlaybackException(
                        "Seek to invalid media item index $mediaItemIndex", null,
                        PlaybackException.ERROR_CODE_BAD_VALUE // O ERROR_CODE_SEEK_UNAVAILABLE
                    ),
                    playbackStateUpdate = Player.STATE_IDLE // O mantener el estado actual si es recuperable
                )
                return@post
            }

            // Detener el habla actual del TTS antes de realizar la búsqueda,
            // ya que vamos a cambiar de posición o de segmento.
            // handleSeekTo también hace esto, pero hacerlo aquí asegura que se detenga
            // incluso si SBP decide que la búsqueda no es "suficiente" para llamar a handleSeekTo
            // (aunque para cambio de índice, siempre debería llamarlo).
            if (isTtsInitialized && textToSpeechEngine.isSpeaking) {
                val stopResult = textToSpeechEngine.stop()
                Timber.tag(TAG)
                    .d("seekToInternal: TTS detenido (resultado: $stopResult) antes de la búsqueda.")
            }
            currentUtteranceId = null // Limpiar el ID del utterance actual

            // Llamar al método seekTo() de la interfaz Player (implementado por SimpleBasePlayer).
            // Esto iniciará el proceso de búsqueda en SimpleBasePlayer.
            // SBP determinará si el índice o la posición han cambiado lo suficiente.
            // Si es así, SBP actualizará su estado interno (currentMediaItemIndex, currentPositionMs)
            // y luego llamará a nuestro `handleSeekTo()`.
            // Después de `handleSeekTo()`, SBP notificará a los listeners
            // (onMediaItemTransition, onPositionDiscontinuity).
            this@TtsPlayerMedia.seekTo(mediaItemIndex, positionMs)

            // No llamamos a speakSegmentAtIndex() directamente aquí.
            // La lógica de reanudación del habla después de una búsqueda debe estar en:
            // 1. `handleSeekTo()`: Si la búsqueda es significativa.
            // 2. O como parte del flujo de `onMediaItemTransition` o `onPositionDiscontinuity`
            //    si `playWhenReady` es true y el reproductor está en `STATE_READY`.
            //    SimpleBasePlayer, después de una búsqueda y si `playWhenReady` es true,
            //    intentará continuar la reproducción, lo que debería llevar a que se hable el nuevo segmento.
            //    Nuestra implementación de `handleSeekTo` ya considera iniciar el habla si es necesario.
            Timber.tag(TAG)
                .d("seekToInternal: Llamada a this.seekTo($mediaItemIndex, $positionMs) realizada. SBP manejará el resto.")
        }
    }

    // (seekToDefaultPositionInternal ya estaba definida, la incluyo por completitud de las utilidades de búsqueda)

    // Función de utilidad para formatear duraciones (puedes moverla a un objeto/clase de utilidad)
    private fun formatDuration(millis: Long): String {
        if (millis == C.TIME_UNSET || millis < 0) return "00:00"
        val totalSeconds = millis / 1000
        val seconds = totalSeconds % 60
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }

    // En TtsPlayerMedia.kt

    // --- UtteranceProgressListener ---
    private val ttsUtteranceListener = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            mainHandler.post {
                Timber.tag(TAG)
                    .d("UtteranceProgressListener: onStart - Utterance ID: $utteranceId. Current SBP State: $playbackState")
                if (utteranceId != currentUtteranceId) {
                    Timber.tag(TAG)
                        .w("UtteranceProgressListener: onStart - ID de utterance no coincide ($utteranceId vs $currentUtteranceId). Ignorando.")
                    return@post
                }

                // El habla ha comenzado para el utterance actual.
                // SimpleBasePlayer debería estar en READY o BUFFERING.
                // Si estábamos en BUFFERING (quizás esperando al TTS), ahora podríamos estar reproduciendo.
                // Si ya estábamos en READY, esto confirma que el habla ha comenzado.

                // SimpleBasePlayer actualiza 'isPlaying' basado en playWhenReady y si puede reproducir.
                // No necesitamos forzar 'isPlaying' aquí.
                // Si playWhenReady es true, SBP debería marcar isPlaying = true.
                // El TtsServiceHandler (como Player.Listener) recibirá onIsPlayingChanged(true).

                // Podríamos actualizar el estado a BUFFERING si el TTS tardara mucho en empezar,
                // pero 'speakSegmentAtIndex' ya se llamó. Si SBP está en READY,
                // y playWhenReady es true, SBP ya debería considerar que está reproduciendo.

                // Notificar progreso inicial (0%) si no se hizo ya en estimateDuration.
                // segmentProgressListener(0f, formatDuration(0L), formatDuration(currentSegmentEstimatedDurationMs))
                // Esto ya se hace en estimateDurationAndResetProgressForSegment.
            }
        }

        override fun onDone(utteranceId: String?) {
            mainHandler.post {
                Timber.tag(TAG)
                    .d("UtteranceProgressListener: onDone - Utterance ID: $utteranceId. Current SBP State: $playbackState")
                if (utteranceId != currentUtteranceId) {
                    Timber.tag(TAG)
                        .w("UtteranceProgressListener: onDone - ID de utterance no coincide ($utteranceId vs $currentUtteranceId). Ignorando.")
                    return@post
                }
                // El utterance actual ha terminado de hablar.
                handleSegmentCompletion(false) // false porque no fue un skip manual
            }
        }

        @Deprecated("Deprecated in Java", ReplaceWith("onError(utteranceId, errorCode)"))
        override fun onError(utteranceId: String?) {
            // Este método está obsoleto, pero algunos motores TTS más antiguos podrían llamarlo.
            mainHandler.post {
                Timber.tag(TAG)
                    .e("UtteranceProgressListener: onError (deprecated) - Utterance ID: $utteranceId. Current SBP State: $playbackState")
                if (utteranceId != null && utteranceId != currentUtteranceId) {
                    Timber.tag(TAG)
                        .w("UtteranceProgressListener: onError (deprecated) - ID de utterance no coincide ($utteranceId vs $currentUtteranceId). Ignorando.")
                    return@post
                }
                // Tratar como un error genérico del TTS para este utterance.
                handleTtsError(
                    utteranceId,
                    PlaybackException.ERROR_CODE_BAD_VALUE
                ) // Usa un código de error interno si es necesario
            }
        }

        override fun onError(utteranceId: String?, errorCode: Int) {
            mainHandler.post {
                Timber.tag(TAG)
                    .e("UtteranceProgressListener: onError - Utterance ID: $utteranceId, ErrorCode: $errorCode. Current SBP State: $playbackState")
                if (utteranceId != null && utteranceId != currentUtteranceId) {
                    Timber.tag(TAG)
                        .w("UtteranceProgressListener: onError - ID de utterance no coincide ($utteranceId vs $currentUtteranceId). Ignorando.")
                    return@post
                }
                handleTtsError(utteranceId, errorCode)
            }
        }

        override fun onStop(utteranceId: String?, interrupted: Boolean) {
            // Se llama cuando el habla se detiene explícitamente (textToSpeechEngine.stop())
            // o si un nuevo speak() con QUEUE_FLUSH interrumpe el actual.
            mainHandler.post {
                Timber.tag(TAG)
                    .d("UtteranceProgressListener: onStop - Utterance ID: $utteranceId, Interrupted: $interrupted. Current SBP State: $playbackState")
                if (utteranceId != null && utteranceId != currentUtteranceId) {
                    Timber.tag(TAG)
                        .w("UtteranceProgressListener: onStop - ID de utterance no coincide ($utteranceId vs $currentUtteranceId). Ignorando.")
                    return@post
                }

                // Si fue interrumpido (por un nuevo speak o un stop()), no necesariamente es el final de la lista de reproducción.
                // Si 'interrupted' es true, es probable que no queramos avanzar al siguiente segmento
                // a menos que la lógica de 'stop' o 'speak' ya lo esté manejando.
                // Si 'playWhenReady' se puso a false, handleSetPlayWhenReady ya llamó a textToSpeechEngine.stop().
                // Si se llamó a speakSegmentAtIndex para un nuevo segmento (QUEUE_FLUSH), este 'onStop'
                // es para el utterance anterior. El nuevo utterance tendrá su propio ciclo de vida.

                // Por lo general, no necesitamos hacer mucho aquí si la parada fue intencional
                // (por pausa o por un nuevo speak que limpia la cola).
                // El estado del reproductor (isPlaying=false) debería ser manejado por SimpleBasePlayer
                // en respuesta a playWhenReady=false o por la lógica que causó la interrupción.
                // Si fue un stop() por pausa, no llamamos a handleSegmentCompletion.
            }
        }

        // onAudioAvailable es menos común de usar para la lógica principal del reproductor,
        // más para guardar audio o análisis.
        // override fun onAudioAvailable(utteranceId: String?, audio: ByteArray?) {
        //    super.onAudioAvailable(utteranceId, audio)
        // }
    }

    // --- Manejo de Errores del TTS ---
    private fun handleTtsError(utteranceId: String?, ttsErrorCode: Int) {
        Timber.tag(TAG)
            .e("handleTtsError: Utterance ID: $utteranceId, TTS ErrorCode: $ttsErrorCode")
        currentUtteranceId = null // Limpiar el ID del utterance fallido

        // Mapear códigos de error de TTS a PlaybackException si es posible, o usar uno genérico.
        val playbackErrorCode = when (ttsErrorCode) {
            TextToSpeech.ERROR_SYNTHESIS -> PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
            TextToSpeech.ERROR_SERVICE -> PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
            TextToSpeech.ERROR_OUTPUT -> PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED
            TextToSpeech.ERROR_NETWORK -> PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED
            TextToSpeech.ERROR_NETWORK_TIMEOUT -> PlaybackException.ERROR_CODE_TIMEOUT
            TextToSpeech.ERROR_NOT_INSTALLED_YET -> PlaybackException.ERROR_CODE_INVALID_STATE
            else -> PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED // Un código genérico para errores de TTS
        }

        updateReportedPlayerState(
            playerErrorUpdate = PlaybackException(
                "TTS Error for utterance $utteranceId, TTS code: $ttsErrorCode",
                null,
                playbackErrorCode
            ),
            playbackStateUpdate = Player.STATE_IDLE // O ENDED si no se puede recuperar y no hay más items.
            // IDLE es más seguro para indicar un problema.
        )
        // No intentamos avanzar al siguiente segmento automáticamente en caso de error,
        // el reproductor entra en estado de error.
    }

    // En TtsPlayerMedia.kt

    // --- Sobrescritura de otros métodos de SimpleBasePlayer ---

    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    fun handleSeekTo(
        mediaItemIndex: Int,
        positionMs: Long,
        seekPositionInCurrentMediaItem: Boolean
    ): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).d(
                "handleSeekTo: mediaItemIndex=$mediaItemIndex, positionMs=$positionMs, " +
                        "seekInCurrent=$seekPositionInCurrentMediaItem, " +
                        "currentMediaItemIndex (antes)=${this.currentMediaItemIndex}, " + // Usar this. para claridad
                        "intendedPlayWhenReady=${this.intendedPlayWhenReady}, " +
                        "playbackState=$playbackState"
            )

            // Validar el índice del media item
            // SimpleBasePlayer ya realiza validaciones y puede lanzar IllegalSeekPositionException.
            // No necesitamos duplicar esa validación aquí a menos que tengamos lógica adicional.
            // if (mediaItemIndex < 0 || mediaItemIndex >= mediaItemCount) { ... }


            val oldMediaItemIndex =
                this.currentMediaItemIndex // El índice antes de que SBP lo actualice
            // SimpleBasePlayer actualizará this.currentMediaItemIndex y this.currentPositionMs
            // internamente ANTES de que este método retorne (o antes de que el futuro se complete).
            // Luego, notificará a los listeners (onPositionDiscontinuity, onMediaItemTransition).

            // Detener el habla actual, ya que estamos buscando una nueva posición/item.
            if (textToSpeechEngine.isSpeaking) {
                val stopResult = textToSpeechEngine.stop()
                if (stopResult == TextToSpeech.SUCCESS) {
                    Timber.tag(TAG).d("handleSeekTo: TTS detenido (SUCCESS) debido a la búsqueda.")
                } else {
                    Timber.tag(TAG)
                        .e("handleSeekTo: TTS stop FAILED ($stopResult) debido a la búsqueda.")
                }
            }
            currentUtteranceId = null // Limpiar el ID del utterance, ya que se detuvo o cambiará

            // La lógica de SimpleBasePlayer se encarga de actualizar el índice y la posición.
            // No necesitamos llamar a super.seekTo() aquí, ya que este método *maneja* la búsqueda.

            // Si el índice del MediaItem realmente cambió (o estamos forzados a tratarlo como un nuevo inicio)
            // y el nuevo índice es válido, preparamos el nuevo segmento.
            // SBP ya habrá actualizado currentMediaItemIndex.
            val newMediaItemIndex =
                getState().currentMediaItemIndex // Obtener el índice actualizado por SBP

            if (oldMediaItemIndex != newMediaItemIndex || seekPositionInCurrentMediaItem) {
                if (newMediaItemIndex >= 0 && newMediaItemIndex < mediaItemCount) {
                    Timber.tag(TAG)
                        .d("handleSeekTo: El índice cambió o se forzó reinicio. Estimando duración para el nuevo índice: $newMediaItemIndex")
                    estimateDurationAndResetProgressForSegment(newMediaItemIndex)
                }
            }

            // Si playWhenReady es true, y el reproductor está en un estado que permite la reproducción (READY),
            // el flujo normal de SBP debería hacer que se llame a speakSegmentAtIndex para el nuevo índice.
            // Esto ocurre porque SBP, después de una búsqueda exitosa y si playWhenReady=true,
            // intentará continuar la reproducción. Esto puede implicar una re-evaluación que
            // indirectamente llame a handleSetPlayWhenReady o directamente inicie la reproducción
            // si el estado es READY.
            // Si el estado es READY y playWhenReady es true, y el índice es válido,
            // speakSegmentAtIndex debería ser llamado.
            if (this.intendedPlayWhenReady && getState().playbackState == Player.STATE_READY) {
                if (newMediaItemIndex >= 0 && newMediaItemIndex < mediaItemCount) {
                    Timber.tag(TAG)
                        .d("handleSeekTo: Búsqueda procesada. intendedPlayWhenReady es true y estado READY. Solicitando hablar nuevo índice $newMediaItemIndex.")
                    // Es importante que SBP haya actualizado completamente su estado antes de llamar a speak.
                    // El post al handler ya ayuda a secuenciar.
                    speakSegmentAtIndex(newMediaItemIndex)
                } else {
                    Timber.tag(TAG)
                        .d("handleSeekTo: Búsqueda procesada, intendedPlayWhenReady es true y estado READY, pero nuevo índice $newMediaItemIndex es inválido.")
                }
            } else {
                Timber.tag(TAG)
                    .d("handleSeekTo: Búsqueda procesada. No se inicia habla inmediatamente (intendedPlayWhenReady=${this.intendedPlayWhenReady} o estado no es READY).")
            }
        }
        return Futures.immediateFuture {}
    }


    fun getStatee(
        oldState: State, // El estado anterior de SimpleBasePlayer
        builder: State.Builder // El builder para construir el nuevo estado
    ): State {
        // Este método es crucial para que SimpleBasePlayer construya su estado
        // y lo informe a los listeners (onTimelineChanged, onPlaybackStateChanged, etc.).

        // 1. Configurar Comandos Disponibles
        val commandsBuilder = Player.Commands.Builder()
            .add(Player.COMMAND_PLAY_PAUSE)
            .add(Player.COMMAND_STOP) // Asumimos que implementaremos handleStop
            .add(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            .add(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            // .add(Player.COMMAND_SEEK_BACK) // Añadir si implementas handleSeekBack
            // .add(Player.COMMAND_SEEK_FORWARD) // Añadir si implementas handleSeekForward
            .add(Player.COMMAND_SET_SPEED_AND_PITCH) // Ya que implementamos handleSetPlaybackSpeed
        // .add(Player.COMMAND_SET_REPEAT_MODE) // Añadir si se soporta funcionalmente
        // .add(Player.COMMAND_SET_SHUFFLE_MODE) // Añadir si se soporta funcionalmente
        // .add(Player.COMMAND_GET_TEXT) // Si tuvieras letras/subtítulos asociados

        // Habilitar/deshabilitar comandos basados en el estado actual
        if (mediaItemCount > 0) {
            commandsBuilder.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            commandsBuilder.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
        } else {
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            // No se puede reproducir/pausar si no hay items y no estamos listos/bufferizando
            if (oldState.playbackState == Player.STATE_IDLE && !oldState.playWhenReady) {
                commandsBuilder.remove(Player.COMMAND_PLAY_PAUSE)
            }
        }
        // Si el TTS no está inicializado, muchos comandos podrían no tener sentido.
        if (!isTtsInitialized) {
            commandsBuilder.remove(Player.COMMAND_PLAY_PAUSE)
            commandsBuilder.remove(Player.COMMAND_SET_SPEED_AND_PITCH)
            // Considera qué otros comandos deshabilitar
        }

        builder.setAvailableCommands(commandsBuilder.build())

        // 2. Configurar Modos de Repetición y Aleatorio
        // Aunque no los implementemos funcionalmente para cambiar el orden del TTS,
        // SBP necesita saber sus valores.
        builder.setRepeatMode(Player.REPEAT_MODE_OFF) // O el valor actual si lo almacenas y gestionas
        builder.setShuffleModeEnabled(false) // O el valor actual

        // Continuaremos con la configuración de la Timeline y otros aspectos de State en la siguiente parte.
        // Por ahora, esto es un buen comienzo para getState.
        // SimpleBasePlayer completará el resto del estado (como playWhenReady, playbackState,
        // currentMediaItemIndex, currentPositionMs, etc.) basado en sus propiedades internas
        // que nosotros actualizamos a través de updateReportedPlayerState() y los métodos handle*().

        return builder.build() // Devuelve el estado construido
    }

    // En TtsPlayerMedia.kt

// ... (código anterior incluyendo handleSeekTo y la configuración de comandos y modos en getState) ...

    fun getStateeee(
        oldState: State, // El estado anterior de SimpleBasePlayer
        builder: State.Builder // El builder para construir el nuevo estado
    ): State {
        // 1. Configurar Comandos Disponibles (ya cubierto en Parte 7.1)
        // ... (código de comandosBuilder.build() y builder.setAvailableCommands()) ...
        // Lo copio aquí para completitud de este fragmento específico de getState
        val commandsBuilder = Player.Commands.Builder()
            .add(Player.COMMAND_PLAY_PAUSE)
            .add(Player.COMMAND_STOP)
            .add(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            .add(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            .add(Player.COMMAND_SET_SPEED_AND_PITCH)

        if (mediaItemCount > 0) {
            commandsBuilder.add(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            commandsBuilder.add(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
        } else {
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_MEDIA_ITEM)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
        }
        if (!isTtsInitialized || oldState.playerError != null) {
            commandsBuilder.remove(Player.COMMAND_PLAY_PAUSE)
            commandsBuilder.remove(Player.COMMAND_SET_SPEED_AND_PITCH)
            commandsBuilder.remove(Player.COMMAND_SEEK_TO_DEFAULT_POSITION)
            // ... (remover otros comandos según sea necesario) ...
            commandsBuilder.remove(Player.COMMAND_STOP)
        } else if (oldState.playbackState == Player.STATE_IDLE && mediaItemCount == 0 && !oldState.playWhenReady) {
            commandsBuilder.remove(Player.COMMAND_PLAY_PAUSE)
        }
        builder.setAvailableCommands(commandsBuilder.build())

        // 2. Configurar Modos de Repetición y Aleatorio (ya cubierto en Parte 7.1)
        builder.setRepeatMode(Player.REPEAT_MODE_OFF) // O el valor que estés gestionando
        builder.setShuffleModeEnabled(false) // O el valor que estés gestionando


        // 3. Configurar la Timeline
        // SimpleBasePlayer maneja la Timeline internamente si le pasamos MediaItems
        // a través de `super.setMediaItems()`. Cuando llamamos a `setMediaItems()`,
        // SBP crea o actualiza su Timeline (generalmente una concatenación de
        // SinglePeriodTimeline si no se especifica una Timeline personalizada).
        // Por lo tanto, aquí no necesitamos construirla explícitamente A MENOS QUE
        // queramos una Timeline muy personalizada (ej. con duraciones que obtenemos
        // de forma asíncrona o que son dinámicas).
        //
        // Si `oldState.timeline` ya refleja la Timeline correcta (porque `setMediaItems`
        // la actualizó y SBP la propagó a `oldState`), no es necesario hacer `builder.setTimeline()`
        // aquí, ya que SBP usará la `oldState.timeline` si no se establece una nueva.
        //
        // Si en algún momento necesitas invalidar y reconstruir la Timeline desde cero
        // aquí (lo cual es menos común si `setMediaItems` es tu fuente de verdad),
        // entonces sí llamarías a `builder.setTimeline(tuNuevaTimeline)`.
        //
        // Para un reproductor TTS donde cada segmento es un MediaItem, la Timeline
        // generada por SBP a partir de `setMediaItems(List<MediaItem>)` suele ser suficiente.
        // La duración de cada "ventana" en la Timeline será C.TIME_UNSET si los
        // MediaItems no tienen una duración específica.

        // Ejemplo: Si quisieras forzar una Timeline vacía si no hay items:
        // if (mediaItemCount == 0 && oldState.timeline.isEmpty()) {
        // builder.setTimeline(Timeline.EMPTY);
        // }
        // Pero SBP debería manejar esto correctamente.

        // 4. Otros parámetros del estado que SBP maneja o nosotros influenciamos:
        //    - playWhenReady: SBP lo toma de su propiedad interna, que actualizamos en handleSetPlayWhenReady.
        //    - playbackState: SBP lo toma de su propiedad interna, que actualizamos con updateReportedPlayerState.
        //    - playerError: Igual que playbackState.
        //    - currentMediaItemIndex, currentPositionMs: SBP los actualiza internamente y con handleSeekTo.
        //    - totalBufferedDurationMs, bufferedPositionMs: Para TTS, el concepto de buffer es diferente.
        //      Podríamos dejarlo como 0 o C.TIME_UNSET, o intentar mapearlo si tiene sentido.
        //      SBP podría poner valores por defecto.
        //    - currentAdGroupIndex, currentAdIndexInAdGroup, isPlayingAd: No aplicable para TTS simple.

        // 5. Configurar `isLoading`
        // `isLoading` debe ser true si el reproductor está en estado BUFFERING y no hay error.
        // SBP podría hacerlo por defecto, pero ser explícito es bueno.
        val isLoading =
            oldState.playbackState == Player.STATE_BUFFERING && oldState.playerError == null
        builder.setIsLoading(isLoading)

        // 6. Duración del MediaItem Actual (`currentMediaItemDurationMs`)
        // SBP obtiene esto de la Timeline para el `currentMediaItemIndex`.
        // Si los MediaItems en tu Timeline tienen duración `C.TIME_UNSET` (común para TTS
        // donde la duración no se conoce de antemano con precisión para Media3),
        // entonces `currentMediaItemDurationMs` será `C.TIME_UNSET`.
        // Nuestra `currentSegmentEstimatedDurationMs` es para nuestra lógica de UI/progreso
        // y no se refleja directamente en la Timeline de SBP a menos que construyamos
        // una Timeline personalizada muy avanzada.

        // SBP se encarga de la mayoría de los demás campos usando `oldState` como base
        // y aplicando los cambios que hemos hecho a través de los métodos `handle*`
        // y `updateReportedPlayerState`.

        return builder.build() // Devuelve el estado construido
    }

    // En TtsPlayerMedia.kt

// ... (código anterior, incluyendo la Parte 7.2.1 con getState) ...

    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handleSetRepeatMode(repeatMode: Int): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).d("handleSetRepeatMode: repeatMode=$repeatMode")
            // SimpleBasePlayer almacena el valor de repeatMode internamente.
            // Para un reproductor TTS, la implementación funcional de la repetición
            // (repetir un segmento o toda la lista) requeriría lógica adicional
            // en handleSegmentCompletion o similar.
            // Si no implementas la lógica funcional, el reproductor simplemente
            // recordará el modo, pero no actuará sobre él.
            //
            // Si quisieras que el estado refleje inmediatamente este cambio a través de
            // una notificación onRepeatModeChanged, SBP debería encargarse de ello
            // al cambiar su estado interno. No es necesario llamar a updateReportedPlayerState()
            // solo por esto, a menos que tu lógica de getState dependa de este valor
            // de una manera que SBP no pueda inferir.
        }
        return Futures.immediateFuture {}
    }

    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).d("handleSetShuffleModeEnabled: shuffleModeEnabled=$shuffleModeEnabled")
            // Similar al modo de repetición, SimpleBasePlayer almacena este valor.
            // Implementar un modo aleatorio funcional para segmentos de TTS
            // implicaría reordenar `textSegments` y los `MediaItem`s correspondientes
            // cuando se establece el contenido o cuando se activa el modo aleatorio.
            // Por ahora, solo se registra el estado.
        }
        return Futures.immediateFuture {}
    }

    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    fun handleSetPlaybackSpeed(speed: Float): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).d("handleSetPlaybackSpeed: speed=$speed")
            if (!isTtsInitialized) {
                Timber.tag(TAG)
                    .w("handleSetPlaybackSpeed: TTS no inicializado. No se puede establecer velocidad.")
                // Podrías almacenar la velocidad deseada y aplicarla en onInit si es necesario.
                return@post
            }

            // El motor TTS de Android espera un float donde 1.0 es la velocidad normal.
            // Puedes añadir validaciones si el motor TTS tiene límites específicos.
            // Por ejemplo, algunos motores podrían no aceptar 0 o valores muy altos.
            // float ttsRate = Math.max(0.1f, speed); // Ejemplo de clamping mínimo

            val result = textToSpeechEngine.setSpeechRate(speed)
            if (result == TextToSpeech.SUCCESS) {
                Timber.tag(TAG).i("Velocidad de TTS (setSpeechRate) establecida a: $speed")
                // SimpleBasePlayer almacena el 'speed' en sus PlaybackParameters
                // y notificará onPlaybackParametersChanged.
                // No es necesario llamar a updateReportedPlayerState() aquí.
            } else {
                Timber.tag(TAG)
                    .e("Error al establecer velocidad de TTS (setSpeechRate) a: $speed, resultado TTS: $result")
                // Podrías considerar reportar un error al Player si esto es crítico,
                // aunque setSpeechRate raramente falla si el motor está inicializado.
                // updateReportedPlayerState(playerErrorUpdate = PlaybackException(...))
            }
        }
        return Futures.immediateFuture {}
    }

    // En TtsPlayerMedia.kt

// ... (código anterior) ...

    /**
     * Maneja la acción de detener la reproducción.
     * SimpleBasePlayer llama a esto cuando se invoca player.stop().
     */
    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handleStop(): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).d("handleStop: Deteniendo la reproducción.")

            // 1. Detener el motor TTS
            if (isTtsInitialized && textToSpeechEngine.isSpeaking) {
                textToSpeechEngine.stop()
            }
            currentUtteranceId = null

            // 2. Resetear el estado del reproductor relacionado con la posición.
            // SimpleBasePlayer se encargará de cambiar el estado a IDLE
            // y resetear la posición a la inicial del item actual o a C.INDEX_UNSET
            // si se llama a clearMediaItems después o como parte de stop().
            // No necesitamos resetear currentMediaItemIndex aquí explícitamente
            // a menos que queramos un comportamiento específico no cubierto por SBP.
            // SBP, después de stop(), pondrá el estado en Player.STATE_IDLE.

            // 3. Limpiar recursos si es necesario (aunque 'release' es para la limpieza final).
            // Por ahora, solo detenemos el habla.

            // SBP se encarga de actualizar el estado a Player.STATE_IDLE.
            // No es necesario llamar a updateReportedPlayerState(playbackStateUpdate = Player.STATE_IDLE)
            // explícitamente aquí, ya que SBP lo hará.
        }
        return Futures.immediateFuture {}
    }

    /**
     * Libera los recursos del reproductor.
     * SimpleBasePlayer llama a esto cuando se invoca player.release().
     */
    @SuppressLint("RestrictedApi") // Para Futures.immediateFuture
    override fun handleRelease(): ListenableFuture<() -> Unit> {
        mainHandler.post {
            Timber.tag(TAG).i("handleRelease: Liberando recursos de TtsPlayerMedia.")

            // 1. Detener y apagar el motor TextToSpeech
            if (this::textToSpeechEngine.isInitialized) { // Verifica si lateinit fue inicializado
                if (textToSpeechEngine.isSpeaking) {
                    textToSpeechEngine.stop()
                }
                textToSpeechEngine.shutdown()
                Timber.tag(TAG).d("handleRelease: Motor TTS apagado.")
            }
            isTtsInitialized = false
            currentUtteranceId = null

            // 2. Limpiar listas y referencias
            textSegments = emptyList()
            pendingSegmentsToSet = null
            pendingMediaItemsToSet = null

            // 3. Quitar callbacks del handler para evitar memory leaks
            mainHandler.removeCallbacksAndMessages(null)

            // SimpleBasePlayer se encarga de su propia limpieza interna.
            // No es necesario llamar a updateReportedPlayerState aquí, ya que el reproductor se está liberando.
            Timber.tag(TAG).i("handleRelease: Recursos liberados.")
        }
        // El futuro se completa cuando las tareas en el handler se ejecutan.
        // Para una limpieza más robusta, podrías usar un CountDownLatch si necesitas
        // esperar a que shutdown() complete alguna operación asíncrona,
        // pero para TTS shutdown() suele ser rápido.
        return Futures.immediateFuture {}
    }


    // --- Funciones de utilidad internas (si no las has movido) ---
    private fun seekToDefaultPositionInternal() {
        // Llama a la lógica de SimpleBasePlayer para buscar la posición por defecto.
        // Esto internamente llamará a handleSeekTo si es necesario.
        // SBP se encargará de actualizar el currentMediaItemIndex y la posición.
        Timber.tag(TAG)
            .d("seekToDefaultPositionInternal: Solicitando búsqueda a la posición por defecto.")
        seekToDefaultPosition() // Este es el método de la interfaz Player que SBP implementa
    }

    // (Asegúrate que formatDuration y otras utilidades estén definidas si no lo están ya)
    // private fun formatDuration(millis: Long): String { ... }

    // En TtsPlayerMedia.kt

// ... (otro código de la clase) ...

    private fun getDefaultPlayerInitialState(): State { /* ... (igual que antes) ... */
        return State.Builder()
            .setAvailableCommands(
                Player.Commands.Builder().addAll(
                    COMMAND_PLAY_PAUSE,
                    COMMAND_STOP,
                    COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
                    COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM,
                    COMMAND_SEEK_TO_MEDIA_ITEM,
                    COMMAND_GET_CURRENT_MEDIA_ITEM,
                    COMMAND_GET_METADATA,
                    COMMAND_GET_TIMELINE
                ).build()
            )
            .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .setPlaybackState(Player.STATE_IDLE)
            // .setPlaylistMetadata(this.playlistMetadata)

            /*.setPlaylistMetadata(
                MediaMetadata.Builder()
                    .setTitle("TTS Player")
                    .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                    .build()
            )
            .setCurrentMediaItemIndex(0)*/
            .build()
    }


    /**
     * Actualiza el estado del reproductor e invalida el estado de SimpleBasePlayer
     * para notificar a los listeners.
     * Este método es una utilidad interna para centralizar los cambios de estado.
     *
     * @param playbackStateUpdate El nuevo estado de reproducción. Si es null, no se cambia.
     * @param playerErrorUpdate El nuevo error. Si es Player.DISCONTINUITY_REASON_INTERNAL (valor sentinela),
     *                          no se cambia. Si es null, se limpia cualquier error existente.
     * @param clearError Si es true y playerErrorUpdate es DISCONTINUITY_REASON_INTERNAL, limpia el error.
     *                   (Este parámetro es para hacer más explícita la limpieza de errores).
     */

    private fun updateReportedPlayerState(
        playbackStateUpdate: @Player.State Int? = null,
        playerErrorUpdate: PlaybackException? = UpdateStateSentinel.NO_ERROR_CHANGE, // Usamos el sentinela
        clearError: Boolean = false
    ) {
        var stateNeedsInvalidation = false

        // 1. Actualizar PlaybackException internamente
        if (playerErrorUpdate != UpdateStateSentinel.NO_ERROR_CHANGE) {
            if (this.internalPlayerError != playerErrorUpdate) {
                this.internalPlayerError = playerErrorUpdate
                Timber.tag(TAG)
                    .d("updateReportedPlayerState: Internal player error will be: ${this.internalPlayerError}")
                stateNeedsInvalidation = true
            }
        } else if (clearError && this.internalPlayerError != null) {
            this.internalPlayerError = null
            Timber.tag(TAG).d("updateReportedPlayerState: Internal player error will be cleared.")
            stateNeedsInvalidation = true
        }

        // 2. Actualizar PlaybackState internamente
        if (playbackStateUpdate != null && this.internalPlaybackState != playbackStateUpdate) {
            this.internalPlaybackState = playbackStateUpdate
            Timber.tag(TAG)
                .d("updateReportedPlayerState: Internal playback state will be: ${this.internalPlaybackState}")
            stateNeedsInvalidation = true
        }

        // 3. Si algo cambió que afecta el estado, invalidar.
        if (stateNeedsInvalidation) {
            invalidateState() // Esto hará que SBP llame a nuestro override fun getState(...)
            Timber.tag(TAG)
                .d("updateReportedPlayerState: State invalidated due to internal changes.")
        } else {
            Timber.tag(TAG)
                .d("updateReportedPlayerState: No changes to internal state requiring invalidation.")
        }
    }

    // Objeto sentinela
    private object UpdateStateSentinel {
        // Un objeto único para diferenciar de un 'null' intencional (limpiar error)
        val NO_ERROR_CHANGE: PlaybackException? =
            PlaybackException("SENTINEL", null, PlaybackException.ERROR_CODE_UNSPECIFIED)
    }

} 