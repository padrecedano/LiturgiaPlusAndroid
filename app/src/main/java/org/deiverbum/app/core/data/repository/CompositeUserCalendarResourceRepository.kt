package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.model.data.UserCalendarResource
import org.deiverbum.app.core.model.data.mapToUserCalendarResources
import javax.inject.Inject

/**
 * Implements a [UserNewsResourceRepository] by combining a [NewsRepository] with a
 * [UserDataRepository].
 */
class CompositeUserCalendarResourceRepository @Inject constructor(
    val newsRepository: CalendarRepository,
    val userDataRepository: UserDataRepository,
) : UserCalendarResourceRepository {

    /**
     * Returns available news resources (joined with user data) matching the given query.
     */
    override fun observeAll(
        query: CalendarResourceQuery,
    ): Flow<List<UserCalendarResource>> =
        newsRepository.getNewsResources(query)
            .combine(userDataRepository.userData) { newsResources, userData ->
                newsResources.mapToUserCalendarResources(userData)
            }

    /**
     * Returns available news resources (joined with user data) for the followed topics.
     */
    override fun observeAllForFollowedTopics(): Flow<List<UserCalendarResource>> =
        userDataRepository.userData.map { it.followedTopics }.distinctUntilChanged()
            .flatMapLatest { followedTopics ->
                when {
                    followedTopics.isEmpty() -> flowOf(emptyList())
                    else -> observeAll(CalendarResourceQuery(filterTopicIds = followedTopics))
                }
            }

    override fun observeAllBookmarked(): Flow<List<UserCalendarResource>> =
        userDataRepository.userData.map { it.bookmarkedNewsResources }.distinctUntilChanged()
            .flatMapLatest { bookmarkedNewsResources ->
                when {
                    bookmarkedNewsResources.isEmpty() -> flowOf(emptyList())
                    else -> observeAll(CalendarResourceQuery(filterNewsIds = bookmarkedNewsResources))
                }
            }
}