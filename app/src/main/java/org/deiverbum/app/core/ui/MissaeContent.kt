package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.component.TextLeftRight
import org.deiverbum.app.core.designsystem.component.textFromHtmlWithMarks
import org.deiverbum.app.core.designsystem.component.textRubric
import org.deiverbum.app.core.designsystem.component.textusRubrica
import org.deiverbum.app.core.designsystem.component.transformBodyText
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.UserDataDynamic
import org.deiverbum.app.core.model.data.missae.Missae
import org.deiverbum.app.core.model.data.missae.MissaeLectionum
import org.deiverbum.app.core.model.data.missae.MissaeLectionumList
import org.deiverbum.app.core.model.data.traditio.Commentarii
import org.deiverbum.app.core.model.data.traditio.Homily
import org.deiverbum.app.util.Constants.TITLE_COMMENTARII
import org.deiverbum.app.util.Constants.TITLE_GOSPEL
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.marksAndHtml

/**
 * Pantallas para las lecturas de la Misa.
 *
 * @author A. Cedano
 * @since 2025.1
 *
 * @param data Objeto del tipo [MissaeContent]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 *
 * @see [MissaeContent]
 * @see [MissaeLectionum]
 */

@Composable
fun MissaeContent(
    data: Missae,
    typus: Int,
    userData: UserData
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge
    when (typus) {
        11 -> LectionumList(
            data.lectionumList!!,
            userData,
            bodyStyle
        )

        13 -> Homiliae(data, userData, bodyStyle)
    }
}

@Composable
///@ExperimentalStdlibApi
fun LectionumList(
    lectionumList: MissaeLectionumList,
    userData: UserData,
    style: TextStyle
) {
    lectionumList.sort()
        if (lectionumList.type == -1) {
            ContentTitle(TITLE_GOSPEL, 2, userData)
        }
        lectionumList.lectionum.forEach {
            LectioSimplex(
                data = it!!,
                type = lectionumList.type,
                userData = userData,
                style = style
            )
    }
}

@Composable
fun LectioMetadata(
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
fun LectioSimplex(
    data: MissaeLectionum,
    type: Int,
    userData: UserData,
    style: TextStyle
) {
    val text = buildAnnotatedString {
        if (type == 0) {
            ContentTitle(data.getHeader(type), 2, userData)
            LectioMetadata(data, style)
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
 * @param data Objeto del tipo [MissaeContent]
 * @param userData Objeto [UserDataDynamic] con las preferencias del usuario
 */
//@ExperimentalStdlibApi
//@ExperimentalMaterial3Api
@Composable
fun Homiliae(
    data: Missae,
    userData: UserData,
    bodyStyle: TextStyle
) {
    data.homiliae!!.forEach {
        HomiliaeMetadata(it, userData, bodyStyle)
        Text(text = AnnotatedString.fromHtml(it.homilia.marksAndHtml().trim()), style = bodyStyle)
    }
}


@Composable
fun HomiliaeMetadata(data: Homily, userData: UserData, style: TextStyle) {
    ContentTitle(
        data.paterOpus.paterForView,
        3,
        userData
    )
    Text(text = data.paterOpus.singleName, style = style)
    buildAnnotatedString {
        //append(contentTitleForHomily(data.paterOpus.paterForView, 3, rubricColor))

        append(data.paterOpus.singleName)
        if (data.tema.isNotEmpty() && data.date > 0) {
            append(Utils.LS2)
            Text(text = textusRubrica(data.tema), style = style)
            append(textRubric(data.tema))
            append(Utils.LS2)
            MetaDate(data.date, style)
            append(Utils.LS2)
        }
        if (data.tema.isNotEmpty() && data.date <= 0) {
            append(Utils.LS2)
            append(textRubric(data.tema))
            Text(text = textusRubrica(data.tema), style = style)

            append(Utils.LS2)
        }
        if (data.tema.isEmpty() && data.date > 0) {
            //append(metaDate(data.date.toString(), fontSize, rubricColor))
            append(Utils.LS2)
        }
        if (data.tema.isEmpty() && data.date <= 0) {
            append(Utils.LS2)
        }
    }
}

@Composable
fun MetaDate(data: Int, style: TextStyle) {

    Text(
        text = textusRubrica(
            Utils.formatDate(
                data.toString(),
                "yyyyMMdd",
                "EEEE d 'de' MMMM 'de' yyyy"
            )
        ),
        style = style
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

@Composable
fun CommentariiContent(
    data: Commentarii,
    userData: UserData
    //onTap: (Offset) -> Unit,
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    HorizontalDivider(
        thickness = 1.dp, modifier = Modifier
            .padding(vertical = 5.dp)
    )
    data.biblicaWithComments.forEach { it ->
        if (it.homiliae.size > 0) {
            LectioSimplex(it.biblica, 0, userData, bodyStyle)
        }

        if (it.homiliae.isNotEmpty()) {
            HorizontalDivider(
                thickness = 1.dp, modifier = Modifier
                    .padding(vertical = 5.dp)
            )
            ContentHeadd(
                text = TITLE_COMMENTARII,
                level = 2,
                userData = userData,
                uppercase = false,
                withColor = true
            )

            it.homiliae.forEach {
                HomiliaeMetadata(it!!, userData, bodyStyle)
                Text(
                    text = AnnotatedString.fromHtml(it.homilia.marksAndHtml().trim()),
                    style = bodyStyle
                )
            }
        }
    }
}


