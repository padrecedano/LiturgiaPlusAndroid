package org.deiverbum.app.core.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.model.data.UserNewsResource
import org.deiverbum.app.core.model.data.mapToUserNewsResources
import javax.inject.Inject

/**
 * Implements a [UserNewsResourceRepository] by combining a [NewsRepository] with a
 * [UserDataRepository].
 */
class CompositeUserNewsResourceRepository @Inject constructor(
    val newsRepository: NewsRepository,
    val userDataRepository: UserDataRepository,
) : UserNewsResourceRepository {

    /**
     * Returns available news resources (joined with user data) matching the given query.
     */
    override fun observeAll(
        query: NewsResourceQuery,
    ): Flow<List<UserNewsResource>> =
        newsRepository.getNewsResources(query)
            .combine(userDataRepository.userData) { newsResources, userData ->
                newsResources.mapToUserNewsResources(userData)
            }

    /**
     * Returns available news resources (joined with user data) for the followed topics.
     */
    override fun observeAllForFollowedTopics(): Flow<List<UserNewsResource>> =
        userDataRepository.userData.map { it.followedTopics }.distinctUntilChanged()
            .flatMapLatest { followedTopics ->
                when {
                    followedTopics.isEmpty() -> flowOf(emptyList())
                    else -> observeAll(NewsResourceQuery(filterTopicIds = followedTopics))
                }
            }

    override fun observeAllBookmarked(): Flow<List<UserNewsResource>> =
        userDataRepository.userData.map { it.bookmarkedNewsResources }.distinctUntilChanged()
            .flatMapLatest { bookmarkedNewsResources ->
                when {
                    bookmarkedNewsResources.isEmpty() -> flowOf(emptyList())
                    else -> observeAll(NewsResourceQuery(filterNewsIds = bookmarkedNewsResources))
                }
            }
}
