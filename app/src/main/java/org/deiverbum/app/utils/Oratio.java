package org.deiverbum.app.utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 7/12/21
 * @since
 */
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Oratio {

    //Made by Jacob Lim
    //The Main Project File for Oratio Library

    public static void speak(TextToSpeech tts, String message, Locale locale, Float pitch, Float speechRate) {
        //TTS Detail Set
        tts.setLanguage(locale);
        tts.setPitch(pitch);
        tts.setSpeechRate(speechRate);

        //TTS Output
        //Check Android Build Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public static void reset(TextToSpeech tts, Context context) {

        //TTS Init
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });
    }
}