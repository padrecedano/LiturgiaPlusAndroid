package org.deiverbum.app.feature.tts

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player.Commands
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture


@UnstableApi
class TtsExoPlayer(looper: Looper?, context: Context?) : SimpleBasePlayer(looper!!),
    OnInitListener {
    private val textToSpeech = TextToSpeech(context, this)
    private var state = State.Builder()
        .setAvailableCommands(
            Commands.Builder().addAll(
                COMMAND_PLAY_PAUSE,
                COMMAND_SEEK_TO_NEXT,
                COMMAND_SEEK_TO_PREVIOUS,
                COMMAND_SEEK_BACK,
                COMMAND_SEEK_FORWARD,
                COMMAND_SET_SHUFFLE_MODE,
                COMMAND_GET_CURRENT_MEDIA_ITEM,
                COMMAND_GET_METADATA,
                COMMAND_STOP,
                COMMAND_RELEASE,
            ).build()
        )
        .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
//        .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_AUDIO_BECOMING_NOISY)
//        .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_REMOTE)
//        .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_AUDIO_FOCUS_LOSS)
        .setAudioAttributes(
            AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build()
        )
        .setPlaylist(
            listOf(
                MediaItemData.Builder("TTS_PLAYLIST")
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                            .setTitle("test")
                            .setDescription("test description")
                            .build()
                    )
                    .build()
            )
        )
        .setPlaylistMetadata(
            MediaMetadata.Builder()
                .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                .setTitle("Book Title")
                .setDescription("Chapter Title")
                .build()
        )
        .setCurrentMediaItemIndex(0)
        .build()

    /**
     * Creates the [TextToSpeech] wrapper.
     *
     * @param looper The [Looper] used to call all methods on.
     */
    init {
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {
                Log.i(TAG, "onStart")
                updatePlaybackState(STATE_READY, true)
            }

            override fun onDone(utteranceId: String) {
                Log.i(TAG, "onDone")
                updatePlaybackState(STATE_IDLE, false)
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String) {
                Log.i(TAG, "onError")
                updatePlaybackState(STATE_ENDED, false)
            }

            override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                super.onRangeStart(utteranceId, start, end, frame)
            }
        })
    }

    override fun getState(): State {
        return state
    }

    fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean) {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            state = state.buildUpon()
                .setPlaybackState(playbackState)
                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
//                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_AUDIO_BECOMING_NOISY)
//                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_REMOTE)
//                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_AUDIO_FOCUS_LOSS)
                .build()
            invalidateState()
        }
    }

    override fun handlePrepare(): ListenableFuture<*> {
        Log.d(TAG, "handlePrepare")
        return super.handlePrepare()
    }

    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
        Log.i(TAG, "handleSetPlayWhenReady: $playWhenReady")
        if (playWhenReady) {
            textToSpeech.speak(
                SAMPLE_TEXT,
                TextToSpeech.QUEUE_FLUSH,
                null,
                TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID
            )
            updatePlaybackState(STATE_READY, true)
        } else {
            textToSpeech.stop()
            updatePlaybackState(STATE_IDLE, false)
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        Log.i(TAG, "handleRelease")
        updatePlaybackState(STATE_ENDED, false)
        textToSpeech.stop()
        textToSpeech.shutdown()
        return Futures.immediateVoidFuture()
    }

    override fun handleStop(): ListenableFuture<*> {
        Log.i(TAG, "handleStop")
        updatePlaybackState(STATE_ENDED, false)
        textToSpeech.stop()
        return Futures.immediateVoidFuture()
    }

    override fun handleSeek(
        mediaItemIndex: Int,
        positionMs: Long,
        seekCommand: Int
    ): ListenableFuture<*> {
        when (seekCommand) {
            COMMAND_SEEK_TO_NEXT -> {
                Log.i(TAG, "handleSeek: COMMAND_SEEK_TO_NEXT")
            }

            COMMAND_SEEK_TO_PREVIOUS -> {
                Log.i(TAG, "handleSeek: COMMAND_SEEK_TO_PREVIOUS")
            }

            COMMAND_SEEK_BACK -> {
                Log.i(TAG, "handleSeek: COMMAND_SEEK_BACK")
            }

            COMMAND_SEEK_FORWARD -> {
                Log.i(TAG, "handleSeek: COMMAND_SEEK_FORWARD")
            }

            else -> {
                Log.i(TAG, "handleSeek: ${seekCommand}")
            }
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<*> {
        return Futures.immediateVoidFuture()
    }

    override fun onInit(status: Int) {
        Log.i(TAG, "Tts init")
    }

    companion object {
        private const val TAG = "TtsPlayer"
        private const val SAMPLE_TEXT =
            "Hello World, this is a sample text for testing Media3's SimpleBasePlayer. I expect background plyback to work and a notification to show up. However none of that is working."
    }
}