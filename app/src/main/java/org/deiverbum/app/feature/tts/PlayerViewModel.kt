package org.deiverbum.app.feature.tts

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    private var currentPosition: Long = 0L
    private var currentSpeed: Float = .75F

    // This variable is used to determine if the user is clicked on a radio station to play at least once
    private val _isPlayerSetUp = MutableStateFlow(false)
    val isPlayerSetUp = _isPlayerSetUp.asStateFlow()

    fun setupPlayer() {
        _isPlayerSetUp.update {
            true
        }
    }

    fun initializePlayer(context: Context, videoUrl: String) {
        if (_playerState.value == null) {
            viewModelScope.launch {
                val exoPlayer = ExoPlayer.Builder(context).build().also {
                    val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                    it.setMediaItem(mediaItem)
                    it.setPlaybackSpeed(currentSpeed)
                    it.prepare()
                    it.playWhenReady = true
                    it.seekTo(currentPosition)
                    it.addListener(object : Player.Listener {
                        override fun onPlayerError(error: PlaybackException) {
                            handleError(error)
                        }
                    })
                }
                _playerState.value = exoPlayer
            }
        }
    }

    @OptIn(UnstableApi::class)
    fun initializePlayerr(context: Context, videoUrl: StringBuilder) {
        if (_playerState.value == null) {
            viewModelScope.launch {
                val audioOffloadPreferences =
                    TrackSelectionParameters.AudioOffloadPreferences.Builder()
                        .setAudioOffloadMode(TrackSelectionParameters.AudioOffloadPreferences.AUDIO_OFFLOAD_MODE_ENABLED) // Add additional options as needed
                        .setIsGaplessSupportRequired(true)
                        .setIsSpeedChangeSupportRequired(true)
                        .build()

                val exoPlayer = ExoPlayer.Builder(context).build().also {
                    val mmd = MediaMetadata.Builder()
                        .setTitle("Example")
                        .setArtist("Artist name")
                        .setSubtitle("Lorem ipsum")
                        .build()
                    val mediaItem = MediaItem.Builder()
                        .setMediaId("123")
                        .setMediaMetadata(mmd)
                        .setUri("")
                        //.setRequestMetadata(rmd)
                        .build()
                    //val mediaItem=MediaItem.fromBundle()
                    //val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                    it.setMediaItem(mediaItem)
                    //it.trackSelectionParameters.buildUpon().setAudioOffloadPreferences(audioOffloadPreferences)
                    it.setPlaybackSpeed(currentSpeed)

                    it.prepare()
                    it.playWhenReady = true
                    it.seekTo(currentPosition)
                    //it.setPlaybackSpeed(0.75F)
                    it.addListener(object : Player.Listener {
                        override fun onPlayerError(error: PlaybackException) {
                            handleError(error)
                        }
                    })
                }
                _playerState.value = exoPlayer
            }
        }
    }


    fun savePlayerState() {
        _playerState.value?.let {
            currentPosition = it.currentPosition
            //currentSpeed=it.currentSpeed
        }
    }

    fun savePlayerSpeed() {
        //_currentSpeed.value?.let {
        //currentPosition = it.currentPosition
        //currentSpeed=it.currentSpeed
        //}
    }

    fun releasePlayer() {
        _playerState.value?.release()
        _playerState.value = null
    }

    private fun handleError(error: PlaybackException) {
        when (error.errorCode) {
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> {
                // Handle network connection error
                println("Network connection error")
            }

            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                // Handle file not found error
                println("File not found")
            }

            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> {
                // Handle decoder initialization error
                println("Decoder initialization error")
            }

            else -> {
                // Handle other types of errors
                println("Other error: ${error.message}")
            }
        }
    }
}