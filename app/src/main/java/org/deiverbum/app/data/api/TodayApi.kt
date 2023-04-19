package org.deiverbum.app.data.api

import org.deiverbum.app.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface TodayApi {

    @GET("/jakarta/daily.json")
    suspend fun getBible(
        @Query("city") city: String,
        @Query("key") key: String? = "BuildConfig.API_KEY"
    ): BibleResponse

    @GET("/jakarta/daily.json")
    suspend fun getHomily(
        @Query("city") city: String,
        @Query("key") key: String? = "BuildConfig.API_KEY"
    ): HomilyResponse

    @GET("/jakarta/daily.json")
    suspend fun getBiblicalComment(
        @Query("city") city: String,
        @Query("key") key: String? = "BuildConfig.API_KEY"
    ): BiblicalCommentResponse

    @GET("/jakarta/daily.json")
    suspend fun getBreviary(
        @Query("city") city: String,
        @Query("key") key: String? = "BuildConfig.API_KEY"
    ): TodayResponse

}
