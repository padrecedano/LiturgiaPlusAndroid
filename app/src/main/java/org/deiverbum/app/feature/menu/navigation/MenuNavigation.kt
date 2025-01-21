@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app.feature.menu.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.bugreport.navigation.BugRoute
import org.deiverbum.app.feature.file.navigation.FileRoute
import org.deiverbum.app.util.FileNameUtil

@Serializable
data class MenuRoute(
    val menuItem: String? = "-1",
)

//@Composable
fun NavController.navigateToMenu(
    menuItem: String? = null,
    navOptions: NavOptions? = null,
) {
    val fileName = FileNameUtil.fileMap[menuItem]

    if (fileName != null) {
        navigate(route = FileRoute(fileTitle = menuItem, fileName = fileName), navOptions)
    } else {
        when (menuItem) {
            "Reportar Error" -> navigate(route = BugRoute(menuItem), navOptions)
        }
    }
}

@ExperimentalLayoutApi
@ExperimentalCoroutinesApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.menuScreen(
    onTopicClick: (String) -> Unit
) {
    composable<MenuRoute> {
        MenuScreen(onTopicClick = onTopicClick)
    }
}

@Composable
fun MenuScreen(onTopicClick: (String) -> Unit) {
    val t = onTopicClick::toString
    //val s=onTopicClick.invoke()
    Text(t.name)
}


