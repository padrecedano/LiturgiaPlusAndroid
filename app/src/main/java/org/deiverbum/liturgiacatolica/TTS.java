package org.deiverbum.liturgiacatolica;

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

/**
 * Created by cedano on 18/10/17.
 */

public class TTS {
    private static final String TAG = "TTSClass";
    private static TextToSpeech tts;
    String[] strPartes;
    String strActual = "0";
    String strPrevia = "0";
    String strOracion;
    int i = 0;
    int x = 0;
    private String[] strContenido;

    public TTS(final Context context, String[] contenido) {
        this.strContenido = contenido;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
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
//leerTexto(strContenido,"1");


                                    }

                                    @Override
                                    public void onDone(String s) {


                                        for (String textos : strContenido) {
                                            i = 1;
                                            x = i - 1;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("");
                                            sb.append(i);
                                            strActual = sb.toString();

                                            StringBuilder sbX = new StringBuilder();
                                            sbX.append("");
                                            sbX.append(x);
                                            strPrevia = sbX.toString();

                                            if (s.equals(strPrevia)) {
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
                                        Log.i(TAG, "Error: " + s);
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


    void leerTexto(String strTexto, String strId) {
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

    public void cerrar() {
        tts.stop();
        tts.shutdown();
    }
}
