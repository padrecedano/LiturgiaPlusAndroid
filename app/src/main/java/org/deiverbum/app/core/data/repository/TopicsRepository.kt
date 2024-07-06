package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.Syncable
import org.deiverbum.app.core.model.data.Topic

interface TopicsRepository : Syncable {
    /**
     * Gets the available topics as a stream
     */
    fun getTopics(): Flow<List<Topic>>

    /**
     * Gets data for a specific topic
     */
    fun getTopic(id: String): Flow<Topic>
}
