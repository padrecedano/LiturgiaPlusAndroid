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
import utils.VolleyErrorHelper;

import static org.deiverbum.liturgiacatolica.Constants.BR;
import static org.deiverbum.liturgiacatolica.Constants.BRS;
import static org.deiverbum.liturgiacatolica.Constants.CE;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.Constants.ERR_GENERAL;
import static org.deiverbum.liturgiacatolica.Constants.ERR_RESPONSORIO;
import static org.deiverbum.liturgiacatolica.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.Constants.LA_TITULO;
import static org.deiverbum.liturgiacatolica.Constants.LA_URL;
import static org.deiverbum.liturgiacatolica.Constants.LECTURA_BREVE;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.Constants.PADRENUESTRO;
import static org.deiverbum.liturgiacatolica.Constants.PRECES;
import static org.deiverbum.liturgiacatolica.Constants.PRE_ANT;
import static org.deiverbum.liturgiacatolica.Constants.RESP_BREVE;
import static org.deiverbum.liturgiacatolica.Constants.SALMODIA;

public class LaudesActivity extends AppCompatActivity {
    private static final String URL_BASE = "http://deiverbum.org/api/v1/h4.php?fecha=";
    private static final String URL_JSON = "misa";
    private static final String TAG = "LaudesActivity";
    ArrayAdapter adapter;
    ListView listView;
    String items;
    JsonArrayRequest jsArrayRequest;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laudes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utilClass = new Utils();
        requestQueue= Volley.newRequestQueue(this);
        final TextView mTextView = (TextView) findViewById(R.id.txt_breviario);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        final ProgressDialog progressDialog = new ProgressDialog(LaudesActivity.this);
        progressDialog.setMessage(PACIENCIA);

        // Nueva petición JSONObject
        //utilClass.getHoy()
        jsArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                LA_URL + utilClass.getHoy(),
                "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items = showLaudes(response);
                        mTextView.setText(Utils.fromHtml(items));
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

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
        progressDialog.show();
    }


    protected String showLaudes(JSONArray js_arr) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
        StringBuilder sb = new StringBuilder();

        try {
            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject oInfo = liturgia.getJSONObject("info");

            int nCodigo = Integer.parseInt(oInfo.getString("codigo"));
            if (nCodigo < 1) {
                sb.append(ERR_GENERAL);

            } else {

                JSONObject lh = liturgia.getJSONObject("lh");
                JSONObject hora = lh.getJSONObject("la");
                JSONObject s1 = hora.getJSONObject("salmos").getJSONObject("s1");
                JSONObject s2 = hora.getJSONObject("salmos").getJSONObject("s2");
                JSONObject s3 = hora.getJSONObject("salmos").getJSONObject("s3");
                JSONObject biblica = hora.getJSONObject("biblica");
                JSONObject ce = hora.getJSONObject("ce");

                JSONObject preces = hora.getJSONObject("preces");

                String sVida = "";

                int id_tiempo = Integer.parseInt(oInfo.getString("id_tiempo"));
                switch (id_tiempo) {
                    case 9:
                        sVida = "<h3>" + hora.getString("txt_santo") + "</h3>" + CSS_SM_A + hora.getString("txt_vida") + CSS_SM_Z + BRS; // they are executed if variable == c1
                        break;
                    default:
                        break;
                }

                String sMensaje = oInfo.getString("mensaje");

                String sAntifonaInv = PRE_ANT + hora.getString("antifonai") + BR;
                String sHimno = HIMNO + utilClass.getFormato(hora.getString("himno"));

                //Contenido completo de cada salmo... ufff

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

                String sLecturaBreve = ERR_RESPONSORIO;
                String sRespLBreve = "";
                String sForma = biblica.getString("id_forma");
                if (sForma != null && !sForma.isEmpty()) {
                    int nForma = Integer.parseInt(sForma);//biblica.getInt("id_forma");
                    String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                    sRespLBreve = utilClass.getResponsorio(respArray, nForma);
                    sLecturaBreve = CSS_RED_A + LECTURA_BREVE + NBSP_4 + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS +
                            biblica.getString("txt_lbreve") + BRS;
                    sRespLBreve = RESP_BREVE + BRS + sRespLBreve;
                }
                String sAntifonaCE = ce.getString("txt_antifonace");

                //Otro dolorcito de cabeza a resolver, las antífonas del CE de los Domingos, las cuales son trienales. Una posible solución sería incluirlas en un array

                //CE con 3 ciclos
/*            String[] ce_parts = ce.getString("txt_antifonace").split("\\|");
            String txt_antifonace=css_redx+css_smx+"Anfífona"+css_smz+css_redz+brs+
                    css_nbsp+css_redx+"Año A: "+css_redz+ce_parts[0]+brs+
                    css_nbsp+css_redx+"Año B: "+css_redz+ce_parts[1]+brs+
                    css_nbsp+css_redx+"Año C: "+css_redz+ce_parts[2];
*/
                sAntifonaCE = CE + sAntifonaCE + BRS;
                String sPrecesIntro = preces.getString("txt_preces_intro");
                String sPreces = "";
                if (!utilClass.isNull(sPrecesIntro)) {
                    String[] introArray = preces.getString("txt_preces_intro").split("\\|");
                    String sPrecesCuerpo = utilClass.getFormato(preces.getString("txt_preces"));
                    sPreces = PRECES + utilClass.getPreces(introArray, sPrecesCuerpo) + PADRENUESTRO;
                }

                String sOracion = ORACION + utilClass.getFormato(hora.getString("oracion"));

                //Agregamos el contenido al Stringbuilder y lo mostramos en nuestro textview usando el formta html :)
                sb.append(LA_TITULO);
                sb.append(sVida);
                sb.append(sMensaje);

                sb.append(sAntifonaInv);
                sb.append(sHimno);
                sb.append(SALMODIA);
                sb.append(sSalmoCompleto1);
                sb.append(sSalmoCompleto2);
                sb.append(sSalmoCompleto3);

                sb.append(sLecturaBreve);
                sb.append(sRespLBreve);
                sb.append(sAntifonaCE);
//            sb.append(sPrecesIntro);
                sb.append(sPreces);
                sb.append(sOracion);
            }

/*            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(utilClass.getHoy() + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);
*/
        } catch (JSONException e) {
//            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
            e.printStackTrace();
        }

        return sb.toString();
    }


}
