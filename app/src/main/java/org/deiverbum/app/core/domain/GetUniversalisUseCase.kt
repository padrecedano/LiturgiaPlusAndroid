package org.deiverbum.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.UniversalisRequest
import javax.inject.Inject

/**
 * Este caso de uso obtiene los Universalis de la fecha dada.
 * Si no los encuentra en la base de datos local, llama al método de inserción
 * en la fuente de datos remota.
 */
class GetUniversalisUseCase @Inject constructor(
    private val topicsRepository: UniversalisRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Returns a list of topics with their associated followed state.
     *
     * @param sortBy - the field used to sort the topics. Default NONE = no sorting.
     */
    operator fun invoke(
        sortBy: HomeSortField = HomeSortField.NONE,
        date: Int,
        topicId: Int
    ): Flow<List<UniversalisRequest>> {
        return combine(
            userDataRepository.userData,
            topicsRepository.getUniversalisByDate(UniversalisResourceQuery(setOf(date), topicId)),

            ) { userData, topics ->
            if (topics.isEmpty() || topics[0].data.isEmpty()) {
                topicsRepository.insertFromRemote(UniversalisResourceQuery(setOf(date), topicId))
            }
            val followedTopics = topics.map { topic ->
                UniversalisRequest(
                    date = topics[0].data[0].todayDate, //TODO:Este flujo se repite ¿?
                    resource = "",
                    name = "",
                    id = 1,
                    data = topic.data,
                    dynamic = userData.dynamic
                )
            }.sortedBy { it.date }
            when (sortBy) {
                HomeSortField.ID -> followedTopics.sortedBy { it.id }
                else -> followedTopics
            }
        }.filterNotNull()
    }
}