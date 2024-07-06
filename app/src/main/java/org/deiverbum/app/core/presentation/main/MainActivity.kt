package org.deiverbum.app.core.presentation.main

//import org.deiverbum.app.core.presentation.today.TodayViewModel
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
import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
import org.deiverbum.app.BaseApplication
import org.deiverbum.app.R
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.presentation.sync.InitialSyncViewModel
import org.deiverbum.app.core.presentation.sync.SyncItemUiState
import org.deiverbum.app.core.presentation.sync.SyncViewModel
import org.deiverbum.app.core.presentation.today.TodayViewModel
import org.deiverbum.app.core.presentation.ui.ProvideAppBarAction
import org.deiverbum.app.core.presentation.ui.ProvideAppBarTitle
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
        val appContainer = (application as BaseApplication).container
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
        /*setContent {
            CupcakeTheme {

                }

                CupcakeApp(theDate= Utils.hoy.toInt())

        }*/
        /*
                setContent {
                    UnscrambleTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            val counter by mainViewModel.counter.collectAsStateWithLifecycle()

                            FlowExample(counter = counter,
                                onAdd = {
                                    mainViewModel.loadData(TodayRequest(20240303,3,false,false))
                                },
                                onRemove = {
                                    //mainViewModel.decrementCounter()
                                })
                        }
                    }
                }

        */
        /*setContent {
            UnscrambleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    GameScreen()
                }
            }
        }*/

        /**/
        /*
                setContent {
                    CupcakeTheme {
                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                        val scope = rememberCoroutineScope()
                        LiturgiaApp()
                    }
                    /*
                    CupcakeTheme {
                        val navController = rememberNavController()
                        val backStackEntry by navController.currentBackStackEntryAsState()

                        /*var currentScreen = CupcakeScreenn.valueOf(
                            backStackEntry?.destination?.route ?: CupcakeScreenn.Start.name
                        )*/
                        var canNavigateBack = navController.previousBackStackEntry != null
                        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                        val scope = rememberCoroutineScope()

                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            drawerContent = {
                                ModalDrawerSheet {
                                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                                    Divider()
                                    NavigationDrawerItem(
                                        label = { Text(text = "Drawer Item") },
                                        selected = false,
                                        onClick = { /*TODO*/ }
                                    )
                                    // ...other drawer items
                                }
                            }
                        ) {

                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                var appBarState by remember {
                                    mutableStateOf(AppBarState())
                                }

                                Scaffold(

                                    floatingActionButton = {
                                        ExtendedFloatingActionButton(
                                            text = { Text("Show drawer") },
                                            icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                                            onClick = {
                                                scope.launch {
                                                    drawerState.apply {
                                                        if (isClosed) open() else close()
                                                    }
                                                }
                                            }
                                        )
                                    },
                                    topBar = {
                                        MyTopAppBar(navController,
                                            canNavigateBack = navController.previousBackStackEntry != null
                                        )
                                        ProvideAppBarAction {
                                            Button(onClick = { /*TODO*/ }) {
                                                Text(text = "action1")

                                            }

                                        }

                                        /*                            TopAppBar(

                                                                        title = {
                                                                            Text(text = appBarState.title)
                                                                        },

                                                                        navigationIcon =

                                                                        {
                                                                            if(canNavigateBack){
                                                                            IconButton(onClick = { /* do something */ }) {
                                                                                Icon(
                                                                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                                                    contentDescription = "Localized description"
                                                                                )
                                                                            }
                                                                        }},

                                                                        actions = {
                                                                            appBarState.actions?.invoke(this)
                                                                        }
                                                                    )*/
                                    }
                                ) { values ->
                                    NavHost(
                                        navController = navController,
                                        startDestination = CupcakeScreen.Start.name,
                                        modifier = Modifier.padding(
                                            values
                                        )
                                    ) {
                                        composable(
                                            "example/{ui}/{theDate}",
                                            arguments = listOf(navArgument("ui") {
                                                type = NavType.StringType
                                            }, navArgument("theDate") { type = NavType.IntType })
                                        ) { backStackEntry ->
                                            // Creates a ViewModel from the current BackStackEntry
                                            // Available in the androidx.hilt:hilt-navigation-compose artifact
                                            val viewModel = hiltViewModel<TodayViewModel>()
                                            val idUI = backStackEntry.arguments?.getString("ui")
                                            val theDate = backStackEntry.arguments?.getInt("theDate")
                                            ProvideAppBarAction {
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        drawerState.apply {
                                                            if (isClosed) open() else close()
                                                        }
                                                    }
                                                }) {
                                                    Icon(
                                                        Icons.Filled.CalendarMonth,
                                                        contentDescription = "Calendario"
                                                    )
                                                }
                                            }


                                            if (idUI != null && theDate != null) {
                                                ProvideAppBarTitle { Text(idUI.toString()) }
                                                MyScreen(viewModel, theDate)

                                            }
                                            theDate?.let {

                                                //CarScreen(carId = it.toInt())
                                            }

                                        }

                                        composable("screen_a") {

                                            ScreenA(
                                                /* onComposing = {
                                                     appBarState = it
                                                 },*/
                                                canNavigateBack = true,
                                                navController = navController
                                            )
                                            ProvideAppBarTitle { Text("Lorem ipsum*") }

                                        }
                                        composable("screen_c") {
                                            ScreenB(
                                                /*onComposing = {
                                                    appBarState = it
                                                },*/
                                                navController = navController
                                            )
                                            ProvideAppBarTitle { Text("Lorem ipsum") }

                                        }
                                        composable(route = CupcakeScreen.Start.name) {
                                            var title: String = ""
                                            ProvideAppBarAction {
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        drawerState.apply {
                                                            if (isClosed) open() else close()
                                                        }
                                                    }
                                                }) {
                                                    Icon(
                                                        Icons.Filled.Menu,
                                                        contentDescription = "Menú principal"
                                                    )
                                                }
                                            }
                                            MainScreen(
                                                onNextButtonClicked = {
                                                    val fecha = (20240301..20240321).random()
                                                    //title= LiturgyHelper.liturgyByType(it)!!
                                                    //ProvideAppBarTitle{ Text("lllllllll") }

                                                    //viewModel.loadData(TodayRequest(fecha, it, false, false))
        //currentScreen="LLLL"
                                                    //viewModel.loadData(TodayRequest(20240325, 3, false, false))
        //viewModel.uiState.value
                                                    //Log.d("dfg",viewModel.uiState.value.toString())
                                                    val args = Bundle().apply {
                                                        putString("emailId", "emailId")
                                                        putString("password", "password")
                                                    }
                                                    navController.navigate("example/${it}/${fecha}")

                                                    //navController.navigate("${CupcakeScreen.Universalis.name}/${it}")
                                                }
                                            )
                                        }
                                        /*
                                        composable(route = CupcakeScreen.Universalis.name) {

                                            TestScreen(
                                                viewModel,
                                                quantityOptions = DataSource.quantityOptions,
                                                onNextButtonClicked = {
                                                    val fecha = (20240301..20240321).random()
                                                    viewModel.loadData(TodayRequest(fecha, it, false, false))
                                                },
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(dimensionResource(R.dimen.padding_medium)),

                                                )
                                            //ProvideAppBarTitle{ Text(title) }

                                        }
                                        */
                                        composable(route = "universalis/{id}") { backStackEntry ->
                                            ProvideAppBarAction {
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        drawerState.apply {
                                                            if (isClosed) open() else close()
                                                        }
                                                    }
                                                }) {
                                                    Icon(
                                                        Icons.Filled.CalendarMonth,
                                                        contentDescription = "Calendario"
                                                    )
                                                }
                                            }

                                            TestScreen(
                                                backStackEntry,
                                                //viewModel,
                                                onNextButtonClicked = {
                                                    val fecha = (20240301..20240321).random()
                                                    //viewModel.loadData(TodayRequest(fecha, it, false, false))
                                                },
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(dimensionResource(R.dimen.padding_medium)),

                                                )
                                            //ProvideAppBarTitle{ Text(title) }

                                        }
                                        /*
                                                                    composable(route = "laudes") {

                                                                        TestScreen(
                                                                            viewModel,
                                                                            quantityOptions = DataSource.quantityOptions,
                                                                            onNextButtonClicked = {
                                                                                val fecha = (20240301..20240321).random()
                                                                                viewModel.loadData(TodayRequest(fecha, it, false, false))
                                                                            },
                                                                            modifier = Modifier
                                                                                .fillMaxSize()
                                                                                .padding(dimensionResource(R.dimen.padding_medium)),

                                                                            )
                                                                        ProvideAppBarTitle{ Text("Laudes") }

                                                                    }
                                        */
                                        composable("profile/{userId}") { backStackEntry ->
                                            Profile(
                                                navController,
                                                backStackEntry.arguments?.getString("userId")
                                            )
                                        }
        //buildGraph()

                                    }
                                }
                            }
                        }
                    }
                    */
                }
        */

        /*setContent { // In here, we can call composables!
            MaterialTheme {
                MainScreen(initialSyncViewModel)
            }
        }*/
        //mUpdateCode = resources.getInteger(R.integer.app_version_code)
        //setPrivacy()
        //showMain()
        //inAppUpdate = InAppUpdate(this)
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

    fun NavGraphBuilder.buildGraph() {
        composable(route = "start1") {
            ProvideAppBarTitle { Text("1") }
            ProvideAppBarAction {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action1")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action2")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action3")
                }
            }
        }
        composable(route = "start2") {
            ProvideAppBarTitle { Text("2") }
            ProvideAppBarAction {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action1")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action2")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "action3")
                }
            }
        }
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

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.start),
    Flavor(title = R.string.lbl_tercia),
    Pickup(title = R.string.lbl_sexta),
    Universalis(title = R.string.universalis),
    Nona(title = R.string.lbl_nona)
}