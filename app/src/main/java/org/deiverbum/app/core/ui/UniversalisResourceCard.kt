package org.deiverbum.app.core.ui

import LPlusIcons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.UniversalisRequest

/**
 * [UniversalisResourceCardExpanded] card usada en `UniversalisScreen`
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalisResourceCardExpanded(
    universalisResource: List<UniversalisRequest>,
    isBookmarked: Boolean,
    hasBeenViewed: Boolean,
    onToggleBookmark: () -> Unit,
    onClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    topicId: String?,
    onReaderClick: () -> Unit,

    ) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        // Use custom label for accessibility services to communicate button's action to user.
        // Pass null for action to only override the label and not the actual action.
        /*modifier = modifier.semantics {
            onClick(label = clickActionLabel, action = null)
        },*/
    ) {
        Column {
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        var title: String
                        universalisResource[0].id.let {
                            title = when {
                                it == 20 -> {
                                    val sancti =
                                        universalisResource[0].data[0].liturgia!!.liturgiaTypus as Alteri.Sancti
                                    sancti.sanctus.monthName
                                }

                                else -> {
//"TODO: UniversalisResourceCard 75" ERROR cuando no hay homilías
                                    universalisResource[0].data[0].liturgia!!.tempus!!.externus.toString()
                                }
                            }
                        }
                        UniversalisResourceTitle(
                            title,
                            modifier = Modifier.fillMaxWidth((.8f)),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        //BookmarkButton(isBookmarked, onToggleBookmark)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (!hasBeenViewed) {
                            NotificationDot(
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(8.dp),
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                        }
                        UniversalisResourceMetaData(universalisResource[0].data[0].liturgia!!.nomen)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    var data = universalisResource[0].data[0]
                    Universalis(
                        data,
                        topicId = universalisResource[0].id,
                        universalisResource[0].dynamic,
                        //onReaderClick
                    )
                }
            }
        }
    }
}

@Composable
fun UniversalisResourceTitle(
    newsResourceTitle: String,
    modifier: Modifier = Modifier,
) {
    Text(newsResourceTitle, style = MaterialTheme.typography.headlineSmall, modifier = modifier)
}

@Composable
fun ReaderButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NiaIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = {
            onClick()
        },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = LPlusIcons.ReaderBorder,
                contentDescription = "stringResource(R.string.core_ui_bookmark)",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = LPlusIcons.Reader,
                contentDescription = "stringResource(R.string.core_ui_unbookmark)",
            )
        },
    )
}

@Composable
fun UniversalisResourceMetaData(
    content: String,
) {
    Text(
        text = content,
        style = MaterialTheme.typography.titleMedium,
    )
}

