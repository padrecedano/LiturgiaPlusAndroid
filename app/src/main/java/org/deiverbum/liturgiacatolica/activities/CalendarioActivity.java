package org.deiverbum.liturgiacatolica.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.utils.Utils;

public class CalendarioActivity extends AppCompatActivity {
    private static final String TAG = "SantoActivity";
    String items;
    StringRequest sStringRequest;

    JsonArrayRequest jsArrayRequest;
    CalendarView calendar;
    private Utils utilClass;
    private RequestQueue requestQueue;
    private String strFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final CalendarView simpleCalendarView = findViewById(R.id.calendarView);
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String sb = String.format("%04d", year) +
                        String.format("%02d", month + 1) +
                        String.format("%02d", dayOfMonth);
                strFecha = sb;
                Log.d(TAG, "Día: " + dayOfMonth + " Mes: " + month + " Año: " + year);
                simpleCalendarView.performLongClick();
            }
        });

        this.registerForContextMenu(simpleCalendarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilClass = new Utils();
//        final TextView mTextView = (TextView) findViewById(R.id.txt_santo);
//        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18)

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.calendario_menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);

    }


    public boolean onContextItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.nav_oficio:
                i = new Intent(this, OficioActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            case R.id.nav_santo:
                i = new Intent(this, SantoActivity.class);
                i.putExtra("FECHA", strFecha);
                startActivity(i);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


}

