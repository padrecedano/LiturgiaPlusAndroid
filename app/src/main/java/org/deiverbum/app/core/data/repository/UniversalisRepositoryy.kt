package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.Syncable
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

interface UniversalisRepositoryy : Syncable {
    //fun getNewsResources(todayDate:Int): Universalis
    /**
     * Returns available news resources that match the specified [query].
     */
    fun getNewsResources(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterTopicIds = null,
            filterNewsIds = 0,
        ),
    ): Flow<List<UniversalisResource>>

    /**
     * Gets the available topics as a stream
     */
    fun getUniversalisList(): Flow<List<Universalis>>

    /**
     * Gets data for a specific topic
     */
    fun getUniversalisById(id: String): Flow<Universalis>
}
