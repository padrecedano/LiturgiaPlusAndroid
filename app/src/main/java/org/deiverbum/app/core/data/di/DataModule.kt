package org.deiverbum.app.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.data.repository.OfflineFirstUniversalisRepository
import org.deiverbum.app.core.data.repository.OfflineFirstUserDataRepository
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.util.ConnectivityManagerNetworkMonitor
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneBroadcastMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    //Universalis con la fecha de hoy
    @Binds
    fun bindsUniversalisResourceRepository(
        newsRepository: OfflineFirstUniversalisRepository,
    ): UniversalisRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor

}