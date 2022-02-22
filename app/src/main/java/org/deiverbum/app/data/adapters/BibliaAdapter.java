package org.deiverbum.app.data.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.model.BibliaLibros;

import java.util.List;

/**
 * <p>
 * Esta clase maneja el adaptador de la pantalla <code>Breviario</code>,
 * presentada desde <code>BreviarioFragment</code>.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */

public class BibliaAdapter extends RecyclerView.Adapter<BibliaAdapter.ViewHolder> {
    private final List<BibliaLibros> mDataSet;

    /**
     * Inicializa el dataset del adaptador.
     *
     * @param dataSet List<BreviarioItem> con los datos con que
     *                se llenarán las vistas del {@link RecyclerView}.
     */
    public BibliaAdapter(List<BibliaLibros> dataSet) {
        mDataSet = dataSet;
    }

    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_rosario, viewGroup, false);

        return new ViewHolder(v);
    }

    /**
     * Reemplaza los contenidos de la vista
     * y guarda una referencia invocando al método
     * {@link ViewHolder#setData(BibliaLibros)}
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
        BibliaLibros mItem;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(v1 -> {
                Bundle bundle = new Bundle();
                bundle.putInt("bookId", mItem.getId());
                Navigation.findNavController(v1).navigate(R.id.nav_biblia_libros, bundle);
            });
            txtTitle = v.findViewById(R.id.tv_title);
            txtDescription = v.findViewById(R.id.tv_description);
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item Un objeto {@link BibliaLibros}
         */
        public void setData(BibliaLibros item) {
            mItem = item;
            txtTitle.setText(item.getName());
            txtDescription.setText(item.getDescription());
        }
    }
}