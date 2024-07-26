package org.deiverbum.app.core.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.model.data.UserUniversalisResource
import org.deiverbum.app.core.model.data.mapToUserUniversalisResources
import javax.inject.Inject

/**
 * Implements a [UserNewsResourceRepository] by combining a [NewsRepository] with a
 * [UserDataRepository].
 */
class CompositeUserUniversalisResourceRepository @Inject constructor(
    val newsRepository: UniversalisRepository,
    val userDataRepository: UserDataRepository,
) : UserUniversalisResourceRepository {

    /**
     * Returns available news resources (joined with user data) matching the given query.
     */


    /**
     * Returns available news resources (joined with user data) matching the given query.
     */
    val q = UniversalisResourceQuery(setOf(20240202), 0)
    override fun observeAll(
        query: UniversalisResourceQuery,
    ): Flow<List<UserUniversalisResource>> =
        newsRepository.getUniversalisByDate(query)
            .combine(userDataRepository.userData) { newsResources, userData ->
                newsResources.mapToUserUniversalisResources(userData)
            }


    /**
     * Returns available news resources (joined with user data) for the followed topics.
     */
    override fun observeAllForFollowedTopics(): Flow<List<UserUniversalisResource>> =
        userDataRepository.userData.map { it.followedTopics }.distinctUntilChanged()
            .flatMapLatest { followedTopics ->
                when {
                    followedTopics.isEmpty() -> flowOf(emptyList())
                    else -> observeAll(UniversalisResourceQuery(filterTopicIds = setOf(1)/*followedTopics*/))
                }
            }

    override fun observeAllBookmarked(): Flow<List<UserUniversalisResource>> {
        TODO("Not yet implemented")
    }

}

fun observeAllBookmarkedd(): Flow<List<UserUniversalisResource>> {
    TODO("Not yet implemented")
}
//.combine(userDataRepository.userData) { newsResources, userData ->
//    newsResources.mapToUserNewsResources(userData)


fun observeAllForFollowedTopics(): Flow<List<UserUniversalisResource>> {
    TODO("Not yet implemented")
}

fun observeAllBookmarked(): Flow<List<UserUniversalisResource>> {
    TODO("Not yet implemented")
}



