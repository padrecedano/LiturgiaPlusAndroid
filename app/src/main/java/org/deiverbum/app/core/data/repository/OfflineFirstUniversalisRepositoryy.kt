package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.data.changeListSync
import org.deiverbum.app.core.database.dao.nia.UniversalisDao
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.external.CompletoriumExternal
import org.deiverbum.app.core.database.model.external.LaudesExternal
import org.deiverbum.app.core.database.model.external.MissaeLectionumExternal
import org.deiverbum.app.core.database.model.external.MixtusExternal
import org.deiverbum.app.core.database.model.external.NonamExternal
import org.deiverbum.app.core.database.model.external.OfficiumExternal
import org.deiverbum.app.core.database.model.external.PopulatedVesperasResource
import org.deiverbum.app.core.database.model.external.SextamExternal
import org.deiverbum.app.core.database.model.external.TertiamExternal
import org.deiverbum.app.core.database.model.external.UniversalisExternal
import org.deiverbum.app.core.database.model.external.asExternalModel
import org.deiverbum.app.core.datastore.ChangeListVersions
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.network.NiaNetworkDataSource
import javax.inject.Inject

/**
 * Implementación del backend de almacenamiento en disco de [UniversalisRepository].
 * Las lecturas se realizan exclusivamente desde el almacenamiento local para admitir el acceso sin conexión.
 *
 * @since 2024.1
 */
class OfflineFirstUniversalisRepositoryy @Inject constructor(
    private val universalisDao: UniversalisDao,
    private val network: NiaNetworkDataSource,
) : UniversalisRepository {

    fun getUniversalisList(): Flow<List<Universalis>> =
        universalisDao.getUniversalisList()
            .map { it.map(UniversalisEntity::asExternalModel) }

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
            ).map { it.map(SextamExternal::asExternalModel) }

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

            /*else -> /*topicDao.getUniversalisByDate(
                filterDates = query.filterDates!!,
            ).map {
                it.map(PopulatedUniversalisResource::asExternalModel)


        }*/
            {.onEmpty {
            val n=network.getUniversalis(listOf("20240729"))
            println(n.data[0].todayDate)
        }
        return r
    }*/
            //TODO: Crear una respuesta vacía para el else
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





