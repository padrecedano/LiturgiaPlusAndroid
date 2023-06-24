package org.deiverbum.app.presentation.biblia

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.databinding.FragmentBibliaBinding
import org.deiverbum.app.model.BibleBooks
import org.deiverbum.app.presentation.base.BaseHomeFragment
import org.deiverbum.app.presentation.biblia.adapter.BibliaAdapter
import org.deiverbum.app.util.Utils

/**
 * Este Fragmento maneja la pantalla principal del módulo **Biblia**.
 * Muestra un `RecyclerView` con los diferentes libros de la Biblia.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */

@AndroidEntryPoint
class BibliaFragment : BaseHomeFragment<FragmentBibliaBinding>() {
    private lateinit var mAdapter: BibliaAdapter
    private lateinit var mList: List<BibleBooks>

    override fun constructViewBinding(): ViewBinding = FragmentBibliaBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        pickOutDate()
        prepareItems()
        initUi()
    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun prepareItems() {
        mList = ArrayList()
        mList = listOf(
            BibleBooks(1, "Génesis", "En el principio creó Dios el cielo y la tierra"),
            BibleBooks(2, "Éxodo", "...los israelitas que fueron a Egipto con Jacob"),
            BibleBooks(3, "Levítico", "Yahvé llamó a Moisés y le habló así..."),
            BibleBooks(4, "Números", "Yahvé habló a Moisés en el desierto..."),
            BibleBooks(5, "Deuteronomio", "..."),
            BibleBooks(6, "Josué", "..."),
            BibleBooks(7, "Jueces", "..."),
            BibleBooks(8, "Rut", "..."),
            BibleBooks(9, "1 Samuel", "..."),
            BibleBooks(10, "2 Samuel", "..."),
            BibleBooks(11, "1 Reyes", "..."),
            BibleBooks(12, "2 Reyes", "..."),
            BibleBooks(13, "1 Crónicas", "..."),
            BibleBooks(14, "2 Crónicas", "..."),
            BibleBooks(15, "Esdras", "..."),
            BibleBooks(16, "Nehemías", "..."),
            BibleBooks(17, "Tobías", "..."),
            BibleBooks(18, "Judit", "..."),
            BibleBooks(19, "Ester", "..."),
            BibleBooks(20, "1 Macabeos", "..."),
            BibleBooks(21, "2 Macabeos", "..."),
            BibleBooks(22, "Salmos", "..."),
            BibleBooks(23, "Cantar de los Cantares", "..."),
            BibleBooks(24, "Lamentaciones", "..."),
            BibleBooks(25, "Job", "..."),
            BibleBooks(26, "Proverbios", "..."),
            BibleBooks(27, "Eclesiastés", "..."),
            BibleBooks(28, "Sabiduría", "..."),
            BibleBooks(29, "Eclesiástico", "..."),
            BibleBooks(30, "Isaías", "..."),
            BibleBooks(31, "Jeremías", "..."),
            BibleBooks(32, "Baruc", "..."),
            BibleBooks(33, "Ezequiel", "..."),
            BibleBooks(34, "Daniel", "..."),
            BibleBooks(35, "Oseas", "..."),
            BibleBooks(36, "Joel", "..."),
            BibleBooks(37, "Amós", "..."),
            BibleBooks(38, "Abdías", "..."),
            BibleBooks(39, "Jonás", "..."),
            BibleBooks(40, "Miqueas", "..."),
            BibleBooks(41, "Nahún", "..."),
            BibleBooks(42, "Habacuc", "..."),
            BibleBooks(43, "Sofonías", "..."),
            BibleBooks(44, "Ageo", "..."),
            BibleBooks(45, "Zacarías", "..."),
            BibleBooks(46, "Malaquías", "..."),
            BibleBooks(47, "Mateo", "..."),
            BibleBooks(48, "Marcos", "..."),
            BibleBooks(49, "Lucas", "..."),
            BibleBooks(50, "Juan", "..."),
            BibleBooks(51, "Hechos de los Apóstoles", "..."),
            BibleBooks(52, "Romanos", "..."),
            BibleBooks(53, "1 Corintios", "..."),
            BibleBooks(54, "2 Corintios", "..."),
            BibleBooks(55, "Gálatas", "..."),
            BibleBooks(56, "Efesios", "..."),
            BibleBooks(57, "Filipenses", "..."),
            BibleBooks(58, "Colosenses", "..."),
            BibleBooks(59, "1 Tesalonicenses", "..."),
            BibleBooks(60, "2 Tesalonicenses", "..."),
            BibleBooks(61, "1 Timoteo", "..."),
            BibleBooks(62, "2 Timoteo", "..."),
            BibleBooks(63, "Tito", "..."),
            BibleBooks(64, "Filemón", "..."),
            BibleBooks(65, "Hebreos", "..."),
            BibleBooks(66, "Santiago", "..."),
            BibleBooks(67, "1 Pedro", "..."),
            BibleBooks(68, "2 Pedro", "..."),
            BibleBooks(69, "1 Juan", "..."),
            BibleBooks(70, "2 Juan", "..."),
            BibleBooks(71, "3 Juan", "..."),
            BibleBooks(72, "Judas", "..."),
            BibleBooks(73, "Apocalipsis", "...")
        )
        mAdapter= BibliaAdapter(mList)
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
}