package org.deiverbum.liturgiacatolica;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

import static org.deiverbum.liturgiacatolica.Constants.BRS;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.Constants.ERR_GENERAL;
import static org.deiverbum.liturgiacatolica.Constants.HI1_URL;
import static org.deiverbum.liturgiacatolica.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.Constants.HI_TITULO;
import static org.deiverbum.liturgiacatolica.Constants.LA_URL;
import static org.deiverbum.liturgiacatolica.Constants.LECTURA_BREVE;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.Constants.SALMODIA;

public class TerciaActivity extends AppCompatActivity {
    private Utils utilClass;
    ArrayAdapter adapter;
    ListView listView;
    String items;
    private static final String URL_BASE = "http://deiverbum.org/api/v1/h4.php?fecha=";
    private static final String URL_JSON = "misa";
    private RequestQueue requestQueue;
    JsonArrayRequest jsArrayRequest;
    private static final String TAG = "PostAdapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilClass = new Utils();
        requestQueue= Volley.newRequestQueue(this);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                HI1_URL + utilClass.getHoy(),
                "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        TextView mTextView = (TextView) findViewById(R.id.txt_tercia);
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        items = showTercia(response);
//                        adapter.notifyDataSetChanged();
                        mTextView.setText(Utils.fromHtml(items));
//                        mTextView.setText(response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                        TextView mTextView = (TextView) findViewById(R.id.txt_tercia);
                        String sError=ERR_GENERAL+"<br>El error generado es el siguiente: "+error.getMessage().toString();
                        mTextView.setText(utilClass.fromHtml(sError));
                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);

    }


    protected String showTercia(JSONArray js_arr) {
        /*La Hora Intermedia es otro dolorcito de cabeza... estoy pensando sobre la decisión
        de qué hora mostrar, de cómo mostrarlo. Se puede usar la hora local del usuario y mostrar
        la hora que corresponda, si son más de las 5:59 se muestra por defecto la nona.
        Otra posibilidad es mostrar en el textview tres botones para que el usuario elija la hora.
        Aparte de eso, hay que manejar los himnos, determinando si el día de la fecha es domingo,
        si no es domingo, si es o no solemnidad, etc... en fin, ese lío de la hora intermedia
        tal y como viene en el salterio
         */
        // Por el momento muestro la hora tercia, que he llamado, para que nos entendamos con la API, hora 31
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
        StringBuilder sb = new StringBuilder();
        try {

            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject js_info = liturgia.getJSONObject("info");

            JSONObject lh = liturgia.getJSONObject("lh");
            JSONObject hora = lh.getJSONObject("hi");
            JSONObject s1 = hora.getJSONObject("salmos").getJSONObject("s1");
            JSONObject s2 = hora.getJSONObject("salmos").getJSONObject("s2");
            JSONObject s3 = hora.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = hora.getJSONObject("biblica");

            String sHimno = HIMNO + utilClass.getHimnos(hora.getString("himnos")) + BRS;

            String sOrden1 = s1.getString("orden");
            String sAntifona1 = s1.getString("antifona");
            String sRef1 = s1.getString("txt_ref");
            String sTema1 = s1.getString("tema");
            String sIntro1 = s1.getString("txt_intro");
            String sParte1 = s1.getString("parte");

            String sSalmo1 = s1.getString("txt_salmo");
            String sSalmoCompleto1 = utilClass.getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = s2.getString("antifona");
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = utilClass.getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = s3.getString("antifona");
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = utilClass.getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

            String sBiblicaResp = biblica.getString("txt_responsorio");
            String sResponsorio = "";
            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] respArray = sBiblicaResp.split("\\|");
                sResponsorio = utilClass.getResponsorio(respArray, 31);
            }

            String sBiblica = CSS_RED_A + LECTURA_BREVE + NBSP_4
                    + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS + biblica.getString("txt_lbreve") + BRS;

            String sOracion = ORACION + utilClass.getFormato(hora.getString("oracion"));

            sb.append(HI_TITULO + BRS + "Por el momento sólo se muestra la Hora Tercia" + BRS);
            sb.append(sHimno);
            sb.append(SALMODIA);
            sb.append(sSalmoCompleto1);
            sb.append(sSalmoCompleto2);
            sb.append(sSalmoCompleto3);

            sb.append(sBiblica);
            sb.append(sResponsorio);
            sb.append(sOracion);

/*            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(utilClass.getHoy() + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);
*/
        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
            e.printStackTrace();
        }

        return sb.toString();
    }


}
