package org.deiverbum.app.core.model.data

import org.deiverbum.app.core.model.data.book.Book

/**
 * A [NewsResource] with additional user information such as whether the user is following the
 * news resource's topics and whether they have saved (bookmarked) this news resource.
 */
@ConsistentCopyVisibility
data class UserBooksResource internal constructor(
    val title: String,
    val dynamic: UserDataDynamic,
) {
    constructor(newsResource: Book, userData: UserData) : this(
        title = newsResource.title,
        dynamic = userData.dynamic,
    )
}

fun List<Book>.mapToUserNewsResources(userData: UserData): List<UserBooksResource> =
    map { UserBooksResource(it, userData) }