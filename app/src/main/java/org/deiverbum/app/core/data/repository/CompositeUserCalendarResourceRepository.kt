package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.deiverbum.app.core.model.data.UserCalendarResource
import org.deiverbum.app.core.model.data.mapToUserCalendarResources
import javax.inject.Inject

/**
 * Implements a [UserCalendarResourceRepository] by combining a [CalendarRepository] with a
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
}

