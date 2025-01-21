package org.deiverbum.app.feature.tts

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun PlayerControls(player: ExoPlayer?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { player?.playWhenReady = true }) {
            Text("Play")
        }
        Button(onClick = { player?.playWhenReady = false }) {
            Text("Pause")
        }

        Button(onClick = {
            player?.seekTo(player.currentPosition - 10_000) // Seek backward 10 seconds
        }) {
            Text("Seek -10s")
        }
        Button(onClick = {
            player?.seekTo(player.currentPosition + 10_000) // Seek forward 10 seconds
        }) {
            Text("Seek +10s")
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun PlayerControlss(player: TtsPlayerCompose) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            player.playWhenReady = true
        }) {
            Text("Play")
        }
        Button(onClick = { player.playWhenReady = false }) {
            Text("Pause")
        }



        Button(onClick = {
            player.close() // Seek backward 10 seconds
        }) {
            Text("Close")
        }
        Button(onClick = {
            player.playWhenReady = true
            //player.resume() // Seek backward 10 seconds
        }) {
            Text("Resume")
        }
        Button(onClick = {
            player.seekTo(player.currentPosition + 10_000) // Seek forward 10 seconds
        }) {
            Text("Seek +10s")
        }
    }
}