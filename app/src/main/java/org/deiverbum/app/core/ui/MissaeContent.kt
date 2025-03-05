package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textFromHtml
import org.deiverbum.app.core.designsystem.component.textLines
import org.deiverbum.app.core.designsystem.component.textParagraph
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textSpan
import org.deiverbum.app.core.designsystem.component.transformText
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.Homily
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Pantallas para las lecturas de la Misa.
 *
 * @author A. Cedano
 * @since 2025.1
 *
 * @param data Objeto del tipo [Missae]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 *
 * @see [Missae]
 * @see [MissaeLectionum]
 */


@OptIn(ExperimentalStdlibApi::class)
@ExperimentalMaterial3Api
@Composable
fun MissaeLectionumScreen(
    data: Missae,
    userData: UserData,
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {

    val rubricColorr = getRubricColor(userData = userData.dynamic)
    val rubricColor = MaterialTheme.colorScheme.error
    val asb = AnnotatedString.Builder()
    Column {
        val onTap = { point: Offset -> }
        if (data.lectionumList != null) {
            data.lectionumList!!.sort()
            //asb.append(contentTitle("Test",1,userData.dynamic,rc,true))
            asb.append(lectionumList(data.lectionumList!!, userData.dynamic, rubricColor, fontSize))
            TextZoomable(
                onTap = onTap,
                text = asb.toAnnotatedString()
            )
        }
    }
}

private fun AnnotatedString.Builder.appendStyledContentt(
    content: String,
    shouldAddSpacing: Boolean,
    rubricColor: Color
) {
    var currentIndex = 0
    val regex = """<(/?strong|/?em|/?b|/?i|)>""".toRegex()
    val matches = regex.findAll(content).toList()
    val newContent = if (shouldAddSpacing) content + "\n" else content
    if (matches.isEmpty()) {
        append(newContent)
        return
    }
    matches.forEach { match ->
        val matchStart = match.range.first
        if (matchStart > currentIndex) {
            append(newContent.substring(currentIndex, matchStart))
        }

        currentIndex = match.range.last + 1
        when (match.value) {
            "<strong>", "<b>" -> pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            "</strong>", "</b>" -> pop()
            "<em>", "<i>" -> pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
            "</em>", "</i>" -> pop()
            /*"℣", "℟" -> {
                pushStyle(SpanStyle(color = rubricColor))
            }*/
        }
    }

    if (currentIndex < newContent.length) {
        append(newContent.substring(currentIndex))
    }
}

@ExperimentalStdlibApi
fun lectionumList(
    lectionumList: MissaeLectionumList,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return try {
        buildAnnotatedString {
            if (lectionumList.type == -1) {
                append(Utils.LS)
                withStyle(
                    SpanStyle(
                        fontSize = NiaTypography.titleMedium.fontSize,
                        color = rubricColor
                    )
                ) {
                    append(Constants.TITLE_MASS_GOSPEL)
                }
            }
            lectionumList.lectionum.forEach {
                append(
                    lectioSimplex(
                        data = it!!,
                        type = lectionumList.type,
                        userData = userData,
                        rubricColor = rubricColor,
                        fontSize = fontSize
                    )
                )
            }
        }
    } catch (e: Exception) {
        buildAnnotatedString {
            append(Utils.createErrorMessage(e.message))
        }
    }
}

fun lectioMetadata(
    data: MissaeLectionum,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(fontSize = fontSize)
        ) {
            append(data.book.liturgyName)
        }
        append("    ")
        withStyle(
            SpanStyle(
                color = rubricColor,
                fontSize = fontSize,
            )
        ) {
            append(data.pericopa)
        }
        append(textLines(2, fontSize))
        if (data.tema != "") {
            withStyle(SpanStyle(color = rubricColor)) {
                append(data.tema)
            }
            append(textLines(2, fontSize))
        }
    }
}

fun lectioSimplex(
    data: MissaeLectionum,
    type: Int,
    userData: UserDataDynamic,
    rubricColor: Color,
    fontSize: TextUnit
): AnnotatedString {
    var text = AnnotatedString("")
    return buildAnnotatedString {
        if (type == 0) {
            text += textLines(2, fontSize)

            text += contentTitle(data.getHeader(type), 2, userData, rubricColor)
            text += textLines(2, fontSize)
            text += lectioMetadata(data, rubricColor, fontSize)

            when (data.theOrder) {
                in 1..19 -> {
                    text += textFromHtml(data.biblica, fontSize)
                    text += textLines(2, fontSize)
                    text += textSpan(data.getConclusio(), fontSize)
                }

                in 20..29 -> {
                    //text += textLines(2,fontSize)

                    text += transformText(data.biblica, fontSize, rubricColor)
                    //text += contentSpace(10)
                    text += textLines(2, fontSize)

                }

                in 30..39 -> {
                    text += textFromHtml(data.biblica, fontSize)
                    //text += textParagraph(data.getConclusio())
                    text += textLines(2, fontSize)
                    text += textSpan(data.getConclusio(), fontSize)

                }

                in 40..49 -> {
                    text += textFromHtml(data.biblica, fontSize)
                    text += textLines(2, fontSize)
                    text += textParagraph(data.getConclusio())
                }

                else -> {
                    text += textFromHtml(data.biblica, fontSize)
                    text += textLines(2, fontSize)
                    text += textParagraph(data.getConclusio())
                }
            }
        } else {
            text += textLines(2, fontSize)
            text += textFromHtml(data.biblica, fontSize)
            text += textLines(2, fontSize)
            text += textSpaced(listOf(data.getConclusio()), fontSize)
        }
        return text
    }
}

/**
 * Pantalla para las Homilías.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [Missae]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 */
//@ExperimentalStdlibApi
@ExperimentalMaterial3Api
@Composable
fun HomiliaeScreen(
    data: Missae,
    userData: UserData,
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    val rubricColor = getRubricColor(userData = userData.dynamic)
    val asb = AnnotatedString.Builder()
    var aString = AnnotatedString("")
    var final = asb.toAnnotatedString()
    data.homiliae!!.forEach {
        aString += homiliaeMetadata(it, fontSize, rubricColor)
        aString += homiliaeHtml(it)
    }

    Text(text = aString)

    /*TextZoomable(
        onTap = onTap, text = asb.toAnnotatedString()
    )*/
}

fun homiliaeHtml(data: Homily): AnnotatedString {
    return AnnotatedString.fromHtml(Utils.getFormato(data.homilia))
}


fun homiliaeMetadata(data: Homily, fontSize: TextUnit, rubricColor: Color): AnnotatedString {
    return try {
        buildAnnotatedString {
            append(contentTitleForHomily(data.paterOpus.paterForView, 3, rubricColor))
            append(data.paterOpus.singleName)
            if (data.tema.isNotEmpty() && data.date > 0) {
                append(Utils.LS2)
                append(textRubric(data.tema, rubricColor, fontSize))
                append(Utils.LS2)
                append(metaDate(data.date.toString(), fontSize, rubricColor))
                append(Utils.LS2)
            }
            if (data.tema.isNotEmpty() && data.date <= 0) {
                append(Utils.LS2)
                append(textRubric(data.tema, rubricColor, fontSize))
                append(Utils.LS2)
            }
            if (data.tema.isEmpty() && data.date > 0) {
                append(metaDate(data.date.toString(), fontSize, rubricColor))
                append(Utils.LS2)
            }
            if (data.tema.isEmpty() && data.date <= 0) {
                append(Utils.LS2)
            }
        }
    } catch (e: Exception) {
        buildAnnotatedString {
            append(Utils.createErrorMessage(e.message))
        }
    }
}

fun metaDate(data: String, fontSize: TextUnit, rubricColor: Color): AnnotatedString {
    return textRubric(
        Utils.formatDate(
            data,
            "yyyyMMdd",
            "EEEE d 'de' MMMM 'de' yyyy"
        ), rubricColor, fontSize
    )

}
/**
 * Prepara el contenido de la lista de homilías.
 *
 * @since 2025.1
 *
 * @param homiliaeList Lista de objetos [Homily]
 * @param rubricColor Color para las rúbricas según la configuración del usuario
 * @return Un [AnnotatedString] con todas las homilías
 */


/**
 * Pantalla para los Comentarios.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [Commentarii]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 */
@ExperimentalMaterial3Api
@Composable
fun CommentariiScreen(
    data: Commentarii,
    userData: UserData,
    onTap: (Offset) -> Unit,
    fontSize: TextUnit
) {
    //Text(data.forView(1).toString())
    val asb = AnnotatedString.Builder()
    val rubricColor = getRubricColor(userData.dynamic)
    var aString = AnnotatedString("")
    data.biblicaWithComments.forEach {
        if (it.homiliae.isNotEmpty()) {
            //asb.append(it.biblica.getAll(0))
            it.homiliae.forEach {
                aString += homiliaeMetadata(it!!, fontSize, rubricColor)
                aString += homiliaeHtml(it)
            }
            // asb.append(AnnotatedString.fromHtml(it!!.homilia).toString())
            //asb.append(homiliaeSimplex(it!!, rubricColor = rubricColor))
        }
    }

    TextZoomable(
        onTap = onTap, text = aString
    )
}

