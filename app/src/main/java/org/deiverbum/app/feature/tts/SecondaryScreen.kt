package org.deiverbum.app.feature.tts

import android.os.Looper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import org.deiverbum.app.simplemediaservice.components.BottomPlayerUI
import org.deiverbum.app.util.Constants


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ScreenTts(
    vm: SimpleMediaViewModel,
    text: StringBuilder
) {
    val context = LocalContext.current
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }

    val ttsPlayer = TtsPlayerCompose(
        Looper.getMainLooper(),
        context,
        text,
        Constants.SEPARADOR
    ) { current: Int, max: Int ->
        sliderMaxValue = max.toFloat()
    }

    Media3AndroidVieww(ttsPlayer)
    BottomPlayerUI(
        durationString = vm.formatDuration(vm.duration),
        //text=text,
        playResourceProvider = {
            if (vm.isPlaying) android.R.drawable.ic_media_pause
            else android.R.drawable.ic_media_play
        },
        progressProvider = { Pair(vm.progress, vm.progressString) },
        onUiEvent = vm::onUIEvent
    )

}


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Media3AndroidVieww(player: TtsPlayerCompose) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
                setShowSubtitleButton(true)
            }
        },

        update = { playerView ->
            playerView.player = player
        }

    )
}