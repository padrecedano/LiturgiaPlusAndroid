package org.deiverbum.app.feature.universalis

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UserDataDynamic

@Composable
fun UniversalisScreenView(
    data: Universalis,
    topicId: String?,
    userData: UserDataDynamic,
    modifier: Modifier
) {
    Universalis(data, topicId = topicId, userData)
}

@Composable
fun Universalis(data: Universalis, topicId: String?, userData: UserDataDynamic) {
    val itemId = topicId!!.toInt()
    when (itemId) {
        1 -> MixtusScreen(
            data = data.liturgia!!.liturgiaTypus as LHMixtus,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        2 -> OfficiumScreen(
            data = data.liturgia!!.liturgiaTypus as LHOfficium,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        3 -> LaudesScreen(
            data = data.liturgia!!.liturgiaTypus as LHLaudes,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        4, 5, 6 -> IntermediaScreen(
            data = data.liturgia!!.liturgiaTypus as LHIntermedia,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        7 -> VesperasScreen(
            data = data.liturgia!!.liturgiaTypus as LHVesperas,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        8 -> CompletoriumScreen(
            data = data.liturgia!!.liturgiaTypus as LHCompletorium,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )

        11 -> MissaeLectionumScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData
        )
    }
}

@Composable
fun ContentTitle(text: String, level: Int, rubricColor: Color) {
    val textStyle: TextStyle = when (level) {
        1 -> NiaTypography.titleLarge
        2 -> NiaTypography.titleMedium
        3 -> NiaTypography.titleSmall
        else -> NiaTypography.bodyLarge
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = text.uppercase(), style = textStyle, color = rubricColor)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SectionTitle(text: String, level: Int) {
    val textStyle: TextStyle = when (level) {
        1 -> NiaTypography.titleLarge
        2 -> NiaTypography.titleMedium
        3 -> NiaTypography.titleSmall
        else -> NiaTypography.bodyLarge
    }
    //Spacer(modifier = Modifier.height(16.dp))

    Text(text = text.lowercase(), style = textStyle)
    //Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SpaceSmall() {
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SpaceNormal() {
    Spacer(modifier = Modifier.height(24.dp))
}