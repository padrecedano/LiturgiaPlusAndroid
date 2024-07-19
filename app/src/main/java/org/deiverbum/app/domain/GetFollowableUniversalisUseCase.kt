package org.deiverbum.app.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.data.repository.UniversalisRepositoryy
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.FollowableUniversalis
import javax.inject.Inject

/**
 * A use case which obtains a list of topics with their followed state.
 */
class GetFollowableUniversalisUseCase @Inject constructor(
    private val topicsRepository: UniversalisRepositoryy,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Returns a list of topics with their associated followed state.
     *
     * @param sortBy - the field used to sort the topics. Default NONE = no sorting.
     */
    operator fun invoke(sortBy: UniversalisSortField = UniversalisSortField.NONE): Flow<List<FollowableUniversalis>> =
        combine(
            userDataRepository.userData,
            topicsRepository.getUniversalisList(),
        ) { userData, topics ->
            val followedTopics = topics
                .map { topic ->
                    FollowableUniversalis(
                        topic = topic,
                        content = topic.getAllForView(),
                        isFollowed = true//topic.id in userData.followedTopics,
                    )
                }
            when (sortBy) {
                UniversalisSortField.NAME -> followedTopics.sortedBy { it.topic.todayDate }
                else -> followedTopics
            }
        }
}

enum class UniversalisSortField {
    NONE,
    NAME,
}
