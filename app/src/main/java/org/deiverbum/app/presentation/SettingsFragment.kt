package org.deiverbum.app.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.deiverbum.app.R
import org.deiverbum.app.util.Constants

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
        val acceptTerms = findPreference<Preference>(Constants.PREF_ACCEPT)
        if (acceptTerms != null) {
            acceptTerms.onPreferenceClickListener =
                Preference.OnPreferenceClickListener { arg0: Preference? ->
                    showConfirm()
                    true
                }
        }
    }

    private fun showConfirm() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireActivity())
        materialAlertDialogBuilder.setTitle(Constants.DIALOG_LEGAL_TITLE)
        materialAlertDialogBuilder.setMessage(Constants.DIALOG_LEGAL_BODY)
        materialAlertDialogBuilder.setPositiveButton(
            Constants.DIALOG_LEGAL_OK
        ) { dialogInterface: DialogInterface?, i: Int -> closeApp() }
        materialAlertDialogBuilder.setNegativeButton(
            Constants.DIALOG_LEGAL_CANCEL
        ) { dialogInterface: DialogInterface?, i: Int -> updatePreference() }
        materialAlertDialogBuilder.show()
    }

    private fun closeApp() {
        requireActivity().finishAndRemoveTask()
    }

    private fun updatePreference() {
        val sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = sp.edit()
        editor.putBoolean(Constants.PREF_ACCEPT, true)
        editor.apply()
    }
}