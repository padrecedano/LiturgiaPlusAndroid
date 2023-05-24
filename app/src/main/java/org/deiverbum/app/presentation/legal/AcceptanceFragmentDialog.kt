package org.deiverbum.app.presentation.legal

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentAcceptanceBinding
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.presentation.file.FileItemUiState
import org.deiverbum.app.presentation.file.FileViewModel
import org.deiverbum.app.presentation.sync.SyncItemUiState
import org.deiverbum.app.presentation.sync.SyncViewModel
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants.*
import org.deiverbum.app.utils.Utils
import timber.log.Timber

/**
 *
 * <p>
 *     Fragmento que muestra el diálogo inicial para la Aceptación
 *     de la Política de Privacidad y los Términos y Condiciones de Uso.
 * </p>
 * <p>
 *     1. Cuando el usuario pulse en el botón Aceptar la entrada `accept_terms`
 *     se Shared Preferences se establecerá a `true`. <br />
 *     2. Se verificará si no hay una sincronización inicial, en cuyo caso llamará a
 *     [SyncViewModel.initialSync()] para obtener dicha sincronización. <br />
 *     3. Desde [SyncViewModel] quedará iniciado [TodayWorker], quien se ocupará
 *     de futuras sincornizaciones.
 * </p>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@AndroidEntryPoint
class AcceptanceFragmentDialog : DialogFragment() {
    private val mViewModel: FileViewModel by viewModels()
    private val syncViewModel: SyncViewModel by viewModels()
    private var textPrivacy: TextView? = null
    private var textTerms: TextView? = null
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }
    private var _binding: FragmentAcceptanceBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }


    /*
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.accept_warning))
            .setPositiveButton(getString(R.string.ok)) { _,_ -> }
            .create()
*/




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        //mViewModel = ViewModelProvider(this)[FileViewModel::class.java]
        //syncViewModel = ViewModelProvider(this)[SyncViewModel::class.java]
        _binding = FragmentAcceptanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //val editor = prefs.edit()
        //editor.putBoolean("accept_terms", true).apply()
//dismiss()
        prepareView()
        //observeData()
        return root
    }

    private fun prepareView() {
        ColorUtils.isNightMode = isNightMode
        //val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        //val sp = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val fontSize = prefs.getString("font_size", "18")!!.toFloat()
        val textInitial = binding.textInitial
        textPrivacy = binding.textPrivacy
        textTerms = binding.textTerms
        val textFinal = binding.textFinal
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textPrivacy!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textTerms!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        //val button: Button = binding.btnEmail
        val ssb = SpannableStringBuilder()
        val intro = String.format(
            "%s %s%n%s",
            requireActivity().resources.getString(R.string.accept_intro),
            requireActivity().resources.getString(R.string.app_name),
            requireActivity().resources.getString(R.string.app_version_code_view)
        )
        ssb.append(Utils.toH3Red(intro))
        ssb.append(LS2)
        ssb.append(requireActivity().resources.getString(R.string.accept_info))
        textInitial.text = ssb

        val fileRequest = FileRequest(listOf(FILE_TERMS,FILE_PRIVACY), 1, 6, isNightMode, isVoiceOn = false, false)
        mViewModel.loadData(fileRequest)
        fetchData()

        val sb = SpannableStringBuilder()
        sb.append(Utils.toH2Red(requireActivity().resources.getString(R.string.title_acceptance)))
        sb.append(LS2)
        sb.append(requireActivity().resources.getString(R.string.accept_warning))
        textFinal.setText(sb, TextView.BufferType.SPANNABLE)


        binding.btnEmail.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean(PREF_ACCEPT, true).apply()
            val isInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
            if (!isInitialSync) {
                val syncRequest = SyncRequest(true)
                lifecycleScope.launch(Dispatchers.IO) {
                    syncViewModel.initialSync(syncRequest)
                    fetchDataSync()
                }
            }
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
            allData.forEach{
                if(it.fileName==FILE_TERMS){
                    binding.textTerms.text = it.text
                }
                if(it.fileName==FILE_PRIVACY){
                    binding.textPrivacy.text = it.text
                }
            }
            binding.btnEmail.visibility=View.VISIBLE
        }
    }

    private fun fetchDataSync() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoadedSync(state.itemState)
                        is SyncViewModel.SyncUiState.Error -> showError(state.message.toInt())
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoadedSync(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            if(syncResponse.syncStatus.status==1){
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
            //Timber.d(syncItemUiState.allData.dataForView.toString())
            dismiss()
        }
    }

    private fun showLoading() {
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }

    companion object {
        private const val TAG = "AcceptanceFragmentDialog"
        fun display(fragmentManager: FragmentManager?) {
            val acceptanceFragmentDialog = AcceptanceFragmentDialog()
            acceptanceFragmentDialog.isCancelable = false
            acceptanceFragmentDialog.show(fragmentManager!!, TAG)
        }
    }
}