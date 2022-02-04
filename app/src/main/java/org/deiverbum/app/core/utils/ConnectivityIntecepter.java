package org.deiverbum.app.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */


public class ConnectivityIntecepter implements Interceptor {

    Context context;

    public ConnectivityIntecepter(Context context){
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network networkCapabilities;
        networkCapabilities = cm.getActiveNetwork();
        NetworkCapabilities activeNetwork;
        activeNetwork = cm.getNetworkCapabilities(networkCapabilities);
        if (activeNetwork != null) {
            if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                isConnected = true;
            }else if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                isConnected = true;
            }else isConnected = activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
        }
        if (!isConnected){
            throw new NoConnectivityException();
        }else {
            return chain.proceed(chain.request());
        }
    }
}