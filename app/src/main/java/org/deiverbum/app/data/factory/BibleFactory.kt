package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.BibleEntityData
import org.deiverbum.app.data.source.firebase.FirebaseBibleEntityData
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

    fun create(): BibleEntityData {
        return  firebaseSyncEntityData
    }

}