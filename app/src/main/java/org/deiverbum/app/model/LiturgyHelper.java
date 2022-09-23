package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.RED_COLOR;
import static org.deiverbum.app.utils.Constants.TITLE_CONCLUSION;
import static org.deiverbum.app.utils.Constants.TITLE_INITIAL_INVOCATION;
import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Reúne aquellos elementos que son comúnes a las diversas horas del Breviary.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public final class LiturgyHelper  {

    public static final Map<Integer, String> breviaryHours = new HashMap<Integer, String>();
        /*breviaryHours.put(0, "mixto");
        breviaryHours.put(1, "oficio");
        breviaryHours.put(2, "laudes");
        breviaryHours.put(3, "third");
        breviaryHours.put(4, "fourth");*/
    //public static final Map<Integer, String> myMap = new HashMap<Integer, String>();

    public static final Map<Integer, String> myMap;
    static {
        Map<Integer, String> aMap = new HashMap<Integer, String>();
        aMap.put(0, "mixto");
        aMap.put(1, "oficio");
        aMap.put(2, "laudes");
        aMap.put(3, "tercia");
        aMap.put(4, "sexta");
        aMap.put(5, "nona");
        aMap.put(6, "visperas");
        aMap.put(7, "completas");

        myMap = Collections.unmodifiableMap(aMap);
    }


    public LiturgyHelper() {

    }


}

