package org.deiverbum.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.adapters.BreviarioAdapter;
import org.deiverbum.app.data.adapters.BreviarioItem;
import org.deiverbum.app.databinding.FragmentBreviarioBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Este Fragmento maneja lo relativo al módulo <b>Breviario</b>.
 *     Muestra un <code>RecyclerView</code> con las diferentes oraciones
 *     de la Liturgia de las Horas.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class BreviarioFragment extends Fragment{
    private FragmentBreviarioBinding binding;
    RecyclerView recyclerView;
    private List<BreviarioItem> mList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBreviarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        mList = new ArrayList<>();
        //BreviarioAdapter mAdapter = new BreviarioAdapter(mList);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        recyclerView.setAdapter(new BreviarioAdapter(mList));
        prepareItems();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems() {
        int colorGrupo1 =
                ContextCompat.getColor(requireActivity(),R.color.color_fondo_grupo1);
        int colorGrupo2 = ContextCompat.getColor(requireActivity(),
                R.color.color_fondo_grupo2);

        mList.add(new BreviarioItem("Oficio+Laudes", colorGrupo1, "M",R.id.nav_mixto));
        mList.add(new BreviarioItem("Oficio", colorGrupo1, "O",R.id.nav_oficio));
        mList.add(new BreviarioItem("Laudes",  colorGrupo1, "L",R.id.nav_laudes));

        mList.add(new BreviarioItem("Tercia",  colorGrupo2, "T",R.id.nav_tercia));
        mList.add(new BreviarioItem("Sexta",  colorGrupo2, "S",R.id.nav_sexta));
        mList.add(new BreviarioItem("Nona", colorGrupo2, "N",R.id.nav_nona));

        mList.add(new BreviarioItem("Vísperas",  colorGrupo1, "V",R.id.nav_visperas));
        mList.add(new BreviarioItem("Completas",  colorGrupo1, "C",R.id.nav_completas));
        mList.add(new BreviarioItem("Más...", colorGrupo1, "+",R.id.nav_breviario_mas));
        //mAdapter.notifyDataSetChanged();

    }
}

