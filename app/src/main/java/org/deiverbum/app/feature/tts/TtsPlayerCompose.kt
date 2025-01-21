package org.deiverbum.app.feature.tts

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.common.util.UnstableApi
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import java.util.Locale


/**
 * Esta clase se encarga se manejar la lectura de texto a voz.
 * Versión adaptada a Jetpack Compose.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
@UnstableApi
class TtsPlayerCompose(
    looper: Looper?, context: Context?,
    text: StringBuilder,
    splitRegex: String,
    private var mProgressListener: (Int, Int) -> Unit

) : SimpleBasePlayer(looper!!), OnInitListener {
    private var mTexts: Array<String>
    private val mTts: TextToSpeech
    private var mTextProgress = 0
    private var mIsPlaying = false
    private var currentPositionMs: Long = 0
    private var durationMs: Long = 0
    private var state = State.Builder()
        .setAvailableCommands(

            Player.Commands.Builder().addAll(
                COMMAND_PLAY_PAUSE,
                COMMAND_STOP,
                COMMAND_SET_SPEED_AND_PITCH,
                COMMAND_GET_TIMELINE,
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
            MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
                .setTitle("TTS test").build()
        )
        .setCurrentMediaItemIndex(0)
        .build()

    init {
        mTexts = text.split(splitRegex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mTts = TextToSpeech(context, this)
        //mTexts = listOf("Uno. Dos. Tres.").toTypedArray()
        //mTexts= text.toString().split("-")

        mTts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String) {
                updatePlaybackState(STATE_ENDED, false)
                //    if (!mIsPlaying || mTextProgress == mTexts.size) return
                ++mTextProgress
                speakText()
            }

            override fun onStart(utteranceId: String) {
                updatePlaybackState(STATE_READY, true)
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String) {
                //onError(utteranceId);
            }
        })
    }

    private fun speakText() {
        // if (mTextProgress >= mTexts.size) return
        //API 21+
        val bundle = Bundle()
        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)
        //for (name in mTexts) {
        mTts.speak(mTexts[mTextProgress], TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID")
        //mTts.speak(name, TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID")

        //}
        //stop()
    }

    private fun speakTextt() {
        if (mTextProgress >= mTexts.size) return
        //API 21+
        val bundle = Bundle()
        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)
        for (name in mTexts) {
            //mTts.speak(mTexts[mTextProgress], TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID")
            mTts.speak(name, TextToSpeech.QUEUE_ADD, bundle, "TTS_ID")

        }
    }

    fun start() {
        mIsPlaying = true
        updatePlaybackState(STATE_READY, true)

        speakText()
    }

    fun resume() {
        mIsPlaying = false
        mTts.stop()
        start()
        updateProgress(mTextProgress, mTexts.size)
    }

    override fun getState(): State {
        return state
    }


    override fun handleSetPlaybackParameters(playbackParameters: PlaybackParameters): ListenableFuture<*> {
        //setPlaybackSpeed(playbackParameters.speed)
        changeSpeechRate(playbackParameters)
        return Futures.immediateVoidFuture()
    }
    override fun handleSeek(
        mediaItemIndex: Int,
        positionMs: Long,
        seekCommand: Int
    ): ListenableFuture<*> {
        val targetTextIndex = (positionMs * mTexts.size / durationMs).toInt()
        mTextProgress = targetTextIndex.coerceIn(0, mTexts.size - 1)
        currentPositionMs = positionMs
        changeProgress(targetTextIndex + 1)

        if (mIsPlaying) {
            mTts.stop()
            speakText()
        }

        updateProgress(mTextProgress, mTexts.size)
        return Futures.immediateVoidFuture()
    }

    private fun updatePlaybackState(playbackState: Int, playWhenReady: Boolean) {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post {
            state = state.buildUpon()
                .setPlaybackState(playbackState)
                .setPlayWhenReady(playWhenReady, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
                .setContentPositionMs(currentPositionMs)
                //.setTotalBufferedDurationMs (durationMs)

                .setTotalBufferedDurationMs(PositionSupplier.getConstant(durationMs))
                .build()
            invalidateState()
        }
    }

    private fun updatePlaybackStatee(playbackState: Int, playWhenReady: Boolean) {
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
        if (playWhenReady) {
            val locSpanish = Locale("spa", "ESP")
            mTts.setLanguage(locSpanish)
            speakText()
        } else {
            mTts.stop()
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        mTts.stop()
        mTts.shutdown()
        return Futures.immediateVoidFuture()
    }

    override fun handleStop(): ListenableFuture<*> {
        mTts.stop()
        return Futures.immediateVoidFuture()
    }

    override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<*> {
        return Futures.immediateVoidFuture()
    }

    private fun updateProgress(current: Int, max: Int) {
        mProgressListener.invoke(current, max)
    }

    fun changeProgress(progress: Int) {
        mTextProgress = progress
        if (!mIsPlaying) return
        pause()
        start()
    }

    fun changeSpeechRate(playbackParameters: PlaybackParameters) {
        mTts.setSpeechRate(playbackParameters.speed)
        //state.
        //updatePlaybackState(playbackParameters.speed)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locSpanish = Locale("spa", "ESP")
            val result = mTts.setLanguage(locSpanish)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                return
            }
            durationMs = mTexts.sumOf { it.length * 50L } // Estimate duration
            updateProgress(0, mTexts.size)
        }
    }

    fun close() {
        mTts.stop()
        mTts.shutdown()
    }
}

@UnstableApi
class TtsPlayerComposee(
    looper: Looper?,
    context: Context?,
    text: StringBuilder,
    splitRegex: String,
    private var mProgressListener: (Int, Int) -> Unit
) : SimpleBasePlayer(looper!!), OnInitListener {
    private val mTexts: Array<String>
    private val mTts: TextToSpeech
    private var mTextProgress = 0
    private var mIsPlaying = false

    //private val compositionPlayerInternal: @MonotonicNonNull CompositionPlayerInternal? = null
    private var currentPositionMs: Long = 0
    private var durationMs: Long = 0

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
        .setPlaylist(listOf(MediaItemData.Builder("test").build()))
        /*.setPlaylistMetadata(
            MediaMetadata.Builder().setMediaType(MediaMetadata.MEDIA_TYPE_PLAYLIST)
                .setTitle("TTS test").build()
        )*/
        .setCurrentMediaItemIndex(0)
        .setContentPositionMs(0)
        .build()

    init {
        mTexts = text.split(splitRegex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mTts = TextToSpeech(context, this)
        mTts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {
                updatePlaybackState(STATE_READY, true)
            }

            override fun onDone(utteranceId: String) {
                if (!mIsPlaying || mTextProgress == mTexts.size - 1) return
                currentPositionMs += mTexts[mTextProgress].length * 50L // Estimate spoken duration
                ++mTextProgress
                updateProgress(mTextProgress, mTexts.size)
                speakText()
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String) {
                // Handle error
            }
        })
    }

    override fun handleSeek(
        mediaItemIndex: Int,
        positionMs: Long,
        seekCommand: Int
    ): ListenableFuture<*> {
        val targetTextIndex = (positionMs * mTexts.size / durationMs).toInt()
        mTextProgress = targetTextIndex.coerceIn(0, mTexts.size - 1)
        currentPositionMs = positionMs

        if (mIsPlaying) {
            mTts.stop()
            speakText()
        }

        updateProgress(mTextProgress, mTexts.size)
        return Futures.immediateVoidFuture()
    }

    private fun speakText() {
        if (mTextProgress >= mTexts.size) return
        val bundle = Bundle()
        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC)
        mTts.speak(mTexts[mTextProgress], TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID")
    }

    fun start() {
        mIsPlaying = true
        speakText()
    }

    fun resume() {
        mIsPlaying = false
        mTts.stop()
        start()
        updateProgress(mTextProgress, mTexts.size)
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
                .setContentPositionMs(currentPositionMs)
                //.setTotalBufferedDurationMs (durationMs)
                .build()
            invalidateState()
        }
    }

    override fun handleSetPlayWhenReady(playWhenReady: Boolean): ListenableFuture<*> {
        if (playWhenReady) {
            val locSpanish = Locale("spa", "ESP")
            mTts.setLanguage(locSpanish)
            speakText()
        } else {
            mTts.stop()
        }
        return Futures.immediateVoidFuture()
    }

    override fun handleRelease(): ListenableFuture<*> {
        mTts.stop()
        mTts.shutdown()
        return Futures.immediateVoidFuture()
    }

    override fun handleStop(): ListenableFuture<*> {
        mTts.stop()
        return Futures.immediateVoidFuture()
    }

    override fun handleSetShuffleModeEnabled(shuffleModeEnabled: Boolean): ListenableFuture<*> {
        return Futures.immediateVoidFuture()
    }

    private fun updateProgress(current: Int, max: Int) {
        mProgressListener.invoke(current, max)
    }

    fun changeProgress(progress: Int) {
        mTextProgress = progress
        if (!mIsPlaying) return
        pause()
        start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locSpanish = Locale("spa", "ESP")
            val result = mTts.setLanguage(locSpanish)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                return
            }
            durationMs = mTexts.sumOf { it.length * 50L } // Estimate duration
            updateProgress(0, mTexts.size)
        }
    }

    fun close() {
        mTts.stop()
        mTts.shutdown()
    }
}