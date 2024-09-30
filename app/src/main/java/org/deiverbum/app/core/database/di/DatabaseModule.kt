package org.deiverbum.app.core.database.di

import android.app.Application
import androidx.room.Room.databaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.database.AppDatabase
import org.deiverbum.app.util.Configuration.DATABASE_PATH
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
            .createFromAsset(DATABASE_PATH)
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            //.addTypeConverter(roomConverter)

            .build()
    }
    /*
        @Provides
        @Singleton
        fun provideTodayDao(appDB: AppDatabase): TodayDao {
            return appDB.todayDao()
        }
    */
    /*@Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }*/

}