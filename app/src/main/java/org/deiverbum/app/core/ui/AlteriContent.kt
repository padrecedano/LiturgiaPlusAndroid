package org.deiverbum.app.core.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textFromList
import org.deiverbum.app.core.designsystem.component.textIndent
import org.deiverbum.app.core.designsystem.component.textSmall
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.component.textVR
import org.deiverbum.app.core.model.data.AlteriRosarium
import org.deiverbum.app.core.model.data.AlteriSanctii
import org.deiverbum.app.core.model.data.Introitus
import org.deiverbum.app.core.model.data.PadreNuestro
import org.deiverbum.app.core.model.data.Rosarium
import org.deiverbum.app.core.model.data.RosariumMysteriumOrdo
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper

/**
 * Pantalla para los Santos.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [org.deiverbum.app.core.model.data.AlteriSanctii]
 * @param userData Objeto [UserData] con las preferencias del usuario
 */
@ExperimentalMaterial3Api
@Composable
fun SanctiiScreen(
    data: AlteriSanctii,
    userData: UserData,
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData.dynamic)
    var text = AnnotatedString("")
    text += textSmall(data.sanctus.martyrologyum, userData.dynamic)
    text += contentSpace(8)
    text += textSmall("(Martirologio Romano)", userData.dynamic)
    text += contentTitle("Vida", 1, userData.dynamic, rubricColor)
    text += textFromHtml(
        data.sanctus.vita.replace(
            Constants.OLD_SEPARATOR.toRegex(), ""
        )
    )

    TextZoomable(
        onTap = onTap, text = text
    )
}


/**
 * Pantalla para el Santo Rosario de la fecha dada.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [org.deiverbum.app.core.model.data.AlteriRosarium]
 * @param userData Objeto [UserData] con las preferencias del usuario
 */
@ExperimentalMaterial3Api
@Composable
fun RosariumScreen(
    data: AlteriRosarium,
    userData: UserData,
    onTap: (Offset) -> Unit
) {
    val rubricColor = getRubricColor(userData = userData.dynamic)
    var text = AnnotatedString("")
    text += contentTitle(
        "Misterios ${data.rosarium.series.series}",
        1,
        userData.dynamic,
        rubricColor
    )
    text += contentTitle(Constants.TITLE_INITIAL_INVOCATION, 2, userData.dynamic, rubricColor)
    text += textVR(texts = listOf(Introitus().txtInNomine, Introitus().txtAmen), rubricColor)
    text += contentSpace(10)
    text += textVR(texts = Introitus().minor, rubricColor)
    text += contentSpace(10)
    data.rosarium.mysteriorum.forEach {
        text += contentTitle(
            RosariumMysteriumOrdo.ORDINAL[it.ordo - 1],
            2,
            userData.dynamic,
            rubricColor,
            true
        )
        text += sectionTitle(it.mysterium.mysterium, 1, userData.dynamic, false)
        text += textFromHtml(PadreNuestro.texto)
        text += contentSpace(10)
        repeat(10) { index ->
            text += contentTitle(
                (index + 1).toString(),
                3,
                userData.dynamic,
                rubricColor,
                false
            )
            text += textFromHtml(data.rosarium.aveMaria)
        }
        text += contentSpace(10)
        text += textSpaced(LiturgyHelper.finisPsalmus)
        text += contentSpace(10)
    }
    text += contentTitle(
        stringResource(R.string.title_litanies),
        2,
        userData.dynamic,
        rubricColor
    )
    Rosarium.LITANIAE.forEach {
        text += textIndent(it.textus, it.responsum, rubricColor)
        text += contentSpace(4)
    }
    text += contentTitle(stringResource(R.string.title_salve), 2, userData.dynamic, rubricColor)
    text += textFromList(Rosarium.salve.textus)
    text += contentSpace(10)
    text += contentTitle(stringResource(R.string.title_prayer), 2, userData.dynamic, rubricColor)
    text += textFromList(Rosarium.oratio.textus)

    TextZoomable(
        onTap = onTap, text = text
    )
}
