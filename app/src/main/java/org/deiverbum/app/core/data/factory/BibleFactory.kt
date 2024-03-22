package org.deiverbum.app.core.data.factory

import org.deiverbum.app.core.data.source.firebase.FirebaseBibleEntityData
import javax.inject.Inject

/**
 * Factory para el  módulo `Biblia`.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */

class BibleFactory @Inject constructor(
    private val firebaseSyncEntityData: FirebaseBibleEntityData

) {

    fun create(): FirebaseBibleEntityData {
        return firebaseSyncEntityData
    }

}