package org.deiverbum.app.core.notifications

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationsModule {
    @Binds
    abstract fun bindNotifier(
        notifier: NoOpNotifier,
    ): Notifier
}
