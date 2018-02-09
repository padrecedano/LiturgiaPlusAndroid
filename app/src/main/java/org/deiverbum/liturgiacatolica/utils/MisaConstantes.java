package org.deiverbum.liturgiacatolica.utils;

// Created by cedano on 15/12/16.


import static org.deiverbum.liturgiacatolica.utils.Constants.BR;
import static org.deiverbum.liturgiacatolica.utils.Constants.BRS;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_A;
import static org.deiverbum.liturgiacatolica.utils.Constants.CSS_RED_Z;
import static org.deiverbum.liturgiacatolica.utils.Constants.NBSP_4;

public final class MisaConstantes {
    public static final String ADVIENTO = "<h2>" + CSS_RED_A + "TIEMPO DE ADVIENTO" + CSS_RED_Z + "</h2>";
    public static final String R_INICIALES = "<h3>" + CSS_RED_A + "RITOS INICIALES" + CSS_RED_Z + "</h3>";

    public static final String ANT_TITULO = CSS_RED_A + "ANTÍFONA DE ENTRADA"+CSS_RED_Z+BRS;
    public static final String LECTURA_1 = BRS + CSS_RED_A + "PRIMERA LECTURA" + CSS_RED_Z + BRS;
    public static final String LECTURA_2 = BRS + CSS_RED_A + "SEGUNDA LECTURA" + CSS_RED_Z + BRS;

    public static final String SALMO_R = BRS + CSS_RED_A + "SALMO RESPONSORIAL" + CSS_RED_Z + BRS;
    public static final String ALELUYA = BRS + CSS_RED_A + "Aleluya" + CSS_RED_Z + NBSP_4;

    public static final String ALELUYA_R = BRS + CSS_RED_A + "R./ " + CSS_RED_Z + "Aleluya, aleluya, aleluya."+BRS;

    public static final String EVANGELIO = BRS + CSS_RED_A + "EVANGELIO" + CSS_RED_Z + BRS;

    public static final String O_COLECTA = BRS + CSS_RED_A + "ORACIÓN COLECTA" + CSS_RED_Z + BRS;
    public static final String SI_GLORIA = BRS + CSS_RED_A + "Se dice " + CSS_RED_Z + "Gloria"+BRS;
    public static final String NO_GLORIA = BRS + CSS_RED_A + "No se dice " + CSS_RED_Z + "Gloria"+BRS;

    public static final String O_OFRENDAS = BRS + CSS_RED_A + "ORACIÓN SOBRE LAS OFRENDAS" + CSS_RED_Z + BRS;
//    public static final String ORACION = BRS + CSS_RED_A + "ORACIÓN" + CSS_RED_Z + BRS;
    public static final String SI_CREDO = BRS + CSS_RED_A + "Se dice " + CSS_RED_Z + "Credo"+BRS;
    public static final String NO_CREDO = BRS + CSS_RED_A + "No se dice " + CSS_RED_Z + "Credo"+BRS;
    public static final String L_EUCARISTICA = "<h3>" + CSS_RED_A + "LITURGIA EUCARÍSTICA" + CSS_RED_Z + "</h3>";

    public static final String O_FIELES = BRS + CSS_RED_A + "ORACIÓN DE LOS FIELES" + CSS_RED_Z +BRS;
    public static final String PREFACIO = BRS + CSS_RED_A + "PREFACIO" + CSS_RED_Z +BRS;
    public static final String PLEGARIA = BRS + CSS_RED_A + "PLEGARIA EUCARÍSTICA" + CSS_RED_Z +BRS;
    public static final String R_COMUNION = "<h3>" + CSS_RED_A + "RITO DE LA COMUNIÓN" + CSS_RED_Z + "</h3>";
    public static final String O_DOMINICAL = BRS + CSS_RED_A + "ORACIÓN DOMINICAL" + CSS_RED_Z +BR+"Padre Nuestro..."+BRS;
    public static final String ANT_COMUNION = CSS_RED_A + "ANTÍFONA DE LA COMUNIÓN"+CSS_RED_Z+BRS;

    public static final String O_COMUNION = BRS + CSS_RED_A + "ORACIÓN DESPUÉS DE LA COMUNIÓN" + CSS_RED_Z + BRS;

    //Mensajes de error
    public static final String ERR_RESPONSORIO = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Hay un error en el responsorio de este día, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "a.cedano@deiverbum.org";

    private MisaConstantes() {
        // restrict instantiation
    }


}
