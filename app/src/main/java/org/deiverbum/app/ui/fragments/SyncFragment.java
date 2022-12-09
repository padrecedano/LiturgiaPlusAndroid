package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;

import android.content.SharedPreferences;
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

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.FragmentSyncBinding;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;
import org.deiverbum.app.viewmodel.SyncViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SyncFragment extends Fragment {
    private SyncViewModel mViewModel;

    private FragmentSyncBinding binding;
    private ZoomTextView mTextView;

    private ProgressBar progressBar;


    public static ActionMode mActionMode;


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
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        observeData();
    }

    void observeData() {
        mTextView.setText(PACIENCIA);
        mViewModel.getObservable().observe(getViewLifecycleOwner(),
                data -> {
                    progressBar.setVisibility(View.GONE);
                    if (data!=null) {
                        mTextView.setText(Utils.fromHtml(data.getAll()));
                    } else {
                        mTextView.setText(Utils.fromHtml("data.getError())"));
                    }
                });
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