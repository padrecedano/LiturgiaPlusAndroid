package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.database.model.UITopicEntity
import org.deiverbum.app.core.database.model.asExternalModel
import org.deiverbum.app.core.model.data.Topic
import org.deiverbum.app.core.model.data.UITopic
import javax.inject.Inject

class OfflineFirstHomeTopicsRepository @Inject constructor(
    private val universalisDao: UniversalisDao,
) : HomeTopicsRepository {


    override fun getTopics(): Flow<List<UITopic>> =
        universalisDao.getTopicEntities()
            .map { it.map(UITopicEntity::asExternalModel) }

    override fun getTopic(id: String): Flow<Topic> {
        TODO("Not yet implemented")
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }

}
