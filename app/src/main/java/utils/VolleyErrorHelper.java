package utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.deiverbum.liturgiacatolica.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cedano on 30/12/16.
 */

public class VolleyErrorHelper extends HtmlFormat {
    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */

    private static final String s_ErrorSinConexion = FONT_RED + "¡ERROR DE CONEXIÓN!" + _FONT + BRS + "No estás conectado a internet." + BR +
            "En esta primera etapa de desarrollo la conexión a internet es necesaria para utilizar la aplicación. " +
            "En un futuro, D.M., implementaremos la posiblidad de utilizar la aplicación sin conexión." + BR +
            "Perdona esta pequeña limitación y vuelve a intentarlo cuando tengas conexión a internet.";

    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return s_ErrorSinConexion;
        }
        return context.getResources().getString(R.string.generic_error);
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 404:
                case 422:
                case 401:
                    try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        HashMap<String, String> result = new Gson().fromJson(new String(response.data),
                                new TypeToken<Map<String, String>>() {
                                }.getType());

                        if (result != null && result.containsKey("error")) {
                            return result.get("error");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage();

                default:
                    return context.getResources().getString(R.string.generic_server_down);
            }
        }
        return context.getResources().getString(R.string.generic_error);
    }
}