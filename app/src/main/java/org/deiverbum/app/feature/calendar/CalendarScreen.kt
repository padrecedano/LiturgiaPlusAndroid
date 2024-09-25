@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package org.deiverbum.app.feature.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.theme.LocalTintTheme
import org.deiverbum.app.feature.home.HomeScreenCalendarFinal
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalLayoutApi
@Composable
internal fun CalendarScreen(
    onTopicClick: (String) -> Unit,
    onDateSelected: (Long?) -> Unit,
    //viewModel: CalendarViewModel = hiltViewModel()
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

@Composable
fun CalendarScreen(
    selectedDate: Int,
    onTopicClick: (String) -> Unit,

    //uiState: CalendarUiState
    viewModel: CalendarViewModel = hiltViewModel(),
    //viewModell: CalendarUniversalisViewModel = hiltViewModel()


) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val universalisState by viewModell.universalisState.collectAsStateWithLifecycle()

    //viewModel.onDateSelected(selectedDate)
    HomeScreenCalendarFinal(
        selectedDate = selectedDate,
        uiState = uiState,
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
    )
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


@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .testTag("bookmarks:empty"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val iconTint = LocalTintTheme.current.iconTint
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_misa),
            colorFilter = if (iconTint != Color.Unspecified) ColorFilter.tint(iconTint) else null,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(id = R.string.feature_bookmarks_empty_error),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.feature_bookmarks_empty_description),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    NiaLoadingWheel(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
            .testTag("calendar:loading"),
        contentDesc = stringResource(id = R.string.feature_bookmarks_loading),
    )
}
