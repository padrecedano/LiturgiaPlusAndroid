package org.deiverbum.app.feature.mas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun MasScreen(
    onTopicClick: (String) -> Unit
) {
    val t = LoremIpsum()
    Text(t.values.last())
}