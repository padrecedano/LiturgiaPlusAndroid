package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONArray;

import utils.Utils;

import static org.deiverbum.liturgiacatolica.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.Constants.URL_SANTO;

public class SantoActivity extends AppCompatActivity {
    private static final String TAG = "SantoActivity";
    String items;
    StringRequest sStringRequest;

    JsonArrayRequest jsArrayRequest;
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

        utilClass = new Utils();
        final TextView mTextView = (TextView) findViewById(R.id.txt_santo);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

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
                        mTextView.setText(Utils.fromHtml(sResponse));
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
        requestQueue.add(sRequest);
        progressDialog.show();


    }

    protected String showSanto(JSONArray arrJson) {
        return "Prueba";
    }
}
