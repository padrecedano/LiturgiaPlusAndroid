package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.stringFromHtml
import org.deiverbum.app.core.designsystem.component.textDefault
import org.deiverbum.app.core.designsystem.component.textDefaultItalic
import org.deiverbum.app.core.designsystem.component.textFromHtml
import org.deiverbum.app.core.designsystem.component.textLines
import org.deiverbum.app.core.designsystem.component.textMultiColor
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textSmall
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textSpan
import org.deiverbum.app.core.designsystem.component.textVR
import org.deiverbum.app.core.designsystem.component.textWithR
import org.deiverbum.app.core.designsystem.component.textWithV
import org.deiverbum.app.core.designsystem.component.transformBodyText
import org.deiverbum.app.core.designsystem.component.transformEpigraph
import org.deiverbum.app.core.designsystem.component.transformText
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
import org.deiverbum.app.core.model.data.LHResponsorium
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
import org.deiverbum.app.util.splitParts

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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {

    var text = AnnotatedString("")


    Column {
        //data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusPrima(userData = userData, fontSize = fontSize, rubricColor = rubricColor)
        text += invitatorium(
            data.invitatorium, calendarTime, userData, rubricColor,
            fontSize = fontSize
        )
        text += hymnus(data.hymnus, userData, rubricColor, fontSize)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, fontSize, rubricColor)
        text += officiumLectionis(data.officiumLectionis, userData.dynamic, rubricColor, fontSize)
        text += missaeLectionum(
            lectionum = data.lectionumList,
            userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            calendarTime = calendarTime,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += preces(
            preces = data.preces,
            userData = userData.dynamic,
            fontSize = fontSize,
            rubricColor = rubricColor
        )
        text += paterNoster(userData.dynamic, fontSize = fontSize, rubricColor = rubricColor)
        text += oratio(
            data = data.oratio,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += conclusionisMaior(
            userData = userData.dynamic,
            fontSize = fontSize,
            rubricColor = rubricColor
        )
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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    var text = AnnotatedString("")

    Column {
        //data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusPrima(userData = userData, fontSize = fontSize, rubricColor = rubricColor)
        text += invitatorium(
            data.invitatorium, calendarTime, userData, rubricColor,
            fontSize = fontSize
        )
        text += hymnus(data.hymnus, userData, rubricColor, fontSize)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
        text += officiumLectionis(
            officiumLectionis = data.officiumLectionis,
            userData = userData.dynamic,
            fontSize = fontSize,
            rubricColor = rubricColor
        )
        text += oratio(
            data = data.oratio,
            fontSize = fontSize,
            rubricColor = rubricColor,
            userData = userData.dynamic
        )
        text += conclusionisMaior(
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    var text = AnnotatedString("")

    Column {
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            text += textSmall(data.sanctus!!.vitaBrevis, userData.dynamic)
        }
        text += introitusPrima(userData = userData, fontSize = fontSize, rubricColor = rubricColor)
        text += invitatorium(
            data.invitatorium, calendarTime, userData, rubricColor,
            fontSize = fontSize
        )
        text += hymnus(
            data = data.hymnus,
            userData = userData,
            fontSize = fontSize,
            rubricColor = rubricColor
        )
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, fontSize, rubricColor)
        text += canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            calendarTime = calendarTime,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += preces(
            preces = data.preces,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += paterNoster(userData.dynamic, fontSize = fontSize, rubricColor = rubricColor)
        text += oratio(
            data = data.oratio,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += conclusionisMaior(
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    var text = AnnotatedString("")
    Column {
        text += introitusAltera(
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += hymnus(data.hymnus, userData, rubricColor, fontSize)
        text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
        text += lectioBrevis(data.lectioBrevis, userData.dynamic, fontSize, rubricColor)
        text += oratio(
            data = data.oratio,
            userData = userData.dynamic,
            rubricColor = rubricColor,
            fontSize = fontSize
        )
        text += conclusionisMinor(
            userData = userData.dynamic,
            fontSize = fontSize,
            rubricColor = rubricColor
        )
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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    var text = AnnotatedString("")
    text += introitusAltera(
        userData = userData.dynamic,
        fontSize = fontSize,
        rubricColor = rubricColor
    )
    text += hymnus(data.hymnus, userData, rubricColor, fontSize)
    //data.psalmodia.psalmus[0].psalmus=data.psalmodia.psalmus[0].psalmus+"∸"

    text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
    text += lectioBrevis(data.lectioBrevis, userData.dynamic, fontSize, rubricColor)
    text += canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
    text += preces(
        preces = data.preces,
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
    text += paterNoster(userData.dynamic, fontSize = fontSize, rubricColor = rubricColor)
    text += oratio(
        data = data.oratio,
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
    text += conclusionisMaior(
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
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
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    //data.lectioBrevis.normalizeByTime(calendarTime)
    var text = AnnotatedString("")
    text += introitusAltera(
        userData = userData.dynamic,
        fontSize = fontSize,
        rubricColor = rubricColor
    )
    text += completoriumKyrie(
        data = data.kyrie,
        userData = userData.dynamic,
        fontSize = fontSize,
        rubricColor = rubricColor
    )
    text += hymnus(data.hymnus, userData, rubricColor, fontSize)
    text += psalmodia(data.psalmodia, -1, calendarTime, userData.dynamic, fontSize, rubricColor)
    text += lectioBrevis(data.lectioBrevis, userData.dynamic, fontSize, rubricColor)
    text += canticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        calendarTime = calendarTime,
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
    text += paterNoster(userData.dynamic, fontSize = fontSize, rubricColor = rubricColor)
    text += oratio(
        data = data.oratio,
        userData = userData.dynamic,
        rubricColor = rubricColor,
        fontSize = fontSize
    )
    text += conclusionisCompletorium(
        data = data,
        userData = userData.dynamic,
        fontSize = fontSize,
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

fun introitusPrima(userData: UserData, fontSize: TextUnit, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(1, fontSize))
        append(contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, userData.dynamic, rubricColor))
        append(textLines(2, fontSize))
        append(textVR(texts = Introitus().prima, fontSize = fontSize, rubricColor = rubricColor))
        append(textLines(1, fontSize))
        append(finisPsalmus(fontSize, rubricColor))
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
fun introitusAltera(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitle(
                text = Constants.TITLE_INITIAL_INVOCATION,
                level = 2,
                userData = userData,
                rubricColor = rubricColor
            )
        )
        append(textLines(2, fontSize))
        append(textVR(texts = Introitus().altera, rubricColor, fontSize))
        append(textLines(1, fontSize))
        append(finisPsalmus(fontSize, rubricColor, true))
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
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(1, fontSize))
        append(sectionTitle(Constants.TITLE_INVITATORY, 1, userData.dynamic))
        append(textLines(2, fontSize))
        append(
            psalmodiaUnica(
                psalmodia, calendarTime, userData.dynamic, rubricColor,
                fontSize
            )
        )
        append(textLines(2, fontSize))
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
fun hymnus(
    data: LHHymn,
    userData: UserData,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        //append(textLines(2,fontSize))
        append(contentTitle(Constants.TITLE_HYMN, 2, userData.dynamic, rubricColor))
        append(textLines(2, fontSize))
        append(transformBodyText(data.hymnus, rubricColor, fontSize))
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
    fontSize: TextUnit,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")

    try {
        val antiphonaeAnte = AnnotatedString.Builder()
        val antiphonaePost = AnnotatedString.Builder()
        text += textLines(2, fontSize)
        text += contentTitle(Constants.TITLE_PSALMODY, 2, userData, rubricColor)
        text += textLines(2, fontSize)

        if (psalmodia.typus == 1) {
            if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                //psalmodia.antiphonae[indexHora].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnte(
                        psalmodia.antiphonae[indexHora],
                        rubricColor, fontSize,
                        false
                    )
                )
            } else {
                //psalmodia.antiphonae[0].normalizeByTime(calendarTime)
                antiphonaeAnte.append(
                    antiphonaeAnte(
                        psalmodia.antiphonae[0],
                        rubricColor, fontSize,
                        true
                    )
                )
                antiphonaePost.append(
                    antiphonaePost(
                        psalmodia.antiphonae[0],
                        fontSize,
                        rubricColor
                    )
                )
            }
            text += antiphonaeAnte.toAnnotatedString()
            text += textLines(fontSize = fontSize)
            for (s in psalmodia.psalmus) {
                text += psalmus(s, userData, rubricColor, fontSize)
            }
            text += antiphonaePost(psalmodia.antiphonae[indexHora], fontSize, rubricColor)
        }

        if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
            val lastIndex = psalmodia.psalmus.lastIndex
            psalmodia.psalmus.forEachIndexed { index, s ->
                //psalmodia.antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                text += antiphonaeAnte(
                    psalmodia.antiphonae[s.theOrder - 1],
                    rubricColor,
                    fontSize,
                    true
                )
                text += textLines(lines = 2, fontSize = fontSize)

                text += psalmus(s, userData, rubricColor, fontSize)
                //text += textLines(fontSize = fontSize)

                text += antiphonaePost(psalmodia.antiphonae[s.theOrder - 1], fontSize, rubricColor)
                if (index != lastIndex) {
                    text += textLines(lines = 3, fontSize = fontSize)
                }
            }
        }
        return text
    } catch (e: Exception) {
        return AnnotatedString(Utils.createErrorMessage(e.message))
    }
}

/**
 * Crea el texto de la Antífona con el tamaño por defecto.
 *
 * @param text La antífona
 * @param fontSize El tamaño de la fuente, según los ajustes del usuario
 *
 * @return Un objeto [AnnotatedString] con el texto de la antífona.
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 * @see [LHAntiphon]
 */
fun antiphonaTextus(
    text: String,
    fontSize: TextUnit
): AnnotatedString {
    return textDefault(text, fontSize)
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
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    //psalmodia.antiphonae[0].normalizeByTime(calendarTime)
    return antiphonaeEtPsalmus(
        psalmodia.antiphonae[0],
        false,
        psalmodia.psalmus[0],
        calendarTime,
        userData, rubricColor, fontSize
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
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    //antiphonae.normalizeByTime(calendarTime)
    return buildAnnotatedString {
        append(antiphonaeAnte(antiphonae, rubricColor, fontSize, withOrder))
        append(textLines(2, fontSize))
        append(psalmus(psalmus, userData, rubricColor, fontSize))
        //append(textLines(1,fontSize))
        append(antiphonaePost(antiphonae, fontSize, rubricColor))
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
    fontSize: TextUnit,
    withOrder: Boolean = true
): AnnotatedString {
    return buildAnnotatedString {
        if (withOrder) {
            append(textRubric("Ant. ${antiphonae.theOrder}. ", rubricColor, fontSize))
        } else {
            append(textRubric("Ant. ", rubricColor, fontSize))
        }
        if (antiphonae.haveSymbol) {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
            append(textRubric(" † ", rubricColor, fontSize))
        } else {
            append(antiphonaTextus(antiphonae.antiphon, fontSize))
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
    fontSize: TextUnit,
    rubricColor: Color,
): AnnotatedString {
    return buildAnnotatedString {
        append(textRubric("Ant. ", rubricColor, fontSize))
        append(antiphonaTextus(antiphonae.antiphon, fontSize))
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
    fontSize: TextUnit,
): AnnotatedString {
    var text = AnnotatedString("")
    //val normalizeText = psalmus.normalize()
    if (psalmus.pericopa != "") {
        text += textRubric(psalmus.pericopa, rubricColor, fontSize)
        text += textLines(2, fontSize)
    }
    if ((psalmus.theme != null) && (psalmus.theme != "")) {
        text += textRubric(psalmus.theme!!, rubricColor, fontSize)
        text += textLines(2, fontSize)
    }
    if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
        text += transformEpigraph(psalmus.epigraph!!, userData)
        text += textLines(2, fontSize)
    }
    if ((psalmus.thePart != null) && (psalmus.thePart != 0)) {
        text += textRubric(toRoman(psalmus.thePart), rubricColor, fontSize)
        text += textLines(2, fontSize)
    }
    text += transformText(text = psalmus.psalmus, fontSize = fontSize, color = rubricColor)
    text += textLines(1, fontSize)
    text += finisPsalmus(fontSize, rubricColor, psalmus.haveGloria)
    return text
}

fun lectioBrevis(
    data: LHLectioBrevis,
    userData: UserDataDynamic,
    fontSize: TextUnit,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(1, fontSize))
        append(
            contentTitleAndText(
                listOf(Constants.TITLE_SHORT_READING, data.pericopa),
                2,
                userData,
                rubricColor
            )
        )
        //append(Utils.transformBodyText(data.biblica, rubricColor, fontSize))
        append(textLines(1, fontSize))
        append(textDefault(data.biblica, fontSize))
        append(textLines(2, fontSize))
        append(
            responsoriumBrevis(
                data = data.responsorium,
                userData = userData,
                fontSize = fontSize,
                rubricColor = rubricColor
            )
        )
    }
}

fun responsoriumBrevis(
    data: LHResponsoriumBrevis,
    userData: UserDataDynamic,
    fontSize: TextUnit,

    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        responsoriumBrevisTitle(
            typus = data.typus,
            userData = userData,
            fontSize,
            rubricColor = rubricColor
        )
        append(responsoriumBrevisText(data, fontSize, rubricColor))
    }
}

fun responsoriumText(
    data: LHResponsorium,
    fontSize: TextUnit,
    rubricColor: Color
): AnnotatedString {
    val respArray =
        data.responsorium.splitParts("\\|")
    return buildAnnotatedString {
        when (data.typus) {
            0 -> {
                append(textLines(2, fontSize))
                append(textDefault(data.responsorium, fontSize))
                append(textLines(1, fontSize))
            }

            1 -> if (respArray.size == 3) {
                append(
                    textWithR(
                        text = respArray[0],
                        fontSize = fontSize,
                        rubricColor = rubricColor
                    )
                )
                append(textRubric(" * ", rubricColor, fontSize))
                append(textDefault(respArray[1], fontSize))
                append(textLines(2, fontSize))
                append(
                    textWithV(
                        text = respArray[2],
                        fontSize = fontSize,
                        rubricColor = rubricColor
                    )
                )
                append(textLines(2, fontSize))
                append(textRubric(LiturgyHelper.R, rubricColor, fontSize))
                append(textDefault(respArray[1][0].uppercaseChar().toString(), fontSize))
                append(textDefault(respArray[1].substring(1), fontSize))
            }

            2 -> {
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textRubric(" * ", rubricColor, fontSize))
                append(textDefault(respArray[1], fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textRubric(LiturgyHelper.R, rubricColor, fontSize))
                append(textDefault(respArray[1][0].uppercaseChar().toString(), fontSize))
                append(textDefault(respArray[1].substring(1), fontSize))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textWithV(respArray[0], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[0], rubricColor, fontSize))
                    append(textLines(2, fontSize))
                    append(textWithV(respArray[1], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[2], rubricColor, fontSize))
                    append(textLines(2, fontSize))
                    append(textWithV(respArray[3], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[0], rubricColor, fontSize))
                }

            6001020 -> if (respArray.size == 3) {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[1], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
            }

            4 -> {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[1], rubricColor, fontSize))
                append(textLines(1, fontSize))

                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(1, fontSize))

                append(textWithR(respArray[0], rubricColor, fontSize))
            }

            201 -> {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[1], rubricColor, fontSize))
            }

            else -> {
                append(LS2)
                append(errorMessage(ErrorHelper.RESPONSORIUM, rubricColor))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(data.typus.toString())
                append(textLines(1, fontSize))

            }
        }
        append("   ")
        append(textRubric(text = data.source, fontSize = fontSize, rubricColor = rubricColor))
    }
}

fun responsoriumBrevisText(
    data: LHResponsoriumBrevis,
    fontSize: TextUnit,
    rubricColor: Color
): AnnotatedString {
    val respArray = data.responsorium.splitParts("\\|")
    return buildAnnotatedString {
        when (data.typus) {
            0 -> {
                append(textLines(2, fontSize))
                append(textDefault(data.responsorium, fontSize))
                append(textLines(1, fontSize))
            }

            1 -> if (respArray.size == 3) {
                append(
                    textWithR(
                        text = respArray[0],
                        fontSize = fontSize,
                        rubricColor = rubricColor
                    )
                )
                append(textRubric(" * ", rubricColor, fontSize))
                append(textDefault(respArray[1], fontSize))
                append(textLines(2, fontSize))
                append(
                    textWithV(
                        text = respArray[2],
                        fontSize = fontSize,
                        rubricColor = rubricColor
                    )
                )
                append(textLines(2, fontSize))
                append(textRubric(LiturgyHelper.R, rubricColor, fontSize))
                append(textDefault(respArray[1][0].uppercaseChar().toString(), fontSize))
                append(textDefault(respArray[1].substring(1), fontSize))
            }

            2 -> {
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textRubric(" * ", rubricColor, fontSize))
                append(textDefault(respArray[1], fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textRubric(LiturgyHelper.R, rubricColor, fontSize))
                append(textDefault(respArray[1][0].uppercaseChar().toString(), fontSize))
                append(textDefault(respArray[1].substring(1), fontSize))
            }

            6001230 ->
                if (respArray.size == 4) {
                    append(textWithV(respArray[0], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[0], rubricColor, fontSize))
                    append(textLines(2, fontSize))
                    append(textWithV(respArray[1], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[2], rubricColor, fontSize))
                    append(textLines(2, fontSize))
                    append(textWithV(respArray[3], rubricColor, fontSize))
                    append(textLines(1, fontSize))
                    append(textWithR(respArray[0], rubricColor, fontSize))
                }

            6001020 -> if (respArray.size == 3) {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[1], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))
                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
            }

            4 -> {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))

                append(textWithV(respArray[1], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
                append(textLines(2, fontSize))

                append(textWithV(respArray[2], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[0], rubricColor, fontSize))
            }

            201 -> {
                append(textWithV(respArray[0], rubricColor, fontSize))
                append(textLines(1, fontSize))
                append(textWithR(respArray[1], rubricColor, fontSize))
            }

            else -> {
                append(LS2)
                append(errorMessage(ErrorHelper.RESPONSORIUM, rubricColor))
                append("Tamaño del responsorio: ")
                append(respArray.size.toString())
                append(" Código forma: ")
                append(data.typus.toString())
                append(textLines(1, fontSize))

            }
        }
    }
}

fun responsoriumBrevisTitle(
    typus: Int,
    userData: UserDataDynamic,
    fontSize: TextUnit,

    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        if (typus > 0) {
            append(contentTitle(Constants.TITLE_RESPONSORY_SHORT, 2, userData, rubricColor))
        } else {
            textRubric(
                Constants.NOT_RESPONSORY,
                rubricColor,
                fontSize
            )
        }
    }
}

fun officiumLectionis(
    officiumLectionis: LHOfficiumLectionis,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    var text = AnnotatedString("")
    val list = officiumLectionis.responsorium.splitParts("\\|").toList()
    text += textLines(2, fontSize)

    text += sectionTitle(Constants.TITLE_OFFICE_OF_READING, 1, userData)
    text += textLines(2, fontSize)
    text += textVR(list, rubricColor, fontSize)
    //text += textLines(2,fontSize)

    text += lectioPrior(officiumLectionis.lectioPrior, userData, fontSize, rubricColor)
    text += lectioAltera(officiumLectionis.lectioAltera, userData, fontSize = fontSize, rubricColor)
    return text
}

fun lectioPrior(
    lectioPrior: MutableList<LHOfficiumLectioPrior>,
    userData: UserDataDynamic,
    fontSize: TextUnit,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    for (item in lectioPrior) {
        text += textLines(2, fontSize)
        text += contentTitle("PRIMERA LECTURA", 2, userData, rubricColor)
        text += textLines(2, fontSize)
        text += textMultiColor(listOf(item.book.liturgyName, item.pericopa), fontSize, rubricColor)
        text += textLines(2, fontSize)
        text += textRubric(item.tema, rubricColor, fontSize)
        text += textLines(2, fontSize)
        text += textFromHtml(item.biblica, fontSize)
        //text += AnnotatedString.fromHtml(item.biblica)
        //text += textLines(2,fontSize)
        text += textRubric("Responsorio ${item.responsorium.source}", rubricColor, fontSize)
        text += textLines(2, fontSize)
        text += responsoriumBrevisText(item.responsorium, fontSize, rubricColor)
    }
    return text
}

fun lectioAltera(
    lectioAltera: MutableList<LHOfficiumLectioAltera>,
    userData: UserDataDynamic,
    fontSize: TextUnit,

    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    for (item in lectioAltera) {
        text += textLines(2, fontSize)
        text += contentTitle("SEGUNDA LECTURA", 2, userData, rubricColor)
        text += textLines(2, fontSize)
        text += textSpan(text = item.paterOpus?.opusForView!!, fontSize = fontSize)
        text += textLines(2, fontSize)
        text += textRubric(item.theSource!!, rubricColor, fontSize)
        text += textLines(2, fontSize)
        text += textRubric(item.tema!!, rubricColor, fontSize)
        text += textLines(2, fontSize)
        text += textFromHtml(item.homilia, fontSize)
        //text += textLines(2,fontSize)
        text += textRubric("Responsorio ${item.responsorium!!.source}", rubricColor, fontSize)
        text += textLines(2, fontSize)
        text += responsoriumBrevisText(item.responsorium!!, fontSize, rubricColor)
    }
    return text
}

fun canticumEvangelicum(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))

        append(
            contentTitle(
                Constants.TITLE_GOSPEL_CANTICLE,
                2,
                userData,
                rubricColor = rubricColor
            )
        )
        append(textLines(2, fontSize))

        append(psalmodiaUnica(psalmodia, calendarTime, userData, rubricColor, fontSize))
    }
}

fun missaeLectionum(
    lectionum: MissaeLectionumList,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        for (item in lectionum.lectionum) {
            append(contentTitle("EVANGELIO", 2, userData, rubricColor))
            append(textLines(2, fontSize))

            append(
                textMultiColor(
                    listOf(item!!.book.liturgyName, item.pericopa),
                    fontSize,
                    rubricColor
                )
            )
            append(textLines(2, fontSize))
            append(textRubric(item.tema, rubricColor, fontSize))
            //append(textLines(2,fontSize))
            append(lectioSimplex(item, -1, userData, rubricColor, fontSize))
        }
    }
}

fun preces(
    preces: LHIntercession,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    val introArray = preces.intro.splitParts("\\|")
    val listPreces = preces.preces.splitParts("∞")
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        append(contentTitle(Constants.TITLE_INTERCESSIONS, 2, userData, rubricColor))
        append(textLines(2, fontSize))
        if (listPreces.isNotEmpty() && introArray.size == 3) {
            val response = introArray[1]
            val firstPart = listPreces[0].splitParts("§")
            append(textDefault(introArray[0], fontSize))
            append(textLines(2, fontSize))
            append(textDefaultItalic(response, fontSize))
            firstPart.forEach {
                append(textLines(2, fontSize))
                append(transformBodyText(it, fontSize = fontSize, rubricColor = rubricColor))
                append(textLines(2, fontSize))
                append(textDefaultItalic(response, fontSize))
            }
            if (listPreces.size == 2) {
                append(textLines(2, fontSize))
                append(
                    transformBodyText(
                        listPreces[1],
                        fontSize = fontSize,
                        rubricColor = rubricColor
                    )
                )
                append(textLines(2, fontSize))
                append(textDefaultItalic(response, fontSize))
            }
            append(textLines(2, fontSize))
            append(textDefault(introArray[2], fontSize))
        } else {
            append(textDefault(preces.intro, fontSize))
            append(textLines(2, fontSize))
            append(
                transformBodyText(
                    text = preces.preces,
                    fontSize = fontSize,
                    rubricColor = rubricColor
                )
            )
        }
    }
}

fun paterNoster(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        append(contentTitle(Constants.TITLE_PATER_NOSTER, 2, userData, rubricColor))
        append(textLines(2, fontSize))
        append(
            transformBodyText(
                text = PadreNuestro.texto,
                fontSize = fontSize,
                rubricColor = rubricColor
            )
        )
    }
}

fun oratio(
    data: Oratio,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        append(contentTitle(Constants.TITLE_PRAYER, 2, userData, rubricColor))
        append(textLines(2, fontSize))
        append(
            transformBodyText(
                text = data.oratio,
                fontSize = fontSize,
                rubricColor = rubricColor
            )
        )
    }
}

fun conclusionisTitle(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        append(
            contentTitle(
                text = Constants.TITLE_CONCLUSION,
                level = 2,
                userData = userData,
                rubricColor = rubricColor
            )
        )
        append(textLines(2, fontSize))
    }
}

fun conclusionisMinor(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(conclusionisTitle(userData, rubricColor, fontSize))
        append(
            textVR(
                texts = RitusConclusionis().minor,
                rubricColor = rubricColor,
                fontSize = fontSize
            )
        )
    }
}

fun conclusionisMaior(
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(
            conclusionisTitle(
                userData = userData,
                fontSize = fontSize,
                rubricColor = rubricColor
            )
        )
        append(
            textVR(
                texts = RitusConclusionis().maior,
                rubricColor = rubricColor,
                fontSize = fontSize
            )
        )
    }
}

fun completoriumKyrie(
    data: Kyrie,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_SOUL_SEARCHING, 2, userData, rubricColor))
        return buildAnnotatedString {
            append(LS)
            append(textRubric(data.primumRubrica, rubricColor, fontSize))
            append(LS2)
            append(data.introductio)
            append(LS2)
            append(textRubric(data.secundoRubrica, rubricColor, fontSize))
            append(LS2)
            append(transformText(data.kyrie, rubricColor))
            append(LS2)
            append(textRubric(data.tertiaRubrica, rubricColor, fontSize))
            append(LS2)
            append(textRubric(data.quartaRubrica, rubricColor, fontSize))
            append(LS2)
            append(textVR(data.conclusio, rubricColor, fontSize))
        }
    }
}

fun conclusionisCompletorium(
    userData: UserDataDynamic,
    rubricColor: Color,
    data: LHCompletorium,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        append(textLines(2, fontSize))
        append(contentTitle(Constants.TITLE_CONCLUSION, 2, userData, rubricColor))
        append(textLines(2, fontSize))
        append(
            textVR(
                texts = data.conclusio.benedictio,
                rubricColor = rubricColor,
                fontSize = fontSize
            )
        )
        append(textLines(2, fontSize))
        append(contentTitle(Constants.TITLE_VIRGIN_ANTIHPON, 2, userData, rubricColor))
        append(textLines(2, fontSize))
        append(stringFromHtml(text = data.conclusio.antiphon.antiphon))
    }
}

fun finisPsalmus(fontSize: TextUnit, color: Color, haveGloria: Boolean = true): AnnotatedString {
    return if (haveGloria) {
        textSpaced(texts = LiturgyHelper.finisPsalmus, fontSize = fontSize)
    } else {
        buildAnnotatedString {
            append(textLines(1, fontSize))
            append(textRubric(text = gloriaNonDicitur, fontSize = fontSize, rubricColor = color))
            append(textLines(2, fontSize))

        }
    }
}

//@Composable
