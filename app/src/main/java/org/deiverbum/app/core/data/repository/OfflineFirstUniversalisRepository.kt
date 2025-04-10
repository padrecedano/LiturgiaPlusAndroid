package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.database.dao.UniversalisDao
import org.deiverbum.app.core.database.model.external.CommentariiExternal
import org.deiverbum.app.core.database.model.external.CompletoriumExternal
import org.deiverbum.app.core.database.model.external.HomiliaeExternal
import org.deiverbum.app.core.database.model.external.LaudesExternal
import org.deiverbum.app.core.database.model.external.MissaeLectionumExternal
import org.deiverbum.app.core.database.model.external.MixtusExternal
import org.deiverbum.app.core.database.model.external.NonamExternal
import org.deiverbum.app.core.database.model.external.OfficiumExternal
import org.deiverbum.app.core.database.model.external.RosariumExternal
import org.deiverbum.app.core.database.model.external.SanctiiExternal
import org.deiverbum.app.core.database.model.external.SextamExternal
import org.deiverbum.app.core.database.model.external.TertiamExternal
import org.deiverbum.app.core.database.model.external.UniversalisExternal
import org.deiverbum.app.core.database.model.external.VesperasExternal
import org.deiverbum.app.core.database.model.external.asExternalModel
import org.deiverbum.app.core.database.model.relation.LocalOfficiumPascua
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.datastore.ChangeListVersions
import org.deiverbum.app.core.model.universalis.Universalis
import org.deiverbum.app.core.model.universalis.UniversalisResourceQuery
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.util.DateTimeUtil
import org.deiverbum.app.util.Utils
import javax.inject.Inject

// Heuristic value to optimize for serialization and deserialization cost on client and server
// for each news resource batch.
private const val SYNC_BATCH_SIZE = 40

/**
 * Implementación del backend de almacenamiento en disco de [UniversalisRepository].
 * Las lecturas se realizan exclusivamente desde el almacenamiento local para admitir el acceso sin conexión.
 *
 * @since 2025.1
 */
class OfflineFirstUniversalisRepository @Inject constructor(
    private val universalisDao: UniversalisDao,
    private val network: NiaNetworkDataSource,
    //private val localFile: LocalDataSource,
    val userDataRepository: UserDataRepository,
    ) : UniversalisRepository {

    override fun countUniversalis(query: UniversalisResourceQuery): Flow<Int> {
        return universalisDao.countUniversalis(query.filterDate)
    }
    override fun getUniversalisByDate(
        query: UniversalisResourceQuery,
    ): Flow<Universalis> {
        //val count=universalisDao.countUniversalis(query.filterDate)
        //count.collect()
        return when (query.filterTopicId) {
            1 -> {
                if (DateTimeUtil.isPasqua(query.filterDate)) {
                    universalisDao.getOfficiumPasqua(1).map(LocalOfficiumPascua::asExternalModel)
                } else {
                    universalisDao.getMixtusByDate(
                        filterDate = query.filterDate,
                    ).map(MixtusExternal::asExternalModel)
                }

            }

            2 -> {
                if (DateTimeUtil.isPasqua(query.filterDate)) {
                    universalisDao.getOfficiumPasqua(1).map(LocalOfficiumPascua::asExternalModel)
                } else {
                    universalisDao.getOfficiumByDate(
                filterDate = query.filterDate,
                    ).map(OfficiumExternal::asExternalModel)
                }
            }

            3 -> universalisDao.getLaudesByDate(
                filterDate = query.filterDate,
            ).map(LaudesExternal::asExternalModel)

            4 -> universalisDao.getTertiamByDate(
                filterDate = query.filterDate,
            ).map(TertiamExternal::asExternalModel)

            5 -> universalisDao.getSextamByDate(
                filterDate = query.filterDate,
            ).map(SextamExternal::asExternalModel)

            6 -> universalisDao.getNonamByDate(
                filterDate = query.filterDate,
            ).map(NonamExternal::asExternalModel)

            7 -> universalisDao.getVesperasByDate(
                filterDate = query.filterDate,
            ).map(VesperasExternal::asExternalModel)

            8 -> universalisDao.getCompletoriumByDate(
                filterDate = query.filterDate,
            ).map(CompletoriumExternal::asExternalModel)

            11 -> universalisDao.getMissaeLectionumByDate(
                filterDate = query.filterDate,
            ).map(MissaeLectionumExternal::asExternalModel)

            12 -> universalisDao.getCommentariiByDate(
                filterDate = query.filterDate,
            ).map(CommentariiExternal::asExternalModel)

            13 -> universalisDao.getHomiliaeByDate(
                filterDate = query.filterDate,
            ).map(HomiliaeExternal::asExternalModel)

            20 -> {
                val monthAndDay = Utils.getMonthAndDay(query.filterDate.toString())
                universalisDao.getSanctiiByDate(
                    monthAndDay?.get(0),
                    monthAndDay?.get(1)
                ).map(SanctiiExternal::asExternalModel)
            }

            30 ->
                universalisDao.getRosariumByDate(
                    DateTimeUtil.getDayOfWeek(
                        query.filterDate
                    )
                ).map(RosariumExternal::asExternalModel)

            else -> universalisDao.getUniversalisByDate(
                filterDates = query.filterDate
            ).onEmpty {
                println("Vacío")
            }.map(UniversalisExternal::asExternalModel)

        }
        
    }

    override suspend fun insertFromRemote(query: UniversalisResourceQuery) {
        val networkUniversalis = network.getUniversalis(listOf(query.filterDate))
        universalisDao.upsertUniversalis(networkUniversalis.data)
    }

    override fun getUniversalisForTest(id: Int): Flow<Universalis> {
        //TODO("Not yet implemented")
        return universalisDao.getUniversalisByDate(id).map(UniversalisExternal::asExternalModel)

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
                universalisDao.upsertUniversaliss(
                    TODO()
                    //entities = networkTopics.map(NetworkUniversalis::asEntity),
                )
            },
        )
}