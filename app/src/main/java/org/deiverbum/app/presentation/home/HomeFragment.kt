package org.deiverbum.app.presentation.home

import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentHomeBinding
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.presentation.home.adapter.HomeAdapter
import org.deiverbum.app.presentation.home.adapter.HomeItem
import org.deiverbum.app.presentation.sync.SyncItemUiState
import org.deiverbum.app.presentation.sync.SyncViewModel
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Constants.PREF_INITIAL_SYNC
import org.deiverbum.app.utils.Constants.PREF_LAST_YEAR_CLEANED
import org.deiverbum.app.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

/**
 * <p>
 * HomeFragment es el primer fragmento que se abre en la aplicación.
 * Contiene un <code>RecyclerView</code> con todas las opciones
 * disponibles para el usuario.
 * En las versiones anteriores estos elementos se presentaban
 * directamente desde <code>MainActivity</code>.
 * Con la implementación de fragmentos adaptamos al código al nuevo
 * estándar conocido como Componentes de Navegación, combinándolo con
 * los Componentes de Arquitectura, creando un código mucho más ligero
 * y mejor organizado.
 * </p>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var mList: List<HomeItem>

    private val homeViewModel: HomeViewModel by viewModels()
    private val syncViewModel: SyncViewModel by viewModels()
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }
    override fun constructViewBinding(): ViewBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        //homeViewModel.getData()
        pickOutDate()
        setConfiguration()
        //prepareItems("1")
        initUi()
    }

    private fun setConfiguration() {
        //val mViewModel = ViewModelProvider(this)[SyncViewModel::class.java]
        val isInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)
        val isAccept = prefs.getBoolean(Constants.PREF_ACCEPT, false)
        val theme = prefs.getString("theme", "1")
        if (!isInitialSync && isAccept) {
            syncViewModel.initialSync(SyncRequest(true))
            /*syncViewModel.initialSyncStatus.observe(
                viewLifecycleOwner
            ) { data: Int ->
                val isSuccess = data > 0
                prefs.edit().putBoolean(PREF_INITIAL_SYNC, isSuccess).apply()
            }*/
        }
        val dayNumber = Utils.getDay(Utils.getHoy()).toInt()
        if (dayNumber >= 30 || 1==1) {
            val lastYearCleaned = prefs.getInt(PREF_LAST_YEAR_CLEANED, 0)
            val systemTime = System.currentTimeMillis()
            val sdfY = SimpleDateFormat("yyyy", Locale("es", "ES"))
            val theDate = Date(systemTime)
            val currentYear = sdfY.format(theDate).toInt()
            if (lastYearCleaned == 0 || lastYearCleaned == currentYear - 1) {
                val sdfMd = SimpleDateFormat("MMdd", Locale("es", "ES"))
                val mmDD = sdfMd.format(theDate).toInt()
                if (mmDD >= 1225 || 1==1) {
                    syncViewModel.cleanUpYear(SyncRequest(false,currentYear - 1))
                    fetchData()
                    /*syncViewModel.yearClean.observe(
                        viewLifecycleOwner
                    ) { data: Int ->
                        if (data > 0) {
                            prefs.edit().putInt(PREF_LAST_YEAR_CLEANED, currentYear).apply()
                        }
                    }*/
                }
            }
        }
        prepareItems(theme!!)
    }

    private fun pickOutDate() {
        val bundle = arguments
        val todayDate: String
        todayDate = if (bundle != null) {
            if (bundle.getString("FECHA") == null) Utils.getHoy() else bundle.getString("FECHA")!!
        } else {
            Utils.getHoy()
        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        Objects.requireNonNull(actionBar)?.subtitle =
            Utils.getTitleDate(todayDate)
    }

    private fun prepareItems(theme:String) {
        mList = ArrayList()
        var adapterBgColor: Int

        if (theme == "1") {
            adapterBgColor=R.color.transparent
            val imageColor = ContextCompat.getColor(requireActivity(), R.color.colorMainIcon)
            val colorBreviario = ContextCompat.getColor(requireActivity(), R.color.colorBreviario)
            val colorMisa = ContextCompat.getColor(requireActivity(), R.color.colorMisa)
            val colorHomilias = ContextCompat.getColor(requireActivity(), R.color.colorHomilias)

            val colorLecturas =
                ContextCompat.getColor(requireActivity(), R.color.colorMain_lecturas_img)
            val colorEvangelio =
                ContextCompat.getColor(requireActivity(), R.color.colorMain_evangelio_img)
            val colorSantos = ContextCompat.getColor(requireActivity(), R.color.colorSantos)

            val colorCalendario = ContextCompat.getColor(requireActivity(), R.color.colorCalendario)
            val colorOraciones = ContextCompat.getColor(requireActivity(), R.color.colorOraciones)
            val colorMas = ContextCompat.getColor(requireActivity(), R.color.colorMain_img_mas)
            val colorBiblia = ContextCompat.getColor(requireActivity(), R.color.colorBiblia)
            val colorPadres = ContextCompat.getColor(requireActivity(), R.color.colorPadres)
            val colorSacramentos =
                ContextCompat.getColor(requireActivity(), R.color.colorSacramentos)

            mList = listOf(

                HomeItem(
                    "Breviario",
                    1,
                    R.drawable.ic_breviario,
                    colorBreviario,
                    R.id.nav_breviario,
                    imageColor
                ),
                HomeItem("Misa", 2, R.drawable.ic_misa, colorMisa, R.id.nav_misa, imageColor),
                HomeItem(
                    "Homilías",
                    3,
                    R.drawable.ic_homilias,
                    colorHomilias,
                    R.id.nav_homilias,
                    imageColor
                ),
                HomeItem(
                    "Santos",
                    4,
                    R.drawable.ic_santos,
                    colorSantos,
                    R.id.nav_santo,
                    imageColor
                ),
                HomeItem(
                    "Lecturas",
                    5,
                    R.drawable.ic_lecturas,
                    colorLecturas,
                    R.id.nav_lecturas,
                    imageColor
                ),
                HomeItem(
                    "Comentarios",
                    6,
                    R.drawable.ic_comentarios,
                    colorEvangelio,
                    R.id.nav_comentarios,
                    imageColor
                ),
                HomeItem(
                    "Calendario",
                    7,
                    R.drawable.ic_calendario,
                    colorCalendario,
                    R.id.nav_calendario,
                    imageColor
                ),
                HomeItem(
                    "Oraciones",
                    8,
                    R.drawable.ic_oraciones,
                    colorOraciones,
                    R.id.nav_oraciones,
                    imageColor
                ),
                HomeItem(
                    "Biblia",
                    9,
                    R.drawable.ic_biblia,
                    colorBiblia,
                    R.id.nav_biblia,
                    imageColor
                ),
                HomeItem(
                    "Patrística",
                    10,
                    R.drawable.ic_patristica,
                    colorMas,
                    R.id.nav_patristica,
                    imageColor
                ),
                HomeItem(
                    "Sacramentos",
                    11,
                    R.drawable.ic_sacramentos,
                    colorSacramentos,
                    R.id.nav_sacramentos,
                    imageColor
                ),
                HomeItem("Más...", 12, R.drawable.ic_mas, colorPadres, R.id.nav_mas, imageColor)
            )

            getViewBinding().homeParent.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorSurface
                )
            )

            homeAdapter= HomeAdapter(mList, Color.LTGRAY)

        } else if (theme == "2") {
            adapterBgColor=R.color.transparent

            val colorUnique =
                ContextCompat.getColor(requireActivity(), R.color.md_theme_dark_background)
            val imageColor = ContextCompat.getColor(requireActivity(), R.color.color_nav_start)
            getViewBinding().homeParent.setBackgroundColor(colorUnique)
            mList = listOf(
                HomeItem("Breviario", 1, R.drawable.ic_breviario, colorUnique, R.id.nav_breviario, imageColor),
                HomeItem("Misa", 2, R.drawable.ic_misa, colorUnique, R.id.nav_misa, imageColor),
                HomeItem("Homilías", 3, R.drawable.ic_homilias, colorUnique, R.id.nav_homilias, imageColor),
                HomeItem("Santos", 4, R.drawable.ic_santos, colorUnique, R.id.nav_santo, imageColor),
                HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorUnique, R.id.nav_lecturas, imageColor),
                HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorUnique, R.id.nav_comentarios, imageColor),
                HomeItem("Calendario", 7, R.drawable.ic_calendario, colorUnique, R.id.nav_calendario, imageColor),
                HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorUnique, R.id.nav_oraciones, imageColor),
                HomeItem("Biblia", 9, R.drawable.ic_biblia, colorUnique, R.id.nav_biblia, imageColor),
                HomeItem("Patrística", 10, R.drawable.ic_patristica, colorUnique, R.id.nav_patristica, imageColor),
                HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorUnique, R.id.nav_sacramentos, imageColor),
                HomeItem("Más...", 12, R.drawable.ic_mas, colorUnique, R.id.nav_mas, imageColor))
            homeAdapter= HomeAdapter(mList,Color.LTGRAY)

        } else {
            adapterBgColor=R.color.transparent

            val colorUnique = ContextCompat.getColor(requireActivity(), R.color.color_nav_start)
            val imageColor = ContextCompat.getColor(requireActivity(), R.color.colorBreviario)
            mList = listOf(

            HomeItem("Breviario", 1, R.drawable.ic_breviario, colorUnique, R.id.nav_breviario, imageColor),
            HomeItem("Misa", 2, R.drawable.ic_misa, colorUnique, R.id.nav_misa, imageColor),
            HomeItem("Homilías", 3, R.drawable.ic_homilias, colorUnique, R.id.nav_homilias, imageColor),
            HomeItem("Santos", 4, R.drawable.ic_santos, colorUnique, R.id.nav_santo, imageColor),
            HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorUnique, R.id.nav_lecturas, imageColor),
            HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorUnique, R.id.nav_comentarios, imageColor),
            HomeItem("Calendario", 7, R.drawable.ic_calendario, colorUnique, R.id.nav_calendario, imageColor),
            HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorUnique, R.id.nav_oraciones, imageColor),
            HomeItem("Biblia", 9, R.drawable.ic_biblia, colorUnique, R.id.nav_biblia, imageColor),
            HomeItem("Patrística", 10, R.drawable.ic_patristica, colorUnique, R.id.nav_patristica, imageColor),
            HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorUnique, R.id.nav_sacramentos, imageColor),
            HomeItem("Más...", 12, R.drawable.ic_mas, colorUnique, R.id.nav_mas, imageColor))
            getViewBinding().homeParent.setBackgroundColor(colorUnique)
            homeAdapter= HomeAdapter(mList,Color.LTGRAY)

        }

    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            layoutManager = GridLayoutManager(context,3)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncViewModel.uiState.collect { state ->
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
            //getViewBinding().progressBar.visibility = View.GONE
            //mTextVieww.text = Utils.fromHtml(allData.dataForView.toString())//.dataForView
            if (syncResponse.syncStatus.lastYearCleaned!=0) {
                prefs.edit().putInt(PREF_LAST_YEAR_CLEANED, syncResponse.syncStatus.lastYearCleaned).apply()

                //getViewBinding().include.btnEmail.visibility = View.VISIBLE
                //getViewBinding().include.btnEmail.setIconResource(R.drawable.ic_refresh_black_24dp)
                //getViewBinding().include.btnEmail.text = Constants.SYNC_LABEL
                //getViewBinding().include.tvBottom.text =
                //    allData.syncStatus.getNotWorkerMessage(isNightMode())
            } else {
                //getViewBinding().include.tvBottom.text = allData.syncStatus.getWorkerMessage()
            }
        }
    }

    private fun showLoading() {
        //mTextVieww.text = Constants.PACIENCIA

    }

    private fun showError(stringRes: String) {
        //mTextVieww.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }


}