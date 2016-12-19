package org.deiverbum.liturgiacatolica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
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
import static org.deiverbum.liturgiacatolica.Constants.CE;
import static org.deiverbum.liturgiacatolica.Constants.CSS_B_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_B_Z;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.Constants.ERR_GENERAL;
import static org.deiverbum.liturgiacatolica.Constants.H4_URL;
import static org.deiverbum.liturgiacatolica.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.Constants.LECTURA_BREVE;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.Constants.PADRENUESTRO;
import static org.deiverbum.liturgiacatolica.Constants.PRECES;
import static org.deiverbum.liturgiacatolica.Constants.RESP_BREVE;
import static org.deiverbum.liturgiacatolica.Constants.SALMODIA;
import static org.deiverbum.liturgiacatolica.Constants.VI_TITULO;

public class VisperasActivity extends AppCompatActivity {
    private static final String URL_BASE = "http://deiverbum.org/api/v1/h4.php?fecha=";
    private static final String URL_JSON = "misa";
    private static final String TAG = "PostAdapter";
    ArrayAdapter adapter;
    ListView listView;
    String items;
    JsonArrayRequest jsArrayRequest;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visperas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilClass = new Utils();
        requestQueue= Volley.newRequestQueue(this);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                H4_URL + utilClass.getHoy(),
                "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        TextView mTextView = (TextView) findViewById(R.id.txt_breviario);
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        items = showVisperas(response);
//                        adapter.notifyDataSetChanged();
                        mTextView.setText(Utils.fromHtml(items));
//                        mTextView.setText(response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                        TextView mTextView = (TextView) findViewById(R.id.txt_breviario);
                        String sError=ERR_GENERAL+"<br>El error generado es el siguiente: "+error.getMessage().toString();
                        mTextView.setText(Utils.fromHtml(sError));
                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);

    }
    protected String showVisperas(JSONArray js_arr) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
        StringBuilder sb = new StringBuilder();

        try {

            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject js_info = liturgia.getJSONObject("info");
            JSONObject lh = liturgia.getJSONObject("lh");
            JSONObject hora = lh.getJSONObject("vi");
            JSONObject s1 = hora.getJSONObject("salmos").getJSONObject("s1");
            JSONObject s2 = hora.getJSONObject("salmos").getJSONObject("s2");
            JSONObject s3 = hora.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = hora.getJSONObject("biblica");
            JSONObject ce = hora.getJSONObject("ce");

            JSONObject preces = hora.getJSONObject("preces");

            String sVida = "";

            int id_tiempo = js_info.getInt("id_tiempo");
            switch (id_tiempo) {
                case 9:
                    sVida = CSS_B_A + hora.getString("txt_santo") + CSS_B_Z + BRS + CSS_SM_A + hora.getString("txt_vida") + CSS_SM_Z;
                    break;
                default:
                    break;
            }

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

//            String sAntifonaInv = PRE_ANT+hora.getString("antifonai")+BR;
            String sHimno = HIMNO + utilClass.getFormato(hora.getString("himno"));
            String sLecturaBreve = "Hay un error con la Lectura Breve, por favor comunícala al desarrollador: padre.cedano@gmail.com";
            String sRespLBreve = "";
            String sForma = biblica.getString("id_forma");
            if (sForma != null && !sForma.isEmpty())

            {

                int nForma = Integer.parseInt(sForma);//biblica.getInt("id_forma");

                String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                sRespLBreve = utilClass.getResponsorio(respArray, nForma);
                sLecturaBreve = CSS_RED_A + LECTURA_BREVE + NBSP_4 + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS +
                        biblica.getString("txt_lbreve") + BRS;
                sRespLBreve = RESP_BREVE + BRS + sRespLBreve;

            }
            String sAntifonaCE = ce.getString("txt_antifonace");

            //CE con 3 ciclos
/*            String[] ce_parts = ce.getString("txt_antifonace").split("\\|");
            String txt_antifonace=css_redx+css_smx+"Anfífona"+css_smz+css_redz+brs+
                    css_nbsp+css_redx+"Año A: "+css_redz+ce_parts[0]+brs+
                    css_nbsp+css_redx+"Año B: "+css_redz+ce_parts[1]+brs+
                    css_nbsp+css_redx+"Año C: "+css_redz+ce_parts[2];
*/
            sAntifonaCE = CE + sAntifonaCE + BRS;
//            String sPrecesIntro=PRECES+preces.getString("txt_preces_intro")+BRS;
            String[] introArray = preces.getString("txt_preces_intro").split("\\|");
            String sPrecesCuerpo = utilClass.getFormato(preces.getString("txt_preces"));

//            String sPreces = getPreces(introArray, sPrecesCuerpo);
            String sPreces = PRECES+utilClass.getPreces(introArray, sPrecesCuerpo)+PADRENUESTRO;


            String sOracion = ORACION + utilClass.getFormato(hora.getString("oracion"));

            sb.append(VI_TITULO);

            //           sb.append(sAntifonaInv);
            sb.append(sHimno);
            sb.append(SALMODIA);
            sb.append(sSalmoCompleto1);
            sb.append(sSalmoCompleto2);
            sb.append(sSalmoCompleto3);

            sb.append(sLecturaBreve);
            sb.append(sRespLBreve);
            sb.append(sAntifonaCE);
            //           sb.append(sPrecesIntro);
            sb.append(sPreces);
            sb.append(sOracion);

 /*           textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(utilClass.getHoy() + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);
*/
        } catch (JSONException e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        return sb.toString();
    }

}
