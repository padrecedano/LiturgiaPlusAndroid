package org.deiverbum.app.core.model.data


/**
 * An entity of [SearchResult] with additional user information such as whether the user is
 * following a topic.
 */
data class UniversalisSearchResult(
    //val topics: List<FollowableTopic> = emptyList(),
    val topicId: Int = -1,
    val newsResources: List<UserCalendarResource> = emptyList(),
)