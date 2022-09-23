package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentAuthorBinding;
import org.deiverbum.app.databinding.FragmentFileBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Configuration;
import org.deiverbum.app.utils.Constants;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>
 *     Este Fragmento coordina la obtención de datos que provienen de archivos
 *     guardados en el directorio res/raw.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@AndroidEntryPoint
public class AuthorFragment extends Fragment {

    private FileViewModel mViewModel;
    private FragmentAuthorBinding binding;
    private TextView mTextView;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FileViewModel.class);

        binding = FragmentAuthorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));

        mTextView = binding.include.tvZoomable;
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setClickable(true);
        progressBar = binding.pb.progressBar;

        Button button = binding.include.btnEmail;
        button.setOnClickListener(v -> {
            String subject = String.format(Locale.getDefault(), "Mensaje " +
                    "desde Liturgy+ v. %d", Constants.VERSION_CODE);
            composeEmail(new String[]{Configuration.MY_EMAIL}, subject);
        });

        observeBook();
        return root;
    }

    private void observeBook() {
        mTextView.setText(PACIENCIA);
        if (getArguments() != null) {
            String filePath = getArguments().getString("rawPath");
            mViewModel.getBook(filePath).observe(getViewLifecycleOwner(),
                    data -> {
                        progressBar.setVisibility(View.GONE);

                        if (data.status == DataWrapper.Status.SUCCESS) {
                            Book book = data.getData();
                            mTextView.setText(book.getForView(), TextView.BufferType.SPANNABLE);
                        } else {
                            mTextView.setText(Utils.fromHtml(data.getError()));
                        }

                    });

        }
      }

    private void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, "Escribe a continuación tu " +
                "mensaje: " +
                "\n\n");
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
        requireActivity().onBackPressed();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}