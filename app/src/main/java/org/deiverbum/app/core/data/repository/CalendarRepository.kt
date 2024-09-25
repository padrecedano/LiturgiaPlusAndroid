package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.Syncable
import org.deiverbum.app.core.model.data.CalendarResource

/**
 * Encapsulation class for query parameters for [NewsResource]
 */
data class CalendarResourceQuery(
    /**
     * Topic ids to filter for. Null means any topic id will match.
     */
    val filterTopicIds: Set<String>? = null,
    /**
     * News ids to filter for. Null means any news id will match.
     */
    val filterNewsIds: Set<String>? = null,
)

/**
 * Data layer implementation for [NewsResource]
 */
interface CalendarRepository : Syncable {
    /**
     * Returns available news resources that match the specified [query].
     */
    fun getNewsResources(
        query: CalendarResourceQuery = CalendarResourceQuery(
            filterTopicIds = null,
            filterNewsIds = null,
        ),
    ): Flow<List<CalendarResource>>
}