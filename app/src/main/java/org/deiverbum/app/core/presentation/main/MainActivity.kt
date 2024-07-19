package org.deiverbum.app.core.presentation.main

import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceManager
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.presentation.sync.InitialSyncViewModel
import org.deiverbum.app.core.presentation.sync.SyncItemUiState
import org.deiverbum.app.core.presentation.sync.SyncViewModel
import org.deiverbum.app.core.presentation.today.TodayViewModel
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.PREF_ACCEPT
import org.deiverbum.app.util.Constants.PREF_INITIAL_SYNC
import org.deiverbum.app.util.Utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Actividad principal y punto de entrada de la aplicación.
 *
 * Migrada a Kotlin en la versión 2024.1
 *
 * @author A. Cedano
 */
//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var strFechaHoy: String? = null
    private lateinit var navController: NavController
    private var mUpdateCode = 0
    private var pastYearsCleaned: Boolean = false

    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var acceptTerms: Boolean = false
    private val syncViewModel: SyncViewModel by viewModels()
    private val initialSyncViewModel: InitialSyncViewModel by viewModels()
    //private val viewModel: TodayViewModel by viewModels()

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val appUpdateManager by lazy {
        AppUpdateManagerFactory.create(this)
    }

    /*
    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
*/
    private val mFirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }
    private var pressedTime: Long = 0


    private val installStateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { installState: InstallState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupAlerter()
            }
        }

    private val navControllerListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            val bundle = Bundle()
            val screenName = destination.label.toString()
            bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        }

    @ExperimentalMaterial3WindowSizeClassApi
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val mainViewModel: TodayViewModel by viewModels()
        strFechaHoy = Utils.fecha
        /*
                setContent {
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                    JetnewsApp(appContainer, widthSizeClass)
                }
        */
        setContent {
            //MainCompose()
        }

        checkForInitialSync()
        checkForDataToClean()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.graph.startDestinationId == navController.currentDestination?.id) {
                    if (pressedTime + 2000 > System.currentTimeMillis()) {
                        finish()
                    } else {
                        /*val snack = Snackbar.make(
                            mainBinding.root,
                            "Presiona de nuevo el botón de retroceso para cerrar la aplicación.",
                            Snackbar.LENGTH_LONG
                        )
                        snack.show()*/
                    }
                    pressedTime = System.currentTimeMillis()
                } else {
                    navController.navigateUp()
                }
            }
        })

    }


    /**
     * Este método inicializa Firebase AppCheck.
     *
     * @since 2024.1
     */
    private fun appCheck() {
        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )
        firebaseAppCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
        )
    }

    private fun showMain() {
        //setContentView(mainBinding.root)
        //val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        //toolbar.subtitle = strFechaHoy
        //setSupportActionBar(toolbar)
        //val drawerLayout = mainBinding.drawerLayout
        //val navHostFragment =
        //    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        //navController = navHostFragment.navController
        /*        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
                toolbar.setupWithNavController(navController, appBarConfiguration)
                findViewById<NavigationView>(R.id.nav_view)
                    .setupWithNavController(navController)
        */
        if (!acceptTerms) {
            openDialog()
        } else {
            appCheck()
            checkAppUpdate()
        }
    }

    private fun setPrivacy() {
        acceptTerms = prefs.getBoolean(PREF_ACCEPT, false)
        val collectData = prefs.getBoolean(Constants.PREF_ANALYTICS, true)
        val collectCrash = prefs.getBoolean(Constants.PREF_CRASHLYTICS, true)
        if (!BuildConfig.DEBUG) {
            mFirebaseAnalytics.setAnalyticsCollectionEnabled(collectData)
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(collectCrash)
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    /*
        override fun onSupportNavigateUp(): Boolean {
            val navController = findNavController(this, R.id.nav_host_fragment_activity_main)
            return (navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp())
        }
    */
    private fun openDialog() {
        //display(supportFragmentManager)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            when (val resultCode = result.resultCode) {
                RESULT_OK -> {
                    val bundle = Bundle()
                    bundle.putInt("result_code", resultCode)
                    mFirebaseAnalytics.logEvent("app_udate", bundle)
                    popupAlerter()
                }

                RESULT_CANCELED -> {
                    val bundle = Bundle()
                    bundle.putInt("result_code", resultCode)
                    mFirebaseAnalytics.logEvent("cancel_update", bundle)

                }

                else -> {
                    val bundle = Bundle()
                    bundle.putInt("result_code", resultCode)
                    mFirebaseAnalytics.logEvent("update_fail", bundle)
                }
            }
        }


    private fun checkAppUpdate() {

        appUpdateManager.registerListener(installStateUpdatedListener)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
                        //AppUpdateType.FLEXIBLE
                        //starter,
                        //mUpdateCode
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkNewAppVersionState() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupAlerter()
                }
            }
    }

    private fun popupAlerter() {
        /*val snackbar = Snackbar.make(
            mainBinding.root,
            "Nueva versión descargada, por favor reinicia",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("REINICIAR") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(
            ContextCompat.getColor(this, R.color.colorInAppUpdate)
        )
        snackbar.show()*/
    }


    /**
     * Este método verifica si se hizo una sincronización inicial.
     *
     * @since 2024.1
     */
    private fun checkForInitialSync() {
        val hasInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
        val acceptedTerms = prefs.getBoolean(PREF_ACCEPT, false)
        if (!hasInitialSync && acceptedTerms) {
            val syncRequest =
                SyncRequest(hasInitialSync = false, getCurrentYear() - 1, isWorkScheduled = false)
            initialSyncViewModel.launchSync(syncRequest)
            fetchDataInitial()
        }
    }


    /**
     * Este método verifica si hay datos antiguos para limpiar.
     *
     * Se lanzará únicamente a partir del día 29 de los meses de
     * marzo, junio, septiembre y diciembre. Su propósito es obtener
     * el valor de la entrada [Constants.PREF_PAST_YEARS_CLEANED] en SharedPreferences,
     * compararlo con el valor del _año actual menos uno_ (`currentYear - 1`).
     * Si es menor o igual, significa que habrá que limpiar datos de años anteriores,
     * lo cual se hará llamando a [SyncViewModel.launchSync].
     *
     * @since 2024.1
     */
    private fun checkForDataToClean() {
        val dayNumber = Utils.getDay(Utils.hoy).toInt()
        val monthNumber = Utils.getMonth(
            Utils.hoy
        ).toInt()
        val hasInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
        val acceptedTerms = prefs.getBoolean(PREF_ACCEPT, false)
        if (!hasInitialSync && acceptedTerms) {
            val syncRequest =
                SyncRequest(hasInitialSync = false, getCurrentYear() - 1, isWorkScheduled = false)
            initialSyncViewModel.launchSync(syncRequest)
            fetchDataInitial()
        }
        if (hasInitialSync && dayNumber >= 29 && (monthNumber == 3 || monthNumber == 6 || monthNumber == 9 || monthNumber == 12)) {
            pastYearsCleaned = prefs.getBoolean(Constants.PREF_PAST_YEARS_CLEANED, false)
            val pastYear = getCurrentYear() - 1
            //if (!pastYearsCleaned) {
            syncViewModel.launchSync(
                SyncRequest(
                    hasInitialSync = true,
                    pastYear,
                    isWorkScheduled = true
                )
            )
            fetchData()
            //}
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoaded(state.itemState)
                        else -> showNoData()
                    }
                }
            }
        }
    }

    //TODO descomentar o reparar
    private fun onLoaded(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            if (syncResponse.syncStatus.lastYearCleaned && !pastYearsCleaned) {
                prefs.edit().putBoolean(Constants.PREF_PAST_YEARS_CLEANED, true).apply()
            }
        }
    }

    private fun fetchDataInitial() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                initialSyncViewModel.uiState.collect { state ->
                    when (state) {
                        is InitialSyncViewModel.SyncUiState.Loaded -> onLoadedInitial(state.itemState)
                        else -> showNoData()
                    }
                }
            }
        }
    }

    private fun onLoadedInitial(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            if (syncResponse.allToday.isNotEmpty()) {
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
            if (syncResponse.syncStatus.lastYearCleaned) {
                //prefs.edit().putBoolean(Constants.PREF_LAST_YEAR_CLEANED, true).apply()
            }
        }
    }

    private fun getCurrentYear(): Int {
        val systemTime = System.currentTimeMillis()
        val sdfY = SimpleDateFormat("yyyy", Locale("es", "ES"))
        val theDate = Date(systemTime)
        return sdfY.format(theDate).toInt()
    }

    private fun showNoData() {
        //val msgNoData = applicationContext.resources?.getString(R.string.err_no_data)
        //Toast.makeText(applicationContext, msgNoData, Toast.LENGTH_SHORT).show()

    }

    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                ColorUtils.isNightMode=false} // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {ColorUtils.isNightMode=true} // Night mode is active, we're using dark theme
        }

    }*/


    private fun unregisterListener() {
        appUpdateManager.unregisterListener(
            installStateUpdatedListener
        )
        navController.removeOnDestinationChangedListener(navControllerListener)
    }

    override fun onDestroy() {
        unregisterListener()
        super.onDestroy()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navControllerListener)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (acceptTerms) {
            checkNewAppVersionState()
            navController.addOnDestinationChangedListener(navControllerListener)
        }
    }
}