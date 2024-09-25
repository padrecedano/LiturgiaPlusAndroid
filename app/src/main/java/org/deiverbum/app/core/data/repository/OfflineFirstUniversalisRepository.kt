package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.external.CommentariiExternal
import org.deiverbum.app.core.database.model.external.CompletoriumExternal
import org.deiverbum.app.core.database.model.external.HomiliaeExternal
import org.deiverbum.app.core.database.model.external.LaudesExternal
import org.deiverbum.app.core.database.model.external.MissaeLectionumExternal
import org.deiverbum.app.core.database.model.external.MixtusExternal
import org.deiverbum.app.core.database.model.external.NonamExternal
import org.deiverbum.app.core.database.model.external.OfficiumExternal
import org.deiverbum.app.core.database.model.external.PopulatedVesperasResource
import org.deiverbum.app.core.database.model.external.SanctiiExternal
import org.deiverbum.app.core.database.model.external.SextamExternal
import org.deiverbum.app.core.database.model.external.TertiamExternal
import org.deiverbum.app.core.database.model.external.UniversalisExternal
import org.deiverbum.app.core.database.model.external.asExternalModel
import org.deiverbum.app.core.datastore.ChangeListVersions
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.util.Utils
import javax.inject.Inject

// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Implementación del backend de almacenamiento en disco de [UniversalisRepository].
 * Las lecturas se realizan exclusivamente desde el almacenamiento local para admitir el acceso sin conexión.
 *
 * @since 2024.1
 */
class OfflineFirstUniversalisRepository @Inject constructor(
    private val universalisDao: UniversalisDao,
    private val network: NiaNetworkDataSource,
    val userDataRepository: UserDataRepository,

    ) : UniversalisRepository {

    /**
     * Returns available news resources (joined with user data) matching the given query.
     */
    override fun getUniversalisForTest(id: Int): Flow<Universalis> =
        universalisDao.getUniversalisByDate(id).map { it.asExternalModel() }

    override fun getReader(): String {
        return "Lorem ipsum"
    }

    override fun getUniversalisByDate(
        query: UniversalisResourceQuery,
    ): Flow<List<UniversalisResource>> {
        val r = when (query.filterTopicsIds) {
            1 -> universalisDao.getMixtusByDate(
                filterDates = query.filterDates!!,
            ).map {
                it.map(MixtusExternal::asExternalModel)
            }

            2 -> universalisDao.getOfficiumByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(OfficiumExternal::asExternalModel) }

            3 -> universalisDao.getLaudesByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(LaudesExternal::asExternalModel) }

            4 -> universalisDao.getTertiamByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(TertiamExternal::asExternalModel) }

            5 -> universalisDao.getSextamByDate(
                filterDates = query.filterDates!!,
            ).map {
                it.map(SextamExternal::asExternalModel)
            }

            6 -> universalisDao.getNonamByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(NonamExternal::asExternalModel) }

            7 -> universalisDao.getVesperasByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(PopulatedVesperasResource::asExternalModel) }

            8 -> universalisDao.getCompletoriumByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(CompletoriumExternal::asExternalModel) }

            11 -> universalisDao.getMissaeLectionumByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(MissaeLectionumExternal::asExternalModel) }

            12 -> universalisDao.getCommentariiByDate(
                filterDates = query.filterDates!!,
            ).map {
                it.map(CommentariiExternal::asExternalModel)
            }

            13 -> universalisDao.getHomiliaeByDate(
                filterDates = query.filterDates!!,
            ).map { it.map(HomiliaeExternal::asExternalModel) }

            20 -> {
                val monthAndDay = Utils.getMonthAndDay(query.filterDates!!.elementAt(0).toString())

                universalisDao.getSanctiiByDate(
                    monthAndDay?.get(0),
                    monthAndDay?.get(1)
                ).map { it.map(SanctiiExternal::asExternalModel) }
            }

            else -> universalisDao.getUniversalisByDate(
                filterDates = query.filterDates!!,
            ).onEmpty {
                println("Vacío")
            }.map {
                it.map(UniversalisExternal::asExternalModel)
            }
        }
        return r
    }

    override fun getOneUniversalisByDate(query: UniversalisResourceQuery): Flow<UniversalisResource> {
        TODO("Not yet implemented")
    }



    override suspend fun insertFromRemote(query: UniversalisResourceQuery) {
        val networkUniversalis = network.getUniversalis(query.filterDates!!.toList())
        universalisDao.upsertUniversaliss(networkUniversalis.data)
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
            modelDeleter = universalisDao::deleteUniversalis,
            modelUpdater = { changedIds ->
                val networkTopics = network.getTopics(ids = changedIds)
                universalisDao.upsertUniversalis(
                    TODO()
                    //entities = networkTopics.map(NetworkUniversalis::asEntity),
                )
            },
        )
}