package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.core.data.repository.InitialSyncRepository
import org.deiverbum.app.core.data.repository.InitialSyncRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class InitialSyncModule {

    @Binds
    abstract fun initialSyncRepository(initialSyncRepositoryImpl: InitialSyncRepositoryImpl): InitialSyncRepository

}