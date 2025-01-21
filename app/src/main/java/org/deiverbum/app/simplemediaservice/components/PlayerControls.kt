package org.deiverbum.app.simplemediaservice.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.deiverbum.app.feature.tts.UIEvent
import org.deiverbum.app.feature.tts.UIEventTts

@Composable
internal fun PlayerControls(
    playResourceProvider: () -> Int,
    onUiEvent: (UIEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(android.R.drawable.ic_media_rew),
            contentDescription = "Backward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.Backward) })
                .padding(12.dp)
                .size(34.dp)
        )
        Image(
            painter = painterResource(id = playResourceProvider()),
            contentDescription = "Play/Pause Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.PlayPause) })
                .padding(8.dp)
                .size(56.dp)
        )
        Icon(
            painter = painterResource(android.R.drawable.ic_media_ff),
            contentDescription = "Forward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEvent.Forward) })
                .padding(12.dp)
                .size(34.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerControlsTts(
    playResourceProvider: () -> ImageVector,
    onUiEvent: (UIEventTts) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Icon(
            //icon = painterResource(android.R.drawable.ic_media_rew),
            imageVector= Icons.Default.FastRewind,
            contentDescription = "Backward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEventTts.Backward) })
                .padding(12.dp)
                .size(34.dp)
        )*/
        Image(
            //painter = painterResource(id = playResourceProvider()),
            imageVector = playResourceProvider(),
            contentDescription = "Play/Pause Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEventTts.PlayPause) })
                .padding(8.dp)
                .size(56.dp)
        )
        /*Icon(
            painter = painterResource(android.R.drawable.ic_media_ff),
            contentDescription = "Forward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEventTts.Forward) })
                .padding(12.dp)
                .size(34.dp)
        )*/
    }
}