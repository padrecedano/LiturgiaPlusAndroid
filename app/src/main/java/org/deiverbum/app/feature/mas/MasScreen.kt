package org.deiverbum.app.feature.mas

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.model.FileRequestt
import org.deiverbum.app.core.presentation.file.FileViewModel
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.universalis.LoadingState

@Composable
fun MasScreen(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: MasViewModel = hiltViewModel(),
    fileRequest: FileRequestt,
    viewModel: FileViewModel = hiltViewModel(),
    ) {

    LaunchedEffect(fileRequest) { viewModel.setFileRequest(fileRequest) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    /*
    when (uiState) {
        FileViewModel.FileUiState.Empty -> EmptyState()
        is FileViewModel.FileUiState.Error -> ErrorState()
        is FileViewModel.FileUiState.Loaded -> {
            (uiState as FileViewModel.FileUiState.Loaded).itemState.allData.forEach {
                Text(text = it.text.toString())
            }
        }
        is FileViewModel.FileUiState.Loading -> LoadingState()
    }
*/

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
            LazyColumn {
                items(uiState.itemState.allData.size) {
                    uiState.itemState.allData.forEach { file ->
                        Text(file.text.toString())
                    }
                }
            }
        }
        FileViewModel.FileUiState.Loading -> LoadingState()
    }

}

