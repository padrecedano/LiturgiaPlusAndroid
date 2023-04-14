package org.deiverbum.app.core.module

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideFirebaseInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}