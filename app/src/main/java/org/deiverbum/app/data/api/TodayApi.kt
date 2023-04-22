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
    suspend fun getBible(
        @Query("a") a: String,
        @Query("b") key: String? = "BuildConfig.API_KEY"
    ): BibleResponse

    @GET("today/{thePath}")
    suspend fun getHomily(
        @Query("a") a: String,
        @Query("b") key: String? = "BuildConfig.API_KEY"
    ): HomilyResponse

    @GET("today/{thePath}")
    suspend fun getBiblicalComment(
        @Query("a") a: String,
        @Query("b") key: String? = "BuildConfig.API_KEY"
    ): BiblicalCommentResponse

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


}
