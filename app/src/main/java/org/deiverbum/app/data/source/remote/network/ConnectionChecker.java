package org.deiverbum.app.data.source.remote.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01
 */
public class ConnectionChecker {

    private final Context mContext;

    public ConnectionChecker(Context context) {
        mContext = context;
    }

    @SuppressWarnings("unused")
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}

