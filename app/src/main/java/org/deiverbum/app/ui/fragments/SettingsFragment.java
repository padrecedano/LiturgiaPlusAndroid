package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_BODY;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_CANCEL;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_OK;
import static org.deiverbum.app.utils.Constants.DIALOG_LEGAL_TITLE;
import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.deiverbum.app.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
        Preference acceptTerms = findPreference(PREF_ACCEPT);

        if (acceptTerms != null) {
            acceptTerms.setOnPreferenceClickListener(
                    arg0 -> {
                        showConfirm();
                        return true;
                    });
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
                (dialogInterface, i) -> updatePreference());
        materialAlertDialogBuilder.show();
    }

    private void closeApp() {
        requireActivity().finishAndRemoveTask();
    }

    private void updatePreference(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(PREF_ACCEPT, true);
        editor.apply();
    }
}