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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class OracionesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemListener {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Button btnSanto = (Button) findViewById(R.id.btn_breviario);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        arrayList.add(new DataModel("Santo Rosario", R.drawable.app_breviario_100x100, "#AF601A"));
        arrayList.add(new DataModel("Àngelus", R.drawable.app_misa_100x100, "#C39BD3"));
        arrayList.add(new DataModel("Credo", R.drawable.app_santo_100x100, "#673BB7"));
        arrayList.add(new DataModel("Santos", R.drawable.ic_home_breviario_new, "#4BAA50"));
        arrayList.add(new DataModel("Calendario", R.drawable.app_misa_100x100, "#F94336"));
        arrayList.add(new DataModel("Oraciones", R.drawable.ic_main_breviario, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        final Button btnSanto2 = (Button) findViewById(R.id.btn_completas);

        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 350);
        recyclerView.setLayoutManager(layoutManager);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.oraciones, menu);
        return true;
    }


    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
        Intent i = null;


        switch (item.text) {

            case "Breviario":
                i = new Intent(this, BreviarioActivity.class);
                startActivity(i);
                break;

            case "Misa":
                i = new Intent(this, MisaActivity.class);
                startActivity(i);
                break;

            case "Homilías":
                i = new Intent(this, HomiliasActivity.class);
                startActivity(i);
                break;

            case "Santos":
                i = new Intent(this, SantoActivity.class);
                startActivity(i);
                break;

            case "Calendario":
                i = new Intent(this, CalendarioActivity.class);
                startActivity(i);
                break;

            case "Oraciones":
                i = new Intent(this, OracionesActivity.class);
                startActivity(i);
                break;

            default:
                //               throw new IllegalArgumentException("Invalid day of the week: " + dayOfWeekArg);
        }


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
