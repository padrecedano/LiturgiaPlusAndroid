package org.deiverbum.app.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import org.deiverbum.app.data.networking.CoroutineDispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleForDelete {
/*
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("BuildConfig.BASE_PRAY_URL")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    */
    @Provides
    fun provideCoroutineDispatcher() = CoroutineDispatcherProvider()
}