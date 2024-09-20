package org.deiverbum.app.feature.calendar

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalCoroutinesApi
@Composable
internal fun CalendarRoute(
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: ForYouViewModel = hiltViewModel(),
) {
    /*val onboardingUiState by viewModel.onboardingUiState.collectAsStateWithLifecycle()
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()
    val deepLinkedUserNewsResource by viewModel.deepLinkedNewsResource.collectAsStateWithLifecycle()
*/
    var showModal by remember { mutableStateOf(true) }
// [END_EXCLUDE]
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    if (selectedDate != null) {
        val date = Date(selectedDate!!)
        val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        Text("Selected date: $formattedDate")
    } else {
        Text("No date selected")
    }

    if (showModal) {
// [END_EXCLUDE]
        DatePickerModal(
            onDateSelected = {
                selectedDate = it
                showModal = false
            },
            onDismiss = { showModal = false }
        )
    }

    /*DatePickerModalInput(
        onDateSelected = onDateSelected,
        onDismiss = {}
    )*/
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
// [END android_compose_components_datepicker_modal]

