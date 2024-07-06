package org.deiverbum.app.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ReviewDispatchersModule {
    /*
    @Provides
    @Dispatcher(NiaDispatchers.IO)
    fun providesIODispatcherReview(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(NiaDispatchers.Default)
    fun providesDefaultDispatcherReview(): CoroutineDispatcher = Dispatchers.Default
*/

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher
