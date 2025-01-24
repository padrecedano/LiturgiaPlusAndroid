package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.Homily
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.getFormato

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
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {

    val rubricColor = getRubricColor(userData = userData)
    val asb = AnnotatedString.Builder()
    Column {
        val onTap = { point: Offset -> }
        if (data.lectionumList != null) {
            data.lectionumList!!.sort()
            asb.append(lectionumList(data.lectionumList!!, rubricColor))
            TextZoomable(
                onTap = onTap,
                text = asb.toAnnotatedString()
            )
        }
    }
}

private fun AnnotatedString.Builder.appendStyledContent(
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
fun lectionumList(lectionumList: MissaeLectionumList, rubricColor: Color): AnnotatedString {
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
    rubricColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        if (type == 0) {
            withStyle(
                SpanStyle(
                    fontSize = NiaTypography.titleMedium.fontSize,
                    color = rubricColor
                )
            ) {
                append(data.getHeader(type))
            }
            append(Utils.LS2)
            append(lectioMetadata(data, rubricColor))
            when (data.theOrder) {
                in 20..29 -> {
                    append(Utils.transformText(data.biblica, rubricColor))
                    append(Utils.LS)
                }

                else -> appendStyledContent(
                    getFormato(data.biblica, rubricColor),
                    true,
                    rubricColor
                )
            }
            append(Utils.LS2)
        }
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
@ExperimentalStdlibApi
@ExperimentalMaterial3Api
@Composable
fun HomiliaeScreen(
    data: Missae,
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData)
    val asb = AnnotatedString.Builder()
    asb.append(homiliaeList(data.homiliae, rubricColor = rubricColor))
    TextZoomable(
        onTap = onTap, text = asb.toAnnotatedString()
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
@ExperimentalStdlibApi
fun homiliaeList(
    homiliaeList: MutableList<Homily>?,
    rubricColor: Color
): AnnotatedString {
    val asb = AnnotatedString.Builder()
    for (item in homiliaeList!!) {
        asb.append(contentTitle(item.paterOpus.pater!!.liturgyName, 3, rubricColor))
        asb.append(item.paterOpus.singleName)
        asb.append(Utils.LS2)
        asb.append(textRubric(item.tema, rubricColor))
        if (item.date > 0) {
            asb.append(Utils.LS2)
            asb.append(
                textRubric(
                    Utils.formatDate(
                        item.date.toString(),
                        "yyyyMMdd",
                        "EEEE d 'de' MMMM 'de' yyyy"
                    ), rubricColor
                )
            )
        }
        asb.append(Utils.LS2)
        //asb.append(annotatedStringFromHtml(item.homilia))
    }
    return asb.toAnnotatedString()
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
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    //Text(data.forView(1).toString())
    TextZoomable(
        onTap = onTap, text = buildAnnotatedString { append(data.forView(1)) }
    )
}