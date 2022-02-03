package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.MSG_LEGAL;
import static org.deiverbum.app.utils.Constants.PACIENCIA;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentFileBinding;
import org.deiverbum.app.model.Book;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.FileViewModel;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>
 *     Este Fragmento coordina la obtención de datos que provienen de archivos
 *     guardados en el directorio res/raw.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01
 */
@AndroidEntryPoint
public class FileFragment extends Fragment {
    private static final String TAG = "FileFragment";

    private FileViewModel mViewModel;
    private FragmentFileBinding binding;
    private TextView mTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FileViewModel.class);

        binding = FragmentFileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        mTextView = binding.includeTv.tvClickable;
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setClickable(true);
        mViewModel.getText(getArguments().getString("rawPath")).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(Utils.fromHtml(s), TextView.BufferType.SPANNABLE);
            }
        });
        observeBook();
        return root;
    }

    private void observeBook() {
        //progressBar.setVisibility(View.VISIBLE);
        mTextView.setText(PACIENCIA);
        String filePath = getArguments().getString("rawPath");
        mViewModel.getBook(filePath).observe(getViewLifecycleOwner(),
                data -> {
                    //progressBar.setVisibility(View.GONE);
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        mTextView.setText(book.getForView(), TextView.BufferType.SPANNABLE);
                    } else {
                        mTextView.setText(Utils.fromHtml(data.getError()));
                    }

                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}