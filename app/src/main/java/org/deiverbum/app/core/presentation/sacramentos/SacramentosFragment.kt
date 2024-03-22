package org.deiverbum.app.core.presentation.sacramentos

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.core.presentation.base.BaseHomeFragment
import org.deiverbum.app.core.presentation.sacramentos.adapter.SacramentoItem
import org.deiverbum.app.core.presentation.sacramentos.adapter.SacramentosParent
import org.deiverbum.app.core.presentation.sacramentos.adapter.SacramentosParentAdapter
import org.deiverbum.app.databinding.FragmentSacramentosBinding
import org.deiverbum.app.util.Constants.CIC_BAPTISMUS
import org.deiverbum.app.util.Constants.CIC_UNCTIONIS
import org.deiverbum.app.util.Constants.FILE_BAPTISMUS
import org.deiverbum.app.util.Constants.FILE_UNCTIONIS_ARTICULO_MORTIS
import org.deiverbum.app.util.Constants.FILE_VIATICUM
import org.deiverbum.app.util.Constants.UNCTIONIS_ORDINARIUM


@AndroidEntryPoint
class SacramentosFragment : BaseHomeFragment<FragmentSacramentosBinding>() {
    private lateinit var mAdapter: SacramentosParentAdapter
    private lateinit var parentList: List<SacramentosParent>
    private lateinit var childList: List<SacramentoItem>

    override fun constructViewBinding(): ViewBinding =
        FragmentSacramentosBinding.inflate(layoutInflater)

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
        parentList = ArrayList()
        childList = ArrayList()
        val parentList: List<SacramentosParent> = listOf(
            SacramentosParent(
                "Bautismo",
                listOf(
                    SacramentoItem("Bautismo", "Normativa canónica", CIC_BAPTISMUS),
                    SacramentoItem("Bautismo", "Bautismo en peligro de muerte", FILE_BAPTISMUS),
                )
            ),
            SacramentosParent(
                "Unción de los Enfermos",
                listOf(
                    SacramentoItem("Unción", "Normativa canónica", CIC_UNCTIONIS),
                    SacramentoItem("Unción", "Rito ordinario", UNCTIONIS_ORDINARIUM),
                    SacramentoItem("Unción", "Viático fuera de la Misa", FILE_VIATICUM),
                    SacramentoItem(
                        "Unción",
                        "Sacramentos en inmediato peligro de muerte",
                        FILE_UNCTIONIS_ARTICULO_MORTIS
                    ),

                    )
            )
        )

        mAdapter = SacramentosParentAdapter(parentList)

    }
}