package org.deiverbum.app.feature.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.HorizontalDivider
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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaZoneId
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.data.ui.ChildItem
import org.deiverbum.app.core.model.data.ui.ItemUI
import org.deiverbum.app.core.model.data.ui.MainItem
import org.deiverbum.app.core.ui.LocalTimeZone
import org.deiverbum.app.feature.calendar.EmptyState
import org.deiverbum.app.feature.universalis.LoadingState
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
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
    onNextButtonClicked: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    currentTimeZone: StateFlow<TimeZone>,
    currentDate: StateFlow<LocalDateTime>,
    //onTopicCheckedChanged: (String, Boolean) -> Unit,
    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val currentTime = viewModel.timer.collectAsState()
    val ctz = currentTimeZone.collectAsState()
    val currentDate = currentDate.collectAsState()
    val test = LocalTimeZone.current.toJavaZoneId()
    /*LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        // refresh your data
        viewModel.updateTopicSelection("a",false)
    }*/
    HomeScreen(
        uiState = uiState,
        modifier = modifier,
        onTopicClick = onNextButtonClicked,
        currentTimeZone = ctz,
        currentDate = currentDate,
        //onTopicCheckedChanged = viewModel::updateTopicSelection,

    )
    val z = ZoneId.systemDefault()
    val zi = ZoneId.of(ctz.value.id)

    val time = ZonedDateTime.now(zi)
    //val zdt = ZonedDateTime..atZone(z)


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

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier,
    onTopicClick: (String) -> Unit,
    currentTimeZone: State<TimeZone>,
    currentDate: State<LocalDateTime>,
    //onTopicCheckedChanged: (String, Boolean) -> Unit,
) {

    when (uiState) {
        HomeUiState.Empty -> EmptyState(modifier = modifier)
        is HomeUiState.HomeData -> {
            HomeItems(
                uiState = uiState,
                onTopicClick = onTopicClick,
                currentTimeZone = currentTimeZone,
                currentDate = currentDate,
                modifier = modifier,
                haveDate = true
            )
        }

        HomeUiState.Loading -> LoadingState(modifier = modifier)
        is HomeUiState.HomeError -> {
            HomeItems(
                uiState = uiState,
                onTopicClick = onTopicClick,
                currentTimeZone = currentTimeZone,
                currentDate = currentDate,
                modifier = modifier,
                haveDate = false
            )
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun HomeItems(
    uiState: HomeUiState,
    onTopicClick: (String) -> Unit,
    modifier: Modifier,
    haveDate: Boolean = false,
    currentTimeZone: State<TimeZone>,
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

        if (haveDate) {
            val data = uiState as HomeUiState.HomeData
            val zi = ZoneId.of(currentTimeZone.value.id)
            val time = ZonedDateTime.now(zi)
            item {
                Text("New ${data.selectedDate}")
            }
            item {
                Text(currentTimeZone.value.id)
            }
            item {
                val t = time.format(DateTimeFormatter.ofPattern("dd.MMMM yyyy HH:mm:ss"))
                Text("This changes always: ${t}")
            }
            item {
                Text(currentDate.value.format(DateTimeFormatter.ofPattern("dd.MMMM yyyy HH:mm:ss")))
            }
            item {
                Text(
                    text = data.topics.data.fecha,
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                )
            }
            item {
                Text(
                    text = data.topics.data.liturgia!!.nomen,
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(modifier = Modifier.padding(10.dp, 10.dp))
            }
        }
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
                                HomeButton(it) { onTopicClick(it.title) }
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

