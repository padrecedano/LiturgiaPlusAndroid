package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.deiverbum.app.core.designsystem.component.normalStyle
import org.deiverbum.app.core.designsystem.component.spanRubric
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Utils

@Composable
fun ContentHeadd(
    text: String,
    level: Int,
    userData: UserData,
    uppercase: Boolean = true,
    withColor: Boolean = false
) {
    val finalText = if (uppercase) text.uppercase() else text
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)

    val textStyle: TextStyle = when (level) {
        1 -> typography.headlineLarge
        2 -> typography.headlineMedium
        3 -> typography.headlineSmall
        else -> typography.headlineSmall
    }

    val annotatedString = buildAnnotatedString {
        if (withColor) {
            withStyle(spanRubric()) {
                append(finalText)
            }
        } else {
            append(finalText)
        }
    }
    Text(text = annotatedString, style = textStyle, fontWeight = FontWeight.SemiBold)
}

@Composable
fun ContentLabel(
    data: String,
    level: Int,
    userData: UserData,
    uppercase: Boolean = true,
    withColor: Boolean = false

) {
    val finalText = if (uppercase) data.uppercase() else data
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)

    val textStyle: TextStyle = when (level) {
        1 -> typography.labelLarge
        2 -> typography.labelMedium
        3 -> typography.labelSmall
        else -> typography.labelSmall
    }
    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        4 -> typography.bodyLarge.fontSize
        else -> typography.bodyLarge.fontSize
    }
    val text = buildAnnotatedString {
        if (withColor) {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.error,
                    fontSize = fontSize,
                )
            ) {
                append(finalText)
            }
        } else {
            append(finalText)
        }
    }
    Text(text = text, style = textStyle)
}

@Composable
fun ContentTitle(
    data: String,
    level: Int,
    userData: UserData,
    uppercase: Boolean = true,
    withExtraSpace: Boolean = true

) {
    val finalText = if (uppercase) data.uppercase() else data
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val style: TextStyle = when (level) {
        1 -> typography.titleLarge
        2 -> typography.titleMedium
        3 -> typography.titleSmall
        4 -> typography.bodyLarge
        else -> typography.bodyLarge
    }
    val text = buildAnnotatedString {
        withStyle(spanRubric())
        {
            append(finalText)
        }
    }
    when (withExtraSpace) {
        true -> Text(
            text = text,
            style = style,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        false -> Text(text = text, style = style)

    }
}

@Composable
fun ContentTitleAndText(
    texts: List<String>,
    level: Int,
    userData: UserData,
    uppercase: Boolean = true,
    style: TextStyle
) {
    val title = if (uppercase) texts[0].uppercase() else texts[0]
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val titleStyle: TextStyle = when (level) {
        1 -> typography.titleLarge
        2 -> typography.titleMedium
        3 -> typography.titleSmall
        4 -> typography.bodyLarge
        else -> typography.bodyLarge
    }
    Row {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 8.dp),
            text = title,
            style = titleStyle,
            color = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = texts[1],
            color = MaterialTheme.colorScheme.error,
            style = style
        )
    }
}

@Composable
fun contentTitle(
    data: String,
    level: Int,
    userData: UserData,
    //rubricColor: Color,
    uppercase: Boolean = true
): AnnotatedString {
    val finalText = if (uppercase) data.uppercase() else data
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        4 -> typography.bodyLarge.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = fontSize,
                color = MaterialTheme.colorScheme.error
            )
        ) {
            append(finalText)
        }
    }
}

fun sectionTitle(
    text: String,
    level: Int,
    userData: UserData,
    lower: Boolean = true
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)

    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))

        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            if (lower) {
                append(text.lowercase())
            } else {
                append(text)
            }
        }
    }
}

@Composable
fun SectionTitlee(
    data: String,
    level: Int,
    userData: UserData,
    lower: Boolean = true
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)

    val textStyle: TextStyle = when (level) {
        1 -> typography.titleLarge
        2 -> typography.titleMedium
        3 -> typography.titleSmall
        else -> typography.titleSmall
    }
    val text = buildAnnotatedString {

        if (lower) {
            append(data.lowercase())
        } else {
            append(data)
        }
    }
    Text(
        text = text, style = textStyle, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun errorMessage(msg: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append("¡ERROR! ")
        }
        append("\n\n")
        append(msg)
        append(Utils.toRed(Configuration.MY_GMAIL))
    }
}

fun errorMessageAudio(msg: String): AnnotatedString {
    return buildAnnotatedString {
        append(msg)
        append(Configuration.MY_GMAIL)
        append(".")
    }
}

fun contentSpace(lineHeight: Int): AnnotatedString {
    val paragraphStyle = ParagraphStyle(
        lineHeight = lineHeight.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Top,
            trim = LineHeightStyle.Trim.Both
        )
    )
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
        }
    }
}