package org.deiverbum.app.feature.home


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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.ui.ChildItem
import org.deiverbum.app.core.model.data.ui.ItemUI
import org.deiverbum.app.core.model.data.ui.MainItem
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.universalis.LoadingState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun populateData(): List<MainItem> {
    val a = ChildItem(
        "a", listOf
            (
            ItemUI(1, "Mixto", 2),
            ItemUI(2, "Oficio", 2),
            ItemUI(3, "Laudes", 2)
        )
    )
    val b = ChildItem(
        "b",
        listOf(
            ItemUI(4, "Tercia", 2),
            ItemUI(5, "Sexta", 2),
            ItemUI(6, "Nona", 2)
        )
    )
    val c = ChildItem(
        "c",
        listOf(
            ItemUI(7, "Vísperas", 2),
            ItemUI(8, "Completas", 2),
        )
    )

    val d = ChildItem(
        "d",
        listOf(
            ItemUI(11, "Lecturas", 2),
            ItemUI(12, "Comentarios", 2),
            ItemUI(13, "Homilías", 2),

            )
    )
    val e = ChildItem(
        "e",
        listOf(
            ItemUI(20, "Santos", 3),
            ItemUI(21, "Rosario", 3),


            )
    )

    return listOf(
        MainItem("Breviario", listOf(a, b, c)),
        MainItem("Misa", listOf(d)),
        MainItem("Otros", listOf(e)),

        )

}

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    onTopicClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    currentDate: StateFlow<LocalDateTime>,
    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentDate = currentDate.collectAsState()

    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onTopicClick = onTopicClick,
        currentDate = currentDate,
    )

}

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier,
    onTopicClick: (String) -> Unit,
    currentDate: State<LocalDateTime>,
) {

    when (uiState) {
        HomeUiState.Empty -> EmptyState(modifier = modifier)
        is HomeUiState.HomeData -> {
            HomeItems(
                onTopicClick = onTopicClick,
                currentDate = currentDate,
                modifier = modifier,
            )
        }

        HomeUiState.Loading -> LoadingState(modifier = modifier)
        is HomeUiState.HomeError -> {
            HomeItems(
                onTopicClick = onTopicClick,
                currentDate = currentDate,
                modifier = modifier,
            )
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun HomeItems(
    onTopicClick: (String) -> Unit,
    modifier: Modifier,
    currentDate: State<LocalDateTime>

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
                                it.currentDate =
                                    currentDate.value.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                                        .toInt()
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
                            HomeButton(it) { onTopicClick(it.title) }
                            Spacer(modifier = chipModifier)
                        }
                    }
                }
            }
        }
    }
}


/**
 * Botón personalizable que muestra los elementos de la pantalla inicial
 * y lanza el lambda [onClick] cuando el composable es seleccionado.
 */
@Composable
fun HomeButton(
    itemUI: ItemUI,
    //currentDate: State<LocalDateTime>,
    onClick: () -> Unit
) {

    AssistChip(
        onClick = {
            onClick()
        },
        label = {
            Text(
                itemUI.title,
                //fontSize = 16.scaledSp(),
                style = NiaTypography.bodyLarge,
                textAlign = TextAlign.Center,
                //modifier =Modifier
                //    .
                //textAlign = TextAlign.Center
            )
        }
    )
}

