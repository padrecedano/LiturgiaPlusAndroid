package org.deiverbum.app.core.ui

import LPlusIcons
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import org.deiverbum.app.core.designsystem.component.DynamicAsyncImage
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.theme.LPlusTheme


@Composable
fun AssistChipExample(
    onTopicClick: String
) {
    val nestedNavController = rememberNavController()

    AssistChip(
        onClick = {
            Log.d("Assist chip", "hello world" + onTopicClick)
            //nestedNavController.navigateToTopic(onTopicClick)
        },
        label = { Text("Assist chip*") },
        leadingIcon = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Localized description",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}

@Composable
fun TodayItem(
    name: String,
    following: Boolean,
    topicImageUrl: String,
    onClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    description: String = "",
    isSelected: Boolean = false,
) {

    ListItem(
        /*leadingContent = {
            InterestsIcon(topicImageUrl, iconModifier.size(64.dp))
        },*/
        headlineContent = {
            Text(text = name)
        },
        supportingContent = {
            Text(text = description)
        },
        trailingContent = {
            NiaIconToggleButton(
                checked = following,
                onCheckedChange = onFollowButtonClick,
                icon = {
                    Icon(
                        imageVector = LPlusIcons.Add,
                        contentDescription = "core_ui_interests_card_follow_button_content_desc",
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = LPlusIcons.Check,
                        contentDescription = "core_ui_interests_card_unfollow_button_content_desc",

                        )
                },
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Color.Transparent
            },
        ),
        modifier = modifier
            .semantics(mergeDescendants = true) {
                selected = isSelected
            }
            .clickable(enabled = true, onClick = onClick),
    )
}

@Composable
private fun TodayIcon(topicImageUrl: String, modifier: Modifier = Modifier) {
    if (topicImageUrl.isEmpty()) {
        Icon(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp),
            imageVector = LPlusIcons.Person,
            // decorative image
            contentDescription = null,
        )
    } else {
        DynamicAsyncImage(
            imageUrl = topicImageUrl,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
private fun TodayCardPreview() {
    LPlusTheme {
        Surface {
            TodayItem(
                name = "Compose",
                description = "Description",
                following = false,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun TodayCardLongNamePreview() {
    LPlusTheme {
        Surface {
            TodayItem(
                name = "This is a very very very very long name",
                description = "Description",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun TodayCardLongDescriptionPreview() {
    LPlusTheme {
        Surface {
            TodayItem(
                name = "Compose",
                description = "This is a very very very very very very very " +
                        "very very very long description",
                following = false,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun TodayCardWithEmptyDescriptionPreview() {
    LPlusTheme {
        Surface {
            TodayItem(
                name = "Compose",
                description = "",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun TodayCardSelectedPreview() {
    LPlusTheme {
        Surface {
            TodayItem(
                name = "Compose",
                description = "",
                following = true,
                topicImageUrl = "",
                onClick = { },
                onFollowButtonClick = { },
                isSelected = true,
            )
        }
    }
}
