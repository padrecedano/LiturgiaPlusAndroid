package org.deiverbum.liturgiacatolica.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
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

import static org.deiverbum.liturgiacatolica.utils.Constants.BR;
import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_SM_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.FIN_SALMO;
import static org.deiverbum.liturgiacatolica.utils.Constants.HIMNO;
import static org.deiverbum.liturgiacatolica.utils.Constants.INVITATORIO;
import static org.deiverbum.liturgiacatolica.utils.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_2;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.utils.Constants.OL_TITULO;
import static org.deiverbum.liturgiacatolica.utils.Constants.OL_URL;
import static org.deiverbum.liturgiacatolica.utils.Constants.ORACION;
import static org.deiverbum.liturgiacatolica.utils.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRE_ANT;
import static org.deiverbum.liturgiacatolica.utils.Constants.PRIMERA_LECTURA;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_LOWER;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_R;
import static org.deiverbum.liturgiacatolica.utils.Constants.RESP_V;
import static org.deiverbum.liturgiacatolica.utils.Constants.SALMODIA;
import static org.deiverbum.liturgiacatolica.utils.Constants.SALUDO_OFICIO;
import static org.deiverbum.liturgiacatolica.utils.Constants.SEGUNDA_LECTURA;
import static org.deiverbum.liturgiacatolica.utils.Constants.SEPARADOR;

public class OficioActivity extends AppCompatActivity {

    private static final String TAG = "OficioActivity";
    Spanned strContenido;
    JsonObjectRequest jsonObjectRequest;
    TextView mTextView;
    int i = 0;
    int x = 0;
    TextToSpeech tts;
    private Utils utilClass;
    private RequestQueue requestQueue;
    private String strFechaHoy;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_oficio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilClass = new Utils();
        //final TextView mTextView = findViewById(R.id.txt_breviario);
        mTextView = (ZoomTextView) findViewById(R.id.txt_breviario);

        //   TextView textView = (TextView)findViewById(R.id.textView);  //tambien probe con la linea anterior pero no funciona
/*
        if (this.getIntent().getExtras() != null) {
             strFechaHoy = getIntent().getStringExtra("FECHA");
        }else{
            strFechaHoy=utilClass.getHoy();

        }
*/
        strFechaHoy = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();


        Log.i(TAG, strFechaHoy);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(OficioActivity.this);
        progressDialog.setMessage(PACIENCIA);
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, OL_URL + strFechaHoy, "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.i(TAG, response.toString());
                        String resp = showOficio(response);
                        strContenido = Utils.fromHtml(resp);
                        mTextView.setText(Utils.fromHtml(resp.replaceAll(SEPARADOR, "")));
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
        progressDialog.show();
    }

    protected String showOficio(JSONObject jsonDatos) {
        StringBuilder sb = new StringBuilder();
        try {

            //Objetos
            JSONObject jsonBreviario = jsonDatos.getJSONObject("breviario");
            JSONObject jsonInfo = jsonBreviario.getJSONObject("info");
            JSONObject jsonContenido = jsonBreviario.getJSONObject("contenido");
            JSONObject salmoUno = jsonContenido.getJSONObject("salmos").getJSONObject("s1");
            JSONObject salmoDos = jsonContenido.getJSONObject("salmos").getJSONObject("s2");
            JSONObject salmoTres = jsonContenido.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = jsonContenido.getJSONObject("biblica");
            JSONObject patristica = jsonContenido.getJSONObject("patristica");

            //Info inicial
            String infoFecha = jsonInfo.getString("fecha") + BRS;
            String infoTiempo = "<h1>" + jsonInfo.getString("tiempo") + "</h1>";
            String infoSemana = "<h3>" + jsonInfo.getString("semana") + "</h3>";
            String infoSalterio = jsonInfo.getString("salterio") + BRS;
            String infoMensaje = jsonInfo.getString("mensaje");

            //Contenido

            String txtAntifona = PRE_ANT + utilClass.getFormato(jsonContenido.getString("antifona")) + BRS;
            String txtInvitatorio = utilClass.getFormato(jsonContenido.getString("invitatorio"));
            String txtVida = "";


            if (!jsonContenido.getString("vida").equals("")) {
                txtVida = CSS_SM_A + jsonContenido.getString("vida") + CSS_SM_Z + BRS;
            }

            String txtSanto = "";


            if (!jsonContenido.getString("santo").equals("")) {
                txtSanto = CSS_SM_A + jsonContenido.getString("santo") + CSS_SM_Z + BRS;
            }

            String txtHimno = HIMNO + utilClass.getFormato(jsonContenido.getString("himno")) + BRS;
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

            //Lecturas
            String txtResponsorio = jsonContenido.getString("responsorio");
            if (!utilClass.isNull(txtResponsorio)) {
                String[] arrResponsorio = txtResponsorio.split("\\|");
                txtResponsorio = RESP_V + arrResponsorio[0] + BR + RESP_R + arrResponsorio[1] + BRS;
            }
            //Bíblica
            String txtBiblicaFuente = PRIMERA_LECTURA + biblica.getString("libro") +
                    CSS_RED_A + NBSP_4 +
                    biblica.getString("capitulo") + ", " + biblica.getString("v_inicial") + biblica.getString("v_final")
                    + CSS_RED_Z + BRS;
            String txtBiblicaTema = CSS_RED_A + biblica.getString("tema") + CSS_RED_Z;
            String txtBiblicaTexto = biblica.getString("texto");
            String txtBiblicaRef = CSS_RED_A + RESP_LOWER + NBSP_2 + biblica.getString("ref") + CSS_RED_Z + BRS;
            String txtBiblicaResponsorio = biblica.getString("responsorio");

            //Hay que construir el responsorio. Los responsorios son recibidos en forma de matriz y en base a un código son desplegados
            String txtBiblicaResponsorioFinal = "";
            if (txtBiblicaResponsorio != null && !txtBiblicaResponsorio.isEmpty() && !txtBiblicaResponsorio.equals("null")) {

                String[] arrPartes = txtBiblicaResponsorio.split("\\|");
                txtBiblicaResponsorioFinal = utilClass.getResponsorio(arrPartes, 1);
            }


            //Patrística
            String txtPadres;

            String txtPadresObra = patristica.getString("padre") + ", " +
                    patristica.getString("obra");
            String txtPadresFuente = BR + CSS_RED_A + CSS_SM_A + "(" + patristica.getString("fuente") + ")" + CSS_SM_Z +
                    BRS + patristica.getString("tema") + CSS_RED_Z;

            String txtPadresTexto = patristica.getString("texto");
            String txtPadresRef = CSS_RED_A + RESP_LOWER + " " + patristica.getString("ref") + CSS_RED_Z + BRS;
            String txtPadresResponsorio = patristica.getString("responsorio");
            String txtPadresRespFinal = "";
            if (txtPadresResponsorio != null && !txtPadresResponsorio.isEmpty() && !txtPadresResponsorio.equals("null")) {

                String[] arrParts = txtPadresResponsorio.split("\\|");
                txtPadresRespFinal = utilClass.getResponsorio(arrParts, 1);
            }

            String txtOracion = ORACION + utilClass.getFormato(jsonContenido.getString("oracion"));


            sb.append(infoFecha);
            sb.append(infoTiempo);
            sb.append(infoSemana);
            sb.append(infoSalterio);
            sb.append(infoMensaje);

            sb.append(OL_TITULO);
            sb.append(infoMensaje);

            if (!txtSanto.equals("")) {
                sb.append("<h3>");
                sb.append(txtSanto);
                sb.append("</h3>");
            }


            if (!txtVida.equals("")) {
                sb.append(txtVida);
                sb.append(SEPARADOR);
            }

            sb.append(INVITATORIO);
            sb.append(SALUDO_OFICIO);
            sb.append(txtAntifona);
            sb.append(txtInvitatorio);
            sb.append(FIN_SALMO);
            sb.append(BRS);
            sb.append(utilClass.getAntifonaLimpia(txtAntifona));
            sb.append(SEPARADOR);
            sb.append(txtHimno);
            sb.append(SEPARADOR);
            sb.append(SALMODIA);
            sb.append(salmoUnoCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoDosCompleto);
            sb.append(SEPARADOR);
            sb.append(salmoTresCompleto);
            sb.append(SEPARADOR);


            sb.append(txtResponsorio);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaFuente);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaTema);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaTexto);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaRef);
            sb.append(SEPARADOR);
            sb.append(txtBiblicaResponsorioFinal);
            sb.append(SEPARADOR);
            sb.append(SEGUNDA_LECTURA);
            sb.append(txtPadresObra);
            sb.append(SEPARADOR);
            sb.append(txtPadresFuente);
            sb.append(SEPARADOR);
            sb.append(txtPadresTexto);
            sb.append(SEPARADOR);
            sb.append(txtPadresRef);
            sb.append(SEPARADOR);
            sb.append(txtPadresRespFinal);

            sb.append(SEPARADOR);

            sb.append(txtOracion);

            //           }
        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return sb.toString();
    }


}
