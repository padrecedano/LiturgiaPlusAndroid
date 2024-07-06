package org.deiverbum.app.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R


@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToInterests: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = {
            Icon(
                painterResource(R.drawable.ic_about),
                null,
                Modifier.padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == "JetnewsDestinations.HOME_ROUTE",
            onClick = navigateToHome,
            icon = { Icon(Icons.Filled.Home, stringResource(R.string.lbl_laudes)) },
            label = { Text(stringResource(R.string.lbl_laudes)) },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            selected = currentRoute == "JetnewsDestinations.INTERESTS_ROUTE",
            onClick = navigateToInterests,
            icon = { Icon(Icons.Filled.ListAlt, stringResource(R.string.lbl_completas)) },
            label = { Text(stringResource(R.string.lbl_completas)) },
            alwaysShowLabel = false
        )
        Spacer(Modifier.weight(1f))
    }
}
/*
@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    JetnewsTheme {
        AppNavRail(
            currentRoute = JetnewsDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToInterests = {},
        )
    }
}*/