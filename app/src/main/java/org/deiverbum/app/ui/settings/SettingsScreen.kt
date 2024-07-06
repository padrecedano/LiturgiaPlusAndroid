@file:OptIn(ExperimentalMaterial3Api::class)

package org.deiverbum.app.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.deiverbum.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenn(
    drawerState: DrawerState,

    //vm: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.configuration),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {}
    }
}

@Composable
fun SettingsScreen(
    vm: SettingsViewModelNIA = hiltViewModel(),
    drawerState: DrawerState,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AaaBbbCcc",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        ) {
            SettingsGroup(name = R.string.lbl_nona) {
                SettingsSwitchComp(
                    name = R.string.lbl_mixto,
                    icon = R.drawable.ic_about,
                    iconDesc = R.string.lbl_office,
                    //state = vm.isSwitchOn.collectAsState()
                ) {
                    //vm.toggleSwitch()
                }
                SettingsTextComp(
                    name = R.string.lbl_office,
                    icon = R.drawable.ic_configuration,
                    iconDesc = R.string.lbl_office,
                    //state = vm.textPreference.collectAsState(),
                    //onSave = { finalText -> vm.saveText(finalText) },
                    //onCheck = { text -> vm.checkTextInput(text) },
                )
            }

            SettingsGroup(name = R.string.label_accept) {
                SettingsNumberComp(
                    name = R.string.lbl_santo_hoy,
                    icon = R.drawable.ic_terms,
                    iconDesc = R.string.lbl_comentarios_hoy,
                    //state = vm.textPreference.collectAsState(),
                    //inputFiler = {text -> filterNumbers(text)}
                    //        onSave = { finalText -> vm.saveNumber(finalText) },
                    //onCheck = { text -> vm.checkNumber(text) },
                )
            }
        }
    }
}