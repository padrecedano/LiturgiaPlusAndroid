package org.deiverbum.app.feature.universalis.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.universalis.UniversalisFromHomeScreen
import org.deiverbum.app.util.DateTimeUtil

@Serializable
data class UniversalisRoute(
    var initialTopicId: String? = null,
    var initialDate: Int = DateTimeUtil.getTodayDate(),//Utils.hoy.toInt(),
)

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.universalisFromHome(onBackClick: () -> Unit) {
    composable<UniversalisRoute> {
        //TtsScreen()
        //TtsScreenWithBottomSheetControls()
        UniversalisFromHomeScreen(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToUniversalis(
    topicId: String,
    //currentDate:Int,

    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = UniversalisRoute(topicId)) { navOptions() }
}

fun NavController.navigateToUniversalisFromCalendar(
    topicId: String,
    currentDate: Int,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    //navigate(route = CalendarRoute(topicId,currentDate)) { navOptions() }
    navigate(route = UniversalisRoute(topicId, currentDate)) { navOptions() }

}
