package org.deiverbum.app.core.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.navigation.FloatingWindow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.flow.filterNot

@ExperimentalMaterial3Api
@Composable
fun MyTopAppBar(navController: NavController, canNavigateBack: Boolean) {
    val currentContentBackStackEntry by produceState(
        initialValue = null as NavBackStackEntry?,
        producer = {
            navController.currentBackStackEntryFlow
                .filterNot { it.destination is FloatingWindow }
                .collect { value = it }
        }
    )
    //val canNavigateBack= navController.previousBackStackEntry != null

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            //containerColor = md_theme_light_primaryContainer, //{AppBarColors(currentContentBackStackEntry)},
            //containerColor = AppBarColors(currentContentBackStackEntry),

            //actionIconContentColor = Color.Yellow,
            //navigationIconContentColor = Color.Blue,
            //scrolledContainerColor = Color.Green,
            //titleContentColor = Color.Yellow


            //MaterialTheme.colorScheme.primaryContainer,
            //titleContentColor = MaterialTheme.colorScheme.primary,
        )
        /*navigationIcon = {
            val backPressDispatcher = LocalOnBackPressedDispatcherOwner.current
            IconButton(
                onClick = { backPressDispatcher?.onBackPressedDispatcher?.onBackPressed() },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrowBackIos"
                    )
                }
            )
        }*/,
        navigationIcon = {

            if (canNavigateBack) {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        title = {
            //val t=LiturgyHelper.liturgyByType(currentContentBackStackEntry?.arguments?.getString("id").toInt())
            AppBarTitle(currentContentBackStackEntry)
        },
        actions = {
            //val t=LiturgyHelper.liturgyByType(currentContentBackStackEntry?.arguments?.getString("id").toInt())
            AppBarAction(currentContentBackStackEntry)
        }
    )
}
