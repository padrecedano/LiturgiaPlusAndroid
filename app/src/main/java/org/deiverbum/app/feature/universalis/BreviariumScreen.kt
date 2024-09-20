package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
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
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LHResponsoriumBrevis
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.Oratio
import org.deiverbum.app.core.model.data.PadreNuestro
import org.deiverbum.app.core.model.data.RitusConclusionis
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.ui.contentTitle
import org.deiverbum.app.core.ui.contentTitleAndText
import org.deiverbum.app.core.ui.sectionTitle
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import java.util.Locale

/**
 * Pantallas para  [LHMixtus]
 *
 * @since 2024.1
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
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData))
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
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData))
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
        asb.append(invitatorium(data.invitatorium, -1, calendarTime, userData))
        asb.append(hymnus(data.hymnus, rubricColor))
        asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData))
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
        asb.append(psalmodia(data.psalmodia, data.hourIndex, calendarTime, userData))
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
    asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData))
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
    asb.append(psalmodia(data.psalmodia, -1, calendarTime, userData))
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

fun invitatorium(
    psalmodia: LHInvitatory,
    i: Int,
    calendarTime: Int,
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        append(sectionTitle(Constants.TITLE_INVITATORY, 1))
        append(psalmodia.getComposable(i, calendarTime, userData))
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
    userData: UserDataDynamic
): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitle(Constants.TITLE_PSALMODY, 2, userData.rubricColor.value))
        append(psalmodia.getComposable(i, calendarTime, userData))
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
        asb.append(stringFromHtml(item.biblica))
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
        asb.append(stringFromHtml(item.homilia))
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
            append(stringFromHtml(item.biblica))
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