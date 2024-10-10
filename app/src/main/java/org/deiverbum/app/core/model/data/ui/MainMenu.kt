package org.deiverbum.app.core.model.data.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    var title: String = "",
    var icon: ImageVector,
    var group: Int
)