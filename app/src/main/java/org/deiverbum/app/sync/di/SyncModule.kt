package org.deiverbum.app.sync.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.data.util.SyncManager
import org.deiverbum.app.sync.status.StubSyncSubscriber
import org.deiverbum.app.sync.status.SyncSubscriber
import org.deiverbum.app.sync.status.WorkManagerSyncManager

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {
    @Binds
    internal abstract fun bindsSyncStatusMonitor(
        syncStatusMonitor: WorkManagerSyncManager,
    ): SyncManager

    @Binds
    internal abstract fun bindsSyncSubscriber(
        syncSubscriber: StubSyncSubscriber,
    ): SyncSubscriber
}
