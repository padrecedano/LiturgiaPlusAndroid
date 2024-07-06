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

@Composable
fun ScreenB(
    //onComposing: (AppBarState) -> Unit,
    navController: NavController
) {
/*
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = "My Screen B",
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Star,
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
            text = "Screen B"
        )
        Button(onClick = {
            navController.navigate("screen_a")
        }) {
            Text(text = "Navigate to Screen A")
        }
    }
}