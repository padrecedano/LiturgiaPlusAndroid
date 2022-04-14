package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.VOICE_INI;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.deiverbum.app.R;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentHomiliasBinding;
import org.deiverbum.app.utils.TextToSpeechCallback;
import org.deiverbum.app.utils.TtsManager;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;
import org.deiverbum.app.viewmodel.HomiliasViewModel;
import org.deiverbum.app.viewmodel.TodayViewModel;
import org.deiverbum.app.workers.ExampleWorker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class TodayFragment extends Fragment implements TextToSpeechCallback {
    private HomiliasViewModel mViewModel;
    private TodayViewModel todayViewModel;

    private FragmentHomiliasBinding binding;
    private ZoomTextView mTextView;
    private String mDate;
    private WorkManager mWorkManager;

    private ProgressBar progressBar;

    private SeekBar seekBar;
    private boolean isReading = false;
    private boolean isVoiceOn;
    private StringBuilder sbReader;
    private Menu audioMenu;
    private Menu mainMenu;

    public static ActionMode mActionMode;
    private TtsManager mTtsManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentHomiliasBinding.inflate(inflater, container, false);
        inflater.inflate(R.layout.seekbar, container, false);
        View root = binding.getRoot();
        setConfiguration();
        return root;
    }

    private void setConfiguration() {
        mViewModel =
                new ViewModelProvider(this).get(HomiliasViewModel.class);

        todayViewModel =
                new ViewModelProvider(this).get(TodayViewModel.class);
        mTextView = binding.include.tvZoomable;
        progressBar = binding.progressBar;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        isVoiceOn = prefs.getBoolean("voice", true);
        if (isVoiceOn) {
            sbReader = new StringBuilder(VOICE_INI);
        }
        pickOutDate();
        //observeData();
        //observeDatas();
        //mViewModel.fetchData(mDate);
        //mViewModel.getVMSalmodia(mDate);
        //observeSalmodia();
        observeLaudes();
        //observeTercia();

        //observeLast();

    }

    public void fetchData(Integer theDate) {
        this.mWorkManager= WorkManager.getInstance(getActivity());

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data inputData = new Data.Builder()
                .putInt("THE_DATE", theDate)
                .build();



        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(ExampleWorker.class, 15,
                        TimeUnit.MINUTES)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        .setInputData(inputData)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                "SYNC_DATA_WORK_NAME",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work
                // policy
                periodicSyncDataWork //work request
        );
        mWorkManager.getWorkInfoByIdLiveData(periodicSyncDataWork.getId()).observe(this,
                new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                mWorkManager.cancelWorkById(workInfo.getId());
            }
        });



    }

    private void pickOutDate() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDate = bundle.getString("FECHA") == null ? Utils.getHoy() :
                    bundle.getString("FECHA");
        }else{
            mDate=Utils.getHoy();
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Objects.requireNonNull(actionBar).setSubtitle(Utils.getTitleDate(mDate));
    }

    void observeData() {
        mTextView.setText(PACIENCIA);
        mViewModel.getObservable(mDate).observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        mTextView.setText(data.getData().getForView(), TextView.BufferType.SPANNABLE);
                        if(isVoiceOn){
                            sbReader.append(data.getData().getAllForRead());
                            setPlayerButton();
                        }
                    } else {
                        mTextView.setText(Utils.fromHtml(data.getError()));
                    }
                });
    }


    void observeLaudes() {
        mTextView.setText(PACIENCIA);
        todayViewModel.getLaudes(mDate).observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if(data!=null) {
                        Log.d("XYZa",
                                String.valueOf(data.toString()));
                        //mTextView.setText(data.getInvitatorio().getAll(true));
                        mTextView.setText(data.getForView(false));


                    }
                    //Log.d("XYZb",String.valueOf(data.get(1).salmodia.getSalmoId()));

                    //mTextView.setText(data.getAllForView());
                });
    }

    void observeTercia() {
        mTextView.setText(PACIENCIA);
        todayViewModel.getTercia(mDate).observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if(data!=null) {
                        Log.d("XYZa",
                                String.valueOf(data.toString()));
                        //mTextView.setText(data.getInvitatorio().getAll(true));
                        mTextView.setText(data.getForView());

                    }
                });
    }
    void observeSalmodia() {
        mTextView.setText(PACIENCIA);
        todayViewModel.getTodayWithOficioB(mDate).observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if(data!=null) {
                        Log.d("XYZa",
                                String.valueOf(data.toString()));
                        //mTextView.setText(data.getInvitatorio().getAll(true));
                        mTextView.setText(data.getForView(false));


                    }
                    //Log.d("XYZb",String.valueOf(data.get(1).salmodia.getSalmoId()));

                    //mTextView.setText(data.getAllForView());
                });
    }



    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        mainMenu = menu;
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_voz) {
            if (mActionMode == null) {
                mActionMode =
                        requireActivity().startActionMode(mActionModeCallback);
            }
            readText();
            isReading = true;
            requireActivity().invalidateOptionsMenu();
            return true;
        }

        NavController navController = NavHostFragment.findNavController(this);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    private String prepareForRead() {
        String notQuotes = Utils.stripQuotation(sbReader.toString());
        return String.valueOf(Utils.fromHtml(notQuotes));
    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.item_voz);
        if (isReading) {
            item.setVisible(false);
        }
    }


    @Override
    public void onCompleted() {
    }

    @Override
    public void onError() {
    }

    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int menuItem = item.getItemId();

            if (menuItem == R.id.audio_play) {
                readText();
                audioMenu.findItem(R.id.audio_pause).setVisible(true);
                audioMenu.findItem(R.id.audio_stop).setVisible(true);
                item.setVisible(false);
                return true;
            }

            if (menuItem == R.id.audio_pause) {
                mTtsManager.pause();
                audioMenu.findItem(R.id.audio_resume).setVisible(true);
                item.setVisible(false);
                return true;
            }

            if (menuItem == R.id.audio_resume) {
                mTtsManager.resume();
                audioMenu.findItem(R.id.audio_pause).setVisible(true);
                item.setVisible(false);
                return true;
            }
            if (menuItem == R.id.audio_stop) {
                mTtsManager.stop();
                audioMenu.findItem(R.id.audio_play).setVisible(true);
                audioMenu.findItem(R.id.audio_pause).setVisible(false);
                audioMenu.findItem(R.id.audio_resume).setVisible(false);
                item.setVisible(false);
                return true;
            }
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            audioMenu = menu;

            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(getContext()).inflate(R.layout.seekbar, null);
            mode.setCustomView(view);
            seekBar = view.findViewById(R.id.seekbar);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            cleanTTS();
            setPlayerButton();
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    };

    private void setPlayerButton() {
        mainMenu.findItem(R.id.item_voz).setVisible(isVoiceOn);
    }

    private void readText() {
        mTtsManager = new TtsManager(getContext(), prepareForRead(), SEPARADOR, (current, max) -> {
            seekBar.setProgress(current);
            seekBar.setMax(max);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mTtsManager == null) return;
                mTtsManager.changeProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mTtsManager.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mActionMode != null) {
            mActionMode.finish();
        }
        cleanTTS();
        binding = null;
    }

    private void cleanTTS() {
        if (mTtsManager != null) {
            mTtsManager.close();
        }
    }
}