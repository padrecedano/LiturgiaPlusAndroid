package org.deiverbum.liturgiacatolica;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

public class HomiliasActivity extends AppCompatActivity {

    //    private static final String URL_BASE = "http://deiverbum.org/api/beta/";
//    private static final String URL_JSON = "homilias";
    private static final String URL_BASE = "http://www.deiverbum.org/";
    private static final String URL_JSON = "homilias-ciclo-a_semana-01_tiempo-adviento_dia-01-domingo";


    JsonObjectRequest jsArrayRequest;
    String items;
    ArrayAdapter adapter;
    private RequestQueue requestQueue;
    private WebView webView;

    //Ojo solución a fromHTML deprecated... ver: http://stackoverflow.com/questions/37904739/html-fromhtml-deprecated-in-android-n
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homilias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final TextView mTextView = (TextView) findViewById(R.id.txt_homilias);
        webView = (WebView) findViewById(R.id.webViewHomilias);
        webView.setWebViewClient(new MyWebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);

        //       webView.loadUrl("https://drive.google.com/open?id=0B0jwB_jVsocpbjZMTkE5dUVjczQ");
        webView.loadUrl(URL_BASE + URL_JSON);

// Instantiate the RequestQueue.
/*        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.deiverbum.org/api/homilias";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ fromHtml(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!"+error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
