package org.deiverbum.app.feature.bugreport.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.bugreport.BugScreen

@Serializable
data class BugRoute(
    val fileTitle: String? = null,
    val fileName: String? = null,
)

fun NavController.navigateToBug(
    fileName: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = BugRoute(fileName), navOptions)
}

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.bugReportScreen(
    //onFileClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable<BugRoute> {
        BugScreen()
    }
}


