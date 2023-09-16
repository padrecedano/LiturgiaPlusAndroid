package org.deiverbum.app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.data.repository.TodayRepositoryImpl
import org.deiverbum.app.domain.repository.TodayRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class TodayModule {

    @Binds
    abstract fun TodayRepository(TodayRepositoryImpl: TodayRepositoryImpl): TodayRepository

}