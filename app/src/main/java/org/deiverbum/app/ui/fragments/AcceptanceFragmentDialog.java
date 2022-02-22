package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.FILE_PRIVACY;
import static org.deiverbum.app.utils.Constants.FILE_TERMS;
import static org.deiverbum.app.utils.Constants.MSG_LEGAL;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;
import static org.deiverbum.app.utils.Utils.LS2;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentAcceptanceBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Configuration;
import org.deiverbum.app.utils.Constants;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;

import java.util.Locale;

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

    private boolean checkStatus;
    private FileViewModel mViewModel;
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

        float fontSize = Float.parseFloat(sp.getString("font_size", "18"));
        TextView textInitial = binding.textInitial;
        textPrivacy = binding.textPrivacy;
        textTerms = binding.textTerms;
        TextView textFinal = binding.textFinal;
        TextView textContacto = binding.textContacto;
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textPrivacy.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textTerms.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textContacto.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        Button button = binding.btnEmail;

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

        //sb.clear();
        //sb.append(Utils.toH2Red("Contacto"));
        //sb.append("\n\nSi tienes alguna duda sobre la Política de
        // Privacidad o los Términos y Condiciones de Uso, ponte en contacto con nosotros pulsando en el botón \"Enviar eMail\" que aparece a continación.");
       //sb.append(LS2);
        //sb.append(MSG_LEGAL);
        //textContacto.setText(sb, TextView.BufferType.SPANNABLE);

        SwitchMaterial switchAccept = binding.switchAccept;
        switchAccept.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sp.edit();
            checkStatus = isChecked;
            editor.putBoolean(PREF_ACCEPT, isChecked);
            editor.apply();
            if (isChecked) {
                dismiss();
            }
        });

        button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_ACCEPT, true);
            editor.apply();
            dismiss();
        });
    }

    private void observeData() {
        mViewModel.getBook(FILE_PRIVACY).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textPrivacy.setText(book.getForView(), TextView.BufferType.SPANNABLE);
                    } else {
                        textPrivacy.setText(Utils.fromHtml(data.getError()));
                    }
                });

        mViewModel.getBook(FILE_TERMS).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textTerms.setText(book.getForView(),
                                TextView.BufferType.SPANNABLE);
                    } else {
                        textTerms.setText(Utils.fromHtml(data.getError()));
                    }
                });
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
        if (checkStatus) {
            dismiss();
        }
    }

}
