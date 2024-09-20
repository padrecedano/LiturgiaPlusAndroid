package org.deiverbum.app.core.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UserNewsResourceRepositoryModule {
    /*@Binds
    fun bindsUserNewsResourceRepository(
        userDataRepository: CompositeUserNewsResourceRepository,
    ): UserNewsResourceRepository*/
}
