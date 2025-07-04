package org.deiverbum.app.feature.tts


import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.UnstableApi
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@SuppressLint("RestrictedApi")
@UnstableApi
class MinimalTtsPlayer @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("MainLooper") private val looper: Looper
) : SimpleBasePlayer(looper) {
    private var state =
        State.Builder()
            .setAvailableCommands(Player.Commands.Builder().addAllCommands().build())
            .setPlaylist(
                ImmutableList.of(
                    MediaItemData.Builder(/* uid= */ "First").build(),
                    MediaItemData.Builder(/* uid= */ "Second").build(),
                )
            )
            .setPlayWhenReady(true, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
            .build()

    private val TAG = "MinimalTtsPlayer"
    private val mainHandler: Handler = Handler(looper)

    private var localMediaItemTitles: MutableList<String> = mutableListOf()
    private var currentMinimalPlayerInternalIndex: Int = C.INDEX_UNSET

    // Estado que SimpleBasePlayer leerá a través de getState()
    private var sbpPlaybackState: Int = Player.STATE_IDLE
    private var sbpPlayWhenReady: Boolean = false
    private var sbpPlayerError: PlaybackException? = null
    private var sbpIsLoading: Boolean = false // Ejemplo si necesitas un estado de carga

    init {
        Timber.tag(TAG).i("MinimalTtsPlayer: Initialized")
        // SBP se inicializa a un estado por defecto.
        // La primera llamada a getState() establecerá el estado inicial de SBP.
        sbpPlaybackState = Player.STATE_IDLE; // Ya es el valor por defecto
        sbpPlayWhenReady = false;           // Ya es el valor por defecto
        sbpPlayerError = null;              // Ya es el valor por defecto
        sbpIsLoading = false;               // Ya es el valor por defecto
        invalidateState() // Para asegurar que SBP obtenga el estado inicial de nuestros getters.
    }

    companion object {
        val DEFAULT_COMMANDS: Player.Commands = Player.Commands.Builder()
            .addAll(
                Player.COMMAND_PLAY_PAUSE, Player.COMMAND_PREPARE, Player.COMMAND_STOP,
                Player.COMMAND_SEEK_TO_MEDIA_ITEM, Player.COMMAND_SET_MEDIA_ITEM,
                Player.COMMAND_GET_CURRENT_MEDIA_ITEM, Player.COMMAND_GET_TIMELINE,
                Player.COMMAND_GET_METADATA, Player.COMMAND_SEEK_BACK, Player.COMMAND_SEEK_FORWARD,
                Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM, Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM,
                Player.COMMAND_SET_SHUFFLE_MODE // Añade/quita según necesites
            ).build()
    }

    fun getAvailableCommandss(): Player.Commands {
        return DEFAULT_COMMANDS
    }

    override fun getState(): State {
        return state

        // SBP llama a esto para obtener el estado actual.
        // Construye el estado usando tus variables y las propiedades de SBP (Timeline, Index).
        //val currentSbpTimeline = this.currentTimeline // El Timeline que SBP gestiona
        val currentSbpIndex = this.currentMediaItemIndex // El índice que SBP gestiona
        //val currentSbpTimeline = this.currentTimeline // El Timeline que SBP gestiona
        //val currentSbpIndex = this.currentMediaItemIndex // El índice que SBP gestiona

        Timber.tag(TAG).v(
            "getState() - Building state: playbackState=$sbpPlaybackState, playWhenReady=$sbpPlayWhenReady, " +
                    "isLoading=$sbpIsLoading, error=$sbpPlayerError, "
            //+ "SBP's currentTimeline windowCount=${currentSbpTimeline.windowCount}, SBP's currentMediaItemIndex=$currentSbpIndex" // ¡REVISA ESTOS VALORES!
        )
        Timber.tag(TAG).v(
            "getState() - Building state: playbackState=$sbpPlaybackState, playWhenReady=$sbpPlayWhenReady, " +
                    "isLoading=$sbpIsLoading, error=$sbpPlayerError, "
            //+ "sbpTimelineWindows=${currentSbpTimeline.windowCount}, sbpIndex=$currentSbpIndex"
        )
        //return getDefaultPlayerInitialState()

        return State.Builder()
            .setAvailableCommands(getAvailableCommandss())
            .setPlaybackState(sbpPlaybackState)
            .setPlayWhenReady(
                sbpPlayWhenReady,
                PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST
            ) // O la razón apropiada
            .setPlayerError(sbpPlayerError)
            //.setTimeline(currentSbpTimeline)
            //setCurrentMediaItemIndex(currentSbpIndex)
            .setIsLoading(sbpIsLoading)
            // Valores por defecto o los que necesites para los siguientes:
            //.setSeekBackIncrementMs(C.DEFAULT_SEEK_BACK_INCREMENT_MS)
            //.setSeekForwardIncrementMs(C.DEFAULT_SEEK_FORWARD_INCREMENT_MS)
            //.setMaxSeekToPreviousPositionMs(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)
            // .setPlaybackParameters(PlaybackParameters.DEFAULT)
            // .setTrackSelectionParameters(TrackSelectionParameters.DEFAULT_WITHOUT_CONTEXT)
            // .setContentPositionMs(0) // SBP no usa esto activamente para reproductores sin duración
            // .setContentBufferedPositionMs(0)
            // .setTotalBufferedDurationMs(0)
            .build()
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

    // Renombrado para mayor claridad: actualiza tu estado local y luego invalida SBP
    private fun updateLocalStateAndInvalidateSbp(
        playbackStateUpdate: Int? = null,
        playWhenReadyUpdate: Boolean? = null,
        playerErrorUpdate: PlaybackException? = null,
        isLoadingUpdate: Boolean? = null,
        clearError: Boolean = false // Flag explícito para limpiar el error
    ) {
        var stateActuallyChanged = false

        playbackStateUpdate?.let {
            if (sbpPlaybackState != it) {
                sbpPlaybackState = it; stateActuallyChanged = true
                Timber.tag(TAG).d("updateLocalState: sbpPlaybackState -> $it")
            }
        }
        playWhenReadyUpdate?.let {
            if (sbpPlayWhenReady != it) {
                sbpPlayWhenReady = it; stateActuallyChanged = true
                Timber.tag(TAG).d("updateLocalState: sbpPlayWhenReady -> $it")
            }
        }
        isLoadingUpdate?.let {
            if (sbpIsLoading != it) {
                sbpIsLoading = it; stateActuallyChanged = true
                Timber.tag(TAG).d("updateLocalState: sbpIsLoading -> $it")
            }
        }

        if (clearError) {
            if (sbpPlayerError != null) {
                sbpPlayerError = null; stateActuallyChanged = true
                Timber.tag(TAG).d("updateLocalState: sbpPlayerError -> null (cleared)")
            }
        } else { // Solo actualiza el error si no se está limpiando explícitamente
            playerErrorUpdate?.let {
                // Compara si el nuevo error es diferente del actual.
                // Podrías necesitar una comparación más profunda si los errores pueden ser "iguales" pero instancias diferentes.
                if (sbpPlayerError?.message != it.message || sbpPlayerError?.errorCode != it.errorCode) {
                    sbpPlayerError = it; stateActuallyChanged = true
                    Timber.tag(TAG).d("updateLocalState: sbpPlayerError -> $it")
                }
            }
        }


        if (stateActuallyChanged) {
            Timber.tag(TAG)
                .i("updateLocalState: Local state components changed, calling invalidateState().")
            invalidateState() // Notificar a SBP para que llame a nuestro getState()
        } else {
            Timber.tag(TAG)
                .d("updateLocalState: No local state component change to report via invalidateState().")
        }
    }

    override fun handleSetMediaItems(
        mediaItems: MutableList<MediaItem>,
        startIndex: Int,
        startPositionMs: Long
    ): ListenableFuture<*> {
        Timber.tag(TAG)
            .i("handleSetMediaItems: Received ${mediaItems.size} items. Current SBP mediaItemCount BEFORE SBP processes: ${this.currentTimeline.windowCount}")

        localMediaItemTitles.clear()
        mediaItems.forEach { mediaItem ->
            mediaItem.mediaMetadata?.title?.toString()?.let { localMediaItemTitles.add(it) }
        }
        Timber.tag(TAG)
            .d("handleSetMediaItems: localMediaItemTitles populated with ${localMediaItemTitles.size} titles.")

        currentMinimalPlayerInternalIndex = if (localMediaItemTitles.isNotEmpty()) {
            if (startIndex >= 0 && startIndex < localMediaItemTitles.size) startIndex else 0
        } else {
            C.INDEX_UNSET
        }
        Timber.tag(TAG)
            .d("handleSetMediaItems: currentMinimalPlayerInternalIndex (internal logic) set to: $currentMinimalPlayerInternalIndex")

        // SBP es responsable de actualizar su Timeline y currentMediaItemIndex
        // basado en los 'mediaItems' que se le pasaron a su método setMediaItems (el método público).
        // Después de que este handleSetMediaItems retorne, SBP hará su trabajo interno.
        // Llamar a invalidateState() aquí (o posteado) es para asegurar que SBP
        // reevalúe su estado general una vez que haya tenido la oportunidad de procesar los ítems.
        // SBP llamará a nuestro getState() donde le proporcionaremos los valores actualizados
        // para playbackState, playWhenReady, etc., y SBP usará su propio Timeline y currentMediaItemIndex.
        mainHandler.post {
            Timber.tag(TAG)
                .d("handleSetMediaItems (posted): Calling invalidateState() to prompt SBP to re-query state after it processes media items.")
            invalidateState()
        }

        Timber.tag(TAG)
            .i("handleSetMediaItems: Exiting. SBP mediaItemCount AFTER SBP processes (will be visible in next getState()): ${this.currentTimeline.windowCount}")
        return Futures.immediateVoidFuture()
    }

    override fun handlePrepare(): ListenableFuture<*> {
        state =
            state
                .buildUpon()
                .setPlayerError(null)
                .setPlaybackState(if (state.timeline.isEmpty) STATE_ENDED else STATE_BUFFERING)
                .build()
        return Futures.immediateVoidFuture()
        Timber.tag(TAG)
            .i("handlePrepare. Current SBP mediaItemCount (from SBP's timeline): ${this.currentTimeline.windowCount}")
        // Limpiar cualquier error previo al preparar
        updateLocalStateAndInvalidateSbp(clearError = true)

        if (this.currentTimeline.windowCount > 0) { // Chequea el Timeline de SBP
            // Si hay ítems, el estado debería ser READY.
            // Si el índice actual de SBP es inválido, podría ser necesario ajustarlo aquí o en getState.
            // Por ahora, solo nos aseguramos de que el estado sea READY.
            updateLocalStateAndInvalidateSbp(
                playbackStateUpdate = Player.STATE_READY,
                isLoadingUpdate = false
            )
        } else {
            Timber.tag(TAG).w("handlePrepare: No media items in SBP's timeline. Setting to IDLE.")
            updateLocalStateAndInvalidateSbp(
                playbackStateUpdate = Player.STATE_IDLE,
                isLoadingUpdate = false
            )
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
        state =
            state
                .buildUpon()
                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                .build()
        return Futures.immediateVoidFuture()
        Timber.tag(TAG)
            .i("handleSetPlayWhenReady: $playWhenReady. SBP mediaItemCount: ${this.currentTimeline.windowCount}, Current local sbpPlaybackState: $sbpPlaybackState")

        // Actualiza la intención de playWhenReady y limpia errores previos que podrían impedir la reproducción.
        updateLocalStateAndInvalidateSbp(playWhenReadyUpdate = playWhenReady, clearError = true)

        if (playWhenReady) { // Intento de reproducir
            if (this.currentTimeline.windowCount == 0) {
                Timber.tag(TAG).w("handleSetPlayWhenReady: No media items. Cannot play.")
                // El estado de playWhenReady ya se actualizó, pero el playbackState debería ser IDLE.
                if (sbpPlaybackState != Player.STATE_IDLE) {
                    updateLocalStateAndInvalidateSbp(
                        playbackStateUpdate = Player.STATE_IDLE,
                        isLoadingUpdate = false
                    )
                }
            } else if (sbpPlaybackState == Player.STATE_IDLE && this.currentTimeline.windowCount > 0) {
                Timber.tag(TAG)
                    .w("handleSetPlayWhenReady: Player was IDLE but has items. Client should call prepare() before play().")
                // El cliente debería llamar a prepare() primero.
                // Reportar un error para indicar un uso incorrecto de la API.
                updateLocalStateAndInvalidateSbp(
                    playerErrorUpdate = PlaybackException(
                        "Player not prepared. Call prepare() before play().",
                        null,
                        PlaybackException.ERROR_CODE_INVALID_STATE
                    )
                )
            } else if (sbpPlaybackState == Player.STATE_READY) {
                Timber.tag(TAG)
                    .i("handleSetPlayWhenReady: Player is READY. Simulating playback start for SBP index ${this.currentMediaItemIndex}.")
                // Aquí iría la lógica de TTS: textToSpeech.speak(...)
                // Si la síntesis de TTS no es instantánea, podrías transicionar a BUFFERING:
                // updateLocalStateAndInvalidateSbp(playbackStateUpdate = Player.STATE_BUFFERING, isLoadingUpdate = true)
                // Y luego, en el callback onStart de TTS, pasar a READY:
                // updateLocalStateAndInvalidateSbp(playbackStateUpdate = Player.STATE_READY, isLoadingUpdate = false)
            } else if (sbpPlaybackState == Player.STATE_ENDED) {
                Timber.tag(TAG)
                    .i("handleSetPlayWhenReady: Player was ENDED. Consider seeking to start or next item to replay.")
                // Para reiniciar, el cliente podría necesitar llamar a seekToDefaultPosition() o seekTo(index, 0) y luego play().
                // Si se quiere reiniciar automáticamente, se podría añadir lógica aquí.
            } else if (sbpPlaybackState == Player.STATE_BUFFERING) {
                Timber.tag(TAG)
                    .d("handleSetPlayWhenReady: Player is already BUFFERING. Assuming playback will proceed.")
            }
        } else { // playWhenReady es false (PAUSA)
            Timber.tag(TAG).i("handleSetPlayWhenReady: Pause requested.")
            // Aquí iría la lógica de TTS: textToSpeech.stop()
            // Si estaba reproduciendo (READY o BUFFERING), y no está ya en ENDED o IDLE, pasar a READY.
            // La bandera sbpPlayWhenReady ya se habrá establecido en false por la llamada anterior a updateLocalStateAndInvalidateSbp.
            if (sbpPlaybackState == Player.STATE_BUFFERING || (sbpPlaybackState == Player.STATE_READY && this.playWhenReady /* el valor de SBP antes de este cambio */)) {
                updateLocalStateAndInvalidateSbp(
                    playbackStateUpdate = Player.STATE_READY,
                    isLoadingUpdate = false
                )
            }
        }

        // El Future retornado por SimpleBasePlayer.handleSetPlayWhenReady es para
        // acciones que deben ejecutarse después de que SBP haya procesado el cambio.
        // Para la mayoría de los reproductores simples, un Future vacío inmediato está bien.
        return Futures.immediateFuture {}
    }

// ... otros métodos handle* ...

    // CORREGIDO: El nombre del método es handleSeekToMediaItem
    // ... otros métodos handle* ...

    // CORRECCIÓN FINAL Y DEFINITIVA del nombre del método
    // ... otros métodos handle* ...

    // CORRECCIÓN FINALÍSIMA Y ESPERO QUE DEFINITIVA, GRACIAS A TU AYUDA
    override fun handleSeek(
        mediaItemIndex: Int,
        positionMs: Long,
        seekCommand: Int // Parámetro crucial que estaba omitiendo
    ): ListenableFuture<*> {
        Timber.tag(TAG).i(
            "handleSeek: index=$mediaItemIndex, positionMs=$positionMs, command=$seekCommand. " +
                    "SBP mediaItemCount: ${this.currentTimeline.windowCount}"
        )

        updateLocalStateAndInvalidateSbp(clearError = true)

        // La lógica aquí podría necesitar diferenciar basada en seekCommand si es necesario,
        // pero para un simple seekTo(index, positionMs), el mediaItemIndex será el objetivo.
        if (this.currentTimeline.windowCount > 0 && mediaItemIndex >= 0 && mediaItemIndex < this.currentTimeline.windowCount) {
            currentMinimalPlayerInternalIndex = mediaItemIndex

            val targetPlaybackState =
                if (sbpPlaybackState == Player.STATE_ENDED || sbpPlaybackState == Player.STATE_READY || sbpPlaybackState == Player.STATE_BUFFERING) {
                    Player.STATE_READY
                } else {
                    sbpPlaybackState
                }
            updateLocalStateAndInvalidateSbp(
                playbackStateUpdate = targetPlaybackState,
                isLoadingUpdate = false
            )

            Timber.tag(TAG)
                .d("handleSeek: Seek to SBP index $mediaItemIndex initiated. Internal index: $currentMinimalPlayerInternalIndex.")
            if (sbpPlayWhenReady && targetPlaybackState == Player.STATE_READY) {
                Timber.tag(TAG)
                    .d("MinimalTtsPlayer: SIMULATING SPEECH RESTART after seek for item at SBP index $mediaItemIndex")
                // TTS: textToSpeech.speak(...)
            }
        } else {
            Timber.tag(TAG)
                .w("handleSeek: Invalid seek parameters (index $mediaItemIndex for ${this.currentTimeline.windowCount} items).")
            updateLocalStateAndInvalidateSbp(
                playerErrorUpdate = PlaybackException(
                    "Invalid seek parameters",
                    null,
                    PlaybackException.ERROR_CODE_BAD_VALUE
                )
            )
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        Timber.tag(TAG).i("MinimalTtsPlayer: handleRelease called.")
        localMediaItemTitles.clear()
        return Futures.immediateVoidFuture()
    }
} // Fin de la clase MinimalTtsPlayer
// Fin de la clase MinimalTtsPlayer
// Fin de la clase MinimalTtsPlayer