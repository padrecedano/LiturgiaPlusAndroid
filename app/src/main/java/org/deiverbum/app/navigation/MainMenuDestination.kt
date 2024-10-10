package org.deiverbum.app.navigation

import LPlusIcons
import androidx.compose.ui.graphics.vector.ImageVector
import org.deiverbum.app.R
import org.deiverbum.app.feature.menu.navigation.MenuRoute
import kotlin.reflect.KClass

/**
 * Tipo para los destinos de nivel superior en la aplicación. Cada uno de estos destinos
 * puede contener una o más pantallas (según el tamaño de la ventana).
 * La navegación de una pantalla a la siguiente dentro de un único destino
 * se gestionará directamente en los elementos componibles.
 */
enum class MainMenuDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: KClass<*>,

    ) {

    BUG_REPORT(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = MenuRoute::class,
    ),
    BUG_REPORTT(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = MenuRoute::class,
    ),

}

