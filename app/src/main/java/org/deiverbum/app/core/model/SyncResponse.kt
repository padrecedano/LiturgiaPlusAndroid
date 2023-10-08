package org.deiverbum.app.core.model

import org.deiverbum.app.core.model.data.SyncStatus
import org.deiverbum.app.core.model.data.Today

/**
 * Maneja las respuestas destinadas al módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 *
 * @param [_syncStatus] Un objeto [SyncStatus] con el estado de la sincronización.
 * @param [_allToday] Una lista de objetos [Today] con eventuales fechas a sincronizar.
 */
class SyncResponse(

    //var dataForView: SpannableStringBuilder,//1:NetWork, 2:Firebase, 0:Error
    _syncStatus: SyncStatus,
    _allToday: List<Today> = emptyList()
) {
    var syncStatus: SyncStatus = _syncStatus
    var allToday: List<Today> = _allToday

}