package org.deiverbum.app.feature.mas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.presentation.file.FileViewModel
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.universalis.LoadingState

@Composable
fun MasScreen(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: MasViewModel = hiltViewModel(),
    viewModel: FileViewModel = hiltViewModel(),


    ) {
    val fileRequest = FileRequest(
        listOf("raw/oratio/angelus.json"), 1, 6, false,
        isVoiceOn = true,
        isBrevis = true
    )
    //viewModel.resetGame(fileRequest)
    viewModel.loadData(fileRequest)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MasScreen(modifier = modifier, uiState = uiState)


}
@Composable
fun MasScreen(
    modifier: Modifier,
    uiState: FileViewModel.FileUiState
) {
    when (uiState) {

        FileViewModel.FileUiState.Empty -> EmptyState()
        is FileViewModel.FileUiState.Error -> ErrorState()
        is FileViewModel.FileUiState.Loaded -> {
            uiState.itemState.allData.forEach {
                //getViewBinding().progressBar.visibility = View.GONE
                Text(text = it.text.toString())
                //
            }
        }

        FileViewModel.FileUiState.Loading -> LoadingState()
    }


}

