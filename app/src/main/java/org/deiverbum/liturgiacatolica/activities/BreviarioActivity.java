package org.deiverbum.liturgiacatolica.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.utils.Utils;


public class BreviarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "BreviarioActivity";
    //   private String sHoy = getFecha();
    private Utils utilClass;
    private String strFechaHoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breviario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        utilClass = new Utils();
        strFechaHoy = utilClass.getFecha();
        final Button btnMixto = findViewById(R.id.btn_mixto);
        btnMixto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnMixto", TAG, strFechaHoy);

                mensajeTemporal();
//                Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
//                startActivity(i);

            }
        });

        final Button btnOficio = findViewById(R.id.btn_oficio);
        btnOficio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnOficio", TAG, strFechaHoy);
                Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
                startActivity(i);
            }
        });

        final Button btnLaudes = findViewById(R.id.btn_laudes);
        btnLaudes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnLaudes", TAG, strFechaHoy);

                Intent i = new Intent(BreviarioActivity.this, LaudesActivity.class);
                startActivity(i);

            }
        });

        final Button btnVisperas = findViewById(R.id.btn_visperas);
        btnVisperas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnMixto", TAG, strFechaHoy);
                Intent i = new Intent(BreviarioActivity.this, VisperasActivity.class);
                startActivity(i);

            }
        });

        final Button btnTercia = findViewById(R.id.btn_tercia);
        btnTercia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnTercia", TAG, strFechaHoy);
                Intent i = new Intent(BreviarioActivity.this, TerciaActivity.class);
                startActivity(i);

            }
        });

        final Button btnSexta = findViewById(R.id.btn_sexta);
        btnSexta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnSexta", TAG, strFechaHoy);
                mensajeTemporal();
//                Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
//                startActivity(i);

            }
        });

        final Button btnNona = findViewById(R.id.btn_nona);
        btnNona.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnNona", TAG, strFechaHoy);

                mensajeTemporal();
//                Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
//                startActivity(i);

            }
        });

        final Button btnCompletas = findViewById(R.id.btn_completas);
        btnCompletas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                utilClass.setFabric("btnCompletas", TAG, strFechaHoy);

                mensajeTemporal();
//                Intent i = new Intent(BreviarioActivity.this, OficioActivity.class);
//                startActivity(i);

            }
        });


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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        utilClass.setFabric(item.getTitle().toString(), TAG, strFechaHoy);
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        final TextView textViewToChange = findViewById(R.id.txt_container);

        if (id == R.id.nav_misa) {
            Intent i = new Intent(BreviarioActivity.this, MisaActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_rituales) {
            Utils utilClass = new Utils();
            utilClass.mensajeTemporal(getApplicationContext());

        } else if (id == R.id.nav_homilias) {
            Intent i = new Intent(BreviarioActivity.this, HomiliasActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_santo) {
            Utils utilClass = new Utils();
            utilClass.mensajeTemporal(getApplicationContext());

        } else if (id == R.id.nav_evangelio) {
            Intent i = new Intent(BreviarioActivity.this, EvangelioActivity.class);
            startActivity(i);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void mensajeTemporal() {
        Context context = getApplicationContext();
        CharSequence text = "Este módulo se encuentra en fase de desarrollo... perdona las molestias.";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}