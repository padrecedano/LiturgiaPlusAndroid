package org.deiverbum.app.data.api

import org.deiverbum.app.data.model.*
import org.deiverbum.app.model.*
import retrofit2.http.*

interface TodayApi {


    @GET("{endPoint}/{dateString}")
    suspend fun getToday(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): Today?


    @GET("today/{thePath}")
    suspend fun getTodayAll(@Path("thePath") thePath: String?): List<Today?>

}
