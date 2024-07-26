package org.deiverbum.app.core.model.data

/**
 * A [UniversalisResource] with additional user information such as whether the user is following the
 * news resource's topics and whether they have saved (bookmarked) this news resource.
 */
data class UserUniversalisResource internal constructor(

    val data: List<Universalis>,
    //val themeConfig:DarkThemeConfig,
    //val hasReaderOn:Boolean,
    val userData: UserDataDynamic,
    /*val isSaved: Boolean,
    val hasBeenViewed: Boolean,*/
) {
    constructor(newsResource: UniversalisResource, userData: UserData) : this(
        //todayDate = newsResource.todayDate,
        /*title = newsResource.title,
        content = newsResource.content,
        url = newsResource.url,
        headerImageUrl = newsResource.headerImageUrl,
        publishDate = newsResource.publishDate,
        type = newsResource.type,*/
        /*followableTopics = newsResource.topics.map { topic ->
            TopicRequest(
                date = topic.todayDate,
                resource = "****",
            )
        },*/
        data = newsResource.data,
        userData = userData.dynamic

        //isSaved = newsResource.id in userData.bookmarkedNewsResources,
        //hasBeenViewed = newsResource.id in userData.viewedNewsResources,
    )
}

fun List<UniversalisResource>.mapToUserUniversalisResources(userData: UserData): List<UserUniversalisResource> =
    map { UserUniversalisResource(it, userData) }
