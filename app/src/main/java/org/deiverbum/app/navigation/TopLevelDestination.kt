package org.deiverbum.app.navigation

import LPlusIcons
import androidx.compose.ui.graphics.vector.ImageVector
import org.deiverbum.app.R
import org.deiverbum.app.feature.calendar.navigation.CalendarRoute
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.mas.navigation.MasRoute
import kotlin.reflect.KClass

/**
 * Tipo para los destinos de nivel superior en la aplicación. Cada uno de estos destinos
 * puede contener una o más pantallas (según el tamaño de la ventana).
 * La navegación de una pantalla a la siguiente dentro de un único destino
 * se gestionará directamente en los elementos componibles.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: KClass<*>,

    ) {

    HOME(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = HomeRoute::class,
    ),

    CALENDAR(
        selectedIcon = LPlusIcons.Calendar,
        unselectedIcon = LPlusIcons.CalendarBorder,
        iconTextId = R.string.feature_calendar_title,
        titleTextId = R.string.feature_calendar_title,
        route = CalendarRoute::class,

        ),
    MAS(
        selectedIcon = LPlusIcons.Other,
        unselectedIcon = LPlusIcons.OtherBorder,
        iconTextId = R.string.feature_other_title,
        titleTextId = R.string.feature_other_title,
        route = MasRoute::class,
    ),
}

