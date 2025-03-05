package org.deiverbum.app.core.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import org.deiverbum.app.core.designsystem.component.textForAudio
import org.deiverbum.app.core.designsystem.component.textFromListAudio
import org.deiverbum.app.core.designsystem.component.transformEpigraphAudio
import org.deiverbum.app.core.designsystem.component.transformTextAudio
import org.deiverbum.app.core.model.data.Breviarium
import org.deiverbum.app.core.model.data.Introitus
import org.deiverbum.app.core.model.data.LHAntiphon
import org.deiverbum.app.core.model.data.LHHymn
import org.deiverbum.app.core.model.data.LHIntercession
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHInvitatory
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHLectioBrevis
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHPsalm
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LHResponsoriumBrevis
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Oratio
import org.deiverbum.app.core.model.data.PadreNuestro
import org.deiverbum.app.core.model.data.RitusConclusionis
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.normalizeForRead
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.ErrorHelper
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.LiturgyHelper.Companion.gloriaNonDicitur
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.splitParts

/**
 * Devuelve el contenido del Oficio para audio.
 *
 * @param data Un objeto [LHOfficium] con el contenido de la hora
 * @param userData Un objeto [UserData] con las configuraciones del usuario
 * @param calendarTime Un identificador del tiempo litúrgico para algunas normalizaciones
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [LHOfficium]
 */

fun officiumAudio(
    data: LHOfficium,
    userData: UserData,
    calendarTime: Int,
): AnnotatedString {
    var text = AnnotatedString("")

    data.officiumLectionis.normalizeByTime(calendarTime)
    if (data.sanctus != null && data.hasSaint) {
        data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
        text += AnnotatedString.fromHtml(data.sanctus!!.vitaBrevis)
    }
    //text += introitusMaiorAudio()
    //text += invitatoriumAudio(data.invitatorium, calendarTime, userData)

    //text += hymnus(data.hymnus, userData, rubricColor,fontSize)
    text += psalmodiaAudio(data.psalmodia, -1, calendarTime, userData.dynamic)
    /*text += officiumLectionis(
        officiumLectionis = data.officiumLectionis,
        userData = userData.dynamic,
        fontSize = fontSize,
        rubricColor = rubricColor
    )
    text += oratio(data = data.oratio, fontSize=fontSize,rubricColor = rubricColor, userData = userData.dynamic)
    text += conclusionisMaior(
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )*/
    return text
    //}
}

fun audioIntermedia(
    data: LHIntermedia,
    userData: UserData,
    calendarTime: Int,
): AnnotatedString {
    var text = AnnotatedString("")
    text += introitusMinorAudio()
    text += hymnusAudio(data.hymnus, userData)
    text += psalmodiaAudio(data.psalmodia, -1, calendarTime, userData.dynamic)
    text += lectioBrevisAudio(data.lectioBrevis, userData.dynamic)
    text += oratioAudio(data = data.oratio, userData = userData.dynamic)
    text += conclusionisMinorAudio()
    return text
}


/**
 * Devuelve el contenido de Laudes para audio.
 *
 * @param data Un objeto [LHLaudes] con el contenido de la hora
 * @param userData Un objeto [UserData] con las configuraciones del usuario
 * @param calendarTime Un identificador del tiempo litúrgico para algunas normalizaciones
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [LHLaudes]
 */

fun audioLaudes(
    data: LHLaudes,
    userData: UserData,
    calendarTime: Int,
): AnnotatedString {
    var text = AnnotatedString("")
    text += introitusMaiorAudio()
    text += hymnusAudio(data.hymnus, userData)
    text += psalmodiaAudio(data.psalmodia, -1, calendarTime, userData.dynamic)
    text += lectioBrevisAudio(data.lectioBrevis, userData.dynamic)
    text += canticumEvangelicumAudio(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic
    )
    text += precesAudio(preces = data.preces, userData = userData.dynamic)
    text += transformTextAudio(text = PadreNuestro.texto)
    text += oratioAudio(data = data.oratio, userData = userData.dynamic)
    text += conclusionisMaiorAudio()
    return text
    //}
}


/**
 * Devuelve el contenido de Vísperas para audio.
 *
 * @param data Un objeto [LHVesperas] con el contenido de la hora
 * @param userData Un objeto [UserData] con las configuraciones del usuario
 * @param calendarTime Un identificador del tiempo litúrgico para algunas normalizaciones
 *
 * @return Un objeto [AnnotatedString] con el texto a ser leído.
 *
 * @since 2025.1
 *
 * @see [LHVesperas]
 */

fun audioVesperas(
    data: LHVesperas,
    userData: UserData,
    calendarTime: Int,
): AnnotatedString {
    var text = AnnotatedString("")
    /*text += introitusMaiorAudio()
    text += hymnusAudio(data.hymnus, userData)
    text += psalmodiaAudio(data.psalmodia, -1, calendarTime, userData.dynamic)
    text += lectioBrevisAudio(data.lectioBrevis, userData.dynamic)
    text += canticumEvangelicumAudio(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic
    )*/
    text += precesAudio(preces = data.preces, userData = userData.dynamic)
    text += transformTextAudio(text = PadreNuestro.texto)
    text += oratioAudio(data = data.oratio, userData = userData.dynamic)
    text += conclusionisMaiorAudio()
    return text
    //}
}


/**
 * Devuelve la introducción de las horas mayores para audio.
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

fun introitusMaiorAudio(): AnnotatedString {
    return buildAnnotatedString {
        append(Constants.TITLE_INITIAL_INVOCATION)
        append(".")
        append(textFromListAudio(texts = Introitus().prima))
        append(finisPsalmusAudio())
    }
}

/**
 * Crea la introducción de las horas menores para audio.
 *
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun introitusMinorAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_INITIAL_INVOCATION}.")
        append(textFromListAudio(texts = Introitus().altera))
        append(finisPsalmusAudio())
    }
}

/**
 * Crea el contenido del Invitatorio para audio.
 *
 * @param psalmodia Un objeto [LHInvitatory] con el invitatorio
 * @param calendarTime Identificador del tiempo litúrgico
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 *
 * @return Un objeto [AnnotatedString] con el invitatorio formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun invitatoriumAudio(
    psalmodia: LHInvitatory,
    calendarTime: Int,
    userData: UserData,
): AnnotatedString {
    return buildAnnotatedString {
        append(Constants.TITLE_INVITATORY)
        append(".")
        append(
            psalmodiaUnicaAudio(
                psalmodia, calendarTime, userData.dynamic
            )
        )
    }
}

/**
 * Crea el contenido del Himno para audio.
 *
 * @param data Un objeto [LHHymn] con el Himno
 * @param userData Un objeto [UserDataDynamic] con la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con el himno formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun hymnusAudio(
    data: LHHymn,
    userData: UserData,
): AnnotatedString {
    return buildAnnotatedString {
        append(textForAudio("${Constants.TITLE_HYMN}."))
        append(transformTextAudio(data.hymnus))
    }
}


/**
 * Crea el contenido de la Salmodia, según los diferentes tipos.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 * @param indexHora El índice de la hora (para el caso de la Hora Intermedia)
 * @param calendarTime Identificador del tiempo litúrgico
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
fun psalmodiaAudio(
    psalmodia: LHPsalmody,
    indexHora: Int,
    calendarTime: Int,
    userData: UserDataDynamic
): AnnotatedString {
    var text = AnnotatedString("")

    try {
        val antiphonaeAnte = AnnotatedString.Builder()
        val antiphonaePost = AnnotatedString.Builder()
        text += textForAudio("${Constants.TITLE_PSALMODY}.")

        if (psalmodia.typus == 1) {
            if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                psalmodia.antiphonae[indexHora].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnteAudio(
                        psalmodia.antiphonae[indexHora],
                        false
                    )
                )
            } else {
                psalmodia.antiphonae[0].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnteAudio(
                        psalmodia.antiphonae[0],
                        true
                    )
                )
                antiphonaePost.append(
                    antiphonaePostAudio(
                        psalmodia.antiphonae[0],
                    )
                )
            }
            text += antiphonaeAnte.toAnnotatedString()
            for (s in psalmodia.psalmus) {
                text += psalmusAudio(s, userData)
            }
            text += antiphonaePostAudio(psalmodia.antiphonae[indexHora])
        }

        if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
            val lastIndex = psalmodia.psalmus.lastIndex
            psalmodia.psalmus.forEachIndexed { index, s ->
                psalmodia.antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                text += antiphonaeAnteAudio(
                    psalmodia.antiphonae[s.theOrder - 1],
                    true
                )

                text += psalmusAudio(s, userData)
                text += antiphonaePostAudio(psalmodia.antiphonae[s.theOrder - 1])
            }
        }
        return text
    } catch (e: Exception) {
        return AnnotatedString(Utils.createErrorMessage(e.message))
    }
}

/**
 * Crea el contenido de la Salmodia para audio, cuando hay un solo Salmo / Antífona.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 */
fun psalmodiaUnicaAudio(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic,
): AnnotatedString {
    psalmodia.antiphonae[0].normalizeByTime(calendarTime)
    return antiphonaeEtPsalmusAudio(
        psalmodia.antiphonae[0],
        false,
        psalmodia.psalmus[0],
        calendarTime,
        userData
    )
}

/**
 * Crea el contenido para audio de la Antífona y el Salmo para la Salmodia.
 *
 * @param antiphonae Un objeto [LHAntiphon] con la antífona
 * @param withOrder Para indicar si la antífona lleva orden numérico
 * @param psalmus Un objeto [LHPsalm] con el salmo
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @return Un objeto [AnnotatedString] con la salmodia formateada.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHPsalmody]
 * @see [LHAntiphon]
 * @see [LHPsalm]
 */
fun antiphonaeEtPsalmusAudio(
    antiphonae: LHAntiphon,
    withOrder: Boolean,
    psalmus: LHPsalm,
    calendarTime: Int,
    userData: UserDataDynamic,
): AnnotatedString {
    antiphonae.normalizeByTime(calendarTime)
    return buildAnnotatedString {
        append(antiphonaeAnteAudio(antiphonae, withOrder))
        append(psalmusAudio(psalmus, userData))
        append(antiphonaePostAudio(antiphonae))
    }
}

/**
 * Prepara para la vista la antífona antes del salmo.
 * En la salmodia, cuando corresponden varias antífonas, las mismas se presentan antes del
 * salmo precedidas de la palabra "Ant. " más el número de la antífona, que se determina
 * mediante la propiedad [LHAntiphon.theOrder].
 *
 * También aquí se determina mediante el valor de [LHAntiphon.haveSymbol] si el texto de la antífona contiene el símbolo †
 * para colocarlo al final de la misma formateado con el color de rúbrica.
 *
 * @param antiphonae antífona representada por un objeto [LHAntiphon].
 * @param rubricColor el color para las rúbricas, según el modo claro u oscuro.
 * @param withOrder Para determinar si se requiere el orden de la antífona.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun antiphonaeAnteAudio(
    antiphonae: LHAntiphon,
    withOrder: Boolean = true
): AnnotatedString {
    return buildAnnotatedString {
        if (withOrder) {
            append("Antífona ${antiphonae.theOrder}. ")
        } else {
            append("Antífona: ")
        }
        append(antiphonae.antiphon)
        /*if (antiphonae.haveSymbol) {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
            append(textRubric(" † ", rubricColor, fontSize))
        } else {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
        }*/
    }
}

/**
 * Prepara para la vista la antífona después del salmo.
 * En la salmodia, las antífonas después del salmo se presentan siempre
 * precedidas de la palabra "Ant. " y luego la antífona.
 *
 * @param antiphonae antífona representada por un objeto [LHAntiphon].
 * @param rubricColor el color para las rúbricas, según el modo claro u oscuro.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun antiphonaePostAudio(
    antiphonae: LHAntiphon,
): AnnotatedString {
    return buildAnnotatedString {
        append("Antífona: ")
        append(antiphonae.antiphon)
    }
}

/**
 * Prepara un objeto [LHPsalm] para la vista.
 * Verifica si el salmo termina con el marcador "∸", en cuyo caso,
 * se omite al final el "Gloria al Padre ..."
 *
 * @param psalmus salmo representado por un objeto [LHPsalm].
 * @param rubricColor el color para las rúbricas, según el modo claro u oscuro.
 *
 * @return Un [AnnotatedString] con la antífona preparada.
 *
 * @since 2025.1
 */

fun psalmusAudio(
    psalmus: LHPsalm,
    userData: UserDataDynamic,
): AnnotatedString {
    var text = AnnotatedString("")
    //val normalizeText = psalmus.normalize()
    if (psalmus.pericopa != "") {
        text += textForAudio("${psalmus.pericopa}.")
    }
    if ((psalmus.theme != null) && (psalmus.theme != "")) {
        text += textForAudio("${psalmus.theme!!}.")
    }
    if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
        text += transformEpigraphAudio(psalmus.epigraph!!)
    }

    text += transformTextAudio(text = psalmus.psalmus)
    text += finisPsalmusAudio(psalmus.haveGloria)
    return text
}

fun finisPsalmusAudio(haveGloria: Boolean = true): AnnotatedString {
    return if (haveGloria) {
        textFromListAudio(texts = LiturgyHelper.finisPsalmus)
    } else {
        buildAnnotatedString {
            append(gloriaNonDicitur)

        }
    }
}

fun lectioBrevisAudio(
    data: LHLectioBrevis,
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_SHORT_READING}.")
        append(textForAudio(data.biblica))
        append(
            responsoriumBrevisAudio(
                data = data.responsorium,
                userData = userData,

                )
        )
    }
}

fun responsoriumBrevisAudio(
    data: LHResponsoriumBrevis,
    userData: UserDataDynamic,

    ): AnnotatedString {
    return buildAnnotatedString {
        responsoriumBrevisTitleAudio(
            typus = data.typus,
            userData = userData,
        )
        append(responsoriumBrevisTextAudio(data))
    }
}

fun responsoriumBrevisTitleAudio(
    typus: Int,
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        if (typus > 0) {
            append("${Constants.TITLE_RESPONSORY_SHORT}.")
        } else {
            append(Constants.NOT_RESPONSORY)
        }
    }
}

fun responsoriumBrevisTextAudio(
    data: LHResponsoriumBrevis,
): AnnotatedString {
    val respArray = data.responsorium.splitParts("\\|")
    return buildAnnotatedString {
        when (data.typus) {
            0 -> {
                append(textForAudio(data.responsorium))
            }

            1 -> if (respArray.size == 3) {
                append(textForAudio(text = respArray[0]))
                append(" ")
                append(textForAudio(text = respArray[1]))
                append(" ")
                append(textForAudio(text = respArray[2]))
                append(" ")
                append(textForAudio(text = respArray[1]))
            }

            2 -> {
                append(textForAudio(text = respArray[0]))
                append(" ")
                append(textForAudio(text = respArray[1]))
                append(" ")
                append(textForAudio(text = respArray[2]))
                append(" ")
                append(textForAudio(text = respArray[1]))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textForAudio(text = respArray[0]))
                    append(textForAudio(text = respArray[0]))
                    append(textForAudio(text = respArray[1]))
                    append(textForAudio(text = respArray[2]))
                    append(textForAudio(text = respArray[3]))
                    append(textForAudio(text = respArray[0]))
                }

            6001020 -> if (respArray.size == 3) {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[2]))
                append(textForAudio(text = respArray[0]))
            }

            4 -> {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[2]))
                append(textForAudio(text = respArray[0]))
            }

            201 -> {
                append(textForAudio(text = respArray[0]))
                append(textForAudio(text = respArray[1]))
            }

            else -> {
                append(LS2)
                append(errorMessageAudio(ErrorHelper.RESPONSORIUM))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(data.typus.toString())

            }
        }
    }
}

fun canticumEvangelicumAudio(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_GOSPEL_CANTICLE}.")
        append(psalmodiaUnicaAudio(psalmodia, calendarTime, userData))
    }
}

fun precesAudio(
    preces: LHIntercession,
    userData: UserDataDynamic
): AnnotatedString {
    val introArray = preces.intro.splitParts("\\|")
    val listPreces = preces.normalizeForRead().splitParts("∞")
    return buildAnnotatedString {
        if (listPreces.isNotEmpty() && introArray.size == 3) {
            val response = introArray[1]
            val firstPart = listPreces[0].splitParts("§")
            append("${Constants.TITLE_INTERCESSIONS}.")
            append(textForAudio(introArray[0]))
            append(response)
            firstPart.forEach {
                append(it)
                append(response)
            }
            if (listPreces.size == 2) {
                append(listPreces[1])
                append(response)
            }
            append(textForAudio(introArray[2]))
        } else {
            append(errorMessageAudio(ErrorHelper.PRECES))
            append(textForAudio(preces.intro))
            append(" ")
            append(transformTextAudio(text = preces.normalizeForRead()))
        }
    }
}


fun oratioAudio(data: Oratio, userData: UserDataDynamic): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_PRAYER}.")
        append(transformTextAudio(text = data.oratio))
    }
}

fun conclusionisMaiorAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_CONCLUSION}.")
        append(textFromListAudio(RitusConclusionis().maior))
    }
}

fun conclusionisMinorAudio(
): AnnotatedString {
    return buildAnnotatedString {
        append("${Constants.TITLE_CONCLUSION}.")
        append(textFromListAudio(RitusConclusionis().minor))
    }
}
