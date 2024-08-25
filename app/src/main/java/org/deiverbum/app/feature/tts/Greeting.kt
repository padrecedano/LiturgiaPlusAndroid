package org.deiverbum.app.feature.tts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlin.random.Random

@Composable
fun Greeting(name: String) {
    val cantAudioToSpeech = TtsAudioManager.cantDataToSpeech.value
    val currentAudioPlaying = TtsAudioManager.currentTTSPlaying.value

    val context = LocalContext.current
    val locale = java.util.Locale.getDefault()

    TtsAudioManager.initialize(context, locale)

    LaunchedEffect(Unit) {
        generateTTS()
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        if (TtsAudioManager.finish.value) {
            Text(text = "FINISH")
        }


        Button(onClick = {
            TtsAudioManager.nextTTS()
        }) {

            Text(text = "NEXT")

        }

        Button(onClick = {
            generateTTS()
        }) {

            Text(text = "NEW")

        }

        Button(onClick = {
            TtsAudioManager.stop()
        }) {

            Text(text = "STOP")

        }


        Button(onClick = {

            TtsAudioManager.start()

        }) {

            Text(text = "START PLAYING TTS")

        }
        Text(text = "$cantAudioToSpeech")

        Text(text = currentAudioPlaying.toString())


    }

}

fun generateTTS() {
    TtsAudioManager.addTTS(
        AudioContent(
            "https://cdn.pixabay.com/photo/2023/01/02/04/13/dog-7691238_640.jpg",
            "GraphQL es un lenguaje de consulta y manipulación de datos para APIs, y un entorno de ejecución para realizar consultas con datos existentes.\u200B GraphQL fue desarrollado internamente por Facebook en 2012 antes de ser liberado públicamente en 2015.\u200B",
            "title  ${Random.nextInt()}"
        )
    )

    TtsAudioManager.addTTS(
        AudioContent(
            "https://cdn.pixabay.com/photo/2022/08/20/09/16/nature-7398655__340.jpg",
            "Kotlin es un lenguaje de programación de tipado estático que corre sobre la máquina virtual de Java y que también puede ser compilado a código fuente de JavaScript. Es desarrollado principalmente por JetBrains en sus oficinas de San Petersburgo.",
            "title  ${Random.nextInt()}"
        )
    )
    TtsAudioManager.addTTS(
        AudioContent(
            "https://cdn.pixabay.com/photo/2022/11/14/20/14/compass-7592447__340.jpg",
            "Jetpack Compose es un framework (estructura o marco de trabajo que, bajo parámetros estandarizados, ejecutan tareas específicas en el desarrollo de un software) con la particularidad de ejecutar prácticas modernas en los desarrolladores de software a partir de la reutilización de componentes, así como también contando ",
            "title  ${Random.nextInt()}"
        )
    )
    TtsAudioManager.addTTS(
        AudioContent(
            "https://cdn.pixabay.com/photo/2022/11/28/14/07/skyline-7622147_640.jpg",
            "Microsoft Corporation es una empresa tecnológica multinacional estadounidense que produce software de computadora, productos electrónicos de consumo, computadoras personales y servicios relacionados, con sede en el campus de Microsoft ubicado en Redmond, Washington, Estados Unidos",
            "title  ${Random.nextInt()}"
        )
    )
}