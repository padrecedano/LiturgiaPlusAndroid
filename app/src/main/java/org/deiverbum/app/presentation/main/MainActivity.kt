package org.deiverbum.app.presentation.main

import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.BuildConfig
import org.deiverbum.app.R
import org.deiverbum.app.databinding.ActivityMainBinding
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.presentation.legal.AcceptanceFragmentDialog.Companion.display
import org.deiverbum.app.presentation.sync.SyncItemUiState
import org.deiverbum.app.presentation.sync.SyncViewModel
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.PREF_ACCEPT
import org.deiverbum.app.util.Utils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Actividad principal y punto de entrada de la aplicación.
 *
 * Migrada a Kotlin en la versión 2023.1.3
 *
 * @author A. Cedano
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var strFechaHoy: String? = null
    private lateinit var navController: NavController
    private var mUpdateCode = 0
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var acceptTerms:Boolean = false
    private val syncViewModel: SyncViewModel by viewModels()
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }
    private val appUpdateManager by lazy {
        AppUpdateManagerFactory.create(this)
    }
    private val mainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mFirebaseAnalytics by lazy{
        FirebaseAnalytics.getInstance(this)    }
    private var pressedTime: Long = 0
    private val installStateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { installState: InstallState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupAlerter()
            }
        }

    private val navControllerListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        val bundle = Bundle()
        val screenName = destination.label.toString()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUpdateCode = resources.getInteger(R.integer.app_version_code)
        strFechaHoy = Utils.getFecha()
        setPrivacy()
        showMain()
        checkForDataToClean()
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.graph.startDestinationId == navController.currentDestination?.id) {
                    if (pressedTime + 2000 > System.currentTimeMillis()) {
                        finish()
                    } else {
                        val snack = Snackbar.make(
                            mainBinding.root,
                            "Presiona de nuevo el botón de retroceso para cerrar la aplicación.",
                            Snackbar.LENGTH_LONG
                        )
                        snack.show()
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
     * @since 2023.1.3
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
        setContentView(mainBinding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.subtitle = strFechaHoy
        setSupportActionBar(toolbar)
        val drawerLayout = mainBinding.drawerLayout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        return (navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp())
    }

    private fun openDialog() {
        display(supportFragmentManager)
    }

    private val updateFlowResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                popupAlerter()
            }
        }

    private fun checkAppUpdate() {
        val starter =
            IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
                val request = IntentSenderRequest.Builder(intent)
                    .setFillInIntent(fillInIntent)
                    .setFlags(flagsValues, flagsMask)
                    .build()
                updateFlowResultLauncher.launch(request)
            }
        appUpdateManager.registerListener(installStateUpdatedListener)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        starter,
                        mUpdateCode
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
        val snackbar = Snackbar.make(
            mainBinding.root,
            "Nueva versión descargada, por favor reinicia",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("REINICIAR") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        snackbar.show()
    }

    /**
     * Este método verifica si hay datos antiguos para limpiar.
     *
     * Se lanzará únicamente a partir del día 29 de los meses de
     * marzo, junio, septiembre y diciembre. Su propósito es obtener
     * el valor de la entrada [Constants.PREF_LAST_YEAR_CLEANED] en SharedPreferences,
     * compararlo con el valor del _año actual menos uno_ (`currentYear - 1`).
     * Si es menor o igual, significa que habrá que limpiar datos de años anteriores,
     * lo cual se hará llamando a [SyncViewModel.launchSync].
     *
     * @since 2023.1.3
     */
    private fun checkForDataToClean() {
        val dayNumber = Utils.getDay(Utils.getHoy()).toInt()
        val monthNumber = Utils.getMonth(
            Utils.getHoy()).toInt()
        val hasInitialSync = prefs.getBoolean(Constants.PREF_INITIAL_SYNC, false)

        if (hasInitialSync && dayNumber >= 29 && (monthNumber == 3 || monthNumber == 6 || monthNumber == 9 || monthNumber == 12)) {
            val lastYearCleaned = prefs.getInt(Constants.PREF_LAST_YEAR_CLEANED, 0)
            val systemTime = System.currentTimeMillis()
            val sdfY = SimpleDateFormat("yyyy", Locale("es", "ES"))
            val theDate = Date(systemTime)
            val currentYear = sdfY.format(theDate).toInt()
            if (lastYearCleaned == 0 || lastYearCleaned <= currentYear - 1) {
                    syncViewModel.launchSync(SyncRequest(hasInitialSync=true,currentYear, isWorkScheduled=true))
                fetchData()
                }
            }
        }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoaded(state.itemState)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            if (syncResponse.syncStatus.lastYearCleaned!=0) {
                prefs.edit().putInt(Constants.PREF_LAST_YEAR_CLEANED, syncResponse.syncStatus.lastYearCleaned).apply()
            }
        }
    }

    private fun showLoading() {
    }

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