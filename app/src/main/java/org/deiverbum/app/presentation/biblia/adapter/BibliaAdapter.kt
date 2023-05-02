package org.deiverbum.app.presentation.biblia.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.deiverbum.app.R
import org.deiverbum.app.model.BibleBooks

/**
 *
 *
 * Esta clase maneja el adaptador de la pantalla `Breviary`,
 * presentada desde `BreviarioFragment`.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */
class BibliaAdapter(private val mDataSet: List<BibleBooks>) : RecyclerView.Adapter<BibliaAdapter.ViewHolder>() {
    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_rosario, viewGroup, false)
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
        private val txtTitle: TextView
        private val txtDescription: TextView
        private var mItem: BibleBooks? = null

        init {
            v.setOnClickListener { v1: View? ->
                val bundle = Bundle()
                bundle.putInt("bookId", mItem!!.id)
                findNavController(v1!!).navigate(R.id.nav_biblia_libros, bundle)
            }
            txtTitle = v.findViewById(R.id.tv_title)
            txtDescription = v.findViewById(R.id.tv_description)
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item Un objeto [BibleBooks]
         */
        fun setData(item: BibleBooks) {
            mItem = item
            txtTitle.text = item.name
            txtDescription.text = item.description
        }
    }
}