package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.database.dao.nia.UniversalisDao
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.nia.PopulatedUniversalisResource
import org.deiverbum.app.core.database.model.nia.asExternalModel
import org.deiverbum.app.core.datastore.ChangeListVersions
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.network.NiaNetworkDataSource
import javax.inject.Inject

/**
 * Disk storage backed implementation of the [TopicsRepository].
 * Reads are exclusively from local storage to support offline access.
 */
class OfflineFirstUniversalisRepositoryy @Inject constructor(
    private val topicDao: UniversalisDao,
    private val network: NiaNetworkDataSource,
) : UniversalisRepositoryy {

    override fun getUniversalisList(): Flow<List<Universalis>> =
        topicDao.getUniversalisList()
            .map { it.map(UniversalisEntity::asExternalModel) }


    override fun getUniversalisById(id: String): Flow<Universalis> =
        topicDao.getUniversalisByDate(id.toInt()).map { it.asExternalModel() }

    override fun getNewsResources(
        query: UniversalisResourceQuery,

        ): Flow<List<UniversalisResource>> = topicDao.getUniversalisByDate(
        filterTopicIds = query.filterTopicIds ?: emptySet(),
        //filterNewsIds = query.filterNewsIds ?: emptySet(),
        //todayDate=20240319

    )
        .map {
            it.map(PopulatedUniversalisResource::asExternalModel)
        }


    /*
    override fun getNewsResources(todayDate:Int): Flow<Universalis> =
        topicDao.getUniversalisPopulatedByDate(todayDate = 20240319)
        .map(PopulatedUniversalisResource::asExternalModelk)
*/
    override suspend fun syncWith(synchronizer: Synchronizer): Boolean =
        synchronizer.changeListSync(
            versionReader = ChangeListVersions::topicVersion,
            changeListFetcher = { currentVersion ->
                network.getTopicChangeList(after = currentVersion)
            },
            versionUpdater = { latestVersion ->
                copy(topicVersion = latestVersion)
            },
            modelDeleter = topicDao::deleteUniversalis,
            modelUpdater = { changedIds ->
                val networkTopics = network.getTopics(ids = changedIds)
                topicDao.upsertUniversalis(
                    TODO()
                    //entities = networkTopics.map(NetworkUniversalis::asEntity),
                )
            },
        )
}
