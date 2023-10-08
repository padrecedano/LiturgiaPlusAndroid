package org.deiverbum.app.core.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.deiverbum.app.R

/**
 *
 *
 * Esta clase maneja el adaptador de la pantalla `Home`,
 * presentada desde `HomeFragment`.
 * Es una versión mejorada de la clase `MainItemsAdapter`,
 * la cual será eliminada desde la versión 2021.1
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class HomeAdapter(private val mDataSet: List<HomeItem>, private var bgColor: Int) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    init {
        Companion.bgColor = bgColor
    }

    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_card, viewGroup, false)
        return ViewHolder(v)
    }

    /**
     * Reemplaza los contenidos de la vista
     * y guarda una referencia invocando al método
     * [ViewHolder.setData]
     * con el propósito de obtener el id de navegación
     * al hacer clic en cada elemento
     *
     * @param viewHolder Este [ViewHolder]
     * @param position   La posición del elemento
     */
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setData(mDataSet[position])
    }

    /**
     * Devuelve el tamaño del dataset
     */
    override fun getItemCount(): Int {
        return mDataSet.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val viewIcon: ImageView
        private val viewText: TextView
        private val relativeLayout: RelativeLayout
        private var mItem: HomeItem? = null
        init {
            val cardView = v.findViewById<LinearLayout>(R.id.mainCardView)
            cardView.setBackgroundColor(bgColor)
            v.setOnClickListener { v1: View? ->
                val itemId = mItem!!.itemId
                if (itemId == 2 || itemId > 9) {
                    val snackbar = Snackbar
                        .make(
                            v1!!, "Este módulo está pendiente de " +
                                    "programación...",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.show()
                } else {
                    findNavController(v1!!).navigate(mItem!!.navId)
                }
            }
            viewIcon = v.findViewById(R.id.thumbnail)
            viewText = v.findViewById(R.id.title)
            relativeLayout = v.findViewById(R.id.mainCardRelativeLayout)
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item Item con imagen y texto
         */
        fun setData(item: HomeItem) {
            mItem = item
            viewIcon.setImageResource(item.thumbnail)
            viewIcon.setColorFilter(item.imageColor)

            //viewIcon.settin.setBackgroundColor(item.imageColor);}
            viewText.text = item.name
            relativeLayout.setBackgroundColor(item.color)
        }
    }

    companion object {
        private var bgColor = 0
    }
}

/**
 *
 *
 * HomeItem es la clase para manejar las opciones
 * que aparecen en la pantalla inicial de la aplicación
 * desde un elemento `RecyclerView`.
 *
 *
 * Esta clase trabaja con: {@link org.deiverbum.app.core.presentation.home.adapter.HomeAdapter}
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class HomeItem(
    var name: String?,
    var itemId: Int,
    var thumbnail: Int,
    var color: Int,
    var navId: Int,
    imageColor: Int
) {
    var imageColor = imageColor
        private set

}