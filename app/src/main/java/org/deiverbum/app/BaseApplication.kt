package org.deiverbum.app

/**
 * Created by A. Cedano on 11,November,2021
 */
/*@HiltAndroidApp
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

    companion object {
        const val JETNEWS_APP_URI = "https://developer.android.com/jetnews"
    }

    // AppContainer instance used by the rest of classes to obtain dependencies
    //lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        //container = AppContainerImpl(this)
    }

}*/
