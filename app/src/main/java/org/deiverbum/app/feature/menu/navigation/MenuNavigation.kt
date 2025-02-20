package org.deiverbum.app.feature.menu.navigation
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.feature.bugreport.navigation.BugRoute
import org.deiverbum.app.feature.file.navigation.FileRoute
import org.deiverbum.app.feature.settings.navigation.SettingsRoute
import org.deiverbum.app.util.FileNameUtil

/**
 * Controlador de navegación del menú principal. Los destinos de navegación se deciden
 * de acuerdo al item que haya sido seleccionado en el menú.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */
fun NavController.navigateToMenu(
    menuItem: String? = null,
    navOptions: NavOptions? = null,
) {
    val fileName = FileNameUtil.fileMap[menuItem]
    val analyticsHelper: AnalyticsHelper

    if (fileName != null) {
        navigate(route = FileRoute(fileTitle = menuItem, fileName = fileName), navOptions)
    } else {
        when (menuItem) {
            "Reportar Error" -> navigate(route = BugRoute(menuItem), navOptions)
            "Ajustes" -> {
                navigate(route = SettingsRoute(menuItem), navOptions)
            }
        }
    }
}