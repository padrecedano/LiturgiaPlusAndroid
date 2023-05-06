package org.deiverbum.app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.data.repository.*
import org.deiverbum.app.domain.repository.*

@Module
@InstallIn(ViewModelComponent::class)
abstract class TodayModule {

    @Binds
    abstract fun todayRepository(todayRepositoryImpl: TodayRepositoryImpl): TodayRepository

    @Binds
    abstract fun fileRepository(todayRepositoryImpl: FileRepositoryImpl):
            FileRepository

    @Binds
    abstract fun syncRepository(syncRepositoryImpl: SyncRepositoryImpl):
            SyncRepository
}