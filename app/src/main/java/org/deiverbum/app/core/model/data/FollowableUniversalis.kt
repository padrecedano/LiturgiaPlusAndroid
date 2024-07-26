package org.deiverbum.app.core.model.data

/**
 * A [topic] with the additional information for whether or not it is followed.
 */
// TODO consider changing to UserTopic and flattening
data class FollowableUniversalis(
    val topic: Universalis,
    val content: String,
    val isFollowed: Boolean,
)