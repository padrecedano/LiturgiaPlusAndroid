package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.core.data.repository.BibleRepository
import org.deiverbum.app.core.data.repository.BibleRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class BibleModule {

    @Binds
    abstract fun bibleRepository(bibleRepositoryImpl: BibleRepositoryImpl): BibleRepository

}