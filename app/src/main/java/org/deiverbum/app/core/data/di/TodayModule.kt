package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.core.data.repository.TodayRepository
import org.deiverbum.app.core.data.repository.TodayRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class TodayModule {

    @Binds
    abstract fun TodayRepository(todayRepositoryImpl: TodayRepositoryImpl): TodayRepository

}