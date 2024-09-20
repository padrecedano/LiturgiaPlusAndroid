package org.deiverbum.app.feature.tts

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

class TtsManagerComposeB(
    context: Context,
    text: StringBuilder,
    splitRegex: String,
    private var mProgressListener: (Int, Int) -> Unit,
    initialSliderValue: Float,
    initialSliderMaxValue: Float
) : OnInitListener {
    private val mTexts: Array<String>
    private val mTts: TextToSpeech
    private var mTextProgress = 0
    private var mIsPlaying = false
    var sliderValue by mutableStateOf(initialSliderValue)
    var sliderMaxValue by mutableStateOf(initialSliderMaxValue)

    init {
        mTexts = text.split(splitRegex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mTts = TextToSpeech(context, this)
        mTts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String) {
                if (!mIsPlaying || mTextProgress == mTexts.size) return
                ++mTextProgress
                updateProgress(mTextProgress, mTexts.size)
                // currentTextIndex = mTextProgress
                speakText()
            }

            override fun onStart(utteranceId: String) {}

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String) {
            }
        })
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

    fun pause() {
        mIsPlaying = false
        mTts.stop()
        updateProgress(mTextProgress, mTexts.size)
    }

    fun resume() {
        mIsPlaying = true
        speakText()
    }

    fun stop() {
        mIsPlaying = false
        mTts.stop()
        mTextProgress = 0
        updateProgress(mTextProgress, mTexts.size)
    }

    private fun updateProgress(current: Int, max: Int) {
        mProgressListener.invoke(current, max) // Aquí se llama listener
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
            changeProgress(0)
        }
    }

    fun close() {
        mTts.stop()
        mTts.shutdown()
    }
}