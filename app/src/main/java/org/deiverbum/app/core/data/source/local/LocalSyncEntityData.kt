package org.deiverbum.app.core.data.source.local

import org.deiverbum.app.core.data.source.SyncEntityData
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 * Fuente de datos local para la sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

class LocalSyncEntityData @Inject constructor(
    private val todayDao: TodayDao
) : SyncEntityData {

    /**
     * Este método es creado en [SyncFactory.create][org.deiverbum.app.data.factory.SyncFactory.create].
     * Recibe una petición de sincronización expresada
     * en un objeto del tipo [SyncRequest].
     *
     * Verifica si el valor de la propiedad [SyncRequest.yearToClean]
     * no es igual a `0`, en cuyo caso eliminará de la base de datos
     * los registros del calendario de años anteriores, optimizando el espacio.
     *
     * @param syncRequest es un objeto [SyncRequest] con la petición de sincronización.
     * @return Un objeto [SyncResponse] con una propiedad [Source.LOCAL].
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        val se = todayDao.syncInfo()
        if (syncRequest.yearToClean != 0) {
            val rowsDeleted = todayDao.deletePastYear(syncRequest.yearToClean)
            if (rowsDeleted > 0) {
                se.lastYearCleaned = true
            }
        }
        se.source = Source.LOCAL

        return SyncResponse(se)
        //return SyncResponse(SyncStatus())
    }





    /**
     * Este método inserta en la base de datos el contenido de un objeto [SyncResponse].
     *
     * Verifica si el valor de la propiedad [SyncResponse.allToday] no está vacío para hacer la inserción.
     * Si se inserta alguna fila, llama al método [TodayDao.syncUpdate] para actualizar
     * la fecha de la última sincronización al momento actual.
     *
     * @param syncResponse Es un objeto [SyncResponse] con los datos a sincronizar.
     */
    override suspend fun addSync(syncResponse: SyncResponse) {
        if (syncResponse.allToday.isNotEmpty()) {
            //syncResponse.allToday=syncResponse.allToday.take(3)
            val insertedRows =
                todayDao.universalisInsertAll(syncResponse.allToday)
            if (insertedRows.isNotEmpty() && syncResponse.syncStatus.lastUpdate != "") {
                todayDao.syncUpdate(syncResponse.syncStatus.lastUpdate)
            }
        }

    }

}