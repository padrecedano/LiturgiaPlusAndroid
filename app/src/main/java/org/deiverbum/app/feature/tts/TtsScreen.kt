package org.deiverbum.app.feature.tts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber
import java.text.DecimalFormat

@Composable
fun TtsScreen(
    viewModel: TtsViewModel = hiltViewModel()
) {
    val inputText by viewModel.inputText.collectAsState()
    val ttsUiState by viewModel.ttsUiState.collectAsState()

    TtsScreenContent(
        inputText = inputText,
        onInputTextChanged = { newText -> viewModel.onInputTextChanged(newText) }, // Lambda para actualizar el texto
        ttsProgress = ttsUiState, // El objeto completo con el estado de la reproducción
        currentRate = ttsUiState.speechRate, // Extrae la velocidad actual del estado
        currentPitch = ttsUiState.speechPitch, // Extrae el tono actual del estado
        onPlay = { viewModel.playText() },
        onPause = { viewModel.pausePlayback() },
        onResume = { viewModel.resumePlayback() },
        onStop = { viewModel.stopPlayback() },
        onSeek = { position -> viewModel.seekToRelativePosition(position) }, // Lambda para el seek
        onRateChange = { rate -> viewModel.setSpeechRate(rate) }, // Lambda para cambiar velocidad
        onPitchChange = { pitch -> viewModel.setSpeechPitch(pitch) } // Lambda para cambiar tono
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TtsScreenContent(
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    ttsProgress: TtsProgressData,

    currentRate: Float,
    currentPitch: Float,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit,
    onSeek: (Float) -> Unit,
    onRateChange: (Float) -> Unit,
    onPitchChange: (Float) -> Unit
) {
    var showSettingsDialog by remember { mutableStateOf(false) }
    // Los tempRate y tempPitch se inicializarán en el diálogo cuando se muestre.
    val decimalFormat = remember { DecimalFormat("0.0") }
    var dialogRateValue by remember(currentRate, showSettingsDialog) {
        mutableStateOf(if (showSettingsDialog) currentRate else currentRate)
        // O más simple si solo quieres que se resetee cuando se abre:
        // mutableStateOf(currentRate) -> pero esto significa que si currentRate cambia mientras
        // el diálogo está abierto, el diálogo no lo reflejará hasta que se reabra.
        // La clave `currentRate, showSettingsDialog` es para que se "resetee"
        // al valor actual cuando el diálogo se abre.
    }
    var dialogPitchValue by remember(currentPitch, showSettingsDialog) {
        mutableStateOf(if (showSettingsDialog) currentPitch else currentPitch)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Lector de Texto a Voz", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = inputText,
            onValueChange = onInputTextChanged,
            label = { Text("Ingresa el texto a leer") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 200.dp),
            maxLines = 10,
            placeholder = { Text("Pega o escribe aquí el texto que deseas escuchar...") }
        )

        // --- Sección de Estado y Progreso ---
        val statusText = when (ttsProgress.playbackState) {
            TtsPlaybackState.PLAYING -> "Reproduciendo: Segmento ${ttsProgress.currentSegment + 1}/${ttsProgress.totalSegments}"
            TtsPlaybackState.PAUSED -> "Pausado: Segmento ${ttsProgress.currentSegment + 1}/${ttsProgress.totalSegments}"
            TtsPlaybackState.STOPPED -> "Detenido"
            TtsPlaybackState.COMPLETED -> "Lectura Completada"
            TtsPlaybackState.IDLE -> "Listo para reproducir"
            TtsPlaybackState.BUFFERING -> "Cargando..."
            TtsPlaybackState.ERROR -> "Error: ${ttsProgress.error ?: "Desconocido"}"
        }
        Text(
            text = statusText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        if (ttsProgress.isBuffering && ttsProgress.playbackState == TtsPlaybackState.BUFFERING) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
            if (ttsProgress.currentSegmentText.isNotBlank()) {
                Text(
                    text = "Iniciando: ${
                        ttsProgress.currentSegmentText.take(60).replace("\n", " ")
                    }...",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (ttsProgress.playbackState == TtsPlaybackState.PLAYING || ttsProgress.playbackState == TtsPlaybackState.PAUSED) {
            if (ttsProgress.currentSegmentText.isNotBlank()) {
                Text(
                    text = "“${ttsProgress.currentSegmentText.take(150).replace("\n", " ")}...”",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
// --- Fin Sección de Estado y Progreso ---
        // --- Sección Slider de Progreso ---
        if (ttsProgress.totalSegments > 0) {
            val sliderPosition = if (ttsProgress.totalSegments > 1) {
                // Asegurar que no haya división por cero si totalSegments es 1
                (ttsProgress.currentSegment.toFloat() / (ttsProgress.totalSegments - 1).coerceAtLeast(
                    1
                ))
            } else { // Caso de un solo segmento
                // Si solo hay un segmento, el slider puede estar al 0% (inicio), 50% (reproduciendo), o 100% (completado)
                when (ttsProgress.playbackState) {
                    TtsPlaybackState.PLAYING, TtsPlaybackState.BUFFERING -> 0.5f // Visualmente a la mitad si está sonando/cargando
                    TtsPlaybackState.COMPLETED -> 1f
                    else -> 0f // Al inicio si está idle, stopped, paused, o error
                }
            }.coerceIn(0f, 1f) // Asegura que el valor esté entre 0 y 1

            Timber.d("TtsScreen Slider Update: currentSegment=${ttsProgress.currentSegment}, totalSegments=${ttsProgress.totalSegments}, sliderPosition=$sliderPosition, state=${ttsProgress.playbackState}")

            Slider(
                value = sliderPosition,
                onValueChange = { newPosition ->
                    // Solo permitir seek si no está en un estado que lo impida
                    if (ttsProgress.playbackState != TtsPlaybackState.IDLE &&
                        ttsProgress.playbackState != TtsPlaybackState.STOPPED &&
                        ttsProgress.playbackState != TtsPlaybackState.COMPLETED &&
                        ttsProgress.playbackState != TtsPlaybackState.ERROR &&
                        !ttsProgress.isBuffering
                    ) {
                        onSeek(newPosition)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = ttsProgress.playbackState != TtsPlaybackState.IDLE &&
                        ttsProgress.playbackState != TtsPlaybackState.STOPPED &&
                        ttsProgress.playbackState != TtsPlaybackState.COMPLETED &&
                        ttsProgress.playbackState != TtsPlaybackState.ERROR &&
                        !ttsProgress.isBuffering // No permitir seek mientras se está cargando un segmento
            )
        } else {
            // Slider deshabilitado si no hay segmentos
            Slider(
                value = 0f,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // --- Fin Sección Slider de Progreso ---

        // --- Sección Botones de Control ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón Play/Pause/Resume combinado
            val isPlaying = ttsProgress.playbackState == TtsPlaybackState.PLAYING
            val isPaused = ttsProgress.playbackState == TtsPlaybackState.PAUSED
            val canPlayOrResume = inputText.isNotBlank() && !ttsProgress.isBuffering

            if (isPlaying) {
                Button(onClick = onPause, enabled = !ttsProgress.isBuffering) {
                    Icon(Icons.Filled.Pause, contentDescription = "Pausar")
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Pausar")
                }
            } else { // No está reproduciendo (puede estar pausado, detenido, idle, etc.)
                Button(
                    onClick = {
                        if (isPaused) {
                            onResume()
                        } else {
                            onPlay() // Esto incluye idle, stopped, completed, error
                        }
                    },
                    enabled = canPlayOrResume
                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = if (isPaused) "Reanudar" else "Reproducir"
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(if (isPaused) "Reanudar" else "Play")
                }
            }

            Button(
                onClick = onStop,
                // Habilitar Stop si está reproduciendo, pausado, o incluso cargando (buffering)
                enabled = (ttsProgress.playbackState == TtsPlaybackState.PLAYING ||
                        ttsProgress.playbackState == TtsPlaybackState.PAUSED ||
                        ttsProgress.playbackState == TtsPlaybackState.BUFFERING)
            ) {
                Icon(Icons.Filled.Stop, contentDescription = "Detener")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Stop")
            }
        }
        // --- Sección Botón de Configuración ---
        Box(modifier = Modifier.fillMaxWidth()) { // Para alinear el botón a la derecha
            IconButton(
                onClick = {
                    // Al abrir el diálogo, inicializar los valores temporales
                    // con los valores actuales del TTS.
                    dialogRateValue = currentRate
                    dialogPitchValue = currentPitch
                    showSettingsDialog = true
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Filled.Settings, contentDescription = "Ajustes de Voz")
            }
        }
        // --- Fin Sección Botón de Configuración ---

        // --- Diálogo de Configuración ---
        if (showSettingsDialog) {
            // Variables temporales para los sliders dentro del diálogo
            var dialogRate by remember { mutableStateOf(currentRate) }
            var dialogPitch by remember { mutableStateOf(currentPitch) }

            AlertDialog(
                onDismissRequest = { showSettingsDialog = false },
                title = { Text("Ajustes de Voz") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Slider para Velocidad (Rate)
                        Text("Velocidad: ${decimalFormat.format(dialogRate)}x")
                        Slider(
                            value = dialogRate,
                            onValueChange = { dialogRate = it },
                            valueRange = 0.5f..2.0f, // Rango típico para velocidad
                            steps = 14 // (2.0 - 0.5) / 0.1 - 1 = 15 / 0.1 - 1 = 15 - 1 = 14 steps
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Slider para Tono (Pitch)
                        Text("Tono: ${decimalFormat.format(dialogPitch)}x")
                        Slider(
                            value = dialogPitch,
                            onValueChange = { dialogPitch = it },
                            valueRange = 0.5f..2.0f, // Rango típico para tono
                            steps = 14
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onRateChange(dialogRate)
                            onPitchChange(dialogPitch)
                            showSettingsDialog = false
                        }
                    ) {
                        Text("Aplicar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showSettingsDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
        // --- Fin Diálogo de Configuración ---

    } // Fin del Column principal
} // Fin de TtsScreenContent
