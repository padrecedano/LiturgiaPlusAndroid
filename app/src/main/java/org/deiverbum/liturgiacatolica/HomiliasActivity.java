package org.deiverbum.liturgiacatolica;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import utils.Utils;

import static org.deiverbum.liturgiacatolica.Constants.MY_DEFAULT_TIMEOUT;
import static org.deiverbum.liturgiacatolica.Constants.PACIENCIA;
import static org.deiverbum.liturgiacatolica.Constants.URL_HOMILIAS;

public class HomiliasActivity extends AppCompatActivity {
    private static final String TAG = "OficioActivity";
    String items;
    StringRequest sRequest;
    private Utils utilClass;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homilias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilClass = new Utils();
        final TextView mTextView = (TextView) findViewById(R.id.txt_homilias);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(HomiliasActivity.this);
        progressDialog.setMessage(PACIENCIA);

        StringRequest sRequest = new StringRequest(Request.Method.GET, URL_HOMILIAS + utilClass.getHoy(),
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
                mTextView.setText("That didn't work!");
            }
        });


        sRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(sRequest);
        progressDialog.show();
    }

    private String showHomilias(JSONArray arrJson) {
        return "Prueba";
    }
}
