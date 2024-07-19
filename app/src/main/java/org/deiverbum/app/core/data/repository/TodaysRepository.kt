package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.data.Universalis

interface TodaysRepository /*: Syncable*/ {

    /**
     * Gets data for a specific topic
     */
    fun getTopic(id: Int): Flow<Universalis>
}
