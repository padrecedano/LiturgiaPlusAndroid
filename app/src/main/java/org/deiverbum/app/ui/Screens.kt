package org.deiverbum.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterial3Api
@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            navController.navigate(it.label)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = Navigator.NavTarget.Home.label
    ) {
        //...
        composable("home") {
            HomeScreen(navController)
        }
        composable("details") {
            val viewModel = viewModel<DetailViewModel>()
            DetailScreen(viewModel::getDetailText)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier,

        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        FloatingActionButton(onClick = { navController.navigate("detail/tercia") }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        ElevatedAssistChip(
            onClick = { navController.navigate("detail/sexta") },
            label = { Text("Sexta") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )

        ElevatedAssistChip(
            onClick = { navController.navigate("detail/nona") },
            label = { Text("Nona") },
            leadingIcon = {
                Icon(
                    Icons.Filled.AddTask,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text =
            """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                """.trimIndent(),
        )
    }
}


@Composable
fun DetailScreen(textProvider: () -> String) {
    Text(text = textProvider())
}

@Composable
fun TodayScreen(textProvider: () -> String) {
    Text(text = textProvider())
}

class DetailViewModel : ViewModel() {

    fun getDetailText(): String {
        // some imaginary backend call
        return "Detail"
    }
}