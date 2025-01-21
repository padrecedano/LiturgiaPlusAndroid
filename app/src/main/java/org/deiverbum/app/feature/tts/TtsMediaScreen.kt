package org.deiverbum.app.feature.tts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
internal fun TtsMediaScreen(
    vm: TtsMediaViewModel,
    //navController: NavController,
    startServiceTts: () -> Unit,
) {
    val state = vm.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.value) {
            UIStateTts.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )

            is UIStateTts.Ready -> {
                LaunchedEffect(true) { // This is only call first time
                    //startServiceTts()
                }

                //ReadyContentTts(vm = vm)
            }
        }

    }
}

