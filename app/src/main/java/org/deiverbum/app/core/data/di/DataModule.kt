package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.data.repository.DefaultRecentSearchRepository
import org.deiverbum.app.core.data.repository.DefaultSearchContentsRepository
import org.deiverbum.app.core.data.repository.NewsRepository
import org.deiverbum.app.core.data.repository.OfflineFirstNewsRepository
import org.deiverbum.app.core.data.repository.OfflineFirstTodayRepository
import org.deiverbum.app.core.data.repository.OfflineFirstTopicsRepository
import org.deiverbum.app.core.data.repository.OfflineFirstUniversalisRepository
import org.deiverbum.app.core.data.repository.OfflineFirstUniversalisRepositoryy
import org.deiverbum.app.core.data.repository.OfflineFirstUserDataRepository
import org.deiverbum.app.core.data.repository.RecentSearchRepository
import org.deiverbum.app.core.data.repository.SearchContentsRepository
import org.deiverbum.app.core.data.repository.TodayRepository
import org.deiverbum.app.core.data.repository.TodayRepositoryImpl
import org.deiverbum.app.core.data.repository.TodaysRepository
import org.deiverbum.app.core.data.repository.TopicsRepository
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UniversalisRepositoryy
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.util.ConnectivityManagerNetworkMonitor
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneBroadcastMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor

@Module
@InstallIn(SingletonComponent::class)
//@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindsTodayRepository(
        todayRepository: TodayRepositoryImpl,
    ): TodayRepository

    @Binds
    fun bindsTopicRepository(
        topicsRepository: OfflineFirstTopicsRepository,
    ): TopicsRepository

    @Binds
    fun bindsUniversalisRepositoryy(
        topicsRepository: OfflineFirstUniversalisRepositoryy,
    ): UniversalisRepository

    @Binds
    fun bindsTodayNewRepository(
        topicsRepository: OfflineFirstTodayRepository,
    ): TodaysRepository


    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsNewsResourceRepository(
        newsRepository: OfflineFirstNewsRepository,
    ): NewsRepository

    @Binds
    fun bindsUniversalisResourceRepository(
        newsRepository: OfflineFirstUniversalisRepository,
    ): UniversalisRepositoryy

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: DefaultRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentsRepository(
        searchContentsRepository: DefaultSearchContentsRepository,
    ): SearchContentsRepository

    /*

    @Binds
    fun bindsUserUniversalisResourceRepository(
        userUniversalisResourceRepository: UserUniversalisResourceRepository,
    ): UserUniversalisResourceRepository
    */

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor


}