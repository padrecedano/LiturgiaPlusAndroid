package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SYNC_LABEL;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.FragmentSyncBinding;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;
import org.deiverbum.app.viewmodel.SyncViewModel;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SyncFragment extends Fragment {
    public static ActionMode mActionMode;
    private SyncViewModel mViewModel;
    private FragmentSyncBinding binding;
    private ZoomTextView mTextView;
    private ProgressBar progressBar;
    private ExtendedFloatingActionButton mButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSyncBinding.inflate(inflater, container, false);
        inflater.inflate(R.layout.seekbar, container, false);
        View root = binding.getRoot();
        setConfiguration();
        return root;
    }

    private void setConfiguration() {
        mViewModel =
                new ViewModelProvider(this).get(SyncViewModel.class);
        mTextView = binding.include.tvZoomable;
        progressBar = binding.progressBar;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        String fontFamily = String.format(new Locale("es"), "fonts/%s", prefs.getString("font_name", "robotoslab_regular.ttf"));
        Typeface tf = Typeface.createFromAsset(requireActivity().getAssets(), fontFamily);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTextView.setTypeface(tf);

        mButton = binding.include.btnEmail;
        mButton.setVisibility(View.GONE);

        mButton.setOnClickListener(v -> {
            mViewModel.launchSyncWorker();
            mButton.setVisibility(View.GONE);
            observeData();
        });

        if (!isWorkScheduled()) {
            progressBar.setVisibility(View.GONE);
            mTextView.setText(Utils.fromHtml(new SyncStatus().getLastUpdate(isNightMode())));
            mButton.setIconResource(R.drawable.ic_refresh_black_24dp);
            mButton.setText(SYNC_LABEL);
            mButton.setVisibility(View.VISIBLE);
        } else {
            observeData();
        }
    }

    void observeData() {
        mTextView.setText(PACIENCIA);
        mViewModel.getObservable().observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if (data != null) {
                        mTextView.setText(Utils.fromHtml(data.getAll(isNightMode())));
                    } else {
                        mTextView.setText(Utils.fromHtml(new SyncStatus().getLastUpdate(isNightMode())));
                    }
                });
    }

    private boolean isWorkScheduled() {
        WorkManager instance = WorkManager.getInstance(Objects.requireNonNull(requireActivity()).getApplicationContext());
        ListenableFuture<List<WorkInfo>> statuses = instance.getWorkInfosByTag("TAG_SYNC_DATA");
        try {
            boolean running = false;
            List<WorkInfo> workInfoList = statuses.get();
            for (WorkInfo workInfo : workInfoList) {
                WorkInfo.State state = workInfo.getState();
                running = state == WorkInfo.State.RUNNING | state == WorkInfo.State.ENQUEUED;
            }
            return running;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public boolean isNightMode() {
        int nightModeFlags = requireActivity().getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mActionMode != null) {
            mActionMode.finish();
        }
        binding = null;
    }

}