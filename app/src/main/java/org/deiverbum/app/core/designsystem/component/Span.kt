package org.deiverbum.app.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle

@Composable
fun spanRubric(rubricColor: Color): SpanStyle {
    return SpanStyle(
        color = rubricColor,
        //fontSize = NiaTypography.titleMedium.fontSize,
    )
}
