package org.deiverbum.app.core.ui

import androidx.annotation.OptIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.AnnotatedString
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.AlteriRosarium
import org.deiverbum.app.core.model.data.AlteriSanctii
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.Sortable
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UserData


//@ExperimentalStdlibApi
@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun UniversalisBody(
    data: Universalis,
    topicId: Int,
    userData: UserData,
) {
    val onTap = { _: Offset -> }
    //val rubricColor = LocalCustomColorsPalette.current.rubricColor
    val rubricColor = MaterialTheme.colorScheme.error

    if (data.liturgia!!.liturgiaTypus is Sortable) {
        data.liturgia!!.liturgiaTypus?.sort()
    }
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val fontSize = typography.bodyLarge.fontSize
    data.liturgia!!.liturgiaTypus?.normalizeByTime(data.timeFK)
    when (topicId) {
        1 -> {
            MixtusScreen(
                data = data.liturgia!!.liturgiaTypus as LHMixtus,
                calendarTime = data.timeFK,
                userData = userData,
                rubricColor = rubricColor,
                onTap = onTap,
                fontSize = fontSize
            )
        }

        2 -> OfficiumScreen(
            data = data.liturgia!!.liturgiaTypus as LHOfficium,
            calendarTime = data.timeFK,
            userData = userData,
            rubricColor = rubricColor,
            onTap = onTap,
            fontSize = fontSize
        )

        3 -> LaudesScreen(
            data = data.liturgia!!.liturgiaTypus as LHLaudes,
            calendarTime = data.timeFK,
            userData = userData,
            rubricColor = rubricColor,
            onTap = onTap,
            fontSize = fontSize
        )

        4, 5, 6 -> IntermediaScreen(
            data = data.liturgia!!.liturgiaTypus as LHIntermedia,
            calendarTime = data.timeFK,
            userData = userData,
            rubricColor = rubricColor,
            onTap = onTap,
            fontSize = fontSize
        )

        7 -> VesperasScreen(
            data = data.liturgia!!.liturgiaTypus as LHVesperas,
            calendarTime = data.timeFK,
            userData = userData,
            rubricColor = rubricColor,
            onTap = onTap,
            fontSize = fontSize
        )

        8 -> CompletoriumScreen(
            data = data.liturgia!!.liturgiaTypus as LHCompletorium,
            calendarTime = data.timeFK,
            userData = userData,
            rubricColor = rubricColor,
            onTap = onTap,
            fontSize = fontSize
        )

        11 -> MissaeLectionumScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            userData = userData,
            onTap = onTap,
            fontSize = fontSize
        )

        12 -> CommentariiScreen(
            data = data.liturgia!!.liturgiaTypus as Commentarii,
            userData = userData,
            onTap = onTap,
            fontSize = fontSize
        )

        13 -> HomiliaeScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            userData = userData,
            onTap = onTap,
            fontSize = fontSize
        )

        20 -> SanctiiScreen(
            data = data.liturgia!!.liturgiaTypus as AlteriSanctii,
            userData = userData,
            onTap = onTap,
            fontSize = fontSize
        )

        30 -> RosariumScreen(
            data = data.liturgia!!.liturgiaTypus as AlteriRosarium,
            userData = userData,
            onTap = onTap,
            fontSize = fontSize
        )
    }
}

fun universalisBodyForRead(
    data: Universalis,
    topicId: Int,
    userData: UserData,
): AnnotatedString {
    var text = AnnotatedString("")
    when (val typus = data.liturgia!!.liturgiaTypus) {
        is LHOfficium -> {
            text += officiumAudio(typus, userData, 7)
        }

        is LHLaudes -> {
            text += audioLaudes(typus, userData, 7)
        }

        is LHIntermedia -> {
            text += audioIntermedia(typus, userData, 7)
        }

        is LHVesperas -> {
            text += audioVesperas(typus, userData, 7)
        }
    }
    return text
}




