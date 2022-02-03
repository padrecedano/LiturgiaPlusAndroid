package org.deiverbum.app.data.adapters;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;

import java.util.List;

/**
 * <p>
 * Esta clase maneja el adaptador de la pantalla <code>Breviario</code>,
 * presentada desde <code>BreviarioFragment</code>.
 * Es una versión mejorada de la clase <code>BreviarioRecyclerAdapter</code>,
 * la cual será eliminada desde la versión 2021.1
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @date 18/11/21
 * @since 2021.1
 */

public class OracionesAdapter extends RecyclerView.Adapter<OracionesAdapter.ViewHolder> {
    private static final String TAG = "BreviarioAdapter";
    private List<OracionItem> mDataSet;

    /**
     * Inicializa el dataset del adaptador.
     *
     * @param dataSet List<BreviarioItem> con los datos con que
     *                se llenarán las vistas del {@link RecyclerView}.
     */
    public OracionesAdapter(List<OracionItem> dataSet) {
        mDataSet = dataSet;
    }

    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_rosario, viewGroup, false);

        return new ViewHolder(v);
    }

    /**
     * Reemplaza los contenidos de la vista
     * y guarda una referencia invocando al método
     * {@link ViewHolder#setData(OracionItem)}
     * con el propósito de obtener el id de navegación
     * al hacer clic en cada elemento
     *
     * @param viewHolder Este {@link ViewHolder}
     * @param position   La posición del elemento
     */

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.setData(mDataSet.get(position));
    }

    /**
     * Devuelve el tamaño del dataset
     */
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final TextView txtDescription;
        private RelativeLayout relativeLayout;
        OracionItem mItem;

        public ViewHolder(View v) {
            super(v);


            v.setOnClickListener(v1 -> {
                Bundle bundle = new Bundle();
                bundle.putInt("id", mItem.id);
                Navigation.findNavController(v1).navigate(mItem.navId, bundle);
            });
            txtTitle = (TextView) v.findViewById(R.id.tv_title);
            txtDescription = (TextView) v.findViewById(R.id.tv_description);
            relativeLayout = v.findViewById(R.id.relativeLayout);
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item
         */
        public void setData(OracionItem item) {
            mItem = item;
            //viewIcon.setText(item.letra);
            txtTitle.setText(item.title);
            txtDescription.setText(item.description);

            //relativeLayout.setBackgroundColor(item.color);
        }
    }
}