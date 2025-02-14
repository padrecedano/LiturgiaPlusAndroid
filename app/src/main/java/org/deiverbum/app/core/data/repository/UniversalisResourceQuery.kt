package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.Syncable
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResourceQuery


interface UniversalisRepository : Syncable {
    /**
     * Returns available news resources that match the specified [query].
     */
    fun getUniversalisByDate(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDate = 0,
            filterTopicId = 1,
        ),
    ): Flow<Universalis>

    suspend fun insertFromRemote(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterDate = 0,
            filterTopicId = 1,
        ),
    )

    fun getUniversalisForTest(id: Int): Flow<Universalis>
}
