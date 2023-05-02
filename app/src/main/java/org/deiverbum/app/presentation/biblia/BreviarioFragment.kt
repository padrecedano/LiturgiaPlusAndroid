package org.deiverbum.app.presentation.breviario

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.presentation.breviario.adapter.BreviarioItem
import org.deiverbum.app.databinding.FragmentBreviarioBinding
import org.deiverbum.app.presentation.base.BaseHomeFragment
import org.deiverbum.app.presentation.breviario.adapter.BreviarioAdapter


@AndroidEntryPoint
class BibliaFragment : BaseHomeFragment<FragmentBreviarioBinding>() {
    private lateinit var breviarioAdapter: BreviarioAdapter
    private lateinit var mList: List<BreviarioItem>

    override fun constructViewBinding(): ViewBinding = FragmentBreviarioBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        prepareItems()
        initUi()
    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            layoutManager = GridLayoutManager(context,3)
            setHasFixedSize(true)
            adapter = breviarioAdapter
        }
    }

    private fun prepareItems() {
        val colorGrupo1 = ContextCompat.getColor(requireActivity(), R.color.color_fondo_grupo1)
        val colorGrupo2 = ContextCompat.getColor(
            requireActivity(), R.color.color_fondo_grupo2
        )
        mList = ArrayList()

        mList = listOf(
            BreviarioItem("Oficio+Laudes", colorGrupo1, "M", R.id.nav_mixto),
            BreviarioItem("Oficio", colorGrupo1, "O", R.id.nav_oficio),
            BreviarioItem("Laudes", colorGrupo1, "L", R.id.nav_laudes),
            BreviarioItem("Tercia", colorGrupo2, "T", R.id.nav_tercia),
            BreviarioItem("Sexta", colorGrupo2, "S", R.id.nav_sexta),
            BreviarioItem("Nona", colorGrupo2, "N", R.id.nav_nona),
            BreviarioItem("Vísperas", colorGrupo1, "V", R.id.nav_visperas),
            BreviarioItem("Completas", colorGrupo1, "C", R.id.nav_completas),
            BreviarioItem("Más...", colorGrupo1, "+", R.id.nav_breviario_mas)
        )
        breviarioAdapter= BreviarioAdapter(mList)
    }
}