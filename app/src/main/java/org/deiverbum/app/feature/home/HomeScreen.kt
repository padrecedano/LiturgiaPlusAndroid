package org.deiverbum.app.feature.home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.deiverbum.app.core.model.data.ui.ItemUI
import org.deiverbum.app.core.model.data.ui.ItemUICollection
import org.deiverbum.app.feature.calendar.CalendarUiState
import org.deiverbum.app.feature.calendar.DatePickerModal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun populateData(): List<MainItem> {
    var breviariumItems = listOf(ItemUI(0, "Laudes + Lecturas Oficio", 1, ""))
    var screenItems = ItemUICollection("Breviario", breviariumItems)
    val a = ChildItem("a", listOf(ItemUI(0, "Mixto", 1, "")))
    val b = ChildItem(
        "b", listOf
            (
            ItemUI(0, "Mixto", 2, ""),
            ItemUI(1, "Oficio", 2, ""),
            ItemUI(2, "Laudes", 2, "")
        )
    )
    val c = ChildItem(
        "c",
        listOf(
            ItemUI(3, "Tercia", 2, ""),
            ItemUI(4, "Sexta", 2, ""),
            ItemUI(5, "Nona", 2, "")
        )
    )
    val d = ChildItem(
        "d",
        listOf(
            ItemUI(6, "Vísperas", 2, ""),
            ItemUI(7, "Completas", 2, ""),
        )
    )

    val e = ChildItem(
        "e",
        listOf(
            ItemUI(8, "Comentarios", 2, ""),
            ItemUI(9, "Homilías", 2, ""),
            ItemUI(10, "Lecturas", 2, ""),

            )
    )
    val f = ChildItem(
        "f",
        listOf(
            ItemUI(20, "Santos", 3, ""),
            ItemUI(21, "Rosario", 3, ""),

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
fun HomeScreenCalendarNew(
    onNextButtonClicked: (String) -> Unit,
    onDateSelected: (Long) -> Unit
) {

    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(8.dp))

    val rowModifier = Modifier
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(8.dp))

    Box(
        modifier = Modifier,
    ) {

        LazyColumn(modifier = rowModifier) {
            item {
                //TimedLayout()
                // OutlinedCardExample()
                Spacer(modifier = Modifier.height(10.dp))

            }


            items(populateData()) { item ->

                if (item.parent == "Breviario") {
                    Text(item.parent, fontSize = 26.sp)
                    item.childs.forEach {
                        if (it.group == "a") {
                            FlowRow(modifier = rowModifier) {
                                it.items.forEach {
                                    HomeButton(it) {
                                        onNextButtonClicked(it.id.toString())
                                        onDateSelected
                                    }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }
                        if (it.group == "b") {
                            FlowRow(modifier = rowModifier) {
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
    }
}

@ExperimentalLayoutApi
@Composable
fun HomeScreenCalendarFinal(
    selectedDate: Int,

    onTopicClick: (String) -> Unit,
    //onDateSelected: (Int?) -> Unit,
    uiState: CalendarUiState,
    //universalisUiState: UniversalisUiState,
    //viewModel: CalendarViewModel = hiltViewModel()
    //viewModel: CalendarUniversalisViewModel = hiltViewModel()

) {

    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val universalisState by viewModel.universalisState.collectAsStateWithLifecycle()

    var showTopics by remember { mutableStateOf(false) }

    when (uiState) {
        CalendarUiState.Loading -> {
            Text(
                "\n\n\n" +
                        "\n\n" +
                        "\nLoad"
            )
            Text("\t\t\t\t\t\t$selectedDate")
            showTopics = true
        }

        is CalendarUiState.CalendarData -> {
            showTopics = true
            Text(
                "\n" +
                        "\n\n" +
                        "\n\n" +
                        "\nData:${uiState.topics.fecha}"
            )
            //Text(text = uiState.topics.fecha)

        }

        CalendarUiState.Empty -> TODO()
        CalendarUiState.Error -> TODO()
    }

    if (showTopics) {
        val chipModifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))

        val rowModifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .heightIn(max = 500.dp)
            .clip(RoundedCornerShape(8.dp))

        Box(
            modifier = Modifier,
        ) {

            LazyColumn(modifier = rowModifier) {
                item {
                    //TimedLayout()
                    //OutlinedCardExample()
                    Spacer(modifier = Modifier.height(10.dp))

                }


                items(populateData()) { item ->

                    if (item.parent == "Breviario") {
                        Text(item.parent, fontSize = 26.sp)
                        item.childs.forEach { it ->
                            if (it.group == "a") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        HomeButton(it) {
                                            onTopicClick(it.id.toString())

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
                            if (it.group == "d") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        HomeButton(it) { onTopicClick(it.title) }
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
                                    HomeButton(it) { onTopicClick(it.title) }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }


}

@ExperimentalLayoutApi
@Composable
fun HomeScreenCalendar(
    onTopicClick: (String) -> Unit,
    onDateSelected: (Int?) -> Unit,
    //uiState: CalendarUiState
) {
    var showTopics by remember { mutableStateOf(true) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    /*
        when (uiState) {
            CalendarUiState.Empty -> Text("Empty")
            CalendarUiState.Error -> Text("Error")
            CalendarUiState.Loading -> {
                Text("Loading")
                //showDatePicker=true
            }
            is CalendarUiState.CalendarData -> {
                //showDatePicker=false
                val t=LoremIpsum()
                /*if(uiState.topics.isNotEmpty()) {
                    showTopics=false
                    Text(uiState.topics[0].data[0].getAllForView())
                }else{
                    Text(t.values.last())
                    //¿?
                }*/

            }


        }
    */

    if (selectedDate != null || 1 == 2) {
        val date = Date(selectedDate!!)
        val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        Text("Selected date: $formattedDate")
    } else {
        Text("No date selected")
    }
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
                onDateSelected(it!!.toInt())
                //viewModel.onDateSelected(selectedDate)
                showDatePicker = false
                showTopics = true
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTopics) {
        val chipModifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))

        val rowModifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .heightIn(max = 500.dp)
            .clip(RoundedCornerShape(8.dp))

        Box(
            modifier = Modifier,
        ) {

            LazyColumn(modifier = rowModifier) {
                item {
                    //TimedLayout()
                    //OutlinedCardExample()
                    Spacer(modifier = Modifier.height(10.dp))

                }


                items(populateData()) { item ->

                    if (item.parent == "Breviario") {
                        Text(item.parent, fontSize = 26.sp)
                        item.childs.forEach { it ->
                            if (it.group == "a") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        HomeButton(it) {
                                            onTopicClick(it.id.toString())
                                            onDateSelected
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
                            if (it.group == "d") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        HomeButton(it) { onTopicClick(it.title) }
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
                                    HomeButton(it) { onTopicClick(it.title) }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun HomeScreen(
    onNextButtonClicked: (String) -> Unit
) {

    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(8.dp))

    val rowModifier = Modifier
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(8.dp))

    Box(
        modifier = Modifier,
    ) {

        LazyColumn(modifier = rowModifier) {
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
                            FlowRow(modifier = rowModifier) {
                                it.items.forEach {
                                    HomeButton(it) {
                                        //onNextButtonClicked(it.id.toString())
                                    }
                                    Spacer(modifier = chipModifier)
                                }
                            }
                        }
                        if (it.group == "b") {
                            FlowRow(modifier = rowModifier) {
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
        onClick = { onClick() },
        label = { Text(itemUI.title) }
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

data class MainItem(var parent: String, var childs: List<ChildItem>)
data class ChildItem(var group: String, var items: List<ItemUI>)




