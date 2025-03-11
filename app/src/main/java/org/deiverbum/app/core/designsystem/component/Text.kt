package org.deiverbum.app.core.designsystem.component

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.deiverbum.app.core.designsystem.theme.Orange90
import org.deiverbum.app.core.designsystem.theme.Red40
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.AudioHelper
import org.deiverbum.app.util.Constants.PRECES_R
import org.deiverbum.app.util.LiturgyHelper.Companion.R
import org.deiverbum.app.util.LiturgyHelper.Companion.V
import org.deiverbum.app.util.Utils.getFormato
import org.deiverbum.app.util.marksAndHtml
import org.deiverbum.app.util.replaceChars

@Composable
fun TextSmall(text: String, userData: UserDataDynamic, style: TextStyle) {
    val typography = getPersonalizedTypography(userData.fontSize)
    val textFinal = buildAnnotatedString {
        withStyle(SpanStyle(fontSize = typography.bodySmall.fontSize)) {
            append(text)
        }
    }
    Text(
        text = textFinal,
        style = style,
        fontSize = typography.bodySmall.fontSize,
        fontWeight = FontWeight.Light
    )
}

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

@Composable
fun textSmalll(text: String, userData: UserDataDynamic) {
    val typography = getPersonalizedTypography(userData.fontSize)
    val paragraphStyle = ParagraphStyle(
        platformStyle = PlatformParagraphStyle(includeFontPadding = false),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Bottom,
            trim = LineHeightStyle.Trim.LastLineBottom
        )
    )
    val text = buildAnnotatedString {
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
    Text(text = text, style = typography.bodySmall)
}

@Composable
fun textRubric(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(spanRubric()) {
            append(text)
        }
    }
}

@Composable
fun textusRubrica(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
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


fun textFromHtml(data: String): AnnotatedString {
    return buildAnnotatedString {
        //pushStyle(normalStyle(fontSize = fontSize))
        append(AnnotatedString.fromHtml(getFormato(data)))
    }
}

/**
 * Método específico para aquellos textos que puedan tener
 * etiquetas HTML o elementos del marcado personalizado,
 * por ejemplo, la primera lectura del Oficio.
 * En este caso se sustituyen las etiquetas <p> por saltos de línea.
 * Aquí no entra el formato, por ejemplo en el caso del Salmo Responsorial
 * (ver para ese caso [textFromHtml]
 */
@Composable
fun TextFromHtmlWithMarks(data: String, style: TextStyle) {
    Text(text = AnnotatedString.fromHtml(data.marksAndHtml()), style = style)
}

fun textFromHtmlWithMarks(data: String): AnnotatedString {
    return buildAnnotatedString {
        //pushStyle(normalStyle(fontSize = fontSize))
        append(AnnotatedString.fromHtml(data.marksAndHtml()))
    }
}

fun textFromHtmlAudio(data: String): AnnotatedString {
    return buildAnnotatedString {
        append(AnnotatedString.fromHtml(transformStringAudio(data)))
    }
}

@Composable
fun textVR(texts: List<String>): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append(V)
        }
        append(" ${texts[0]}\n")
        withStyle(style = spanRubric()) {
            append(R)
        }
        append(" ${texts[1]}")
    }
}

@Composable
fun TextVR(texts: List<String>, style: TextStyle) {
    val text = buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append(V)
        }
        append(" ${texts[0]}\n")
        withStyle(style = spanRubric()) {
            append(R)
        }
        append(" ${texts[1]}")
    }
    Text(text = text, style = style)
}

@Composable
fun textWithV(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append(V)
        }
        append(" $text")
    }
}

@Composable
fun textWithR(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append(R)
        }
        append(" $text")
    }
}

fun textSpaced(texts: List<String>): AnnotatedString {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    return buildAnnotatedString {
        //pushStyle(normalStyle(fontSize = fontSize))
        texts.forEach {
            withStyle(style = paragraphStyle) {
                append(it)
                //append("\n")
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

fun textDefaultt(text: String, userData: UserDataDynamic): AnnotatedString {
    val t = userData.fontSize
    val typography = getPersonalizedTypography(userData.fontSize)
    val def = typography.bodyLarge

    return buildAnnotatedString {
        withStyle(style = ParagraphStyle(lineHeight = def.lineHeight)) {

            withStyle(
                SpanStyle(
                    fontSize = def.fontSize,
                )
            ) {
                append(text)
            }
        }
    }
}

fun textDefault(text: String, fontSize: TextUnit): AnnotatedString {
    return buildAnnotatedString {
        //withStyle(style = ParagraphStyle(lineHeight = fontSize)) {

        withStyle(
            SpanStyle(
                fontSize = fontSize,
            )
        ) {
            append(text)
        }
        //}
    }
}

fun textDefaultItalic(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                //fontSize = fontSize,
                fontStyle = FontStyle.Italic
            )
        ) {
            append(text)
        }
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
    val fontSize = userData.dynamic.fontSize.key.toInt()
    //var fontSizee=userData.dynamic.fontSize.


    Text(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = zoomState, lockRotationOnZoomPan = true),
        //.verticalScroll(rememberScrollState()),
        text = text,//LoremIpsum(2000).values.joinToString(),
        fontSize = (textScale * fontSize).sp,  // set base text size as needed
        lineHeight = (textScale * fontSize).sp  // should be same or more than fontSize
    )
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

@Composable
fun transformBodyText(text: String) = buildAnnotatedString {
    //withStyle(style = ParagraphStyle(lineHeight = fontSize)) {
    text.forEach { c ->
        when (c) {
            '¦' -> {
                append("\t")
            }

            '≀', '_', '~', '⊣' -> {
                append("\n\t\t")
            }

            '§' -> {
                append("\n\n")
            }

            '≠' -> {
                append("\n\t\t")
                withStyle(spanRubric()) { append(PRECES_R) }
            }

            'ƞ' -> {
                append(" ")
                withStyle(spanRubric()) { append("N.") }
                append(" ")
            }

            /*'∞' -> {
                withStyle(SpanStyle(fontSize = fontSize)) { append("\n\n") }
                withStyle(SpanStyle(color = rubricColor, fontSize = fontSize)) {
                    append(PRECES_IL)
                }
                withStyle(SpanStyle(fontSize = fontSize)) {
                    append("\n\n")
                }
            }*/
            '℣', '℟' -> withStyle(spanRubric()) { append(c) }
            '†' -> {
                withStyle(spanRubric()) { append(c) }
                append(" ")
            }

            '⟨' -> {
                withStyle(spanRubric()) { append("(") }
            }

            '⟩' -> {
                withStyle(spanRubric()) { append(")") }
            }

            'Ɽ' -> {
                withStyle(spanRubric()) { append("R. ") }
            }

            else -> {
                append(c)
            }
        }
    }
    //}
}

/**
 * Crea dos elementos `Text`, el primero pegado al margen izquierdo, y el segundo al margen derecho.
 * Se usa el parámetro `colorCode` para determinar qué combinación de colores tendrán los textos:
 * - `0` Ambos en rojo
 * - `1` Ambos en negro
 * - `2` El primero en rojo, el segundo en negro
 * - `3` El primero en negro, el segundo en rojo
 */
@Composable
fun TextLeftRight(texts: List<String>, style: TextStyle, colorCode: Int) {
    val first = AnnotatedString.Builder()
    val second = AnnotatedString.Builder()
    when (colorCode) {
        0 -> {
            first.withStyle(spanRubric()) { append(texts[0]) }
            second.withStyle(spanRubric()) { append(texts[1]) }
        }

        1 -> {
            first.append(texts[0])
            second.append(texts[1])
        }

        2 -> {
            first.withStyle(spanRubric()) { append(texts[1]) }
            second.append(texts[0])
        }

        3 -> {
            first.append(texts[0])
            second.withStyle(spanRubric()) { append(texts[1]) }
        }

        else -> {
            first.append(texts[0])
            second.append(texts[1])
        }
    }

    Row {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp, bottom = 8.dp),
            text = first.toAnnotatedString(),
            style = style
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = second.toAnnotatedString(),
            style = style
        )
    }
}

/**
 * Formatea el texto litúrgico según la convención de marcado.
 * Método adaptado para Jetpack Compose.
 *
 * @param text Una cadena con el texto a transformar.
 * @return Un objeto [AnnotatedString] con el texto transformado.
 *
 * @since 2025.1
 */

fun transformTextAudio(text: String) = buildAnnotatedString {
    append(text.replaceChars(AudioHelper.charsToReplace))
}

/**
 * Formatea el texto litúrgico según la convención de marcado.
 * Método adaptado para Jetpack Compose.
 * Se usa para los textos que pueden tener carácteres de marcado,
 * o bien etiquetas HTML, como las lecturas bíblicas por ejemplo.
 *
 * @param text Una cadena con el texto a transformar.
 * @return Un objeto [String] con el texto transformado.
 *
 * @since 2025.1
 */

fun transformStringAudio(text: String) = text.replaceChars(AudioHelper.charsToReplace)


