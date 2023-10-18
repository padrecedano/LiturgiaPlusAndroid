package org.deiverbum.app.core.network.retrofit

import kotlinx.serialization.Serializable
import okhttp3.Call
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.util.Configuration
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitNiaNetworkApi {
    /*    @GET(value = "topics")
        suspend fun getTopics(
            @Query("id") ids: List<String>?,
        ): NetworkResponse<List<NetworkTopic>>

        @GET(value = "newsresources")
        suspend fun getNewsResources(
            @Query("id") ids: List<String>?,
        ): NetworkResponse<List<NetworkNewsResource>>

        @GET(value = "changelists/topics")
        suspend fun getTopicChangeList(
            @Query("after") after: Int?,
        ): List<NetworkChangeList>

        @GET("{endPoint}/{dateString}")
        suspend fun getToday(
            @Path("endPoint") endPoint: String?,
            @Path("dateString") dateString: String?
        ): Universalis?*/

    @GET(value = "tercia/{dateString}")
    suspend fun getNewsResourcesChangeList(
        @Path("dateString") dateString: Int?,
    ): Universalis
}

private const val NIA_BASE_URL = Configuration.URL_API

/**
 * Wrapper for data provided from the [NIA_BASE_URL]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

/**
 * [Retrofit] backed [NiaNetworkDataSource]
 */
@Singleton
class RetrofitNiaNetwork @Inject constructor(
    //networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NiaNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(NIA_BASE_URL)
        .callFactory(okhttpCallFactory)
        /*.addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )*/
        .build()
        .create(RetrofitNiaNetworkApi::class.java)

    /*
        override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
            networkApi.getTopics(ids = ids).data

        override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
            networkApi.getNewsResources(ids = ids).data

        override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
            networkApi.getTopicChangeList(after = after)
    */
    override suspend fun getNewsResourceChangeList(after: Int?): Universalis =
        networkApi.getNewsResourcesChangeList(after)
}