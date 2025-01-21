package org.deiverbum.app.feature.tts

import android.os.Looper
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import org.deiverbum.app.util.Constants

@Composable
fun Media3PlayerView(videoUrl: String, playerViewModel: PlayerViewModel = viewModel()) {

    val context = LocalContext.current
    val player by playerViewModel.playerState.collectAsState()



    LaunchedEffect(videoUrl) {
        playerViewModel.initializePlayer(context, videoUrl)
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewModel.savePlayerState()
            playerViewModel.releasePlayer()
        }
    }

    Column {
        Media3AndroidView(player)
        PlayerControls(player)
    }

}

@UnstableApi
@Composable
fun ExoPlayerVieww(text: StringBuilder, playerViewModel: PlayerViewModel = viewModel()) {
    val context = LocalContext.current
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    val player by playerViewModel.playerState.collectAsState()

    // Observe the player setup state
    val isPlayerSetUp by playerViewModel.isPlayerSetUp.collectAsStateWithLifecycle()

    // Get the managed MediaController
    //val mediaController by rememberManagedMediaController()
    val ttsPlayerr = TtsExoPlayer(
        Looper.getMainLooper(),
        context,
        text,
        Constants.SEPARADOR
    ) { current: Int, max: Int ->
        sliderMaxValue = max.toFloat()
    }

    val ttsPlayer = TtsPlayerCompose(
        Looper.getMainLooper(),
        context,
        text,
        Constants.SEPARADOR
    ) { current: Int, max: Int ->
        sliderMaxValue = max.toFloat()
    }
    LaunchedEffect(text) {
        //playerViewModel.initializePlayerr(context, text)
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewModel.savePlayerState()
            playerViewModel.releasePlayer()
        }
    }

    Column {
        Media3TtsView(ttsPlayerr)

        //Media3AndroidVieww(ttsPlayer)
        //PlayerControlss(ttsPlayer)
    }
}

@Composable
fun Media3AndroidView(player: ExoPlayer?) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            PlayerView(context).apply {
                this.player = player
            }
        },
        update = { playerView ->
            playerView.player = player
        }
    )
}


@OptIn(UnstableApi::class)
@Composable
fun Media3TtsView(player: TtsExoPlayer) {
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