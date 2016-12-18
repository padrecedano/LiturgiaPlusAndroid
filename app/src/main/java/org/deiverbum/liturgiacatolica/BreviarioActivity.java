package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.deiverbum.liturgiacatolica.Constants.*;


public class BreviarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDateChangeListener {
    CalendarView calendar = null;
    private String sHoy = getFecha();//"201610099";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breviario);
        // setContentView(R.layout.inicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //       calendar=(CalendarView)findViewById(R.id.calendar);
        //       calendar.setOnDateChangeListener(this);

/*        Button next = (Button) findViewById(R.id.btn_lh);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LiturgiaHoras.class);
                startActivityForResult(myIntent, 0);
            }

        });
*/

    }


    @Override
    public void onSelectedDayChange(CalendarView view, int year,
                                    int monthOfYear, int dayOfMonth) {
        Calendar then = new GregorianCalendar(year, monthOfYear, dayOfMonth);

        Toast.makeText(this, year + "*" + then.getTime().toString(), Toast.LENGTH_LONG)
                .show();
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
        getMenuInflater().inflate(R.menu.breviario, menu);
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
        final TextView textViewToChange = (TextView) findViewById(R.id.txt_container);

        if (id == R.id.nav_ol) {
            Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
            startActivity(i);


//            getJSON(OL_URL + getFecha());
//            Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
//            startActivity(i);

        } else if (id == R.id.nav_la) {
//            getJSON(LA_URL + getFecha());
            Intent i = new Intent(BreviarioActivity.this, LaudesActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_hi) {
//            getJSON(HI1_URL + getFecha());
            Intent i = new Intent(BreviarioActivity.this, TerciaActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_vi) {
//            getJSON(H4_URL + getFecha());
            Intent i = new Intent(BreviarioActivity.this, VisperasActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_co) {
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container) != null) {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.

                // Create a new Fragment to be placed in the activity layout
                OficioFragment firstFragment = new OficioFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                firstFragment.setArguments(getIntent().getExtras());
                FragmentManager fragmentManager = getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragment_container, firstFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                // Add the fragment to the 'fragment_container' FrameLayout
            }

//            textViewToChange.setText("Próximamente...");
//            newFragment.setArguments();

//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
//            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);

// Commit the transaction
//            transaction.commit();
        } else if (id == R.id.nav_mi) {
            textViewToChange.setText("Próximamente...");

        } else if (id == R.id.nav_ri) {
            textViewToChange.setText("Próximamente...");
        } else if (id == R.id.nav_tr) {
            textViewToChange.setText("Próximamente...");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getJSON(String url) {

        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            ProgressDialog myProgressDialog;//= new  ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BreviarioActivity.this, "Por favor espere...", "cargando", true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                // Mostrar errores
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Operaciones http
                    String uri = params[0];

                    BufferedReader bufferedReader;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json).append("\n");
                        }
                        bufferedReader.close();
                        con.disconnect();
                        return sb.toString().trim();

                    } catch (Exception e) {
                        return null;
                    }
                } else
                    return "Parece que tu equipo no está conectado a internet. En esta fase de la aplicación la conexión a internet es necesaria" +
                            " para su funcionamiento. En posteriores versiones activaremos el uso sin esta limitación. Perdona las molestias.";


            }

            @Override
            protected void onPostExecute(String s) {
                final TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
                show(s);
                super.onPostExecute(s);
                loading.dismiss();
            }

        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);

        super.onSaveInstanceState(savedInstanceState);
        // Save state to the savedInstanceState
        savedInstanceState.putString("MyString", String.valueOf(textViewToChange.getText()));
        textViewToChange.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);

        super.onRestoreInstanceState(savedInstanceState);
        // Restore state from savedInstanceState
        //*** Tengo un problema aquí, cuando se gira la pantalla se pierde el color rojo de los textos, queda el texto en negro
        String myString = savedInstanceState.getString("MyString");

        textViewToChange.setMovementMethod(new ScrollingMovementMethod());
        textViewToChange.setText(myString);


    }


    protected void show(String s) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
        int nHora = 0;

        try {
            JSONArray js_arr = new JSONArray(s);

            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject js_info = liturgia.getJSONObject("info");

            // De acuerdo a un id de la hora recibido desde el servidor se invoca el método adecuado
            nHora = Integer.parseInt(js_info.getString("id_hora"));

            switch (nHora) {
                case 1:
 //                   showOficio(js_arr);
                    break;
                case 2:
 //                   showLaudes(js_arr);
                    break;

                case 31:
 //                   showHi(js_arr);
                    break;

                case 4:
  //                  showVisperas(js_arr);
                    break;

                default:
                    break;
            }

        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
        }
    }









    /**
     * Método que organiza todos los componentes de un salmo dado, evaluando los que son nulos para evitar espacios vacíos
     *
     * @param sOrden    El orden del salmo: 1, 2, 3
     * @param sAntifona El texto de la antífona
     * @param sRef      La referencia del salmo
     * @param sTema     El tema (presente sólo en algunos salmos)
     * @param sIntro    El texto introductorio (presente sólo en algunos salmos)
     * @param sSalmo    El texto del salmo propiamente dicho
     * @return Una cadena formateda con el salmo completo
     */

    private String getSalmoCompleto(String sOrden, String sAntifona, String sRef, String sTema, String sIntro, String sParte, String sSalmo) {
        if (sRef != null && !sRef.isEmpty()) {
            sRef = CSS_RED_A + sRef + CSS_RED_Z + BRS;

        } else {
            sRef = "";
        }


        if (sTema != null && !sTema.isEmpty()) {
            sTema = CSS_RED_A + CSS_SM_A + sTema.toUpperCase() + CSS_SM_Z + CSS_RED_Z + BRS;

        } else {
            sTema = "";
        }

        if (sIntro != null && !sIntro.isEmpty()) {
            sIntro = CSS_SM_A + getFormato(sIntro) + CSS_SM_Z + BRS;

        } else {
            sIntro = "";
        }

        if (sParte != null && !sParte.isEmpty()) {
            sParte = CSS_RED_A + sParte + CSS_RED_Z + BRS;

        } else {
            sParte = "";
        }


        String sSalmoCompleto = CSS_RED_A + PRE_ANT + sOrden + ". " + CSS_RED_Z + sAntifona + BRS + getFormato(sRef) +
                sTema + sIntro + sParte + getFormato(sSalmo) + BRS + CSS_RED_A + PRE_ANT + CSS_RED_Z + sAntifona + BRS;

        return sSalmoCompleto;


    }

    /**
     * Método que da formato a algunos textos recibidos desde el servidor <br />
     * He creado una especie de contrato entre la API y la APP para reducir el volúmen de datos <br />
     * que se transmite y formatear el contenido de forma local. <br />
     * El reemplazo de caracteres es como sigue: <br />
     * § : salto de párrafo <br />
     * _ : salto de línea y sangría (para los salmos y algunos himnos)
     * ~ : salto de línea sin sangría
     * ¦ : varios espacios en blanco (una especia de tabulador), se usa en algunas referencias de textos
     * ∞ : la rúbrica <i>Se pueden añadir algunas intenciones libres.</i>
     * ≠ : salto de línea y sangría con un guión rojo, para las preces
     * ℇ : rúbrica litúrgica "O bien"
     * † : cruz en antífonas
     * ƞ : la N. de color rojo que sustituye el nombre del Papa o del Obispo
     * Ɽ : la R./ de color rojo (responsorio)
     * ⟨ : paréntesis de apertura en rojo
     * ⟩ : paréntesis de cierre en rojo
     * ã : (T. P. Aleluya.):
     *
     * @param sOrigen El texto del salmo como es recibido del servidor (los saltos de línea vienen indicados por '_' y de párrafo por '§'
     * @return Una cadena con el salmo formateado.
     */

    private String getFormato(String sOrigen) {
        String sFormateado;

        sFormateado = sOrigen
                .replace("_", NBSP_SALMOS)
                .replace("§", BRS)
                .replace("~", BR)
                .replace("¦", NBSP_4)
                .replace("≠", PRECES_R)
                .replace("∞", PRECES_IL)
                .replace("ℇ", OBIEN)
                .replace("†", CSS_RED_A+" † "+CSS_RED_Z)
                .replace("ƞ", CSS_RED_A+" N. "+CSS_RED_Z)
                .replace("Ɽ", CSS_RED_A+" R. "+CSS_RED_Z)
                .replace("⟨", CSS_RED_A+"("+CSS_RED_Z)
                .replace("⟩", CSS_RED_A+")"+CSS_RED_Z);

        return sFormateado;
    }


    /**
     * Método que evalua si una cadena no tiene datos <br />
     *
     * @param sOrigen La cadena a evaluar
     * @return Verdadero o Falso.
     */

    private Boolean isNull(String sOrigen) {
        return !(sOrigen != null && !sOrigen.isEmpty() && !sOrigen.equals("null"));
    }


    /**
     * Método que devuelve la fecha del sistema
     *
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    private String getFecha() {
        Date newDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String sHoyNew = format.format(new Date());
        return sHoyNew;
    }

    /**
     * Método que crea las preces *** terminar descripción luego
     *
     * @param introArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param sPreces    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    private String getPreces(String introArray[], String sPreces) {
        String sFinal;
        sFinal = introArray[0] + BRS + "<i>" + introArray[1] + "</i>" + BRS + getFormato(sPreces) +BRS + introArray[2];
        return sFinal;
    }


    /**
     * Método que crea las preces *** terminar descripción luego
     *
     * @param sOrigen Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    private String getHimnos(String sOrigen) {
        String sFinal = "";

        String[] himnoArray = sOrigen.split("\\|");
        String[] himno1Array = himnoArray[0].split("\\_");
        String[] himno2Array = himnoArray[1].split("\\_");
        switch (Integer.parseInt(himno1Array[0])) {
            case 0:
                sFinal = CSS_RED_A + "En los domingos y solemnidades:" + CSS_RED_Z +
                        BRS + getFormato(himno1Array[1]);
                break;
            default:
                break;
        }

        switch (Integer.parseInt(himno2Array[0])) {
            case 2:
                sFinal = sFinal + BRS + CSS_RED_A + "O bien, fuera de los domingos y de las solemnidades:" + CSS_RED_Z +
                        BRS + getFormato(himno2Array[1]);
                break;
            default:
                break;
        }

        return sFinal;
    }


    /**
     * Método que crea la cadena completa de un responsorio dado
     *
     * @param respArray Una matriz con las diferentes partes del responsorio. Antes de pasar el parámetro evauluar que la matriz no sea nula
     * @param nForma    Un valor numérico para indicar de que forma es el responsorio y actuar en consecuencia
     * @return Una cadena con el responsorio completo, con sus respectivos V. y R.
     */

    private String getResponsorio(String respArray[], int nForma) {
        String sResponsorio;
        switch (nForma) {
// Modificar los case, usando el modelo: 6001230
            case 1:
                sResponsorio =
                        RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                RESP_V + respArray[2] + BRS +
                                RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;

            case 2:
                //0-1-2-1
                sResponsorio =
                        RESP_R + respArray[0] + RESP_A + respArray[1] + BRS +
                                RESP_V + respArray[2] + BRS +
                                RESP_R + Character.toUpperCase(respArray[1].charAt(0)) + respArray[1].substring(1);

                break;


            case 6001230:
                //6 partes distribuidas así: 0-0-1-2-3-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 4) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[2] + BRS +
                                    RESP_V + respArray[3] + BR +
                                    RESP_R + respArray[0] + BRS;
                } else {
                    sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + sHoy + BRS;
                }
                break;


            case 6001020:
                //6 partes distribuidas así: 0-0-1-0-2-0, de ahí el código 6001230... si no, imposible entenderse
                // Suele ser el modelo de responsorio para Laudes
                if (respArray.length == 3) {

                    sResponsorio =
                            RESP_V + respArray[0] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[1] + BR +
                                    RESP_R + respArray[0] + BRS +
                                    RESP_V + respArray[2] + BR +
                                    RESP_R + respArray[0] + BRS;
                } else {
                    sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + sHoy + BRS;
                }
                break;


            case 4:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[1] + BR +
                                RESP_R + respArray[0] + BRS +
                                RESP_V + respArray[2] + BR +
                                RESP_R + respArray[0] + BRS;
                break;

            case 31:
                sResponsorio =
                        RESP_V + respArray[0] + BR +
                                RESP_R + respArray[1] + BRS;
                break;


            default:
                sResponsorio = ERR_RESPONSORIO + BRS + "Error " + respArray.length + " de responsorio en la fecha: " + sHoy + BRS;
                break;
        }
        return sResponsorio;


    }
}