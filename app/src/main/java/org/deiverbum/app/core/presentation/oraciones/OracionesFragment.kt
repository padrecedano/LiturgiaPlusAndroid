package org.deiverbum.app.core.presentation.oraciones

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.core.presentation.base.BaseHomeFragment
import org.deiverbum.app.core.presentation.oraciones.adapter.OracionItem
import org.deiverbum.app.core.presentation.oraciones.adapter.OracionesAdapter
import org.deiverbum.app.databinding.FragmentOracionesBinding
import org.deiverbum.app.util.Constants.FILE_ANGELUS
import org.deiverbum.app.util.Constants.FILE_LITANIES
import org.deiverbum.app.util.Constants.FILE_REGINA
import org.deiverbum.app.util.Constants.FILE_ROSARY
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2003
import org.deiverbum.app.util.Constants.FILE_VIA_CRUCIS_2005


@AndroidEntryPoint
class OracionesFragment : BaseHomeFragment<FragmentOracionesBinding>() {
    private lateinit var mAdapter: OracionesAdapter
    private lateinit var mList: List<OracionItem>

    override fun constructViewBinding(): ViewBinding =
        FragmentOracionesBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        prepareItems()
        initUi()
    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.subtitle = ""
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun prepareItems() {
        mList = ArrayList()
        mList = listOf(
            OracionItem(
                1,
                "Misterios Gloriosos",
                "Domingos y Miércoles",
                R.id.nav_rosario,
                FILE_ROSARY
            ),
            OracionItem(
                2,
                "Misterios Gozosos",
                "Lunes y Sábados",
                R.id.nav_rosario,
                FILE_ROSARY
            ),
            OracionItem(
                3,
                "Misterios Dolorosos",
                "Martes y Viernes",
                R.id.nav_rosario,
                FILE_ROSARY
            ),
            OracionItem(4, "Misterios Luminosos", "Jueves", R.id.nav_rosario, "raw/rosario.json"),
            OracionItem(
                5,
                "Letanías Lauretanas",
                "Solamente las Letanías",
                R.id.nav_letanias,
                FILE_LITANIES
            ),
            OracionItem(6, "Ángelus", "Excepto Pascua", R.id.nav_angelus, FILE_ANGELUS),
            OracionItem(7, "Regina Coeli", "Tiempo de Pascua", R.id.nav_regina, FILE_REGINA),
            OracionItem(
                8,
                "Via Crucis 2003",
                "Juan Pablo II",
                R.id.nav_viacrucis2003,
                FILE_VIA_CRUCIS_2003
            ),
            OracionItem(
                9,
                "Via Crucis 2005",
                "Benedicto XVI",
                R.id.nav_viacrucis2005,
                FILE_VIA_CRUCIS_2005
            )
        )

        mAdapter = OracionesAdapter(mList)
    }
}