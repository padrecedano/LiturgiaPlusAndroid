package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
            getJSON(OL_URL + getFecha());

        } else if (id == R.id.nav_la) {
            getJSON(LA_URL + getFecha());

        } else if (id == R.id.nav_hi) {
            getJSON(HI1_URL + getFecha());

        } else if (id == R.id.nav_vi) {
            getJSON(H4_URL + getFecha());

        } else if (id == R.id.nav_co) {
            textViewToChange.setText("Próximamente...");
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
                    showOficio(js_arr);
                    break;
                case 2:
                    showLaudes(js_arr);
                    break;

                case 31:
                    showHi(js_arr);
                    break;

                case 4:
                    showVisperas(js_arr);
                    break;

                default:
                    break;
            }

        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
        }
    }


    protected void showLaudes(JSONArray js_arr) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
        StringBuilder sb = new StringBuilder();

        try {
            JSONObject json_todo = js_arr.getJSONObject(0);
            JSONObject liturgia = json_todo.getJSONObject("liturgia");
            JSONObject js_info = liturgia.getJSONObject("info");
            JSONObject lh = liturgia.getJSONObject("lh");
            JSONObject hora = lh.getJSONObject("la");
            JSONObject s1 = hora.getJSONObject("salmos").getJSONObject("s1");
            JSONObject s2 = hora.getJSONObject("salmos").getJSONObject("s2");
            JSONObject s3 = hora.getJSONObject("salmos").getJSONObject("s3");
            JSONObject biblica = hora.getJSONObject("biblica");
            JSONObject ce = hora.getJSONObject("ce");

            JSONObject preces = hora.getJSONObject("preces");

            String sVida = "";

            int id_tiempo = Integer.parseInt(js_info.getString("id_tiempo"));
            switch (id_tiempo) {
                case 9:
                    sVida = "<h3>" + hora.getString("txt_santo") + "</h3>" + CSS_SM_A + hora.getString("txt_vida") + CSS_SM_Z + BRS; // they are executed if variable == c1
                    break;
                default:
                    break;
            }

            String sAntifonaInv = PRE_ANT + hora.getString("antifonai") + BR;
            String sHimno = HIMNO + getFormato(hora.getString("himno"));

            //Contenido completo de cada salmo... ufff

            String sOrden1 = s1.getString("orden");
            String sAntifona1 = s1.getString("antifona");
            String sRef1 = s1.getString("txt_ref");
            String sTema1 = s1.getString("tema");
            String sIntro1 = s1.getString("txt_intro");
            String sParte1 = s1.getString("parte");
            String sSalmo1 = s1.getString("txt_salmo");
            String sSalmoCompleto1 = getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = s2.getString("antifona");
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = s3.getString("antifona");
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

            /* Se ha creado una especie de contrato en la que a través de un código numérico (nForma)
            se da formato a cada responsorio, usando el método getResponsorio
             */
/*
            int nForma=biblica.getInt("id_forma");

            String sBibilicaResp=biblica.getString("txt_responsorio");
            if (!isNull(sBibilicaResp)) {
                String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                sBibilicaResp=getResponsorio(respArray,nForma);
            }

*/
//            String sLecturaBreve=CSS_RED_A+LECTURA_BREVE+NBSP_4+biblica.getString("lbreve_ref")+CSS_RED_Z+BRS+
//                    biblica.getString("txt_lbreve")+BRS;
            //        String sRespLBreve=RESP_BREVE+BRS+sRespLBreve;


            String sLecturaBreve = "Hay un error con la Lectura Breve, por favor comunícala al desarrollador: padre.cedano@gmail.com";
            String sRespLBreve = "";
            String sForma = biblica.getString("id_forma");
            if (sForma != null && !sForma.isEmpty())

            {

                int nForma = Integer.parseInt(sForma);//biblica.getInt("id_forma");

                String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                sRespLBreve = getResponsorio(respArray, nForma);
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
//            String sPrecesIntro=PRECES+preces.getString("txt_preces_intro")+BRS;
//            String sPreces=getFormato(preces.getString("txt_preces"))+BRS;

            String[] introArray = preces.getString("txt_preces_intro").split("\\|");
            String sPrecesCuerpo = getFormato(preces.getString("txt_preces"));

            String sPreces = getPreces(introArray, sPrecesCuerpo);


            String sOracion = ORACION + getFormato(hora.getString("oracion"));

            //Agregamos el contenido al Stringbuilder y lo mostramos en nuestro textview usando el formta html :)


            sb.append(LA_TITULO);
            sb.append(sVida);

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


            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(sHoy + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);

        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
            e.printStackTrace();
        }

    }


    protected void showOficio(JSONArray js_arr) {
        TextView textViewToChange = (TextView) findViewById(R.id.txt_container);
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

            String sHimno = HIMNO + getFormato(hora.getString("himno")) + BRS;
            String sOrden1 = s1.getString("orden");
            String sAntifona1 = s1.getString("antifona");
            String sRef1 = s1.getString("txt_ref");
            String sTema1 = s1.getString("tema");
            String sIntro1 = s1.getString("txt_intro");
            String sParte1 = s1.getString("parte");

            String sSalmo1 = s1.getString("txt_salmo");
            String sSalmoCompleto1 = getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = s2.getString("antifona");
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = s3.getString("antifona");
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

            //Lecturas
            String sRespOl = hora.getString("resp");
            if (!isNull(sRespOl)) {
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
                txt_biblica_r = getResponsorio(br_parts, 1);
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
                txt_patristica_r = getResponsorio(pr_parts, 1);
            }

            String sOracion = ORACION + getFormato(hora.getString("oracion"));

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

            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(sHoy + "<br><br>" + sb.toString()));
            textViewToChange.scrollTo(0, 0);
        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());

            e.printStackTrace();
        }

    }

    protected void showHi(JSONArray js_arr) {
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

            String sHimno = HIMNO + getHimnos(hora.getString("himnos")) + BRS;

            String sOrden1 = s1.getString("orden");
            String sAntifona1 = s1.getString("antifona");
            String sRef1 = s1.getString("txt_ref");
            String sTema1 = s1.getString("tema");
            String sIntro1 = s1.getString("txt_intro");
            String sParte1 = s1.getString("parte");

            String sSalmo1 = s1.getString("txt_salmo");
            String sSalmoCompleto1 = getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = s2.getString("antifona");
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = s3.getString("antifona");
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

            String sBiblicaResp = biblica.getString("txt_responsorio");
            String sResponsorio = "";
            if (sBiblicaResp != null && !sBiblicaResp.isEmpty() && !sBiblicaResp.equals("null")) {

                String[] respArray = sBiblicaResp.split("\\|");
                sResponsorio = getResponsorio(respArray, 31);
            }

            String sBiblica = CSS_RED_A + LECTURA_BREVE + NBSP_4
                    + biblica.getString("lbreve_ref") + CSS_RED_Z + BRS + biblica.getString("txt_lbreve") + BRS;

            String sOracion = ORACION + getFormato(hora.getString("oracion"));

            sb.append(HI_TITULO + BRS + "Por el momento sólo se muestra la Hora Tercia" + BRS);
            sb.append(sHimno);
            sb.append(SALMODIA);
            sb.append(sSalmoCompleto1);
            sb.append(sSalmoCompleto2);
            sb.append(sSalmoCompleto3);

            sb.append(sBiblica);
            sb.append(sResponsorio);
            sb.append(sOracion);

            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(sHoy + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);

        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
            e.printStackTrace();
        }

    }


    protected void showVisperas(JSONArray js_arr) {
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
            String sSalmoCompleto1 = getSalmoCompleto(sOrden1, sAntifona1, sRef1, sTema1, sIntro1, sParte1, sSalmo1);

            String sOrden2 = s2.getString("orden");
            String sAntifona2 = s2.getString("antifona");
            String sRef2 = s2.getString("txt_ref");
            String sTema2 = s2.getString("tema");
            String sIntro2 = s2.getString("txt_intro");
            String sParte2 = s2.getString("parte");
            String sSalmo2 = s2.getString("txt_salmo");
            String sSalmoCompleto2 = getSalmoCompleto(sOrden2, sAntifona2, sRef2, sTema2, sIntro2, sParte2, sSalmo2);

            String sOrden3 = s3.getString("orden");
            String sAntifona3 = s3.getString("antifona");
            String sRef3 = s3.getString("txt_ref");
            String sTema3 = s3.getString("tema");
            String sIntro3 = s3.getString("txt_intro");
            String sParte3 = s3.getString("parte");
            String sSalmo3 = s3.getString("txt_salmo");
            String sSalmoCompleto3 = getSalmoCompleto(sOrden3, sAntifona3, sRef3, sTema3, sIntro3, sParte3, sSalmo3);

//            String sAntifonaInv = PRE_ANT+hora.getString("antifonai")+BR;
            String sHimno = HIMNO + getFormato(hora.getString("himno"));
            String sLecturaBreve = "Hay un error con la Lectura Breve, por favor comunícala al desarrollador: padre.cedano@gmail.com";
            String sRespLBreve = "";
            String sForma = biblica.getString("id_forma");
            if (sForma != null && !sForma.isEmpty())

            {

                int nForma = Integer.parseInt(sForma);//biblica.getInt("id_forma");

                String[] respArray = biblica.getString("txt_responsorio").split("\\|");
                sRespLBreve = getResponsorio(respArray, nForma);
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
            String sPrecesCuerpo = getFormato(preces.getString("txt_preces"));

            String sPreces = getPreces(introArray, sPrecesCuerpo);


            String sOracion = ORACION + getFormato(hora.getString("oracion"));

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

            textViewToChange.setMovementMethod(new ScrollingMovementMethod());
            textViewToChange.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            textViewToChange.setText(Html.fromHtml(sHoy + "<br><br>" + sb.toString()), TextView.BufferType.SPANNABLE);
            textViewToChange.scrollTo(0, 0);

        } catch (JSONException e) {
            textViewToChange.setText(e.getMessage());
            e.printStackTrace();
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
        sFinal = introArray[0] + BRS + "<i>" + introArray[1] + "</i>" + BRS + getFormato(sPreces) + BRS + introArray[2];
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