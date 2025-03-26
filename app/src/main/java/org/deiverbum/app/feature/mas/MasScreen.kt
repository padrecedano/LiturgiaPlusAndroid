package org.deiverbum.app.feature.mas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.model.data.FileRequestt
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.calendar.ErrorState
import org.deiverbum.app.feature.home.HomeButton
import org.deiverbum.app.feature.home.populateData

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MasScreen(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MasViewModel = hiltViewModel(),
    fileRequest: FileRequestt,
    //viewModel: FileViewModel = hiltViewModel(),
    ) {

    //LaunchedEffect(fileRequest) { viewModel.setFileRequest(fileRequest) }
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
    MasScreen(uiState = uiState, onTopicClick = onTopicClick, modifier = modifier)
    //MoreItems(uiState,onTopicClick, modifier)
    //MasScreen(modifier = modifier, uiState = uiState)


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MasScreen(
    onTopicClick: (String) -> Unit,

    modifier: Modifier,
    uiState: LocalFileUiState
) {
    MoreItems(onTopicClick = onTopicClick, modifier = modifier)
    when (uiState) {

        LocalFileUiState.Empty -> EmptyState()
        is LocalFileUiState.Error -> ErrorState("Error")
        LocalFileUiState.Loading -> MoreItems(onTopicClick = onTopicClick, modifier = modifier)
        is LocalFileUiState.Success -> Text("success")
    }

}

@ExperimentalLayoutApi
@Composable
fun MoreItems(
    onTopicClick: (String) -> Unit,
    modifier: Modifier,

    ) {


    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(1.dp))

    val rowModifier = Modifier
        .padding(horizontal = 1.dp, vertical = 1.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(1.dp))

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(populateData()) { item ->
            if (item.parent == "Breviario") {
                Text(
                    item.parent, fontSize = 26.sp, modifier = Modifier
                        .padding(2.dp)
                )
                item.childs.forEach {
                    if (it.group == "a") {
                        FlowRow(
                            modifier = rowModifier,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),

                            ) {
                            it.items.forEach {
                                //it.currentDate=currentDate.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()
                                HomeButton(it) {
                                    onTopicClick(it.title)
                                }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "b") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onTopicClick(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "c") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onTopicClick(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                }
            } else {
                Text(
                    item.parent,
                    fontSize = 26.sp,
                    modifier = modifier.padding(15.dp, 10.dp)
                )
                item.childs.forEach {
                    FlowRow(modifier = rowModifier) {
                        it.items.forEach {
                            HomeButton(it) {
                                onTopicClick(it.title)
                            }
                            Spacer(modifier = chipModifier)
                        }
                    }
                }
            }
        }
    }
}


