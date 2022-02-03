package org.deiverbum.app.core.module;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
        public abstract class FirebaseModule {

        @Provides
        @Singleton
        static FirebaseFirestore provideFirebaseInstance(){
                return FirebaseFirestore.getInstance();
        }

        }

