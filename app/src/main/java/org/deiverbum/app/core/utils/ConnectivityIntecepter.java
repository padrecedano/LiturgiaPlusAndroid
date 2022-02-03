package org.deiverbum.app.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 28/11/21
 * @since 2021.01
 */


public class ConnectivityIntecepter implements Interceptor {

    Context context;

    public ConnectivityIntecepter(Context context){
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network networkCapabilities = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCapabilities = cm.getActiveNetwork();
        }
        NetworkCapabilities activeNetwork = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activeNetwork = cm.getNetworkCapabilities(networkCapabilities);
        }
        if (activeNetwork != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    isConnected = true;
                }else if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    isConnected = true;
                }else if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    isConnected = true;
                }else {
                    isConnected = false;
                }
            }
        }
        if (!isConnected){
            throw new NoConnectivityException();
        }else {
            return chain.proceed(chain.request());
        }
    }
}