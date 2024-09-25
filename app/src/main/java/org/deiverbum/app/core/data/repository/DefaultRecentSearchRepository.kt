package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.data.model.RecentSearchQuery
import org.deiverbum.app.core.database.dao.RecentSearchQueryDao
import javax.inject.Inject

internal class DefaultRecentSearchRepository @Inject constructor(
    private val recentSearchQueryDao: RecentSearchQueryDao,
) : RecentSearchRepository {
    override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
        TODO("Not yet implemented")
    }

    /*
        override suspend fun insertOrReplaceRecentSearch(searchQuery: String) {
            recentSearchQueryDao.insertOrReplaceRecentSearchQuery(
                UniversalisEntity(),
            )
        }

        override fun getRecentSearchQueries(limit: Int): Flow<List<RecentSearchQuery>> =
            recentSearchQueryDao.getRecentSearchQueryEntities(limit).map { searchQueries ->
                searchQueries.map { it.asExternalModel() }
            }
    */
    override suspend fun clearRecentSearches() = recentSearchQueryDao.clearRecentSearchQueries()
}