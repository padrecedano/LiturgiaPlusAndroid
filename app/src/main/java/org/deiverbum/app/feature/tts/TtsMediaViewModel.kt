package org.deiverbum.app.feature.tts


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.media.service.TtsServiceHandler
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class TtsMediaViewModel @androidx.annotation.OptIn(UnstableApi::class)
@Inject constructor(
    private val simpleMediaServiceHandler: TtsServiceHandler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    // ...

    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }

    private val _uiState = MutableStateFlow<UIStateTts>(UIStateTts.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            //loadData()

            simpleMediaServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
                    is TtsServiceHandler.TtsMediaState.Buffering -> calculateProgressValues(
                        mediaState.progress
                    )

                    TtsServiceHandler.TtsMediaState.Initial -> _uiState.value = UIStateTts.Initial
                    is TtsServiceHandler.TtsMediaState.Playing -> isPlaying = mediaState.isPlaying
                    is TtsServiceHandler.TtsMediaState.Progress -> calculateProgressValues(
                        mediaState.progress
                    )

                    is TtsServiceHandler.TtsMediaState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = UIStateTts.Ready
                    }
                }
            }
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    override fun onCleared() {
        viewModelScope.launch {
            simpleMediaServiceHandler.onPlayerEvent(TtsServiceHandler.TtsPlayerEvent.Stop)
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    fun onUIEvent(uiEvent: UIEventTts) = viewModelScope.launch {
        when (uiEvent) {
            UIEventTts.Backward -> simpleMediaServiceHandler.onPlayerEvent(TtsServiceHandler.TtsPlayerEvent.Backward)
            UIEventTts.Forward -> simpleMediaServiceHandler.onPlayerEvent(TtsServiceHandler.TtsPlayerEvent.Forward)
            UIEventTts.PlayPause -> simpleMediaServiceHandler.onPlayerEvent(TtsServiceHandler.TtsPlayerEvent.PlayPause)
            is UIEventTts.UpdateProgress -> {
                progress = uiEvent.newProgress
                simpleMediaServiceHandler.onPlayerEvent(
                    TtsServiceHandler.TtsPlayerEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }

            UIEventTts.Stop -> simpleMediaServiceHandler.onPlayerEvent(TtsServiceHandler.TtsPlayerEvent.Stop)
        }
    }

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    fun loadData(text: String) {
        //simpleMediaServiceHandler.addMediaItem(text)
    }

}

sealed class UIEventTts {
    object PlayPause : UIEventTts()
    object Backward : UIEventTts()
    object Forward : UIEventTts()
    object Stop : UIEventTts()

    data class UpdateProgress(val newProgress: Float) : UIEventTts()
}

sealed class UIStateTts {
    object Initial : UIStateTts()
    object Ready : UIStateTts()
}