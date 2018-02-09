package org.deiverbum.liturgiacatolica.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

//Created by cedano on 18/10/17.


public class TTS {
    private static final String TAG = "TTSClass";
    private static TextToSpeech tts;
    String[] strPartes;
    String strOracion;
    private String strActual = "0";
    private String strPrevia = "0";
    private int i = 0;
    private int x = 0;
    private int MAXIMO = 0;
    private String[] strContenido;

    public TTS(final Context context, String[] contenido) {

        this.strContenido = contenido;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                MAXIMO = TextToSpeech.getMaxSpeechInputLength();

                Log.d(TAG, "longitud: " + String.valueOf(TextToSpeech.getMaxSpeechInputLength()));
                if (status == TextToSpeech.SUCCESS) {
                    final Locale locSpanish = new Locale("spa", "ESP");
                    int result = tts.setLanguage(locSpanish);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, "Lenguaje no soportado", Toast.LENGTH_SHORT).show();
                    } else {

                        tts.setOnUtteranceProgressListener(
                                new UtteranceProgressListener() {
                                    @Override
                                    public void onStart(String s) {

                                    }

                                    @Override
                                    public void onDone(String s) {


                                        for (String textos : strContenido) {
                                            i = 1;
                                            x = i - 1;
                                            String sb = "" +
                                                    i;
                                            strActual = sb;

                                            String sbX = "" +
                                                    x;
                                            strPrevia = sbX;

                                            if (s.equals(strPrevia) && (strActual.length() < MAXIMO)) {
                                                leerTexto(textos, strActual);
                                                i++;

                                            }

                                        }
                                        //leerTexto(strOracion, "Oracion");
                                        leerTexto("Fin del Oficio", "fin");
                                        if (s.equals("fin")) {
                                            cerrar();
                                            //Log.i(TAG, "Cerramos...");
                                            //tts.stop();
                                            //tts.shutdown();
                                        }

                                    }


                                    @Override
                                    public void onError(String s) {
                                        Log.i(TAG, "Error: " + s + "error:" + TextToSpeech.ERROR);
                                    }
                                });

                        Log.i(TAG, "onInit exitoso");
                        leerTexto("Iniciando lectura", "0");
                    }
                } else {
                    Toast.makeText(context, "Falló la inicialización", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void leerTexto(String strTexto, String strId) {
        Log.d(TAG, "size ->" + String.valueOf(strTexto.length()) + "->" + strTexto);

        //      Log.d(TAG, String.valueOf(strTexto.length()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //API 21+
            Bundle bundle = new Bundle();
            bundle.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM, AudioManager.STREAM_MUSIC);
            tts.speak(strTexto, TextToSpeech.QUEUE_ADD, bundle, strId);


        } else {
            //API 15-
            HashMap<String, String> param = new HashMap<>();
            param.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            tts.speak(strTexto, TextToSpeech.QUEUE_ADD, param);
        }
    }

    private void cerrar() {
        tts.stop();
        tts.shutdown();
    }
}
