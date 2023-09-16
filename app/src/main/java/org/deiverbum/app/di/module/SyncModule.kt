package org.deiverbum.app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.data.repository.SyncRepositoryImpl
import org.deiverbum.app.domain.repository.SyncRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun syncRepository(syncRepositoryImpl: SyncRepositoryImpl): SyncRepository

}