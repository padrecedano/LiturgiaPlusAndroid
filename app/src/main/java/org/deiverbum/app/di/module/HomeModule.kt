package org.deiverbum.app.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.deiverbum.app.presentation.home.adapter.HomeAdapter

@Module
@InstallIn(FragmentComponent::class)
object HomeModule {

    @Provides
    fun provideHomeAdapter(): HomeAdapter {
        return HomeAdapter(emptyList(),1)
    }
}