package org.deiverbum.app.core.ui

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.stringFromHtml
import org.deiverbum.app.core.designsystem.component.textMultiColor
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textSmall
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textVR
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
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.LiturgyHelper.Companion.gloriaNonDicitur
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.toRoman
import org.deiverbum.app.util.Utils.transformText
import java.util.Locale

/**
 * Pantallas para  [LHMixtus]
 *
 * @since 2025.1
 *
 * @see [Breviarium]
 */

@Composable
fun MixtusScreen(
    data: LHMixtus,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        val asb = AnnotatedString.Builder()
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            asb.append(textSmall(data.sanctus!!.vitaBrevis))
        }
        asb.append(introitusMaior(rubricColor = rubricColor))
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData, rubricColor))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData, rubricColor))
        asb.append(lectioBrevis(data.lectioBrevis, rubricColor))
        asb.append(officiumLectionis(data.officiumLectionis, rubricColor))
        asb.append(missaeLectionum(lectionum = data.lectionumList, rubricColor = rubricColor))

        asb.append(
            canticumEvangelicum(
                psalmodia = data.canticumEvangelicum,
                i = 0,
                calendarTime = calendarTime,
                userData = userData
            )
        )
        asb.append(preces(preces = data.preces, rubricColor = rubricColor))
        asb.append(paterNoster(rubricColor))

        asb.append(oratio(data = data.oratio, rubricColor = rubricColor))
        asb.append(conclusionisMaior(rubricColor = rubricColor))
        TextZoomable(
            onTap = onTap, text = asb.toAnnotatedString()
        )
    }

}

fun SpannableStringBuilder.toAnnotatedString(
    primaryColor: Color,
    rubricColor: Color
): AnnotatedString {
    val builder = AnnotatedString.Builder(this.toString())
    val copierContext = CopierContext(primaryColor, rubricColor)
    SpanCopier.values().forEach { copier ->
        getSpans(0, length, copier.spanClass).forEach { span ->
            copier.copySpan(span, getSpanStart(span), getSpanEnd(span), builder, copierContext)
        }
    }
    return builder.toAnnotatedString()
}

private data class CopierContext(
    val primaryColor: Color,
    val rubricColor: Color
)

private enum class SpanCopier {
    URL {
        override val spanClass = URLSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val urlSpan = span as URLSpan
            destination.addStringAnnotation(
                tag = name,
                annotation = urlSpan.url,
                start = start,
                end = end,
            )
            destination.addStyle(
                style = SpanStyle(
                    color = context.primaryColor,
                    textDecoration = TextDecoration.Underline
                ),
                start = start,
                end = end,
            )
        }
    },
    FOREGROUND_COLOR {
        override val spanClass = ForegroundColorSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val colorSpan = span as ForegroundColorSpan
            destination.addStyle(
                style = SpanStyle(color = context.rubricColor),
                start = start,
                end = end,
            )
        }
    },
    UNDERLINE {
        override val spanClass = UnderlineSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            destination.addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = start,
                end = end,
            )
        }
    },
    STYLE {
        override val spanClass = StyleSpan::class.java
        override fun copySpan(
            span: Any,
            start: Int,
            end: Int,
            destination: AnnotatedString.Builder,
            context: CopierContext
        ) {
            val styleSpan = span as StyleSpan

            destination.addStyle(
                style = when (styleSpan.style) {
                    Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
                    Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
                    Typeface.BOLD_ITALIC -> SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )

                    else -> SpanStyle()
                },
                start = start,
                end = end,
            )
        }
    };

    abstract val spanClass: Class<out CharacterStyle>
    abstract fun copySpan(
        span: Any,
        start: Int,
        end: Int,
        destination: AnnotatedString.Builder,
        context: CopierContext
    )
}

@Composable
fun OfficiumScreen(
    data: LHOfficium,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData)

    Column {
        val asb = AnnotatedString.Builder()
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            asb.append(textSmall(data.sanctus!!.vitaBrevis))
        }
        asb.append(introitusMaior(rubricColor = rubricColor))
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData, rubricColor))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData, rubricColor))
        asb.append(officiumLectionis(data.officiumLectionis, rubricColor))
        asb.append(oratio(data = data.oratio, rubricColor = rubricColor))
        asb.append(conclusionisMaior(rubricColor = rubricColor))
        TextZoomable(
            onTap = onTap, text = asb.toAnnotatedString()
        )
    }
}

@Composable
fun LaudesScreen(
    data: LHLaudes,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {


    val rubricColor = getRubricColor(userData = userData)
    Column {
        val asb = AnnotatedString.Builder()
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            asb.append(textSmall(data.sanctus!!.vitaBrevis))
        }
        asb.append(introitusMaior(rubricColor = rubricColor))
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData, rubricColor))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData, rubricColor))
        asb.append(
            lectioBrevis(
                data = data.lectioBrevis,
                rubricColor = rubricColor
            )
        )
        asb.append(
            canticumEvangelicum(
                psalmodia = data.canticumEvangelicum,
                i = 0,
                calendarTime = calendarTime,
                userData = userData
            )
        )
        asb.append(preces(preces = data.preces, rubricColor = rubricColor))
        asb.append(paterNoster(rubricColor))
        asb.append(oratio(data.oratio, rubricColor))
        asb.append(conclusionisMaior(rubricColor))
        TextZoomable(
            onTap = onTap, text = asb.toAnnotatedString()
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun IntermediaScreen(
    data: LHIntermedia,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {

    val rubricColor = getRubricColor(userData = userData)

    Column {
        val asb = AnnotatedString.Builder()
        asb.append(introitusMinor(rubricColor = rubricColor))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, data.hourIndex, calendarTime, userData, rubricColor))
        asb.append(
            lectioBrevis(
                data = data.lectioBrevis,
                rubricColor = rubricColor
            )
        )
        asb.append(oratio(data.oratio, rubricColor))
        asb.append(conclusionisMinor(rubricColor))
        TextZoomable(
            onTap = onTap, text = asb.toAnnotatedString()
        )
    }
}


@Composable
fun VesperasScreen(
    data: LHVesperas,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {

    val rubricColor = getRubricColor(userData = userData)
    val asb = AnnotatedString.Builder()

    asb.append(introitusMaior(rubricColor = rubricColor))
    asb.append(hymnus(data.hymnus, rubricColor))
    asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData, rubricColor))
    asb.append(
        lectioBrevis(
            data = data.lectioBrevis,
            rubricColor = rubricColor
        )
    )
    asb.append(
        canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            i = 0,
            calendarTime = calendarTime,
            userData = userData
        )
    )
    asb.append(preces(preces = data.preces, rubricColor = rubricColor))
    asb.append(paterNoster(rubricColor))
    asb.append(oratio(data.oratio, rubricColor))
    asb.append(conclusionisMaior(rubricColor))
    TextZoomable(
        onTap = onTap, text = asb.toAnnotatedString()
    )
}

@Composable
fun CompletoriumScreen(
    data: LHCompletorium,
    userData: UserDataDynamic,
    calendarTime: Int,
    onTap: (Offset) -> Unit
) {
    data.lectioBrevis.normalizeByTime(calendarTime)
    val rubricColor = getRubricColor(userData = userData)
    val asb = AnnotatedString.Builder()
    asb.append(introitusMinor(rubricColor = rubricColor))
    asb.append(completoriumKyrie(data = data.kyrie, rubricColor = rubricColor))
    asb.append(hymnus(data.hymnus, rubricColor))
    asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData, rubricColor))
    asb.append(
        lectioBrevis(
            data = data.lectioBrevis,
            rubricColor = rubricColor
        )
    )
    asb.append(
        canticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            i = 0,
            calendarTime = calendarTime,
            userData = userData
        )
    )
    asb.append(paterNoster(rubricColor))
    asb.append(oratio(data.oratio, rubricColor))
    asb.append(conclusionisCompletorium(data = data, rubricColor = rubricColor))
    TextZoomable(
        onTap = onTap, text = asb.toAnnotatedString()
    )
}

fun introitusMaior(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, rubricColor))
        append(
            textVR(
                texts = listOf(Introitus().txtDomineLabia, Introitus().txtEtOsMeum),
                rubricColor
            )
        )
        append(Utils.LS)
        append(textSpaced(LiturgyHelper.finisPsalmus))
    }
}

fun introitusMinor(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, rubricColor))
        append(
            textVR(
                texts = listOf(Introitus().txtDeusInAdiutorium, Introitus().txtDomineAdAdiuvandum),
                rubricColor
            )
        )
        append(Utils.LS)
        append(textSpaced(LiturgyHelper.finisPsalmus))
    }
}

//@Composable
fun invitatorium(
    psalmodia: LHInvitatory,
    i: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        append(sectionTitle(Constants.TITLE_INVITATORY, 1))
        //TODO: Salmodia
        append(psalmodiaInvitatorium(psalmodia, calendarTime, userData, rubricColor))
    }
}

fun hymnus(data: LHHymn, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_HYMN, 2, rubricColor))
        append(Utils.transformBodyText(data.hymnus, rubricColor))
    }
}

fun psalmodia(
    psalmodia: LHPsalmody,
    i: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    return try {
        buildAnnotatedString {

            append(contentTitle(Constants.TITLE_PSALMODY, 2, userData.rubricColor.value))

            //val sb = AnnotatedString.Builder()
            //SpannableStringBuilder(header)
            //sb.append(LS2)
            val antiphonaeAnte = AnnotatedString.Builder()
            val antiphonaePost = AnnotatedString.Builder()

            if (psalmodia.typus == 1) {
                if (psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                    psalmodia.antiphonae[i].normalizeByTime(calendarTime)
                    antiphonaeAnte.append(
                        antiphonaeAnte(
                            psalmodia.antiphonae[i],
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

                    //antiphonBefore.append(psalmodia.antiphonae[0].getComposableBefore(rubricColor = rubricColor))
                    antiphonaePost.append(psalmodia.antiphonae[0].getComposableAfter(rubricColor))
                }
                append(antiphonaeAnte.toAnnotatedString())
                //sb.append(antiphonBefore.toAnnotatedString())
                append(LS2)
                for (s in psalmodia.psalmus) {
                    append(psalmus(s, rubricColor))
                }

                //append(antiphonaePost(psalmodia.antiphonae[i],rubricColor))
                append(antiphonaePost(psalmodia.antiphonae[i], rubricColor))


            }

            if (psalmodia.typus == 0 && psalmodia.psalmus.size == psalmodia.antiphonae.size) {
                val lastIndex = psalmodia.psalmus.lastIndex
                psalmodia.psalmus.forEachIndexed { index, s ->
                    psalmodia.antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                    append(antiphonaeAnte(psalmodia.antiphonae[s.theOrder - 1], rubricColor, true))
                    append(LS2)
                    append(psalmus(s, rubricColor))
                    append(LS2)
                    append(antiphonaePost(psalmodia.antiphonae[s.theOrder - 1], rubricColor))
                    if (index != lastIndex) {
                        append(LS2)
                    }
                }
            }

            //return sb.toAnnotatedString()
        }
    } catch (e: Exception) {
        buildAnnotatedString {
            append(Utils.createErrorMessage(e.message))
        }
    }


    //append(psalmodia.getComposable(i, calendarTime, userData))
}

//@Composable
fun psalmodiaInvitatorium(
    psalmodia: LHPsalmody,
    calendarTime: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val s = psalmodia.psalmus[0]
    psalmodia.antiphonae[0].normalizeByTime(calendarTime)
    if (!userData.useMultipleInvitatory) {
        s.psalmus = LHInvitatory.unicum
        s.pericopa = "Salmo 94"
    }
    return buildAnnotatedString {
        append(antiphonaeAnte(psalmodia.antiphonae[0], rubricColor, false))
        append(Utils.LS2)
        //append(textRubric(s.pericopa, rubricColor))
        //append(LS2)
        append(psalmus(s, rubricColor))
        append(LS2)
        append(antiphonaePost(psalmodia.antiphonae[0], rubricColor))
    }


    //append(psalmodia.getComposable(i, calendarTime, userData))
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

//@Composable
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

//@Composable
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
    rubricColor: Color,
): AnnotatedString {
    return buildAnnotatedString {
        if (psalmus.pericopa != "") {
            append(textRubric(psalmus.pericopa, rubricColor))
            append(LS2)
        }
        if ((psalmus.theme != null) && (psalmus.theme != "")) {
            append(psalmus.themeComposable(rubricColor))
            append(LS2)
        }
        if ((psalmus.epigraph != null) && (psalmus.epigraph != "")) {
            append(psalmus.epigraphComposable())
            append(LS2)
        }
        if ((psalmus.thePart != null) && (psalmus.thePart != 0)) {
            append(textRubric(toRoman(psalmus.thePart), rubricColor))
            append(LS2)
        }
        append(transformText(psalmus.psalmus, rubricColor))
        append(LS2)

        if (psalmus.psalmus.endsWith("∸")) {
            append(gloriaNonDicitur)
        } else {
            append(endPsalmForView)
        }

    }
}


fun lectioBrevis(data: LHLectioBrevis, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitleAndText(
                listOf(Constants.TITLE_SHORT_READING, data.pericopa),
                2,
                rubricColor
            )
        )
        append(Utils.transformBodyText(data.biblica, rubricColor))
        append(Utils.LS2)
        append(
            responsoriumBrevis(
                data = data.responsorium,
                rubricColor = rubricColor
            )
        )
    }
}

fun responsoriumBrevis(
    data: LHResponsoriumBrevis,
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        responsoriumBrevisTitle(typus = data.typus, rubricColor = rubricColor)
        append(data.getComposable(rubricColor))
    }
}

fun responsoriumBrevisTitle(typus: Int, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        if (typus > 0) {
            append(contentTitle(Constants.TITLE_RESPONSORY_SHORT, 2, rubricColor))
        } else {
            textRubric(
                "En lugar del responsorio breve, se dice la siguiente antífona:",
                rubricColor
            )
        }
    }
}

fun officiumLectionis(officiumLectionis: LHOfficiumLectionis, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(sectionTitle(Constants.TITLE_OFFICE_OF_READING, 1))
        append(lectioPrior(officiumLectionis.lectioPrior, rubricColor))
        append(Utils.LS)
        append(lectioAltera(officiumLectionis.lectioAltera, rubricColor))
        append(Utils.LS)
    }
}

fun lectioPrior(
    lectioPrior: MutableList<LHOfficiumLectioPrior>,
    rubricColor: Color
): AnnotatedString {
    val asb = AnnotatedString.Builder()
    for (item in lectioPrior) {
        asb.append(contentTitle("PRIMERA LECTURA", 2, rubricColor))
        asb.append(textMultiColor(listOf(item.book.liturgyName, item.pericopa), rubricColor))
        asb.append(Utils.LS2)
        asb.append(textRubric(item.tema, rubricColor))
        asb.append(Utils.LS2)
        //asb.append(annotatedStringFromHtml(item.biblica))
        asb.append(item.responsorium.getComposable(rubricColor = rubricColor))
    }
    return asb.toAnnotatedString()
}

fun lectioAltera(
    lectioAltera: MutableList<LHOfficiumLectioAltera>,
    rubricColor: Color
): AnnotatedString {
    val asb = AnnotatedString.Builder()
    for (item in lectioAltera) {
        asb.append(contentTitle("SEGUNDA LECTURA", 2, rubricColor))
        asb.append(item.paterOpus?.opusForView!!)
        asb.append(Utils.LS2)
        asb.append(textRubric(item.theSource!!, rubricColor))
        asb.append(Utils.LS2)
        asb.append(textRubric(item.tema!!, rubricColor))
        asb.append(Utils.LS2)
        //asb.append(annotatedStringFromHtml(item.homilia))
        asb.append(item.responsorium!!.getComposable(rubricColor = rubricColor))
    }
    return asb.toAnnotatedString()
}

fun canticumEvangelicum(
    psalmodia: LHPsalmody,
    i: Int,
    calendarTime: Int,
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitle(
                Constants.TITLE_GOSPEL_CANTICLE,
                2,
                rubricColor = userData.rubricColor.value
            )
        )
        append(psalmodia.getComposableByIndex(i, calendarTime, userData.rubricColor.value))
    }
}

fun missaeLectionum(lectionum: MissaeLectionumList, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        for (item in lectionum.lectionum) {
            append(contentTitle("EVANGELIO", 2, rubricColor))
            append(textMultiColor(listOf(item!!.book.liturgyName, item.pericopa), rubricColor))
            append(Utils.LS2)
            append(textRubric(item.tema, rubricColor))
            append(Utils.LS2)
            //append(annotatedStringFromHtml((item.biblica)))
        }
    }
}

fun preces(preces: LHIntercession, rubricColor: Color): AnnotatedString {
    val introArray =
        preces.intro.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_INTERCESSIONS, 2, rubricColor))
        if (introArray.size == 3) {
            append(introArray[0])
            append(Utils.LS2)
            append(Utils.fromHtml(String.format(Locale("es"), "<i>%s</i>", introArray[1])))
            append(Utils.LS2)
            append(Utils.transformBodyText(preces.preces, rubricColor))
            append(introArray[2])
        } else {
            append(preces.intro)
            append(Utils.LS2)
            append(Utils.transformBodyText(preces.preces, rubricColor))
        }
    }
}

fun paterNoster(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_PATER_NOSTER, 2, rubricColor))
        append(Utils.transformBodyText(PadreNuestro.texto, rubricColor))
    }
}

fun oratio(data: Oratio, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_PRAYER, 2, rubricColor))
        append(Utils.transformBodyText(data.oratio, rubricColor))
    }
}

fun conclusionisTitle(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(
            contentTitle(
                text = Constants.TITLE_CONCLUSION,
                level = 2,
                rubricColor = rubricColor
            )
        )
    }
}

fun conclusionisMinor(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(conclusionisTitle(rubricColor))
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

fun conclusionisMaior(rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(conclusionisTitle(rubricColor))
        append(
            textVR(
                texts = listOf(RitusConclusionis.txtDominusNosBenedicat, RitusConclusionis.txtAmen),
                rubricColor = rubricColor
            )
        )
    }
}

fun completoriumKyrie(data: Kyrie, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_SOUL_SEARCHING, 2, rubricColor))
        append(data.getIntroduccionComposable(rubricColor))
    }
}

fun conclusionisCompletorium(rubricColor: Color, data: LHCompletorium): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_CONCLUSION, 2, rubricColor))
        append(textVR(texts = data.conclusio.benedictio, rubricColor = rubricColor))
        append(contentTitle(Constants.TITLE_VIRGIN_ANTIHPON, 2, rubricColor))
        append(stringFromHtml(text = data.conclusio.antiphon.antiphon))
    }
}