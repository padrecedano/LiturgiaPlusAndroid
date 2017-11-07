package org.deiverbum.liturgiacatolica;

/**
 * Created by cedano on 16/10/17.
 */

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.Locale;

/*** the implementation of TextToSpeech */
public final class VoiceReadText {
    private static TextToSpeech tts;
    private static boolean READING = false;
// public static final String UTTERANCE_ID = TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID;

    protected VoiceReadText() {
    }

    /**
     * check reading state
     */
    public static boolean isReading() {
        return READING;
    }

    /**
     * initializing with french language
     */
    public static void setContext(final Context ctx) {
        setContext(ctx, Locale.FRANCE);
    }

    /**
     * initialize the TTS by setting the context
     * this should be the very first method that should be
     * called before any other, otherwise the app will
     * terminate prematurely
     *
     * @param ctx  the Activity context,
     * @param lang the Locale language to be used
     */
    public static void setContext(final Context ctx, final Locale lang) {
        tts = new TextToSpeech(ctx.getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            // confugure how it will read
                            //pitch, rate, language, engine, ...
                            tts.setLanguage(lang);
                        }
                    }
                });
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

            @Override
            public void onStart(String p1) {
                READING = true;
            }

            @Override
            public void onDone(String p1) {
                READING = false;
            }

            @Override
            public void onError(String p1) {
                Toast.makeText(ctx.getApplicationContext(), p1,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * stop whatever is being read and
     * read this given text aloud
     *
     * @param txt the text to be read
     */
    public static void read(String txt) {
        read(txt, TextToSpeech.QUEUE_FLUSH);
    }

    /**
     * start reading text aloud
     *
     * @param txt       the text to be read
     * @param queueMode can be :
     *                  TextToSpeech.QUEUE_ADD or
     *                  TextToSpeech.QUEUE_FLUSH
     */
    public static void read(String txt, int queueMode) {
        //you can use TextToSpeech.QUEUE_ADD
        //to append to the end of the queue
        tts.speak(txt, queueMode, null);
        READING = true;
    }

    /*** stop reading text aloud */
    public static void stop() {
        tts.stop();
        READING = false;
    }

    /*** close TTS and release resources */
    public static void shutDown() {
        stop();
        tts.shutdown();
    }
}