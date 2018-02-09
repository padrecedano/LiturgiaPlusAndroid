package org.deiverbum.liturgiacatolica.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.utils.TTS;
import org.deiverbum.liturgiacatolica.utils.Utils;
import org.deiverbum.liturgiacatolica.utils.VolleyErrorHelper;
import org.deiverbum.liturgiacatolica.utils.ZoomTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static org.deiverbum.liturgiacatolica.utils.Constants.BENEDICTUS;
import static org.deiverbum.liturgiacatolica.utils.Constants.BR;
import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.CE;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.ERR_GENERAL;
import static org.deiverbum.liturgiacatolica.utils.Constants.ERR_RESPONSORIO;
import static org.deiverbum.liturgiacatolica.utils.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.utils.Constants.LA_TITULO;
import static org.deiverbum.liturgiacatolica.utils.Constants.LA_URL;
import static org.deiverbum.liturgiacatolica.utils.Constants.LECTURA_BREVE;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.utils.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.utils.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.PADRENUESTRO;
import static org.deiverbum.liturgiacatolica.utils.Constants.PADRENUESTRO_TITULO;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRECES;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRE_ANT;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_BREVE;
import static org.deiverbum.liturgiacatolica.utils.Constants.SALMODIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.SALUDO_OFICIO;
import static org.deiverbum.liturgiacatolica.utils.Constants.SEPARADOR;

public class LaudesActivity extends AppCompatActivity {
    private static final String URL_BASE = "http://deiverbum.org/api/v1/h4.php?fecha=";
    private static final String URL_JSON = "misa";
    private static final String TAG = "LaudesActivity";
    ArrayAdapter adapter;
    ListView listView;
    Spanned strRespuesta;
    JsonArrayRequest jsArrayRequest;
    //   ZoomTextView mTextView;
    TextView mTextView;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laudes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilClass = new Utils();
        requestQueue= Volley.newRequestQueue(this);
        mTextView = (ZoomTextView) findViewById(R.id.txt_breviario);
        //mTextView = findViewById(R.id.txt_breviario);

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
                        String strLaudes = showLaudes(response);
                        strRespuesta = Utils.fromHtml(strLaudes);
//                        mTextView.setText(Utils.fromHtml(items));
                        //                   mTextView.setText(Utils.fromHtml(strLaudes.replaceAll("_", "")));
                        mTextView.setText(Utils.fromHtml(strLaudes.replaceAll(SEPARADOR, "")));

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
//        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
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
                String codLenguaje = Locale.getDefault().getLanguage();
                Log.d(TAG, codLenguaje);
                String sAntifonaInv = PRE_ANT + hora.getString("antifonai") + BR;
                String sHimno = HIMNO + utilClass.getFormato(hora.getString("himno"));

                //Contenido completo de cada salmo... ufff

                String sOrden1 = s1.getString("orden");
                String sAntifona1 = utilClass.getFormato(s1.getString("antifona"));
                String sRef1 = s1.getString("txt_ref");
                String sTema1 = s1.getString("tema");
                String sIntro1 = s1.getString("txt_intro");
                String sParte1 = s1.getString("parte");
                String sSalmo1 = s1.getString("txt_salmo");
                String sSalmoCompleto1 = utilClass.getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

                String sOrden2 = s2.getString("orden");
                String sAntifona2 = utilClass.getFormato(s2.getString("antifona"));
                String sRef2 = s2.getString("txt_ref");
                String sTema2 = s2.getString("tema");
                String sIntro2 = s2.getString("txt_intro");
                String sParte2 = s2.getString("parte");
                String sSalmo2 = s2.getString("txt_salmo");
                String sSalmoCompleto2 = utilClass.getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

                String sOrden3 = s3.getString("orden");
                String sAntifona3 = utilClass.getFormato(s3.getString("antifona"));
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

                //Otro dolorcito de cabeza a resolver, las antífonas del CE de los Domingos,
                // las cuales son trienales. Una posible solución sería incluirlas en un array


                sAntifonaCE = BRS + PRE_ANT + sAntifonaCE + BRS;
                String sPrecesIntro = preces.getString("txt_preces_intro");
                String sPreces = "";
                if (!utilClass.isNull(sPrecesIntro)) {
                    String[] introArray = preces.getString("txt_preces_intro").split("\\|");
                    String sPrecesCuerpo = utilClass.getFormato(preces.getString("txt_preces"));
                    sPreces = PRECES + utilClass.getPreces(introArray, sPrecesCuerpo);
                }

                String sOracion = ORACION + utilClass.getFormato(hora.getString("oracion"));

                //Agregamos el contenido al Stringbuilder y lo mostramos en nuestro textview usando el formta html :)
                sb.append(LA_TITULO);

                sb.append(sVida);
                sb.append(sMensaje);
                sb.append(SEPARADOR);
                sb.append("<p>" + SALUDO_OFICIO + "</p>");

                sb.append(sAntifonaInv);
                sb.append(SEPARADOR);
                sb.append(sHimno);
                sb.append(SEPARADOR);
                sb.append(SALMODIA);
                sb.append(sSalmoCompleto1);
                sb.append(SEPARADOR);
                sb.append(sSalmoCompleto2);
                sb.append(SEPARADOR);
                sb.append(sSalmoCompleto3);
                sb.append(SEPARADOR);

                sb.append(sLecturaBreve);
                sb.append(SEPARADOR);
                sb.append(sRespLBreve);
                sb.append(SEPARADOR);
                sb.append(CE);
                sb.append(sAntifonaCE);
                sb.append(utilClass.getFormato(BENEDICTUS));
                sb.append(sAntifonaCE);

                sb.append(SEPARADOR);

                sb.append(sPreces);
                sb.append(SEPARADOR);
                sb.append(PADRENUESTRO_TITULO);
                sb.append(utilClass.getFormato(PADRENUESTRO));
                sb.append(sOracion);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.breviario_menu_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//calendar.setC
        //noinspection SimplifiableIfStatement
        if (id == R.id.item_voz) {
            String[] strPrimera = strRespuesta.toString().split(SEPARADOR);
            new TTS(getApplicationContext(), strPrimera);
//            Log.d(TAG, strPrimera.toString());

            //           final TextView mTextView = (TextView) findViewById(R.id.txt_breviario);


//            lector.leerTexto("Test","1");


            //ttsFunction();
            //return true;
        }

        if (id == R.id.item_calendario) {
//            Intent i = new Intent(OficioActivity.this, CalendarioActivity.class);
            Intent i = new Intent(this, CalendarioActivity.class);

            startActivity(i);

//            ttsFunction();
            //return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
