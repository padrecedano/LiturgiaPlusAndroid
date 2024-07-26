package org.deiverbum.app.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.TopicRequest
import javax.inject.Inject

/**
 * A use case which obtains a list of topics with their followed state.
 */
class GetFollowableUniversalisUseCase @Inject constructor(
    private val topicsRepository: UniversalisRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Returns a list of topics with their associated followed state.
     *
     * @param sortBy - the field used to sort the topics. Default NONE = no sorting.
     */
    operator fun invoke(
        sortBy: UniversalisSortField = UniversalisSortField.NONE,
        date: Int,
        topicId: Int
    ): Flow<List<TopicRequest>> =
    //var userDataa=userDataRepository.userData
    //var topicss=topicsRepository.getUniversalisByDate(UniversalisResourceQuery(setOf("20240303"),null))
        //TopicRequest()
        combine(
            userDataRepository.userData,
            topicsRepository.getUniversalisByDate(UniversalisResourceQuery(setOf(date), topicId)),
            //topicsRepository.getUniversalisById("20240719"),

        ) { userData, topics ->
            val followedTopics = topics
                .map { topic ->
                    TopicRequest(
                        date = topics[0].data[0].todayDate, //TODO:Este flujo se repite ¿?
                        resource = "",
                        name = "",
                        data = topic.data,
                        dynamic = userData.dynamic
                    )
                }
            when (sortBy) {
                UniversalisSortField.NAME -> followedTopics.sortedBy { it.date }
                else -> followedTopics
            }
        }
}

enum class UniversalisSortField {
    NONE,
    NAME,
}
