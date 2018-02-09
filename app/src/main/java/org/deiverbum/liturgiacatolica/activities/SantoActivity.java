package org.deiverbum.liturgiacatolica.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.deiverbum.liturgiacatolica.R;
import org.deiverbum.liturgiacatolica.utils.Utils;

import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.H2RED_;
import static org.deiverbum.liturgiacatolica.utils.Constants._H2RED;

public class SantoActivity extends AppCompatActivity {
    private static final String TAG = "SantoActivity";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String strFechaHoy;
    String santoMMDD;

    String textoVida;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i(TAG, "ok");


        utilClass = new Utils();
        final TextView mTextView = findViewById(R.id.txt_santo);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        //       DatabaseReference ref = database.getReference("oficio_1");


        santoMMDD = (this.getIntent().getExtras() != null) ? getIntent().getStringExtra("FECHA") : utilClass.getHoy();

        santoMMDD = santoMMDD.substring(santoMMDD.length() - 4);

        Log.i(TAG, "ok" + strFechaHoy);
        strFechaHoy = utilClass.getFecha();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();

        DatabaseReference refSantos = root.child("santos").child(santoMMDD);


        refSantos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.i(TAG, dataSnapshot.toString());

                StringBuilder sb = new StringBuilder();
                if (dataSnapshot.hasChild("nombre")) {
                    String nombre = (String) dataSnapshot.child("nombre").getValue();
                    sb.append(_H2RED);
                    sb.append(nombre);
                    sb.append(H2RED_);
                }
                if (dataSnapshot.hasChild("martirologio")) {
                    String martirologio = (String) dataSnapshot.child("martirologio").getValue();
                    sb.append(martirologio);
                    sb.append("<br />(Martirologio Romano)");
                }
                if (dataSnapshot.hasChild("vida")) {
                    String vida = (String) dataSnapshot.child("vida").getValue();
                    sb.append(_H2RED);
                    sb.append("Vida");
                    sb.append(H2RED_);
                    sb.append(vida);
                }
                if (sb.toString().equals("")) {
                    textoVida = "No se ha introducido la  vida de este santo. ¿Quieres cooperar?";
                } else {
                    textoVida = strFechaHoy + BRS + sb.toString();
                }


                mTextView.setText(Utils.fromHtml(textoVida));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled");
            }
        });

    }


}
