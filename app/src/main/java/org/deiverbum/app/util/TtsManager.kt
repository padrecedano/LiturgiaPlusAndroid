package org.deiverbum.app.util

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

/**
 * Esta clase se encarga se manejar la lectura de texto a voz.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2021.1
 */
class TtsManager(
    context: Context?,
    text: String,
    splitRegex: String,
    private val mProgressListener: (Int, Int) -> Unit
) : OnInitListener {
    private val mTexts: Array<String>
    private val mTts: TextToSpeech
    private var mTextProgress = 0
    private var mIsPlaying = false

    init {
        mTexts = text.split(splitRegex.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        mTts = TextToSpeech(context, this)
        mTts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String) {
                if (!mIsPlaying || mTextProgress == mTexts.size) return
                ++mTextProgress
                updateProgress(mTextProgress, mTexts.size)
                speakText()
            }

            override fun onStart(utteranceId: String) {}

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String) {
                onError(utteranceId)
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
        mIsPlaying = false
        mTts.stop()
        start()
        updateProgress(mTextProgress, mTexts.size)
    }

    fun stop() {
        mIsPlaying = false
        mTts.stop()
        mTextProgress = 0
        updateProgress(mTextProgress, mTexts.size)
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
            changeProgress(1)
        }
    }

    fun close() {
        mTts.stop()
        mTts.shutdown()
    }

    interface TextSpeechProgressListener {
        fun onProgressChanged(current: Int, max: Int)
    }
}