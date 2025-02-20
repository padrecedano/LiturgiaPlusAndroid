package org.deiverbum.app.feature.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.ui.ChildItem
import org.deiverbum.app.core.model.data.ui.ItemUI
import org.deiverbum.app.core.model.data.ui.MainItem
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.universalis.LoadingState
import org.deiverbum.app.ui.NiaAppState

fun populateData(): List<MainItem> {
    var breviariumItems = listOf(ItemUI(0, "Laudes + Lecturas Oficio", 1))
    val a = ChildItem("a", listOf(ItemUI(0, "Mixto", 1)))
    val b = ChildItem(
        "b", listOf
            (
            ItemUI(1, "Mixto", 2),
            ItemUI(2, "Oficio", 2),
            ItemUI(3, "Laudes", 2)
        )
    )
    val c = ChildItem(
        "c",
        listOf(
            ItemUI(4, "Tercia", 2),
            ItemUI(5, "Sexta", 2),
            ItemUI(6, "Nona", 2)
        )
    )
    val d = ChildItem(
        "d",
        listOf(
            ItemUI(7, "Vísperas", 2),
            ItemUI(8, "Completas", 2),
        )
    )

    val e = ChildItem(
        "e",
        listOf(
            ItemUI(11, "Lecturas", 2),
            ItemUI(12, "Comentarios", 2),
            ItemUI(13, "Homilías", 2),

            )
    )
    val f = ChildItem(
        "f",
        listOf(
            ItemUI(20, "Santos", 3),
            ItemUI(21, "Rosario", 3),


            )
    )

    return listOf(
        MainItem("Breviario", listOf(b, c, d)),
        MainItem("Misa", listOf(e)),
        MainItem("Otros", listOf(f)),

        )

}

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    onNextButtonClicked: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    appState: NiaAppState,

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onTopicClick = onNextButtonClicked,
        appState = appState
    )

    /*val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(8.dp))

    val rowModifier = Modifier
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(8.dp))

    Box(
        modifier = Modifier,
    ) {

        LazyColumn(modifier = rowModifier, horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                //TimedLayout()
                //OutlinedCardExample()
                Spacer(modifier = Modifier.height(10.dp))

            }


            items(populateData()) { item ->

                if (item.parent == "Breviario") {
                    Text(item.parent, fontSize = 26.sp)
                    item.childs.forEach {
                        if (it.group == "a") {
                            FlowRow(
                                modifier = rowModifier,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterHorizontally
                                ),

                                ) {
                                it.items.forEach {
                                    HomeButton(it) {
                                        //onNextButtonClicked(it.id.toString())
                                    }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }
                        if (it.group == "b") {
                            FlowRow(
                                //modifier = rowModifier,
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
                                    HomeButton(it) { onNextButtonClicked(it.title) }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }
                        if (it.group == "c") {
                            FlowRow(modifier = rowModifier) {
                                it.items.forEach {
                                    HomeButton(it) { onNextButtonClicked(it.title) }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }
                        if (it.group == "d") {
                            FlowRow(modifier = rowModifier) {
                                it.items.forEach {
                                    HomeButton(it) { onNextButtonClicked(it.title) }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }

                        HorizontalDivider()
                    }
                } else {
                    Text(item.parent, fontSize = 26.sp)
                    item.childs.forEach {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onNextButtonClicked(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }*/
}

@Composable
fun Dataa() {
    TODO("Not yet implemented")
}

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier,
    onTopicClick: (String) -> Unit,
    appState: NiaAppState,
) {

    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(1.dp))

    val rowModifier = Modifier
        .padding(horizontal = 1.dp, vertical = 1.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(1.dp))

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    if (!isOffline) {

        when (uiState) {
            HomeUiState.Empty -> EmptyState(modifier = modifier)
            is HomeUiState.HomeData -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        //TimedLayout()
                        //OutlinedCardExample()

                        Text(
                            text = uiState.topics.data.fecha,
                            modifier = Modifier
                                .padding(2.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    item {
                        Text(
                            text = uiState.topics.data.liturgia!!.nomen,
                            modifier = Modifier
                                .padding(2.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    item {

                        Spacer(modifier = Modifier.height(10.dp))

                    }
                    items(populateData()) { item ->
                        //HorizontalDivider()
                        HorizontalDivider(modifier = Modifier.padding(10.dp, 10.dp))

                        if (item.parent == "Breviario") {
                            Text(
                                item.parent, fontSize = 26.sp, modifier = Modifier
                                    .padding(2.dp)
                            )
                            item.childs.forEach {
                                if (it.group == "a") {
                                    FlowRow(
                                        modifier = rowModifier,
                                        verticalArrangement = Arrangement.spacedBy(14.dp),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            48.dp,
                                            Alignment.CenterHorizontally
                                        ),

                                        ) {
                                        it.items.forEach {
                                            HomeButton(it) {
                                                //onNextButtonClicked(it.id.toString())
                                            }
                                            Spacer(modifier = chipModifier)
                                        }
                                    }
                                }
                                if (it.group == "b") {
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
                                if (it.group == "d") {
                                    FlowRow(modifier = rowModifier) {
                                        it.items.forEach {
                                            HomeButton(it) { onTopicClick(it.title) }
                                            Spacer(modifier = chipModifier)
                                        }
                                    }
                                }

                                //HorizontalDivider()
                            }
                            //HorizontalDivider()

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
                                //HorizontalDivider(modifier=Modifier.padding(10.dp,10.dp))
                            }
                            //HorizontalDivider()

                        }
                    }
                }

            }

            HomeUiState.Loading -> LoadingState(modifier = modifier)
            is HomeUiState.HomeError -> HomeScreenNoDateInfo(
                onTopicClick,
                chipModifier,
                rowModifier,
                modifier
            )
        }
    } else {
        HomeScreenNoDateInfo(onTopicClick, chipModifier, rowModifier, modifier)
    }


}

@ExperimentalLayoutApi
@Composable
fun HomeScreenNoDateInfo(
    onTopicClick: (String) -> Unit,
    chipModifier: Modifier,
    rowModifier: Modifier,
    modifier: Modifier
) {

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Spacer(modifier = Modifier.height(10.dp))

        }
        items(populateData()) { item ->
            //HorizontalDivider()
            HorizontalDivider(modifier = Modifier.padding(10.dp, 10.dp))

            if (item.parent == "Breviario") {
                Text(
                    item.parent, fontSize = 26.sp, modifier = Modifier
                        .padding(2.dp)
                )
                item.childs.forEach {
                    if (it.group == "a") {
                        FlowRow(
                            modifier = rowModifier,
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                48.dp,
                                Alignment.CenterHorizontally
                            ),

                            ) {
                            it.items.forEach {
                                HomeButton(it) {
                                    //onNextButtonClicked(it.id.toString())
                                }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "b") {
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
                    if (it.group == "d") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onTopicClick(it.title) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }

                    //HorizontalDivider()
                }
                //HorizontalDivider()

            } else {
                Text(item.parent, fontSize = 26.sp, modifier = modifier.padding(15.dp, 10.dp))
                item.childs.forEach {
                    FlowRow(modifier = rowModifier) {
                        it.items.forEach {
                            HomeButton(it) { onTopicClick(it.title) }
                            Spacer(modifier = chipModifier)
                        }
                    }
                    //HorizontalDivider(modifier=Modifier.padding(10.dp,10.dp))
                }
                //HorizontalDivider()

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

@Composable
private fun TimedLayout() {
    var show by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        delay(10000)
        show = false
    }
    Column(modifier = Modifier.fillMaxSize()) {
        //Text("Box showing: $show")
        if (show) {
            // OutlinedCardExample()
        }
    }
}

@Composable
fun OutlinedCardExample() {
    OutlinedCard(
        /*colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),*/
        //border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "21 de septiembre 2024\nMiércoles de la XXXIV del Tiempo Ordinario (impar)\nSan Mateo, apóstol",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Left,
        )
    }
}
