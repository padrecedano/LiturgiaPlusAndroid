package org.deiverbum.app.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.model.data.ui.SwitchItem
import org.deiverbum.app.feature.settings.UserEditableSettings

@Composable
fun LPlusSwitch(
    switch: SwitchItem,
    onChangeSwitch: (switch: SwitchItem, isChecked: Boolean, useAnalytics: Boolean) -> Unit,
    settings: UserEditableSettings
) {
    var checked by remember { mutableStateOf(switch.checked) }
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = switch.label)
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onChangeSwitch(switch, checked, settings.dynamic.useAnalytics.name == "ON")
            },
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}

@Composable
fun SwitchWithLabel(label: String, state: Boolean, onStateChange: (Boolean) -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                // This is for removing ripple when Row is clicked
                indication = null,
                role = Role.Switch,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Text(text = label)
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Switch(
            checked = state,
            onCheckedChange = {
                onStateChange(it)
            }
        )
    }
}