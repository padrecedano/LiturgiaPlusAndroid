package org.deiverbum.app.data.api

import io.reactivex.rxjava3.core.Single
import org.deiverbum.app.data.model.*
import org.deiverbum.app.model.*
import org.deiverbum.app.model.crud.Crud
import retrofit2.http.*

interface TodayApi {


    @GET("{endPoint}/{dateString}")
    suspend fun getToday(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): Today?


    @GET("today/{thePath}")
    suspend fun getTodayAll(@Path("thePath") thePath: String?): List<Today?>

    @POST("crud/")
    suspend fun postCrud(@Body syncStatus: SyncStatus?): Crud?

}
