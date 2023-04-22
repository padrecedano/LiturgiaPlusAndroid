package org.deiverbum.app.core.module

import android.app.Application
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.data.db.AppDatabase
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @JvmStatic
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

    @JvmStatic
    @Provides
    @Singleton
    fun provideTodayDao(appDB: AppDatabase): org.deiverbum.app.data.db.dao.TodayDao {
        return appDB.todayDao()
    }

    @Provides
    @Singleton
    fun providePrayDao(appDB: AppDatabase): org.deiverbum.app.data.database.dao.TodayDao {
        return appDB.prayDao()
    }
}