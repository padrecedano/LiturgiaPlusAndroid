package org.deiverbum.app.core.designsystem.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.Orange90
import org.deiverbum.app.core.designsystem.theme.Red40
import org.deiverbum.app.core.model.data.ContentHead
import org.deiverbum.app.core.model.data.ContentTitle
import org.deiverbum.app.core.model.data.SectionTitle
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.ui.SpaceNormal
import org.deiverbum.app.util.LiturgyHelper.Companion.R
import org.deiverbum.app.util.LiturgyHelper.Companion.V
import org.deiverbum.app.util.Utils


@Composable
fun TextSanctus(text: String) {
    TextSmall(text = text)
    SpaceNormal()
}

fun textSmall(text: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = NiaTypography.bodySmall.fontSize
            )
        ) {
            append(text)
        }
    }
}

@Composable
fun TextSmall(text: String) {
    Text(
        text = text,
        style = NiaTypography.bodySmall,
    )
}

@Composable
fun TextBody(text: String, useLineBreak: Boolean) {
    if (useLineBreak) {
        TextWithLineBreak(text)
    } else {
        return Text(
            text = text,
            /*modifier = Modifier
                .width(130.dp)
                .border(BorderStroke(1.dp, Color.Gray)),*/
            fontSize = 14.sp,

            )
    }
}

@Composable
fun TextWithLineBreak(text: String) {
    return Text(
        text = text,
        /*modifier = Modifier
            .width(130.dp)
            .border(BorderStroke(1.dp, Color.Gray)),*/
        fontSize = 14.sp,
        style = TextStyle.Default.copy(
            lineBreak = LineBreak.Paragraph
        )
    )
}


@Composable
fun TextRubric(text: String, rubricColor: Color) {
    Text(text = text, color = rubricColor)
}

fun textRubric(text: String, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(text)
        }
    }
}

@Composable
fun TextFromHtml(text: String) {
    Text(text = stringFromHtml(text))
}

fun stringFromHtml(text: String): String {
    return Utils.fromHtml(text).toString()
}

@Composable
fun TextMultiColor(texts: List<String>, color: Color) {
    Text(buildAnnotatedString {
        append(texts[0])
        append("   ")
        withStyle(style = SpanStyle(color = color)) {
            append(texts[1])
        }
    })
}

fun textMultiColor(texts: List<String>, color: Color): AnnotatedString {
    return buildAnnotatedString {
        append(texts[0])
        append("   ")
        withStyle(style = SpanStyle(color = color)) {
            append(texts[1])
        }
    }
}

@Composable
fun TextVR(texts: List<String>, rubricColor: Color) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(V)
        }
        append(" ${texts[0]}")
    })
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(R)
        }
        append(" ${texts[1]}")
    })

}

fun textVR(texts: List<String>, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(V)
        }
        append(" ${texts[0]}")
        append(Utils.LS)
        withStyle(style = SpanStyle(color = rubricColor)) {
            append(R)
        }
        append(" ${texts[1]}")
    }
}

@Composable
fun TextSpaced(texts: List<String>) {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    Text(
        buildAnnotatedString {
            texts.forEach {
                withStyle(style = paragraphStyle) {
                    append(it)
                }
            }
        }
    )
}

fun textSpaced(texts: List<String>): AnnotatedString {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    return buildAnnotatedString {
        texts.forEach {
            withStyle(style = paragraphStyle) {
                append(it)
            }
        }
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
fun TextRubricc(text: String, userData: UserDataDynamic, style: TextStyle) {
    var rubricColor = userData.rubricColor.value
    if (userData.darkThemeConfig.name == "FOLLOW_SYSTEM") {
        rubricColor = when (isSystemInDarkTheme()) {
            true -> Color.Yellow
            false -> Color.Red
        }
    }
    Text(text = text, color = getRubricColor(userData = userData), style = style)
}


@Composable
fun h(/*text: String, userData: UserDataDynamic,*/data: ContentHead) {
    val paragraphStyle1 = ParagraphStyle(
        textIndent = TextIndent(firstLine = 14.sp)
    )
    val annotatedString = buildAnnotatedString {
        if (data.userData != null) {
            withStyle(style = SpanStyle(color = getRubricColor(data.userData))) {
                //append(Text(text, darkTheme = darkTheme,rubricColor=rubricColor)
                append(data.text)
            }
        } else {
            append(data.text)
        }
    }
    //data.textStyle
    Text(text = annotatedString, style = data.textStyle)
    Spacer(modifier = Modifier.height(16.dp))

}
//return TextRubric(text = annotatedString, userData)
//return annotatedString


@Composable
fun contentTitle(data: ContentTitle) {
    Spacer(modifier = Modifier.height(16.dp))

    val paragraphStyle1 = ParagraphStyle(
        textIndent = TextIndent(firstLine = 14.sp)
    )
    val annotatedString = buildAnnotatedString {
        if (data.userData != null) {
            withStyle(style = SpanStyle(color = getRubricColor(data.userData))) {
                //append(Text(text, darkTheme = darkTheme,rubricColor=rubricColor)
                append(data.text)
            }
        } else {
            append(data.text)
        }
    }
    //data.textStyle
    Text(text = annotatedString, style = data.textStyle)
    Spacer(modifier = Modifier.height(16.dp))

}

@Composable
fun sectionTitle(data: SectionTitle) {
    Spacer(modifier = Modifier.height(16.dp))
    val annotatedString = buildAnnotatedString {
        append(data.text)
    }
    Text(text = annotatedString, style = data.textStyle)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun VerseContent(text: String, userData: UserDataDynamic) {

    val lstValues: List<String> = text.split("§").map { it -> it.trim() }
    lstValues.forEach { it ->
        Spacer(modifier = Modifier.height(16.dp))
        it.split("~").map { it -> it.trim() }.forEach { verse ->
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                        append(verse)

                    }
                }
            )

            //println(verse)}
            //println(it);
        }

        //println(lst)
    }
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
        )
    }
}