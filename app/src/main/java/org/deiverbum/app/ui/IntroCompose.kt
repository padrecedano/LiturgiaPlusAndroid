package org.deiverbum.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.deiverbum.app.R
import org.deiverbum.app.ui.components.AppButton
import org.deiverbum.app.ui.components.BackButton
import org.deiverbum.app.ui.components.OnClickFunction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroCompose(
    navController: NavController,
    text: String,
    buttonText: Int = R.string.audio_resume,
    showBackButton: Boolean = true,
    onNext: OnClickFunction

) = Scaffold(topBar = {
    TopAppBar(title = {}, navigationIcon = {
        if (showBackButton) {
            BackButton {
                navController.popBackStack()
            }
        }
    })
}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(it),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            modifier = Modifier.padding(bottom = 30.dp),
            text = buttonText,
            onClick = onNext
        )
    }
}
