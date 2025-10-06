package org.deiverbum.app.core.media.service

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@UnstableApi
class TtsServiceHandler @androidx.annotation.OptIn(UnstableApi::class)
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val ttsPlayer: ExoPlayer // <--- INYECTAR TtsPlayer AQUÍ
    //private val ttsPlayer: TtsPlayerOld // <--- INYECTAR TtsPlayer AQUÍ

) : Player.Listener {

    private val _simpleMediaState = MutableStateFlow<TtsMediaState>(TtsMediaState.Initial)
    val simpleMediaState = _simpleMediaState.asStateFlow()
    private var text = ""
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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

    private val _ttsMediaState = MutableStateFlow<TtsMediaState>(TtsMediaState.Initial)
    val ttsMediaState = _ttsMediaState.asStateFlow()

    init {
        //ttsPlayer.addListener(this)
        // init {
        ttsPlayer.addListener(this)
//     Timber.d("TtsServiceHandler initialized. Listening to TtsPlayer instance: $ttsPlayer")
// }
        //ttsPlayerM.addListener(this)
        //loadDummyMediaItemsForTesting() // <--- Llama aquí para cargar al inicio
        job = Job()
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

        segments.mapIndexed { index, segmentText ->
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
        ttsPlayer.playWhenReady = true

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
        textSegments.mapIndexed { index, _ ->
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