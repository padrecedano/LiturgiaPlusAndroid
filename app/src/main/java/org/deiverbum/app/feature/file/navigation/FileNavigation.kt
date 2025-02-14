package org.deiverbum.app.feature.file.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.file.FileScreen

/**
 * Ruta para archivos locales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */
@Serializable
data class FileRoute(
    val fileTitle: String? = null,
    val fileName: String? = null,
)

/**
 * Navegador para archivos locales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */
@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.fileScreen(
    onBackClick: () -> Unit,
) {
    composable<FileRoute> {
        FileScreen(
            onBackClick = onBackClick,
        )
    }
}


