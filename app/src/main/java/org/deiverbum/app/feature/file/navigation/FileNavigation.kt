package org.deiverbum.app.feature.file.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.file.FileScreen

@Serializable
data class FileRoute(
    val fileTitle: String? = null,
    val fileName: String? = null,
)

fun NavController.navigateToFile(
    fileName: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = FileRoute(fileName), navOptions)
}

@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.fileScreen(
    onFileClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable<FileRoute> {
        val file = route
        FileScreen(
            onFileClick = onFileClick,
            fileName = route
        )
        //FileRequest(fileName = listOf(route!!),1,1,true,true,true))
    }
}


