package org.deiverbum.liturgiacatolica.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.utils.TTS;
import org.deiverbum.liturgiacatolica.utils.Utils;
import org.deiverbum.liturgiacatolica.utils.VolleyErrorHelper;
import org.deiverbum.liturgiacatolica.utils.ZoomTextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.HI1_URL;
import static org.deiverbum.liturgiacatolica.utils.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.utils.Constants.HI_TITULO;
import static org.deiverbum.liturgiacatolica.utils.Constants.LECTURA_BREVE;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.utils.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.utils.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.SALMODIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.SEPARADOR;

public class TerciaActivity extends AppCompatActivity implements View.OnTouchListener {
    final static float STEP = 200;
    private static final String TAG = "TerciaActivity";
    String items;
    JsonObjectRequest jsonObjectRequest;
    Spanned strContenido;
    TextView mTextView, mtxtRatio2, mtxtRatio3, mtxtRatio4;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilClass = new Utils();
//        final PinchZoomTextView mTextView = (PinchZoomTextView) findViewById(R.id.txt_breviario);
//        final TextView mTextView = (TextView) findViewById(R.id.txt_breviario);
//        mTextView = (TextView) findViewById(R.id.txt_breviario);
        mTextView = (ZoomTextView) findViewById(R.id.txt_breviario);

        //       mTextView.setTextSize(mRatio + 13);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        final ProgressDialog progressDialog = new ProgressDialog(TerciaActivity.this);
        progressDialog.setMessage(PACIENCIA);
        requestQueue = Volley.newRequestQueue(this);

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                HI1_URL + utilClass.getHoy(),
                "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        //writeToFile(response.toString());
                        String newTxt = readFromFile();

                        String strTexto = showTercia(response);
                        //readFile();
                        strContenido = Utils.fromHtml(strTexto);
                        mTextView.setText(Utils.fromHtml(strTexto.replaceAll(SEPARADOR, "")));
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        Log.d(TAG, "Error: " + sError);
                        mTextView.setText(Utils.fromHtml(sError));
                        progressDialog.dismiss();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        progressDialog.show();
    }

    private void writeToFile(String data) {
        String filename = "tercia.json";
        String string = "Hello world!ñdfkvñdfkñgdfkñgldkfñgl";
        FileOutputStream outputStream;
        String path = getFilesDir().getAbsolutePath();
        Log.i(TAG, path);

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            Log.i(TAG, "writeToFile ");

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("tercia.json");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString).append("texto");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


    private void readFile() {
        final int READ_BLOCK_SIZE = 100;

        try {
            FileInputStream fileIn = openFileInput("tercia.json");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            StringBuilder s = new StringBuilder();
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s.append(readstring);
            }
            InputRead.close();
            Log.i(TAG, s.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.breviario_menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_voz) {
            String[] strPrimera = strContenido.toString().split(SEPARADOR);
            new TTS(getApplicationContext(), strPrimera);
        }

        if (id == R.id.item_calendario) {
            Intent i = new Intent(this, CalendarioActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "----------ok");
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                mTextView.setTextSize(mRatio + 13);
            }
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    protected String showTercia(JSONObject jsonDatos) {
        /*La Hora Intermedia es otro dolorcito de cabeza... estoy pensando sobre la decisión
        de qué hora mostrar, de cómo mostrarlo. Se puede usar la hora local del usuario y mostrar
        la hora que corresponda, si son más de las 5:59 se muestra por defecto la nona.
        Otra posibilidad es mostrar en el textview tres botones para que el usuario elija la hora.
        Aparte de eso, hay que manejar los himnos, determinando si el día de la fecha es domingo,
        si no es domingo, si es o no solemnidad, etc... en fin, ese lío de la hora intermedia
        tal y como viene en el salterio
         */
        StringBuilder sb = new StringBuilder();
        try {

            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            JSONObject jsonInfo = jsonBreviario.getJSONObject("info");
            JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");

            JSONObject salmoUno = jsonContenido.getJSONObject("salmos").getJSONObject("s1");
            JSONObject salmoDos = jsonContenido.getJSONObject("salmos").getJSONObject("s2");
            JSONObject salmoTres = jsonContenido.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = jsonContenido.getJSONObject("biblica");

            String strFecha = jsonInfo.getString("fecha") + BRS;
            String strTiempo = "<h1>" + jsonInfo.getString("tiempo") + "</h1>";
            String strSemana = "<h3>" + jsonInfo.getString("semana") + "</h3>";
            String strSalterio = jsonInfo.getString("salterio") + BRS;
            String sMensaje = jsonInfo.getString("mensaje");

            String sHimno = HIMNO + utilClass.getFormato(jsonContenido.getString("himno")) + BRS;

            String salmoUnoOrden = salmoUno.getString("orden");
            String salmoUnoAntifona = salmoUno.getString("antifona");
            String salmoUnoRef = salmoUno.getString("ref");
            String salmoUnoTema = salmoUno.getString("tema");
            String salmoUnoIntro = salmoUno.getString("intro");
            String salmoUnoParte = salmoUno.getString("parte");
            String salmoUnoTexto = salmoUno.getString("salmo");
            String salmoUnoCompleto;
            salmoUnoCompleto = utilClass.getSalmoCompleto(salmoUnoOrden, salmoUnoAntifona, salmoUnoRef, salmoUnoTema, salmoUnoIntro, salmoUnoParte, salmoUnoTexto);

            String salmoDosOrden = salmoDos.getString("orden");
            String salmoDosAntifona = salmoDos.getString("antifona");
            String salmoDosRef = salmoDos.getString("ref");
            String salmoDosTema = salmoDos.getString("tema");
            String salmoDosIntro = salmoDos.getString("intro");
            String salmoDosParte = salmoDos.getString("parte");
            String salmoDosTexto = salmoDos.getString("salmo");
            String salmoDosCompleto;
            salmoDosCompleto = utilClass.getSalmoCompleto(salmoDosOrden, salmoDosAntifona, salmoDosRef, salmoDosTema, salmoDosIntro, salmoDosParte, salmoDosTexto);

            String salmoTresOrden = salmoTres.getString("orden");
            String salmoTresAntifona = salmoTres.getString("antifona");
            String salmoTresRef = salmoTres.getString("ref");
            String salmoTresTema = salmoTres.getString("tema");
            String salmoTresIntro = salmoTres.getString("intro");
            String salmoTresParte = salmoTres.getString("parte");
            String salmoTresTexto = salmoTres.getString("salmo");
            String salmoTresCompleto;
            salmoTresCompleto = utilClass.getSalmoCompleto(salmoTresOrden, salmoTresAntifona, salmoTresRef, salmoTresTema, salmoTresIntro, salmoTresParte, salmoTresTexto);

            String sBiblicaResp = biblica.getString("responsorio");
            String sResponsorio = "";
            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] respArray = sBiblicaResp.split("\\|");
                sResponsorio = utilClass.getResponsorio(respArray, 31);
            }

            String sBiblica = CSS_RED_A + LECTURA_BREVE + NBSP_4
                    + biblica.getString("ref") + CSS_RED_Z + BRS + biblica.getString("texto") + BRS;

            String sOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));
            sb.append(strFecha);
            sb.append(strTiempo);
            sb.append(strSemana);
            sb.append(strSalterio);
            sb.append(sMensaje);
            sb.append(SEPARADOR);
            sb.append(HI_TITULO);

            sb.append(sHimno);
            sb.append(SEPARADOR);
            sb.append(SALMODIA);
            sb.append(salmoUnoCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoDosCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoTresCompleto);
            sb.append(SEPARADOR);

            sb.append(sBiblica);
            sb.append(SEPARADOR);
            sb.append(sResponsorio);
            sb.append(SEPARADOR);
            sb.append(sOracion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
