package org.deiverbum.app.utils;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import java.util.Locale;

public class Constants {

    /*Cambiar tambien en
      build.gradle del módulo app
      strings.xml: app_version_name_and_mail
      strings.xml: accept_info
      */
    public static final int VERSION_CODE = 202201004;
                                         //202201001
    public static final int DB_VERSION = 1;

    public static final String LS = System.getProperty("line.separator");
    public static final String LS2 = LS + LS;

    public static final ForegroundColorSpan RED_COLOR = new ForegroundColorSpan(Color.parseColor("#A52A2A"));
    public static final String VOICE_INI = "Iniciando lectura.";

    public static final String CSS_RED_A = "<font color=\"#A52A2A\">";

    public static final String OLD_SEPARATOR = "≡";
    public static final String SEPARADOR = "\\.";
    public static final String CSS_RED_Z = "</font>";
    public static final String BRS = "<br /><br />";
    public static final String BR = "<br />";

    public static final String PRE_ANT = CSS_RED_A + "Ant. " + CSS_RED_Z;
    public static final String NBSP_4 = " &nbsp;&nbsp;&nbsp;&nbsp;";
    public static final String RESP_V = CSS_RED_A + "V. " + CSS_RED_Z;
    public static final String RESP_R = CSS_RED_A + "R. " + CSS_RED_Z;

    //Mensajes de error
    public static final String ERR_RESPONSORIO = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Hay un error en el responsorio de este día, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com" + BRS;

    public static final String ERR_REPORT = "\n\nA fin de corregir cuanto antes este error, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com";


    public static final String ERR_SUBJECT = String.format(new Locale("es", "ES"),"Reporte de error Liturgia+ v. %d", Constants.VERSION_CODE);
    public static final String ERR_LANG = "Lenguaje no soportado.";
    public static final String ERR_INITIALIZATION = "Falló la inicialización.";
    public static final String ERR_BIBLIA = "Todavía no hay introducción a este libro. <br>Proyecto abierto a la colaboración. <br><a href=\"http://bit.ly/2FInp4n\">Ver los detalles aquí</a>.";
    public static final String ERR_FILE_NOT_FOUND = String.format("%s\n\n%s", "Error: Archivo no encontrado.",ERR_REPORT);
    public static final String ERR_WITH_VERSION = String.format(new Locale("es", "ES"),"Ha ocurrido un error.%n %s %n%n Versión: %d %n%n", ERR_REPORT,Constants.VERSION_CODE);

    //Otros mensajes
    public static final String PACIENCIA = "\n\nLa paciencia todo lo alcanza. Por favor espere ...";
    public static final String NBSP_SALMOS = BR + "&nbsp;&nbsp;";
    public static final String RESP_A = CSS_RED_A + " * " + CSS_RED_Z;
    static final String PRECES_IL = BRS + CSS_RED_A + "Se pueden añadir algunas intenciones libres." + CSS_RED_Z + BRS;
    static final String OBIEN = BRS + CSS_RED_A + "O bien:" + CSS_RED_Z + BRS;
    static final String PRECES_R = NBSP_SALMOS + CSS_RED_A + "– " + CSS_RED_Z;

    public static final String DATA_NOTFOUND = "No se encontraron datos";
    public static final String FIREBASE_SANTOS = "/liturgia/santos/";
    public static final String DOC_NOTFOUND = "Documento no encontrado";
    public static final String CONTENT_NOTFOUND = "Este contenido no está " +
            "disponible todavía. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha. Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo.";

    public static final String NOTFOUND_OR_NOTCONNECTION = "Este contenido no" +
            " está " +
            "disponible. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha o que en este momento no estés " +
            "conectado a internet. " +
            "Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo.";

    public static final String CONTENT_TO_SYNC = String.format("Contenido no encontrado. " +
            "Puede que estés intentando acceder a un " +
            "contenido fuera de fecha o que el contenido aún no exista. %sPuedes reportar esta incidencia al desarrollador para informarle de que hay un problema en " +
            "este módulo mandando un mensaje a <b>%spadre.cedano@gmail.com%s</b> en el que indiques la fecha y el módulo en que ha ocurrido el problema. %sLos contenidos no presentes se incluirán mediante sincronización remota y necesitarás tener conexión a internet para recibirlos.",BRS,CSS_RED_A,CSS_RED_Z,BRS);

    /*
        File paths
     */
    public static final String FILE_PRIVACY = "raw/privacy_202201.json";
    public static final String FILE_TERMS = "raw/terms_202201.json";
    public static final String FILE_VIA_CRUCIS_2003 = "raw/viacrucis2003.json";
    public static final String FILE_VIA_CRUCIS_2005 = "raw/viacrucis2005.json";
    public static final String FILE_REGINA = "raw/regina.json";
    public static final String FILE_ANGELUS = "raw/angelus.json";
    public static final String FILE_LITANIES = "raw/letanias.json";
    public static final String FILE_ROSARY = "raw/rosario.json";
    public static final String FILE_NIGHT_PRAYER = "raw/completas.json";



    public static final String PREF_ACCEPT = "accept_terms";
    public static final String PREF_ANALYTICS = "enable_analytics";
    public static final String PREF_CRASHLYTICS = "enable_crashlytics";

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

    /*
        Room table names
     */
    public static final String TODAY_TABLE = "today";
    public static final String DB_TABLE = "db_table";
    public static final String BIBLE_BOOK = "bible_book";
    public static final String BIBLE_HOMILY_JOIN = "bible_homily_join";
    public static final String BIBLE_HOMILY_THEME = "bible_homily_theme";
    public static final String BIBLE_READING = "bible_reading";
    public static final String HOMILY = "homily";
    public static final String LH_ANTIPHON = "lh_antiphon";
    public static final String LITURGY = "liturgy";
    public static final String LITURGY_COLOR = "liturgy_color";
    public static final String LITURGY_TIME = "liturgy_time";
    public static final String LITURGY_GROUP = "liturgy_group";
    public static final String LITURGY_HOMILY_JOIN = "liturgy_homily_join";

    public static final String LH_INVITATORY = "lh_invitatory";
    public static final String LH_INVITATORY_JOIN = "lh_invitatory_join";
    public static final String LH_GOSPEL_CANTICLE = "lh_gospel_canticle";
    public static final String LH_HYMN = "lh_hymn";

    public static final String LH_HYMN_JOIN = "lh_hymn_join";
    public static final String LH_INTERCESSIONS = "lh_intercessions";
    public static final String LH_INTERCESSIONS_JOIN = "lh_intercessions_join";
    public static final String LH_PSALM = "lh_psalm";
    public static final String LH_PSALMODY = "lh_psalmody";

    public static final String LH_PSALMODY_JOIN = "lh_psalmody_join";
    public static final String LH_EPIGRAPH = "lh_epigraph";
    public static final String LH_THEME = "lh_theme";
    public static final String MASS_READING = "mass_reading";
    public static final String MASS_READING_JOIN = "mass_reading_join";
    public static final String PRAYER = "prayer";

    public static final String SAINT = "saint";
    public static final String SAINT_LIFE = "saint_life";
    public static final String LH_OFFICE_BIBLICAL = "lh_office_biblical";
    public static final String LH_OFFICE_BIBLICAL_JOIN = "lh_office_biblical_join";
    public static final String LH_OFFICE_PATRISTIC = "lh_office_patristic";
    public static final String LH_OFFICE_PATRISTIC_JOIN = "lh_office_patristic_join";
    public static final String LH_OFFICE_VERSE = "lh_office_verse";
    public static final String LH_OFFICE_VERSE_JOIN = "lh_office_verse_join";
    public static final String LH_PRAYER = "lh_prayer";
    public static final String LH_READING_SHORT = "lh_reading_short";
    public static final String LH_READING_SHORT_JOIN = "lh_reading_short_join";
    public static final String LH_RESPONSORY = "lh_responsory";
    public static final String LH_RESPONSORY_SHORT = "lh_responsory_short";
    public static final String SYNC_STATUS = "sync_status";

    public static final String PATER = "pater";
    public static final String PATER_OPUS = "pater_opus";
    public static final String SYNC_LITURGY_HOMILY_JOIN = "sync_liturgy_homily_join";
    public static final String SYNC_MASS_READING = "sync_mass_reading";

    public static final String TITLE_MIXTO = "Laudes y Oficio";
    public static final String TITLE_OFICIO = "Oficio";
    public static final String TITLE_LAUDES = "Laudes";
    public static final String TITLE_TERCIA = "Tercia";
    public static final String TITLE_SEXTA = "Sexta";
    public static final String TITLE_NONA = "Nona";
    public static final String TITLE_VISPERAS = "Vísperas";
    public static final String TITLE_I_VISPERAS = "I Vísperas";
    public static final String TITLE_I_VISPERAS_READ = "Primeras Vísperas";
    public static final String TITLE_COMPLETAS = "Completas";

    public static final String TITLE_HYMN = "Himno";
    public static final String TITLE_MASS_READING = "Lecturas de la Misa";
    public static final String TITLE_INVITATORY = "Invitatorio";
    public static final String TITLE_INTERCESSIONS = "Preces";
    public static final String TITLE_OFFICE_OF_READING = "Lecturas del Oficio";
    public static final String TITLE_RESPONSORY = "Responsorio";
    public static final String TITLE_RESPONSORY_SHORT = "Responsorio Breve";

    public static final String TITLE_PSALMODY = "Salmodia";
    public static final String TITLE_TEDEUM = "Te Deum";
    public static final String TITLE_CONCLUSION = "Conclusión";
    public static final String TITLE_GOSPEL_CANTICLE ="Cántico Evangélico";
    public static final String TITLE_SHORT_READING ="Lectura Breve";
    public static final String TITLE_INITIAL_INVOCATION ="Invocación Inicial";
    public static final String TITLE_VIRGIN_ANTIHPON ="Antífona Final de la Santísima Virgen";
    public static final String TITLE_SOUL_SEARCHING ="Exámen de Conciencia";
    public static final String TITLE_PATER_NOSTER ="Padre Nuestro";
    public static final String TITLE_PRAYER ="Oración";
    public static final String TITLE_BIBLE_COMMENTS ="Comentarios";







    private Constants() {
    }

}
