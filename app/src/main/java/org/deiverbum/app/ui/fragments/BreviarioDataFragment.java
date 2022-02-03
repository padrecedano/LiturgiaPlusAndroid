package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.VOICE_INI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import net.gotev.speech.TextToSpeechCallback;

import org.deiverbum.app.R;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentBreviarioDataBinding;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.utils.TTS;
import org.deiverbum.app.utils.TtsManager;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;
import org.deiverbum.app.viewmodel.BreviarioViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BreviarioDataFragment extends Fragment implements TextToSpeechCallback {
    private static final String TAG = "BreviarioDataFragment";
    private BreviarioViewModel mViewModel;
    private FragmentBreviarioDataBinding binding;
    private ZoomTextView mTextView;
    private ProgressBar progressBar;
    private boolean isVoiceOn;
    private StringBuilder sbReader;
    private Menu menu;
    private String mDate;
    private SeekBar seekBar;
    private boolean isReading = false;

    private boolean hasInvitatorio;
    private TTS tts;
    private SharedPreferences prefs;

    private Menu audioMenu;
    private Menu mainMenu;

    public static ActionMode mActionMode;
    private TtsManager mTtsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        binding = FragmentBreviarioDataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setConfiguration();
        observeHour();
        return root;
    }

    private void setConfiguration() {

        mViewModel =
                new ViewModelProvider(this).get(BreviarioViewModel.class);
        mTextView = binding.include.tvZoomable;
        //mViewModel.test();
        //observeMixtos();
        progressBar = binding.progressBar;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        isVoiceOn = prefs.getBoolean("voice", true);
        if (isVoiceOn) {
            sbReader = new StringBuilder(VOICE_INI);
        }
        pickOutDate();
    }

    private void observeHour() {
        int hourId = getArguments().getInt("hourId");
        switch (hourId) {
            case 0:
                observeMixto();
                break;

            case 1:
                observeOficio();
                break;

            case 2:
                observeLaudes();
                break;

            case 3:
                observeIntermedia(hourId, "tercia");
                break;

            case 4:
                observeIntermedia(hourId, "sexta");
                break;

            case 5:
                observeIntermedia(hourId, "nona");
                break;

            case 6:
                observeVisperas();
                break;

            case 7:
                break;

            default:
                break;
        }
    }

    private void observeMixto() {
        mTextView.setText(PACIENCIA);
        mViewModel.getMixto(mDate).observe(getViewLifecycleOwner(), (Observer<DataWrapper<Mixto, CustomException>>) data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                mTextView.setText(data.getData().getForView(hasInvitatorio));
                if (isVoiceOn) {
                    sbReader.append(data.getData().getForRead(hasInvitatorio));
                    setPlayerButton();
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }


    private void observeOficio() {
        mTextView.setText(PACIENCIA);
        mViewModel.getOficio(mDate).observe(getViewLifecycleOwner(), (Observer<DataWrapper<Oficio, CustomException>>) data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                mTextView.setText(data.getData().getForView(hasInvitatorio));
                if (isVoiceOn) {
                    sbReader.append(data.getData().getForRead(hasInvitatorio));
                    setPlayerButton();
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }

    private void observeLaudes() {
        mTextView.setText(PACIENCIA);
        mViewModel.getLaudes(mDate).observe(getViewLifecycleOwner(), (Observer<DataWrapper<Laudes, CustomException>>) data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                mTextView.setText(data.getData().getForView(hasInvitatorio));
                if (isVoiceOn) {
                    sbReader.append(data.getData().getForRead(hasInvitatorio));
                    setPlayerButton();
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }

    private void observeIntermedia(int hourId, String endPoint) {
        mTextView.setText(PACIENCIA);
        mViewModel.getIntermedia(mDate, hourId, endPoint).observe(getViewLifecycleOwner(), (Observer<DataWrapper<Intermedia, CustomException>>) data -> {
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

    private void observeVisperas() {
        mTextView.setText(PACIENCIA);
        mViewModel.getVisperas(mDate).observe(getViewLifecycleOwner(), (Observer<DataWrapper<Visperas, CustomException>>) data -> {
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
        mDate = (bundle != null && bundle.containsKey("FECHA")) ? bundle.getString("FECHA") : Utils.getHoy();
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