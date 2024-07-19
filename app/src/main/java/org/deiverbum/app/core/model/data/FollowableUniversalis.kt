package org.deiverbum.app.core.model.data

import androidx.compose.ui.text.AnnotatedString

/**
 * A [topic] with the additional information for whether or not it is followed.
 */
// TODO consider changing to UserTopic and flattening
data class FollowableUniversalis(
    val topic: Universalis,
    val content: AnnotatedString,
    val isFollowed: Boolean,
)
