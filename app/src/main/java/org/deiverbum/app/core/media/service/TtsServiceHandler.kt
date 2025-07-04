package org.deiverbum.app.core.media.service

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.deiverbum.app.feature.tts.MinimalTtsPlayer
import org.deiverbum.app.feature.tts.TtsPlayerOld
import org.deiverbum.app.feature.tts.TtsPlayerForApi26
import org.deiverbum.app.feature.tts.TtsPlayerMedia
import timber.log.Timber
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@UnstableApi
class TtsServiceHandler @androidx.annotation.OptIn(UnstableApi::class)
@Inject constructor(
    @ApplicationContext private val context: Context,
    //private val ttsPlayer: ExoPlayer // <--- INYECTAR TtsPlayer AQUÍ
    private val ttsPlayer: TtsPlayerOld // <--- INYECTAR TtsPlayer AQUÍ

) : Player.Listener {

    private val _simpleMediaState = MutableStateFlow<TtsMediaState>(TtsMediaState.Initial)
    val simpleMediaState = _simpleMediaState.asStateFlow()
    private var text = ""
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @RequiresApi(Build.VERSION_CODES.O)
    val ttsPlayerrr = TtsPlayerForApi26(
        Looper.getMainLooper(),
        context,
        this.text,
        { currentSegment: Int, totalSegments: Int ->

        },
        { progressPercent: Float, progressString: String, durationString: String ->
        }
    )



    private var job: Job? = null

    companion object {
        private const val MAX_TTS_SEGMENT_LENGTH = 3900 // Límite típico para TTS
    }

    // Callbacks para TtsPlayerMedia
    private val handleProgressUpdate: (currentSegment: Int, totalSegments: Int) -> Unit =
        { currentSegment, totalSegments ->
            Timber.d("TtsServiceHandler -> TtsPlayerMedia.onProgressUpdate: Segment $currentSegment/$totalSegments")
            // Aquí puedes actualizar _ttsMediaState si necesitas exponer este progreso
            // Por ejemplo, podrías añadir campos a TtsMediaState.Playing o crear un nuevo estado.
            // val currentState = _ttsMediaState.value
            // if (currentState is TtsMediaState.Playing) {
            // _ttsMediaState.value = currentState.copy(currentSegment = currentSegment, totalSegments = totalSegments)
            // }
        }

    private val handleSegmentSpoken: (progressPercent: Float, progressString: String, durationString: String) -> Unit =
        { progressPercent, progressString, durationString ->
            Timber.d("TtsServiceHandler -> TtsPlayerMedia.onSegmentSpoken: $progressString ($progressPercent), Duration: $durationString")
            // Similar al anterior, actualiza _ttsMediaState si es necesario.
            // Esto parece más relacionado con el progreso DENTRO de un segmento,
            // que TtsPlayerMedia actualmente no calcula con precisión.
            // Si TtsPlayerMedia lo hiciera, aquí lo reflejarías.
        }

    // Instancia de tu TtsPlayerMedia con todos los parámetros requeridos
    val ttsPlayerr: TtsPlayerMedia = TtsPlayerMedia(
        context = context,
        looper = Looper.getMainLooper(), // Looper para SimpleBasePlayer
        //textToSpeak = "", // Texto inicial, TtsPlayerMedia lo cambiará con setTextToSpeakAndPrepare
        overallProgressListener = handleProgressUpdate,
        segmentProgressListener = handleSegmentSpoken
    )

    private val _ttsMediaState = MutableStateFlow<TtsMediaState>(TtsMediaState.Initial)
    val ttsMediaState = _ttsMediaState.asStateFlow()

    val ttsPlayerM: Player =
        MinimalTtsPlayer( // Usa Player como tipo para mantener la compatibilidad si es posible
            context = context,
            looper = Looper.getMainLooper() // MinimalTtsPlayer solo necesita context y looper
        ).also {
            Timber.d("TtsServiceHandler: Using MinimalTtsPlayer instance for testing.")
        }

    init {
        //ttsPlayer.addListener(this)
        // init {
        ttsPlayer.addListener(this)
//     Timber.d("TtsServiceHandler initialized. Listening to TtsPlayer instance: $ttsPlayer")
// }
        //ttsPlayerM.addListener(this)
        //loadDummyMediaItemsForTesting() // <--- Llama aquí para cargar al inicio
        loadDummyMediaItemsForTestingMinimal()
        job = Job()
    }

    fun loadDummyMediaItemsForTestingMinimal() {
        Timber.d("loadDummyMediaItemsForTesting: Creando dummy MediaItems...")

        val dummyTexts = listOf(
            "Primer segmento de prueba.",
            "Segundo segmento, un poco más largo.",
            "Tercer segmento."
        )

        val mediaItems = dummyTexts.mapIndexed { index, text ->
            MediaItem.Builder()
                .setMediaId("dummy_segment_$index")
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Dummy Title ${index + 1}") // MinimalTtsPlayer usa el título
                        .setSubtitle(text) // Puedes añadir más metadatos si quieres
                        .build()
                )
                .build()
        }

        val mediaItem = MediaItem.Builder()
            .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
                    .setAlbumTitle("SoundHelix")
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()

        val mediaItemList = mutableListOf<MediaItem>()
        (1..17).forEach {
            mediaItemList.add(
                MediaItem.Builder()
                    .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-$it.mp3")
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                            .setArtworkUri(Uri.parse("https://cdns-images.dzcdn.net/images/cover/1fddc1ab0535ee34189dc4c9f5f87bf9/264x264.jpg"))
                            .setAlbumTitle("SoundHelix")
                            .setDisplayTitle("Song $it")
                            .build()
                    ).build()
            )
        }

        //simpleMediaServiceHandler.addMediaItem(mediaItem)
        //simpleMediaServiceHandler.addMediaItemList(mediaItemList)

        if (mediaItems.isNotEmpty()) {
            Timber.d("loadDummyMediaItemsForTesting: Preparando para llamar a ttsPlayer.setMediaItems con ${mediaItems.size} ítems.")
            ttsPlayer.setMediaItems(mediaItems, true) // true para resetPosition

            // Clave: Asegúrate de que resetPosition sea true si estás estableciendo una nueva lista
            //ttsPlayerM.setMediaItems(mediaItems, true) // true para resetPosition
            //ttsPlayer.setMediaItems(mediaItemList, true) // true para resetPosition

            // Es crucial postear la verificación y las llamadas a prepare/play
            // para darle tiempo a SimpleBasePlayer de procesar setMediaItems internamente.
            val playerHandler = Handler(Looper.getMainLooper()) // O el Looper del player

            playerHandler.post {
                Timber.d("loadDummyMediaItemsForTesting (posted check): SBP MediaItemCount: ${ttsPlayer.mediaItemCount}")
                Timber.d("loadDummyMediaItemsForTesting (posted check): SBP CurrentMediaItemIndex: ${ttsPlayer.currentMediaItemIndex}")
                Timber.d("loadDummyMediaItemsForTesting (posted check): SBP PlaybackState: ${ttsPlayer.playbackState}")

                if (ttsPlayerM.mediaItemCount > 0) {
                    Timber.d("loadDummyMediaItemsForTesting (posted check): MediaItems presentes en SBP. Llamando a prepare().")
                    ttsPlayerM.prepare() // Esto debería llamar a handlePrepare en MinimalTtsPlayer

                    playerHandler.post { // Postea de nuevo para ver el estado después de prepare
                        Timber.d("loadDummyMediaItemsForTesting (after prepare, posted): SBP PlaybackState: ${ttsPlayer.playbackState}")
                        if (ttsPlayerM.playbackState == Player.STATE_READY) {
                            Timber.d("loadDummyMediaItemsForTesting (after prepare, posted): SBP is READY. Calling play().")
                            ttsPlayerM.play() // Esto llamará a setPlayWhenReady(true) -> handleSetPlayWhenReady
                        } else {
                            Timber.w("loadDummyMediaItemsForTesting (after prepare, posted): SBP not READY after prepare. State: ${ttsPlayer.playbackState}. Cannot call play().")
                        }
                    }
                } else {
                    Timber.w("loadDummyMediaItemsForTesting (posted check): ERROR - SBP MediaItemCount sigue siendo 0 después de setMediaItems. No se puede preparar ni reproducir.")
                }
            }
        } else {
            Timber.w("loadDummyMediaItemsForTesting: No dummy MediaItems fueron creados localmente.")
        }
    }

    // ESTE ES EL CALLBACK REAL DEL LISTENER
    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        Timber.d("TtsServiceHandler: Player.Listener.onPlaybackStateChanged - newState: $playbackState, Player: player")
        when (playbackState) {
            Player.STATE_BUFFERING -> { // Usar Player.STATE_BUFFERING, no ExoPlayer.STATE_BUFFERING
                _simpleMediaState.value = TtsMediaState.Buffering(0L) // Ejemplo
                Timber.d("TtsServiceHandler: MediaState -> Buffering")
            }

            Player.STATE_READY -> { // Usar Player.STATE_READY
                _simpleMediaState.value = TtsMediaState.Ready(ttsPlayer.duration)
                Timber.d("TtsServiceHandler: MediaState -> Ready, Duration: ${ttsPlayer.duration}")
            }

            Player.STATE_ENDED -> {
                Timber.d("TtsServiceHandler: MediaState -> Ended")
                // Podrías querer un TtsMediaState.Ended
            }

            Player.STATE_IDLE -> {
                Timber.d("TtsServiceHandler: MediaState -> Idle")
            }
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        Timber.d("TtsServiceHandler: Player.Listener.onIsPlayingChanged - isPlaying: $isPlaying, Player: player")
        _simpleMediaState.value = TtsMediaState.Playing(isPlaying = isPlaying)
    }

    fun addMediaItem(
        textToSpeak: String
    ) {
        this.text = textToSpeak
        val segments = createSegmentsFromText(textToSpeak) // Tu función de segmentación externa



        if (segments.isEmpty()) {
            Timber.w("No segments created from text. Cannot play.")
            /*ttsPlayer.setTextAndMediaItems( // Usando ttsPlayer como nuevo nombre
                emptyList(),
                //emptyList(),
                "",
                 // Pasa lista vacía de MediaItems también
                true
            )*/
            //ttsPlayer.prepare() // Considera llamar a prepare aquí
            _simpleMediaState.value = TtsMediaState.Initial // O un estado apropiado
            return
        }

        val mediaItemsForPlayer = segments.mapIndexed { index, segmentText ->
            MediaItem.Builder()
                .setMediaId("segment_$index")
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Segment ${index + 1}")
                        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)

                        .build()
                )
                .build()
        }
        Timber.d("TtsServiceHandler: addMediaItem - Player instance: $ttsPlayer, Current state: ${ttsPlayer.playbackState}, PlayWhenReady: ${ttsPlayer.playWhenReady}")


        // Detener el habla actual si es necesario (TtsPlayerForApi26 podría manejar esto internamente
        // en su setTextAndMediaItems, pero hacerlo aquí es explícito)
        if (ttsPlayer.isPlaying) {
            ttsPlayer.stop() // Esto debería llevarlo a IDLE
        }
        //ttsPlayer.setTextAndMediaItems(mediaItemsForPlayer, textToSpeak, true)

        //ttsPlayer.setTextAndMediaItems( textToSpeak, true)

        //ttsPlayer.setTextAndMediaItems(listOf(textToSpeak),mediaItemsForPlayer,  true)
        Timber.d("TtsServiceHandler: Total mediaItems: ${ttsPlayer.mediaItemCount}")
        Timber.d("TtsServiceHandler: Calling prepare() on instance: $ttsPlayer")

        ttsPlayer.prepare() // Asegúrate de preparar el reproductor
        ttsPlayer.setPlayWhenReady(true)

    }


    fun createSegmentsFromText(textToSpeak: String, maxLength: Int = 4000): List<String> {
        val segments = mutableListOf<String>()
        var remainingText = textToSpeak.trim()

        while (remainingText.isNotEmpty()) {
            if (remainingText.length <= maxLength) {
                segments.add(remainingText)
                break
            } else {
                var splitPoint =
                    remainingText.lastIndexOf(' ', maxLength) // Intenta dividir por espacio
                if (splitPoint == -1 || splitPoint == 0) { // No hay espacio o está al principio
                    splitPoint = maxLength // Corta bruscamente
                }
                segments.add(remainingText.substring(0, splitPoint).trim())
                remainingText = remainingText.substring(splitPoint).trim()
            }
        }
        return segments.filter { it.isNotBlank() }
    }

    // En TtsServiceHandler.kt o donde esté
    fun loadDummyMediaItemsForTesting() {
        Timber.d("loadDummyMediaItemsForTesting: Creando dummy MediaItems...")

        val dummyTexts = listOf(
            "Este es el primer segmento de prueba.",
            "Ahora estamos en el segundo segmento, un poco más largo para ver cómo se maneja.",
            "Tercer y último segmento de prueba."
        )

        val mediaItems = dummyTexts.mapIndexed { index, text ->
            MediaItem.Builder()
                .setMediaId("dummy_segment_$index") // Un ID único para cada item
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(text) // El título puede ser el texto que normalmente sintetizarías
                        .build()
                )
                .build()
        }

        if (mediaItems.isNotEmpty()) {
            Timber.d("loadDummyMediaItemsForTesting: Preparando para llamar a ttsPlayer.setMediaItems con ${mediaItems.size} ítems.")
            // Esta llamada debería ser síncrona en términos de invocar tu handleSetMediaItems.
            // La actualización del Timeline de SBP ocurre después de que tu handleSetMediaItems retorna.
            ttsPlayer.setMediaItems(mediaItems, true) // true para resetPosition

            // Para verificar el estado DESPUÉS de que setMediaItems haya tenido la oportunidad
            // de ser procesado por SimpleBasePlayer (cuya actualización de Timeline podría
            // ser despachada a su propio bucle de mensajes), posteamos la verificación.
            val playerHandler =
                Handler(Looper.getMainLooper()) // O el Looper del player si es diferente

            playerHandler.post {
                Timber.d("loadDummyMediaItemsForTesting (posted check): SBP MediaItemCount: ${ttsPlayer.mediaItemCount}")
                Timber.d("loadDummyMediaItemsForTesting (posted check): SBP CurrentMediaItemIndex: ${ttsPlayer.currentMediaItemIndex}") // El de SBP
                Timber.d("loadDummyMediaItemsForTesting (posted check): TtsPlayer localSegmentTexts count: ${(ttsPlayer as? TtsPlayerOld)?.getLocalSegmentTextsCount() ?: "N/A"}") // Necesitarías un getter
                Timber.d("loadDummyMediaItemsForTesting (posted check): TtsPlayer currentMediaItemIndex (internal): ${(ttsPlayer as? TtsPlayerOld)?.getCurrentTtsPlayerMediaItemIndex() ?: "N/A"}") // Necesitarías un getter

                if (ttsPlayer.mediaItemCount > 0) {
                    Timber.d("loadDummyMediaItemsForTesting (posted check): MediaItems presentes en SBP. Llamando a prepare() y play().")
                    ttsPlayer.prepare()
                    ttsPlayer.play() // Esto llamará a setPlayWhenReady(true)
                } else {
                    Timber.w("loadDummyMediaItemsForTesting (posted check): ERROR - SBP MediaItemCount sigue siendo 0 después de setMediaItems. No se puede preparar ni reproducir.")
                }
            }
        } else {
            Timber.w("loadDummyMediaItemsForTesting: No dummy MediaItems fueron creados localmente.")
        }
    }

    // En tu TtsPlayer.kt, añade estos getters para depuración si es necesario:
// fun getLocalSegmentTextsCount(): Int = localSegmentTexts.size
// fun getCurrentTtsPlayerMediaItemIndex(): Int = this.currentMediaItemIndex
    // Ejemplo de cómo podrías manejar un evento:
    suspend fun onPlayerEvent(playerEvent: TtsPlayerEvent) {
        when (playerEvent) {
            TtsPlayerEvent.Backward -> ttsPlayer.seekBack()
            TtsPlayerEvent.Forward -> ttsPlayer.seekForward()
            TtsPlayerEvent.PlayPause -> {
                if (ttsPlayer.isPlaying) { // player.isPlaying es estándar de Media3
                    ttsPlayer.pause()
                } else {
                    ttsPlayer.prepare()
                    ttsPlayer.play()
                }
            }

            TtsPlayerEvent.Stop -> {
                ttsPlayer.stop()
            }

            is TtsPlayerEvent.UpdateProgress -> {
                val duration = ttsPlayer.duration
                if (duration != C.TIME_UNSET && duration > 0) {
                    ttsPlayer.seekTo((duration * playerEvent.newProgress).toLong())
                }
            }
        }
    }

    fun addMediaItemAndPlay(textToSpeak: String) {
        Timber.d("TtsServiceHandler: addMediaItemAndPlay - Texto: \"${textToSpeak.take(50)}...\"")
        if (textToSpeak.isBlank()) {
            Timber.w("TtsServiceHandler: Texto vacío, no se puede reproducir.")
            ttsPlayer.stop() // Detiene el habla actual
            ttsPlayer.clearMediaItems() // Limpia los MediaItems en SimpleBasePlayer
            //  _ttsMediaState.value = TtsMediaState.Error("Cannot play empty text")
            return
        }

        // 1. Segmentar el texto en TtsServiceHandler
        val textSegments = createSegmentsFromText(textToSpeak, MAX_TTS_SEGMENT_LENGTH)
        if (textSegments.isEmpty()) {
            Timber.w("TtsServiceHandler: No se generaron segmentos del texto.")
            ttsPlayer.stop()
            ttsPlayer.clearMediaItems()
            // _ttsMediaState.value = TtsMediaState.Error("Text could not be segmented")
            return
        }
        Timber.d("TtsServiceHandler: Texto segmentado en ${textSegments.size} partes.")

        // 2. Crear los MediaItems conceptuales en TtsServiceHandler
        val conceptualMediaItems = textSegments.mapIndexed { index, _ ->
            MediaItem.Builder()
                .setMediaId("tts_segment_$index") // ID único para cada segmento
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Segment ${index + 1} of ${textSegments.size}")
                        .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                        .build()
                )
                .build()
        }

        // 3. Llamar al método correcto de TtsPlayerMedia
        /*ttsPlayer.setTextAndMediaItems(
            newTextSegments = textSegments.get(0),
            newConceptualMediaItems = conceptualMediaItems,
            resetPosition = true // Siempre resetear la posición para nuevo texto
        )*/

        // 4. Iniciar la reproducción
        ttsPlayer.playWhenReady = true // Indicar que queremos que se reproduzca cuando esté listo.
        ttsPlayer.prepare()          // Es importante llamar a prepare() después de setMediaItems.
        // SimpleBasePlayer lo necesita para construir la Timeline
        // y transicionar a BUFFERING/READY.
        ttsPlayer.play()             // Asegura que la reproducción comience.
    }

    /**
     * Segmenta el texto en partes más pequeñas adecuadas para el motor TTS.
     * Esta es una implementación básica. Podrías querer una más sofisticada
     * que respete finales de oración o párrafos.
     */
    private fun createSegmentsFromTextt(text: String, maxLength: Int): List<String> {
        if (text.isEmpty()) return emptyList()
        if (maxLength <= 0) return listOf(text) // O lanzar un error

        val segments = mutableListOf<String>()
        var remainingText = text.trim()

        while (remainingText.isNotEmpty()) {
            if (remainingText.length <= maxLength) {
                segments.add(remainingText)
                break
            } else {
                // Intenta cortar en un espacio o signo de puntuación cercano al maxLength
                var cutOffPoint = -1
                // Buscar hacia atrás desde maxLength un buen punto de corte (ej. espacio, punto, coma)
                for (i in maxLength downTo 0) {
                    if (i < remainingText.length && (remainingText[i].isWhitespace() || remainingText[i] == '.' || remainingText[i] == ',' || remainingText[i] == ';')) {
                        cutOffPoint = i + 1 // Incluir el espacio/puntuación o cortar después
                        break
                    }
                }

                if (cutOffPoint == -1 || cutOffPoint > remainingText.length) {
                    // No se encontró un buen punto de corte, o el punto es inválido. Cortar en maxLength.
                    // Esto podría cortar a mitad de palabra, lo cual no es ideal.
                    // Una lógica más avanzada buscaría hacia adelante o tendría mejores heurísticas.
                    cutOffPoint = maxLength
                }
                if (cutOffPoint <= 0) { // Evitar bucles infinitos si maxLength es muy pequeño
                    cutOffPoint = minOf(maxLength, remainingText.length)
                }


                segments.add(remainingText.substring(0, cutOffPoint).trim())
                remainingText = remainingText.substring(cutOffPoint).trim()
            }
        }
        return segments.filter { it.isNotBlank() } // Asegurar que no haya segmentos vacíos
    }

    sealed class TtsPlayerEvent {
        object PlayPause : TtsPlayerEvent()
        object Backward : TtsPlayerEvent()
        object Forward : TtsPlayerEvent()
        object Stop : TtsPlayerEvent()
        data class UpdateProgress(val newProgress: Float) : TtsPlayerEvent()
    }

    sealed class TtsMediaState {
        object Initial : TtsMediaState()
        data class Ready(val duration: Long) : TtsMediaState()
        data class Progress(val progress: Long) : TtsMediaState()
        data class Buffering(val progress: Long) : TtsMediaState()
        data class Playing(val isPlaying: Boolean) : TtsMediaState()
    }
}