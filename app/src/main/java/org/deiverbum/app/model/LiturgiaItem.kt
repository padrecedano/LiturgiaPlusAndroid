package org.deiverbum.app.model

import androidx.compose.ui.graphics.vector.ImageVector


data class LiturgiaItem(
    val id: Int,
    val title: String,
    //@DrawableRes val imageId: Int,
    val icon: ImageVector
)