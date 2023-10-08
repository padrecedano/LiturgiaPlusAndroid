package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.core.data.repository.SyncRepository
import org.deiverbum.app.core.data.repository.SyncRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun syncRepository(syncRepositoryImpl: SyncRepositoryImpl): SyncRepository

}