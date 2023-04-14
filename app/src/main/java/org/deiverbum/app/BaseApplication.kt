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
    @JvmField
    @Inject
    var workerFactory: HiltWorkerFactory? = null
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory!!)
            .build()
    }
}