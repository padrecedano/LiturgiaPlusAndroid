package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
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

import static org.deiverbum.liturgiacatolica.Constants.BR;
import static org.deiverbum.liturgiacatolica.Constants.BRS;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.Constants.ERR_GENERAL;
import static org.deiverbum.liturgiacatolica.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_2;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.Constants.OL_TITULO;
import static org.deiverbum.liturgiacatolica.Constants.OL_URL;
import static org.deiverbum.liturgiacatolica.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.Constants.PRE_ANT;
import static org.deiverbum.liturgiacatolica.Constants.PRIMERA_LECTURA;
import static org.deiverbum.liturgiacatolica.Constants.RESP_LOWER;
import static org.deiverbum.liturgiacatolica.Constants.RESP_R;
import static org.deiverbum.liturgiacatolica.Constants.RESP_V;
import static org.deiverbum.liturgiacatolica.Constants.SALMODIA;
import static org.deiverbum.liturgiacatolica.Constants.SEGUNDA_LECTURA;

public class OficioActivity extends AppCompatActivity {
    private static final String URL_BASE = "";
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

        setContentView(R.layout.activity_oficio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestQueue= Volley.newRequestQueue(this);

        utilClass = new Utils();
        final ProgressDialog progressDialog = new ProgressDialog(OficioActivity.this);
        progressDialog.setMessage(PACIENCIA);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                OL_URL + utilClass.getHoy(),
                "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        TextView mTextView = (TextView) findViewById(R.id.txt_oficio);
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                        items = showOficio(response);
//                        adapter.notifyDataSetChanged();
                        mTextView.setText(Utils.fromHtml(items));
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                        TextView mTextView = (TextView) findViewById(R.id.txt_oficio);
                        String sError=ERR_GENERAL+"<br>El error generado es el siguiente: "+error.getMessage().toString();
                        mTextView.setText(Utils.fromHtml(sError));
                        progressDialog.dismiss();

                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
        progressDialog.show();


    }

    protected String showOficio(JSONArray js_arr) {



        StringBuilder sb = new StringBuilder();

        try {
            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject info = liturgia.getJSONObject("info");

            JSONObject lh = liturgia.getJSONObject("lh");
            JSONObject hora = lh.getJSONObject("ol");
            JSONObject s1 = hora.getJSONObject("salmos").getJSONObject("s1");
            JSONObject s2 = hora.getJSONObject("salmos").getJSONObject("s2");
            JSONObject s3 = hora.getJSONObject("salmos").getJSONObject("s3");

            JSONObject biblica = hora.getJSONObject("biblica");
            JSONObject patristica = hora.getJSONObject("patristica");

            String sAntifonaInv = PRE_ANT + hora.getString("antifonai") + BR;
            String sVida = "";

            int id_tiempo = Integer.parseInt(info.getString("id_tiempo"));
            switch (id_tiempo) {
                case 9:
                    sVida = "<h3>" + hora.getString("txt_santo") + "</h3>" + CSS_SM_A + hora.getString("txt_vida") + CSS_SM_Z + BRS; // they are executed if variable == c1
                    break;
                default:
                    break;
            }

            String sHimno = HIMNO + utilClass.getFormato(hora.getString("himno")) + BRS;
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

            //Lecturas
            String sRespOl = hora.getString("resp");
            if (!utilClass.isNull(sRespOl)) {
                String[] RespOlarray = sRespOl.split("\\|");
                sRespOl = RESP_V + RespOlarray[0] + BR + RESP_R + RespOlarray[1] + BRS;
            }
            //Bíblica
            String txt_biblica_obra = PRIMERA_LECTURA + biblica.getString("b_libro") +
                    CSS_RED_A + NBSP_4 +
                    biblica.getString("cap") + ", " + biblica.getString("vi") + biblica.getString("vf")
                    + CSS_RED_Z + BRS;
            String txt_biblica_tema = CSS_RED_A + biblica.getString("txt_biblica_tema") + CSS_RED_Z;
            String txt_biblica = biblica.getString("txt_biblica");
            String txt_biblica_respref = CSS_RED_A + RESP_LOWER + NBSP_2 + biblica.getString("txt_biblica_respref") + CSS_RED_Z + BRS;
            String sBiblicaResp = biblica.getString("txt_biblica_resp");
            String txt_biblica_r = "";

            //Hay que construir el responsorio. Los responsorios son recibidos en forma de matriz y en base a un código son desplegados

            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] br_parts = sBiblicaResp.split("\\|");
                txt_biblica_r = utilClass.getResponsorio(br_parts, 1);
            }


            //Patrística
            String txt_patristica;

            String padre_obra = SEGUNDA_LECTURA + patristica.getString("padre") + ", " +
                    patristica.getString("obra_tipo") + patristica.getString("obra");
            String txt_ref_patristica = BR + CSS_RED_A + CSS_SM_A + "(" + patristica.getString("txt_ref_patristica") + ")" + CSS_SM_Z +
                    BRS + patristica.getString("txt_tema_patristica") + CSS_RED_Z;

            txt_patristica = patristica.getString("txt_patristica");
            String txt_patristica_respref = CSS_RED_A + RESP_LOWER + " " + patristica.getString("txt_patristica_respref") + CSS_RED_Z + BRS;
            String sPatristicaResp = patristica.getString("txt_patristica_resp");
            String txt_patristica_r = "";
            if (sPatristicaResp != null && !sPatristicaResp.isEmpty() && !sPatristicaResp.equals("null")) {

                String[] pr_parts = sPatristicaResp.split("\\|");
                txt_patristica_r = utilClass.getResponsorio(pr_parts, 1);
            }

            String sOracion = ORACION + utilClass.getFormato(hora.getString("oracion"));

            sb.append(OL_TITULO);
            sb.append(sVida);
            sb.append(sAntifonaInv);
            sb.append(sHimno);
            sb.append(SALMODIA);
            sb.append(sSalmoCompleto1);
            sb.append(sSalmoCompleto2);
            sb.append(sSalmoCompleto3);

            sb.append(sRespOl);
            sb.append(txt_biblica_obra);
            sb.append(txt_biblica_tema);
            sb.append(txt_biblica);
            sb.append(txt_biblica_respref);
            sb.append(txt_biblica_r);
            sb.append(padre_obra);
            sb.append(txt_ref_patristica);
            sb.append(txt_patristica);
            sb.append(txt_patristica_respref);
            sb.append(txt_patristica_r);
            sb.append(sOracion);

 //           textViewToChange.setMovementMethod(new ScrollingMovementMethod());
 //           textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

 //           textViewToChange.setText(Html.fromHtml(utilClass.getHoy() + "<br><br>" + sb.toString()));
 //           textViewToChange.scrollTo(0, 0);
        } catch (JSONException e) {
//            textViewToChange.setText(e.getMessage());

            e.printStackTrace();
        }

        return sb.toString();
    }
}
