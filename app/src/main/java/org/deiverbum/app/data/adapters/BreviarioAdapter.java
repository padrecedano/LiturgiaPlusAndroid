package org.deiverbum.app.data.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.deiverbum.app.R;

import java.util.List;

/**
 * <p>
 * Esta clase maneja el adaptador de la pantalla <code>Breviary</code>,
 * presentada desde <code>BreviarioFragment</code>.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class BreviarioAdapter extends RecyclerView.Adapter<BreviarioAdapter.ViewHolder> {
    private final List<BreviarioItem> mDataSet;

    /**
     * Inicializa el dataset del adaptador.
     *
     * @param dataSet List<BreviarioItem> con los datos con que
     *                se llenarán las vistas del {@link RecyclerView}.
     */
    public BreviarioAdapter(List<BreviarioItem> dataSet) {
        mDataSet = dataSet;
    }

    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_breviario_items, viewGroup, false);

        return new ViewHolder(v);
    }

    /**
     * Reemplaza los contenidos de la vista
     * y guarda una referencia invocando al método
     * {@link ViewHolder#setData(BreviarioItem)}
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
        private final TextView viewIcon;
        private final TextView viewText;
        private final RelativeLayout relativeLayout;
        BreviarioItem mItem;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
                String itemText=mItem.text;
                if(itemText.equals("Más...")){
                    Snackbar snackbar = Snackbar
                            .make(v1, "Este módulo está pendiente de " +
                                            "programación...",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    Navigation.findNavController(v1).navigate(mItem.navId);
                } });

            viewIcon = v.findViewById(R.id.tv_Material);
            viewText = v.findViewById(R.id.tv_Elemento);
            relativeLayout = v.findViewById(R.id.relativeLayout);
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item del tipo {@link BreviarioItem}
         */
        public void setData(BreviarioItem item) {
            mItem = item;
            viewIcon.setText(item.letra);
            viewText.setText(item.text);
            relativeLayout.setBackgroundColor(item.color);
        }
    }
}