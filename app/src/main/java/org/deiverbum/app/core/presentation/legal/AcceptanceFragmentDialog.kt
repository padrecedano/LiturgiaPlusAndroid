package org.deiverbum.app.core.presentation.legal

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.presentation.file.FileItemUiState
import org.deiverbum.app.core.presentation.file.FileViewModel
import org.deiverbum.app.core.presentation.sync.InitialSyncViewModel
import org.deiverbum.app.core.presentation.sync.SyncItemUiState
import org.deiverbum.app.core.presentation.sync.SyncItemUiStateNew
import org.deiverbum.app.core.presentation.sync.SyncViewModel
import org.deiverbum.app.databinding.FragmentAcceptanceBinding
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants.FILE_PRIVACY
import org.deiverbum.app.util.Constants.FILE_TERMS
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Constants.PREF_ACCEPT
import org.deiverbum.app.util.Constants.PREF_INITIAL_SYNC
import org.deiverbum.app.util.Source
import org.deiverbum.app.util.Utils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * Fragmento que muestra el diálogo inicial para la Aceptación de la Política de Privacidad y los Términos y Condiciones de Uso.
 *
 * Para mostrar el texto tanto de la Política de Privacidad como de los Términos y Condiciones el fragmento envía una [FileRequest] a [FileViewModel] pidiendo el contenido de los archivos respectivos. Y muestra también el botón de aceptación.
 *
 * Desde el evento de escucha del botón se lanza la sincronización, siguiendo los criterios siguientes:

 * * 1 . Cuando se instala por primera vez la aplicación, desde [AcceptanceFragmentDialog][org.deiverbum.app.core.presentation.legal.AcceptanceFragmentDialog]:
 *   * *a)*. Se invocará al método [InitialSyncViewModel.launchSync] cuando el usuario pulse en el botón de aceptación.
 *   * *b)*. Seguidamente se llamará al método [fetchDataSync]
 *     que observa el resultado de la sincronización en [InitialSyncViewModel].
 *   * *c)*. En el método [onLoadedSync] se verificará si la fuente de sincronización es el servidor remoto ([Source.NETWORK]) y si hay datos, en cuyo caso se creará una entrada en SharedPreferences con clave [PREF_INITIAL_SYNC] y valor `true`.
 *   Esto indicará que ya ocurrió una sincronización inicial correctamente. Si ha ocurrido algún error y no se sincronizaron los datos desde el servidor remoto, el código deberá insertar en la base de datos el contenido de una semana, obtenido de Firebase. Para ello se llamará al método [org.deiverbum.app.core.data.source.firebase.FirebaseSyncEntityData.getSync]. En este caso no se indicará en las preferencias que hay una sincronización iniciada.
 * * 2 . En lo adelante, cada vez que se inicie la aplicación, se verificará desde el método [org.deiverbum.app.core.presentation.home.HomeFragment.setConfiguration] el estado de [PREF_INITIAL_SYNC] en SharedPreferences.
 *
 *    Si es false:
 *   * *a*. Se llamará a [InitialSyncViewModel.launchSync].
 *
 *   * *b*. Se observará a [getInitialSyncStatus] y si devuelve un valor mayor que `0`
 *         se actualizará la entrada [PREF_INITIAL_SYNC] de SharedPreferences a `true`.
 *
 * * 3 . En el repositorio de sincronización existe también un método getFromFirebase
 *        el cual sirve para buscar las fechas de los próximos siete días en Firebase.
 *        Es un método alternativo, para usar en caso de que la llamada a [SyncViewModel.launchSync] falle
 *        por algún motivo: el servidor está offline en ese momento, u otro.
 *
 *  * 4 . Desde el repositorio que llama [SyncViewModel] quedará iniciado [TodayWorker][org.deiverbum.app.workers.TodayWorker], quien se ocupará de futuras sincronizaciones.
 *
 * 1. Cuando el usuario pulse en el botón `Aceptar` se verificará el valor de la entrada [PREF_ACCEPT] de Shared Preferences. Si es `false` se lanzará una sincronización llamando a [SyncViewModel.launchSync].
 *
 * 2. Luego se observará el estado de la sincronización llamando a [fetchDataSync] y posteriormente a [onLoadedSync]. Si el valor del objeto `SyncResponse` devuelto viene de un [Source.NETWORK] y tiene datos,
 * el valor de la preferencia [PREF_INITIAL_SYNC] se establecerá a `true`. Si por algún error la sincronización inicial no se realiza, el valor de la preferencia [PREF_INITIAL_SYNC] permanecerá en `false` y desde el repositorio
 * se intentará obtener provisionalmente las fechas de los próximos días desde la [Source.FIREBASE].

 * 3. Desde el repositorio que llama [SyncViewModel] quedará iniciado [TodayWorker][org.deiverbum.app.workers.TodayWorker], quien se ocupará de futuras sincornizaciones.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@AndroidEntryPoint
class AcceptanceFragmentDialog : DialogFragment() {
    private val mViewModel: FileViewModel by viewModels()
    private val initialSyncViewModel: InitialSyncViewModel by viewModels()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAcceptanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        prepareView()
        return root
    }

    @Suppress("KotlinConstantConditions")
    private fun prepareView() {
        ColorUtils.isNightMode = isNightMode
        val fontSize = prefs.getString("font_size", "18")!!.toFloat()
        val textInitial = binding.textInitial
        textPrivacy = binding.textPrivacy
        textTerms = binding.textTerms
        val textFinal = binding.textFinal
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textPrivacy!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textTerms!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textPrivacy!!.movementMethod = LinkMovementMethod.getInstance()
        textTerms!!.movementMethod = LinkMovementMethod.getInstance()
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
            binding.btnEmail.isClickable = false
            binding.btnEmail.isEnabled = false
            val editor = prefs.edit()
            editor.putBoolean(PREF_ACCEPT, true).apply()
            //hasInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
            //if (!hasInitialSync) {
            val systemTime = System.currentTimeMillis()
            val sdfY = SimpleDateFormat("yyyy", Locale("es", "ES"))
            val theDate = Date(systemTime)
            val currentYear = sdfY.format(theDate).toInt()
            val syncRequest = SyncRequest(hasInitialSync, currentYear - 1, false)
            lifecycleScope.launch(Dispatchers.IO) {
                initialSyncViewModel.launchSync(syncRequest)
                fetchDataSync()
            }
            //} else {
            //dismiss()
            //}
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
                initialSyncViewModel.uiState.collect { state ->
                    when (state) {
                        is InitialSyncViewModel.SyncUiState.Loaded -> onLoadedSync(state.itemState)
                        is InitialSyncViewModel.SyncUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private suspend fun onLoadedSyncc(syncItemUiState: SyncItemUiStateNew) {
        syncItemUiState.run {
            syncResponse.syncStatus.collect { it ->
                //println(it.lastUpdate)
                //println(it.tableName)
                //it.isWorkScheduled=isWorkScheduled
                if (it.source == Source.NETWORK && syncResponse.allToday.isNotEmpty()) {
                    prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
                }
            }
            dismiss()
        }
    }

    private fun onLoadedSync(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            val syncResponse = syncItemUiState.syncResponse
            if (syncResponse.syncStatus.source == Source.NETWORK && syncResponse.allToday.isNotEmpty()) {
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
            dismiss()
        }
    }

    private fun showLoading() {
    }

    private fun showError(stringRes: String) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
        dismiss()
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