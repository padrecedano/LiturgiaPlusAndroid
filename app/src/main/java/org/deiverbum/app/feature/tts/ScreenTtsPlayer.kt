package org.deiverbum.app.feature.tts

import LPlusIcons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.media3.common.util.UnstableApi
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.simplemediaservice.components.BottomPlayerUITts

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
