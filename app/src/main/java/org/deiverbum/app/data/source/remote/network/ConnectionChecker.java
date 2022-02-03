package org.deiverbum.app.data.source.remote.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 12/12/21
 * @since 2021.01
 */
public class ConnectionChecker {

        private final Context mContext;
    public static boolean isNetworkConnected = false;
        public ConnectionChecker(Context context) {
            mContext = context;
        }

        public boolean isConnected() {
            ConnectivityManager cm =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }

    public void registerNetworkCallback()
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                                                                   @Override
                                                                   public void onAvailable(Network network) {
                                                                       isNetworkConnected= true; // Global Static Variable
                                                                   }
                                                                   @Override
                                                                   public void onLost(Network network) {
                                                                       isNetworkConnected=  false; // Global Static Variable
                                                                   }
                                                               }

            );
            isNetworkConnected= false;
        }catch (Exception e){
            isNetworkConnected= false;
        }
    }
    }
