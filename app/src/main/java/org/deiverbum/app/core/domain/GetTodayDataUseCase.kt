package org.deiverbum.app.core.domain

import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import javax.inject.Inject

/**
 * A use case which obtains a list of topics with their followed state.
 */
class GetTodayDataUseCase @Inject constructor(
    private val topicsRepository: UniversalisRepository,
    private val userDataRepository: UserDataRepository,
) {
    /**
     * Returns a list of topics with their associated followed state.
     *
     * @param sortBy - the field used to sort the topics. Default NONE = no sorting.
     */
    operator fun invoke(
        date: Int,
    ) {
        //var userDataa=userDataRepository.userData
        //var topicss=topicsRepository.getUniversalisByDate(UniversalisResourceQuery(setOf("20240303"),null))
        //TopicRequest()
        //val t= topicsRepository.getUniversalisOfToday(UniversalisResourceQuery(setOf(date), 0))
//if(t.collect())


        //return t
    }
}


