package org.deiverbum.app.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;
import org.deiverbum.app.data.adapters.HomeAdapter;
import org.deiverbum.app.databinding.FragmentHomeBinding;
import org.deiverbum.app.model.HomeItem;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.BreviarioViewModel;
import org.deiverbum.app.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     HomeFragment es el primer fragmento que se abre en la aplicación.
 *     Contiene un <code>RecyclerView</code> con todas las opciones
 *     disponibles para el usuario.
 *     En las versiones anteriores estos elementos se presentaban
 *     directamente desde <code>MainActivity</code>.
 *     Con la implementación de fragmentos adaptamos al código al nuevo
 *     estándar conocido como Componentes de Navegación, combinándolo con
 *     los Componentes de Arquitectura, creando un código mucho más ligero
 *     y mejor organizado.
 * </p>
 *
 * @author      A. Cedano
 * @version     1.0
 * @since       2022.1
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<HomeItem> mList;
    private HomeViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        mList = new ArrayList<>();
        HomeAdapter mAdapter = new HomeAdapter(mList);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        recyclerView.setAdapter(mAdapter);
        prepareItems();
        pickOutDate();
        mViewModel.callFirestore();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems() {
        int colorBreviario =  ContextCompat.getColor(requireActivity(),R.color.colorBreviario);
        int colorMisa =  ContextCompat.getColor(requireActivity(),R.color.colorMisa);
        int colorHomilias =  ContextCompat.getColor(requireActivity(),R.color.colorHomilias);

        int colorLecturas =  ContextCompat.getColor(requireActivity(),R.color.colorMain_lecturas_img);
        int colorEvangelio =  ContextCompat.getColor(requireActivity(),R.color.colorMain_evangelio_img);
        int colorSantos =  ContextCompat.getColor(requireActivity(),R.color.colorSantos);

        int colorCalendario =  ContextCompat.getColor(requireActivity(),R.color.colorCalendario);
        int colorOraciones =  ContextCompat.getColor(requireActivity(),R.color.colorOraciones);
        int colorMas =  ContextCompat.getColor(requireActivity(),R.color.colorMain_img_mas);
        int colorBiblia =  ContextCompat.getColor(requireActivity(),R.color.colorBiblia);
        int colorPadres =  ContextCompat.getColor(requireActivity(),R.color.colorPadres);
        int colorSacramentos =  ContextCompat.getColor(requireActivity(),R.color.colorSacramentos);
        mList.add(new HomeItem("Breviario", 1, R.drawable.ic_breviario, colorBreviario, R.id.nav_breviario));
        mList.add(new HomeItem("Misa", 2, R.drawable.ic_misa, colorMisa, R.id.nav_misa));
        mList.add(new HomeItem("Homilías", 3, R.drawable.ic_homilias, colorHomilias, R.id.nav_homilias));
        mList.add(new HomeItem("Santos", 4, R.drawable.ic_santos, colorSantos, R.id.nav_santo));
        mList.add(new HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorLecturas, R.id.nav_lecturas));
        mList.add(new HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorEvangelio, R.id.nav_comentarios));
        mList.add(new HomeItem("Calendario", 7, R.drawable.ic_calendario, colorCalendario, R.id.nav_calendario));
        mList.add(new HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorOraciones, R.id.nav_oraciones));
        mList.add(new HomeItem("Biblia", 9, R.drawable.ic_biblia, colorBiblia, R.id.nav_biblia));
        mList.add(new HomeItem("Patrística", 10, R.drawable.ic_patristica, colorMas, R.id.nav_patristica));
        mList.add(new HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorSacramentos, R.id.nav_sacramentos));
        mList.add(new HomeItem("Más...", 12, R.drawable.ic_mas, colorPadres,
                R.id.nav_mas));

        //mAdapter.notifyDataSetChanged();
    }

    private void pickOutDate() {

        Bundle bundle = getArguments();
        String dateString;
        if (bundle != null) {
            dateString = bundle.getString("FECHA") == null ? Utils.getHoy() : bundle.getString("FECHA");
        }else{
            dateString=Utils.getHoy();
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Objects.requireNonNull(actionBar).setSubtitle(Utils.getTitleDate(dateString));
    }
}
