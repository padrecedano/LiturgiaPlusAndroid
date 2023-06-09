package org.deiverbum.app.data.source.firebase

import android.text.SpannableStringBuilder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.model.Today
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.Configuration
import org.deiverbum.app.utils.Utils
import javax.inject.Inject

/**
 * <p>Fuente de datos de Firebase para la sincronización.</p>
 * <p>Se llama a esta fuente de datos si falla la llamada a [org.deiverbum.app.data.source.network.NetworkSyncEntityData].</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class FirebaseSyncEntityData @Inject constructor() : SyncEntityData {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * <p>Este método obtiene desde Firestore las fechas de los próximos siete días.</p>
     * <p>Se usa como alternativa, en caso de que falle la sincronización inicial por defecto.</p>
     */

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        try {
            val userList = firebaseFirestore.collection(Configuration.FIREBASE_SYNC_PATH)
                .whereGreaterThanOrEqualTo(
                    "todayDate",
                    Utils.getTodayMinus(45)
                )
                .limit(7).get()
                .await().toObjects(Today::class.java)
                    as List<Today>
            var syncStatus=SyncStatus()
            syncStatus.source=Source.FIREBASE
            return SyncResponse(syncStatus,userList)
        } catch (e: Exception) {
            return SyncResponse(SyncStatus(e.message.toString(), 0))
        }
    }

    override suspend fun addSync(sync: SyncResponse) {
        //
    }

}