package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.BibleEntityData
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.data.source.firebase.FirebaseBibleEntityData
import org.deiverbum.app.data.source.firebase.FirebaseSyncEntityData
import org.deiverbum.app.data.source.local.LocalSyncEntityData
import org.deiverbum.app.data.source.network.NetworkSyncEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 * Factory para el  módulo `Biblia`.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

class BibleFactory @Inject constructor(
    private val firebaseSyncEntityData: FirebaseBibleEntityData

) {

    fun create(source: Source): BibleEntityData {
        return  firebaseSyncEntityData
        }

}