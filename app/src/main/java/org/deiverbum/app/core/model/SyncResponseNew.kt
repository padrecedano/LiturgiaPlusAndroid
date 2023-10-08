package org.deiverbum.app.core.model

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.data.SyncStatus
import org.deiverbum.app.core.model.data.Today

/**
 * <p>Maneja las respuestas destinadas al módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class SyncResponseNew(

    //var dataForView: SpannableStringBuilder,//1:NetWork, 2:Firebase, 0:Error
    //_syncStatus: Flow<SyncStatus>,
    _syncStatus: Flow<SyncStatus>,

    _allToday: List<Today> = emptyList()
) {
    var syncStatus: Flow<SyncStatus> = _syncStatus
    //var syncStatus: SyncStatus = _syncStatus

    var allToday: List<Today> = _allToday

}