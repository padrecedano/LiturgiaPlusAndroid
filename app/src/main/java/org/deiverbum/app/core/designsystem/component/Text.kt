package org.deiverbum.app.core.designsystem.component

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.deiverbum.app.core.designsystem.theme.Orange90
import org.deiverbum.app.core.designsystem.theme.Red40
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.AudioHelper
import org.deiverbum.app.util.Constants.PRECES_IL
import org.deiverbum.app.util.Constants.PRECES_R
import org.deiverbum.app.util.LiturgyHelper.Companion.R
import org.deiverbum.app.util.LiturgyHelper.Companion.V
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.getFormato
import org.deiverbum.app.util.replaceChars

fun textSmall(text: String, userData: UserDataDynamic): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)
    val paragraphStyle = ParagraphStyle(
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontSize = typography.bodySmall.fontSize
                )
            ) {
                append(text)
            }
        }
    }
}

fun textRubric(text: String, rubricColor: Color, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor, fontSize = fontSize)) {
            append(text)
        }
    }
}

/**
 * Formatea el epígrafe de los salmos y le da el tamaño de letra pequeño.
 * Método adaptado para Jetpack Compose.
 *
 * @since 2025.1
 */

fun transformEpigraph(text: String, userData: UserDataDynamic): AnnotatedString {
    //val typography= getPersonalizedTypography(userData.fontSize)
    val typography = getPersonalizedTypography(userData.fontSize)
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = typography.bodySmall.fontSize
            )
        ) {
            text.forEach { c ->
                when (c) {
                    '~' -> append("\n\t\t")
                    else -> append(c)
                }
            }
        }
    }
}

/**
 * Formatea el epígrafe de los salmos para audio.
 *
 * @since 2025.1
 */

fun transformEpigraphAudio(text: String): AnnotatedString {
    return buildAnnotatedString {
        append(text.replace("~", " "))
        append(".")
    }
}

/**
 * Formatea el texto litúrgico según la convención de marcado.
 * Método adaptado para Jetpack Compose.
 *
 * @since 2025.1
 */

fun transformText(text: String, fontSize: TextUnit, color: Color) = buildAnnotatedString {
    pushStyle(SpanStyle(fontSize = fontSize))

    text.forEach { c ->
        when (c) {
            '℣', '℟' -> withStyle(SpanStyle(color = color)) { append(c) }
            '†' -> {
                withStyle(SpanStyle(color = color)) { append(c) }
                append(" ")
            }

            '⟨' -> {
                withStyle(SpanStyle(fontSize = fontSize, color = color)) { append("(") }
            }

            '⟩' -> {
                withStyle(SpanStyle(fontSize = fontSize, color = color)) { append(")") }
            }

            'Ɽ' -> {
                withStyle(SpanStyle(fontSize = fontSize, color = color)) { append("R. ") }
            }

            '¦' -> append("\t")
            '≀', '~', '_' -> append("\n\t\t")
            '§' -> append("\n\n")
            else -> append(c)
        }
    }
}

fun transformTextMixtus(text: String, fontSize: TextUnit): AnnotatedString {
    val map = mapOf(
        "<p>" to "", "<P>" to "", "</p>" to "\n" +
                "\n", "</P>" to "\n\n"
    )
    var result = text
    map.forEach { t, u -> result = result.replace(t, u) }
    return (transformText(result, fontSize, Color.Black))
}

fun stringFromHtml(text: String): String {
    return Utils.fromHtml(text).toString()
}

fun textMultiColor(texts: List<String>, fontSize: TextUnit, color: Color): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        append(texts[0])
        append("   ")
        withStyle(style = SpanStyle(color = color)) {
            append(texts[1])
        }
    }
}

fun textFromHtml(data: String, fontSize: TextUnit): AnnotatedString {
    var annotatedString: AnnotatedString = buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        append(AnnotatedString.fromHtml(getFormato(data)))
    }
    return annotatedString//AnnotatedString.fromHtml(annotatedString)
}

fun readFromHtml(data: String): AnnotatedString {
    var annotatedString: AnnotatedString = buildAnnotatedString {
        append(AnnotatedString.fromHtml(getFormato(data)))
    }
    return annotatedString//AnnotatedString.fromHtml(annotatedString)
}

fun textVR(texts: List<String>, rubricColor: Color, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(SpanStyle(fontSize = fontSize))

        withStyle(style = SpanStyle(color = rubricColor)) {
            append(V)
        }
        append(" ${texts[0]}\n")

        withStyle(style = SpanStyle(color = rubricColor)) {
            append(R)
        }

        append(" ${texts[1]}")

    }
}

fun textWithV(text: String, rubricColor: Color, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(SpanStyle(fontSize = fontSize))
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(V)
        }
        append(" $text")
    }
}

fun textWithR(text: String, rubricColor: Color, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))

        withStyle(style = SpanStyle(color = rubricColor)) {
            append(R)
        }
        append(" $text")
    }
}

fun textSpaced(texts: List<String>, fontSize: TextUnit): AnnotatedString {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        texts.forEach {
            withStyle(style = paragraphStyle) {
                append(it)
                append("\n")
            }
        }
    }
}

fun normalStyle(fontSize: TextUnit): SpanStyle {
    return SpanStyle(fontSize = fontSize)
}

fun textIndent(first: String, second: String, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(first)
        append("\n\t")
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(PRECES_R)
        }
        append(second)
    }
}

fun textParagraph(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
        }
        append(text)
    }
}

fun textDefault(text: String, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = fontSize,
            )
        ) {
            append(text)
        }
    }
}

fun textDefaultItalic(text: String, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = fontSize,
                fontStyle = FontStyle.Italic
            )
        ) {
            append(text)
        }
    }
}


fun textSpan(text: String, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        append(text)
    }
}

fun textLines(lines: Int = 1, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(normalStyle(fontSize = fontSize))
        repeat(lines) {
            append("\n")
        }
    }
}

fun textBody(text: String): AnnotatedString {
    return buildAnnotatedString {
        //withStyle(style = ParagraphStyle(lineHeight = TextUnit.Unspecified)) {}
        append(text)
    }
}

fun textFromList(text: List<String>): AnnotatedString {
    return buildAnnotatedString {
        text.forEach {
            append(it)
            append(" \n")
        }
        //withStyle(style = ParagraphStyle(lineHeight = TextUnit.Unspecified)) {}
        //append(text)
    }
}

fun textSpan(text: AnnotatedString, userData: UserDataDynamic): AnnotatedString {
    val typography = getPersonalizedTypography(userData.fontSize)
    return buildAnnotatedString {
        /*withStyle(
            SpanStyle(
                fontSize = typography.bodyLarge.fontSize
            )
        ) */
        append(text)

    }
}

fun textBold(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(text)
        }
    }
}

@Composable
fun textLink(text: String, url: String, rubricColor: Color): AnnotatedString {
    val uriHandler = LocalUriHandler.current

    return buildAnnotatedString {
        val link =
            LinkAnnotation.Url(
                "https://developer.android.com/jetpack/compose",
                TextLinkStyles(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline,

                        color = rubricColor
                    )
                )
            ) {
                val url = (it as LinkAnnotation.Url).url
                // log some metrics
                uriHandler.openUri(url)
            }
        withLink(link) { append(text) }
    }
}

@Composable
fun getRubricColor(userData: UserDataDynamic): Color {
    var rubricColor = userData.rubricColor.value
    if (userData.darkThemeConfig.name == "FOLLOW_SYSTEM") {
        rubricColor = when (isSystemInDarkTheme()) {
            true -> Orange90
            false -> Red40
        }
    }
    return rubricColor
}


@Composable
fun TextZoomable(text: AnnotatedString, onTap: (Offset) -> Unit) {
    val zoomState = rememberZoomState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zoomable(
                zoomState = zoomState,
                onTap = onTap
            ),
    ) {
        Text(
            text = text,
            style = TextStyle.Default
        )
    }
}

@Composable
fun ZoomableText(text: AnnotatedString, userData: UserData) {

    var textScale by remember { mutableFloatStateOf(1f) }
    val zoomState = rememberTransformableState { zoomChange, _, _ ->
        textScale *= zoomChange
    }
    var fontSize = userData.dynamic.fontSize.key.toInt()
    //var fontSizee=userData.dynamic.fontSize.


    Text(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = zoomState, lockRotationOnZoomPan = true)
            .verticalScroll(rememberScrollState()),
        text = text,//LoremIpsum(2000).values.joinToString(),
        fontSize = (textScale * fontSize).sp,  // set base text size as needed
        lineHeight = (textScale * fontSize).sp  // should be same or more than fontSize
    )
}

@Composable
fun ZoomableTextOld(text: AnnotatedString) {

    var textScale by remember { mutableFloatStateOf(1f) }
    val zoomState = rememberTransformableState { zoomChange, _, _ ->
        textScale *= zoomChange
    }
    val scroll = rememberScrollState(0)


    Text(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(scroll)

            .transformable(state = zoomState),
        text = text,//LoremIpsum(2000).values.joinToString(),
        fontSize = (textScale * 16).sp,  // set base text size as needed
        lineHeight = (textScale * 16).sp  // should be same or more than fontSize
    )
}

@Composable
fun ZoomableBox(text: AnnotatedString) {
    var textScale by remember { mutableFloatStateOf(1f) }
    val zoomState = rememberTransformableState { zoomChange, _, _ ->
        textScale *= zoomChange
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = zoomState)
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .transformable(state = zoomState),
            text = text,//LoremIpsum(2000).values.joinToString(),
            fontSize = (textScale * 16).sp,  // set base text size as needed
            //lineHeight = (textScale * 16).sp  // should be same or more than fontSize
        )
    }
}

/**
 * Formatea el texto litúrgico según la convención de marcado.
 * Método adaptado para Jetpack Compose.
 *
 * @since 2025.1
 */

fun transformBodyText(text: String, rubricColor: Color, fontSize: TextUnit) = buildAnnotatedString {
    pushStyle(normalStyle(fontSize = fontSize))

    text.forEach { c ->

        when (c) {
            '¦' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\t") }
            }

            '≀', '_' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\t\t") }
            }

            '§' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\n") }
            }

            '~' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n") }
            }

            '⊣' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\t\t") }
            }

            '≠' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\t\t") }
                withStyle(SpanStyle(fontSize = fontSize, color = rubricColor)) { append(PRECES_R) }
            }

            'ƞ' -> {
                append(" ")
                withStyle(SpanStyle(fontSize = fontSize, color = rubricColor)) { append("N.") }
                append(" ")
            }

            '∞' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\n") }
                withStyle(SpanStyle(color = rubricColor, fontSize = fontSize)) {
                    append(PRECES_IL)
                }
                withStyle(SpanStyle(fontSize = fontSize)) {
                    append("\n\n")
                }
            }

            else ->
                withStyle(SpanStyle(fontSize = fontSize)) {
                    append(c)
                }
        }
    }
}


fun textFromListAudio(texts: List<String>): AnnotatedString {
    return buildAnnotatedString {
        texts.forEach {
            append(it)
        }
    }
}

fun textForAudio(text: String): AnnotatedString {
    return buildAnnotatedString {
        append(text)
    }
}

/**
 * Formatea el texto litúrgico según la convención de marcado.
 * Método adaptado para Jetpack Compose.
 *
 * @since 2025.1
 */

fun transformTextAudio(text: String) = buildAnnotatedString {
    append(
        text.replaceChars(AudioHelper.charsToReplace)
        /*text
            .replace("℣","")
            .replace("℟","")
            .replace("†","")
            .replace("⟨","")
            .replace("⟩","")
            .replace("Ɽ","")
            .replace("¦","")
            .replace("⟩","")
            .replace("⟩","")
            .replace("⟩","")
            .replace("⟩","")*/


    )


}
