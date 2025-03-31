package org.deiverbum.app.core.designsystem.component

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
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
import org.deiverbum.app.core.model.book.BaseHead
import org.deiverbum.app.core.model.book.Canon
import org.deiverbum.app.core.model.book.CanonWithList
import org.deiverbum.app.core.model.book.LiberBiblical
import org.deiverbum.app.core.model.book.LiberDialog
import org.deiverbum.app.core.model.book.LiberHeadComplex
import org.deiverbum.app.core.model.book.LiberHeadSingle
import org.deiverbum.app.core.model.book.LiberMixtus
import org.deiverbum.app.core.model.book.LiberMixtusB
import org.deiverbum.app.core.model.book.LiberOratio
import org.deiverbum.app.core.model.book.LiberOratioo
import org.deiverbum.app.core.model.book.LiberParagraphus
import org.deiverbum.app.core.model.book.LiberPreces
import org.deiverbum.app.core.model.book.LiberText
import org.deiverbum.app.core.model.book.ParagraphusBase
import org.deiverbum.app.core.model.book.ParagraphusBiblicaBrevis
import org.deiverbum.app.core.model.book.ParagraphusBiblicaLonga
import org.deiverbum.app.core.model.book.ParagraphusDialog
import org.deiverbum.app.core.model.book.ParagraphusMixtus
import org.deiverbum.app.core.model.book.ParagraphusOratio
import org.deiverbum.app.core.model.book.ParagraphusPreces
import org.deiverbum.app.core.model.book.ParagraphusPriest
import org.deiverbum.app.core.model.book.ParagraphusRationarium
import org.deiverbum.app.core.model.book.ParagraphusResponsum
import org.deiverbum.app.core.model.book.ParagraphusRubricaNew
import org.deiverbum.app.core.model.book.ParagraphusRubricaNumerus
import org.deiverbum.app.core.model.book.ParagraphusVersiculus
import org.deiverbum.app.core.model.book.ParagraphusVersiculusResponsum
import org.deiverbum.app.core.model.book.Priest
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.UserDataDynamic
import org.deiverbum.app.core.ui.ContentLabel
import org.deiverbum.app.core.ui.ContentTitle
import org.deiverbum.app.util.AudioHelper
import org.deiverbum.app.util.Constants.PRECES_R
import org.deiverbum.app.util.LiturgyHelper.Companion.EMDASH
import org.deiverbum.app.util.LiturgyHelper.Companion.R
import org.deiverbum.app.util.LiturgyHelper.Companion.V
import org.deiverbum.app.util.Utils.getFormato
import org.deiverbum.app.util.marksAndHtml
import org.deiverbum.app.util.replaceChars
import org.deiverbum.app.util.splitParts

@Composable
fun TextSmall(text: String, userData: UserData, style: TextStyle) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
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

@Composable
fun TextSmall(text: AnnotatedString, userData: UserData, style: TextStyle) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
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
fun textRubric(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(spanRubric()) {
            append(text)
        }
    }
}

@Composable
fun textusRubrica(text: String, isBold: Boolean = false): AnnotatedString {
    return buildAnnotatedString {
        if (isBold) {
            withStyle(
                SpanStyle(
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(text)
            }
        } else {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.error)) {
                append(text)
            }
        }
    }
}

/**
 * Formatea el epígrafe de los salmos y le da el tamaño de letra pequeño.
 * Método adaptado para Jetpack Compose.
 *
 * @since 2025.1
 */

fun transformEpigraph(text: String, userData: UserData): AnnotatedString {
    //val typography= getPersonalizedTypography(userData.fontSize)
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
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
fun textVersiculus(data: ParagraphusVersiculus): AnnotatedString {
    return buildAnnotatedString {
        append(textusRubrica(V))
        append(textBold(data.txt, data.isBold))
    }
}

@Composable
fun textResponsum(data: ParagraphusResponsum): AnnotatedString {
    return buildAnnotatedString {
        append(textusRubrica(R))
        append(data.txt)
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
fun TextVR(texts: List<String>, style: TextStyle, isBold: Boolean = false, lineBreak: Int = 1) {
    val text = buildAnnotatedString {
        withStyle(style = spanRubric()) {
            append(V)
        }
        append(" ${texts[0]}")
        when (lineBreak) {
            1 -> append("\n")
            2 -> append("\n\n")

        }
        withStyle(style = spanRubric()) {
            append(R)
        }
        append(" ${texts[1]}")
    }
    when (isBold) {
        true -> Text(text = text, style = style, fontWeight = FontWeight.Bold)
        false -> Text(text = text, style = style)
    }
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

@Composable
fun TextFromList(
    list: List<String>,
    style: TextStyle,
    isRubric: Boolean = true,
    isBold: Boolean = false
) {
    var i = 0
    val text = buildAnnotatedString {
        if (isRubric) {
            pushStyle(spanRubric())
        }
        list.forEach {
            append(it)
            if (i++ != list.size - 1) {
                // Last iteration
                append("\n\n")
            }
            //append("\n\n")
        }
    }
    if (isBold) {
        Text(text = text, style = style, fontWeight = FontWeight.Bold)
    } else {
        Text(text = text, style = style)
    }
    //withStyle(style = ParagraphStyle(lineHeight = TextUnit.Unspecified)) {}
    //append(text)

}

@Composable
fun TextLiberPriest(
    liber: Priest,
    style: TextStyle,
) {
    var i = 0
    val text = buildAnnotatedString {

        liber.txt.forEach {
            append(it)
            when (liber.lineBreak) {
                1 -> append("\n")
                2 -> append("\n\n")

            }
            /*if (i++ != list.size - 1) {
                // Last iteration
                append("\n\n")
            }*/
            //append("\n\n")
        }
    }
    if (liber.isBold) {
        Text(text = text, style = style, fontWeight = FontWeight.Bold)
    } else {
        Text(text = text, style = style)
    }
    //withStyle(style = ParagraphStyle(lineHeight = TextUnit.Unspecified)) {}
    //append(text)

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

fun textBold(text: String, isBold: Boolean): AnnotatedString {
    if (isBold) {
        return textBold(text)
    }
    return buildAnnotatedString {
        append(text)
    }
}

@Composable
fun TextBase(data: ParagraphusBase, style: TextStyle) {
    /*val i = data.txt.size - 1
    val text = buildAnnotatedString {

        data.txt.forEachIndexed  { index, it ->
            append(it)
            if(index < i) append("\n\n")

        }


    }*/
    Text(text = data.txt, style = style)
}


@Composable
fun textBase(text: String, isBold: Boolean, isRed: Boolean): AnnotatedString {
    return buildAnnotatedString {

        if (isBold && isRed) {
            return textusRubrica(text, isBold)
        } else if (isBold) {
            return textBold(text)
        } else if (isRed) {
            return textusRubrica(text)
        } else {
            append(text)
        }
        append(text)
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

@Composable
fun transformMixtus(texts: String, replaces: List<String>) = buildAnnotatedString {
    //withStyle(style = ParagraphStyle(lineHeight = fontSize)) {
    texts.forEach { c ->
        when (c) {
            '9' -> {
                append("\t")
            }

            '≀', '_', '~', '⊣' -> {
                append("\n\t\t")
            }

            '§' -> {
                append("\n\n")
            }

            '1' -> {
                //append(replaces)
                withStyle(spanRubric()) { append(replaces[0]) }
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

@Composable
fun TextRubricaNumerus(
    data: ParagraphusRubricaNumerus,
    style: TextStyle,
    colorCode: Int
) {
    val text =
        buildString {
            data.txt.forEach {
                append(it)
                /*when (data.extraSpace) {
                    1 -> append("\n")
                    2 -> append("\n\n")
                }*/
            }
        }

    TextMixtus(
        texts = listOf("${data.n}. ", text),
        style = style,
        colorCode = colorCode,
        isBold = data.isBold
    )
}

@Composable
fun TextRubrica(
    data: ParagraphusRubricaNew,
    style: TextStyle
) {
    Text(
        text = buildAnnotatedString {
            //data.txt.forEach {
            append(textusRubrica(data.txt, data.isBold))
            if (!data.isLast) {
                append("\n\n")
            }
            //}
        },
        style = style
    )
}

@Composable
fun TextRubricaNumerus(
    data: ParagraphusRubricaNumerus,
    style: TextStyle
) {
    //val i = data.txt.size - 1

    val text =
        buildString {
            //data.txt.forEachIndexed { index, it ->
            append(data.txt)
            //if (index < i) {
            /*when (data.extraSpace) {
                1 -> append("\n")
                2 -> append("\n\n")
            }*/
            //}
            //if (data.extraSpace) append("\n\n")
            //}
            if (data.isLast) append("\n\n")
        }

    TextMixtus(
        texts = listOf("${data.n}. ", text),
        style = style,
        colorCode = data.colorCode,
        isBold = data.isBold
    )
}


@Composable
fun TextHead(
    data: BaseHead,
    userData: UserData
) {
    when (data) {
        is LiberHeadComplex -> {
            ContentTitle(
                data = data.title,
                level = data.level,
                userData = userData,
                uppercase = false
            )
            ContentLabel(
                data = data.subTitle,
                level = data.level - 1,
                userData = userData,
                uppercase = false,
                withColor = true
            )
        }

        is LiberHeadSingle -> ContentTitle(
            data = data.title,
            level = data.level,
            userData = userData,
            uppercase = false
        )
    }
}

@Composable
fun TextParagraphusPriest(
    data: ParagraphusPriest,
    style: TextStyle
) {
    val text =
        buildAnnotatedString {
            //data.txt.forEachIndexed { index, it ->
            when (data.isBold) {
                true -> append(textBold(data.txt))
                false -> append(data.txt)
            }
            //if (index < i) {
            append(" ")
            /*when (data.extraSpace) {
                1 -> append("\n")
                2 -> append("\n\n")
            }*/


            if (data.isLast) append("\n\n")
        }
    when (data.isBold) {
        true -> Text(text = text, style = style, fontWeight = FontWeight.Bold)
        false -> Text(text = text, style = style)
    }
}

@Composable
fun TextResponsum(
    data: ParagraphusResponsum,
    style: TextStyle
) {

    Text(text = textResponsum(data), style = style)
}

@Composable
fun TextVersiculusResponsum(
    data: ParagraphusVersiculusResponsum,
    style: TextStyle
) {
    val text =
        buildAnnotatedString {
            data.items.forEach {
                when (it) {
                    is ParagraphusVersiculus -> {
                        append(textVersiculus(it))
                        append("\n\n")
                    }

                    is ParagraphusResponsum -> {
                        append(textResponsum(it))
                        if (it.isLast) {
                            append("\n\n")
                        }
                    }


                }
                //append(it)
                //if (data.extraSpace) append("\n\n")
            }
            //if (data.isLast) append("\n\n")
        }

    Text(text = text, style = style)
}

@Composable
fun TextBiblicaBrevis(
    data: ParagraphusBiblicaBrevis,
    style: TextStyle
) {

    Text(text = textusRubrica(data.pericopa), style = style)
    Text(
        text = data.txt,
        style = style
    )
}

@Composable
fun TextBiblicaLonga(
    data: ParagraphusBiblicaLonga,
    style: TextStyle
) {
    Text(
        text = buildAnnotatedString {
            append(data.book)
            append("   ")
            append(textusRubrica(data.pericopa))
            append("\n\n")
            append(data.txt)
            if (!data.isLast) {
                append("\n")
            }
        },
        style = style
    )
}

@Composable
fun TextLiberOratio(
    data: LiberOratio,
    style: TextStyle
) {
    val i = data.txt.size - 1

    val text = buildString {
        data.txt.forEachIndexed { index, it ->
            append(it)
            append(" ")
            when (data.lineBreak) {
                1 -> append("\n")
                2 -> append("\n\n")
            }
            /*when (index == i) {
                true -> append("\n")
                false -> append("\n\n")
            }*/
        }
    }
    TextVR(listOf(text, data.responsum), style, data.isBold, 2)
}

@Composable
fun TextLiberOratioo(
    data: LiberOratioo,
    style: TextStyle
) {
    val text = buildAnnotatedString {
        data.paragraphus.forEach {
            it.txt.forEach {
                append(it)
            }
        }
    }
    Text(text)
    Text(data.responsum)

}

@Composable
fun textParagraphus(data: LiberParagraphus, style: TextUnit) {
    /*when(data.type){
        "mixtusB"->  TextLiberMixtusB(
            data.,
            style
        )
    }

}*/
}

@Composable
fun TextLiberBiblical(
    data: LiberBiblical,
    style: TextStyle
) {
    val i = data.txt.size - 1

    Text(
        text = buildAnnotatedString {
            append(data.book)
            append(" ")
            append(textusRubrica(data.pericopa))
            append("\n\n")
            data.txt.forEachIndexed { index, it ->
                append(it)
                when (index == i) {
                    true -> append("\n")
                    false -> append("\n\n")
                }
            }
        },
        style = style
    )
}

@Composable
fun TextLiberPrecess(
    data: LiberPreces,
    style: TextStyle
) {

    val responsum = textDefaultItalic(data.responsum)
    Text(
        text = buildAnnotatedString {
            append(data.intro)
            append("\n\n")
            append(responsum)
            append("\n\n")
            data.txt.forEach {
                append(it)
                append("\n\n")
                append(textusRubrica("- "))
                append(responsum)
                append("\n\n")

            }
        },
        style = style
    )
}

@Composable
fun TextLiberPreces(
    data: LiberPreces,
    style: TextStyle
) {

    val responsum = textDefaultItalic(data.responsum)
    Text(
        text = buildAnnotatedString {
            append(data.intro)
            append("\n\n")
            append(responsum)
            append("\n\n")
            data.txt.forEach {
                append(it)
                append("\n\n")
                append(textusRubrica("- "))
                append(responsum)

                append("\n\n")

            }
        },
        style = style
    )
}

@Composable
fun textResponsum(
    text: String
): AnnotatedString {
    return buildAnnotatedString {
        append(textusRubrica(R))
        append(" ")
        append(textDefaultItalic(text))
    }
}

@Composable
fun TextLiberDialog(
    liber: LiberDialog,
    style: TextStyle
) {
    val i = liber.txt.size - 1
    val responsum = textResponsum(liber.responsum)
    Text(
        text = buildAnnotatedString {
            if (liber is LiberPreces) {
                append(" ")
                append(liber.intro)
                append("\n\n")
                append(responsum)
                append("\n\n")
            }
            liber.txt.forEachIndexed { index, it ->
                append(textusRubrica(EMDASH))
                append(" ")
                append(it)
                append("\n\n")
                append(responsum)
                when (index == i) {
                    true -> append("\n")
                    false -> append("\n\n")
                }
            }
        },
        style = style
    )
}

@Composable
fun liberText(
    text: String,
    isRed: Boolean = false
): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))

        when (isRed) {
            true -> append(textusRubrica(text))
            false -> append(text)
        }
    }
}

@Composable
fun liberText(
    liber: LiberText,
): AnnotatedString {
    return buildAnnotatedString {
        if (liber.isBold) {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        }
        when (liber.isRed) {
            true -> append(textusRubrica(liber.txt))
            false -> {
                /*if (liber.isBold) {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                }*/
                append(liber.txt)
            }
        }
        when (liber.lineBreak) {
            1 -> append("\n")
            2 -> append("\n\n")
        }
    }
}


@Composable
fun LiberText(
    liber: LiberText,
    style: TextStyle
) {
    val text = buildAnnotatedString {
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        when (liber.isRed) {
            true -> append(textusRubrica(liber.txt))
            false -> append(liber.txt)
        }

        when (liber.lineBreak) {
            1 -> append("\n")
            2 -> append("\n\n")
        }

    }
    when (liber.isBold) {
        true -> Text(text = text, style = style, fontWeight = FontWeight.Bold)
        false -> Text(text = text, style = style)
    }
}

@Composable
fun TextParagraphusDialog(
    liber: ParagraphusDialog,
    style: TextStyle
) {

    //val split = liber.base.txt.splitParts("\\|")
    var i = liber.txt.size - 1
    val previous = if (liber.withDash) EMDASH else V
    Text(
        text = buildAnnotatedString {
            liber.txt.forEachIndexed { index, it ->
                append(textusRubrica(previous))
                append(" ")
                append(textBase(it, isBold = true, isRed = false))
                append("\n\n")
                append(textResponsum(liber.responsum))
                if (index < i) append("\n\n")
            }
        },
        style = style
    )
}

@Composable
fun TextRationarium(
    liber: ParagraphusRationarium,
    style: TextStyle
) {

    //val split = liber.base.txt.splitParts("\\|")
    var i = liber.txt.size - 1
    val previous = if (liber.withDash) EMDASH else V
    Text(
        text = buildAnnotatedString {
            liber.txt.forEachIndexed { index, it ->
                append(textusRubrica(previous))
                append(" ")
                append(textBase(it, isBold = true, isRed = false))
                //append("\n\n")
                //append(textResponsum(liber.responsum))
                if (index < i) append("\n\n")
            }
        },
        style = style
    )
}

@Composable
fun TextParagraphusOratio(
    liber: ParagraphusOratio,
    style: TextStyle
) {

    //val split = liber.base.txt.splitParts("\\|")

    Text(
        text = buildAnnotatedString {
            //liber.txt.forEachIndexed { index, it ->
            append(textusRubrica(V))
            append(" ")
            append(textBase(liber.txt, isBold = true, isRed = false))
            append("\n\n")
            append(textResponsum(liber.responsum))

        },
        style = style
    )
}


@Composable
fun TextParagraphusPreces(
    liber: ParagraphusPreces,
    style: TextStyle
) {
    var i = liber.txt.size - 1
    when (liber.intro) {
        is ParagraphusBase -> Text(text = liber.intro.txt, style = style)
        is ParagraphusMixtus -> TextParagraphusMixtus(liber.intro, style)
    }
    Text(
        text = buildAnnotatedString {
            //append(textBold(liber.intro))
            //append("\n\n")
            append(textResponsum(liber.responsum))
            append("\n\n")

            liber.txt.forEachIndexed { index, it ->
                append(textusRubrica(EMDASH))
                append(" ")
                append(textBase(it, isBold = true, isRed = false))
                append("\n\n")
                append(textResponsum(liber.responsum))
                if (index < i) append("\n\n")
            }
        },
        style = style
    )
}


@Composable
fun TextLiberMixtusB(
    liber: LiberMixtusB,
    style: TextStyle
) {

    val split = liber.base.txt.splitParts("\\|")
    var i = split.size - 1

    Text(
        text = buildAnnotatedString {
            split.forEachIndexed { index, s ->
                //if (index != i && index<i) {
                val new = LiberText(
                    txt = s,
                    isRed = liber.base.isRed,
                    isBold = liber.base.isBold,
                    lineBreak = liber.base.lineBreak
                )
                //if(index==i){
                append(liberText(new))
                /*when (liber.base.isRed) {
                    true -> append(textusRubrica(s))
                    false -> append(s)
                }*/
                if (index < i) {
                    val sub = liber.replace[index]
                    //val newSub=LiberText(txt=s,isRed=liber.base.isRed, isBold = liber.base.isBold, lineBreak = liber.base.lineBreak)

                    append(liberText(sub))
                }
                /*when (sub.isRed) {
                    true -> append(textusRubrica(sub.txt))
                    false -> append(sub.txt)
                }*/
            }
            //}
        },
        style = style
    )
}

@Composable
fun TextParagraphusMixtus(
    liber: ParagraphusMixtus,
    style: TextStyle
) {

    val split = liber.base.txt.splitParts("\\|")
    var i = split.size - 1

    Text(
        text = buildAnnotatedString {
            split.forEachIndexed { index, s ->
                //if (index != i && index<i) {
                val new = LiberText(
                    txt = s,
                    isRed = liber.base.isRed,
                    isBold = liber.base.isBold,
                    lineBreak = liber.base.lineBreak
                )
                //if(index==i){
                append(liberText(new))
                /*when (liber.base.isRed) {
                    true -> append(textusRubrica(s))
                    false -> append(s)
                }*/
                if (index < i) {
                    val sub = liber.replace[index]
                    //val newSub=LiberText(txt=s,isRed=liber.base.isRed, isBold = liber.base.isBold, lineBreak = liber.base.lineBreak)

                    append(liberText(sub))
                }
                /*when (sub.isRed) {
                    true -> append(textusRubrica(sub.txt))
                    false -> append(sub.txt)
                }*/
            }
            //}
        },
        style = style
    )
}


@Composable
fun TextLiberMixtus(
    liber: LiberMixtus,
    style: TextStyle
) {
    val text = buildAnnotatedString {
        liber.data.forEachIndexed { index, s ->
            append(liberText(s))
        }
    }
    Text(text = text, style = style)
}


/**
 * Crea un elementos `Text`, con combinaciones de color según los códigos.
 * Se usa el parámetro `colorCode` para determinar qué combinación de colores tendrán los textos:
 * - `0` Ambos en rojo
 * - `1` Ambos en negro
 * - `2` El primero en rojo, el segundo en negro
 * - `3` El primero en negro, el segundo en rojo
 */

@Composable
fun TextMixtus(
    texts: List<String>,
    style: TextStyle,
    colorCode: Int,
    isBold: Boolean
) {
    val text = buildAnnotatedString {
        when (colorCode) {
            0 -> {
                withStyle(spanRubric()) { append(texts.joinToString(separator = " ")) }
                //second.withStyle(spanRubric()) { append(texts[1]) }
            }

            1 -> {
                append(texts.joinToString(separator = " "))
            }

            2 -> {
                withStyle(spanRubric()) { append(texts[0]) }
                append(texts[1])
            }

            3 -> {
                append(texts[0])
                withStyle(spanRubric()) { append(texts[1]) }
            }

            else -> {
                append(texts[0])
                append(texts[1])
            }
        }
    }
    when (isBold) {
        true -> Text(text = text, style = style, fontWeight = FontWeight.Bold)
        false -> Text(text = text, style = style)
    }


}


/**
 * Crea dos elementos `Text`, el primero pegado al margen izquierdo, y el segundo al margen derecho.
 * Se usa el parámetro `colorCode` para determinar qué combinación de colores tendrán los textos:
 * - `0` Ambos en rojo
 * - `1` Ambos en negro
 * - `2` El primero en rojo, el segundo en negro
 * - `3` El primero en negro, el segundo en rojo
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    texts: List<String>,
    style: TextStyle,
    colorCode: Int
) {
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
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp),
            text = first.toAnnotatedString(),
            style = style,
            overflow = TextOverflow.Visible,
            maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp),
            text = second.toAnnotatedString(),
            style = style,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableTextt(
    modifier: Modifier = Modifier,
    texts: List<String>,
    style: TextStyle,
    colorCode: Int
) {
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
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = first.toAnnotatedString(),
            style = style,
            //overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = second.toAnnotatedString(),
            style = style,
        )
    }
}

@Composable
fun Test(data: String, withSpace: Boolean = true, modifier: Modifier) {
    if (withSpace) {
        modifier.padding(top = 160.dp, bottom = 80.dp)
    }
    Text(text = data, modifier = modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextLeftRightB(
    texts: List<String>,
    style: TextStyle,
    colorCode: Int,
    withSpace: Boolean = true,
    modifier: Modifier
) {
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
    if (withSpace) {
        modifier.padding(top = 160.dp, bottom = 80.dp)
    }
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier,
            text = first.toAnnotatedString(),
            style = style,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = modifier,
            text = second.toAnnotatedString(),
            style = style,
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
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

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp),
            text = first.toAnnotatedString(),
            style = style,
            overflow = TextOverflow.Ellipsis,
            //maxLines = 1
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp),
            text = second.toAnnotatedString(),
            style = style,
        )
    }
}

@Composable
fun TextSpaced(text: AnnotatedString, style: TextStyle) {
    Text(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp),
        text = text,
        style = style,
    )
}

@Composable
fun TextCanon(data: Canon, style: TextStyle) {
    val i = data.txt.size - 1
    Text(
        text = buildAnnotatedString {
            append(textusRubrica(data.n.toString(), true))
            append(" ")
            data.txt.forEachIndexed { index, item ->
                append(item)
                when (index == i) {
                    true -> append("\n")
                    false -> append("\n\n")
                }
            }
        },
        style = style
    )
}

@Composable
fun TextCanonWithList(data: CanonWithList, style: TextStyle) {
    val i = data.txt.size - 1
    TextCanon(data, style)
    Text(
        text = buildAnnotatedString {
            data.list.forEachIndexed { index, item ->
                append("\t- ")
                append(item)
                when (index == i) {
                    true -> append("\n\n")
                    false -> append("\n")
                }
            }
        },
        style = style
    )
}

@Composable
fun TextError(message: String) {
    Text(
        text = stringResource(id = org.deiverbum.app.R.string.error_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
    )
    Text(
        text = "${stringResource(id = org.deiverbum.app.R.string.version)}: ${stringResource(id = org.deiverbum.app.R.string.app_version_and_name)}",
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
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

