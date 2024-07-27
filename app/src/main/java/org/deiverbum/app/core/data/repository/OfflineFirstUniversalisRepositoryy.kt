package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.database.dao.nia.UniversalisDao
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.nia.PopulatedCompletoriumResource
import org.deiverbum.app.core.database.model.nia.PopulatedLaudesResource
import org.deiverbum.app.core.database.model.nia.PopulatedMixtusResource
import org.deiverbum.app.core.database.model.nia.PopulatedNonamResource
import org.deiverbum.app.core.database.model.nia.PopulatedOfficiumResource
import org.deiverbum.app.core.database.model.nia.PopulatedSextamResource
import org.deiverbum.app.core.database.model.nia.PopulatedTertiamResource
import org.deiverbum.app.core.database.model.nia.PopulatedVesperasResource
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
) : UniversalisRepository {

    fun getUniversalisList(): Flow<List<Universalis>> =
        topicDao.getUniversalisList()
            .map { it.map(UniversalisEntity::asExternalModel) }


    fun getUniversalisById(id: String): Flow<Universalis> =
        topicDao.getUniversalisByDate(id.toInt()).map { it.asExternalModel() }

    override fun getUniversalisByDate(
        query: UniversalisResourceQuery,
    ): Flow<List<UniversalisResource>> {
        return when (query.filterTopicsIds) {
            1 -> topicDao.getMixtusByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedMixtusResource::asExternalModel) }

            2 -> topicDao.getOfficiumByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedOfficiumResource::asExternalModel) }

            3 -> topicDao.getLaudesByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedLaudesResource::asExternalModel) }

            4 -> topicDao.getTertiamByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedTertiamResource::asExternalModel) }

            5 -> topicDao.getSextamByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedSextamResource::asExternalModel) }

            6 -> topicDao.getNonamByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedNonamResource::asExternalModel) }

            7 -> topicDao.getVesperasByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedVesperasResource::asExternalModel) }

            8 -> topicDao.getCompletoriumByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedCompletoriumResource::asExternalModel) }
            else -> topicDao.getUniversalisByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedSextamResource::asExternalModel) }

        }
    }

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





