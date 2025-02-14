package org.deiverbum.app.feature.settings.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.settings.SettingsScreen

@Serializable
data class SettingsRoute(
    val fileTitle: String? = null,
    val fileName: String? = null,
)

fun NavController.navigateToSettings(
    fileName: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = SettingsRoute(fileName), navOptions)
}

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.settingsScreen(
    //onFileClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable<SettingsRoute> {
        SettingsScreen(onBackClick = onBackClick)
    }
}


