@file:Suppress("ktlint:standard:max-line-length")

package org.deiverbum.app.feature.menu

import LPlusIcons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.MenuButton
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.supportsDynamicTheming
import org.deiverbum.app.core.model.data.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.data.configuration.ThemeBrand
import org.deiverbum.app.core.model.data.configuration.VoiceReaderConfig
import org.deiverbum.app.core.model.data.ui.MenuItem
import org.deiverbum.app.core.ui.TrackScreenViewEvent
import org.deiverbum.app.feature.settings.SettingsViewModel
import org.deiverbum.app.util.Configuration.MY_EMAIL


@Composable
fun MainMenuDialog(
    onDismiss: () -> Unit,
    onClick: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    MainMenuDialog(
        onDismiss = onDismiss,
        onClick = onClick,
        //settingsUiState = settingsUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeVoiceReaderPreference = viewModel::updateVoiceReaderPreference,
        onChangeMultipleInvitatoryPreference = viewModel::updateMultipleInvitatoryPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@Composable
fun MainMenuDialog(
    //settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
    onClick: (String) -> Unit,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeVoiceReaderPreference: (useVoiceReader: VoiceReaderConfig) -> Unit,
    onChangeMultipleInvitatoryPreference: (useMultipleInvitatory: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    val configuration = LocalConfiguration.current

    /**
     * usePlatformDefaultWidth = false is use as a temporary fix to allow
     * height recalculation during recomposition. This, however, causes
     * Dialog's to occupy full width in Compact mode. Therefore max width
     * is configured below. This should be removed when there's fix to
     * https://issuetracker.google.com/issues/221643630
     */
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.feature_menu_title),
                style = NiaTypography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                MenuPanel(
                    //settings = settingsUiState.settings,
                    supportDynamicColor = supportDynamicColor,
                    onChangeThemeBrand = onChangeThemeBrand,
                    onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                    onChangeVoiceReaderPreference = onChangeVoiceReaderPreference,
                    onChangeMultipleInvitatoryPreference = onChangeMultipleInvitatoryPreference,
                    onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                    onClick = onClick,

                    onDismiss = onDismiss,
                )

                HorizontalDivider(Modifier.padding(top = 8.dp))
                AppNamePanel()
            }
            TrackScreenViewEvent(screenName = "Menu")
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.feature_settings_dismiss_dialog_button_text),
                style = NiaTypography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onDismiss()
                    },
            )
        },
    )
}

// [ColumnScope] is used for using the [ColumnScope.AnimatedVisibility] extension overload composable.
@Composable
private fun MenuPanel(
    //settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeVoiceReaderPreference: (useVoiceReader: VoiceReaderConfig) -> Unit,
    onChangeMultipleInvitatoryPreference: (useMultipleInvitatory: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    onClick: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val menuItems = listOf(
        MenuItem(title = stringResource(R.string.feature_menu_about), icon = LPlusIcons.About, 10),
        MenuItem(
            title = stringResource(R.string.feature_menu_author),
            icon = LPlusIcons.Author,
            11
        ),
        MenuItem(title = stringResource(R.string.feature_menu_new), icon = LPlusIcons.News, 12),

        //TODO: Fusionar contenido con
        // About y ¿Autor? y ¿Agradecimientos?
        // MenuItem(title = stringResource(R.string.feature_menu_help), icon = LPlusIcons.Help, 1),
        MenuItem(title = stringResource(R.string.feature_menu_sync), icon = LPlusIcons.Sync, 2),
        MenuItem(
            title = stringResource(R.string.feature_menu_settings),
            icon = LPlusIcons.Build,
            21
        ),
        MenuItem(title = stringResource(R.string.feature_menu_bugs), icon = LPlusIcons.Bug, 22),
        //MenuItem(title = stringResource(R.string.feature_menu_author), icon = LPlusIcons.Person, 1),
        //MenuItem(title = stringResource(R.string.feature_menu_thanks), icon = LPlusIcons.Thanks, 1),
        MenuItem(title = stringResource(R.string.feature_menu_share), icon = LPlusIcons.Share, 3),
        MenuItem(
            title = stringResource(R.string.feature_menu_play_store),
            icon = LPlusIcons.PlayStore,
            31
        ),
        MenuItem(
            title = stringResource(R.string.feature_settings_privacy_policy),
            icon = LPlusIcons.Privacy,
            4
        ),
        MenuItem(
            title = stringResource(R.string.feature_settings_terms),
            icon = LPlusIcons.Terms,
            41
        ),


        )
    Column(
        Modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.Bottom)
    ) {
        menuItems.forEach {
            if (it.group < 5) {
                HorizontalDivider()
                //MenuDialogItemRow(item = it, onClick = onClick)
            }
            MenuDialogItemRow(
                item = it,
                onClick = { onClick(it.title) },
                onDismiss = onDismiss

            )
        }

    }
}


@Composable
fun MenuDialogItemRow(
    item: MenuItem,
    onClick: () -> Unit,
    onDismiss: () -> Unit,

    ) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
        )
        if (item.title == "Ver en Play Store") {
            MenuButton {
                Text(text = item.title, style = NiaTypography.bodyLarge)
            }
        } else {
            MenuButton(
                onClick = onClick,
                onDismiss = onDismiss
            ) {
                Text(text = item.title, style = NiaTypography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AppNamePanel() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            //alignment = Alignment.Bottom,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = buildString {
                append(stringResource(R.string.app_name))
                append(" v. ")
                append(stringResource(R.string.app_version_and_name))
                append("\n")
                append(MY_EMAIL)
            },
            style = NiaTypography.bodyMedium,
            //fontSize = 16.scaledSp(),
        )

        /*val uriHandler = LocalUriHandler.current
        NiaTextButton(
            onClick = { uriHandler.openUri(PLAY_STORE_URL) },
        ) {
            Text(text = stringResource(R.string.feature_settings_privacy_policy))
        }*/

    }
}




 