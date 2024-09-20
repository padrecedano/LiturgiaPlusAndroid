package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.Syncable
import org.deiverbum.app.core.model.data.NewsResource
import org.deiverbum.app.core.model.data.UniversalisResource

/**
 * Encapsulation class for query parameters for [NewsResource]
 */
data class UniversalisResourceQuery(
    /**
     * Topic ids to filter for. Null means any topic id will match.
     */
    val filterDates: Set<Int>? = null,
    /**
     * News ids to filter for. Null means any news id will match.
     */
    val filterTopicsIds: Int = 0,
)

/**
 * Data layer implementation for [NewsResource]
 */
interface UniversalisRepositoryy : Syncable {

    /**
     * Returns available news resources that match the specified [query].
     */
    fun getUniversalisByDate(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDates = null,
            filterTopicsIds = 1,
        ),
    ): Flow<List<UniversalisResource>>

    suspend fun insertFromRemote(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDates = null,
            filterTopicsIds = 1,
        ),
    )


}

interface UniversalisRepository : Syncable {
    /**
     * Returns available news resources that match the specified [query].
     */
    fun getUniversalisByDate(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDates = null,
            filterTopicsIds = 1,
        ),
    ): Flow<List<UniversalisResource>>

    fun getReader(): String {
        return TODO("Provide the return value")
    }

    //fun getNewsResources(todayDate:Int): Universalis
    /**
     * Returns available news resources that match the specified [query].
     */
    /*fun getNewsResources(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDates = null,
            filterTopicsIds = 0,
        ),
    ): Flow<List<UniversalisResource>>*/

    /**
     * Gets the available topics as a stream
     */
    //fun getUniversalisList(): Flow<List<Universalis>>

    /**
     * Gets data for a specific topic
     */
    //fun getUniversalisById(id: String): Flow<Universalis>

    suspend fun insertFromRemote(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDates = null,
            filterTopicsIds = 1,
        ),
    )
}
