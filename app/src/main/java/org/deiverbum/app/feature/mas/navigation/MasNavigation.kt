package org.deiverbum.app.feature.mas.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.file.navigation.FileRoute
import org.deiverbum.app.feature.mas.MoreRoute
import org.deiverbum.app.util.FileNameUtil

@Serializable
data class MasRoute(
    val initialTopicId: String? = null,
)

fun NavController.navigateToMas(
    initialTopicId: String = "",
    navOptions: NavOptions? = null,
) {
    //val fileName = FileNameUtil.fileMap[initialTopicId]
    navigate(route = MasRoute(), navOptions = navOptions)

    //navigate(route = MasRoute(initialTopicId), navOptions)
}

fun NavController.navigateToFile(
    initialTopicId: String = "",
    navOptions: NavOptions? = null,
) {
    val fileName = FileNameUtil.fileMap[initialTopicId]
    navigate(route = FileRoute(initialTopicId, fileName), navOptions = navOptions)

    //navigate(route = MasRoute(initialTopicId), navOptions)
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.masScreen(
    onTopicClick: (String) -> Unit,
    //currentTimeZone: StateFlow<TimeZone>,
    //currentDate: StateFlow<LocalDateTime>,
) {
    composable<MasRoute> {
        MoreRoute(onTopicClick)

    }
    /*
    composable<MasRoute> {
        val fileRequest = remember {
            FileRequestt(
                listOf(FileItem("raw/oratio/letanias.json","a"), FileItem("raw/oratio/rosario.json","b")), 1, 6, false,
                isVoiceOn = true,
                isBrevis = true
            )
        }
        val fr = FileRequest(
            listOf("raw/oratio/letanias.json"), 1, 6, false,
            isVoiceOn = true,
            isBrevis = true
        )
        MasScreen(onTopicClick = onTopicClick, fileRequest = fileRequest)

    }
    */
}


