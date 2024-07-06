package org.deiverbum.app.ui.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R


@Composable
fun BackButton(onClick: OnClickFunction) {
    IconButton(
        onClick = onClick, modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
    ) {
        Icon(
            Icons.Filled.ArrowBack,
            stringResource(id = R.string.audio_resume),
            modifier = Modifier
                .size(32.dp)
                .padding(0.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }

}
