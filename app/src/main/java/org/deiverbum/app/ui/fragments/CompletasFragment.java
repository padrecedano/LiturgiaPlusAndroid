package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.VOICE_INI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import net.gotev.speech.TextToSpeechCallback;

import org.deiverbum.app.R;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentCompletasBinding;
import org.deiverbum.app.databinding.FragmentFileBinding;
import org.deiverbum.app.model.Breviario;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.utils.TtsManager;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.BreviarioViewModel;
import org.deiverbum.app.viewmodel.CompletasViewModel;
import org.deiverbum.app.viewmodel.FileViewModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>
 *     Este Fragmento coordina la obtención de datos para Completas.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @date 23/12/21
 * @since 2021.01
 */
@AndroidEntryPoint
public class CompletasFragment extends Fragment implements TextToSpeechCallback {
    private static final String TAG = "CompletasFragment";

    private FileViewModel mViewModelB;
    private CompletasViewModel mViewModel;

    private FragmentCompletasBinding binding;
    private TextView mTextView;
    private String mDate;
    private boolean isVoiceOn;
    private StringBuilder sbReader;
    private ProgressBar progressBar;
    private SeekBar seekBar;

    private boolean isReading = false;

    private Menu audioMenu;
    private Menu mainMenu;

    public static ActionMode mActionMode;
    private TtsManager mTtsManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        binding = FragmentCompletasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setConfiguration();

observeData();
        return root;
    }

    private void setConfiguration() {
        mViewModel =
                new ViewModelProvider(this).get(CompletasViewModel.class);
        mTextView = binding.include.tvZoomable;
        progressBar = binding.progressBar;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        isVoiceOn = prefs.getBoolean("voice", true);
        if (isVoiceOn) {
            sbReader = new StringBuilder(VOICE_INI);
        }
        pickOutDate();
    }

    private void observeData() {
        mTextView.setText(PACIENCIA);
        mViewModel.getMeta(mDate);
        mViewModel.getObservable().observe(this, (Observer<DataWrapper<Completas, CustomException>>) data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                    mTextView.setText(data.getData().getForView());
                if (isVoiceOn) {
                    sbReader.append(data.getData().getForRead());
                    setPlayerButton();
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }


    private void pickOutDate() {
        Bundle bundle = getArguments();
        mDate= (bundle != null && bundle.containsKey("FECHA")) ? bundle.getString("FECHA") : Utils.getHoy();
    }

    private void showData(String data) {

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        mainMenu = menu;
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_voz) {
            if (mActionMode == null) {
                mActionMode = getActivity().startActionMode(mActionModeCallback);
            }
            readText();
            isReading = true;
            getActivity().invalidateOptionsMenu();
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
        Log.d(TAG, "completed");

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