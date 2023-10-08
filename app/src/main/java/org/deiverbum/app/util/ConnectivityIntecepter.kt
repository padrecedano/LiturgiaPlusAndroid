package org.deiverbum.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.IntRange
import okhttp3.Interceptor

import okhttp3.Response
import java.io.IOException

/**
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@Suppress("DEPRECATION")
class ConnectivityIntecepter(val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val connectionStatus = connectionType
        return if (connectionStatus == 0) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }// connected to the internet

    // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
    @get:IntRange(from = 0, to = 3)
    private val connectionType: Int
         get() {
             var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
             val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                 if (capabilities != null) {
                     if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                         result = 2
                     } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                         result = 1
                     } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                         result = 3
                     }
                 }
             } else {
                 val activeNetwork = cm.activeNetworkInfo
                 if (activeNetwork != null) {
                     // connected to the internet
                     when (activeNetwork.type) {
                         ConnectivityManager.TYPE_WIFI -> {
                             result = 2
                         }

                         ConnectivityManager.TYPE_MOBILE -> {
                             result = 1
                         }

                         ConnectivityManager.TYPE_VPN -> {
                             result = 3
                         }
                     }
                 }
             }
            return result
        }
}