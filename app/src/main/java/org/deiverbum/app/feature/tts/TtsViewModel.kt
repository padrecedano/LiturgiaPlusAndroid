package org.deiverbum.app.feature.tts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.data.repository.TtsRepository
import org.deiverbum.app.core.model.tts.TtsProgressData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TtsViewModel @Inject constructor(
    private val ttsRepository: TtsRepository
    // Si necesitas llamar a stopAndReleaseService directamente:
    // private val ttsRepositoryConcrete: TtsRepositoryImpl // Inyecta la implementación
) : ViewModel() {

    companion object {
        private const val TAG = "TtsViewModel"
    }

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    // Este es el texto que se está reproduciendo actualmente o que se intentó reproducir.
    // Lo usamos para comparar si el usuario quiere reproducir el mismo texto mientras está pausado.
    private var currentlyPlayingText: String? = null

    val ttsUiState: StateFlow<TtsProgressData> = ttsRepository.ttsProgress
        //.onEach { Timber.d("TtsViewModel", "ttsUiState updated: $it") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TtsProgressData()
        )

    fun onInputTextChanged(newText: String) {
        _inputText.value = newText
    }

    fun loadAndPlayText(text: String) {
        _inputText.value = text // Actualiza el texto interno
        // Aquí iniciarías la lógica para preparar y empezar a reproducir el texto
        // a través de tu ttsRepository o directamente el servicio.
        // Por ejemplo, podrías querer que el servicio TTS se inicie aquí
        // o que el usuario tenga que pulsar "Play" en TtsScreenWithBottomSheetControls.
        // Si quieres que se reproduzca automáticamente:
        ttsRepository.playText(text) // Asumiendo que tienes una función así
    }

    fun playText() { // Si el usuario pulsa play y el texto ya está cargado
        if (_inputText.value.isNotBlank() && ttsUiState.value.playbackState != TtsPlaybackState.PLAYING) {
            ttsRepository.playText(_inputText.value)
        }
    }

    fun playTextt() {
        val textToPlay = _inputText.value
        if (textToPlay.isNotBlank()) {
            val currentProgress = ttsUiState.value
            // Comprobar si está pausado Y si el texto en el input es el mismo que se estaba reproduciendo.
            if (currentProgress.playbackState == TtsPlaybackState.PAUSED &&
                textToPlay == currentlyPlayingText
            ) {
                Timber.d(TAG, "Resuming playback for the same text.")
                resumePlayback()
            } else {
                Timber.d(TAG, "Starting new playback for text: $textToPlay")
                currentlyPlayingText = textToPlay // Guardar el texto que se va a reproducir
                ttsRepository.playText(textToPlay)
            }
        } else {
            Timber.d(TAG, "Input text is blank, cannot play.")
            // Opcionalmente, podrías detener la reproducción si hay algo en curso
            // if (ttsUiState.value.playbackState == TtsPlaybackState.PLAYING || ttsUiState.value.playbackState == TtsPlaybackState.PAUSED) {
            //     stopPlayback()
            // }
        }
    }

    fun pausePlayback() {
        if (ttsUiState.value.playbackState == TtsPlaybackState.PLAYING) {
            Timber.d(TAG, "Pausing playback.")
            ttsRepository.pause()
        }
    }

    fun resumePlayback() {
        // Solo reanudar si hay segmentos cargados (es decir, se llamó a playText antes y está pausado)
        // o si hay texto en el input que podría no haberse iniciado.
        if (ttsUiState.value.playbackState == TtsPlaybackState.PAUSED && ttsUiState.value.totalSegments > 0) {
            Timber.d(TAG, "Resuming playback.")
            ttsRepository.resume()
        } else if (ttsUiState.value.playbackState != TtsPlaybackState.PLAYING && _inputText.value.isNotBlank()) {
            // Si no está pausado pero hay texto en el input y no se está reproduciendo, intentar reproducir.
            // Esto cubre el caso donde el usuario pone texto y pulsa "resume" sin haber pulsado "play" antes.
            Timber.d(TAG, "Attempting to play text from input on resume command.")
            playText()
        } else {
            Timber.d(
                TAG,
                "Cannot resume. State: ${ttsUiState.value.playbackState}, Segments: ${ttsUiState.value.totalSegments}"
            )
        }
    }

    fun stopPlayback() {
        Timber.d(TAG, "Stopping playback.")
        ttsRepository.stop()
        currentlyPlayingText = null // Limpiar el texto en reproducción al detener
    }

    fun seekToRelativePosition(sliderPosition: Float) {
        val totalSegments = ttsUiState.value.totalSegments
        if (totalSegments > 0) {
            // Asegurar que el sliderPosition esté entre 0.0 y 1.0
            val clampedSliderPosition = sliderPosition.coerceIn(0.0f, 1.0f)
            // El índice del segmento va de 0 a totalSegments - 1
            val targetSegment =
                (clampedSliderPosition * (totalSegments - 1)).toInt().coerceIn(0, totalSegments - 1)
            Timber.d(
                TAG,
                "Seeking to relative position: $clampedSliderPosition, target segment: $targetSegment"
            )
            ttsRepository.seekToSegment(targetSegment)
        }
    }

    fun setSpeechRate(rate: Float) {
        val clampedRate = rate.coerceIn(0.5f, 2.0f) // Rango de ejemplo
        Timber.d(TAG, "Setting speech rate to: $clampedRate")
        ttsRepository.setSpeechRate(clampedRate)
    }

    fun setSpeechPitch(pitch: Float) {
        val clampedPitch = pitch.coerceIn(0.5f, 2.0f) // Rango de ejemplo
        Timber.d(TAG, "Setting speech pitch to: $clampedPitch")
        ttsRepository.setSpeechPitch(clampedPitch)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d(TAG, "onCleared called.")
        // Decisión CRÍTICA: ¿Debe detenerse el TTS cuando esta pantalla desaparece?
        //
        // CASO 1: La reproducción DEBE continuar en segundo plano (lector de audiolibros).
        // En este caso, NO llames a `ttsRepository.release()` ni a `stopPlayback()`.
        // El servicio en primer plano (`TextToSpeechService`) mantendrá la reproducción.
        // El usuario lo detendrá a través de la notificación o volviendo a la app.
        //
        // CASO 2: La reproducción SÓLO tiene sentido mientras esta pantalla está visible.
        // En este caso, SÍ debes detener y liberar.
        // Ejemplo:
        // Timber.d(TAG, "Stopping and releasing TTS resources as ViewModel is cleared.")
        // ttsRepository.stop() // Detiene la reproducción actual
        // ttsRepository.release() // Desvincula del servicio.
        // Si el servicio fue iniciado con startService y quieres que se detenga completamente:
        // if (ttsRepository is TtsRepositoryImpl) { // Necesitas acceso a la implementación
        //      (ttsRepository as TtsRepositoryImpl).stopAndReleaseService()
        // }

        // Para nuestro caso de "lector de grandes cantidades de texto", asumimos que
        // la reproducción debe continuar si el usuario navega fuera.
        // Por lo tanto, NO hacemos nada aquí para detener el servicio.
    }
}