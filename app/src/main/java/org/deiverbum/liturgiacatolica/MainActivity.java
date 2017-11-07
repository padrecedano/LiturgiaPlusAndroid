package org.deiverbum.liturgiacatolica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import utils.Utils;

/*import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

*/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemListener {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    TextView textView;
    String version;
    String strFechaHoy;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Utils utilClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        version = BuildConfig.VERSION_NAME + String.valueOf(BuildConfig.VERSION_CODE);



        /*
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
*/
        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        arrayList.add(new DataModel("Breviario", R.drawable.app_breviario_100x100, "#09A9FF"));
        arrayList.add(new DataModel("Misa", R.drawable.app_misa_100x100, "#3E51B1"));
        arrayList.add(new DataModel("Homilías", R.drawable.app_santo_100x100, "#673BB7"));
        arrayList.add(new DataModel("Santos", R.drawable.ic_home_breviario_new, "#4BAA50"));
        arrayList.add(new DataModel("Calendario", R.drawable.app_misa_100x100, "#F94336"));
        arrayList.add(new DataModel("Oraciones", R.drawable.ic_main_breviario, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 350);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/

/*
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
*/

        utilClass = new Utils();
        strFechaHoy = utilClass.getFecha();
        final TextView mTextView = (TextView) findViewById(R.id.txt_main);
        mTextView.setText(strFechaHoy + " " + "v. " + version);

        //Acción de los botones
/*
        final Button btnBreviario = (Button) findViewById(R.id.btn_breviario);
        btnBreviario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
/*
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "btnBreviario");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
/*
                Intent i = new Intent(MainActivity.this, BreviarioActivity.class);
                startActivity(i);

            }
        });

        final Button btnMisa = (Button) findViewById(R.id.btn_misa);
        btnMisa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName(TAG)
                        .putContentType("Misa")
                        .putContentId(v.toString()));

                Intent i = new Intent(MainActivity.this, MisaActivity.class);
                startActivity(i);

            }
        });


        final Button btnSanto = (Button) findViewById(R.id.btn_santo);
        btnSanto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Answers.getInstance().logContentView(new ContentViewEvent()
                        .putContentName(TAG)
                        .putContentType("Santos")
                        .putContentId(v.toString()));

                Intent i = new Intent(MainActivity.this, SantoActivity.class);
                startActivity(i);

            }
        });

        final Button btnOraciones = (Button) findViewById(R.id.btn_oraciones);
        btnOraciones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
/*
                Utils utilClass = new Utils();
                utilClass.mensajeTemporal(getApplicationContext());
*/
/*
                Intent i = new Intent(MainActivity.this, MainRecycler.class);
                startActivity(i);

            }
        });

*/
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
        getMenuInflater().inflate(R.menu.main, menu);
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

    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
        Intent i = null;
        utilClass.setFabric(item.text, TAG, strFechaHoy);


        switch (item.text) {

            case "Breviario":

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "btnBreviario");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "breviario");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                i = new Intent(getApplicationContext(), BreviarioActivity.class);
                startActivity(i);
                break;

            case "Misa":
                i = new Intent(MainActivity.this, MisaActivity.class);
                startActivity(i);
                break;

            case "Homilías":
                i = new Intent(MainActivity.this, HomiliasActivity.class);
                startActivity(i);
                break;

            case "Santos":
                i = new Intent(MainActivity.this, SantoActivity.class);
                startActivity(i);
                break;

            case "Calendario":
                i = new Intent(MainActivity.this, CalendarioActivity.class);
                startActivity(i);
                break;

            case "Oraciones":
                i = new Intent(MainActivity.this, OracionesActivity.class);
                startActivity(i);
                break;

            default:
                //               throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekArg);
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        utilClass.setFabric(item.getTitle().toString(), TAG, strFechaHoy);

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
