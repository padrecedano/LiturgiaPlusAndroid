package org.deiverbum.app.core.ui

import androidx.annotation.OptIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.core.model.data.Alteri
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
import org.deiverbum.app.core.model.data.UserDataDynamic

@ExperimentalStdlibApi
@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun UniversalisBody(
    data: Universalis,
    topicId: Int,
    userData: UserDataDynamic,
) {
    val onTap = { _: Offset -> }
    if (data.liturgia!!.liturgiaTypus is Sortable) {
        data.liturgia!!.liturgiaTypus?.sort()
    }
    when (topicId) {
        1 -> MixtusScreen(
            data = data.liturgia!!.liturgiaTypus as LHMixtus,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        2 -> OfficiumScreen(
            data = data.liturgia!!.liturgiaTypus as LHOfficium,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        3 -> LaudesScreen(
            data = data.liturgia!!.liturgiaTypus as LHLaudes,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        4, 5, 6 -> IntermediaScreen(
            data = data.liturgia!!.liturgiaTypus as LHIntermedia,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        7 -> VesperasScreen(
            data = data.liturgia!!.liturgiaTypus as LHVesperas,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        8 -> CompletoriumScreen(
            data = data.liturgia!!.liturgiaTypus as LHCompletorium,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        11 -> MissaeLectionumScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            userData = userData,
            onTap = onTap
        )

        12 -> CommentariiScreen(
            data = data.liturgia!!.liturgiaTypus as Commentarii,
            userData = userData,
            onTap = onTap
        )

        13 -> HomiliaeScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            userData = userData,
            onTap = onTap
        )

        20 -> SanctiiScreen(
            data = data.liturgia!!.liturgiaTypus as Alteri.Sancti,
            userData = userData,
            onTap = onTap
        )
    }
}





