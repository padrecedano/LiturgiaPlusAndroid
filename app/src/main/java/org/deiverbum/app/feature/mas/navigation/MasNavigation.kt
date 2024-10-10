package org.deiverbum.app.feature.mas.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.core.model.FileRequest

@Serializable
data class MasRoute(
    val initialTopicId: String? = null,
)

fun NavController.navigateToMas(
    initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = MasRoute(initialTopicId), navOptions)
}

@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.masScreen(
    onTopicClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable<MasRoute> {
        val fileRequest = remember {
            FileRequest(
                listOf("raw/oratio/letanias.json", "raw/oratio/rosario.json"), 1, 6, false,
                isVoiceOn = true,
                isBrevis = true
            )
        }
        val fr = FileRequest(
            listOf("raw/oratio/letanias.json"), 1, 6, false,
            isVoiceOn = true,
            isBrevis = true
        )
        //MasScreen(onTopicClick = onTopicClick, fileRequest = fileRequest)
    }
}


