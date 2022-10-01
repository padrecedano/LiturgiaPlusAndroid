package org.deiverbum.app.utils;


import static org.deiverbum.app.utils.Constants.ERR_INITIALIZATION;
import static org.deiverbum.app.utils.Constants.ERR_LANG;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2021.01
 */
public class TtsManager implements TextToSpeech.OnInitListener {

    private final String[] mTexts;
    private final TextToSpeech mTts;
    private final TextSpeechProgressListener mProgressListener;

    private int mTextProgress = 0;
    private boolean mIsPlaying;

    public interface TextSpeechProgressListener {
        void onProgressChanged(int current, int max);
    }

    public TtsManager(Context context, String text, String splitRegex, @Nullable TextSpeechProgressListener listener) {
        mTexts = text.split(splitRegex);
        mProgressListener = listener;
        mTts = new TextToSpeech(context, this);

        mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onDone(String utteranceId) {
                if (!mIsPlaying || mTextProgress == mTexts.length) return;
                ++mTextProgress;
                updateProgress(mTextProgress, mTexts.length);
                speakText();
            }

            @Override
            public void onStart(String utteranceId) {}

            @Override
            public void onError(String utteranceId) {}
        });
    }

    private void speakText() {
        if (mTextProgress >= mTexts.length) return;
        //API 21+
        Bundle bundle = new Bundle();
        bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
        mTts.speak(mTexts[mTextProgress], TextToSpeech.QUEUE_FLUSH, bundle, "TTS_ID");
    }


    public void start() {
        mIsPlaying = true;
        speakText();
    }

    public void pause() {
        mIsPlaying = false;
        mTts.stop();
        updateProgress(mTextProgress, mTexts.length);
    }

    public void resume() {
        mIsPlaying = false;
        mTts.stop();
        start();
        updateProgress(mTextProgress, mTexts.length);
    }

    public void stop() {
        mIsPlaying = false;
        mTts.stop();
        mTextProgress = 0;
        updateProgress(mTextProgress, mTexts.length);
    }

    private void updateProgress(int current, int max) {
        if (mProgressListener == null) return;
        mProgressListener.onProgressChanged(current, max);
    }

    public void changeProgress(int progress) {
        mTextProgress = progress;
        if (!mIsPlaying) return;
        pause();
        start();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            final Locale locSpanish = new Locale("spa", "ESP");
            int result = mTts.setLanguage(locSpanish);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", ERR_LANG);
                return;
            }
            changeProgress(1);
        } else {
            Log.e("TTS", ERR_INITIALIZATION);
        }
    }

    public void close() {
        mTts.stop();
        mTts.shutdown();
    }
}
