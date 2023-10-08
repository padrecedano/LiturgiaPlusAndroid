package org.deiverbum.app.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.deiverbum.app.core.presentation.home.adapter.HomeAdapter

@Module
@InstallIn(FragmentComponent::class)
object HomeModule {

    @Provides
    fun provideHomeAdapter(): HomeAdapter {
        return HomeAdapter(emptyList(),1)
    }
}