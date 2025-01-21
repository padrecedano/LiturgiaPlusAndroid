package org.deiverbum.app.core.media.service

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.feature.tts.TtsPlayer
import javax.inject.Inject

@UnstableApi
class TtsServiceHandler @androidx.annotation.OptIn(UnstableApi::class)
@Inject constructor(
    //private val player: TtsPlayer,
    @ApplicationContext private val context: Context,

    ) : Player.Listener {

    private val _simpleMediaState = MutableStateFlow<TtsMediaState>(TtsMediaState.Initial)
    val simpleMediaState = _simpleMediaState.asStateFlow()
    private var text = ""
    val player = TtsPlayer(Looper.getMainLooper(), context, text) { current: Int, max: Int ->/*status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.forLanguageTag("es")
            }*/
    }
    private var job: Job? = null

    init {
        player.addListener(this)
        job = Job()
    }

    fun addMediaItem(mediaItem: MediaItem, text: String) {
        this.text = text
        player.setContent(text)
        player.setMediaItem(mediaItem)
        //player.
        player.prepare()

    }

    fun addMediaItemList(mediaItemList: List<MediaItem>) {
        player.setMediaItems(mediaItemList)
        player.prepare()
    }

    suspend fun onPlayerEvent(playerEvent: TtsPlayerEvent) {
        when (playerEvent) {
            TtsPlayerEvent.Backward -> player.seekBack()
            TtsPlayerEvent.Forward -> player.seekForward()
            TtsPlayerEvent.PlayPause -> {
                if (player.isPlaying || player.mIsPlaying) {
                    player.pause()
                    player.pauseTts()
                    stopProgressUpdate()
                } else {
                    player.play()
                    player.mIsPlaying = true
                    _simpleMediaState.value = TtsMediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }

            TtsPlayerEvent.Stop -> stopProgressUpdate()
            is TtsPlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING ->
                _simpleMediaState.value =
                    TtsMediaState.Buffering(player.mTextProgress.toLong())

            ExoPlayer.STATE_READY ->
                _simpleMediaState.value =
                    TtsMediaState.Ready(player.duration)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _simpleMediaState.value = TtsMediaState.Playing(isPlaying = isPlaying)
        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _simpleMediaState.value = TtsMediaState.Progress(player.mTextProgress.toLong())
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _simpleMediaState.value = TtsMediaState.Playing(isPlaying = false)
    }
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