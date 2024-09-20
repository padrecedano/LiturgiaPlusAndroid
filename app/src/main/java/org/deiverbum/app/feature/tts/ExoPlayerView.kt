package org.deiverbum.app.feature.tts

import android.os.Looper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import org.deiverbum.app.util.Constants
import timber.log.Timber

/**
 * Composable function that displays an ExoPlayer to play a video using Jetpack Compose.
 *
 * @OptIn annotation to UnstableApi is used to indicate that the API is still experimental and may
 * undergo changes in the future.
 *
 * @see EXAMPLE_VIDEO_URI Replace with the actual URI of the video to be played.
 */
@UnstableApi
@Composable
fun ExoPlayerView(text: StringBuilder) {
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

    DisposableEffect(Unit) {
        onDispose {
            ttsPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                Timber.d("aaa", ttsPlayer.contentDuration)
                //ttsPlayer.contentDuration=3000
                player = ttsPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}