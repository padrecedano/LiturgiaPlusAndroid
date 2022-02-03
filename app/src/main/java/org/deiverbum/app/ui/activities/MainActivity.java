package org.deiverbum.app.ui.activities;

import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;
import static org.deiverbum.app.utils.Constants.PREF_ANALYTICS;
import static org.deiverbum.app.utils.Constants.PREF_CRASHLYTICS;
import static org.deiverbum.app.utils.Constants.VERSION_CODE;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.ActivityMainBinding;
import org.deiverbum.app.ui.fragments.AcceptanceFragmentDialog;
import org.deiverbum.app.utils.Utils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity  {
    String strFechaHoy;
    private static final String TAG = "MainActivity";
    private static final int UPDATE_REQUEST_CODE = VERSION_CODE;
    //202001030;
    private AppBarConfiguration mAppBarConfiguration;
    private boolean acceptTerms;


    private AppUpdateManager appUpdateManager;
    private final InstallStateUpdatedListener installStateUpdatedListener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popupAlerter();
        }
    };





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strFechaHoy = Utils.getFecha();
        //showNoticeDialog();


        setPrivacy();
        //navController.navigate(R.id.nav_initial);

        showMain();

            //navController.navigate(R.id.nav_initial);

            //navController.navigate(R.id.nav_initial);
            //showMain();


    }

    private void showMain() {
        ActivityMainBinding binding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(strFechaHoy);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (!acceptTerms) {
            openDialog();
            //navController.navigate(R.id.nav_initial);
        }else{
            checkAppUpdate();
            navController.navigate(R.id.nav_home);

        }


    }

    private void setPrivacy() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        acceptTerms = prefs.getBoolean(PREF_ACCEPT, false);

        boolean collectData = prefs.getBoolean(PREF_ANALYTICS, true);
        boolean collectCrash = prefs.getBoolean(PREF_CRASHLYTICS, true);
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(collectData);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(collectCrash);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void openDialog() {
        AcceptanceFragmentDialog.display(getSupportFragmentManager());
    }

    private void checkAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(installStateUpdatedListener);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            UPDATE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterListener();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.d(TAG, "Update flow failed! Result code: " + resultCode);
            }
        }
    }

    /*
      You should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupAlerter();
                    }
                });
    }

    private void popupAlerter() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Nueva versión descargada, por favor reinicia",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("REINICIAR", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.colorAccent));
        snackbar.show();

    }

    private void unregisterListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

}