package org.deiverbum.app.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.deiverbum.app.data.repository.BibleRepositoryImpl
import org.deiverbum.app.domain.repository.BibleRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class BibleModule {

    @Binds
    abstract fun bibleRepository(bibleRepositoryImpl: BibleRepositoryImpl): BibleRepository

}