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
    /*@Provides
    fun providesTopicsDao(
        database: NiaDatabase,
    ): TopicDao = database.topicDao()*/
    /*
        @Provides
        fun providesTodaysDao(
            database: NiaDatabase,
        ): TodayDao = database.todayDao()
        */
    /*
        @Provides
        fun providesNewsResourceDao(
            database: NiaDatabase,
        ): NewsResourceDao = database.newsResourceDao()
    */
    /*
        @Provides
        fun providesUniversalisDao(
            database: NiaDatabase,
        ): UniversalisDao = database.universalisDao()
    */
    @Provides
    fun providesUniversalisDao(
        database: AppDatabase,
    ): UniversalisDao = database.universalisDaoo()
    /*@Provides
    fun providesTopicFtsDao(
        database: NiaDatabase,
    ): TopicFtsDao = database.topicFtsDao()

    @Provides
    fun providesNewsResourceFtsDao(
        database: NiaDatabase,
    ): NewsResourceFtsDao = database.newsResourceFtsDao()

    @Provides
    fun providesRecentSearchQueryDao(
        database: NiaDatabase,
    ): RecentSearchQueryDao = database.recentSearchQueryDao()
    */
}