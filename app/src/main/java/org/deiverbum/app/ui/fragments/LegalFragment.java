package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_BODY;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_CANCEL;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_OK;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_TITLE;
import static org.deiverbum.app.utils.Constants.MSG_LEGAL;
import static org.deiverbum.app.utils.Constants.PACIENCIA;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.deiverbum.app.utils.Configuration;
import org.deiverbum.app.utils.Constants;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

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
        Button button = binding.btnEmail;
        button.setOnClickListener(v -> {
            String subject = String.format(Locale.getDefault(), "Dudas " +
                    "Política Privacidad y/o " +
                    "Términos y Condiciones Liturgia+ v. %d", Constants.VERSION_CODE);
            composeEmail(new String[]{Configuration.MY_EMAIL}, subject);
        });
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
                            mTextView.setText(book.getForView(isNightMode()), TextView.BufferType.SPANNABLE);
                            //saveHtmlFile(book.getForHtml().toString());
                            agreeYes = book.getAgreeYes();
                            agreeNot = book.getAgreeNot();
                            //footLayout.setVisibility(View.VISIBLE);

                        } else {
                            mTextView.setText(Utils.fromHtml(data.getError()));
                        }
                        textContacto.setText(MSG_LEGAL);
                        bottomLayout.setVisibility(View.VISIBLE);
                        setAcceptText();

                    });
        }
    }


    @SuppressWarnings("unused")
    private void saveHtmlFile(String html) {
        String path = requireActivity().getExternalFilesDir(null).getAbsolutePath();
        String fileName = DateFormat.format("dd_MM_yyyy_hh_mm_ss", System.currentTimeMillis()).toString();
        fileName = fileName + ".html";
        File file = new File(path, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "Plantea a continuación tu duda: \n\n");
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
        requireActivity().onBackPressed();
    }

    public boolean isNightMode() {
        int nightModeFlags = requireActivity().getApplicationContext().getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}