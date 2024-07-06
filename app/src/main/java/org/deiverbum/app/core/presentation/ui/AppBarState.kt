package org.deiverbum.app.core.presentation.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppBarState(
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationIcon: (@Composable RowScope.() -> Unit)? = null,

    //val canNavigateBack: Boolean = true,

)