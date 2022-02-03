package org.deiverbum.app.utils;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

public class Constants {

    public static ForegroundColorSpan RED_COLOR = new ForegroundColorSpan(Color.parseColor("#A52A2A"));

    public static final int MY_DEFAULT_TIMEOUT = 15000;
    public static final long SCREEN_TIME_OFF = 1000 * 60 * 7;
    public static final String VOICE_INI = "Iniciando lectura.";
    public static final String ERR_LANG = "Lenguaje no soportado.";
    public static final String ERR_INITIALIZATION = "Falló la inicialización.";
    public static final String ERR_BIBLIA = "Todavía no hay introducción a este libro. <br>Proyecto abierto a la colaboración. <br><a href=\"http://bit.ly/2FInp4n\">Ver los detalles aquí</a>.";
    public static final int VERSION_CODE = 202201000;

    public static final String CSS_RED_A = "<font color=\"#A52A2A\">";
    public static final String URL_FIREBASE = "es/2021_1/";
    public static final String CALENDAR_PATH = URL_FIREBASE + "calendar/";
    public static final String REDCOLOR = "<font color=\"#A52A2A\">%s</font>";

    public static final String OLD_SEPARATOR = "≡";
    public static final String SEPARADOR = "\\.";
    public static final String CSS_RED_Z = "</font>";
    public static final String BRS = "<br /><br />";
    public static final String BR = "<br />";
    public static final String CSS_SM_A = "<small>";
    public static final String CSS_SM_Z = "</small>";
    public static final String CSS_B_A = "<b>";
    public static final String CSS_B_Z = "</b>";
    public static final String PRE_ANT = CSS_RED_A + "Ant. " + CSS_RED_Z;
    public static final String NBSP_2 = " &nbsp;&nbsp;";
    public static final String NBSP_4 = " &nbsp;&nbsp;&nbsp;&nbsp;";
    public static final String RESP_V = CSS_RED_A + "V. " + CSS_RED_Z;
    public static final String RESP_R = CSS_RED_A + "R. " + CSS_RED_Z;
    public static final String PADRENUESTRO = "Padre nuestro,~¦que estás en el cielo,~¦santificado sea tu Nombre;~¦" +
            "venga a nosotros tu reino;~¦hágase tu voluntad~¦en la tierra como en el cielo.~¦" +
            "Danos hoy nuestro pan de cada día;~¦perdona nuestras ofensas,~¦" +
            "como también nosotros perdonamos a los que nos ofenden;~¦" +
            "no nos dejes caer en la tentación,~¦y líbranos del mal. Amén.";
    public static final String ORACION = BRS + CSS_RED_A + "ORACIÓN" + CSS_RED_Z + BRS;
    //Mensajes de error
    public static final String ERR_RESPONSORIO = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Hay un error en el responsorio de este día, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com" + BRS;

    public static final String ERR_REPORT = "\n\nA fin de corregir cuanto antes este error, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com";

    public static final String ERR_CUSTOM = "Ha ocurrido un error:\n\n%s\n\nA fin de corregir cuanto antes este error, " +
            "por favor comunícalo al desarrollador pulsando en el botón que aparece en la parte inferior derecha de la pantalla";

    public static final String ERR_SUBJECT = String.format("*Reporte de error Liturgia+ v. %d", Constants.VERSION_CODE);
    public static final String ERR_CONEXION = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "No estás conectado a internet." + BR +
            "En esta primera etapa de desarrollo la conexión a internet es necesaria para utilizar la aplicación. " +
            "En un futuro, D.M., implementaremos la posiblidad de utilizar la aplicación sin conexión.";
    public static final String ERR_GENERAL = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Lamentablemente ha ocurrido un error... " +
            "«El que esté sin pecado que tire la primera piedra.» " + BRS + "Es posible que " +
            "la liturgia de hoy aún no haya sido introducida. Intenta más tarde. Si el error persiste te ruego que me lo " +
            "comuniques a la dirección siguiente: " + CSS_RED_A + CSS_B_A + "padre.cedano@gmail.com " + CSS_RED_Z + CSS_B_Z + BRS +
            "Gracias por la paciencia y espero resolverlo pronto.";
    //Otros mensajes
    public static final String PACIENCIA = "\n\nLa paciencia todo lo alcanza. Por favor espere ...";
    public static final String BREVIARIO_INTRO = "La vida entera de los fieles, durante cada una de las horas del día y de la noche, " +
            "constituye como una liturgia, mediante la cual ellos se ofrecen en servicio de amor a Dios y a los hombres, "
            + " adhiriéndose a la acción de Cristo, que con su vida entre nosotros y el ofrecimiento de sí mismo "
            + "ha santificado la vida de todos los hombres.";
    public static final String NBSP_SALMOS = BR + "&nbsp;&nbsp;";
    public static final String FIN_SALMO = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
            + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
            + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
    public static final String RESP_A = CSS_RED_A + " * " + CSS_RED_Z;
    static final String PRECES_IL = BRS + CSS_RED_A + "Se pueden añadir algunas intenciones libres." + CSS_RED_Z + BRS;
    static final String OBIEN = BRS + CSS_RED_A + "O bien:" + CSS_RED_Z + BRS;
    static final String PRECES_R = NBSP_SALMOS + CSS_RED_A + "– " + CSS_RED_Z;

    public static final String DATA_NOTFOUND = "No se encontraron datos";
    public static final String FIREBASE_SANTOS = "/liturgia/santos/";
    public static final String DOC_NOTFOUND = "Documento no encontrado";

    public static final String FILE_PRIVACY = "res/raw/privacy_20220127.json";
    public static final String FILE_TERMS = "res/raw/terms_20220127.json";
    public static final String PREF_ACCEPT = "accept_terms";
    public static final String PREF_ANALYTICS = "enable_analytics";
    public static final String PREF_CRASHLYTICS = "enable_crashlytics";

    public static final String ACCEPT_YES = "He leído y acepto la " +
            "Política de" +
            " Privacidad y los Términos y Condiciones de Uso.";

    public static final String ACCEPT_NO = "No acepto la Política de " +
            "Privacidad y/o los Términos y Condiciones de Uso, por tanto, no " +
            "pienso usar esta Aplicación.";



    public static final String MSG_LEGAL = "Si tienes alguna duda, puedes " +
            "pulsar en el botón \"Enviar eMail\" para ponerte en contacto con" +
            " " +
            "el Desarrollador. Se abrirá tu programa de eMail para que " +
            "puedas " +
            "redactar y enviar tu mensaje. Los datos recibidos serán " +
            "tratados de acuerdo a " +
            "lo indicado en la Política de Privacidad.\n\n";

    public static final String DIALOG_LEGAL_TITLE = "Aviso";
    public static final String DIALOG_LEGAL_BODY = "Retirarás tu consentimiento y Liturgia+ se cerrará al pulsar CONFIRMAR.";

    public static final String DIALOG_LEGAL_OK = "CONFIRMAR";
    public static final String DIALOG_LEGAL_CANCEL = "CANCELAR";


    private Constants() {
    }

}
