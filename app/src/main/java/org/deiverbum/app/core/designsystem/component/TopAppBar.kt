package org.deiverbum.app.core.designsystem.component

import LPlusIcons
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.deiverbum.app.core.designsystem.theme.LPlusTheme
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.core.model.configuration.UserDataDynamic
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NiaTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = titleRes),
                style = NiaTypography.titleLarge
                //fontSize = 24.scaledSp()
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LPlusTopAppBar(
    @StringRes titleRes: Int,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    readerIcon: ImageVector,
    calendarIcon: ImageVector,

    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onReaderClick: () -> Unit = {},

    onActionClick: () -> Unit = {
        Timber.d("a", "a")
    },
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = titleRes), style = NiaTypography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(
                onClick = onReaderClick
            ) {
                Icon(
                    imageVector = readerIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick =
            { Timber.d("a", "axy") }
                //onActionClick
            ) {
                Icon(
                    imageVector = calendarIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisTopAppBar(
    title: String,
    subtitle: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    readerIcon: ImageVector,
    calendarIcon: ImageVector,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onReaderClick: () -> Unit = {},

    onActionClick: () -> Unit = {
        Timber.d("a", "a")
    },
    userData: UserDataDynamic,
) {
    TopAppBar(
        title = {
            Column {
                Text(text = title, style = NiaTypography.titleLarge)
                Text(text = subtitle, style = NiaTypography.titleSmall)
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            if (userData.useVoiceReader.name == "ON") {
                IconButton(
                    onClick = onReaderClick
                ) {
                    Icon(
                        imageVector = readerIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            IconButton(onClick =
            { Timber.d("a", "axy") }
                //onActionClick
            ) {
                Icon(
                    imageVector = calendarIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisSingleAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String,
    actionIcon: ImageVector,
    actionIconContentDescription: String,
    readerIcon: ImageVector,
    calendarIcon: ImageVector,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onReaderClick: () -> Unit = {},

    onActionClick: () -> Unit = {
        Timber.d("a", "a")
    },
) {
    TopAppBar(
        title = { Text(title, style = NiaTypography.titleLarge) },

        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(
                onClick = onReaderClick
            ) {
                Icon(
                    imageVector = readerIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick =
            { }
                //onActionClick
            ) {
                Icon(
                    imageVector = calendarIcon,
                    contentDescription = actionIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

        },
        colors = colors,
        modifier = modifier.testTag("niaTopAppBar"),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun NiaTopAppBarPreview() {
    LPlusTheme {
        LPlusTopAppBar(
            titleRes = android.R.string.untitled,
            navigationIcon = LPlusIcons.Search,
            readerIcon = LPlusIcons.Reader,
            calendarIcon = LPlusIcons.Calendar,

            navigationIconContentDescription = "Navigation icon",
            actionIcon = LPlusIcons.MoreVert,
            actionIconContentDescription = "Action icon",
        )
    }
}
