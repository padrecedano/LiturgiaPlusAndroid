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
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants.*
import org.deiverbum.app.utils.Utils

/**
 *
 * Fragmento que muestra el diálogo inicial para la Aceptación de la Política de Privacidad y los Términos y Condiciones de Uso.
 *
 * Para mostrar el texto tanto de la Política de Privacidad como de los Términos y Condiciones el fragmento envía una [FileRequest] a [FileViewModel] pidiendo el contenido de los archivos respectivos. Y muestra también el botón de aceptación.
 *
 * Desde el evento de escucha del botón se lanza la sincronización, siguiendo los criterios siguientes:
 *
 * 1. Cuando el usuario pulse en el botón `Aceptar` se verificará el valor de la entrada [PREF_ACCEPT] de Shared Preferences. Si es `false` se lanzará una sincronización llamando a [SyncViewModel.launchSync].
 *
 * 2. Luego se observará el estado de la sincronización llamando a [fetchDataSync] y posteriormente a [onLoadedSync]. Si el valor del objeto [SyncResponse] devuelto viene de un [Source.NETWORK] y tiene datos,
 * el valor de la preferencia [PREF_INITIAL_SYNC] se establecerá a `true`. Si por algún error la sincronización inicial no se realiza, el valor de la preferencia [PREF_INITIAL_SYNC] permanecerá en `false` y desde el repositorio
 * se intentará obtener provisionalmente las fechas de los próximos días desde la [Source.FIREBASE].

 * 3. Desde el repositorio que llama [SyncViewModel] quedará iniciado [TodayWorker], quien se ocupará de futuras sincornizaciones.
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
    private var hasInitialSync:Boolean=false
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

    @Suppress("KotlinConstantConditions")
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
        fetchFileData()

        val sb = SpannableStringBuilder()
        sb.append(Utils.toH2Red(requireActivity().resources.getString(R.string.title_acceptance)))
        sb.append(LS2)
        sb.append(requireActivity().resources.getString(R.string.accept_warning))
        textFinal.setText(sb, TextView.BufferType.SPANNABLE)


        binding.btnEmail.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean(PREF_ACCEPT, true).apply()
            hasInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
            if (!hasInitialSync) {
                val syncRequest = SyncRequest(hasInitialSync,0,false)
                lifecycleScope.launch(Dispatchers.IO) {
                    syncViewModel.launchSync(syncRequest)
                    fetchDataSync()
                }
            }else{
                dismiss()
            }
        }
    }

    private fun fetchFileData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is FileViewModel.FileUiState.Loaded -> onFileLoaded(state.itemState)
                        is FileViewModel.FileUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }


    private fun onFileLoaded(fileItemUiState: FileItemUiState) {
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
            val syncResponse=syncItemUiState.syncResponse
            if(syncResponse.syncStatus.source==Source.NETWORK && syncResponse.allToday.isNotEmpty()){
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
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