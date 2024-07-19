package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.data.repository.CompositeUserUniversalisResourceRepository
import org.deiverbum.app.core.data.repository.UserUniversalisResourceRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface UserUniversalisResourceRepositoryModule {
    @Binds
    fun bindsUserUniversalisResourceRepository(
        userDataRepository: CompositeUserUniversalisResourceRepository,
    ): UserUniversalisResourceRepository
}
