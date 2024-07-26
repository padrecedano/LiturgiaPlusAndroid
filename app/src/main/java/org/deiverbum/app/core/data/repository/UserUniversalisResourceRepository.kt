package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.data.UserNewsResource
import org.deiverbum.app.core.model.data.UserUniversalisResource

/**
 * Data layer implementation for [UserNewsResource]
 */
interface UserUniversalisResourceRepository {
    /**
     * Returns available news resources as a stream.
     */

    fun observeAll(
        query: UniversalisResourceQuery = UniversalisResourceQuery(
            filterTopicIds = null,
            filterNewsIds = 0,
        ),
    ): Flow<List<UserUniversalisResource>>

    /**
     * Returns available news resources for the user's followed topics as a stream.
     */
    fun observeAllForFollowedTopics(): Flow<List<UserUniversalisResource>>

    /**
     * Returns the user's bookmarked news resources as a stream.
     */
    fun observeAllBookmarked(): Flow<List<UserUniversalisResource>>

}
