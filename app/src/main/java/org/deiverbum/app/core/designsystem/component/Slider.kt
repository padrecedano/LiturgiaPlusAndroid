package org.deiverbum.app.core.designsystem.component

import LPlusIcons
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.NotStarted
import androidx.compose.material.icons.outlined.PauseCircleOutline
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.deiverbum.app.feature.tts.SliderAdvancedExample
import org.deiverbum.app.feature.tts.TtsManagerComposeA

private var mTtsManager: TtsManagerComposeA? = null

@ExperimentalMaterial3Api
@Composable
fun TextToSpeechScreenAA(text: StringBuilder) {
    var isSpeaking by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    var sliderValue by remember { mutableStateOf(0f) }
    var sliderMaxValue by remember { mutableStateOf(100f) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var currentTextIndex by remember { mutableStateOf(0) }
    //SliderLabel(label = "a", minWidth = 1.dp)
    //SliderAdvancedExample()


    SliderAdvancedExample(sliderValue, sliderMaxValue, isSpeaking, isPaused) { newValue ->
        // Manejo de cambios en el valor del control deslizante
        mTtsManager?.changeProgress(newValue.toInt())
    }
    /*Column(modifier = Modifier.padding(24.dp)) {
        // Mostrar un control deslizante para seguir el progreso de la reproducción
        SliderA(sliderValue, sliderMaxValue) { newValue ->
            // Manejo de cambios en el valor del control deslizante
            mTtsManager?.changeProgress(newValue.toInt())
        }

        // Comprobar si TTS dice
        if (isSpeaking) {
            //readTextAa(text = text)
            readTextAa(text = text, onTextChanged = { index ->
                currentTextIndex = index
            }, onSliderValuesChanged = { current, max ->
                sliderValue = current.toFloat()
                sliderMaxValue = max.toFloat()
            })
        }

        // Botón para iniciar la reproducción
        Button(onClick = {
            isSpeaking = true
        }) {
            Image(
                painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                modifier = Modifier.size(20.dp)
            )
        }

        // Botón para detener la reproducción
        Button(onClick = {
            mTtsManager?.stop()
            isSpeaking = false
        }) {
            Image(
                painterResource(id = R.drawable.ic_baseline_stop_circle_24),
                contentDescription = "Stop",
                modifier = Modifier.size(20.dp)
            )
        }

        //TODO: Buttons for Pause / Resume ...
    }*/
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderReader(
    sliderValue: Float,
    sliderMaxValue: Float,
    isSpeaking: Boolean,
    isPaused: Boolean,
    onSliderValueChange: (Float) -> Unit

) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
        Slider(
            modifier = Modifier
                .weight(1f),
            value = sliderValue,
            valueRange = 0f..sliderMaxValue,
            onValueChange = { newValue ->
                onSliderValueChange(newValue)
            },
        )
        if (!isSpeaking) {
            Icon(imageVector = Icons.Outlined.PlayCircle, contentDescription = "Leer")
            Icon(imageVector = Icons.Outlined.NotStarted, contentDescription = "Leer")

        } else {
            Icon(imageVector = Icons.Outlined.PauseCircleOutline, contentDescription = "Pausar")
            Icon(imageVector = Icons.Outlined.StopCircle, contentDescription = "Parar")
        }
        if (isSpeaking) {
            Icon(imageVector = Icons.Outlined.PauseCircleOutline, contentDescription = "Resume")
            Icon(imageVector = Icons.Outlined.StopCircle, contentDescription = "Resume")
        }
        if (isPaused) {
            Icon(imageVector = Icons.Outlined.RestartAlt, contentDescription = "Resume")

            Icon(imageVector = Icons.Outlined.RestoreFromTrash, contentDescription = "Resume")
            //Icon(imageVector = Icons.Outlined.StopCircle, contentDescription = "Resume")
        }
    }
}

@Composable
fun PlayButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NiaIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = LPlusIcons.BookmarkBorder,
                contentDescription = "stringResource(R.string.core_ui_bookmark)",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = LPlusIcons.Bookmark,
                contentDescription = "stringResource(R.string.core_ui_unbookmark)",
            )
        },
    )
}
