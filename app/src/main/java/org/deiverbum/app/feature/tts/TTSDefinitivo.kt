package org.deiverbum.app.feature.tts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R
import org.deiverbum.app.util.Constants

private var mTtsManager: TtsManagerComposeB? = null
private var mTtsManagerA: TtsManagerComposeA? = null

@ExperimentalMaterial3Api
@Composable
fun TextToSpeechScreenB(text: StringBuilder) {
    var isSpeaking by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0f) }
    var sliderMaxValue by remember { mutableStateOf(100f) }
    var currentTextIndex by remember { mutableStateOf(0) }
    val textLines = text.split(Constants.SEPARADOR.toRegex()).dropLastWhile { it.isEmpty() }

    // Establecer el valor máximo del control deslizante de acuerdo con el número de líneas de texto
    sliderMaxValue = textLines.size.toFloat()

    Column(modifier = Modifier.padding(24.dp)) {
        SliderMinimalExampleB(sliderValue, sliderMaxValue) { newValue ->
            mTtsManager?.changeProgress(newValue.toInt())
        }

        if (isSpeaking) {
            readTextB(text = text, onTextChanged = { index ->
                currentTextIndex = index
            }, onSliderValuesChanged = { current, max ->
                sliderValue = current.toFloat()
                sliderMaxValue = max.toFloat()
            })
        }

        // Desplazamiento vertical del texto
        // LazyColumn(modifier = Modifier.weight(1f)) {
        //     items(textLines.size) { index ->
        //         Text(
        //             text = textLines[index],
        //             modifier = Modifier.padding(14.dp).background(if (index == currentTextIndex) Color.LightGray else Color.Transparent)
        //         )
        //     }
        // }

        Button(onClick = {
            isSpeaking = true
        }) {
            Image(
                painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                modifier = Modifier.size(20.dp)
            )
        }

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
    }
}

@Composable
private fun readTextBbb(
    text: StringBuilder,
    onTextChanged: (Int) -> Unit,
    onSliderValuesChanged: (Int, Int) -> Unit
) {
    if (mTtsManagerA == null) {
        mTtsManagerA = TtsManagerComposeA(
            LocalContext.current, text,
            Constants.SEPARADOR,
        ) { current: Int, max: Int ->
            onSliderValuesChanged(current, max) // Update current value
            onTextChanged(current) // Update Max value
        }
        mTtsManagerA!!.start()
    }
}


@Composable
fun SliderMinimalExampleB(
    sliderValue: Float,
    sliderMaxValue: Float,
    onSliderValueChange: (Float) -> Unit
) {
    Column {
        Slider(
            value = sliderValue,
            valueRange = 0f..sliderMaxValue,
            onValueChange = { newValue ->
                onSliderValueChange(newValue)
            }
        )
        Text(text = "Progress: ${sliderValue.toInt()}%")
    }
}


@Composable
private fun readTextB(
    text: StringBuilder,
    onTextChanged: (Int) -> Unit,
    onSliderValuesChanged: (Int, Int) -> Unit

) {
    var sliderValue by remember { mutableFloatStateOf(0f) }
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    // Inicializar TtsManagerCompose si Aún no se ha inicializado
    if (mTtsManager == null) {
        mTtsManager = TtsManagerComposeB(
            LocalContext.current, text,
            Constants.SEPARADOR,
            { current: Int, max: Int ->
                // Actualizar los valores del control deslizante
                sliderValue = current.toFloat() // Update current value
                sliderMaxValue = max.toFloat() // Update Max value
            },
            0f, // Valor inicial del control deslizante
            // Valor máximo del control deslizante
            text.split(Constants.SEPARADOR.toRegex()).dropLastWhile { it.isEmpty() }.size.toFloat()
        )
        mTtsManager!!.start()
    }
}


