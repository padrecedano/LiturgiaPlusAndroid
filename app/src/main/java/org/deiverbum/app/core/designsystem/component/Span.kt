package org.deiverbum.app.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle

@Composable
fun spanRubric(): SpanStyle {
    return SpanStyle(
        color = MaterialTheme.colorScheme.error,
    )
}
