package org.deiverbum.app;


import android.app.Application;

import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by A. Cedano on 11,November,2021
 */
@HiltAndroidApp
public class BaseApplication extends Application implements Configuration.Provider {

    @Inject
    HiltWorkerFactory workerFactory;

    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build();
    }


}
