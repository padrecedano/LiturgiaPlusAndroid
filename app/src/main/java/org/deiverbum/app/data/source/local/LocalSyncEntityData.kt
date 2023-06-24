package org.deiverbum.app.data.source.local

import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.util.Source
import org.deiverbum.app.util.Utils
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
     * Este método recibe una petición de sincronización expresada
     * en un objeto del tipo [SyncRequest].
     *
     * Verifica si el valor de la propiedad [SyncRequest.yearToClean]
     * no es igual a `0`. Esto sirve para borrar de la base de datos
     * los datos del calendario de años anteriores, optimizando el espacio.
     *
     * @param syncRequest es un objeto [SyncRequest] con la petición de sincronización.
     * @return Un objeto [SyncResponse] con una propiedad [Source.LOCAL].
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        val se = todayDao.syncInfo()
        if (syncRequest.yearToClean != 0) {
            val rowsDeleted = todayDao.deleteLastYear(syncRequest.yearToClean)
            if (rowsDeleted > 0) {
                se?.lastYearCleaned = syncRequest.yearToClean - 1
            }
        }
        se?.source = Source.LOCAL
        return SyncResponse(se!!)
    }

    /**
     * Este método inserta en la base de datos el contenido de un objeto [SyncResponse].
     *
     * Verifica si el valor de la propiedad [SyncResponse.allToday] no está vacío para hacer la inserción.
     * Si se inserta alguna filla, llama al método [TodayDao.syncUpdate] para actualizar
     * la fecha de la última sincronización al momento actual.
     *
     * @param syncResponse Es un objeto [SyncResponse] con los datos a sincronizar.
     */
    override suspend fun addSync(syncResponse: SyncResponse) {
        if (syncResponse.allToday.isNotEmpty()) {
            val insertedRows =
                todayDao.insertAllTodays(syncResponse.allToday)
            if (insertedRows.isNotEmpty()) {
                todayDao.syncUpdate(Utils.getCurrentTimeStamp())
            }
        }
    }
}