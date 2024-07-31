package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.database.dao.nia.UniversalisDao
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.network.NiaNetworkDataSource
import javax.inject.Inject

/**
 * Disk storage backed implementation of the [TodaysRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstTodayRepository @Inject constructor(
    private val topicDao: UniversalisDao,
    private val network: NiaNetworkDataSource,
) : TodaysRepository {

    /*override fun getTopics(): Flow<List<Topic>> =
        topicDao.getTopicEntities()
            .map { it.map(TopicEntity::asExternalModel) }*/

    override fun getTopic(todayDate: Int): Flow<Universalis> =
        topicDao.getTopicEntity(20240319).map { it.asExternalModel() }

    /*override suspend*/ fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }


}
