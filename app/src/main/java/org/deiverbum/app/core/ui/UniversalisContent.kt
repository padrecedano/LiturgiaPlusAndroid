package org.deiverbum.app.core.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.launch
import org.deiverbum.app.core.designsystem.component.PlayButton
import org.deiverbum.app.core.designsystem.component.TextZoomable
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.Sortable
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.feature.tts.TextToSpeechScreen
import org.deiverbum.app.feature.tts.TextToSpeechScreenn
import org.deiverbum.app.feature.universalis.CompletoriumScreen
import org.deiverbum.app.feature.universalis.IntermediaScreen
import org.deiverbum.app.feature.universalis.LaudesScreen
import org.deiverbum.app.feature.universalis.MissaeLectionumScreen
import org.deiverbum.app.feature.universalis.MixtusScreen
import org.deiverbum.app.feature.universalis.OfficiumScreen
import org.deiverbum.app.feature.universalis.VesperasScreen

@OptIn(UnstableApi::class)
@ExperimentalMaterial3Api
@Composable
fun Universalis(
    data: Universalis,
    topicId: Int,
    userData: UserDataDynamic,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val timeViewState by remember {
        mutableStateOf(false)
    }

    //TODO: Limpiar
    val itemId = topicId
    val onTap = { point: Offset -> }

    if (data.liturgia!!.liturgiaTypus is Sortable) {
        data.liturgia!!.liturgiaTypus?.sort()
    }

    val sb = StringBuilder()

    sb.append(data.getAllForRead())

    if (userData.useVoiceReader || 1 == 1) {
        if (1 == 2) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

            TextToSpeechScreen()
                TextToSpeechScreenn(text = sb)
                PlayButton(isBookmarked = true, onClick = { /*TODO*/ })
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                }
            }
        }

        if (timeViewState) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                TextToSpeechScreen()
            }
        }

    }
    when (itemId) {
        1 -> MixtusScreen(
            data = data.liturgia!!.liturgiaTypus as LHMixtus,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        2 -> OfficiumScreen(
            data = data.liturgia!!.liturgiaTypus as LHOfficium,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        3 -> LaudesScreen(
            data = data.liturgia!!.liturgiaTypus as LHLaudes,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        4, 5, 6 -> IntermediaScreen(
            data = data.liturgia!!.liturgiaTypus as LHIntermedia,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        7 -> VesperasScreen(
            data = data.liturgia!!.liturgiaTypus as LHVesperas,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        8 -> CompletoriumScreen(
            data = data.liturgia!!.liturgiaTypus as LHCompletorium,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        11 -> MissaeLectionumScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        12 -> CommentariiScreen(
            data = data.liturgia!!.liturgiaTypus as Commentarii,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        13 -> HomiliaeScreen(
            data = data.liturgia!!.liturgiaTypus as Missae,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )

        20 -> SanctiiScreen(
            data = data.liturgia!!.liturgiaTypus as Alteri.Sancti,
            hourId = itemId,
            calendarTime = data.timeFK,
            userData = userData,
            onTap = onTap
        )
    }
}


@ExperimentalMaterial3Api
@Composable
fun CommentariiScreen(
    data: Commentarii,
    hourId: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    //Text(data.forView(1).toString())
    TextZoomable(
        onTap = onTap, text = buildAnnotatedString { append(data.forView(1)) }
    )
}

@ExperimentalMaterial3Api
@Composable
fun HomiliaeScreen(
    data: Missae,
    hourId: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    //Text(data.forView(1).toString())
    TextZoomable(
        onTap = onTap, text = buildAnnotatedString {
            append(data.forView(1))
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun SanctiiScreen(
    data: Alteri.Sancti,
    hourId: Int,
    calendarTime: Int,
    userData: UserDataDynamic,
    onTap: (Offset) -> Unit
) {
    //Text(data.sanctus.forView.toString())

    TextZoomable(
        onTap = onTap, text = buildAnnotatedString { append(data.sanctus.forView) }
    )

}

@Composable
fun ContentTitle(text: String, level: Int, rubricColor: Color) {
    val textStyle: TextStyle = when (level) {
        1 -> NiaTypography.titleLarge
        2 -> NiaTypography.titleMedium
        3 -> NiaTypography.titleSmall
        else -> NiaTypography.bodyLarge
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = text.uppercase(), style = textStyle, color = rubricColor)
    Spacer(modifier = Modifier.height(16.dp))
}

fun contentTitle(text: String, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(text.uppercase())
            }
        }
    }
}

fun contentTitleAndText(texts: List<String>, level: Int, rubricColor: Color): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    color = rubricColor
                )
            ) {
                append(texts[0].uppercase())
            }
            withStyle(
                SpanStyle(
                    color = rubricColor
                )
            ) {
                append("\t")
                append(texts[1])
            }
        }
    }
}

fun sectionTitle(text: String, level: Int): AnnotatedString {
    val paragraphStyle = ParagraphStyle(lineHeight = 60.sp)
    val fontSize: TextUnit = when (level) {
        1 -> NiaTypography.titleLarge.fontSize
        2 -> NiaTypography.titleMedium.fontSize
        3 -> NiaTypography.titleSmall.fontSize
        else -> NiaTypography.bodyLarge.fontSize
    }
    return buildAnnotatedString {
        withStyle(style = paragraphStyle) {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                )
            ) {
                append(text.lowercase())
            }
        }
    }
}


@Composable
fun SpaceNormal() {
    Spacer(modifier = Modifier.height(24.dp))
}