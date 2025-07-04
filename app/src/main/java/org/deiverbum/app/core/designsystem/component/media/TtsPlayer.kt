package org.deiverbum.app.core.designsystem.component.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.deiverbum.app.feature.tts.UIEventTts


@Composable
internal fun PlayerBarTts(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (UIEventTts) -> Unit
) {
    val newProgressValue = remember { mutableStateOf(0f) }
    val useNewProgressValue = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = if (useNewProgressValue.value) newProgressValue.value else progress,
            onValueChange = { newValue ->
                useNewProgressValue.value = true
                newProgressValue.value = newValue
                onUiEvent(UIEventTts.UpdateProgress(newProgress = newValue))
            },
            onValueChangeFinished = {
                useNewProgressValue.value = false
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
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
        Image(
            //painter = painterResource(id = playResourceProvider()),
            imageVector = playResourceProvider(),
            contentDescription = "Play/Pause Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(UIEventTts.Stop) })
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