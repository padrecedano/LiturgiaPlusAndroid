package org.deiverbum.liturgiacatolica.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.deiverbum.liturgiacatolica.BuildConfig;
import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.data.DataModel;
import org.deiverbum.liturgiacatolica.data.RecyclerViewAdapter;
import org.deiverbum.liturgiacatolica.utils.Utils;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemListener {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    String version;
    String strFechaHoy;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Utils utilClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        version = BuildConfig.VERSION_NAME + String.valueOf(BuildConfig.VERSION_CODE);

        Fabric.with(this, new Crashlytics());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        int colorBreviario = getResources().getColor(R.color.colorBreviario);
        int colorMisa = getResources().getColor(R.color.colorMisa);
        int colorHomilias = getResources().getColor(R.color.colorHomilias);
        int colorSantos = getResources().getColor(R.color.colorSantos);
        int colorCalendario = getResources().getColor(R.color.colorCalendario);
        int colorOraciones = getResources().getColor(R.color.colorOraciones);

        arrayList.add(new DataModel("Breviario", R.drawable.ic_breviario_100x100, colorBreviario));
        arrayList.add(new DataModel("Misa", R.drawable.ic_misa_100x100, colorMisa));
        arrayList.add(new DataModel("Homilías", R.drawable.ic_homilias_100x100, colorHomilias));
        arrayList.add(new DataModel("Santos", R.drawable.ic_santos_100x100, colorSantos));
        arrayList.add(new DataModel("Calendario", R.drawable.ic_calendar2_100x100, colorCalendario));
        arrayList.add(new DataModel("Oraciones", R.drawable.ic_oraciones_100x100, colorOraciones));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /*
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         */

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 350);
        recyclerView.setLayoutManager(layoutManager);


        /*
         Simple GridLayoutManager that spans two columns
         */

/*
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
*/

        utilClass = new Utils();
        strFechaHoy = utilClass.getFecha();
        final TextView mTextView = findViewById(R.id.txt_main);
        mTextView.setText(strFechaHoy + " " + "v. " + version);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(TAG, item.toString());
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(DataModel item) {
        Intent i;
        utilClass.setFabric(item.text, TAG, strFechaHoy);

        switch (item.text) {

            case "Breviario":
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
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        utilClass.setFabric(item.getTitle().toString(), TAG, strFechaHoy);
        if (id == R.id.nav_sobre) {
            //Log.i(TAG,item.toString());
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
