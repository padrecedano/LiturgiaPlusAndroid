package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.FILE_PRIVACY;
import static org.deiverbum.app.utils.Constants.FILE_TERMS;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;
import static org.deiverbum.app.utils.Utils.LS2;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import org.deiverbum.app.R;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentAcceptanceBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;
import org.deiverbum.app.viewmodel.SyncViewModel;

import java.text.MessageFormat;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>Fragmento que muestra el diálogo inicial para la Aceptción de la
 * Política de Privacidad y los Términos y Condiciones de Uso.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

@AndroidEntryPoint
public class AcceptanceFragmentDialog extends DialogFragment {

    public static final String TAG = "AcceptanceFragmentDialog";
    private FragmentAcceptanceBinding binding;

    private FileViewModel mViewModel;
    private SyncViewModel syncViewModel;

    private TextView textPrivacy;
    private TextView textTerms;

    public static void display(FragmentManager fragmentManager) {
        AcceptanceFragmentDialog acceptanceFragmentDialog = new AcceptanceFragmentDialog();
        acceptanceFragmentDialog.setCancelable(false);
        acceptanceFragmentDialog.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FileViewModel.class);
        syncViewModel = new ViewModelProvider(this).get(SyncViewModel.class);
        binding = FragmentAcceptanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareView();
        observeData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void prepareView() {
        ColorUtils.isNightMode=isNightMode();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(sp.getString("font_size", "18"));
        TextView textInitial = binding.textInitial;
        textPrivacy = binding.textPrivacy;
        textTerms = binding.textTerms;
        TextView textFinal = binding.textFinal;
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textPrivacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textTerms.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        Button button = binding.btnEmail;
  SpannableStringBuilder ssb=new SpannableStringBuilder();
ssb.append(Utils.toH3Red(getActivity().getResources().getString(R.string.accept_intro)));
ssb.append(LS2);
ssb.append(getActivity().getResources().getString(R.string.accept_info));
        textInitial.setText(ssb);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.toH2Red("Aceptación"));
        sb.append("\n\nSi aceptas tanto la Política de Privacidad, como los " +
                "Términos y Condiciones de Uso, pulsa en el botón Aceptar. " +
                "Accederás a la " +
                "pantalla inicial de la aplicación y el " +
                "estado de tu aceptación se guardará en el dispositivo. " +
                "Será revocado si desinstalas la Aplicación o si desmarcas el" +
                " botón de aceptación en algún momento.");
        textFinal.setText(sb, TextView.BufferType.SPANNABLE);

        button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_ACCEPT, true).apply();
            boolean isInitialSync=sp.getBoolean("initialSync", false);
            if(!isInitialSync) {
                syncViewModel.initialSync();
                syncViewModel.getInitialSyncStatus().observe(getViewLifecycleOwner(),
                        data -> {
                            boolean isSuccess = data > 0;
                            sp.edit().putBoolean("initialSync", isSuccess).apply();
                        });
            }
            dismiss();
        });
    }

    private void observeData() {
        mViewModel.getBook(FILE_PRIVACY).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textPrivacy.setText(book.getForView(isNightMode()), TextView.BufferType.SPANNABLE);
                    } else {
                        textPrivacy.setText(Utils.fromHtml(data.getError()));
                    }
                });

        mViewModel.getBook(FILE_TERMS).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textTerms.setText(book.getForView(isNightMode()),
                                TextView.BufferType.SPANNABLE);
                    } else {
                        textTerms.setText(Utils.fromHtml(data.getError()));
                    }
                });
    }

    public boolean isNightMode() {
        int nightModeFlags = requireActivity().getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

}
