package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.model.data.SearchResult
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import javax.inject.Inject

internal class DefaultSearchContentsRepository @Inject constructor(
    //private val newsResourceDao: NewsResourceDao,
    //private val newsResourceFtsDao: NewsResourceFtsDao,
    private val topicDao: UniversalisDao,
    //private val topicFtsDao: TopicFtsDao,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchContentsRepository {
    override suspend fun populateFtsData() {
        TODO("Not yet implemented")
    }

    override fun searchContents(searchQuery: String): Flow<SearchResult> {
        TODO("Not yet implemented")
    }

    override fun getSearchContentsCount(): Flow<Int> {
        TODO("Not yet implemented")
    }


}