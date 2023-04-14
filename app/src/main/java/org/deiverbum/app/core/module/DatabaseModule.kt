package org.deiverbum.app.core.module;

import android.app.Application;

import androidx.room.Room;

import org.deiverbum.app.data.db.AppDatabase;
import org.deiverbum.app.data.db.dao.TodayDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Module
@InstallIn(SingletonComponent.class)

public class DatabaseModule {

    @Provides
    @Singleton
    public static AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class,
                        "LiturgiaPlusDB")
                .createFromAsset("database/liturgia_202301000.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static TodayDao provideTodayDao(AppDatabase appDB) {
        return appDB.todayDao();
    }
}

