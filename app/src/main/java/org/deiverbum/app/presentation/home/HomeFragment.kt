package org.deiverbum.app.presentation.home

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentHomeBinding
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.presentation.home.adapter.HomeAdapter
import org.deiverbum.app.presentation.home.adapter.HomeItem
import org.deiverbum.app.util.Utils

/**
 * Este fragmento es el primero que se abre en la aplicación. Contiene un `RecyclerView` con todas las opciones
 * disponibles para el usuario.
 *
 * En las versiones anteriores estos elementos se presentaban
 * directamente desde `MainActivity`.
 * Con la implementación de fragmentos adaptamos al código al nuevo
 * estándar conocido como Componentes de Navegación, combinándolo con
 * los Componentes de Arquitectura, creando un código mucho más ligero
 * y mejor organizado.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var mList: List<HomeItem>

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
        val theme = prefs.getString("theme", "1")
        prepareItems(theme!!)
    }

    private fun pickOutDate() {
        val bundle = arguments
        val todayDate: String = if (bundle != null) {
            if (bundle.getString("FECHA") == null) Utils.getHoy() else bundle.getString("FECHA")!!
        } else {
            Utils.getHoy()
        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle =
            Utils.getTitleDate(todayDate)
    }

    private fun prepareItems(theme:String) {
        mList = ArrayList()
        //var adapterBgColor: Int

        when (theme) {
            "1" -> {
                //adapterBgColor=R.color.transparent
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
                        R.color.colorBackgroundMain
                    )
                )

                homeAdapter= HomeAdapter(mList, Color.LTGRAY)

            }
            "2" -> {
                //adapterBgColor=R.color.transparent

                val colorUnique =
                    ContextCompat.getColor(requireActivity(), R.color.black)
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

            }
            else -> {
                //adapterBgColor=R.color.transparent

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

    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            layoutManager = GridLayoutManager(context,3)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }







}