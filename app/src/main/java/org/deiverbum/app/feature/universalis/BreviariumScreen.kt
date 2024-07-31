package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.core.designsystem.component.TextFromHtml
import org.deiverbum.app.core.designsystem.component.TextMultiColor
import org.deiverbum.app.core.designsystem.component.TextRubric
import org.deiverbum.app.core.designsystem.component.TextSanctus
import org.deiverbum.app.core.designsystem.component.TextSpaced
import org.deiverbum.app.core.designsystem.component.TextVR
import org.deiverbum.app.core.designsystem.component.getRubricColor
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
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            TextSanctus(text = data.sanctus!!.vitaBrevis)
        }
        IntroitusMaior(rubricColor = rubricColor)
        Invitatorium(data.invitatorium, -1, calendarTime, userData)
        SpaceSmall()
        Hymnus(data = data.hymnus, rubricColor = rubricColor)
        SpaceSmall()
        Psalmodia(data.psalmodia, -1, calendarTime, userData)
        SpaceNormal()
        LectioBrevis(data = data.lectioBrevis, hourId = hourId, rubricColor = rubricColor)
        SpaceNormal()
        OfficiumLectionis(officiumLectionis = data.officiumLectionis, rubricColor = rubricColor)

        MissaeLectionum(lectionum = data.lectionumList, rubricColor = rubricColor)
        SpaceNormal()
        CanticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            i = 0,
            calendarTime = calendarTime,
            rubricColor = rubricColor
        )
        SpaceSmall()
        Preces(preces = data.preces, rubricColor = rubricColor)
        SpaceSmall()
        PaterNoster(rubricColor = rubricColor)
        SpaceNormal()
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceNormal()
        RitusConclusionisMaior(rubricColor = rubricColor)
    }

}

@Composable
fun OfficiumScreen(
    data: LHOfficium,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        data.officiumLectionis.normalizeByTime(calendarTime)
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            TextSanctus(text = data.sanctus!!.vitaBrevis)
        }
        IntroitusMaior(rubricColor = rubricColor)
        Invitatorium(
            invitatorium = data.invitatorium,
            i = -1,
            calendarTime = calendarTime,
            userData = userData
        )
        SpaceSmall()
        Hymnus(data = data.hymnus, rubricColor)
        SpaceSmall()
        Psalmodia(data.psalmodia, -1, calendarTime, userData)
        OfficiumLectionis(data.officiumLectionis, rubricColor)
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceSmall()
        RitusConclusionisMaior(rubricColor = rubricColor)
    }
}

@Composable
fun LaudesScreen(
    data: LHLaudes,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        if (data.sanctus != null && data.hasSaint) {
            data.invitatorium.normalizeIsSaint(data.sanctus!!.nomen)
            TextSanctus(text = data.sanctus!!.vitaBrevis)
        }
        IntroitusMaior(rubricColor = rubricColor)
        Invitatorium(data.invitatorium, -1, calendarTime, userData)
        SpaceSmall()
        Hymnus(data = data.hymnus, rubricColor = rubricColor)
        SpaceSmall()
        Psalmodia(data.psalmodia, -1, calendarTime, userData)
        SpaceNormal()
        LectioBrevis(data = data.lectioBrevis, hourId = hourId, rubricColor = rubricColor)
        SpaceNormal()
        CanticumEvangelicum(
            psalmodia = data.canticumEvangelicum,
            i = 0,
            calendarTime = calendarTime,
            rubricColor = rubricColor
        )
        SpaceSmall()
        Preces(preces = data.preces, rubricColor = rubricColor)
        SpaceSmall()
        PaterNoster(rubricColor = rubricColor)
        SpaceNormal()
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceNormal()
        RitusConclusionisMaior(rubricColor = rubricColor)
    }
}

@Composable
fun IntermediaScreen(
    data: LHIntermedia,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        IntroitusMinor(rubricColor = rubricColor)
        SpaceSmall()
        Hymnus(data.hymnus, rubricColor)
        SpaceSmall()
        Psalmodia(data.psalmodia, data.hourIndex, calendarTime, userData)
        SpaceNormal()
        LectioBrevis(data = data.lectioBrevis, hourId = hourId, rubricColor = rubricColor)
        SpaceNormal()
        Oratio(data = data.oratio, rubricColor = rubricColor)
        //SpaceNormal()
        SpaceSmall()
        RitusConclusionisMinor(rubricColor = rubricColor)
    }
}


@Composable
fun VesperasScreen(
    data: LHVesperas,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    IntroitusMaior(rubricColor = rubricColor)
    SpaceSmall()
    Hymnus(data = data.hymnus, rubricColor = rubricColor)
    SpaceSmall()
    Psalmodia(data.psalmodia, -1, calendarTime, userData)
    SpaceNormal()
    LectioBrevis(data = data.lectioBrevis, hourId = hourId, rubricColor = rubricColor)
    SpaceNormal()
    CanticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        i = 0,
        calendarTime = calendarTime,
        rubricColor = rubricColor
    )
    SpaceSmall()
    Preces(preces = data.preces, rubricColor = rubricColor)
    SpaceSmall()
    PaterNoster(rubricColor = rubricColor)
    SpaceNormal()
    Oratio(data = data.oratio, rubricColor = rubricColor)
    SpaceNormal()
    RitusConclusionisMaior(rubricColor = rubricColor)
}

@Composable
fun CompletoriumScreen(
    data: LHCompletorium,
    userData: UserDataDynamic,
    calendarTime: Int,
    hourId: Int
) {
    data.lectioBrevis.normalizeByTime(calendarTime)
    val rubricColor = getRubricColor(userData = userData)
    IntroitusMinor(rubricColor = rubricColor)
    SpaceSmall()
    CompletoriumKyrie(data = data.kyrie, rubricColor = rubricColor)
    SpaceNormal()

    Hymnus(data = data.hymnus, rubricColor = rubricColor)
    SpaceSmall()
    Psalmodia(data.psalmodia, -1, calendarTime, userData)
    SpaceNormal()
    LectioBrevis(data = data.lectioBrevis, hourId = hourId, rubricColor = rubricColor)
    SpaceNormal()
    CanticumEvangelicum(
        psalmodia = data.canticumEvangelicum,
        i = 0,
        calendarTime = calendarTime,
        rubricColor = rubricColor
    )
    SpaceSmall()
    PaterNoster(rubricColor = rubricColor)
    SpaceNormal()
    Oratio(data = data.oratio, rubricColor = rubricColor)
    SpaceNormal()
    RitusConclusionisCompletorium(data = data, rubricColor = rubricColor)

}

@Composable
fun RitusConclusionisCompletorium(rubricColor: Color, data: LHCompletorium) {
    ContentTitle(
        text = Constants.TITLE_CONCLUSION,
        level = 2,
        rubricColor = rubricColor
    )
    SpaceNormal()
    TextVR(texts = data.conclusio.benedictio, rubricColor)
    SpaceNormal()
    ContentTitle(
        text = Constants.TITLE_VIRGIN_ANTIHPON,
        level = 2,
        rubricColor = rubricColor
    )
    //SpaceNormal()

    //Text(text=data.conclusio.getComposeVirginAntiphona())
    TextFromHtml(text = data.conclusio.antiphon.antiphon)
}


@Composable
fun CompletoriumKyrie(data: Kyrie, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_SOUL_SEARCHING,
        level = 2,
        rubricColor = rubricColor
    )
    Text(text = data.getIntroduccionComposable(rubricColor))
}


@Composable
fun IntroitusMaior(rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_INITIAL_INVOCATION,
        level = 2,
        rubricColor = rubricColor
    )
    TextVR(texts = listOf(Introitus().txtDomineLabia, Introitus().txtEtOsMeum), rubricColor)
    SpaceNormal()
    FinisPsalmus()
}

@Composable
fun IntroitusMinor(rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_INITIAL_INVOCATION,
        level = 2,
        rubricColor = rubricColor
    )
    TextVR(
        texts = listOf(Introitus().txtDeusInAdiutorium, Introitus().txtDomineAdAdiuvandum),
        rubricColor
    )
    SpaceNormal()
    FinisPsalmus()
}

@Composable
fun FinisPsalmus() {
    TextSpaced(texts = LiturgyHelper.finisPsalmus)
}


@Composable
fun Invitatorium(invitatorium: LHInvitatory, i: Int, calendarTime: Int, userData: UserDataDynamic) {
    Text(text = invitatorium.getComposable(i, calendarTime, userData))
}

@Composable
fun Hymnus(data: LHHymn, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_HYMN,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(text = data.hymnus)
}

@Composable
fun Psalmodia(psalmodia: LHPsalmody, i: Int, calendarTime: Int, userData: UserDataDynamic) {
    Text(text = psalmodia.getComposable(i, calendarTime, userData))
}

@Composable
fun OfficiumLectionis(officiumLectionis: LHOfficiumLectionis, rubricColor: Color) {
    SectionTitle(
        text = Constants.TITLE_OFFICE_OF_READING,
        level = 1,
    )
    LectioPrior(officiumLectionis.lectioPrior, rubricColor)
    SpaceSmall()
    LectioAltera(officiumLectionis.lectioAltera, rubricColor)
    SpaceSmall()
}


@Composable
fun LectioPrior(lectioPrior: MutableList<LHOfficiumLectioPrior>, rubricColor: Color) {
    for (item in lectioPrior) {
        Column {
            ContentTitle(
                text = "PRIMERA LECTURA",
                level = 2,
                rubricColor = rubricColor
            )
            TextMultiColor(listOf(item.book.liturgyName, item.pericopa), rubricColor)
            SpaceSmall()
            TextRubric(item.tema, rubricColor)
            SpaceSmall()
            TextFromHtml(item.biblica)
            Text(
                item.responsorium.getComposable(rubricColor = rubricColor),
                //modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
fun LectioAltera(lectioAltera: MutableList<LHOfficiumLectioAltera>, rubricColor: Color) {
    for (item in lectioAltera) {
        Column {
            ContentTitle(
                text = "SEGUNDA LECTURA",
                level = 2,
                rubricColor = rubricColor
            )
            Text(item.paterOpus?.opusForView!!)
            SpaceSmall()
            TextRubric(item.theSource!!, rubricColor)
            SpaceSmall()
            TextRubric(item.tema!!, rubricColor)
            SpaceSmall()
            TextFromHtml(item.homilia)
            Text(item.responsorium!!.getComposable(rubricColor = rubricColor))
        }
    }
}

@Composable
fun LectioBrevis(data: LHLectioBrevis, hourId: Int, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_SHORT_READING,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(text = data.biblica)
    SpaceNormal()
    ResponsoriumBrevis(data = data.responsorium, hourId = hourId, rubricColor = rubricColor)
}

@Composable
fun ResponsoriumBrevisTitle(typus: Int, rubricColor: Color) {
    if (typus > 0) {
        ContentTitle(
            text = Constants.TITLE_RESPONSORY_SHORT,
            level = 2,
            rubricColor = rubricColor
        )
    } else {
        TextRubric("En lugar del responsorio breve, se dice la siguiente antífona:", rubricColor)
    }
}

@Composable
fun ResponsoriumBrevis(data: LHResponsoriumBrevis, hourId: Int, rubricColor: Color) {
    ResponsoriumBrevisTitle(typus = data.typus, rubricColor = rubricColor)
    Text(text = data.getComposable(rubricColor))
}


@Composable
fun MissaeLectionum(lectionum: MissaeLectionumList, rubricColor: Color) {
    for (item in lectionum.lectionum) {
        Column {
            ContentTitle(
                text = "EVANGELIO",
                level = 2,
                rubricColor = rubricColor
            )
            TextMultiColor(listOf(item!!.book.liturgyName, item.pericopa), rubricColor)
            SpaceSmall()
            TextRubric(item.tema, rubricColor)
            SpaceSmall()
            TextFromHtml(item.biblica)
            /*Text(
                item.responsorium.getComposable(rubricColor = rubricColor),
                modifier = Modifier.padding(0.dp)
            )*/
        }
    }
}

@Composable
fun CanticumEvangelicum(psalmodia: LHPsalmody, i: Int, calendarTime: Int, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_GOSPEL_CANTICLE,
        level = 2,
        rubricColor = rubricColor
    )
    Text(text = psalmodia.getComposableByIndex(i, calendarTime, rubricColor))
}

@Composable
fun Preces(preces: LHIntercession, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_INTERCESSIONS,
        level = 2,
        rubricColor = rubricColor
    )
    val introArray =
        preces.intro.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    Text(
        buildAnnotatedString {
            if (introArray.size == 3) {
                append(introArray[0])
                append(Utils.LS2)
                append(Utils.fromHtml(String.format(Locale("es"), "<i>%s</i>", introArray[1])))
                append(Utils.LS2)
                append(Utils.fromHtml(preces.preces))
                append(introArray[2])
                //append(Utils.LS2)
            } else {
                append(preces.intro)
                append(Utils.LS2)
                append(Utils.fromHtml(preces.preces))
            }
        }
    )
}

@Composable
fun PaterNoster(rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_PATER_NOSTER,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(PadreNuestro.texto)
}

@Composable
fun Oratio(data: Oratio, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_PRAYER,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(data.oratio)
}

@Composable
fun RitusConclusionisTitle(rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_CONCLUSION,
        level = 2,
        rubricColor = rubricColor
    )
}

@Composable
fun RitusConclusionisMinor(rubricColor: Color) {
    RitusConclusionisTitle(rubricColor)
    TextVR(
        texts = listOf(RitusConclusionis.txtBenedicamusDomino, RitusConclusionis.txtDeoGratias),
        rubricColor = rubricColor
    )
}

@Composable
fun RitusConclusionisMaior(rubricColor: Color) {
    RitusConclusionisTitle(rubricColor)
    TextVR(
        texts = listOf(RitusConclusionis.txtDominusNosBenedicat, RitusConclusionis.txtAmen),
        rubricColor = rubricColor
    )
}

