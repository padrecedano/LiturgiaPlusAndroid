package org.deiverbum.app.util;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

/**
 * Clase utilitaria que se usa en varias partes de la aplicación
 */
@SuppressWarnings("all")
public final class ColorUtils {

    public static final String LS = System.getProperty("line.separator");
    private static final ForegroundColorSpan redDefault = new ForegroundColorSpan(Color.parseColor("#A52A2A"));
    private static final ForegroundColorSpan redNightMode = new ForegroundColorSpan(Color.parseColor("#FFDAB9"));
    public static boolean isNightMode;

    public static void setNightMode(boolean nightMode) {
        //nightMode=nightMode;
    }

    public static final ForegroundColorSpan getRed() {
        return isNightMode ? redNightMode : redDefault;
    }

    public static final String getRedCode() {
        return isNightMode ? "#FFDAB9" : "#A52A2A";
    }

}
