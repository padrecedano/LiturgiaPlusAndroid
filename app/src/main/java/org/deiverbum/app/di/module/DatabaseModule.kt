package org.deiverbum.app.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "dbName"
/*
    @Provides
    fun provideMyRoomDatabase(@ApplicationContext context: Context): MyRoomDatabase {
        return Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun providePrayDao(myRoomDatabase: MyRoomDatabase): PrayDao {
        return myRoomDatabase.prayDao()
    }
 */
}