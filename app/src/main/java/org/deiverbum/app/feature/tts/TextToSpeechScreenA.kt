package org.deiverbum.app.feature.tts

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.NotStarted
import androidx.compose.material.icons.outlined.PauseCircleOutline
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R
import org.deiverbum.app.util.Constants
import timber.log.Timber
import java.util.Locale

private var mTtsManager: TtsManagerComposeA? = null

@ExperimentalMaterial3Api
@Composable
fun TextToSpeechScreenA(text: StringBuilder) {
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

@Composable
private fun readTextAa(
    text: StringBuilder,
    onTextChanged: (Int) -> Unit,
    onSliderValuesChanged: (Int, Int) -> Unit
) {
    if (mTtsManager == null) {
        mTtsManager = TtsManagerComposeA(
            LocalContext.current, text,
            Constants.SEPARADOR,
        ) { current: Int, max: Int ->
            onSliderValuesChanged(current, max) // Update current value
            onTextChanged(current) // Update Max value
        }
        mTtsManager!!.start()
    }
}

@Composable
private fun readTextA(text: StringBuilder) {
    var sliderValue by remember { mutableFloatStateOf(0f) }
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    // Inicializar TtsManagerCompose si Aún no se ha inicializado
    if (mTtsManager == null) {
        mTtsManager = TtsManagerComposeA(
            LocalContext.current, text,
            Constants.SEPARADOR,
        ) { current: Int, max: Int ->
            // Actualizar los valores del control deslizante
            sliderValue = current.toFloat() // Update current value
            sliderMaxValue = max.toFloat() // Update Max value
        }
        mTtsManager!!.start()
    }
}

@ExperimentalMaterial3Api
@Composable
fun SliderA(
    sliderValue: Float,
    sliderMaxValue: Float,
    onSliderValueChange: (Float) -> Unit
) {
    Column {

        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
            Slider(
                modifier = Modifier
                    //.weight(1f, fill = false)
                    .padding(8.dp),
                value = sliderValue,
                valueRange = 0f..sliderMaxValue,
                onValueChange = { newValue ->
                    // Llamar al controlador cuando se cambia el valor
                    onSliderValueChange(newValue)
                },

                /*thumb = {
            Icon(
                imageVector = Icons.Outlined.ArrowCircleRight,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.Red,

            ),
        }*/
            )
            //Text("Title", textAlign = TextAlign.Center,
            //    modifier = Modifier.weight(1f)) // Fill this with remaining space available
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow),
    ) {
        Row {

            Slider(
                modifier = Modifier
                    //.weight(1f, fill = false)
                    .padding(8.dp),
                value = sliderValue,
                valueRange = 0f..sliderMaxValue,
                onValueChange = { newValue ->
                    // Llamar al controlador cuando se cambia el valor
                    onSliderValueChange(newValue)
                },

                /*thumb = {
            Icon(
                imageVector = Icons.Outlined.ArrowCircleRight,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.Red,

            ),
        }*/
            )
        }

        Icon(Icons.Filled.ShoppingCart, "")
    }
    Column {
        Slider(
            value = sliderValue,
            valueRange = 0f..sliderMaxValue,
            onValueChange = { newValue ->
                // Llamar al controlador cuando se cambia el valor
                onSliderValueChange(newValue)
            },
            thumb = {

                val shape = RoundedCornerShape(1.dp)
                Spacer(
                    modifier = Modifier
                        .size(20.dp)

                        .background(Red, shape)
                )
            },

            /*thumb = {
        Icon(
            imageVector = Icons.Outlined.ArrowCircleRight,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = Color.Red,

        ),
    }*/
        )
        // Muestra el porcentaje de progreso
        Text(text = "Progress: ${sliderValue.toInt()}%")
    }
}

@Composable
fun SliderLabel(
    label: String,
    minWidth: Dp,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        textAlign = TextAlign.Center,
        color = Color.White,
        maxLines = 1,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)
            .defaultMinSize(minWidth = minWidth)
    )
}

@Composable
fun SliderAdvancedExample(
    sliderValue: Float,
    sliderMaxValue: Float,
    isSpeaking: Boolean,
    isPaused: Boolean,
    onSliderValueChange: (Float) -> Unit,
    //onClick:()->Unit
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
        Button(onClick = {
            Timber.d("a")
        }) {
            Image(
                painterResource(id = R.drawable.ic_play),
                contentDescription = "Play",
                modifier = Modifier.size(20.dp)
            )
        }

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

            //Icon(imageVector = Icons.Outlined.PauseCircleOutline, contentDescription = "Resume")
            //Icon(imageVector = Icons.Outlined.StopCircle, contentDescription = "Resume")
        }
    }

}

val sentences = listOf(
    "Good morning, Captain",
    "Do you need another mule skinner",
    "Out on your new road line",
)

//TODO: Ver luego
@Composable
fun TextToSpeechScreen() {
    var isSpeaking by remember { mutableStateOf(true) }
    val tts = rememberTextToSpeechNew()

    Column(modifier = Modifier.padding(24.dp)) {
        isSpeaking = false
        for (sentence in sentences) {
            Button(onClick = {
                if (tts.value?.isSpeaking == true) {
                    tts.value?.stop()
                    isSpeaking = false
                } else {
                    tts.value?.speak(
                        sentence, TextToSpeech.QUEUE_FLUSH, null, ""
                    )
                    isSpeaking = true
                }
            }) {
                Text(sentence)
            } // End Button
        } // End for

    }
}

@Composable
fun rememberTextToSpeechNew(): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.US
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