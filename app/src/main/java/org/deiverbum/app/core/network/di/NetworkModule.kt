package org.deiverbum.app.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deiverbum.app.BuildConfig
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()


}


/*
@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
    /*
        @Provides
        @Singleton
        fun provideRetrofit(
            gsonConverterFactory: GsonConverterFactory?,
            rxJava3CallAdapterFactory: RxJava3CallAdapterFactory?,
            okHttpClient: OkHttpClient?
        ): Retrofit {
            return Retrofit.Builder().baseUrl(Configuration.URL_API)
                .addConverterFactory(gsonConverterFactory!!)
                .addCallAdapterFactory(rxJava3CallAdapterFactory!!)
                .client(okHttpClient!!)
                .build()
        }
    */

    private val moshi = Moshi.Builder()
        //.addLast(KotlinJsonAdapterFactory())
        .build()

/*
    @Singleton
    @Provides
    fun provideRetrofitt(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        //.addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(MoshiConverterFactory.create().asLenient())
        //.addConverterFactory(MoshiConverterFactory.create())
        //.addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(MoshiConverterFactory.create(moshi))

        .baseUrl(Configuration.URL_API)
        .client(okHttpClient)
        .build()

*/
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    /*
        @Provides
        @Singleton
        fun provideHttpClient(application: Context): OkHttpClient {
            val dispatcher = Dispatcher()
            val logging = HttpLoggingInterceptor()
    //= new HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client =OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
            client.addInterceptor(ConnectivityIntecepter(application))
            val interceptor = Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            client.addInterceptor(interceptor)
            client.interceptors().add(logging)
            return client.build()
        }
    */
/*
    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }*/

    /*
        @Provides
        @Singleton
        fun provideGson(): Gson {
            return GsonBuilder().setLenient().create()
        }*/
    /*
        @Provides
        @Singleton
        fun providesGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }
    */
    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }




}*/