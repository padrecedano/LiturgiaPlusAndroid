package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.component.TextLeftRight
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textFromHtmlWithMarks
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textusRubrica
import org.deiverbum.app.core.designsystem.component.transformBodyText
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.missae.MissaeLectionum
import org.deiverbum.app.core.model.data.missae.MissaeLectionumList
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.data.traditio.Homily
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


//@OptIn(ExperimentalStdlibApi::class)
//@ExperimentalMaterial3Api
@Composable
fun MissaeLectionumContent(
    data: Missae,
    userData: UserData,
    fontSize: TextUnit
) {
    val rubricColor = MaterialTheme.colorScheme.error
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge
    val text = buildAnnotatedString {
        if (data.lectionumList != null) {
            data.lectionumList!!.sort()
            append(
                lectionumList(
                    data.lectionumList!!,
                    userData.dynamic,
                    rubricColor,
                    bodyStyle
                )
            )
        }
    }
    Text(text = text, style = bodyStyle)
}

@Composable
///@ExperimentalStdlibApi
fun lectionumList(
    lectionumList: MissaeLectionumList,
    userData: UserDataDynamic,
    rubricColor: Color,
    style: TextStyle
): AnnotatedString {
    return buildAnnotatedString {
        if (lectionumList.type == -1) {
            append(contentSpace(2))
            withStyle(
                SpanStyle(
                    color = rubricColor
                )
            ) {
                append(Constants.TITLE_MASS_GOSPEL)
            }
        }
        lectionumList.lectionum.forEach {
            lectioSimplex(
                data = it!!,
                type = lectionumList.type,
                userData = userData,
                style = style
            )
        }
    }
}

@Composable
fun lectioMetadata(
    data: MissaeLectionum,
    style: TextStyle
) {
    TextLeftRight(
        texts = listOf(data.book.liturgyName, data.pericopa),
        style = style,
        colorCode = 3
    )
    Text(
        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        text = textusRubrica(data.tema),
        style = style
    )
}

@Composable
fun lectioSimplex(
    data: MissaeLectionum,
    type: Int,
    userData: UserDataDynamic,
    style: TextStyle
) {
    val text = buildAnnotatedString {
        if (type == 0) {
            ContentTitle(data.getHeader(type), 2, userData)
            lectioMetadata(data, style)
            when (data.theOrder) {
                !in 20..29 -> {
                    //append("\n\n")
                    append(textFromHtmlWithMarks(data.biblica).trim())
                    append("\n\n")
                    append(data.getConclusio())
                }
                else -> {
                    append(transformBodyText(data.biblica).trim())
                    //append("\n\n")
                }
            }
        } else {
            //append("\n")
            append(textFromHtmlWithMarks(data.biblica).trim())
            //append("\n\n")
            append(data.getConclusio())
        }
    }
    Text(text = text, style = style)
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


@Composable
fun homiliaeMetadata(data: Homily, fontSize: TextUnit, rubricColor: Color): AnnotatedString {
    return buildAnnotatedString {
        append(contentTitleForHomily(data.paterOpus.paterForView, 3, rubricColor))
        append(data.paterOpus.singleName)
        if (data.tema.isNotEmpty() && data.date > 0) {
            append(Utils.LS2)
            append(textRubric(data.tema))
            append(Utils.LS2)
            append(metaDate(data.date.toString(), fontSize, rubricColor))
            append(Utils.LS2)
        }
        if (data.tema.isNotEmpty() && data.date <= 0) {
            append(Utils.LS2)
            append(textRubric(data.tema))
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
}

@Composable
fun metaDate(data: String, fontSize: TextUnit, rubricColor: Color): AnnotatedString {
    return textRubric(
        Utils.formatDate(
            data,
            "yyyyMMdd",
            "EEEE d 'de' MMMM 'de' yyyy"
        )
    )

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

