package org.deiverbum.app.domain.model

import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.model.Today

/**
 * <p>Maneja las respuestas destinadas al módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class SyncResponse(

    //var dataForView: SpannableStringBuilder,//1:NetWork, 2:Firebase, 0:Error
    _syncStatus: SyncStatus,
    _allToday: List<Today> = emptyList()
) {
    var syncStatus: SyncStatus = _syncStatus
    var allToday: List<Today> = _allToday

}