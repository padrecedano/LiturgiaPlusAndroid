package org.deiverbum.app.core.ui

import android.text.Spanned
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.core.text.parseAsHtml
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textParagraph
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textSpan
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
    onTap: (Offset) -> Unit
) {

    val rubricColor = getRubricColor(userData = userData.dynamic)
    val asb = AnnotatedString.Builder()
    Column {
        val onTap = { point: Offset -> }
        if (data.lectionumList != null) {
            data.lectionumList!!.sort()
            asb.append(lectionumList(data.lectionumList!!, userData.dynamic, rubricColor))
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
    rubricColor: Color
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
                        userData = userData,
                        rubricColor = rubricColor,
                        type = lectionumList.type
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
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(fontSize = NiaTypography.titleMedium.fontSize)
        ) {
            append(data.book.liturgyName)
        }
        append("    ")
        withStyle(
            SpanStyle(
                color = rubricColor,
                fontSize = NiaTypography.titleMedium.fontSize,
            )
        ) {
            append(data.pericopa)
        }
        append(Utils.LS2)
        if (data.tema != "") {
            withStyle(SpanStyle(color = rubricColor)) {
                append(data.tema)
            }
            append(Utils.LS2)
        }
    }
}

fun lectioSimplex(
    data: MissaeLectionum,
    type: Int,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    var text = AnnotatedString("")
    return buildAnnotatedString {
        if (type == 0) {
            text += contentTitle(data.getHeader(type), 2, userData, rubricColor)
            text += lectioMetadata(data, rubricColor)

            when (data.theOrder) {
                in 1..19 -> {
                    text += textFromHtml(data.biblica)
                    text += textSpan(data.getConclusio())
                }

                in 20..29 -> {
                    text += Utils.transformText(data.biblica, rubricColor)
                    text += contentSpace(10)
                }

                in 30..39 -> {
                    text += textFromHtml(data.biblica)
                    //text += textParagraph(data.getConclusio())
                    text += textSpan(data.getConclusio())

                }

                in 40..49 -> {
                    text += textFromHtml(data.biblica)
                    text += textParagraph(data.getConclusio())
                }

                else -> {
                    text += textFromHtml(data.biblica)
                    text += textParagraph(data.getConclusio())
                }
            }
        } else {
            text += textFromHtml(data.biblica)
            text += textSpaced(listOf(data.getConclusio()))
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
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData.dynamic)
    val asb = AnnotatedString.Builder()
    var aString = AnnotatedString("")
    var final = asb.toAnnotatedString()
    data.homiliae!!.forEach {
        aString += homiliaeMetadata(it, rubricColor)
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


fun homiliaeMetadata(data: Homily, rubricColor: Color): AnnotatedString {
    return try {
        buildAnnotatedString {
            append(contentTitleForHomily(data.paterOpus.paterForView, 3, rubricColor))
            append(data.paterOpus.singleName)
            if (data.tema.isNotEmpty() && data.date > 0) {
                append(Utils.LS2)
                append(textRubric(data.tema, rubricColor))
                append(Utils.LS2)
                append(metaDate(data.date.toString(), rubricColor))
                append(Utils.LS2)
            }
            if (data.tema.isNotEmpty() && data.date <= 0) {
                append(Utils.LS2)
                append(textRubric(data.tema, rubricColor))
                append(Utils.LS2)
            }
            if (data.tema.isEmpty() && data.date > 0) {
                append(metaDate(data.date.toString(), rubricColor))
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

fun metaDate(data: String, rubricColor: Color): AnnotatedString {
    return textRubric(
        Utils.formatDate(
            data,
            "yyyyMMdd",
            "EEEE d 'de' MMMM 'de' yyyy"
        ), rubricColor
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
//@ExperimentalStdlibApi
fun homiliaeList(
    homiliaeList: MutableList<Homily>?,
    userData: UserDataDynamic,
    rubricColor: Color
): AnnotatedString {
    val asb = AnnotatedString.Builder()

    return try {
        buildAnnotatedString {
            append(homiliaeSimplex(homiliaeList!![1], userData, rubricColor))

            for (item in homiliaeList) {
                //append(homiliaeSimplex(item, rubricColor))
            }
        }
    } catch (e: Exception) {
        buildAnnotatedString {
            append(Utils.createErrorMessage(e.message))
        }
    }
}

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
    onTap: (Offset) -> Unit
) {
    //Text(data.forView(1).toString())
    val asb = AnnotatedString.Builder()
    val rubricColor = getRubricColor(userData.dynamic)
    var aString = AnnotatedString("")
    data.biblicaWithComments.forEach {
        if (it.homiliae.isNotEmpty()) {
            //asb.append(it.biblica.getAll(0))
            it.homiliae.forEach {
                aString += homiliaeMetadata(it!!, rubricColor)
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

fun homiliaeSimplex(data: Homily, userData: UserDataDynamic, rubricColor: Color): AnnotatedString {
    return try {
        buildAnnotatedString {
            append(contentTitle(data.paterOpus.paterForView, 3, userData, rubricColor))
            append(data.paterOpus.singleName)
            if (data.tema !== "") {
                append(Utils.LS2)
                append(textRubric(data.tema, rubricColor))
            }
            if (data.date > 0) {
                append(Utils.LS2)
                append(
                    textRubric(
                        Utils.formatDate(
                            data.date.toString(),
                            "yyyyMMdd",
                            "EEEE d 'de' MMMM 'de' yyyy"
                        ), rubricColor
                    )
                )
            }
            //append(Utils.LS2)
            //append(fromHtml(data.homilia))

            val target: Spanned = data.homilia.parseAsHtml()

            appendStyledContent(data.homilia, true, rubricColor)

        }
    } catch (e: Exception) {
        buildAnnotatedString {
            append(Utils.createErrorMessage(e.message))
        }
    }
}