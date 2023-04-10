package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.FILE_ANGELUS;
import static org.deiverbum.app.utils.Constants.FILE_LITANIES;
import static org.deiverbum.app.utils.Constants.FILE_REGINA;
import static org.deiverbum.app.utils.Constants.FILE_VIA_CRUCIS_2003;
import static org.deiverbum.app.utils.Constants.FILE_VIA_CRUCIS_2005;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.SEPARADOR;
import static org.deiverbum.app.utils.Constants.VOICE_INI;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import org.deiverbum.app.R;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentTextBinding;
import org.deiverbum.app.model.OracionSimple;
import org.deiverbum.app.model.Rosario;
import org.deiverbum.app.model.ViaCrucis;
import org.deiverbum.app.utils.TextToSpeechCallback;
import org.deiverbum.app.utils.TtsManager;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.utils.ZoomTextView;
import org.deiverbum.app.viewmodel.OracionesViewModel;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OracionesDataFragment extends Fragment implements TextToSpeechCallback {
    public static ActionMode mActionMode;
    private OracionesViewModel mViewModel;
    private FragmentTextBinding binding;
    private ZoomTextView mTextView;
    private ProgressBar progressBar;
    private boolean isVoiceOn;
    private StringBuilder sbReader;
    private SeekBar seekBar;
    private boolean isReading = false;
    private SharedPreferences prefs;
    private Menu audioMenu;
    private MenuItem voiceItem;
    private TtsManager mTtsManager;
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
    private ActionBar actionBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu);
                voiceItem = menu.findItem(R.id.item_voz);
                voiceItem.setVisible(isVoiceOn);
                if (isReading) {
                    voiceItem.setVisible(false);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    NavController navController = NavHostFragment.findNavController(requireParentFragment());
                    navController.popBackStack();
                    return true;
                } else if (item.getItemId() == R.id.item_voz) {
                    if (mActionMode == null) {
                        mActionMode =
                                requireActivity().startActionMode(mActionModeCallback);
                    }
                    readText();
                    isReading = true;
                    voiceItem.setVisible(false);
                    requireActivity().invalidateOptionsMenu();
                    return true;
                } else {
                    NavController navController = NavHostFragment.findNavController(requireParentFragment());
                    return NavigationUI.onNavDestinationSelected(item, navController);
                }
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding = FragmentTextBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setConfiguration();
        observeHour();
        return root;
    }

    private void setConfiguration() {
        mViewModel =
                new ViewModelProvider(this).get(OracionesViewModel.class);
        mTextView = binding.include.tvZoomable;
        progressBar = binding.progressBar;
        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        String fontFamily = String.format(new Locale("es"), "fonts/%s", prefs.getString("font_name", "robotoslab_regular.ttf"));
        Typeface tf = Typeface.createFromAsset(requireActivity().getAssets(), fontFamily);
        mTextView.setTypeface(tf);
        isVoiceOn = prefs.getBoolean("voice", true);
        //pickOutDate();
    }

    private void observeHour() {
        int param = requireArguments().getInt("id");
        switch (param) {
            case 1:
            case 2:
            case 3:
            case 4:
                observeRosario(param);
                break;

            case 5:
                observeSingle(FILE_LITANIES);
                break;

            case 6:
                observeSingle(FILE_ANGELUS);
                break;

            case 7:
                observeSingle(FILE_REGINA);
                break;

            case 8:
                observeViaCrucis(FILE_VIA_CRUCIS_2003);
                break;

            case 9:
                observeViaCrucis(FILE_VIA_CRUCIS_2005);
                break;

            default:
                break;
        }
    }

    private void observeRosario(int param) {
        boolean isBrevis = prefs.getBoolean("brevis", true);
        mTextView.setText(PACIENCIA);
        mViewModel.getRosario(param).observe(getViewLifecycleOwner(), data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                Rosario rosario = data.getData();
                actionBar.setTitle("Rosario");
                actionBar.setSubtitle(rosario.getSubTitle());
                mTextView.setText(rosario.getForView(isBrevis, isNightMode()));
                if (isVoiceOn) {
                    sbReader = new StringBuilder(VOICE_INI);
                    sbReader.append(rosario.getForRead());
                }
            } else {
                mTextView.setText(data.getError());
            }
        });
    }

    private void observeSingle(String rawPath) {
        mTextView.setText(PACIENCIA);
        mViewModel.getOracionSimple(rawPath).observe(getViewLifecycleOwner(), data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                OracionSimple oracionSimple = data.getData();
                actionBar.setTitle(oracionSimple.getTitulo());
                actionBar.setSubtitle("");
                mTextView.setText(oracionSimple.getForView(isNightMode()));
                if (isVoiceOn) {
                    sbReader = new StringBuilder(VOICE_INI);
                    sbReader.append(oracionSimple.getForRead());
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }

    private void observeViaCrucis(String rawPath) {
        mTextView.setText(PACIENCIA);
        mViewModel.getViaCrucis(rawPath).observe(getViewLifecycleOwner(), data -> {
            progressBar.setVisibility(View.GONE);
            if (data.status == DataWrapper.Status.SUCCESS) {
                ViaCrucis viaCrucis = data.getData();
                actionBar.setTitle(viaCrucis.getTitulo());
                actionBar.setSubtitle("");
                mTextView.setText(viaCrucis.getForView(isNightMode()));
                if (isVoiceOn) {
                    sbReader = new StringBuilder(VOICE_INI);
                    sbReader.append(viaCrucis.getForRead());
                }
            } else {
                mTextView.setText(Utils.fromHtml(data.getError()));
            }
        });
    }

    private String prepareForRead() {
        String notQuotes = Utils.stripQuotation(sbReader.toString());
        return String.valueOf(Utils.fromHtml(notQuotes));
    }

    private void setPlayerButton() {
        voiceItem.setVisible(isVoiceOn);
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
    public void onCompleted() {
    }

    @Override
    public void onError() {
    }

    private void cleanTTS() {
        if (mTtsManager != null) {
            mTtsManager.close();
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
        cleanTTS();
        binding = null;
    }

}