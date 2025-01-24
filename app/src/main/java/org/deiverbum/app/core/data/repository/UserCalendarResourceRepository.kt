package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.data.UserCalendarResource

/**
 * Data layer implementation for [UserNewsResource]
 */
interface UserCalendarResourceRepository {
    /**
     * Returns available news resources as a stream.
     */
    fun observeAll(
        query: CalendarResourceQuery = CalendarResourceQuery(
            filterTopicIds = null,
            filterNewsIds = null,
        ),
    ): Flow<List<UserCalendarResource>>


}