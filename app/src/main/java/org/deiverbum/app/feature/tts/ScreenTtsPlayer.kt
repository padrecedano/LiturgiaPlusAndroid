package org.deiverbum.app.feature.tts

import LPlusIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.component.media.PlayerBarTts
import org.deiverbum.app.core.designsystem.component.media.PlayerControlsTts

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ScreenTtsPlayer(
    vm: TtsMediaViewModel,
    text: StringBuilder
) {
    BottomPlayerUITts(
        durationString = vm.formatDuration(vm.duration),
        text = text,
        playResourceProvider = {
            if (vm.isPlaying) Icons.Rounded.PauseCircle
            else Icons.Rounded.PlayCircle
        },
        progressProvider = { Pair(vm.progress, vm.progressString) },
        onUiEvent = vm::onUIEvent
    )

    NiaIconToggleButton(
        checked = vm.isPlaying,
        onCheckedChange = {},
        icon = {
            Icon(
                imageVector = LPlusIcons.Reader,
                contentDescription = "core_ui_interests_card_follow_button_content_desc",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = LPlusIcons.Pause,
                contentDescription = "core_ui_interests_card_unfollow_button_content_desc",

                )
        },
    )
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
