package org.deiverbum.app.core.model.data

/**
 * A [NewsResource] with additional user information such as whether the user is following the
 * news resource's topics and whether they have saved (bookmarked) this news resource.
 */
data class UserUniversalisResource internal constructor(
    val todayDate: Int,
    /*val title: String,
    val content: String,
    val url: String,
    val headerImageUrl: String?,
    val publishDate: Instant,
    val type: String,*/
    val followableTopics: List<FollowableUniversalis>,
    /*val isSaved: Boolean,
    val hasBeenViewed: Boolean,*/
) {
    constructor(newsResource: UniversalisResource, userData: UserData) : this(
        todayDate = newsResource.todayDate,
        /*title = newsResource.title,
        content = newsResource.content,
        url = newsResource.url,
        headerImageUrl = newsResource.headerImageUrl,
        publishDate = newsResource.publishDate,
        type = newsResource.type,*/
        followableTopics = newsResource.topics.map { topic ->
            FollowableUniversalis(
                topic = topic,
                content = topic.getAllForView(),
                isFollowed = true,//topic.id in userData.followedTopics,
            )
        },
        //isSaved = newsResource.id in userData.bookmarkedNewsResources,
        //hasBeenViewed = newsResource.id in userData.viewedNewsResources,
    )
}

fun List<UniversalisResource>.mapToUserUniversalisResources(userData: UserData): List<UserUniversalisResource> =
    map { UserUniversalisResource(it, userData) }
