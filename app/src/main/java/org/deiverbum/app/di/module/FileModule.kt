package org.deiverbum.app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.data.repository.FileRepositoryImpl
import org.deiverbum.app.domain.repository.FileRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class FileModule {

    @Binds
    abstract fun fileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

}