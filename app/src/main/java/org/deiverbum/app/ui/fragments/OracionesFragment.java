package org.deiverbum.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.adapters.OracionItem;
import org.deiverbum.app.data.adapters.OracionesAdapter;
import org.deiverbum.app.databinding.FragmentOracionesBinding;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Este Fragmento maneja lo relativo al módulo <b>Oraciones</b>.
 * Muestra un <code>RecyclerView</code> con las diferentes oraciones
 * de la Liturgy de las Horas.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class OracionesFragment extends Fragment {
    RecyclerView recyclerView;
    private FragmentOracionesBinding binding;
    private List<OracionItem> mList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOracionesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        mList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new OracionesAdapter(mList));
        prepareItems();
        pickOutDate();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems() {
        mList.add(new OracionItem(1, "Misterios Gloriosos", "Domingos y Miércoles", R.id.nav_rosario));
        mList.add(new OracionItem(2, "Misterios Gozosos", "Lunes y Sábados", R.id.nav_rosario));
        mList.add(new OracionItem(3, "Misterios Dolorosos", "Martes y Viernes", R.id.nav_rosario));
        mList.add(new OracionItem(4, "Misterios Luminosos", "Jueves", R.id.nav_rosario));
        mList.add(new OracionItem(5, "Letanías Lauretanas", "Solamente las Letanías", R.id.nav_rosario));
        mList.add(new OracionItem(6, "Ángelus", "Recuerda la Encarnación de Cristo", R.id.nav_rosario));
        mList.add(new OracionItem(7, "Regina Coeli", "En lugar del Àngelus, en el tiempo de Pascua", R.id.nav_rosario));
        mList.add(new OracionItem(8, "Via Crucis 2003", "Con meditaciones de Juan Pablo II", R.id.nav_rosario));
        mList.add(new OracionItem(9, "Via Crucis 2005", "Con meditaciones de Joseph Ratzinger", R.id.nav_rosario));
        //mAdapter.notifyDataSetChanged();
    }

    private void pickOutDate() {
        Bundle bundle = getArguments();
        String dateString;
        if (bundle != null) {
            dateString = bundle.getString("FECHA") == null ? Utils.getHoy() : bundle.getString("FECHA");
        } else {
            dateString = Utils.getHoy();
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Objects.requireNonNull(actionBar).setSubtitle(Utils.getTitleDate(dateString));
    }

}

