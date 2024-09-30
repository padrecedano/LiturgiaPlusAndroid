package org.deiverbum.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by A. Cedano on 11,November,2021
 */
@HiltAndroidApp
class BaseApplication : Application() {
    //@Inject
    //lateinit var imageLoader: dagger.Lazy<ImageLoader>

    /*
        @Inject
        lateinit var profileVerifierLogger: ProfileVerifierLogger
    */
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // Initialize Sync; the system responsible for keeping data in the app up to date.
        //Sync.initialize(context = this)
        //profileVerifierLogger()
    }

    //override fun newImageLoader(): ImageLoader = imageLoader.get()
}
