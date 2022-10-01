package org.deiverbum.app.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.deiverbum.app.R;
import org.deiverbum.app.model.HomeItem;

import java.util.List;

/**
 * <p>
 * Esta clase maneja el adaptador de la pantalla <code>Home</code>,
 * presentada desde <code>HomeFragment</code>.
 * Es una versión mejorada de la clase <code>MainItemsAdapter</code>,
 * la cual será eliminada desde la versión 2021.1
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final List<HomeItem> mDataSet;

    /**
     * Inicializa el dataset del adaptador.
     *
     * @param dataSet  Una lista de objetos {@link HomeItem} con los datos con que
     *                se llenarán las vistas del {@link RecyclerView}.
     */
    public HomeAdapter(List<HomeItem> dataSet) {
        mDataSet = dataSet;
    }

    /**
     * Crea las nuevas vistas (invocadas por el layout manager)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_card, viewGroup, false);

        return new ViewHolder(v);
    }

    /**
     * Reemplaza los contenidos de la vista
     * y guarda una referencia invocando al método
     * {@link ViewHolder#setData(HomeItem)}
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
        private final ImageView viewIcon;
        private final TextView viewText;
        private final RelativeLayout relativeLayout;
        HomeItem mItem;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
                int itemId=mItem.getItemId();
                if(itemId==2 || itemId>9){
                    Snackbar snackbar = Snackbar
                            .make(v1, "Este módulo está pendiente de " +
                                            "programación...",
                                    Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    Navigation.findNavController(v1).navigate(mItem.getNavId());
                } });
            viewIcon = v.findViewById(R.id.thumbnail);
            viewText = v.findViewById(R.id.title);
            relativeLayout = v.findViewById(R.id.mainCardRelativeLayout);
        }

        /**
         * Establece los valores respectivos en las vistas
         * y guarda una referencia del objeto
         * para poder obtener el id de navegación al hacer click
         *
         * @param item Item con imagen y texto
         */
        public void setData(HomeItem item) {
            mItem = item;
            viewIcon.setImageResource(item.getThumbnail());
            viewText.setText(item.getName());
            relativeLayout.setBackgroundColor(item.getColor());
        }
    }
}
