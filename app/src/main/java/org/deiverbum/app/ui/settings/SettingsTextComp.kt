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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SettingsTextComp(
    @DrawableRes icon: Int,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    //state: State<String>, // current value
    //onSave: (String) -> Unit, // method to save the new value
    //onCheck: (String) -> Boolean // check if new value is valid to save
) {

    // if the dialog is visible
    var isDialogShown by remember {
        mutableStateOf(false)
    }

    // conditional visibility in dependence to state
    if (isDialogShown) {
        Dialog(onDismissRequest = {
            // dismiss the dialog on touch outside
            isDialogShown = false
        }) {
            /*TextEditDialog(name, state, onSave, onCheck) {
                // to dismiss dialog from within
                isDialogShown = false
            }*/
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {
            // clicking on the preference, will show the dialog
            isDialogShown = true
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
                    // setting text title
                    Text(
                        text = stringResource(id = name),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // current value shown
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