package org.deiverbum.app.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.network.NiaNetworkDataSource
import org.deiverbum.app.core.network.retrofit.RetrofitNiaNetwork

@Module
@InstallIn(SingletonComponent::class)
internal interface FlavoredNetworkModule {

    @Binds
    fun binds(impl: RetrofitNiaNetwork): NiaNetworkDataSource
}