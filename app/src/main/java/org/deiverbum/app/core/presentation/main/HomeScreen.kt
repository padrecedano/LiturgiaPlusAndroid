package org.deiverbum.app.core.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.deiverbum.app.R
import org.deiverbum.app.core.presentation.sync.SyncViewModel
import org.deiverbum.app.core.presentation.ui.ScreenA
import org.deiverbum.app.core.presentation.ui.ScreenB
import org.deiverbum.app.core.presentation.ui.StartOrderScreen

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

@ExperimentalMaterial3Api
@Composable
fun LiturgiaAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        //title = { Text(stringResource(id = R.string.app_name)) },
        title = { Text(stringResource(currentScreen.title)) },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.accept)
                    )
                }
            }
        },
        actions = {
            if (!canNavigateBack) {
                IconButton(onClick = {

                    /* do something */
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Abrir menú principal"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiturgiaApp(
    viewModel: SyncViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
// Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {


            Scaffold(
                topBar = {
                    LiturgiaAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            ) { innerPadding ->
                val uiState by viewModel.uiState.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = CupcakeScreen.Start.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                ) {
                    composable(route = CupcakeScreen.Start.name) {
                        StartOrderScreen(
                            quantityOptions = DataSource.quantityOptions,
                            onNextButtonClicked = {
                                //viewModel.launchSync()
                                navController.navigate(CupcakeScreen.Flavor.name)
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(dimensionResource(R.dimen.padding_medium))
                        )
                    }
                    composable(CupcakeScreen.Flavor.name) {
                        ScreenA(
                            /* onComposing = {
                                 appBarState = it
                             },*/
                            canNavigateBack = true,
                            navController = navController
                        )
                        //ProvideAppBarTitle { Text("Lorem ipsum*") }

                    }
                    composable(CupcakeScreen.Nona.name) {
                        ScreenB(
                            /* onComposing = {
                                 appBarState = it
                             },*/
                            navController = navController
                        )
                        //ProvideAppBarTitle { Text("Lorem ipsum*") }

                    }


                }
            }
        }
    }
}
