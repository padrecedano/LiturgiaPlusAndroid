package org.deiverbum.app.feature.tts

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.TtsManagerCompose
import org.deiverbum.app.util.TtsManagerComposee
import java.util.Locale


private var mTtsManager: TtsManagerComposeA? = null
private var mTtsManagerr: TtsManagerComposee? = null

@ExperimentalMaterial3Api
@Composable
fun TextToSpeechScreenn(text: StringBuilder) {
    var isSpeaking by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    var sliderValue by remember { mutableStateOf(0f) }
    var sliderMaxValue by remember { mutableStateOf(100f) }

    Column(modifier = Modifier.padding(24.dp)) {
        // Mostrar un control deslizante para seguir el progreso de la reproducción
        /*SliderAdvancedExample(sliderValue, sliderMaxValue,isSpeaking,isPaused) { newValue ->
            // Manejo de cambios en el valor del control deslizante
            mTtsManager?.changeProgress(newValue.toInt())
        }*/
        SliderA(sliderValue, sliderMaxValue) { newValue ->
            // Manejo de cambios en el valor del control deslizante
            mTtsManager?.changeProgress(newValue.toInt())
        }

        // Comprobar si TTS dice
        if (isSpeaking) {
            readTextNew(text = text)
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
            mTtsManagerr?.stop()
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
@ExperimentalMaterial3Api
@Composable
fun TextToSpeechScreen(text: StringBuilder) {
    var isSpeaking by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0f) }
    var sliderMaxValue by remember { mutableStateOf(100f) }

    Column(modifier = Modifier.padding(24.dp)) {
        //isSpeaking = false
        SliderMinimalExample(sliderValue, sliderMaxValue)
        if (isSpeaking) {
            readText(text = text)
        }

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
fun SliderMinimalExamplee(
    sliderValue: Float,
    sliderMaxValue: Float,
    onSliderValueChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column {
        Slider(
            value = sliderValue,
            valueRange = 0f..sliderMaxValue,
            onValueChange = { newValue ->
                // Llamar al controlador cuando se cambia el valor
                onSliderValueChange(newValue)
                sliderPosition = newValue
            }
        )
        // Muestra el porcentaje de progreso
        Text(text = "Progress: ${sliderValue.toInt()}%")
        Text(text = sliderPosition.toString())

    }
}

@Composable
fun SliderMinimalExample(
    sliderValue: Float,
    sliderMaxValue: Float
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            //value = sliderPosition,
            value = sliderValue,
            valueRange = 0f..sliderMaxValue,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())
    }
}

@Composable
fun rememberTextToSpeech(): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.forLanguageTag("es")
            }
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    return tts
}

@Composable
private fun readTextNew(text: StringBuilder) {
    // Inicializar TtsManagerCompose si Aún no se ha inicializado
    var sliderValue by remember { mutableFloatStateOf(0f) }
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    if (mTtsManagerr == null) {
        mTtsManagerr = TtsManagerComposee(
            LocalContext.current, text,
            Constants.SEPARADOR,
        ) { current: Int, max: Int ->
            // Actualizar los valores del control deslizante
            sliderValue = current.toFloat() // Update current value
            sliderMaxValue = max.toFloat() // Update Max value
        }
        mTtsManagerr!!.start()
    }
}
@Composable
private fun readText(text: StringBuilder) {
    var sliderValue by remember { mutableFloatStateOf(0f) }
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    mTtsManager = TtsManagerComposeA(
        LocalContext.current, text,
        Constants.SEPARADOR,
    ) { current: Int, max: Int ->
        sliderValue = current.toFloat() // Update current value
        sliderMaxValue = max.toFloat() // Update Max alue
    }
    mTtsManager!!.start()
}

@Composable
private fun readTextt(text: StringBuilder) {

    mTtsManager = TtsManagerComposeA(
        LocalContext.current, text,
        Constants.SEPARADOR,
    ) { current: Int, max: Int ->
//        seekBar!!.progress = current
//        seekBar!!.max = max
    }
    mTtsManager!!.start()
}

@Composable
fun rememberTextToSpeechh(text: StringBuilder): MutableState<TtsManagerCompose?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TtsManagerCompose?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TtsManagerCompose(
            context, text,
            Constants.SEPARADOR,
        ) { current: Int, max: Int ->/*status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.forLanguageTag("es")
            }*/
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.close()
        }
    }
    return tts
}

@ExperimentalMaterial3Api
@Composable
fun SliderMinimalExampleee() {
    var sliderValue by remember { mutableFloatStateOf(0f) }

    CustomSlider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = sliderValue,
        onValueChange = {
            sliderValue = it
        },
        valueRange = 0f..50f,
        gap = 10,
        showIndicator = true,
        thumb = { thumbValue ->
            CustomSliderDefaults.Thumb(
                thumbValue = "$thumbValue%",
                color = Color.Transparent,
                size = 40.dp,
                modifier = Modifier.background(
                    brush = Brush.linearGradient(listOf(Color.Cyan, Color.Blue)),
                    shape = CircleShape
                )
            )
        },
        track = { sliderState ->
            Box(
                modifier = Modifier
                    .track()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.4f),
                        shape = CircleShape
                    )
                    .background(Color.White)
                    .padding(3.5.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .progress(sliderState = sliderState)
                        .background(
                            brush = Brush.linearGradient(listOf(Color.Red, Color.Magenta))
                        )
                )
            }
        }
    )
    /*var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())
    }*/
}

