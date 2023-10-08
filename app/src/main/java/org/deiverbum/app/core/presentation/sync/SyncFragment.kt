package org.deiverbum.app.core.presentation.sync

import android.content.SharedPreferences
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.presentation.base.BaseFragment
import org.deiverbum.app.databinding.FragmentSyncBinding
import org.deiverbum.app.util.Constants.PACIENCIA
import org.deiverbum.app.util.Constants.PREF_INITIAL_SYNC
import org.deiverbum.app.util.Constants.PREF_LAST_YEAR_CLEANED
import org.deiverbum.app.util.Source
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.ZoomTextView
import java.util.Locale

/**
 * Este fragmento maneja la lógica de Sincronización, definida del siguiente modo:
 *
 * * 1\. Cuando se instala por primera vez la aplicación, desde [AcceptanceFragmentDialog][org.deiverbum.app.core.presentation.legal.AcceptanceFragmentDialog]:
 *     * *a)*. Se invocará al método [SyncViewModel.launchSync] cuando el usuario pulse en el botón de aceptación.
 *     * *b)*. Seguidamente se llamará al método [fetchDataSync] [org.deiverbum.app.core.presentation.legal.AcceptanceFragmentDialog.fetchDataSync]
 *     que observa el resultado de la sincronización en [SyncViewModel].
 *     * *c)*. En el método [AcceptanceFragmentDialog.onLoadedSync][org.deiverbum.app.core.presentation.legal.AcceptanceFragmentDialog.onLoadedSync] se verificará si la fuente de sincronización es el servidor remoto ([Source.NETWORK][org.deiverbum.app.util.Source.NETWORK]) y si hay datos, en cuyo caso se creará una entrada en SharedPreferences con clave [PREF_INITIAL_SYNC] y valor `true`.
 *             Esto indicará que ya ocurrió una sincronización inicial correctamente. Si ha ocurrido algún error y no se sincronizaron los datos desde el servidor remoto, el código deberá insertar en la base de datos el contenido de una semana, obtenido de Firebase. Para ello se llamará al método [FirebaseSyncEntityData.getSync][org.deiverbum.app.core.data.source.firebase.FirebaseSyncEntityData.getSync]. En este caso no se indicará en las preferencias que hay una sincronización iniciada.
 * * 2\. En lo adelante, cada vez que se inicie la aplicación, se verificará desde el método [HomeFragment.setConfiguration()] el estado de [PREF_INITIAL_SYNC] en SharedPreferences.
 *
 *    Si es false:
 *
 *    * *a*. Se llamará a [SyncViewModel.launchSync].
 *
 *    * *b*. Se observará a [getInitialSyncStatus] y si devuelve un valor mayor que `0`
 *         se actualizará la entrada [PREF_INITIAL_SYNC] de SharedPreferences a `true`.
 *
 * * 3\. En el repositorio de sincronización existe también un método getFromFirebase
 *        el cual sirve para buscar las fechas de los próximos siete días en Firebase.
 *        Es un método alternativo, para usar en caso de que la llamada a initialSync falle
 *        por algún motivo: el servidor está offline en ese momento, u otro.
 */
@AndroidEntryPoint
class SyncFragment : BaseFragment<FragmentSyncBinding>() {
    private val mViewModel: SyncViewModel by viewModels()
    private lateinit var mTextVieww: ZoomTextView
    private var hasInitial: Boolean = false

    private var progressBar: ProgressBar? = null
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }

    override fun constructViewBinding(): ViewBinding = FragmentSyncBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        setConfiguration()
        hasInitial = prefs.getBoolean(PREF_INITIAL_SYNC, false)
        val syncRequest = SyncRequest(hasInitial, isWorkScheduled = isWorkScheduled)
        mViewModel.launchSync(syncRequest)
        fetchData()
        /*
                getViewBinding().include.btnEmail.setOnClickListener {
                    mViewModel.launchSync(syncRequest)
                    fetchData()
                }
        */
    }

    private fun setConfiguration() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
        mTextVieww = getViewBinding().include.tvZoomable
        progressBar = getViewBinding().progressBar
        val fontSize = prefs.getString("font_size", "18")?.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            prefs.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextVieww.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize!!)
        mTextVieww.typeface = tf
        getViewBinding().include.tvBottom.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        getViewBinding().include.tvBottom.typeface = tf
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoaded(state.itemState)
                        is SyncViewModel.SyncUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            val syncResponse = syncItemUiState.syncResponse
            syncResponse.syncStatus.lastYearCleaned = prefs.getInt(PREF_LAST_YEAR_CLEANED, 0)
            if (syncResponse.syncStatus.source == Source.NETWORK && !hasInitial) {
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
            mTextVieww.text =
                Utils.fromHtml(syncResponse.syncStatus.getAll(isNightMode()))//.dataForView
            if (!isWorkScheduled) {
                getViewBinding().include.tvBottom.text =
                    syncResponse.syncStatus.getNotWorkerMessage(isNightMode())
            } else {
                getViewBinding().include.tvBottom.text = syncResponse.syncStatus.getWorkerMessage()
            }
        }
    }

    private suspend fun onLoadedNew(syncItemUiState: SyncItemUiStateNew) {
        syncItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            syncResponse.syncStatus.collect { it ->
                it.lastYearCleaned = prefs.getInt(PREF_LAST_YEAR_CLEANED, 0)
                if (it.source == Source.NETWORK && !hasInitial) {
                    prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
                }
                //println(it.lastUpdate)
                //println(it.tableName)
                //it.isWorkScheduled=isWorkScheduled
                mTextVieww.text =
                    Utils.fromHtml(it.getAll(isNightMode()))//.dataForView

                if (!isWorkScheduled) {
                    getViewBinding().include.tvBottom.text =
                        it.getNotWorkerMessage(isNightMode())
                } else {
                    getViewBinding().include.tvBottom.text = it.getWorkerMessage()
                }

            }


            /*syncItemUiState.syncResponse.collect()
            val syncResponse = syncItemUiState.syncResponse
            mTextVieww.text =
                Utils.fromHtml(syncItemUiState.getAll(isNightMode()))
            Log.d("bbb",syncResponse.syncStatus.tableName)*/
            /*
                        syncResponse.syncStatus.collect { it ->
                            println(it.lastUpdate)
                            println(it.tableName)
                            //it.isWorkScheduled=isWorkScheduled
                            mTextVieww.text =
                                Utils.fromHtml(it.getAll(isNightMode()))//.dataForView
                            if (!it.isWorkScheduled) {
                                getViewBinding().include.tvBottom.text =
                                    it.getNotWorkerMessage(isNightMode())
                            } else {
                                getViewBinding().include.tvBottom.text = it.getWorkerMessage()
                            }
                            //getViewBinding().include.tvBottom.text =it.tableName+" "+it.lastUpdate
                            // Update View with the latest favorite news
                        }
            */

            //syncItemUiState.syncResponse.syncStatus.collect( { value -> println(value))
            //syncResponse.allToday
            /*
            syncResponse.syncStatus.lastYearCleaned = prefs.getInt(PREF_LAST_YEAR_CLEANED, 0)
            if (syncResponse.syncStatus.source == Source.NETWORK && !hasInitial) {
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
            }
            mTextVieww.text =
                Utils.fromHtml(syncResponse.syncStatus.getAll(isNightMode()))//.dataForView
            if (!isWorkScheduled) {
                getViewBinding().include.tvBottom.text =
                    syncResponse.syncStatus.getNotWorkerMessage(isNightMode())
            } else {
                getViewBinding().include.tvBottom.text = syncResponse.syncStatus.getWorkerMessage()
            }*/
        }
    }

    private fun showLoading() {
        mTextVieww.text = PACIENCIA
    }

    private fun showError(stringRes: String) {
        mTextVieww.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}