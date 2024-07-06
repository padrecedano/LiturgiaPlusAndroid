package org.deiverbum.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FlowExample(counter: String, onAdd: () -> Unit, onRemove: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(onClick = { onAdd() }) {
            Text(text = "Add")
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
                .weight(1f)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Counter Value :: $counter ",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White
            )
        }
        Button(onClick = { onRemove() }) {
            Text(text = "Remove")
        }
    }
}