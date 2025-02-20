package org.deiverbum.app.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.database.AppDatabase
import org.deiverbum.app.core.database.dao.UniversalisDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesUniversalisDao(
        database: AppDatabase,
    ): UniversalisDao = database.universalisDao()
}