package org.deiverbum.app.presentation.sync

import android.content.SharedPreferences
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentSyncBinding
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Constants.*
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.utils.ZoomTextView
import timber.log.Timber
import java.util.*
import java.util.concurrent.ExecutionException

/**
 * Este fragmento maneja la lógica de Sincronización, definida del siguiente modo:
 *
 * 1. Cuando se instala por primera vez la aplicación, desde [org.deiverbum.app.presentation.legal.AcceptanceFragmentDialog]:
 *     - *a*. Se invocará al método [SyncViewModel.launchSync] cuando el usuario
 *          pulse en el botón de aceptación.
 *     - *b*. Seguidamente se llamará al método [AcceptanceFragmentDialog.fetchDataSync] que observa el resultado
 *          de la sincronización en [SyncViewModel].
 *     - *c*. En el método [AcceptanceFragmentDialog.onLoadedSync] se verificará,
 *          si la fuente de sincronización es el servidor remoto y si hay datos,
 *          en cuyo caso se creará una entrada en SharedPreferences
 *          con clave [Constants.PREF_INITIAL_SYNC] y valor true.
 *          Esto indicará que ya ocurrió una sincronización inicial correctamente.
 *          Si ha ocurrido algún error y no se sincronizaron los datos desde el
 *          servidor remoto, el código deberá insertar en la base de datos el
 *          contenido de una semana, obtenido de Firebase. Para ello se llamará al
 *          método [org.deiverbum.app.data.source.firebase.FirebaseSyncEntityData.getSync].
 *          En este caso no se indicará en las preferencias que hay una sincronización iniciada.
 *
 * 2. En lo adelante, cada vez que se inicie la aplicación, se verificará desde el método [HomeFragment.setConfiguration()] el estado de [PREF_INITIAL_SYNC] en SharedPreferences.
 *
 *    Si es false:
 *
 *    - *a*. Se llamará a [SyncViewModel.launchSync].
 *
 *    - *b*. Se observará a [getInitialSyncStatus] y si devuelve un valor mayor que `0`
 *         se actualizará la entrada [PREF_INITIAL_SYNC] de SharedPreferences a `true`.
 *
 * 3. En el repositorio de sincronización existe también un método getFromFirebase
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
        setMenu()
         hasInitial = prefs.getBoolean(Constants.PREF_INITIAL_SYNC, false)
        val syncRequest = SyncRequest(hasInitial,isWorkScheduled=isWorkScheduled)
        //Timber.d(syncRequest.isInitial.toString())
        mViewModel.launchSync(syncRequest)
        fetchData()

        getViewBinding().include.btnEmail.setOnClickListener {
            val editor = prefs.edit()
            //editor.putBoolean(PREF_ACCEPT, true).apply()
            val hasInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
            //val syncRequest = SyncRequest(hasInitialSync,0,false)
            val syncRequest = SyncRequest(hasInitialSync,isWorkScheduled=isWorkScheduled)
            //Timber.d(syncRequest.isInitial.toString())
            mViewModel.launchSync(syncRequest)
            fetchData()
        }

    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        navController.popBackStack()
                        true
                    }

                    else -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        NavigationUI.onNavDestinationSelected(menuItem, navController)
                    }
                }

            }
        }, viewLifecycleOwner)
    }

    private fun setConfiguration() {
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



    //syncRequest =
        //SyncRequest(pickOutDate(), 1, isNightMode(), false)

        //pickOutDate()
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
            var syncResponse=syncItemUiState.syncResponse
            syncResponse.syncStatus.lastYearCleaned=prefs.getInt(PREF_LAST_YEAR_CLEANED,0)
            //Timber.d(prefs.getInt(PREF_LAST_YEAR_CLEANED,0).toString())
if(syncResponse.syncStatus.source==Source.LOCAL && !hasInitial){
    prefs.edit().putBoolean(PREF_INITIAL_SYNC, true).apply()
}
            mTextVieww.text = Utils.fromHtml(syncResponse.syncStatus.getAll(isNightMode()))//.dataForView
            if (!isWorkScheduled) {
                getViewBinding().include.btnEmail.visibility = View.VISIBLE
                getViewBinding().include.btnEmail.setIconResource(R.drawable.ic_refresh_black_24dp)
                getViewBinding().include.btnEmail.text = SYNC_LABEL
                getViewBinding().include.tvBottom.text =
                    syncResponse.syncStatus.getNotWorkerMessage(isNightMode())
            } else {
                getViewBinding().include.tvBottom.text = syncResponse.syncStatus.getWorkerMessage()
            }
        }
    }

    private fun showLoading() {
        mTextVieww.text = Constants.PACIENCIA

    }

    private fun showError(stringRes: String) {
        mTextVieww.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }


    /*override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        //_binding = null
    }*/
}

/*Fragment() {
    private var mViewModel: SyncViewModelBis? = null
    private var binding: FragmentSyncBinding? = null
    private var mTextView: ZoomTextView? = null
    private var progressBar: ProgressBar? = null
    private var mButton: ExtendedFloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentSyncBinding.inflate(inflater, container, false)
        inflater.inflate(R.layout.seekbar, container, false)
        val root: View = binding!!.root
        setConfiguration()
        return root
    }

    private fun setConfiguration() {
        mViewModel = ViewModelProvider(this).get(SyncViewModelBis::class.java)
        mTextView = binding!!.include.tvZoomable
        progressBar = binding!!.progressBar
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val fontSize = prefs.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            prefs.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView!!.typeface = tf
        mButton = binding!!.include.btnEmail
        mButton!!.visibility = View.GONE
        mButton!!.setOnClickListener { v: View? ->
            mViewModel!!.launchSyncWorker()
            mButton!!.visibility = View.GONE
            observeData()
        }
        if (!isWorkScheduled) {
            progressBar!!.visibility = View.GONE
            mTextView!!.text = Utils.fromHtml(
                SyncStatus().getLastUpdate(
                    isNightMode
                )
            )
            mButton!!.setIconResource(R.drawable.ic_refresh_black_24dp)
            mButton!!.text = Constants.SYNC_LABEL
            mButton!!.visibility = View.VISIBLE
        } else {
            observeData()
        }
    }

    fun observeData() {
        mTextView!!.text = Constants.PACIENCIA
        mViewModel!!.observable.observe(
            viewLifecycleOwner
        ) { data: SyncStatus? ->
            progressBar!!.visibility = View.GONE
            if (data != null) {
                mTextView!!.text = Utils.fromHtml(data.getAll(isNightMode))
            } else {
                mTextView!!.text = Utils.fromHtml(
                    SyncStatus().getLastUpdate(
                        isNightMode
                    )
                )
            }
        }
    }


    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        binding = null
    }

    companion object {
        var mActionMode: ActionMode? = null
    }
}*/