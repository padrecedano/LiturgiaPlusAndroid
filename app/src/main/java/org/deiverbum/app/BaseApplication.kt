package org.deiverbum.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by A. Cedano on 11,November,2021
 */
@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {
    /*
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
*/

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    /*
        override fun getWorkManagerConfiguration() =
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                //.setMinimumLoggingLevel(android.util.Log.VERBOSE)
                .build()
    */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            //.setMinimumLoggingLevel(android.util.Log.VERBOSE)
            .build()
}
