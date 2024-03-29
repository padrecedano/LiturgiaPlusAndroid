package org.deiverbum.app.core.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deiverbum.app.BuildConfig
import org.deiverbum.app.core.model.data.Alteri
import org.deiverbum.app.core.model.data.Commentarii
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.LiturgiaTypus
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.network.api.TodayApi
import org.deiverbum.app.util.Configuration
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/*
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideTodayApi(retrofit: Retrofit): TodayApi = retrofit.create(TodayApi::class.java)

}
*/

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    private const val BASE_URL = "https://restcountries.eu/rest/v2/"

    private val moshii = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(LiturgiaTypus::class.java, "typus")
                .withSubtype(LHMixtus::class.java, "mixtus")
                .withSubtype(LHOfficium::class.java, "officium")
                .withSubtype(LHLaudes::class.java, "laudes")
                .withSubtype(LHIntermedia::class.java, "intermedia")
                .withSubtype(LHIntermedia::class.java, "tertiam")
                .withSubtype(LHIntermedia::class.java, "sextam")
                .withSubtype(LHIntermedia::class.java, "nonam")
                .withSubtype(LHVesperas::class.java, "vesperas")
                .withSubtype(LHCompletorium::class.java, "completorium")
                .withSubtype(Missae::class.java, "lectionum")
                .withSubtype(Missae::class.java, "homiliae")
                .withSubtype(Commentarii::class.java, "commentarii")
                .withSubtype(Alteri.Sancti::class.java, "sanctii")


            //.withDefaultValue(LiturgiaTypus.Unknown)

        )
        .add(KotlinJsonAdapterFactory())
        .build()


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            /*.addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )*/
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))

        .baseUrl(Configuration.URL_API)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideTodayApi(retrofit: Retrofit): TodayApi = retrofit.create(TodayApi::class.java)

    /*@Singleton
    @Provides
    fun providesRepository(apiService: TodayApi) = Repository(apiService)*/
}