package org.deiverbum.app.core.designsystem.component

import android.text.Spanned
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun ParagraphStyleSmall(text: Spanned) {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 20.sp)) {
                append(text)
            }
        }
    )
}