package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.data.repository.HomeTopicsRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.FollowableUITopic
import javax.inject.Inject

/**
 * A use case which obtains a list of topics with their followed state.
 */
class GetHomeTopicsUseCase @Inject constructor(
    private val topicsRepository: HomeTopicsRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Returns a list of topics with their associated followed state.
     *
     * @param sortBy - the field used to sort the topics. Default NONE = no sorting.
     */
    operator fun invoke(sortBy: HomeSortField = HomeSortField.NONE): Flow<List<FollowableUITopic>> =
        combine(
            userDataRepository.userData,
            topicsRepository.getTopics(),
        ) { userData, topics ->
            val followedTopics = topics
                .map { topic ->
                    FollowableUITopic(
                        topic = topic,
                        isFollowed = false//topic.id in userData.followedTopics,
                    )
                }
            when (sortBy) {
                HomeSortField.ID -> followedTopics.sortedBy { it.topic.id }
                else -> followedTopics
            }
        }
}

enum class HomeSortField {
    NONE,
    ID,
}