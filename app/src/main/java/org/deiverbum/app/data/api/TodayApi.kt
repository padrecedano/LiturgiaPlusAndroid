package org.deiverbum.app.data.api

import io.reactivex.rxjava3.core.Single
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
    suspend fun getBreviary(
        @Query("a") city: String,
        @Query("b") key: String? = "BuildConfig.API_KEY"
    ): TodayResponse

    @GET("{endPoint}/{dateString}")
    fun getBreviario(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): BreviaryHour?


    @GET("today/{thePath}")
    fun getTodayAll(@Path("thePath") thePath: String?): List<Today?>

}
