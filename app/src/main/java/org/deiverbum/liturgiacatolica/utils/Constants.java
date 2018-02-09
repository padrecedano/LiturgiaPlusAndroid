package org.deiverbum.liturgiacatolica.utils;

// Created by cedano on 25/7/16.

public final class Constants {
    public static final int MY_DEFAULT_TIMEOUT = 15000;

    public static final String HOY = "20160729";
    public static final String URL_SANTO = "http://www.deiverbum.org/api/beta/santo/";
    public static final String CSS_RED_A = "<font color=\"#A52A2A\">";
    public static final String INVISIBLE_A = "<span style=\"width: 0.1px;\">";
    public static final String INVISIBLE_Z = "</span>";
    public static final String SEPARADOR = "≡";
    public static final String CSS_RED_Z = "</font>";
    public static final String OL_TITULO = "<h2>" + CSS_RED_A + "OFICIO DE LECTURA" + CSS_RED_Z + "</h2>";
    public static final String LA_TITULO = "<h2>" + CSS_RED_A + "LAUDES" + CSS_RED_Z + "</h2>";
    public static final String VI_TITULO = "<h2>" + CSS_RED_A + "VÍSPERAS" + CSS_RED_Z + "</h2>";
    public static final String HI_TITULO = "<h2>" + CSS_RED_A + "HORA INTERMEDIA" + CSS_RED_Z + "</h2>";
    public static final String CO_TITULO = "<h2>" + CSS_RED_A + "COMPLETAS" + CSS_RED_Z + "</h2>";
    public static final String BRS = "<br /><br />";
    public static final String BR = "<br />";
    public static final String CSS_SM_A = "<small>";
    public static final String CSS_SM_Z = "</small>";
    public static final String CSS_B_A = "<b>";
    public static final String CSS_B_Z = "</b>";
    public static final String SALUDO_OFICIO = CSS_RED_A + "V." + CSS_RED_Z + " Señor, abre mis labios." + BR +
            CSS_RED_A + "R." + CSS_RED_Z + " Y mi boca proclamará tu alabanza." + BRS;
    public static final String INVITATORIO = "<h3>invitatorio</h3>";
    public static final String HIMNO = BRS + CSS_RED_A + "HIMNO" + CSS_RED_Z + BRS;
    public static final String SALMODIA = BRS + CSS_RED_A + "SALMODIA" + CSS_RED_Z + BRS;
    public static final String _H2RED = "<h2>" + CSS_RED_A;
    public static final String H2RED_ = CSS_RED_A + "</h2>";
    public static final String PRIMERA_LECTURA = BRS + CSS_RED_A + "PRIMERA LECTURA" + CSS_RED_Z + BRS;
    public static final String SEGUNDA_LECTURA = BRS + CSS_RED_A + "SEGUNDA LECTURA" + CSS_RED_Z + BRS;
    public static final String RESP_UPPER = CSS_RED_A + "RESPONSORIO" + CSS_RED_Z;
    public static final String RESP_LOWER = "Responsorio";
    public static final String RESP_BREVE = CSS_RED_A + "RESPONSORIO BREVE" + CSS_RED_Z;
    public static final String LECTURA_BREVE = BRS + "LECTURA BREVE";
    public static final String PRE_ANT = CSS_RED_A + "Ant. " + CSS_RED_Z;
    public static final String NBSP_2 = " &nbsp;&nbsp;";
    public static final String NBSP_4 = " &nbsp;&nbsp;&nbsp;&nbsp;";
    public static final String RESP_V = CSS_RED_A + "V. " + CSS_RED_Z;
    public static final String RESP_R = CSS_RED_A + "R. " + CSS_RED_Z;
    public static final String CE = BR + CSS_RED_A + "CÁNTICO EVANGÉLICO" + CSS_RED_Z;
    public static final String PRECES = BR + CSS_RED_A + "PRECES" + CSS_RED_Z + BRS;
    public static final String PADRENUESTRO_TITULO = BRS + CSS_RED_A + "PADRE NUESTRO" + CSS_RED_Z + BRS;
    public static final String PADRENUESTRO = "Padre nuestro,~¦que estás en el cielo,~¦santificado sea tu Nombre;~¦" +
            "venga a nosotros tu reino;~¦hágase tu voluntad~¦en la tierra como en el cielo.~¦" +
            "Danos hoy nuestro pan de cada día;~¦perdona nuestras ofensas,~¦" +
            "como también nosotros perdonamos a los que nos ofenden;~¦" +
            "no nos dejes caer en la tentación,~¦y líbranos del mal. Amén.";
    public static final String ORACION = BRS + CSS_RED_A + "ORACIÓN" + CSS_RED_Z + BRS;
    //Mensajes de error
    public static final String ERR_RESPONSORIO = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Hay un error en el responsorio de este día, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com" + BRS;
    public static final String ERR_CONEXION = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "No estás conectado a internet." + BR +
            "En esta primera etapa de desarrollo la conexión a internet es necesaria para utilizar la aplicación. " +
            "En un futuro, D.M., implementaremos la posiblidad de utilizar la aplicación sin conexión.";
    public static final String ERR_GENERAL = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Lamentablemente ha ocurrido un error... " +
            "«El que esté sin pecado que tire la primera piedra.» " + BRS + "Es posible que " +
            "la liturgia de hoy aún no haya sido introducida. Intenta más tarde. Si el error persiste te ruego que me lo " +
            "comuniques a la dirección siguiente: " + CSS_RED_A + CSS_B_A + "padre.cedano@gmail.com " + CSS_RED_Z + CSS_B_Z + BRS +
            "Gracias por la paciencia y espero resolverlo pronto.";
    public static final String BENEDICTUS = new StringBuilder().append("Bendito sea el Señor, Dios de Israel,_porque ha visitado y redimido a su pueblo,_suscitándonos una fuerza de salvación_en la casa de David, su siervo,_según lo había predicho desde antiguo_por boca de sus santos profetas.§Es la salvación que nos libra de nuestros enemigos_y de la mano de todos los que nos odian;_ha realizado así la misericordia que tuvo con nuestros padres,_recordando su santa alianza_y el juramento que juró a nuestro padre Abraham.§Para concedernos que, libres de temor,_arrancados de la mano de los enemigos,_le sirvamos con santidad y justicia,_en su presencia, todos nuestros días.§Y a ti, niño, te llamarán profeta del Altísimo,_porque irás delante del Señor_a preparar sus caminos,_anunciando a su pueblo la salvación,_el perdón de sus pecados.§Por la entrañable misericordia de nuestro Dios,_nos visitará el sol que nace de lo alto,_para iluminar a los que viven en tiniebla_y en sombra de muerte,_para guiar nuestros pasos_por el camino de la paz.§").toString();
    //Otros mensajes
    public static final String PACIENCIA = "La paciencia todo lo alcanza. Por favor espere ...";
    static final String NBSP_SALMOS = BR + "&nbsp;&nbsp;";
    public static final String FIN_SALMO = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
            + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
            + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
    static final String RESP_A = CSS_RED_A + " * " + CSS_RED_Z;
    static final String PRECES_IL = BRS + CSS_RED_A + "Se pueden añadir algunas intenciones libres." + CSS_RED_Z + BRS;
    static final String OBIEN = BRS + CSS_RED_A + "O bien:" + CSS_RED_Z + BRS;
    static final String PRECES_R = NBSP_SALMOS + CSS_RED_A + "– " + CSS_RED_Z;
    private static final String URL_API = "http://deiverbum.org/api/v1/";
    public static final String OL_URL = URL_API + "oficio/";
    public static final String LA_URL = URL_API + "laudes/";
    public static final String HI1_URL = URL_API + "tercia/";
    public static final String H4_URL = URL_API + "visperas/";
    public static final String URL_HOMILIAS = URL_API + "homilias/";
    public static final String URL_EVANGELIO = URL_API + "evangelio/";

    private Constants() {
        // restrict instantiation
    }


}