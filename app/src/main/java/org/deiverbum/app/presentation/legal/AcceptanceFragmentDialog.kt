package org.deiverbum.app.presentation.legal

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.SpannableStringBuilder
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentAcceptanceBinding
import org.deiverbum.app.presentation.file.FileViewModel
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.viewmodel.SyncViewModel

/**
 *
 * Fragmento que muestra el diálogo inicial para la Aceptción de la
 * Política de Privacidad y los Términos y Condiciones de Uso.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@AndroidEntryPoint
class AcceptanceFragmentDialog : DialogFragment() {
    private var binding: FragmentAcceptanceBinding? = null
    private var mViewModel: FileViewModel? = null
    private var syncViewModel: SyncViewModel? = null
    private var textPrivacy: TextView? = null
    private var textTerms: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewModel = ViewModelProvider(this).get(FileViewModel::class.java)
        syncViewModel = ViewModelProvider(this).get(SyncViewModel::class.java)
        binding = FragmentAcceptanceBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        prepareView()
        observeData()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun prepareView() {
        ColorUtils.isNightMode = isNightMode
        val sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val fontSize = sp.getString("font_size", "18")!!.toFloat()
        val textInitial = binding!!.textInitial
        textPrivacy = binding!!.textPrivacy
        textTerms = binding!!.textTerms
        val textFinal = binding!!.textFinal
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textPrivacy!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textTerms!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        val button: Button = binding!!.btnEmail
        val ssb = SpannableStringBuilder()
        val intro = String.format(
            "%s %s%n%s",
            requireActivity().resources.getString(R.string.accept_intro),
            requireActivity().resources.getString(R.string.app_name),
            requireActivity().resources.getString(R.string.app_version_code_view)
        )
        ssb.append(Utils.toH3Red(intro))
        ssb.append(Constants.LS2)
        ssb.append(requireActivity().resources.getString(R.string.accept_info))
        textInitial.text = ssb
        val sb = SpannableStringBuilder()
        sb.append(Utils.toH2Red("Aceptación"))
        sb.append(
            """
    
    
    Si aceptas tanto la Política de Privacidad, como los Términos y Condiciones de Uso, pulsa en el botón Aceptar. Accederás a la pantalla inicial de la aplicación y el estado de tu aceptación se guardará en el dispositivo. Será revocado si desinstalas la Aplicación o si desmarcas el botón de aceptación en algún momento.
    """.trimIndent()
        )
        textFinal.setText(sb, TextView.BufferType.SPANNABLE)
        button.setOnClickListener { v: View? ->
            val editor = sp.edit()
            editor.putBoolean(Constants.PREF_ACCEPT, true).apply()
            val isInitialSync = sp.getBoolean("initialSync", false)
            if (!isInitialSync) {
                syncViewModel!!.initialSync()
                syncViewModel!!.initialSyncStatus.observe(
                    viewLifecycleOwner
                ) { data: Int ->
                    val isSuccess = data > 0
                    sp.edit().putBoolean("initialSync", isSuccess).apply()
                }
            }
            dismiss()
        }
    }

    private fun observeData() {
        /*
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
        */
    }

    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }

    companion object {
        const val TAG = "AcceptanceFragmentDialog"
        fun display(fragmentManager: FragmentManager?) {
            val acceptanceFragmentDialog = AcceptanceFragmentDialog()
            acceptanceFragmentDialog.isCancelable = false
            acceptanceFragmentDialog.show(fragmentManager!!, TAG)
        }
    }
}