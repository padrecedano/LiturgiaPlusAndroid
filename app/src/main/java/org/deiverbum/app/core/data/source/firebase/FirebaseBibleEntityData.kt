package org.deiverbum.app.core.data.source.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.deiverbum.app.core.data.source.BibleEntityData
import org.deiverbum.app.core.model.biblia.BibleBookRequest
import org.deiverbum.app.core.model.biblia.BibleBooks
import org.deiverbum.app.util.Configuration
import javax.inject.Inject

/**
 * Fuente de datos de Firebase para el módulo `Biblia`.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class FirebaseBibleEntityData @Inject constructor() : BibleEntityData {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Este método obtiene desde Firestore las fechas de los próximos ocho días.
     * Se usa como alternativa, en caso de que falle la sincronización inicial por defecto.
     */

    override suspend fun getBibleBook(bookRequest: BibleBookRequest): BibleBooks {

        try {
            val bookRef =
                firebaseFirestore.collection(Configuration.BIBLIA_PATH).document(bookRequest.bookId.toString())
                    .get()
                    .await()
                    .toObject(BibleBooks::class.java)
            return bookRef!!
            /*val bibleBook = firebaseFirestore.collection(Configuration.FIREBASE_SYNC_PATH)
                .whereGreaterThanOrEqualTo(
                    "todayDate",
                    Utils.getTodayMinus(1)
                )
                .limit(8).get()
                .await().toObjects(BibleBook::class.java)
                    as BibleBook
            return bibleBook*/
            //val syncStatus=SyncStatus()
            //syncStatus.source=Source.FIREBASE
            //SyncResponse(syncStatus,userList)
        } catch (e: Exception) {
            return BibleBooks()
            //SyncResponse(SyncStatus(e.message.toString(), 0))
        }
    }


}
