package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import utils.Utils;

import static org.deiverbum.liturgiacatolica.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.Constants.URL_SANTO;

public class SantoActivity extends AppCompatActivity {
    private static final String TAG = "SantoActivity";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String items;
    StringRequest sStringRequest;
    DatabaseReference rootRef, demoRef;
    JsonArrayRequest jsArrayRequest;
    String texto;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_santo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
        final TextView mTextView = (TextView) findViewById(R.id.txt_santo);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        //       DatabaseReference ref = database.getReference("oficio_1");
        String params = utilClass.getFechaMMDD();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        // DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("santos");
        DatabaseReference mDatabaseReference = database.getReference().child("santos");

        DatabaseReference zone1Ref = mDatabaseReference.child("1015");
        //       DatabaseReference zone1NameRef = zone1Ref.child("mensaje");
        Log.i(TAG, "ok" + mDatabaseReference.toString());

        zone1Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String txt = (String) dataSnapshot.getValue();
                //texto  = (String) dataSnapshot.child("info").getValue()+"...";
                Log.i(TAG, txt);
                mTextView.setText(Utils.fromHtml(txt));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled");


            }
        });




        requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(SantoActivity.this);
        progressDialog.setMessage(PACIENCIA);
/*        sStringRequest = new StringRequest(
                Request.Method.GET, URL_SANTO + utilClass.getHoy(), "",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items = showSanto(response);
                        mTextView.setText(Utils.fromHtml(items));
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyErrorHelper errorVolley = new VolleyErrorHelper();
                        String sError = errorVolley.getMessage(error,getApplicationContext());
                        Log.d(TAG, "Error: " + sError);
                        mTextView.setText(Utils.fromHtml(sError));
                        progressDialog.dismiss();
                    }
                }
        );
*/
        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_SANTO + utilClass.getHoy(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResponse) {
                        // Display the first 500 characters of the response string.
                        //                       mTextView.setText(Utils.fromHtml(sResponse));

                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Ha ocurrido un error desconocido.");
            }
        });


        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(sRequest);
//        progressDialog.show();


    }


    protected String showSanto(JSONArray arrJson) {
        return "Prueba";
    }
}
