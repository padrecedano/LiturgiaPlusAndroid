package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Utils

@Composable
fun ContentHeadd(
    text: String,
    level: Int,
    userData: UserData,
    fontSize: TextUnit,
    rubricColor: Color,
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
    val paragraphStyle = ParagraphStyle(
        lineHeight = textStyle.lineHeight,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    val text = buildAnnotatedString {
        if (withColor) {
            withStyle(SpanStyle(color = rubricColor)) {
                append(finalText)
            }
        } else {
            append(finalText)

        }


        //append(finalText)
    }


    Text(text = text, style = textStyle)
}

fun contentHead(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    fontSize: TextUnit,
    rubricColor: Color,
    uppercase: Boolean = true,
    withColor: Boolean = false

): AnnotatedString {
    val finalText = if (uppercase) text.uppercase() else text
    val typography = getPersonalizedTypography(userData.fontSize)
    val fontSize: TextUnit = when (level) {
        1 -> typography.headlineLarge.fontSize
        2 -> typography.headlineMedium.fontSize
        3 -> typography.headlineSmall.fontSize
        else -> typography.headlineSmall.fontSize
    }
    val textStyle: TextStyle = when (level) {
        1 -> typography.headlineLarge
        2 -> typography.headlineMedium
        3 -> typography.headlineSmall
        else -> typography.headlineSmall
    }
    val paragraphStyle = ParagraphStyle(
        lineHeight = textStyle.lineHeight,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    return buildAnnotatedString {
        //pushStyle(normalStyle(fontSize = fontSize))
        //pushStyle(paragraphStyle)
        withStyle(style = paragraphStyle) {

            withStyle(
                if (withColor) {
                    SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = fontSize,
                        color = rubricColor
                    )
                } else {
                    SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = fontSize
                    )
                }
            ) {
                append(finalText)
            }
        }
    }
}

@Composable
fun ContentLabell(
    resource: UniversalisResource,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    uppercase: Boolean = true,
    withColor: Boolean = false

) {
    //val text=resource.data.
    val date = Utils.formatDate(
        resource.date.toString(),
        "yyyyMMdd",
        "EEEE d 'de' MMMM 'de' yyyy"
    )
    val finalText = if (uppercase) date.uppercase() else date
    val typography = getPersonalizedTypography(userData.fontSize)
    val fontSize: TextUnit = when (level) {
        1 -> typography.labelLarge.fontSize
        2 -> typography.labelMedium.fontSize
        3 -> typography.labelSmall.fontSize
        else -> typography.labelSmall.fontSize
    }
    val textStyle: TextStyle = when (level) {
        1 -> typography.headlineLarge
        2 -> typography.headlineMedium
        3 -> typography.headlineSmall
        else -> typography.headlineSmall
    }
    val paragraphStyle = ParagraphStyle(
        //lineHeight = textStyle.lineHeight,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Top,
            trim = LineHeightStyle.Trim.None
        )
    )
    Row(
        modifier = Modifier.padding(1.dp),
        // causes narrow chips
        //modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column {
            /*ContentLabell(
                data = date,
                level = 1,
                userData = userData,
                rubricColor = rubricColor,
                uppercase = false,
                withColor = false
            )
            Spacer(modifier = Modifier.width(1.dp))

            ContentHeadd(
                    resource.metaData.tempus,
                    3,
                    userData,
                    fontSize,
                    rubricColor,
                    false
                )

            SectionTitlee(resource.metaData.nomen, 2, resource.dynamic.dynamic, false)
            ContentHeadd(LiturgyHelper.titulusMap[resource.id].toString() ,1,userData,fontSize,rubricColor,false,true)
*/

        }
    }
    //Text(text = text)

    //Text(text=text,style=textStyle)
    //Text(text=text,style=textStyle)


}

fun contentLabel(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    uppercase: Boolean = true,
    withColor: Boolean = false

): AnnotatedString {
    val finalText = if (uppercase) text.uppercase() else text
    val typography = getPersonalizedTypography(userData.fontSize)
    val fontSize: TextUnit = when (level) {
        1 -> typography.labelLarge.fontSize
        2 -> typography.labelMedium.fontSize
        3 -> typography.labelSmall.fontSize
        else -> typography.labelSmall.fontSize
    }
    val textStyle: TextStyle = when (level) {
        1 -> typography.headlineLarge
        2 -> typography.headlineMedium
        3 -> typography.headlineSmall
        else -> typography.headlineSmall
    }
    return buildAnnotatedString {
        //pushStyle(defaultStyle(textStyle = textStyle))
        withStyle(
            if (withColor) {
                SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            } else {
                SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize
                )
            }
        ) {
            append(finalText)
        }
    }
}

@Composable
fun ContentLabell(
    data: String,
    level: Int,
    userData: UserData,
    rubricColor: Color,
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
    userData: UserDataDynamic,
    uppercase: Boolean = true
) {
    val finalText = if (uppercase) data.uppercase() else data
    val typography = getPersonalizedTypography(userData.fontSize)
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
    Text(text = text, style = style, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
}

@Composable
fun ContentTitleAndText(
    texts: List<String>,
    level: Int,
    userData: UserDataDynamic,
    uppercase: Boolean = true,
    style: TextStyle
) {
    val title = if (uppercase) texts[0].uppercase() else texts[0]
    val typography = getPersonalizedTypography(userData.fontSize)
    val titleStyle: TextStyle = when (level) {
        1 -> typography.titleLarge
        2 -> typography.titleMedium
        3 -> typography.titleSmall
        4 -> typography.bodyLarge
        else -> typography.bodyLarge
    }

    //Text(text = text, style = style,modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
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


fun contentTitle(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    uppercase: Boolean = true
): AnnotatedString {
    val finalText = if (uppercase) text.uppercase() else text
    val typography = getPersonalizedTypography(userData.fontSize)
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
                color = rubricColor
            )
        ) {
            append(finalText)
        }
    }
}

fun contentTitleForHomily(text: String, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(
        lineHeight = 18.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )

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

        withStyle(style = paragraphStyle) {
        }
    }
}

fun contentTitleAndText(
    texts: List<String>,
    level: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)

    val paragraphStyle = ParagraphStyle(
        //lineHeight = 25.sp,
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    val fontSize: TextUnit = when (level) {
        1 -> typography.titleLarge.fontSize
        2 -> typography.titleMedium.fontSize
        3 -> typography.titleSmall.fontSize
        else -> typography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))

        withStyle(style = paragraphStyle) {
        }
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    //fontSize = fontSize,
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
                append("   ")
                append(texts[1])
            }
        }
        withStyle(style = paragraphStyle) {
        }
    }
}

fun sectionTitle(
    text: String,
    level: Int,
    userData: UserDataDynamic,
    lower: Boolean = true
): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)

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
            //append("\n")

            //}
            //}
            //withStyle(style = paragraphStyle) {
            //}
        }
    }
}

@Composable
fun SectionTitlee(
    data: String,
    level: Int,
    userData: UserDataDynamic,
    lower: Boolean = true
) {
    val typography = getPersonalizedTypography(userData.fontSize)

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


