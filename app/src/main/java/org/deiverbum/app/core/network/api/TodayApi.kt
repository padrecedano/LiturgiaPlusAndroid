package org.deiverbum.app.core.network.api

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.data.SyncStatus
import org.deiverbum.app.core.model.data.Today
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisSync
import org.deiverbum.app.core.model.data.crud.Crud
import retrofit2.http.*

interface TodayApi {


    @GET("{endPoint}/{dateString}")
    suspend fun getToday(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): Today?

    @GET("{endPoint}/{dateString}")
    suspend fun getUniversalis(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): Universalis?

    @GET("test/{dateString}")
    //suspend fun getTest(@Path("dateString") dateString: String?): Universalis
    suspend fun getTest(@Path("dateString") dateString: String?): Universalis

    @GET("all")
    suspend fun getUniversalisSync(): UniversalisSync

    @GET("today/{thePath}")
    suspend fun getTodayAllNew(@Path("thePath") thePath: String?): List<Today>


    @POST("crud/")
    suspend fun postCrud(@Body syncStatus: SyncStatus?): Crud


    @POST("crud/")
    fun postCrudd(@Body syncStatus: SyncStatus): Flow<SyncStatus>

}
