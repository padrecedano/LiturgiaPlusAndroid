package org.deiverbum.app.core.presentation.legal

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.presentation.file.FileFragmentArgs
import org.deiverbum.app.core.presentation.file.FileItemUiState
import org.deiverbum.app.core.presentation.file.FileViewModel
import org.deiverbum.app.databinding.FragmentLegalBinding
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_TERMS
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 *
 *
 * Este Fragmento maneja lo relativo a documentos legales.
 * Extrae el contenido de un archivo .json y muestra la Política de
 * Privacidad o los Términos y Condiciones de Uso, según el caso.
 *
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@AndroidEntryPoint
class LegalFragment : Fragment() {
    private var acceptLegal = false
    private val prefs: SharedPreferences by lazy {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }
    private val mViewModel: FileViewModel by viewModels()
    private var _binding: FragmentLegalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLegalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        prepareView()
        return root
    }

    private fun prepareView() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
        acceptLegal = prefs.getBoolean(Constants.PREF_ACCEPT, false)
        val fontSize: Float = prefs.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            prefs.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        binding.switchAccept.isChecked = acceptLegal
        binding.switchAccept.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (!isChecked) {
                showConfirm()
            }
        }

        binding.textLegal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        binding.textLegal.typeface = tf
        binding.textAgree.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        binding.textAgree.typeface = tf
        binding.textContacto.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        binding.textContacto.typeface = tf
        binding.textLegal.movementMethod = LinkMovementMethod.getInstance()

        val args: FileFragmentArgs by navArgs()

        val fileRequest =
            FileRequest(listOf(args.rawPath), 1, 6, isNightMode, isVoiceOn = false, false)
        //binding.progressBar.visibility = View.VISIBLE

        mViewModel.loadData(fileRequest)
        fetchData()

        binding.btnEmail.setOnClickListener {
            val subject = String.format(
                Locale.getDefault(), "Dudas " +
                        "Política Privacidad y/o " +
                        "Términos y Condiciones Liturgia+ v. %d", Constants.VERSION_CODE
            )
            composeEmail(arrayOf(Configuration.MY_EMAIL), subject)
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is FileViewModel.FileUiState.Loaded -> onLoaded(state.itemState)
                        is FileViewModel.FileUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(fileItemUiState: FileItemUiState) {
        fileItemUiState.run {
            allData.forEach {
                binding.textLegal.text = it.text
                setAcceptText(it.fileName)
            }
        }
        binding.progressBar.visibility = View.GONE
        binding.btnEmail.visibility = View.VISIBLE
        binding.bottomLayout.visibility = View.VISIBLE
    }


    private fun showLoading() {
        //val msgNoData = activity?.resources?.getString(R.string.err_no_data)
        binding.textLegal.text = "Cargando datos..."
    }

    private fun showError(stringRes: String) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    @Suppress("unused")
    private fun saveHtmlFile(html: String) {
        val path = requireActivity().getExternalFilesDir(null)!!.absolutePath
        var fileName =
            DateFormat.format("dd_MM_yyyy_hh_mm_ss", System.currentTimeMillis()).toString()
        fileName = "$fileName.html"
        val file = File(path, fileName)
        try {
            val out = FileOutputStream(file)
            val data = html.toByteArray()
            out.write(data)
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setAcceptText(fileName: String) {
        var acceptText: String
        if (fileName == FILE_TERMS) {
            acceptText =
                if (acceptLegal) activity?.resources!!.getString(R.string.privacy_agree) else activity?.resources!!.getString(
                    R.string.privacy_disagree
                )
            binding.textAgree.text = acceptText
        }
        if (fileName == FILE_PRIVACY) {
            acceptText =
                if (acceptLegal) activity?.resources!!.getString(R.string.privacy_agree) else activity?.resources!!.getString(
                    R.string.privacy_disagree
                )
            binding.textAgree.text = acceptText

        }
    }

    private fun showConfirm() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireActivity())
        materialAlertDialogBuilder.setTitle(Constants.DIALOG_LEGAL_TITLE)
        materialAlertDialogBuilder.setMessage(Constants.DIALOG_LEGAL_BODY)
        materialAlertDialogBuilder.setCancelable(false)
        materialAlertDialogBuilder.setPositiveButton(
            Constants.DIALOG_LEGAL_OK
        ) { _: DialogInterface?, _: Int -> closeApp() }
        materialAlertDialogBuilder.setNegativeButton(
            Constants.DIALOG_LEGAL_CANCEL
        ) { _: DialogInterface?, _: Int -> updateSwitch() }
        materialAlertDialogBuilder.show()
    }

    private fun closeApp() {
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean(Constants.PREF_ACCEPT, false)
        editor.apply()
        requireActivity().finishAndRemoveTask()
    }

    private fun updateSwitch() {
        binding.switchAccept.isChecked = true
    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, "Plantea a continuación tu duda: \n\n")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
        requireActivity().onBackPressedDispatcher
    }

    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}