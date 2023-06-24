package org.deiverbum.app.data.source.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.model.Today
import org.deiverbum.app.util.Source
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Utils
import javax.inject.Inject

/**
 * Fuente de datos de Firebase para la sincronización.
 * Se llama a esta fuente de datos si falla la llamada a [org.deiverbum.app.data.source.network.NetworkSyncEntityData].
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class FirebaseSyncEntityData @Inject constructor() : SyncEntityData {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Este método obtiene desde Firestore las fechas de los próximos ocho días.
     * Se usa como alternativa, en caso de que falle la sincronización inicial por defecto.
     */

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        return try {
            val todayList = firebaseFirestore.collection(Configuration.FIREBASE_SYNC_PATH)
                .whereGreaterThanOrEqualTo(
                    "todayDate",
                    Utils.getTodayMinus(1)
                )
                .limit(8).get()
                .await().toObjects(Today::class.java)
                    as List<Today>
            val syncStatus=SyncStatus()
            syncStatus.source=Source.FIREBASE
            SyncResponse(syncStatus,todayList)
        } catch (e: Exception) {
            SyncResponse(SyncStatus(e.message.toString(), 0))
        }
    }

    override suspend fun addSync(syncResponse: SyncResponse) {
        //
    }

}
