package org.deiverbum.app.core.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.deiverbum.app.core.presentation.main.CupcakeScreen

@Composable
fun ScreenA(
    //onComposing: (AppBarState) -> Unit,
    //   navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    navController: NavController
) {
    /*
        LaunchedEffect(key1 = true) {
            onComposing(
                AppBarState(
                    title = "My Screen A",
                    navigationIcon =

                    {
                        if(canNavigateBack){
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        }},

                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null
                            )
                        }
                    }
                )
            )
        }

    */
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = CupcakeScreen.Nona.name
        )
        Button(onClick = {
            navController.navigate(CupcakeScreen.Nona.name)
        }) {
            Text(text = "Navigate to Screen B")
        }
    }
}