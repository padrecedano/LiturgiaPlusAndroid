package org.deiverbum.app.presentation.breviario.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.deiverbum.app.R

/**
 *
 *
 * Esta clase maneja el adaptador de la pantalla `Breviary`,
 * presentada desde `BreviarioFragment`.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class BreviarioAdapter(private val mDataSet: List<BreviarioItem>) :
    RecyclerView.Adapter<BreviarioAdapter.ViewHolder>() {
    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_breviario_items, viewGroup, false)
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
        private val viewIcon: TextView
        private val viewText: TextView
        private val relativeLayout: RelativeLayout
        private var mItem: BreviarioItem? = null

        init {
            v.setOnClickListener { v1: View? ->
                val itemText = mItem!!.text
                if (itemText == "Más...") {
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
            viewIcon = v.findViewById(R.id.tv_Material)
            viewText = v.findViewById(R.id.tv_Elemento)
            relativeLayout = v.findViewById(R.id.relativeLayout)
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item del tipo [BreviarioItem]
         */
        fun setData(item: BreviarioItem) {
            mItem = item
            viewIcon.text = item.letra
            viewText.text = item.text
            relativeLayout.setBackgroundColor(item.color)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun update(newItems: List<BreviarioItem>) {
        mDataSet.run {
            //clear()
            //addAll(newItems)
            notifyDataSetChanged()
        }
    }
}