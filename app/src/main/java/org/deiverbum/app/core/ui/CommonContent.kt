package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deiverbum.app.core.designsystem.theme.NiaTypography

@Composable
fun ContentTitle(text: String, level: Int, rubricColor: Color) {
    val textStyle: TextStyle = when (level) {
        1 -> NiaTypography.titleLarge
        2 -> NiaTypography.titleMedium
        3 -> NiaTypography.titleSmall
        else -> NiaTypography.bodyLarge
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = text.uppercase(), style = textStyle, color = rubricColor)
    Spacer(modifier = Modifier.height(16.dp))
}

fun contentTitle(text: String, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        4 -> NiaTypography.bodyLarge.fontSize

        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(text.uppercase())
            }
        }
    }
}

fun contentTitleAndText(texts: List<String>, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(texts[0].uppercase())
            }
            withStyle(
                SpanStyle(
                    color = rubricColor
                )
            ) {
                append("\t")
                append(texts[1])
            }
        }
    }
}

fun sectionTitle(text: String, level: Int): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                )
            ) {
                append(text.lowercase())
            }
        }
    }
}

@Composable
fun SpaceNormal() {
    Spacer(modifier = Modifier.height(24.dp))
}