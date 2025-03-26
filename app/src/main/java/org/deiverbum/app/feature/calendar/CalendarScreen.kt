@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package org.deiverbum.app.feature.calendar

import LPlusIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.feature.home.HomeButton
import org.deiverbum.app.feature.home.populateData
import org.deiverbum.app.util.DateTimeUtil
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InterestsRoute(
    onTopicClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    shouldHighlightSelectedTopic: Boolean = false,
    viewModel: CalendarUniversalisViewModel = hiltViewModel(),
) {
    val uiState by viewModel.universalisState.collectAsStateWithLifecycle()
    HomeItemss(
        uiState = uiState,
        onTopicClick = onTopicClick,
        //currentTimeZone = currentTimeZone,
        //currentDate = currentDate,
        modifier = modifier,
        haveDate = true
    )

}

@Composable
internal fun InterestsScreen(
    uiState: CalendarUniversalisUiState,
    onTopicClick: (String) -> Unit,
) {
    //Text("aaaaaa")
    val randomNumberr = listOf(1, 2, 3, 4, 5, 6).random()
    val randomNumber = listOf("Oficio", "Laudes", "Tercia").random()

    TextButton(
        onClick = {
            onTopicClick(randomNumber.toString())
        }
    ) {
        Text("Text Button")
    }
    when (uiState) {
        CalendarUniversalisUiState.Empty -> {
            Text("empty")
        }

        CalendarUniversalisUiState.Error -> Text("Error")
        CalendarUniversalisUiState.Loading -> Text("Loading")
        is CalendarUniversalisUiState.UniversalisData -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            )
            {

                Text(uiState.selectedTopicId!!)
                Text(uiState.topics.data.fecha)

                //Text(uiState.topics.data.getAllForView())
            }
        }
    }
}


@ExperimentalLayoutApi
@Composable
internal fun CalendarScreenn(
    onTopicClick: (String) -> Unit,
    onDateSelected: (Long?) -> Unit,
    viewModel: CalendarUniversalisViewModel = hiltViewModel()
) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val selectedTopicId by viewModel.selectedTopicIdd.collectAsStateWithLifecycle()
    //val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()


    DatePickerAndButtons(
        //uiState = uiState,
        onTopicClick = onTopicClick,
        //onDateSelected = viewModel::onDateSelected,

    )


}

@ExperimentalLayoutApi
@Composable
internal fun TestScreen(
    onTopicClick: (String) -> Unit,
    viewModel: CalendarUniversalisViewModel = hiltViewModel()
) {
    val uiState by viewModel.universalisState.collectAsStateWithLifecycle()
    TestScreen(onTopicClick = { viewModel.onTopicClick(it, 1) })
//val selectedTopicId by viewModel.selectedTopicIdd.collectAsStateWithLifecycle()
    //val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
}

@Composable
fun TestScreen() {
    Text("aaaa")
}

@ExperimentalLayoutApi
@Composable
internal fun CalendarScreen(
    onTopicClick: (String) -> Unit,
    onDateSelected: (Long?) -> Unit,
    viewModel: CalendarUniversalisViewModel = hiltViewModel()
) {
    val uiState by viewModel.universalisState.collectAsStateWithLifecycle()
    //val selectedTopicId by viewModel.selectedTopicIdd.collectAsStateWithLifecycle()
    //val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()


    //DatePickerModal(onDateSelected=onDateSelected, onDismiss = {})

    /*DatePickerFieldToModal(uiState=uiState,onTopicClick=onTopicClick
        /*onTopicClick={
            viewModel.onTopicClick(it)
            onTopicClick(it)
        }*/
    )*/


}

@ExperimentalLayoutApi
@Composable
fun CalendarScreen(
    selectedDate: Int,
    onTopicClick: (String) -> Unit,

    //uiState: CalendarUiState
    //viewModel: CalendarViewModel = hiltViewModel(),
    //viewModell: CalendarUniversalisViewModel = hiltViewModel()


) {
    //val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val universalisState by viewModell.universalisState.collectAsStateWithLifecycle()

    //viewModel.onDateSelected(selectedDate)
    /*HomeScreenCalendarFinal(
        selectedDate = selectedDate,
        //uiState = uiState,
        //universalisUiState=universalisState,
        onTopicClick = onTopicClick,
        /* onTopicClick = {
             //viewModell.onTopicClickCalendar(it, selectedDate)

             //viewModell::onTopicClickCalendar
             viewModel.onTopicClickk(it, selectedDate)
             //viewModell.onTopicClickCalendar(it, selectedDate)

             onTopicClick(it)
             //onTopicClick(it,selectedDate)}
         }*/
    )*/
    /*
        when(uiState){
            CalendarUiState.Loading -> Text("\n\n\n" +
                    "\n\n" +
                    "\nLoad")
            is CalendarUiState.CalendarData -> {
                Text("\n" +
                        "\n\n" +
                        "\n\n" +
                        "\nData:${(uiState as CalendarUiState.CalendarData).topics.fecha}"
                )
                //Text(text = uiState.topics.fecha)

            }
            CalendarUiState.Empty -> TODO()
            CalendarUiState.Error -> TODO()
        }
       */

}

@ExperimentalLayoutApi
@Composable
fun DatePickerAndButtons(
    onTopicClick: (String) -> Unit,
    //onDateSelected: (Int?) -> Unit,
    //uiState: CalendarUiState,
    //selectedDatee: Int?
    //viewModel: CalendarViewModel
) {
    var showTopics by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    if (selectedDate != null) {
        val date = Date(selectedDate!!)
        val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        val dateInt = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(date).toInt()

        //Text("Selected date: $formattedDate\n\n")
        showDatePicker = false
        CalendarScreen(
            selectedDate = dateInt,
            onTopicClick = onTopicClick
            //uiState=uiState
        )
    } else {
        Text("No date selected")
    }
    if (showDatePicker) {
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
// [START android_compose_components_datepicker_modal]
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@ExperimentalMaterial3Api
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    //onDateSelected: () -> Unit
    onValueChange: (String) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(true) }
    val datePickerState = rememberDatePickerState()
    var showMenu by remember { mutableStateOf(false) }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {
                onValueChange
            },
            //showMenu = true
            //showDatePicker=false
            //},
            label = { Text("DOB") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    showDatePicker = !showDatePicker
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Select date*"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            //DropDownDemo()
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(

                        state = datePickerState,
                        showModeToggle = false,

                        )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    Timber.d(formatter.format(Date(millis)))
    return formatter.format(Date(millis))
}

//TODO: Reubicar
@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("bookmarks:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = stringResource(id = R.string.feature_bookmarks_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

    }
}

//TODO: Reubicar
@Composable
fun ErrorState(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("bookmarks:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = message,//stringResource(id = R.string.feature_bookmarks_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

    }
}


@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    NiaLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .testTag("calendar:loading"),
        contentDesc = stringResource(id = R.string.feature_bookmarks_loading),
    )
}

@ExperimentalLayoutApi
@Composable
fun HomeScreenCalendarFinal(
    selectedDate: Int,
    onTopicClick: (String, Int) -> Unit,
    //uiState: CalendarUiState,

) {

    val showTopics by remember { mutableStateOf(true) }
    if (showTopics) {
        val chipModifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))

        val rowModifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .heightIn(max = 500.dp)
            .clip(RoundedCornerShape(8.dp))


        LazyColumn(modifier = Modifier.height(100.dp)) {
                item {
                    //TimedLayout()
                    //OutlinedCardExample()
                    Spacer(modifier = Modifier.height(10.dp))

                }

                items(populateData()) { item ->

                    if (item.parent == "Breviario") {
                        Text(item.parent)
                        item.childs.forEach { it ->
                            if (it.group == "a") {
                                FlowRow(
                                    modifier = Modifier
                                        .safeDrawingPadding()
                                        .fillMaxWidth(1f)
                                        // .padding(16.dp)

                                        //.wrapContentHeight(align = Alignment.Center)
                                        .verticalScroll(rememberScrollState()),
                                    verticalArrangement = Arrangement.spacedBy(14.dp),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        48.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    //modifier = rowModifier,
                                    //verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)

                                ) {
                                    repeat(it.items.size) {
                                        /*HomeButton(it, currentDate) {
                                                                            onTopicClick(it.id.toString(),selectedDate)

                                                                        }*/
                                        Spacer(modifier = chipModifier)
                                    }
                                }
                            }
                            if (it.group == "b") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        HomeButton(it) {
                                            onTopicClick(it.title, selectedDate)
                                        }
                                        Spacer(modifier = chipModifier)
                                    }
                                }
                            }
                            if (it.group == "c") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        //HomeButton(it) { onTopicClick(it.title) }
                                        Spacer(modifier = chipModifier)
                                    }
                                }
                            }
                            if (it.group == "d") {
                                FlowRow(modifier = rowModifier) {
                                    it.items.forEach {
                                        //HomeButton(it) { onTopicClick(it.title) }
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
                                    //HomeButton(it) { onTopicClick(it.title) }
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

//@Preview
@Composable
fun DatePickerFieldToModal(
    uiState: CalendarUniversalisUiState,
    modifier: Modifier = Modifier,
    onTopicClick: (String, Int) -> Unit
) {
    var selectedDate by remember {
        mutableStateOf<Long?>(
            DateTimeUtil.getToday().toEpochMilliseconds()
        )
    }
    var showModal by remember { mutableStateOf(false) }
    val currentDate = selectedDate?.let { convertMillisToDate(it) } ?: ""

    /*Row(verticalAlignment = Alignment.CenterVertically) {*/
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Column(modifier = Modifier.weight(1f)) {
        item {
            OutlinedTextField(
                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {
                    var a = 1
                },
                label = { Text("DOB") },
                placeholder = { Text("MM/DD/YYYY") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .pointerInput(selectedDate) {
                        awaitEachGesture {
                            // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                            // in the Initial pass to observe events before the text field consumes them
                            // in the Main pass.
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent =
                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                showModal = true
                            }
                        }
                    }
            )

        }
        item {
            HomeScreenCalendarFinal(currentDate.toInt(), onTopicClick)
        }
        item {
            AssistChipExample(
                title = "Mixto",
                selectedDate = currentDate,
                onTopicClick = onTopicClick
            )
            AssistChipExample(
                title = "Oficio",
                selectedDate = currentDate,
                onTopicClick = onTopicClick
            )
            AssistChipExample(
                title = "Laudes",
                selectedDate = currentDate,
                onTopicClick = onTopicClick
            )
        }

    }

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

@Composable
fun AssistChipExample(title: String, selectedDate: String, onTopicClick: (String, Int) -> Unit) {
    AssistChip(
        onClick = {
            onTopicClick(title, selectedDate.toInt())
        },
        label = { Text(title) },
        leadingIcon = {
            Icon(
                LPlusIcons.Bookmark,
                contentDescription = "Localized description",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}

fun convertMillisToDatee(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@ExperimentalLayoutApi
@Composable
fun HomeItemss(
    uiState: CalendarUniversalisUiState,
    //onTopicClick: (String) -> Unit,
    onTopicClick: (String, Int) -> Unit,

    modifier: Modifier,
    haveDate: Boolean = false,
    //currentTimeZone: State<TimeZone>,
    //currentDate: State<LocalDateTime>

) {
    val chipModifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(1.dp))

    val rowModifier = Modifier
        .padding(horizontal = 1.dp, vertical = 1.dp)
        .heightIn(max = 500.dp)
        .clip(RoundedCornerShape(1.dp))


    var selectedDate by remember { mutableStateOf<Long?>(null) }

    var showModal by remember { mutableStateOf(false) }
    val currentDate =
        selectedDate?.let { convertMillisToDate(it) } ?: DateTimeUtil.getTodayDate().toString()
    val state = rememberSaveable(currentDate) { mutableStateOf(currentDate) }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (haveDate) {
            item {
                OutlinedTextField(
                    value = state.value,//selectedDate?.let { convertMillisToDate(it) } ?: DateTimeUtil.getTodayDate().toString(),
                    onValueChange = {
                        var a = 1
                    },
                    label = { Text("Fecha") },
                    placeholder = { Text("MM/DD/YYYY") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .pointerInput(selectedDate) {
                            awaitEachGesture {
                                // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                // in the Initial pass to observe events before the text field consumes them
                                // in the Main pass.
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showModal = true
                                }
                            }
                        }
                )

            }


            item {
                Text(
                    text = "Selecciona la celebración ...",
                    modifier = Modifier
                        .padding(2.dp),
                    textAlign = TextAlign.Center,
                )
            }
            item {
                Text(
                    text = "data.topics.data.liturgia!!.nomen",
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
                                //it.currentDate=currentDate.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()
                                HomeButton(it) { onTopicClick(it.title, state.value.toInt()) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "b") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onTopicClick(it.title, state.value.toInt()) }
                                Spacer(modifier = chipModifier)
                            }
                        }
                    }
                    if (it.group == "c") {
                        FlowRow(modifier = rowModifier) {
                            it.items.forEach {
                                HomeButton(it) { onTopicClick(it.title, state.value.toInt()) }
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
                            HomeButton(it) { onTopicClick(it.title, state.value.toInt()) }
                            Spacer(modifier = chipModifier)
                        }
                    }
                }
            }
        }
    }


    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }

}
