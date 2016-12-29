package org.deiverbum.liturgiacatolica;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by cedano on 19/12/16.
 */

public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        view.loadUrl(url);
        return true;
    }
}