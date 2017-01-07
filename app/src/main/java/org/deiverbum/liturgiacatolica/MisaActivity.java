package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;
import utils.VolleyErrorHelper;

import static org.deiverbum.liturgiacatolica.Constants.BR;
import static org.deiverbum.liturgiacatolica.Constants.BRS;
import static org.deiverbum.liturgiacatolica.Constants.CSS_B_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_B_Z;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.liturgiacatolica.Constants.NBSP_4;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static utils.MisaConstantes.ADVIENTO;
import static utils.MisaConstantes.ALELUYA;
import static utils.MisaConstantes.ALELUYA_R;
import static utils.MisaConstantes.ANT_COMUNION;
import static utils.MisaConstantes.ANT_TITULO;
import static utils.MisaConstantes.EVANGELIO;
import static utils.MisaConstantes.LECTURA_1;
import static utils.MisaConstantes.LECTURA_2;
import static utils.MisaConstantes.L_EUCARISTICA;
import static utils.MisaConstantes.NO_CREDO;
import static utils.MisaConstantes.NO_GLORIA;
import static utils.MisaConstantes.O_COLECTA;
import static utils.MisaConstantes.O_COMUNION;
import static utils.MisaConstantes.O_DOMINICAL;
import static utils.MisaConstantes.O_FIELES;
import static utils.MisaConstantes.PLEGARIA;
import static utils.MisaConstantes.PREFACIO;
import static utils.MisaConstantes.R_COMUNION;
import static utils.MisaConstantes.R_INICIALES;
import static utils.MisaConstantes.SALMO_R;
import static utils.MisaConstantes.SI_CREDO;
import static utils.MisaConstantes.SI_GLORIA;


public class MisaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String URL_BASE = "http://deiverbum.org/api/beta/";
    private static final String URL_JSON = "misa";
    private static final String TAG = "PostAdapter";
    ArrayAdapter adapter;
    ListView listView;
    String items;
    JsonObjectRequest jsArrayRequest;
    private Utils utilClass;
    private RequestQueue requestQueue;

    //Ojo solución a fromHTML deprecated... ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utilClass = new Utils();

        setContentView(R.layout.activity_misa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Crear y setear adaptador
        adapter = new PostAdapter(this);



    /*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
    */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView mTextView = (TextView) findViewById(R.id.txt_container);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//            mTextView.setMovementMethod(new ScrollingMovementMethod());

        requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(MisaActivity.this);
        progressDialog.setMessage(PACIENCIA);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        adapter.notifyDataSetChanged();
                        mTextView.setText(fromHtml(items));
                        progressDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = VolleyErrorHelper.getMessage(error, getApplicationContext());
                        mTextView.setText(Utils.fromHtml(sError));
                        Log.d(TAG, sError);
                        progressDialog.dismiss();

                    }
                }
        );
        jsArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
        progressDialog.show();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.misa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homilias) {
            Intent i = new Intent(MisaActivity.this, HomiliasActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_evangelio) {

            Intent i = new Intent(MisaActivity.this, EvangelioActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_santo) {
            utilClass = new Utils();
            utilClass.mensajeTemporal(getApplicationContext());

        } else if (id == R.id.nav_rituales) {
            utilClass = new Utils();
            utilClass.mensajeTemporal(getApplicationContext());

            //            final TextView mTextView = (TextView) findViewById(R.id.txt_container);
            //            mTextView.setMovementMethod(new ScrollingMovementMethod());

    /*            requestQueue= Volley.newRequestQueue(this);

                // Nueva petición JSONObject
                jsArrayRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL_BASE + "homilias",
                        "",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                items = parseJson(response);
                                adapter.notifyDataSetChanged();
                                mTextView.setText(fromHtml(items));

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                            }
                        }
                );

                // Añadir petición a la cola
                requestQueue.add(jsArrayRequest);
    */
                /*            // Handle the camera action
                final TextView mTextView = (TextView) findViewById(R.id.txt_container);
    // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this);
                String url ="http://www.deiverbum.org/api/misa";

    // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                mTextView.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!"+error.getLocalizedMessage());
                    }
                });
    // Add the request to the RequestQueue.
                queue.add(stringRequest);
    */
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            final TextView mTextView = (TextView) findViewById(R.id.txt_container);

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://www.deiverbum.org/api/homilias";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            mTextView.setText("Response is: " + fromHtml(response));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mTextView.setText("That didn't work!"+error.getLocalizedMessage());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(MisaActivity.this, HomiliasActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String parseJson(JSONObject jsonObject) {
        // Variables locales
        String test = "";
        JSONArray jsonArray = null;
        StringBuilder sb = new StringBuilder();

        String sTiempo = "";
        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("misa");

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);
                    JSONObject oInfo = objeto.getJSONObject("info");
                    JSONObject oInicio = objeto.getJSONObject("inicio");
                    JSONObject oPalabra = objeto.getJSONObject("palabra");
                    JSONObject oEucaristia = objeto.getJSONObject("eucaristia");
                    JSONObject oPrefacio = oEucaristia.getJSONObject("prefacio");
                    JSONObject oComunion = oEucaristia.getJSONObject("comunion");

                    JSONArray palabraArray = oPalabra.getJSONArray("lecturas");

                    int nTiempo = Integer.parseInt(oInfo.getString("tiempo"));
                    String sMensaje = oInfo.getString("mensaje");
                    if (sMensaje != null && !sMensaje.isEmpty()) {
                        sb.append(sMensaje + BRS + "<hr>");
                    }
                    sTiempo = getTiempo(nTiempo);
                    String sAntifona = ANT_TITULO + oInicio.getString("ant_txt") + BR
                            + CSS_RED_A + "(" + oInicio.getString("ant_ref") + ")" + CSS_RED_Z + BRS;
                    String sColecta = O_COLECTA + utilClass.getFormato(oInicio.getString("colecta"));
                    sb.append(sTiempo);
                    sb.append(R_INICIALES);
                    sb.append(sAntifona);
                    sb.append(sColecta);
                    int nGloria = oInicio.getInt("gloria");
                    if (nGloria == 1) {
                        sb.append(SI_GLORIA);
                    } else {
                        sb.append(NO_GLORIA);
                    }

                    sb.append(getLectura(palabraArray));
                    int nCredo = oPalabra.getInt("credo");
                    if (nCredo == 1) {
                        sb.append(SI_CREDO);
                    } else {
                        sb.append(NO_CREDO);
                    }
                    sb.append(L_EUCARISTICA);

                    String sOfrendas = O_FIELES + utilClass.getFormato(oEucaristia.getString("ofrendas"));
                    sb.append(sOfrendas);
                    String sPrefacio = PREFACIO + utilClass.getFormato(oPrefacio.getString("nombre"))
                            + BR + CSS_RED_A + utilClass.getFormato(oPrefacio.getString("tema")) + CSS_RED_Z
                            + BRS + utilClass.getFormato(oPrefacio.getString("txt"));

                    sb.append(sPrefacio);
                    sb.append(PLEGARIA);

                    String sComunion = R_COMUNION + O_DOMINICAL
                            + ANT_COMUNION
                            + utilClass.getFormato(oComunion.getString("ant")) + BR
                            + CSS_RED_A + "(" + utilClass.getFormato(oComunion.getString("ref")) + ")" + CSS_RED_Z
                            + O_COMUNION
                            + utilClass.getFormato(oComunion.getString("txt"));

                    sb.append(sComunion);

                    //                   sb.append();


                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return sb.toString();
    }

    /**
     * Método que determina el tiempo litúrgico
     *
     * @param nTiempo Un número que identifica cada tiempo litúrgico en la BD
     * @return Una cadena con el tiempo litúrgico
     */

    private String getTiempo(int nTiempo) {
        String sTiempo = "";

        switch (nTiempo) {
            case 1:
                sTiempo = ADVIENTO;
                break;
            default:
                break;
        }
        return sTiempo;

        }

    /**
     * Método que determina crea los diferentes elementos de las lecturas de la Misa
     *
     * @param palabraArray Un número que identifica qué lectura es
     * @return Una cadena con la lectura formateada adecuadamente
     */

    private String getLectura(JSONArray palabraArray) throws JSONException

    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < palabraArray.length(); i++) {
            JSONObject oPalabra = palabraArray.getJSONObject(i);
            //           sb.append(jsonobject.toString());
            int nOrden = oPalabra.getInt("orden");
            switch (nOrden) {
                case 1:
                    sb.append(LECTURA_1);
                    sb.append(CSS_B_A).append("Lectura ").append(oPalabra.getString("libro")).append(BR).append(CSS_RED_A).append(oPalabra.getString("ref")).append(CSS_RED_Z).append(CSS_B_Z).append(BRS);
                    sb.append(CSS_RED_A).append(oPalabra.getString("intro")).append(CSS_RED_Z).append(BRS);
                    sb.append(utilClass.getFormato(oPalabra.getString("txt")));

                    break;

                case 2:
                    sb.append(SALMO_R);
                    sb.append(oPalabra.getString("libro") + NBSP_4);
                    sb.append(CSS_RED_A + oPalabra.getString("ref") + CSS_RED_Z + BR);
                    sb.append(oPalabra.getString("intro") + BRS);
                    sb.append(utilClass.getFormato(oPalabra.getString("txt")));
                    break;

                case 3:
                    sb.append(LECTURA_2);
                    sb.append(CSS_B_A).append("Lectura ").append(oPalabra.getString("libro")).append(BR).append(CSS_RED_A).append(oPalabra.getString("ref")).append(CSS_RED_Z).append(CSS_B_Z).append(BRS);
                    sb.append(CSS_RED_A).append(oPalabra.getString("intro")).append(CSS_RED_Z).append(BRS);
                    sb.append(utilClass.getFormato(oPalabra.getString("txt")));
                    break;

                case 4:
                    sb.append(ALELUYA).append(CSS_RED_A).append(oPalabra.getString("ref")).append(CSS_RED_Z)
                            .append(ALELUYA_R)
                            .append(CSS_RED_A).append("V./ ").append(CSS_RED_Z)
                            .append(utilClass.getFormato(oPalabra.getString("txt"))).append(BRS);
                    break;

                case 5:
                    sb.append(EVANGELIO);
                    sb.append(CSS_B_A).append("Lectura ").append(oPalabra.getString("libro")).append(BR).append(CSS_RED_A).append(oPalabra.getString("ref")).append(CSS_RED_Z).append(CSS_B_Z).append(BRS);
                    sb.append(CSS_RED_A).append(oPalabra.getString("intro")).append(CSS_RED_Z).append(BRS);
                    sb.append(utilClass.getFormato(oPalabra.getString("txt")));
                    break;

                default:
                    break;
            }
            //          String name = jsonobject.getString("intro");
            //          String url = jsonobject.getString("url");
            //           sb.append(name);
        }
            /*
            switch (nOrden) {
                case 1:
                    sb.append("PRIMERA LECTURA");
                    break;
                default:
                    break;
            }
    */
        return sb.toString();

    }

}
