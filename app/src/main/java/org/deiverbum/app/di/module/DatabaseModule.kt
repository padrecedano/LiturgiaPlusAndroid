package org.deiverbum.app.di.module

import android.app.Application
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.data.database.AppDatabase
import org.deiverbum.app.data.database.dao.TodayDao
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application?): AppDatabase {
        return databaseBuilder(
            application!!, AppDatabase::class.java,
            "LiturgiaPlusDB"
        )
            .createFromAsset("database/liturgia_202301000.db")
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideTodayDao(appDB: AppDatabase): TodayDao {
        return appDB.todayDao()
    }
}