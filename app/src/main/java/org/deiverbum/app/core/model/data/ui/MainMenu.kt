package org.deiverbum.app.core.model.data.ui

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Representa los items del menú.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
data class MenuItem(
    var title: String = "",
    var icon: ImageVector,
    var group: Int
)