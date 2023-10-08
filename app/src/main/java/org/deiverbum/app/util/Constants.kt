package org.deiverbum.app.util

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import java.util.Locale

/**
 * Todas las constantes usadas en la aplicación
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2018.1
 */
@Suppress("unused")
object Constants {
    /*Cambiar tambien en
      build.gradle del módulo app
      strings.xml: app_version_name_and_mail
      strings.xml: accept_info
      */
    const val VERSION_CODE = 202301003
    const val VERSION_CODE_FORMATTED = "2023.1.3"

    //LAST: 202201004
    const val DB_VERSION = 1
    const val SYNC_INTERVAL_DAYS = 7
    val LS: String? = System.getProperty("line.separator")
    val LS2 = LS!! + LS
    val RED_COLOR = ForegroundColorSpan(Color.parseColor("#A52A2A"))
    const val VOICE_INI = "Iniciando lectura."
    const val CSS_RED_A = "<font color=\"#A52A2A\">"
    const val OLD_SEPARATOR = "≡"
    const val SEPARADOR = "\\."
    const val CSS_RED_Z = "</font>"
    const val BRS = "<br /><br />"
    const val BR = "<br />"
    const val NBSP_4 = " &nbsp;&nbsp;&nbsp;&nbsp;"
    const val TAB_2 = "\t\t"

    //Mensajes de error
    const val ERR_REPORT = "\n\nA fin de corregir cuanto antes este error, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com"
    val ERR_SUBJECT = String.format(
        Locale("es", "ES"),
        "Reporte de error Liturgia+ v. %s",
        VERSION_CODE_FORMATTED
    )
    const val ERR_LANG = "Lenguaje no soportado."
    const val ERR_INITIALIZATION = "Falló la inicialización."
    const val ERR_BIBLIA =
        "Todavía no hay introducción a este libro. <br>Proyecto abierto a la colaboración. <br><a href=\"http://bit.ly/2FInp4n\">Ver los detalles aquí</a>."
    val ERR_FILE_NOT_FOUND = String.format("%s\n\n%s", "Error: Archivo no encontrado.", ERR_REPORT)
    val ERR_WITH_VERSION = String.format(
        Locale("es", "ES"),
        "Ha ocurrido un error.%n %s %n%n Versión: %d %n%n",
        ERR_REPORT,
        VERSION_CODE
    )
    val ERR_NO_COMMENT = String.format(
        Locale("es", "ES"),
        "No hay comentarios para el Evangelio de hoy.\n %s",
        ERR_REPORT
    )

    //Otros mensajes
    const val PACIENCIA = "\n\nLa paciencia todo lo alcanza. Por favor espere ..."
    const val NBSP_SALMOS = "$BR&nbsp;&nbsp;"
    const val DATA_NOTFOUND = "No se encontraron datos"
    const val FIREBASE_SANTOS = "/liturgia/santos/"
    const val DOC_NOTFOUND = "Documento no encontrado"
    const val CONTENT_NOTFOUND = "Este contenido no está " +
            "disponible todavía. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha. Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo."
    const val NOTFOUND_OR_NOTCONNECTION = "Este contenido no" +
            " está " +
            "disponible. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha o que en este momento no estés " +
            "conectado a internet. " +
            "Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo."
    val CONTENT_TO_SYNC = String.format(
        "Contenido no encontrado. " +
                "Puede que estés intentando acceder a un " +
                "contenido fuera de fecha o que el contenido aún no exista. %sPuedes reportar esta incidencia al desarrollador para informarle de que hay un problema en " +
                "este módulo mandando un mensaje a <b>%spadre.cedano@gmail.com%s</b> en el que indiques la fecha y el módulo en que ha ocurrido el problema. %sLos contenidos no presentes se incluirán mediante sincronización remota y necesitarás tener conexión a internet para recibirlos.",
        BRS,
        CSS_RED_A,
        CSS_RED_Z,
        BRS
    )
    const val SYNC_LABEL = "Sincronizar"

    /*
        File paths raw/terms_202301.json
     */
    const val FILE_PRIVACY = "raw/privacy_202301.json"
    const val FILE_TERMS = "raw/terms_202301.json"
    const val FILE_ABOUT = "raw/about_202201.json"
    const val FILE_AUTHOR = "raw/author_202201.json"
    const val FILE_HELP = "raw/help_202201.json"
    const val FILE_NEW = "raw/new_202301.json"
    const val FILE_THANKS = "raw/thanks_202201.json"
    const val FILE_VIA_CRUCIS_2003 = "raw/viacrucis2003.json"
    const val FILE_VIA_CRUCIS_2005 = "raw/viacrucis2005.json"
    const val FILE_REGINA = "raw/regina.json"
    const val FILE_ANGELUS = "raw/angelus.json"
    const val FILE_LITANIES = "raw/letanias.json"
    const val FILE_ROSARY = "raw/rosario.json"
    const val FILE_NIGHT_PRAYER = "raw/completas.json"
    const val PREF_ACCEPT = "accept_terms"
    const val PREF_ANALYTICS = "enable_analytics"
    const val PREF_CRASHLYTICS = "enable_crashlytics"
    const val PREF_INITIAL_SYNC = "initial_sync"
    const val PREF_LAST_YEAR_CLEANED = "last_year_cleaned"
    const val SYNC_TAG = "TAG_SYNC_DATA"
    const val MSG_LEGAL = "Si tienes alguna duda, puedes " +
            "pulsar en el botón \"Enviar eMail\" para ponerte en contacto con" +
            " " +
            "el Desarrollador. Se abrirá tu programa de eMail para que " +
            "puedas " +
            "redactar y enviar tu mensaje. Los datos recibidos serán " +
            "tratados de acuerdo a " +
            "lo indicado en la Política de Privacidad.\n\n"
    const val DIALOG_LEGAL_TITLE = "Aviso"
    const val DIALOG_LEGAL_BODY =
        "Retirarás tu consentimiento y Liturgia+ se cerrará al pulsar CONFIRMAR."
    const val DIALOG_LEGAL_OK = "CONFIRMAR"
    const val DIALOG_LEGAL_CANCEL = "CANCELAR"

    /*
        Room table names
     */
    const val UNIVERSALIS_TABLE = "universalis"
    const val TODAY_TABLE = "today"
    const val DB_TABLE = "db_table"
    const val BIBLE_BOOK = "bible_book"
    const val BIBLE_HOMILY_JOIN = "bible_homily_join"
    const val BIBLE_HOMILY_THEME = "bible_homily_theme"
    const val BIBLE_READING = "bible_reading"
    const val HOMILY = "homily"
    const val LH_ANTIPHON_JOIN = "lh_antiphon_join"
    const val LH_ANTIPHON = "lh_antiphon"
    const val LITURGY = "liturgy"
    const val LITURGY_COLOR = "liturgy_color"
    const val LITURGY_TIME = "liturgy_time"
    const val LITURGY_GROUP = "liturgy_group"
    const val LITURGY_HOMILY_JOIN = "liturgy_homily_join"
    const val LITURGY_SAINT_JOIN = "liturgy_saint_join"
    const val LH_INVITATORY = "lh_invitatory"
    const val LH_INVITATORY_JOIN = "lh_invitatory_join"
    const val LH_GOSPEL_CANTICLE = "lh_gospel_canticle"
    const val LH_HYMN = "lh_hymn"
    const val LH_HYMN_JOIN = "lh_hymn_join"
    const val LH_INTERCESSIONS = "lh_intercessions"
    const val LH_INTERCESSIONS_JOIN = "lh_intercessions_join"
    const val LH_PSALM_JOIN = "lh_psalm_join"
    const val LH_PSALM = "lh_psalm"
    const val LH_PSALMODY = "lh_psalmody"
    const val LH_PSALMODY_JOIN = "lh_psalmody_join"
    const val LH_EPIGRAPH = "lh_epigraph"
    const val LH_THEME = "lh_theme"
    const val MASS_READING = "mass_reading"
    const val MASS_READING_JOIN = "mass_reading_join"
    const val PRAYER = "prayer"
    const val SAINT = "saint"
    const val SAINT_LIFE = "saint_life"
    const val SAINT_SHORT_LIFE = "saint_short_life"
    const val LH_OFFICE_BIBLICAL = "lh_office_biblical"
    const val LH_OFFICE_BIBLICAL_JOIN = "lh_office_biblical_join"
    const val LH_OFFICE_BIBLICAL_EASTER = "lh_office_biblical_easter"
    const val LH_OFFICE_PATRISTIC = "lh_office_patristic"
    const val LH_OFFICE_PATRISTIC_JOIN = "lh_office_patristic_join"
    const val LH_OFFICE_VERSE = "lh_office_verse"
    const val LH_OFFICE_VERSE_JOIN = "lh_office_verse_join"
    const val LH_PRAYER = "lh_prayer"
    const val LH_READING_SHORT = "lh_reading_short"
    const val LH_READING_SHORT_JOIN = "lh_reading_short_join"
    const val LH_RESPONSORY = "lh_responsory"
    const val LH_RESPONSORY_SHORT = "lh_responsory_short"
    const val SYNC_STATUS = "sync_status"
    const val PATER = "pater"
    const val PATER_OPUS = "pater_opus"
    const val LH_NIGHT_PRAYER = "lh_night_prayer"
    const val KYRIE = "kyrie"
    const val LH_KYRIE_JOIN = "lh_kyrie_join"
    const val VIRGIN_ANTIPHON = "virgin_antiphon"
    const val LH_VIRGIN_ANTIPHON_JOIN = "lh_virgin_antiphon_join"
    const val SYNC_LITURGY_HOMILY_JOIN = "sync_liturgy_homily_join"
    const val SYNC_MASS_READING = "sync_mass_reading"
    const val TITLE_MIXTO = "Laudes y Oficio"
    const val TITLE_OFICIO = "Oficio"
    const val TITLE_LAUDES = "Laudes"
    const val TITLE_TERCIA = "Tercia"
    const val TITLE_SEXTA = "Sexta"
    const val TITLE_NONA = "Nona"
    const val TITLE_VISPERAS = "Vísperas"
    const val TITLE_I_VISPERAS = "I Vísperas"
    const val TITLE_I_VISPERAS_READ = "Primeras Vísperas"
    const val TITLE_COMPLETAS = "Completas"
    const val TITLE_HYMN = "Himno"
    const val TITLE_MASS_READING = "Lecturas de la Misa"
    const val TITLE_INVITATORY = "Invitatorio"
    const val TITLE_INTERCESSIONS = "Preces"
    const val TITLE_OFFICE_OF_READING = "Lecturas del Oficio"
    const val TITLE_RESPONSORY = "Responsorio"
    const val TITLE_RESPONSORY_SHORT = "Responsorio Breve"
    const val TITLE_PSALMODY = "Salmodia"
    const val TITLE_TEDEUM = "Te Deum"
    const val TITLE_CONCLUSION = "Conclusión"
    const val TITLE_GOSPEL_CANTICLE = "Cántico Evangélico"
    const val TITLE_SHORT_READING = "Lectura Breve"
    const val TITLE_INITIAL_INVOCATION = "Invocación Inicial"
    const val TITLE_VIRGIN_ANTIHPON = "Antífona Final de la Santísima Virgen"
    const val TITLE_SOUL_SEARCHING = "Exámen de Conciencia"
    const val TITLE_PATER_NOSTER = "Padre Nuestro"
    const val TITLE_PRAYER = "Oración"
    const val TITLE_BIBLE_COMMENTS = "Comentarios"
    const val PRECES_IL = "Se pueden añadir algunas intenciones libres."
    const val OBIEN = BRS + CSS_RED_A + "O bien:" + CSS_RED_Z + BRS
    const val PRECES_R = "–"
}