package org.deiverbum.app.core.model.data

/**
 * A [topic] with the additional information for whether or not it is followed.
 */
// TODO consider changing to UserTopic and flattening
data class FollowableTopic(
    val topic: Topic,
    val isFollowed: Boolean,
)
