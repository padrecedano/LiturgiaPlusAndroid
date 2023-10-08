package org.deiverbum.app.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.network.api.TodayApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideTodayApi(retrofit: Retrofit): TodayApi = retrofit.create(TodayApi::class.java)

}