package org.deiverbum.app.feature.tts

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.UnstableApi
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import org.deiverbum.app.util.Constants
import java.util.Locale


@UnstableApi
class TtsPlayer(looper: Looper?, context: Context?, text: String) : SimpleBasePlayer(looper!!),
    OnInitListener {
    private var mTexts: Array<String> = emptyArray()

    //private val mTts: TextToSpeech
    private var mTextProgress = 0
    private var mIsPlaying = false
    private val textToSpeech = TextToSpeech(context, this)
    private val mTts = TextToSpeech(context, this)

    private var text: String = ""
    private var state = State.Builder()
        .setAvailableCommands(

            Player.Commands.Builder().addAll(
                COMMAND_PLAY_PAUSE,
                COMMAND_STOP,
                COMMAND_SEEK_BACK,
                COMMAND_SEEK_FORWARD,
                COMMAND_SET_SHUFFLE_MODE,
                COMMAND_GET_CURRENT_MEDIA_ITEM,
                COMMAND_GET_METADATA
            ).build()
        )
        .setPlayWhenReady(false, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
        //.setAudioAttributes(PlaybackService.Companion.getDEFAULT_AUDIO_ATTRIBUTES())
        .setPlaylist(listOf<MediaItemData>(MediaItemData.Builder("test").build()))
        .setPlaylistMetadata(
            MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_PLAYLIST)
                .setTitle("TTS test").build()
        )
        .setCurrentMediaItemIndex(0)
        .build()

    /**
     * Creates the [TextToSpeech] wrapper.
     *
     * @param looper The [Looper] used to call all methods on.
     */
    init {
        mTexts =
            text.split(Constants.SEPARADOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        //mTts = TextToSpeech(context, this)

        val locSpanish = Locale("spa", "ESP")
        this.text = text
        textToSpeech.setLanguage(locSpanish)
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {
                Log.i(TAG, "onStart")
                updatePlaybackState(STATE_READY, true)
            }

            override fun onDone(utteranceId: String) {
                Log.i(TAG, "onDone")
                updatePlaybackState(STATE_ENDED, false)
                if (!mIsPlaying || mTextProgress == mTexts.size) return
                ++mTextProgress
                //updateProgress(sliderValue.toInt(), mTexts.size)
                speakText()
            }

            override fun onError(utteranceId: String) {
                Log.i(TAG, "onError")
                updatePlaybackState(STATE_ENDED, false)
            }
        })
    }

    private fun speakText() {
        if (mTextProgress >= mTexts.size) return
        //API 21+
        val bundle = Bundle()
        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)
        mTts.speak(mTexts[mTextProgress], TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID")
    }

    override fun getState(): State {
        return state
    }

    private fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean) {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            state = state.buildUpon()
                .setPlaybackState(playbackState)
                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                .build()
            invalidateState()
        }
    }

    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
        Log.i(TAG, "handleSetPlayWhenReady: $playWhenReady")

        if (playWhenReady) {
            val locSpanish = Locale("spa", "ESP")
            textToSpeech.setLanguage(locSpanish)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                speakText()
                /*textToSpeech.speak(
                    text,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID
                )*/
            }
        } else {
            textToSpeech.stop()
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        textToSpeech.stop()
        textToSpeech.shutdown()
        return Futures.immediateVoidFuture()
    }

    override fun handleStop(): ListenableFuture<*> {
        textToSpeech.stop()
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
            "Este sábado, miles de venezolanos se congregaron en las principales capitales y ciudades del mundo, en una manifestación global contra lo que consideran un «fraude» perpetrado por el gobierno de Nicolás Maduro en las elecciones presidenciales del pasado 28 de julio. Desde Nueva York hasta Tokio, pasando por Madrid y Caracas, los manifestantes exigieron el reconocimiento del opositor Edmundo González Urrutia como el legítimo presidente de Venezuela, rechazando los resultados oficiales que declararon a Maduro como vencedor."
    }
}