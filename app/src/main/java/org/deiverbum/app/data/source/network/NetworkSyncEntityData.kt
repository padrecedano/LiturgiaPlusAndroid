package org.deiverbum.app.data.source.network

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.*
import javax.inject.Inject

/**
 * <p>Fuente de datos de red para la sincronización.</p>
 * <p>Se llama esta fuente de datos en primer lugar.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class NetworkSyncEntityData @Inject constructor(
    private val todayApi: TodayApi
) : SyncEntityData {

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        SpannableStringBuilder()
        val syncResponse= SyncResponse(SpannableStringBuilder(),null)
        //firebase.loadInitialToday(object : FirebaseCallBack)
        return syncResponse
    }

    /**
     * <p>Este método lanza la sincronización inicial las fechas de los próximos siete días
     * en Firestore mediante {@link FirebaseDataSource#loadInitialToday()}</p>
     * <p>Se usa como alternativa, en caso de que falle la llamada a {@link SyncRepository#initialSync()}</p>
     */
    suspend fun initialSync(syncRequest: SyncRequest): SyncResponse {
        val syncResponse = SyncResponse(SpannableStringBuilder(),null)

        val todayAll=todayApi.getTodayAll("all")
        if(todayAll.isNotEmpty()) {
            //launchSyncWorker()
            //val allHomily: List<Homily> = todayDao.getHomily().toHomilyDomainModel()
            val sb = SpannableStringBuilder()
            //syncResponse.
            //todayApi.i
            /*allHomily.forEach {
            sb.append("\n\n")
            sb.append(it.homilyID.toString())
            sb.append("\n\n")
            sb.append(it.homily)
        }*/
            //return sb;
            //var se=todayDao.syncInfoo()
        }
            //syncResponse.dataForView=SpannableStringBuilder( se!!.getAll(false))

        return syncResponse

    }

    override suspend fun addSync(sync: SyncResponse) {
        TODO("Not yet implemented")
    }

}