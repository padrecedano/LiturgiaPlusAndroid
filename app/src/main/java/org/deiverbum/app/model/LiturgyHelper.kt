package org.deiverbum.app.model;

import java.util.Collections;
import java.util.HashMap;
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
public final class LiturgyHelper {

    public static final Map<Integer, String> myMap;

    static {
        Map<Integer, String> aMap = new HashMap<>();
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