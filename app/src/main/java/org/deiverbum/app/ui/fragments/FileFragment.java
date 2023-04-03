package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PACIENCIA;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.databinding.FragmentFileBinding;
import org.deiverbum.app.model.Book;
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
public class FileFragment extends Fragment {

    private FileViewModel mViewModel;
    private FragmentFileBinding binding;
    private TextView mTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FileViewModel.class);

        binding = FragmentFileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        float fontSize = Float.parseFloat(prefs.getString("font_size", "18"));
        mTextView = binding.include.tvClickable;
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        String fontFamily = String.format(new Locale("es"),"fonts/%s",prefs.getString("font_name", "robotoslab_regular.ttf"));
        Typeface tf= Typeface.createFromAsset(requireActivity().getAssets(),fontFamily);
        mTextView .setTypeface(tf);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setClickable(true);
        observeBook();
        return root;
    }

    private void observeBook() {
        mTextView.setText(PACIENCIA);
        if (getArguments() != null) {
            String filePath = getArguments().getString("rawPath");
            mViewModel.getBook(filePath).observe(getViewLifecycleOwner(),
                    data -> {
                        if (data.status == DataWrapper.Status.SUCCESS) {
                            Book book = data.getData();
                            mTextView.setText(book.getForView(isNightMode()), TextView.BufferType.SPANNABLE);
                        } else {
                            mTextView.setText(Utils.fromHtml(data.getError()));
                        }
                    });
        }
      }

    public boolean isNightMode() {
        int nightModeFlags = requireActivity().getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}