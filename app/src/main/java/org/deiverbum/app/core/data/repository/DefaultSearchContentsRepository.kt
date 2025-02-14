package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.model.data.SearchResult
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import javax.inject.Inject

class DefaultSearchContentsRepository @Inject constructor(
    private val newsResourceDao: UniversalisDao,
    val userDataRepository: UserDataRepository,

    //private val topicDao: TopicDao,
    //private val topicFtsDao: TopicFtsDao,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SearchContentsRepository {
    override suspend fun populateFtsData() {
        TODO("Not yet implemented")
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchContents(searchQuery: String): Flow<SearchResult> {
        // Surround the query by asterisks to match the query when it's in the middle of
        // a word
        val newsResourceIds = newsResourceDao.getMixtusByDateNew(20250131)
        val topicIds = userDataRepository.userData//topicFtsDao.searchAllTopics("*$searchQuery*")

        val newsResourcesFlow = newsResourceIds
            .mapLatest { it }
            .distinctUntilChanged()
            .flatMapLatest {
                //userDataRepository.userData
                newsResourceDao.getMixtusByDateNew(20250131)
            }
        val topicsFlow = topicIds
            .mapLatest { it }
            .distinctUntilChanged()
        //.flatMapLatest(userDataRepository.userData)
        return combine(newsResourcesFlow, topicsFlow) { newsResources, topics ->
            SearchResult(
                userData = topics,
                //topics = topics.map { it },
                universalis = Universalis(),
                //newsResources = newsResources.map { it.asExternalModel() },
            )
        }
    }

    override fun getSearchContentsCount(): Flow<Int> {
        TODO("Not yet implemented")
    }


}