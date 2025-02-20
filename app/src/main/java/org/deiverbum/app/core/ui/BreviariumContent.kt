package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.stringFromHtml
import org.deiverbum.app.core.designsystem.component.textMultiColor
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textSmall
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textSpan
import org.deiverbum.app.core.designsystem.component.textVR
import org.deiverbum.app.core.designsystem.component.textWithR
import org.deiverbum.app.core.designsystem.component.textWithV
import org.deiverbum.app.core.designsystem.component.transformEpigraph
import org.deiverbum.app.core.model.data.Breviarium
import org.deiverbum.app.core.model.data.Introitus
import org.deiverbum.app.core.model.data.Kyrie
import org.deiverbum.app.core.model.data.LHAntiphon
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHHymn
import org.deiverbum.app.core.model.data.LHIntercession
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHInvitatory
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHLectioBrevis
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHOfficiumLectioAltera
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior
import org.deiverbum.app.core.model.data.LHOfficiumLectionis
import org.deiverbum.app.core.model.data.LHPsalm
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LHResponsoriumBrevis
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.Oratio
import org.deiverbum.app.core.model.data.PadreNuestro
import org.deiverbum.app.core.model.data.RitusConclusionis
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.ErrorHelper
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.LiturgyHelper.Companion.gloriaNonDicitur
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.toRoman
import org.deiverbum.app.util.Utils.transformText
import java.util.Locale

/**
 * Pantalla para  Mixto: Oficio + Laudes
 *
 * @param data Una instancia de [LHOfficium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */


@Composable
fun MixtusScreen(
    data: LHMixtus,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {

    var text = AnnotatedString("")

    Column {
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusMaior(userData = userData, rubricColor = rubricColor)
        text += invitatorium(data.invitatorium, calendarTime, userData, rubricColor)
        text += hymnus(data.hymnus, userData, rubricColor)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, rubricColor)
        text += officiumLectionis(data.officiumLectionis, userData.dynamic, rubricColor)
        text += missaeLectionum(
            lectionum = data.lectionumList,
            userData.dynamic,
            rubricColor = rubricColor
        )
        text += canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            calendarTime = calendarTime,
            userData = userData.dynamic,
            rubricColor = rubricColor
        )
        text += preces(preces = data.preces, userData = userData.dynamic, rubricColor = rubricColor)
        text += paterNoster(userData.dynamic, rubricColor)
        text += oratio(data = data.oratio, userData = userData.dynamic, rubricColor = rubricColor)
        text += conclusionisMaior(userData = userData.dynamic, rubricColor = rubricColor)
        TextZoomable(onTap = onTap, text = text)
    }
}

/**
 * Pantalla para  el Oficio
 *
 * @param data Una instancia de [LHOfficium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun OfficiumScreen(
    data: LHOfficium,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    var text = AnnotatedString("")

    Column {
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusMaior(userData = userData, rubricColor = rubricColor)
        text += invitatorium(data.invitatorium, calendarTime, userData, rubricColor)
        text += hymnus(data.hymnus, userData, rubricColor)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
        text += officiumLectionis(data.officiumLectionis, userData.dynamic, rubricColor)
        text += oratio(data = data.oratio, rubricColor = rubricColor, userData = userData.dynamic)
        text += conclusionisMaior(userData = userData.dynamic, rubricColor = rubricColor)
        TextZoomable(onTap = onTap, text = text)
    }
}

/**
 * Pantalla para Laudes
 *
 * @param data Una instancia de [LHLaudes]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun LaudesScreen(
    data: LHLaudes,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    var text = AnnotatedString("")

    Column {
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusMaior(userData = userData, rubricColor = rubricColor)
        text += invitatorium(data.invitatorium, calendarTime, userData, rubricColor)
        text += hymnus(data.hymnus, userData, rubricColor)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, rubricColor)
        text += canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            calendarTime = calendarTime,
            userData = userData.dynamic,
            rubricColor = rubricColor
        )
        text += preces(preces = data.preces, userData = userData.dynamic, rubricColor = rubricColor)
        text += paterNoster(userData.dynamic, rubricColor)
        text += oratio(data = data.oratio, userData = userData.dynamic, rubricColor = rubricColor)
        text += conclusionisMaior(userData = userData.dynamic, rubricColor = rubricColor)
        TextZoomable(onTap = onTap, text = text)
    }
}

/**
 * Pantalla para  las horas intermedias.
 *
 * @param data Una instancia de [LHIntermedia]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@ExperimentalMaterial3Api
@Composable
fun IntermediaScreen(
    data: LHIntermedia,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    var text = AnnotatedString("")
    Column {
        text += introitusMinor(userData = userData.dynamic, rubricColor = rubricColor)
        text += hymnus(data.hymnus, userData, rubricColor)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, rubricColor)
        text += oratio(data = data.oratio, userData = userData.dynamic, rubricColor = rubricColor)
        text += conclusionisMinor(userData = userData.dynamic, rubricColor = rubricColor)
        //ZoomableText(text,userData)
        TextZoomable(onTap = onTap, text = text)

    }
}

/**
 * Pantalla para  Vísperas
 *
 * @param data Una instancia de [LHVesperas]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun VesperasScreen(
    data: LHVesperas,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    var text = AnnotatedString("")
    text += introitusMaior(userData = userData, rubricColor = rubricColor)
    text += hymnus(data.hymnus, userData, rubricColor)
    text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
    text += lectioBrevis(data.lectioBrevis, userData.dynamic, rubricColor)
    text += canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic,
        rubricColor = rubricColor
    )
    text += preces(preces = data.preces, userData = userData.dynamic, rubricColor = rubricColor)
    text += paterNoster(userData.dynamic, rubricColor)
    text += oratio(data = data.oratio, userData = userData.dynamic, rubricColor = rubricColor)
    text += conclusionisMaior(userData = userData.dynamic, rubricColor = rubricColor)
    TextZoomable(onTap = onTap, text = text)
    //ZoomableBox(text)
}

/**
 * Pantalla para  Completas
 *
 * @param data Una instancia de [LHCompletorium]
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param calendarTime Identificador del tiempo litúrgico
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
@Composable
fun CompletoriumScreen(
    data: LHCompletorium,
    userData: UserData,
    rubricColor: Color,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    data.lectioBrevis.normalizeByTime(calendarTime)
    var text = AnnotatedString("")
    text += introitusMinor(userData = userData.dynamic, rubricColor = rubricColor)
    text += completoriumKyrie(
        data = data.kyrie,
        userData = userData.dynamic,
        rubricColor = rubricColor
    )
    text += hymnus(data.hymnus, userData, rubricColor)
    text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, rubricColor)
    text += lectioBrevis(data.lectioBrevis, userData.dynamic, rubricColor)
    text += canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic,
        rubricColor = rubricColor
    )
    text += paterNoster(userData.dynamic, rubricColor)
    text += oratio(data = data.oratio, userData = userData.dynamic, rubricColor = rubricColor)
    text += conclusionisCompletorium(
        data = data,
        userData = userData.dynamic,
        rubricColor = rubricColor
    )
    TextZoomable(onTap = onTap, text = text)
}

/**
 * Devuelve la introducción de las horas mayores.
 *
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

fun introitusMaior(userData: UserData, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, userData.dynamic, rubricColor))
        append(textVR(texts = Introitus().maior, rubricColor))
        append(LS)
        append(finisPsalmus())
    }
}

/**
 * Crea la introducción de las horas menores.
 *
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con la introducción.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun introitusMinor(userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, userData, rubricColor))
        append(textVR(texts = Introitus().minor, rubricColor))
        append(LS)
        append(finisPsalmus())
    }
}

/**
 * Crea el contenido del Invitatorio.
 *
 * @param psalmodia Un objeto [LHInvitatory] con el invitatorio
 * @param calendarTime Identificador del tiempo litúrgico
 * @param userData Un objeto [UserDataDynamic] con los ajustes del usuario
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con el invitatorio formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun invitatorium(
    psalmodia: LHInvitatory,
    calendarTime: Int,
    userData: UserData,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        append(sectionTitle(Constants.TITLE_INVITATORY, 1, userData.dynamic))
        append(psalmodiaUnica(psalmodia, calendarTime, userData.dynamic, rubricColor))
    }
}

/**
 * Crea el contenido del Himno.
 *
 * @param data Un objeto [LHHymn] con el Himno
 * @param userData Un objeto [UserDataDynamic] con la configuración del usuario
 * @param rubricColor El color para las rúbricas según la configuración del usuario
 *
 * @return Un objeto [AnnotatedString] con el himno formateado.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */
fun hymnus(data: LHHymn, userData: UserData, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_HYMN, 2, userData.dynamic, rubricColor))
        append(Utils.transformBodyText(data.hymnus, rubricColor))
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
fun psalmodia(
    psalmodia: LHPsalmody,
    indexHora: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    try {
        val antiphonaeAnte = AnnotatedString.Builder()
        val antiphonaePost = AnnotatedString.Builder()
        text += contentTitle(Constants.TITLE_PSALMODY, 2, userData, rubricColor)
        if (psalmodia.typus == 1) {
            if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                psalmodia.antiphonae[indexHora].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnte(
                        psalmodia.antiphonae[indexHora],
                        rubricColor,
                        false
                    )
                )
            } else {
                psalmodia.antiphonae[0].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnte(
                        psalmodia.antiphonae[0],
                        rubricColor,
                        true
                    )
                )
                antiphonaePost.append(antiphonaePost(psalmodia.antiphonae[0], rubricColor))
            }
            text += antiphonaeAnte.toAnnotatedString()
            text += contentSpace(10)
            for (s in psalmodia.psalmus) {
                text += psalmus(s, userData, rubricColor)
            }
            text += antiphonaePost(psalmodia.antiphonae[indexHora], rubricColor)
        }

        if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
            val lastIndex = psalmodia.psalmus.lastIndex
            psalmodia.psalmus.forEachIndexed { index, s ->
                psalmodia.antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                text += antiphonaeAnte(psalmodia.antiphonae[s.theOrder - 1], rubricColor, true)
                text += contentSpace(10)
                text += psalmus(s, userData, rubricColor)
                //append(LS)
                text += contentSpace(5)
                text += antiphonaePost(psalmodia.antiphonae[s.theOrder - 1], rubricColor)
                if (index != lastIndex) {
                    text += contentSpace(10)
                }
            }
        }
        return text
    } catch (e: Exception) {
        return AnnotatedString(Utils.createErrorMessage(e.message))
    }
}

/**
 * Crea el contenido de la Salmodia, cuando hay un solo Salmo / Antífona.
 *
 * @param psalmodia Un objeto [LHPsalmody] con la salmodia
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
fun psalmodiaUnica(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    psalmodia.antiphonae[0].normalizeByTime(calendarTime)
    return antiphonaeEtPsalmus(
        psalmodia.antiphonae[0],
        false,
        psalmodia.psalmus[0],
        calendarTime,
        userData, rubricColor
    )
}

/**
 * Crea el contenido de la Antífona y el Salmo para la Salmodia.
 *
 * @param antiphonae Un objeto [LHAntiphon] con la antífona
 * @param withOrder Para indicar si la antífona lleva orden numérico
 * @param psalmus Un objeto [LHPsalm] con el salmo
 * @param calendarTime Identificador del tiempo litúrgico
 * @param rubricColor El color para las rúbricas según la configuración del usuario
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
fun antiphonaeEtPsalmus(
    antiphonae: LHAntiphon,
    withOrder: Boolean,
    psalmus: LHPsalm,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    antiphonae.normalizeByTime(calendarTime)
    return buildAnnotatedString {
        append(antiphonaeAnte(antiphonae, rubricColor, withOrder))
        append(LS2)
        append(psalmus(psalmus, userData, rubricColor))
        append(LS)
        append(antiphonaePost(antiphonae, rubricColor))
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

fun antiphonaeAnte(
    antiphonae: LHAntiphon,
    rubricColor: Color,
    withOrder: Boolean = true
): AnnotatedString {
    return buildAnnotatedString {
        if (withOrder) {
            append(textRubric("Ant. ${antiphonae.theOrder}. ", rubricColor))
        } else {
            append(textRubric("Ant. ", rubricColor))
        }
        if (antiphonae.haveSymbol) {
            append(antiphonae.antiphon)
            append(textRubric(" † ", rubricColor))
        } else {
            append(antiphonae.antiphon)
        }
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

fun antiphonaePost(
    antiphonae: LHAntiphon,
    rubricColor: Color,
): AnnotatedString {
    return buildAnnotatedString {
        append(textRubric("Ant. ", rubricColor))
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

fun psalmus(
    psalmus: LHPsalm,
    userData: UserDataDynamic,
    rubricColor: Color,
): AnnotatedString {
    var text = AnnotatedString("")
    if (psalmus.pericopa != "") {
        text += textRubric(psalmus.pericopa, rubricColor)
        text += contentSpace(10)
    }
    if ((psalmus.theme != null) && (psalmus.theme != "")) {
        text += textRubric(psalmus.theme!!, rubricColor)
        text += contentSpace(10)
    }
    if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
        text += transformEpigraph(psalmus.epigraph!!, userData)
        text += contentSpace(10)
    }
    if ((psalmus.thePart != null) && (psalmus.thePart != 0)) {
        text += textRubric(toRoman(psalmus.thePart), rubricColor)
        text += contentSpace(10)
    }
    text += transformText(psalmus.psalmus, rubricColor)
    text += contentSpace(5)

    text += if (psalmus.psalmus.endsWith("∸")) {
        AnnotatedString(gloriaNonDicitur)
    } else {
        finisPsalmus()
    }
    return text
}

fun lectioBrevis(
    data: LHLectioBrevis,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitleAndText(
                listOf(Constants.TITLE_SHORT_READING, data.pericopa),
                2,
                userData,
                rubricColor
            )
        )
        append(Utils.transformBodyText(data.biblica, rubricColor))
        append(LS2)
        append(
            responsoriumBrevis(
                data = data.responsorium,
                userData = userData,
                rubricColor = rubricColor
            )
        )
    }
}

fun responsoriumBrevis(
    data: LHResponsoriumBrevis,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        responsoriumBrevisTitle(typus = data.typus, userData = userData, rubricColor = rubricColor)
        append(responsoriumBrevisText(data, rubricColor))
    }
}

fun responsoriumBrevisText(
    data: LHResponsoriumBrevis,
    rubricColor: Color
): AnnotatedString {
    val respArray =
        data.responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return buildAnnotatedString {
        when (data.typus) {
            0 -> {
                append(LS2)
                append(data.responsorium)
                append(LS)
            }

            1 -> if (respArray.size == 3) {
                append(textWithR(respArray[0], rubricColor))
                append(textRubric(" * ", rubricColor))
                append(respArray[1])
                append(LS2)
                append(textWithV(respArray[2], rubricColor))
                append(LS2)
                append(textRubric(LiturgyHelper.R, rubricColor))
                append(respArray[1][0].uppercaseChar())
                append(respArray[1].substring(1))
            }

            2 -> {
                append(textWithR(respArray[0], rubricColor))
                append(textRubric(" * ", rubricColor))
                append(respArray[1])
                append(LS2)
                append(textWithV(respArray[2], rubricColor))
                append(LS2)
                append(textRubric(LiturgyHelper.R, rubricColor))
                append(respArray[1][0].uppercaseChar())
                append(respArray[1].substring(1))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textWithV(respArray[0], rubricColor))
                    append(LS)
                    append(textWithR(respArray[0], rubricColor))
                    append(LS2)
                    append(textWithV(respArray[1], rubricColor))
                    append(LS)
                    append(textWithR(respArray[2], rubricColor))
                    append(LS2)
                    append(textWithV(respArray[3], rubricColor))
                    append(LS)
                    append(textWithR(respArray[0], rubricColor))
                }

            6001020 -> if (respArray.size == 3) {
                append(textWithV(respArray[0], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
                append(LS2)
                append(textWithV(respArray[1], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
                append(LS2)
                append(textWithV(respArray[2], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
            }

            4 -> {
                append(textWithV(respArray[0], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
                append(LS2)
                append(textWithV(respArray[1], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
                append(LS2)
                append(textWithV(respArray[2], rubricColor))
                append(LS)
                append(textWithR(respArray[0], rubricColor))
            }

            201 -> {
                append(textWithV(respArray[0], rubricColor))
                append(LS)
                append(textWithR(respArray[1], rubricColor))
            }

            else -> {
                append(LS2)
                append(errorMessage(ErrorHelper.RESPONSORIUM, rubricColor))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(data.typus.toString())
                append(LS)
            }
        }
    }
}

fun responsoriumBrevisTitle(
    typus: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        if (typus > 0) {
            append(contentTitle(Constants.TITLE_RESPONSORY_SHORT, 2, userData, rubricColor))
        } else {
            textRubric(
                "En lugar del responsorio breve, se dice la siguiente antífona:",
                rubricColor
            )
        }
    }
}

fun officiumLectionis(
    officiumLectionis: LHOfficiumLectionis,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    val list =
        officiumLectionis.responsorium.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
            .toList()
    text += contentSpace(10)
    text += sectionTitle(Constants.TITLE_OFFICE_OF_READING, 1, userData)
    text += textVR(list, rubricColor)
    text += lectioPrior(officiumLectionis.lectioPrior, userData, rubricColor)
    text += contentSpace(10)
    text += lectioAltera(officiumLectionis.lectioAltera, userData, rubricColor)
    text += contentSpace(10)
    return text
}

fun lectioPrior(
    lectioPrior: MutableList<LHOfficiumLectioPrior>,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    for (item in lectioPrior) {
        text += contentTitle("PRIMERA LECTURA", 2, userData, rubricColor)
        text += textMultiColor(listOf(item.book.liturgyName, item.pericopa), rubricColor)
        text += contentSpace(10)
        text += textRubric(item.tema, rubricColor)
        text += contentSpace(10)
        text += textFromHtml(item.biblica)
        text += textRubric("Responsorio ${item.responsorium.source}", rubricColor)
        text += contentSpace(10)
        text += responsoriumBrevisText(item.responsorium, rubricColor)
    }
    return text
}

fun lectioAltera(
    lectioAltera: MutableList<LHOfficiumLectioAltera>,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    for (item in lectioAltera) {
        text += contentTitle("SEGUNDA LECTURA", 2, userData, rubricColor)
        text += textSpan(item.paterOpus?.opusForView!!)
        text += contentSpace(10)
        text += textRubric(item.theSource!!, rubricColor)
        text += contentSpace(10)
        text += textRubric(item.tema!!, rubricColor)
        text += contentSpace(10)
        text += textFromHtml(item.homilia)
        text += textRubric("Responsorio ${item.responsorium!!.source}", rubricColor)
        text += contentSpace(10)
        text += responsoriumBrevisText(item.responsorium!!, rubricColor)
    }
    return text
}

fun canticumEvangelicum(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitle(
                Constants.TITLE_GOSPEL_CANTICLE,
                2,
                userData,
                rubricColor = rubricColor
            )
        )
        append(psalmodiaUnica(psalmodia, calendarTime, userData, rubricColor))
    }
}

fun missaeLectionum(
    lectionum: MissaeLectionumList,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        for (item in lectionum.lectionum) {
            append(contentTitle("EVANGELIO", 2, userData, rubricColor))
            append(textMultiColor(listOf(item!!.book.liturgyName, item.pericopa), rubricColor))
            append(LS2)
            append(textRubric(item.tema, rubricColor))
            append(LS2)
            append(lectioSimplex(item, -1, userData, rubricColor))
        }
    }
}

fun preces(preces: LHIntercession, userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    val introArray =
        preces.intro.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INTERCESSIONS, 2, userData, rubricColor))
        if (introArray.size == 3) {
            append(introArray[0])
            append(LS2)
            append(Utils.fromHtml(String.format(Locale("es"), "<i>%s</i>", introArray[1])))
            append(LS2)
            append(Utils.transformBodyText(preces.preces, rubricColor))
            append(introArray[2])
        } else {
            append(preces.intro)
            append(LS2)
            append(Utils.transformBodyText(preces.preces, rubricColor))
        }
    }
}

fun paterNoster(userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_PATER_NOSTER, 2, userData, rubricColor))
        append(Utils.transformBodyText(PadreNuestro.texto, rubricColor))
    }
}

fun oratio(data: Oratio, userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_PRAYER, 2, userData, rubricColor))
        append(Utils.transformBodyText(data.oratio, rubricColor))
    }
}

fun conclusionisTitle(userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitle(
                text = Constants.TITLE_CONCLUSION,
                level = 2,
                userData = userData,
                rubricColor = rubricColor
            )
        )
    }
}

fun conclusionisMinor(userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(conclusionisTitle(userData, rubricColor))
        append(
            textVR(
                texts = listOf(
                    RitusConclusionis.txtBenedicamusDomino,
                    RitusConclusionis.txtDeoGratias
                ),
                rubricColor = rubricColor
            )
        )
    }
}

fun conclusionisMaior(userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(conclusionisTitle(userData, rubricColor))
        append(
            textVR(
                texts = listOf(RitusConclusionis.txtDominusNosBenedicat, RitusConclusionis.txtAmen),
                rubricColor = rubricColor
            )
        )
    }
}

fun completoriumKyrie(data: Kyrie, userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_SOUL_SEARCHING, 2, userData, rubricColor))
        return buildAnnotatedString {
            append(LS)
            append(textRubric(data.primumRubrica, rubricColor))
            append(LS2)
            append(data.introductio)
            append(LS2)
            append(textRubric(data.secundoRubrica, rubricColor))
            append(LS2)
            append(transformText(data.kyrie, rubricColor))
            append(LS2)
            append(textRubric(data.tertiaRubrica, rubricColor))
            append(LS2)
            append(textRubric(data.quartaRubrica, rubricColor))
            append(LS2)
            append(textVR(data.conclusio, rubricColor))
        }
    }
}

fun conclusionisCompletorium(
    userData: UserDataDynamic,
    rubricColor: Color,
    data: LHCompletorium
): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_CONCLUSION, 2, userData, rubricColor))
        append(textVR(texts = data.conclusio.benedictio, rubricColor = rubricColor))
        append(contentTitle(Constants.TITLE_VIRGIN_ANTIHPON, 2, userData, rubricColor))
        append(stringFromHtml(text = data.conclusio.antiphon.antiphon))
    }
}

fun finisPsalmus(): AnnotatedString {
    return textSpaced(LiturgyHelper.finisPsalmus)
}