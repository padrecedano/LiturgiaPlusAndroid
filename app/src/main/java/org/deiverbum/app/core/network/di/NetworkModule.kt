package org.deiverbum.app.core.network.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.deiverbum.app.BuildConfig
import org.deiverbum.app.util.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
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
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())

        .baseUrl(Configuration.URL_API)
        .client(okHttpClient)
        .build()


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
    }

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




}