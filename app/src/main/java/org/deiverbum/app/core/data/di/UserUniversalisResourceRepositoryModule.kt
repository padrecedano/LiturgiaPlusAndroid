package org.deiverbum.app.core.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface UserUniversalisResourceRepositoryModule {
    /*@Binds
    fun bindsUserUniversalisResourceRepository(
        userDataRepository: CompositeUserUniversalisResourceRepository,
    ): UserUniversalisResourceRepository*/
}
