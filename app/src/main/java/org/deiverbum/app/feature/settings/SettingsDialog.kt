@file:Suppress("ktlint:standard:max-line-length")

package org.deiverbum.app.feature.settings

import LPlusIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.LPlusSwitch
import org.deiverbum.app.core.designsystem.component.NiaTextButton
import org.deiverbum.app.core.designsystem.component.UniversalisSingleAppBar
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.designsystem.theme.supportsDynamicTheming
import org.deiverbum.app.core.model.data.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.data.configuration.FontSizeConfig
import org.deiverbum.app.core.model.data.configuration.ThemeBrand
import org.deiverbum.app.core.model.data.configuration.VoiceReaderConfig
import org.deiverbum.app.core.ui.TrackScreenViewEvent


@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()
    SettingsDialog(
        onDismiss = onDismiss,
        settingsUiState = settingsUiState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeVoiceReaderPreference = viewModel::updateVoiceReaderPreference,
        onChangeMultipleInvitatoryPreference = viewModel::updateMultipleInvitatoryPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@Composable
fun SettingsDialog(
    settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onDismiss: () -> Unit,
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
                text = stringResource(R.string.feature_settings_title),
                style = NiaTypography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                when (settingsUiState) {
                    SettingsUiState.Loading -> {
                        Text(
                            text = stringResource(R.string.feature_settings_loading),
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    is SettingsUiState.Success -> {
                        SettingsPanel(
                            settings = settingsUiState.settings,
                            supportDynamicColor = supportDynamicColor,
                            onChangeThemeBrand = onChangeThemeBrand,
                            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                            onChangeVoiceReaderPreference = onChangeVoiceReaderPreference,
                            onChangeMultipleInvitatoryPreference = onChangeMultipleInvitatoryPreference,
                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(top = 8.dp))
                LinksPanel()
            }
            TrackScreenViewEvent(screenName = "Settings")
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.feature_settings_dismiss_dialog_button_text),
                style = NiaTypography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}

// [ColumnScope] is used for using the [ColumnScope.AnimatedVisibility] extension overload composable.
@Composable
private fun ColumnScope.SettingsPanel(
    settings: UserEditableSettings,
    supportDynamicColor: Boolean,
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeVoiceReaderPreference: (useVoiceReader: VoiceReaderConfig) -> Unit,
    onChangeMultipleInvitatoryPreference: (useMultipleInvitatory: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_theme))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.feature_settings_brand_default),
            selected = settings.brand == ThemeBrand.DEFAULT,
            onClick = { onChangeThemeBrand(ThemeBrand.DEFAULT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.feature_settings_brand_android),
            selected = settings.brand == ThemeBrand.ANDROID,
            onClick = { onChangeThemeBrand(ThemeBrand.ANDROID) },
        )
    }
    AnimatedVisibility(visible = settings.brand == ThemeBrand.DEFAULT && supportDynamicColor) {
        Column {
            SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_dynamic_color_preference))
            Column(Modifier.selectableGroup()) {
                SettingsDialogThemeChooserRow(
                    text = stringResource(R.string.feature_settings_dynamic_color_yes),
                    selected = settings.dynamic.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(true) },
                )
                SettingsDialogThemeChooserRow(
                    text = stringResource(R.string.feature_settings_dynamic_color_no),
                    selected = !settings.dynamic.useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(false) },
                )
            }
        }
    }
    Column {
        SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_reader_preference))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.generic_yes),
                selected = settings.dynamic.useVoiceReader == VoiceReaderConfig.ON,
                onClick = { onChangeVoiceReaderPreference(VoiceReaderConfig.ON) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.generic_no),
                selected = settings.dynamic.useVoiceReader == VoiceReaderConfig.OFF,
                onClick = { onChangeVoiceReaderPreference(VoiceReaderConfig.OFF) },
            )
        }
    }

    SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_dark_mode_preference))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.feature_settings_dark_mode_config_system_default),
            selected = settings.dynamic.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.feature_settings_dark_mode_config_light),
            selected = settings.dynamic.darkThemeConfig == DarkThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.feature_settings_dark_mode_config_dark),
            selected = settings.dynamic.darkThemeConfig == DarkThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
        )
        //MinimalDropdownMenu()
        SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_default_font_preference))


    }

}


@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = NiaTypography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text, style = NiaTypography.bodyLarge)
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LinksPanel() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        modifier = Modifier.fillMaxWidth(),
    ) {
        val uriHandler = LocalUriHandler.current
        NiaTextButton(
            onClick = { uriHandler.openUri(PRIVACY_POLICY_URL) },
        ) {
            Text(
                text = stringResource(R.string.feature_settings_privacy_policy),
                style = NiaTypography.bodyLarge
            )
        }
        /*val context = LocalContext.current
        NiaTextButton(
            onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            },
        ) {
            Text(text = stringResource(R.string.feature_settings_licenses))
        }*/
        NiaTextButton(
            onClick = { uriHandler.openUri(BRAND_GUIDELINES_URL) },
        ) {
            Text(
                text = stringResource(R.string.feature_settings_terms),
                style = NiaTypography.bodyLarge
            )
        }
        NiaTextButton(
            onClick = { uriHandler.openUri(PLAYSTORE_URL) },
        ) {
            Text(
                text = stringResource(R.string.feature_menu_play_store),
                style = NiaTypography.bodyLarge
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onChangeFontSize: (fontSize: FontSizeConfig) -> Unit,
    settingsUiState: SettingsUiState,
    onBackClick: () -> Unit,

    ) {
    Scaffold(
        topBar = {
            UniversalisSingleAppBar(
                title = stringResource(R.string.feature_settings_title),
                navigationIcon = LPlusIcons.ArrowBack,
                readerIcon = LPlusIcons.Reader,
                calendarIcon = LPlusIcons.Calendar,
                navigationIconContentDescription = "Navigation icon",
                actionIcon = LPlusIcons.MoreVert,
                actionIconContentDescription = "Action icon",
                onNavigationClick = { onBackClick() }
            )
        },

        ) { innerPadding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            when (settingsUiState) {
                SettingsUiState.Loading -> {
                    Text(
                        text = stringResource(R.string.feature_settings_loading),
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                }

                is SettingsUiState.Success -> {
                    var selectedIndex by remember { mutableIntStateOf(settingsUiState.settings.dynamic.fontSize.ordinal) }
                    val fontSizes = FontSizeConfig.entries

                    LargeDropdownMenu(
                        items = fontSizes,
                        selectedIndex = selectedIndex,
                        onItemSelected = { index, _ ->
                            selectedIndex = index
                            onChangeFontSize(fontSizes[index])
                        },
                    )

                    LPlusSwitch(label = stringResource(R.string.pref_title_brevis))
                    //SwitchWithLabel("Rosario forma breve",true,{})
                }
            }
        }
    }
}

//@Preview
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        settingsUiState = settingsUiState,
        onChangeFontSize = viewModel::updateFontSizePreference,
        onBackClick = onBackClick
    )
}

private const val PRIVACY_POLICY_URL = "https://liturgiaplus.app/privacy"
private const val BRAND_GUIDELINES_URL = "https://liturgiaplus.app/terms"
private const val PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=org.deiverbum.app"

