package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.component.TextFromHtml
import org.deiverbum.app.core.designsystem.component.TextMultiColor
import org.deiverbum.app.core.designsystem.component.TextRubric
import org.deiverbum.app.core.designsystem.component.TextSanctus
import org.deiverbum.app.core.designsystem.component.TextVR
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.model.data.Introitus
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHHymn
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
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Oratio
import org.deiverbum.app.core.model.data.RitusConclusionis
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Constants

@Composable
fun MixtusScreen(
    data: LHMixtus,
    userData: UserDataDynamic,
    calendarTime: Int
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
        OfficiumLectionis(officiumLectionis = data.officiumLectionis, rubricColor = rubricColor)
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceSmall()
        RitusConclusionisMaior(rubricColor = rubricColor)
    }

}

@Composable
fun OfficiumScreen(
    data: LHOfficium,
    userData: UserDataDynamic,
    calendarTime: Int
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
    calendarTime: Int
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
        LectioBrevis(data = data.lectioBrevis, rubricColor = rubricColor)
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceSmall()
        RitusConclusionisMaior(rubricColor = rubricColor)
    }
}

@Composable
fun IntermediaScreen(
    data: LHIntermedia,
    userData: UserDataDynamic,
    calendarTime: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    Column {
        IntroitusMinor(rubricColor = rubricColor)
        SpaceSmall()
        Hymnus(data.hymnus, rubricColor)
        SpaceSmall()
        Psalmodia(data.psalmodia, data.hourIndex, calendarTime, userData)

        LectioBrevis(data = data.lectioBrevis, rubricColor = rubricColor)
        Oratio(data = data.oratio, rubricColor = rubricColor)
        SpaceSmall()
        RitusConclusionisMinor(rubricColor = rubricColor)
    }
}


@Composable
fun VesperasScreen(
    data: LHVesperas,
    userData: UserDataDynamic,
    calendarTime: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    IntroitusMaior(rubricColor = rubricColor)
    SpaceSmall()
    Hymnus(data = data.hymnus, rubricColor = rubricColor)
    SpaceSmall()
    Psalmodia(data.psalmodia, -1, calendarTime, userData)
    LectioBrevis(data = data.lectioBrevis, rubricColor = rubricColor)
    Oratio(data = data.oratio, rubricColor = rubricColor)
    SpaceSmall()
    RitusConclusionisMaior(rubricColor = rubricColor)
}

@Composable
fun CompletoriumScreen(
    data: LHCompletorium,
    userData: UserDataDynamic,
    calendarTime: Int
) {
    val rubricColor = getRubricColor(userData = userData)
    IntroitusMinor(rubricColor = rubricColor)
    SpaceSmall()
    Hymnus(data.hymnus, rubricColor)
    SpaceSmall()
    Psalmodia(data.psalmodia, -1, calendarTime, userData)

    LectioBrevis(data = data.lectioBrevis, rubricColor = rubricColor)
    Oratio(data = data.oratio, rubricColor = rubricColor)
    SpaceSmall()
    RitusConclusionisMinor(rubricColor = rubricColor)
}

@Composable
fun IntroitusMaior(rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_INITIAL_INVOCATION,
        level = 2,
        rubricColor = rubricColor
    )
    TextVR(texts = listOf(Introitus().txtDomineLabia, Introitus().txtEtOsMeum), rubricColor)
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
fun Invitatorium(invitatorium: LHInvitatory, i: Int, calendarTime: Int, userData: UserDataDynamic) {
    Text(text = invitatorium.getComposable(i, calendarTime, userData))
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
                modifier = Modifier.padding(0.dp)
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
fun LectioBrevis(data: LHLectioBrevis, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_SHORT_READING,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(text = data.biblica)
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

@Composable
fun Oratio(data: Oratio, rubricColor: Color) {
    ContentTitle(
        text = Constants.TITLE_PRAYER,
        level = 2,
        rubricColor = rubricColor
    )
    TextFromHtml(data.oratio)
}