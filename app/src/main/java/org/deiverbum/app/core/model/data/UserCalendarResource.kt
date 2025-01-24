@file:JvmName("CalendarResourceKt")

package org.deiverbum.app.core.model.data


import kotlinx.datetime.Instant

/**
 * A [NewsResource] with additional user information such as whether the user is following the
 * news resource's topics and whether they have saved (bookmarked) this news resource.
 */
@ConsistentCopyVisibility
data class UserCalendarResource internal constructor(
    val id: String,
    val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,
    val followableTopics: List<FollowableTopic>,
    val isSaved: Boolean,
    val hasBeenViewed: Boolean,
) {
    constructor(newsResource: CalendarResource, userData: UserData) : this(
        id = newsResource.id,
        title = newsResource.title,
        content = newsResource.content,
        url = newsResource.url,
        headerImageUrl = newsResource.headerImageUrl,
        publishDate = newsResource.publishDate,
        type = newsResource.type,
        followableTopics = newsResource.topics.map { topic ->
            FollowableTopic(
                topic = topic,
                isFollowed = true,//topic.id in userData.followedTopics,
            )
        },
        isSaved = true,//newsResource.id in userData.bookmarkedNewsResources,
        hasBeenViewed = true//newsResource.id in userData.viewedNewsResources,
    )
}

fun List<CalendarResource>.mapToUserCalendarResources(userData: UserData): List<UserCalendarResource> =
    map { UserCalendarResource(it, userData) }