package org.deiverbum.app.presentation.main

//import androidx.navigation.ui.AppBarConfiguration.Builder.build
//import androidx.navigation.ui.AppBarConfiguration.Builder.setOpenableLayout
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
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
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
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
import org.deiverbum.app.BuildConfig
import org.deiverbum.app.R
import org.deiverbum.app.databinding.ActivityMainBinding
import org.deiverbum.app.presentation.legal.AcceptanceFragmentDialog.Companion.display
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var strFechaHoy: String? = null
    //var navController: NavController? = null
    private lateinit var navController: NavController
    private var UPDATE_REQUEST_CODE = 0
    private lateinit var mAppBarConfiguration: AppBarConfiguration
    private var acceptTerms = false
    private var onDestinationChangedListener: NavController.OnDestinationChangedListener? = null
    private  lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private  lateinit var appUpdateManager: AppUpdateManager
    private val installStateUpdatedListener: InstallStateUpdatedListener =
        InstallStateUpdatedListener { installState: InstallState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                popupAlerter()
            }
        }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UPDATE_REQUEST_CODE = resources.getInteger(R.integer.app_version_code)
        strFechaHoy = Utils.getFecha()
        setPrivacy()
        showMain()
        /*onDestinationChangedListener =
            NavController.OnDestinationChangedListener { _: NavController?, destination: NavDestination, arguments: Bundle? ->
                val bundle = Bundle()
                val screenName = Objects.requireNonNull(destination.label).toString()
                bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            }
        navController.addOnDestinationChangedListener(onDestinationChangedListener!!)*/
    }

    private val listener = NavController.OnDestinationChangedListener { navController, destination, arguments ->
        val bundle = Bundle()
        val screenName = Objects.requireNonNull(destination.label).toString()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        // react on change
        // you can check destination.id or destination.label and act based on that
    }


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
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.subtitle = strFechaHoy
        setSupportActionBar(toolbar)
        val drawerLayout = binding.drawerLayout
        val navigationView = binding.navView
        //val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
        /*navController = Objects.requireNonNull(navHostFragment)?.navController

        mAppBarConfiguration = AppBarConfiguration(navController!!.graph).openableLayout=drawer
            .setOpenableLayout(drawer)
            .build()*/
//        setupActionBarWithNavController(this, navController!!, mAppBarConfiguration!!)
//        setupWithNavController(navigationView, navController!!)

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
        //val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        acceptTerms = prefs.getBoolean(Constants.PREF_ACCEPT, false)
        val collectData = prefs.getBoolean(Constants.PREF_ANALYTICS, true)
        val collectCrash = prefs.getBoolean(Constants.PREF_CRASHLYTICS, true)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
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

    private fun checkAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
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
                        this,
                        UPDATE_REQUEST_CODE
                    )
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        unregisterListener()
        //onDestinationChangedListener?.let { navController.removeOnDestinationChangedListener(it) }
        navController.removeOnDestinationChangedListener(listener)
        super.onDestroy()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }


    override fun onResume() {
        super.onResume()
        if (acceptTerms) {
            checkNewAppVersionState()
            navController.addOnDestinationChangedListener(listener)

        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                //Log.d(TAG, "Update flow failed! Result code: $resultCode")
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
            findViewById(android.R.id.content),
            "Nueva versión descargada, por favor reinicia",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("REINICIAR") { view: View? -> appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        snackbar.show()
    }

    private fun unregisterListener() {
        appUpdateManager.unregisterListener(
            installStateUpdatedListener
        )
    }

}