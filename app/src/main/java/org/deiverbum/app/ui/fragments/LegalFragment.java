package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_BODY;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_CANCEL;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_OK;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_TITLE;
import static org.deiverbum.app.utils.Constants.MSG_LEGAL;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentLegalBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>
 * Este Fragmento maneja lo relativo a documentos legales.
 * Extrae el contenido de un archivo .json y muestra la Política de
 * Privacidad o los Términos y Condiciones de Uso, según el caso.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@AndroidEntryPoint
public class LegalFragment extends Fragment {

    private FileViewModel mViewModel;
    private FragmentLegalBinding binding;
    private TextView mTextView;
    private TextView textAgree;
    private TextView textContacto;
    private boolean acceptLegal;
    private LinearLayout bottomLayout;
    private String agreeYes;
    private String agreeNot;
    private ProgressBar progressBar;
    private SwitchMaterial switchAccept;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLegalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareView();
        observeBook();
        return root;
    }

    private void prepareView() {
        mViewModel =
                new ViewModelProvider(this).get(FileViewModel.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        acceptLegal = sp.getBoolean(PREF_ACCEPT, false);
        progressBar = binding.progressBar;
        switchAccept = binding.switchAccept;
        switchAccept.setChecked(acceptLegal);
        switchAccept.setOnCheckedChangeListener((buttonView, isChecked) -> {
            acceptLegal = isChecked;
            setAcceptText();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_ACCEPT, isChecked);
            editor.apply();
        });
        mTextView = binding.textLegal;
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setClickable(true);
        textAgree = binding.textAgree;
        textContacto = binding.textContacto;
        bottomLayout = binding.bottomLayout;
        bottomLayout.setVisibility(View.GONE);
    }

    private void observeBook() {
        progressBar.setVisibility(View.VISIBLE);
        mTextView.setText(PACIENCIA);
        if (getArguments() != null) {
            String filePath = getArguments().getString("rawPath");
            mViewModel.getBook(filePath).observe(getViewLifecycleOwner(),
                    data -> {
                        progressBar.setVisibility(View.GONE);
                        if (data.status == DataWrapper.Status.SUCCESS) {
                            Book book = data.getData();
                            mTextView.setText(book.getForView(), TextView.BufferType.SPANNABLE);
                            agreeYes = book.getAgreeYes();
                            agreeNot = book.getAgreeNot();
                        } else {
                            mTextView.setText(Utils.fromHtml(data.getError()));
                        }
                        textContacto.setText(MSG_LEGAL);
                        bottomLayout.setVisibility(View.VISIBLE);
                        setAcceptText();

                    });
        }

    }

    private void setAcceptText() {
        String acceptText = (acceptLegal) ? agreeYes : agreeNot;
        textAgree.setText(acceptText);
        if (!acceptLegal) {
            showConfirm();
        }
    }

    private void showConfirm() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity());
        materialAlertDialogBuilder.setTitle(DIALOG_LEGAL_TITLE);
        materialAlertDialogBuilder.setMessage(DIALOG_LEGAL_BODY);
        materialAlertDialogBuilder.setPositiveButton(DIALOG_LEGAL_OK,
                (dialogInterface, i) -> closeApp());
        materialAlertDialogBuilder.setNegativeButton(DIALOG_LEGAL_CANCEL,
                (dialogInterface, i) -> updateSwitch());
        materialAlertDialogBuilder.show();
    }

    private void closeApp() {
        requireActivity().finishAndRemoveTask();
    }

    private void updateSwitch() {
        switchAccept.setChecked(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}