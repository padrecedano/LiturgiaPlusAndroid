package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.ERR_SUBJECT;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.deiverbum.app.databinding.FragmentBugreportBinding;
import org.deiverbum.app.utils.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BugReportFragment extends Fragment {


    private FragmentBugreportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBugreportBinding.inflate(inflater, container, false);
        Button button = binding.btnSend;
        View root = binding.getRoot();
        button.setOnClickListener(v -> {
            LinearLayout left=binding.leftSide;
            LinearLayout right=binding.rightSide;
            List<CharSequence> selected=new ArrayList<>();
            for(int i = 0; i < left.getChildCount(); i++) {
                CheckBox checkBox= (CheckBox) left.getChildAt(i);
                if(checkBox.isChecked()){
                    selected.add(checkBox.getText());
                }
            }

            for(int i = 0; i < right.getChildCount(); i++) {
                CheckBox checkBox= (CheckBox) right.getChildAt(i);
                if(checkBox.isChecked()){
                    selected.add(checkBox.getText());
                }
            }

            String textSelected= TextUtils.join(", ", selected);
            String msg=String.format("Mensaje: \n\n%s\n\nEn:\n\n%s", Objects.requireNonNull(binding.message.getText()),textSelected);
            composeEmail(new String[]{Configuration.MY_EMAIL},  ERR_SUBJECT, msg);
        });

        return root;
    }

    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
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