package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.core.data.repository.FileRepository
import org.deiverbum.app.core.data.repository.FileRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class FileModule {

    @Binds
    abstract fun fileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository


}