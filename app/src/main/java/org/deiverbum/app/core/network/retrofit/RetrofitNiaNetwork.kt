package org.deiverbum.app.core.network.retrofit

import androidx.tracing.trace
import com.squareup.moshi.Moshi
import kotlinx.serialization.Serializable
import okhttp3.Call
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.core.network.model.NetworkChangeList
import org.deiverbum.app.core.network.model.NetworkNewsResource
import org.deiverbum.app.core.network.model.NetworkTopic
import org.deiverbum.app.core.network.model.NetworkUniversalisResource
import org.deiverbum.app.util.Configuration.URL_API
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitNiaNetworkApi {
    @GET(value = "topics")
    suspend fun getTopics(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkTopic>>

    @GET(value = "newsresources")
    suspend fun getNewsResources(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkNewsResource>>

    @GET(value = "universalisresources/{dateString}")
    suspend fun getUniversalisResources(
        //@Query("id") ids: List<String>?,
        @Path("dateString") dateString: String?
    ): NetworkUniversalisResource

    @GET(value = "universalisresources/{dateString}")
    suspend fun getUniversalisResourcesList(
        //@Query("id") ids: List<String>?,
        @Path("dateString") dateString: String?
    ): NetworkResponse<List<NetworkUniversalisResource>>

    @GET(value = "changelists/topics")
    suspend fun getTopicChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    @GET(value = "changelists/newsresources")
    suspend fun getNewsResourcesChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    //Deprecated methods
    @GET("{endPoint}/{dateString}")
    suspend fun getUniversalisOld(
        @Path("endPoint") endPoint: String?,
        @Path("dateString") dateString: String?
    ): Universalis?
}

private const val NIA_BASE_URL = URL_API//BuildConfig.BACKEND_URL

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
    moshi: Moshi,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : NiaNetworkDataSource {

    private val networkApi = trace("RetrofitNiaNetwork") {
        Retrofit.Builder()
            .baseUrl(NIA_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            /*.addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )*/
            .addConverterFactory(MoshiConverterFactory.create(moshi))

            .build()
            .create(RetrofitNiaNetworkApi::class.java)
    }

    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        networkApi.getTopics(ids = ids).data


    override suspend fun getUniversalis(ids: List<Int>): NetworkUniversalisResource =
        networkApi.getUniversalisResources(dateString = ids[0].toString())

    fun getUniversalisOld(ids: String?, req: String): Universalis =
        Universalis()

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        networkApi.getNewsResources(ids = ids).data

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getTopicChangeList(after = after)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getNewsResourcesChangeList(after = after)
}
