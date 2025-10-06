package org.deiverbum.app.feature.tts

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.model.tts.TtsProgressData
import timber.log.Timber
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TtsScreenWithBottomSheetControls(
    viewModel: TtsViewModel, // Recibe el ViewModel como parámetro obligatorio
    onNavigateBack: (() -> Unit)? = null // Opcional: para manejar la navegación hacia atrás
) {
    val inputText by viewModel.inputText.collectAsState()
    val ttsUiState by viewModel.ttsUiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    // Efecto para mostrar el BottomSheet cuando la reproducción comienza/pausa,
    // o si hay texto cargado y el sheet no está visible.
    LaunchedEffect(ttsUiState.playbackState, inputText, sheetState.isVisible) {
        val shouldBeVisibleBasedOnState =
            (ttsUiState.playbackState == TtsPlaybackState.PLAYING ||
                    ttsUiState.playbackState == TtsPlaybackState.PAUSED ||
                    (inputText.isNotBlank() && ttsUiState.playbackState == TtsPlaybackState.IDLE && ttsUiState.currentSegment == 0 && ttsUiState.totalSegments == 0))

        if (shouldBeVisibleBasedOnState) {
            if (!sheetState.isVisible && !showBottomSheet) {
                showBottomSheet = true
            }
        }
    }

    // Sincronizar showBottomSheet con el estado real del sheet para cierre por arrastre
    LaunchedEffect(sheetState.targetValue) {
        if (sheetState.targetValue == SheetValue.Hidden && showBottomSheet) {
            showBottomSheet = false
        }
    }

    // Controlar la visibilidad del sheet desde showBottomSheet
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet && !sheetState.isVisible) {
            try {
                sheetState.show()
            } catch (e: Exception) {
                Log.w("BottomSheet", "Error showing sheet: ${e.message}")
            }
        } else if (!showBottomSheet && sheetState.isVisible) {
            try {
                sheetState.hide()
            } catch (e: Exception) {
                Log.w("BottomSheet", "Error hiding sheet: ${e.message}")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lector de Voz") },
                navigationIcon = {
                    if (onNavigateBack != null) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (inputText.isNotBlank()) {
                ExtendedFloatingActionButton(
                    text = { Text(if (showBottomSheet) "Ocultar Controles" else "Mostrar Controles") },
                    icon = { Icon(Icons.Filled.RecordVoiceOver, null) },
                    onClick = { showBottomSheet = !showBottomSheet }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (inputText.isNotBlank()) {
                Text(
                    text = "Texto para lectura:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Permite que ocupe el espacio disponible
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = inputText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No hay texto cargado para leer.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Indicador de progreso lineal si no se muestra el bottom sheet pero está reproduciendo/pausado
            val isPlayingOrPaused =
                ttsUiState.playbackState == TtsPlaybackState.PLAYING || ttsUiState.playbackState == TtsPlaybackState.PAUSED
            if (!showBottomSheet && isPlayingOrPaused && ttsUiState.totalSegments > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (ttsUiState.playbackState == TtsPlaybackState.PLAYING) "Reproduciendo..." else "Pausado...",
                        style = MaterialTheme.typography.labelMedium
                    )
                    val progress = if (ttsUiState.totalSegments > 1) {
                        (ttsUiState.currentSegment.toFloat() / (ttsUiState.totalSegments - 1).coerceAtLeast(
                            1
                        ))
                    } else { // Manejo para un solo segmento
                        if (ttsUiState.playbackState == TtsPlaybackState.COMPLETED) 1f else if (isPlayingOrPaused) 0.5f else 0f
                    }
                    LinearProgressIndicator(
                        progress = { progress.coerceIn(0f, 1f) },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${ttsUiState.currentSegment + 1}/${ttsUiState.totalSegments}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        } // Fin de Column (contenido principal)
    } // Fin de Scaffold

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            // Puedes ajustar el windowInsets si es necesario para evitar solapamientos con la barra de navegación
            // windowInsets = WindowInsets(0) // Ejemplo para quitar insets, ajusta según necesidad
        ) {
            TtsControlsContent( // Este es el Composable que definiste en otro lugar
                ttsProgress = ttsUiState,
                onPlay = { viewModel.playText() },
                onPause = { viewModel.pausePlayback() },
                onResume = { viewModel.resumePlayback() },
                onStop = {
                    viewModel.stopPlayback()
                    // Opcionalmente, podrías querer ocultar el sheet al detener
                    // showBottomSheet = false
                },
                onSeek = { position -> viewModel.seekToRelativePosition(position) },
                currentRate = ttsUiState.speechRate,
                currentPitch = ttsUiState.speechPitch,
                onRateChange = { rate -> viewModel.setSpeechRate(rate) },
                onPitchChange = { pitch -> viewModel.setSpeechPitch(pitch) },
                onCloseSheet = { showBottomSheet = false }
            )
        }
    }
} // Fin de TtsScreenWithBottomSheetControls Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TtsScreenWithBottomSheetControlss(
    //viewModel: TtsViewModel = hiltViewModel()
    viewModel: TtsViewModel // Recibe el ViewModel como parámetro, ya no tiene valor por defecto con hiltViewModel()

) {
    val inputText by viewModel.inputText.collectAsState()
    val ttsUiState by viewModel.ttsUiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    // --- Lógica de LaunchedEffect para mostrar/ocultar el BottomSheet ---
    // (Esta es la "Parte 1" de la definición de TtsScreenWithBottomSheetControls que te mostré)
    LaunchedEffect(ttsUiState.playbackState) {
        if (ttsUiState.playbackState == TtsPlaybackState.PLAYING || ttsUiState.playbackState == TtsPlaybackState.PAUSED) {
            if (!sheetState.isVisible && !showBottomSheet) {
                showBottomSheet = true
            }
        }
    }
    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible && showBottomSheet) {
            showBottomSheet = false
        }
    }
    LaunchedEffect(showBottomSheet) {
        if (!showBottomSheet && sheetState.isVisible) {
            try {
                sheetState.hide()
            } catch (e: Exception) {
                Timber.w("BottomSheet", "Error hiding sheet: ${e.message}")
            }
        }
    }

    // --- Scaffold y Contenido Principal de la Pantalla ---
    // (Esta es la "Parte 2" de la definición de TtsScreenWithBottomSheetControls que te mostré)
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lector TTS con BottomSheet") })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        if (ttsUiState.playbackState == TtsPlaybackState.PLAYING || ttsUiState.playbackState == TtsPlaybackState.PAUSED) {
                            "Controles de Audio"
                        } else {
                            "Leer Texto"
                        }
                    )
                },
                icon = { Icon(Icons.Filled.RecordVoiceOver, null) },
                onClick = {
                    if (inputText.isNotBlank() && (ttsUiState.playbackState != TtsPlaybackState.PLAYING && ttsUiState.playbackState != TtsPlaybackState.PAUSED)) {
                        viewModel.playText()
                    } else {
                        showBottomSheet = !showBottomSheet
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { viewModel.onInputTextChanged(it) },
                label = { Text("Ingresa el texto a leer") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 10
            )

            val isPlayingOrPaused =
                ttsUiState.playbackState == TtsPlaybackState.PLAYING || ttsUiState.playbackState == TtsPlaybackState.PAUSED
            if (!showBottomSheet && isPlayingOrPaused) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (ttsUiState.playbackState == TtsPlaybackState.PLAYING) "Reproduciendo..." else "Pausado...",
                        style = MaterialTheme.typography.labelMedium
                    )
                    if (ttsUiState.totalSegments > 0) {
                        val progress = if (ttsUiState.totalSegments > 1) {
                            (ttsUiState.currentSegment.toFloat() / (ttsUiState.totalSegments - 1).coerceAtLeast(
                                1
                            ))
                        } else {
                            if (ttsUiState.playbackState == TtsPlaybackState.COMPLETED) 1f else if (isPlayingOrPaused) 0.5f else 0f
                        }
                        LinearProgressIndicator(
                            progress = { progress.coerceIn(0f, 1f) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )
                        Text(
                            "${ttsUiState.currentSegment + 1}/${ttsUiState.totalSegments}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }

    // --- ModalBottomSheet y Llamada a TtsControlsContent ---
    // (Esta es la "Parte 3" de la definición de TtsScreenWithBottomSheetControls que te mostré)
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            TtsControlsContent( // <--- AQUÍ SE LLAMA A TtsControlsContent
                ttsProgress = ttsUiState,
                onPlay = { viewModel.playText() },
                onPause = { viewModel.pausePlayback() },
                onResume = { viewModel.resumePlayback() },
                onStop = {
                    viewModel.stopPlayback()
                },
                onSeek = { position -> viewModel.seekToRelativePosition(position) },
                currentRate = ttsUiState.speechRate,
                currentPitch = ttsUiState.speechPitch,
                onRateChange = { rate -> viewModel.setSpeechRate(rate) },
                onPitchChange = { pitch -> viewModel.setSpeechPitch(pitch) },
                onCloseSheet = { showBottomSheet = false }
            )
        }
    }
} // Fin de TtsScreenWithBottomSheetControls

@Composable
fun TtsControlsContent(
    modifier: Modifier = Modifier,
    ttsProgress: TtsProgressData,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onStop: () -> Unit,
    onSeek: (Float) -> Unit,
    currentRate: Float,
    currentPitch: Float,
    onRateChange: (Float) -> Unit,
    onPitchChange: (Float) -> Unit,
    onCloseSheet: () -> Unit
) {
    val decimalFormat = remember { DecimalFormat("0.0") }

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp) // Reducido un poco el espaciado
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.GraphicEq, "Reproducción TTS", Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = when (ttsProgress.playbackState) {
                    TtsPlaybackState.PLAYING -> "Reproduciendo"
                    TtsPlaybackState.PAUSED -> "Pausado"
                    TtsPlaybackState.BUFFERING -> "Cargando..."
                    TtsPlaybackState.STOPPED -> "Detenido"
                    TtsPlaybackState.COMPLETED -> "Completado"
                    TtsPlaybackState.IDLE -> "Listo"
                    TtsPlaybackState.ERROR -> "Error"
                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onCloseSheet) {
                Icon(Icons.Filled.Close, "Cerrar controles")
            }
        }

        if (ttsProgress.playbackState == TtsPlaybackState.PLAYING || ttsProgress.playbackState == TtsPlaybackState.PAUSED) {
            if (ttsProgress.currentSegmentText.isNotBlank()) {
                Text(
                    "“${ttsProgress.currentSegmentText.replace("\n", " ")}”",
                    style = MaterialTheme.typography.bodySmall, // Un poco más pequeño
                    textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .heightIn(min = 36.dp) // Altura mínima
                )
            }
        }
        if (ttsProgress.isBuffering && ttsProgress.playbackState == TtsPlaybackState.BUFFERING) {
            CircularProgressIndicator(Modifier.size(28.dp))
        }

        val sliderEnabled = ttsProgress.totalSegments > 0 && !ttsProgress.isBuffering &&
                (ttsProgress.playbackState == TtsPlaybackState.PLAYING || ttsProgress.playbackState == TtsPlaybackState.PAUSED)

        val sliderPosition = if (ttsProgress.totalSegments > 1) {
            (ttsProgress.currentSegment.toFloat() / (ttsProgress.totalSegments - 1).coerceAtLeast(1))
        } else {
            when (ttsProgress.playbackState) {
                TtsPlaybackState.PLAYING, TtsPlaybackState.BUFFERING -> if (ttsProgress.totalSegments > 0) 0.5f else 0f
                TtsPlaybackState.COMPLETED -> 1f
                else -> 0f
            }
        }.coerceIn(0f, 1f)

        Slider(
            value = sliderPosition,
            onValueChange = { newPosition -> if (sliderEnabled) onSeek(newPosition) },
            modifier = Modifier.fillMaxWidth(),
            enabled = sliderEnabled
        )
        Text(
            if (ttsProgress.totalSegments > 0) "${ttsProgress.currentSegment + 1}/${ttsProgress.totalSegments}" else "",
            style = MaterialTheme.typography.labelSmall
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                { if (sliderEnabled) onSeek((sliderPosition - 0.1f).coerceAtLeast(0f)) },
                enabled = sliderEnabled
            ) {
                Icon(Icons.Filled.FastRewind, "Retroceder", Modifier.size(32.dp))
            }
            val isPlaying = ttsProgress.playbackState == TtsPlaybackState.PLAYING
            val isPaused = ttsProgress.playbackState == TtsPlaybackState.PAUSED
            Button(
                onClick = { if (isPlaying) onPause() else if (isPaused) onResume() else onPlay() },
                enabled = !ttsProgress.isBuffering,
                modifier = Modifier
                    .height(52.dp)
                    .widthIn(min = 120.dp)
            ) {
                Icon(
                    if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    null,
                    Modifier.size(
                        ButtonDefaults.IconSize
                    )
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(if (isPlaying) "Pausar" else if (isPaused) "Reanudar" else "Play")
            }
            IconButton(
                { if (sliderEnabled) onSeek((sliderPosition + 0.1f).coerceAtMost(1f)) },
                enabled = sliderEnabled
            ) {
                Icon(Icons.Filled.FastForward, "Avanzar", Modifier.size(32.dp))
            }
        }

        TextButton(
            onClick = onStop,
            enabled = (ttsProgress.playbackState == TtsPlaybackState.PLAYING || ttsProgress.playbackState == TtsPlaybackState.PAUSED || ttsProgress.playbackState == TtsPlaybackState.BUFFERING)
        ) {
            Icon(Icons.Filled.Stop, null, Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Detener")
        }

        Divider(Modifier.padding(vertical = 6.dp))

        Text(
            "Velocidad: ${decimalFormat.format(currentRate)}x",
            style = MaterialTheme.typography.labelMedium
        )
        Slider(
            currentRate,
            onRateChange,
            Modifier.fillMaxWidth(),
            valueRange = 0.5f..2.0f,
            steps = 14
        )

        Text(
            "Tono: ${decimalFormat.format(currentPitch)}x",
            style = MaterialTheme.typography.labelMedium
        )
        Slider(
            currentPitch,
            onPitchChange,
            Modifier.fillMaxWidth(),
            valueRange = 0.5f..2.0f,
            steps = 14
        )
    }
}