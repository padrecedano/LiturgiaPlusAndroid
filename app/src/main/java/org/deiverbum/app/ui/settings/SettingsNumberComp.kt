package org.deiverbum.app.ui.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R

@Composable
fun SettingsNumberComp(
    @DrawableRes icon: Int,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    //state: State<String>,
    //onSave: (String) -> Unit,
    //inputFilter: (String) -> String, // input filter for the preference
    //onCheck: (String) -> Boolean
) {

    var isDialogShown by remember {
        mutableStateOf(false)
    }

    if (isDialogShown) {
        /*Dialog(onDismissRequest = { isDialogShown = isDialogShown.not() }) {
            TextEditDialog(name, state, inputFilter, onSave, onCheck) {
                isDialogShown = isDialogShown.not()
            }
        }*/
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {
            isDialogShown = isDialogShown.not()
        },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = stringResource(id = iconDesc),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = stringResource(id = name),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "state.value",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                    )
                }
            }
            Divider()
        }
    }
}

fun TextEditDialog(
    name: Int,
    storedValue: State<String>,
    onSave: (String) -> String,
    onCheck: (String) -> Unit,
    onDismiss: (String) -> Boolean,
    function: () -> Unit
) {

}

@Composable
private fun TextEditNumberDialog(
    @StringRes name: Int,
    storedValue: State<String>,
    inputFilter: (String) -> String, // filters out not needed letters
    onSave: (String) -> Unit,
    onCheck: (String) -> Boolean,
    onDismiss: () -> Unit
) {

    var currentInput by remember {
        mutableStateOf(TextFieldValue(storedValue.value))
    }

    var isValid by remember {
        mutableStateOf(onCheck(storedValue.value))
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceTint
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(id = name))
            Spacer(modifier = Modifier.height(8.dp))
            TextField(currentInput,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    // filters the input and removes redundant numbers
                    val filteredText = inputFilter(it.text)
                    isValid = onCheck(filteredText)
                    currentInput = TextFieldValue(filteredText)
                })
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    onSave(currentInput.text)
                    onDismiss()
                }, enabled = isValid) {
                    Text(stringResource(id = R.string.audio_resume))
                }
            }
        }
    }
}

@Composable
private fun TextEditDialoggg(
    @StringRes name: Int,
    storedValue: State<String>,
    onSave: (String) -> Unit,
    onCheck: (String) -> Boolean,
    onDismiss: () -> Unit // internal method to dismiss dialog from within
) {

    // storage for new input
    var currentInput by remember {
        mutableStateOf(TextFieldValue(storedValue.value))
    }

    // if the input is valid - run the method for current value
    var isValid by remember {
        mutableStateOf(onCheck(storedValue.value))
    }

    Surface(
        color = MaterialTheme.colorScheme.surfaceTint
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(stringResource(id = name))
            Spacer(modifier = Modifier.height(8.dp))
            TextField(currentInput, onValueChange = {
                // check on change, if the value is valid
                isValid = onCheck(it.text)
                currentInput = it
            })
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    // save and dismiss the dialog
                    onSave(currentInput.text)
                    onDismiss()
                    // disable / enable the button
                }, enabled = isValid) {
                    Text(stringResource(id = R.string.audio_resume))
                }
            }
        }
    }
}
