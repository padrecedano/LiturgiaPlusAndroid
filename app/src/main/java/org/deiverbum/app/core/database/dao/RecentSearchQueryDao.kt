package org.deiverbum.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.model.entity.UniversalisEntity

/**
 * DAO for [RecentSearchQueryEntity] access
 */
@Dao
interface RecentSearchQueryDao {
    @Query(value = "SELECT * FROM universalis ORDER BY todayDate DESC LIMIT :limit")
    fun getRecentSearchQueryEntities(limit: Int): Flow<List<UniversalisEntity>>

    @Upsert
    suspend fun insertOrReplaceRecentSearchQuery(recentSearchQuery: UniversalisEntity)

    @Query(value = "DELETE FROM recentSearchQueries")
    suspend fun clearRecentSearchQueries()
}