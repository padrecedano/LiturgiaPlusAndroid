package org.deiverbum.app.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.deiverbum.app.core.designsystem.theme.NiaTheme
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.data.UserUniversalisResource

/**
 * [UniversalisResourceCardExpanded] card used on the following screens: For You, Saved
 */

@Composable
fun UniversalisResourceCardExpanded(
    userNewsResource: UserUniversalisResource,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier,
    ) {
        Column {
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        BookmarkButton(isBookmarked, onToggleBookmark)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //TODO: Poner color litúrgico
                        /*if (!hasBeenViewed) {
                            NotificationDot(
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(8.dp),
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                        }*/
                        //NewsResourceMetaData(userNewsResource.todayDate, userNewsResource.type)
                    }
                    /*UniversalisResourceContent(
                        userNewsResource.followableTopics[0].topic.getAllForView(
                            TodayRequest(1, 1, true, true)
                        ).toString()
                    )*/
                    UniversalisResourceContent(
                        userNewsResource.data[0].getAllForView(TodayRequest(1, 1, false, false))
                            .toString()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun UniversalisResourceContentNew(
    newsResourceShortDescription: String,
) {
    Text(newsResourceShortDescription, style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun UniversalisResourceContent(
    newsResourceShortDescription: String,
) {
    Text(newsResourceShortDescription, style = MaterialTheme.typography.bodyLarge)
}

@Preview("Bookmark Button")
@Composable
private fun BookmarkButtonPreview() {
    NiaTheme {
        Surface {
            BookmarkButton(isBookmarked = false, onClick = { })
        }
    }
}

@Preview("Bookmark Button Bookmarked")
@Composable
private fun BookmarkButtonBookmarkedPreview() {
    NiaTheme {
        Surface {
            BookmarkButton(isBookmarked = true, onClick = { })
        }
    }
}


