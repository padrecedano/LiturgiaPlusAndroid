package org.deiverbum.app.simplemediaservice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.deiverbum.app.feature.tts.UIEvent
import org.deiverbum.app.feature.tts.UIEventTts

@Composable
fun BottomPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Column(
        modifier = modifier
            //.fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        PlayerBar(
            progress = progress,
            durationString = durationString,
            progressString = progressString,
            onUiEvent = onUiEvent
        )
        PlayerControls(
            playResourceProvider = playResourceProvider,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun BottomPlayerUITts(
    modifier: Modifier = Modifier,
    durationString: String,
    text: StringBuilder,
    playResourceProvider: () -> ImageVector,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (UIEventTts) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Column(
        modifier = modifier
            //.fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        PlayerBarTts(
            progress = progress,
            durationString = durationString,
            progressString = progressString,
            onUiEvent = onUiEvent
        )
        PlayerControlsTts(
            playResourceProvider = playResourceProvider,
            onUiEvent = onUiEvent
        )
    }
}