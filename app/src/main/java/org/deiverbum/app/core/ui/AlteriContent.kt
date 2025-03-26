package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.TextSmall
import org.deiverbum.app.core.designsystem.component.TextSpaced
import org.deiverbum.app.core.designsystem.component.TextVR
import org.deiverbum.app.core.designsystem.component.getRubricColor
import org.deiverbum.app.core.designsystem.component.textFromList
import org.deiverbum.app.core.designsystem.component.textIndent
import org.deiverbum.app.core.designsystem.component.textSpaced
import org.deiverbum.app.core.designsystem.theme.getPersonalizedTypography
import org.deiverbum.app.core.model.alteri.AlteriRosarium
import org.deiverbum.app.core.model.alteri.AlteriSanctii
import org.deiverbum.app.core.model.alteri.Rosarium
import org.deiverbum.app.core.model.alteri.RosariumMysteriumOrdo
import org.deiverbum.app.core.model.configuration.RosariumConfig
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.liturgia.Introitus
import org.deiverbum.app.core.model.liturgia.PadreNuestro
import org.deiverbum.app.core.model.universalis.MetaData
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.firstUpper
import org.deiverbum.app.util.marksAndHtml

/**
 * Pantalla para los Santos.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [org.deiverbum.app.core.model.alteri.anctii]
 * @param userData Objeto [UserData] con las preferencias del usuario
 */

@Composable
fun SanctiiContent(
    data: AlteriSanctii,
    userData: UserData,
    metaData: MetaData,
) {
    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge

    SectionTitlee(
        data = metaData.nomen,
        level = 2,
        userData = userData,
        lower = false
    )

    ContentHeadd(
        text = metaData.tempus,
        level = 3,
        userData = userData,
        uppercase = false
    )

    val martyrologyum = buildAnnotatedString {
        append(AnnotatedString.fromHtml(data.sanctus.martyrologyum))
        append("\n")
        append("(Martirologio Romano)")
    }
    TextSmall(
        text = martyrologyum,
        userData = userData,
        style = bodyStyle
    )
    ContentTitle("Vida", 1, userData)
    Text(
        text = AnnotatedString.fromHtml(data.sanctus.vita.marksAndHtml()),
        style = bodyStyle
    )
}


/**
 * Pantalla para el Santo Rosario de la fecha dada.
 *
 * @since 2025.1
 *
 * @param data Objeto del tipo [org.deiverbum.app.core.model.alteri.osarium]
 * @param userData Objeto [UserData] con las preferencias del usuario
 */

@Composable
fun RosariumContent(
    data: AlteriRosarium,
    userData: UserData,
    metaData: MetaData
) {

    val typography = getPersonalizedTypography(userData.dynamic.fontSize)
    val bodyStyle: TextStyle = typography.bodyLarge
    val rubricColor = getRubricColor(userData = userData.dynamic)

    ContentHeadd(
        text = metaData.tempus,
        level = 2,
        userData = userData,
        uppercase = false
    )
    ContentHeadd(
        text = metaData.nomen.firstUpper(),
        level = 3,
        userData = userData,
        uppercase = false
    )

    ContentTitle(
        data = "Misterios ${data.rosarium.series.series}",
        level = 1,
        userData = userData
    )
    ContentTitle(
        data = Constants.TITLE_INITIAL_INVOCATION,
        level = 2,
        userData = userData
    )

    TextVR(
        texts = listOf(Introitus().txtInNomine, Introitus().txtAmen),
        style = bodyStyle
    )
    TextVR(texts = Introitus().altera, style = bodyStyle)

    var isBrevis = when (userData.dynamic.useMysteriumBrevis) {
        RosariumConfig.ON -> true
        RosariumConfig.OFF -> false
    }
    var paterNoster = when (isBrevis) {
        true -> buildAnnotatedString { append("Padre Nuestro ...") }
        false -> buildAnnotatedString {
            append("\n")
            append(AnnotatedString.fromHtml(PadreNuestro.texto.marksAndHtml()))
        }
    }
    var aveMaria = when (isBrevis) {
        true -> buildAnnotatedString { append("10 Ave María ...") }
        false -> AnnotatedString.fromHtml(data.rosarium.aveMaria.marksAndHtml())
    }
    var gloria = when (isBrevis) {
        true -> buildAnnotatedString { append("Gloria al Padre ...") }
        false -> textSpaced(LiturgyHelper.finisPsalmus)
    }
    data.rosarium.mysteriorum.forEach {
        HorizontalDivider(
            thickness = 1.dp, modifier = Modifier
                .padding(vertical = 5.dp)
        )
        ContentTitle(
            data = RosariumMysteriumOrdo.ORDINAL[it.ordo - 1],
            level = 2,
            userData = userData
        )
        ContentHeadd(
            text = it.mysterium.mysterium,
            level = 3,
            userData = userData,
            uppercase = false,
            withColor = false
        )
        HorizontalDivider(
            thickness = 1.dp, modifier = Modifier
                .padding(vertical = 5.dp)
        )

        if (!isBrevis) {
            Text(text = paterNoster, style = bodyStyle)
            repeat(10) { index ->
                ContentTitle(
                    (index + 1).toString(),
                    3,
                    userData
                )
                Text(
                    text = aveMaria,
                    style = bodyStyle
                )
            }
            Text(text = gloria, style = bodyStyle)

        } else {
            TextSpaced(text = paterNoster, style = bodyStyle)
            TextSpaced(text = aveMaria, style = bodyStyle)
            TextSpaced(text = gloria, style = bodyStyle)
        }
    }

    ContentTitle(
        data = stringResource(id = R.string.title_litanies),
        level = 2,
        userData = userData
    )
    val litanie = buildAnnotatedString {
        Rosarium.LITANIAE.forEach {
            append(textIndent(it.textus, it.responsum, rubricColor))
            append(contentSpace(4))
        }
    }
    Text(text = litanie, style = bodyStyle)

    ContentTitle(
        data = stringResource(id = R.string.title_salve),
        level = 2,
        userData = userData
    )
    Text(text = textFromList(Rosarium.salve.textus), style = bodyStyle)
    ContentTitle(
        data = stringResource(id = R.string.title_prayer),
        level = 2,
        userData = userData
    )
    Text(text = textFromList(Rosarium.oratio.textus), style = bodyStyle)
}
