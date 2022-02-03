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
import org.deiverbum.app.data.adapters.BibliaAdapter;
import org.deiverbum.app.data.adapters.OracionItem;
import org.deiverbum.app.data.adapters.OracionesAdapter;
import org.deiverbum.app.databinding.FragmentBibliaBinding;
import org.deiverbum.app.model.BibliaLibros;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     Este Fragmento maneja lo relativo al módulo <b>Biblia</b>.
 *     Muestra un <code>RecyclerView</code> con las diferentes oraciones
 *     de la Liturgia de las Horas.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */
public class BibliaFragment extends Fragment{
    private static final String TAG = "BibliaFragment";
    private FragmentBibliaBinding binding;
    RecyclerView recyclerView;
    private BibliaAdapter mAdapter;
    private List<BibliaLibros> booksList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBibliaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        booksList = new ArrayList<>();
        mAdapter = new BibliaAdapter(booksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        recyclerView.setAdapter(new BibliaAdapter(booksList));
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
        /*
        mList.add(new OracionItem(1,"Misterios Gloriosos", "Domingos y Miércoles",R.id.nav_rosario));
        mList.add(new OracionItem(2,"Misterios Gozosos", "Lunes y Sábados",R.id.nav_rosario));
        mList.add(new OracionItem(3,"Misterios Dolorosos", "Martes y Viernes",R.id.nav_rosario));
        mList.add(new OracionItem(4,"Misterios Luminosos", "Jueves",R.id.nav_rosario));
        mList.add(new OracionItem(5,"Letanías Lauretanas", "Solamente las Letanías",R.id.nav_rosario));
        mList.add(new OracionItem(6,"Ángelus", "Recuerda la Encarnación de Cristo",R.id.nav_rosario));
        mList.add(new OracionItem(7,"Regina Coeli", "En lugar del Àngelus, en el tiempo de Pascua",R.id.nav_rosario));
        mList.add(new OracionItem(8,"Via Crucis 2003", "Con meditaciones de Juan Pablo II",R.id.nav_rosario));
        mList.add(new OracionItem(9,"Via Crucis 2005", "Con meditaciones de Joseph Ratzinger",R.id.nav_rosario));
       mAdapter.notifyDataSetChanged();*/
        //List<BibliaLibros> booksList = new ArrayList<>();
        booksList.add(new BibliaLibros(1, "Génesis", "En el principio creó Dios el cielo y la tierra"));
        booksList.add(new BibliaLibros(2, "Éxodo", "...los israelitas que fueron a Egipto con Jacob"));
        booksList.add(new BibliaLibros(3, "Levítico", "Yahvé llamó a Moisés y le habló así..."));
        booksList.add(new BibliaLibros(4, "Números", "Yahvé habló a Moisés en el desierto..."));
        booksList.add(new BibliaLibros(5, "Deuteronomio", "..."));
        booksList.add(new BibliaLibros(6, "Josué", "..."));
        booksList.add(new BibliaLibros(7, "Jueces", "..."));
        booksList.add(new BibliaLibros(8, "Rut", "..."));
        booksList.add(new BibliaLibros(9, "1 Samuel", "..."));
        booksList.add(new BibliaLibros(10, "2 Samuel", "..."));
        booksList.add(new BibliaLibros(11, "1 Reyes", "..."));
        booksList.add(new BibliaLibros(12, "2 Reyes", "..."));
        booksList.add(new BibliaLibros(13, "1 Crónicas", "..."));
        booksList.add(new BibliaLibros(14, "2 Crónicas", "..."));
        booksList.add(new BibliaLibros(15, "Esdras", "..."));
        booksList.add(new BibliaLibros(16, "Nehemías", "..."));
        booksList.add(new BibliaLibros(17, "Tobías", "..."));
        booksList.add(new BibliaLibros(18, "Judit", "..."));
        booksList.add(new BibliaLibros(19, "Ester", "..."));
        booksList.add(new BibliaLibros(20, "1 Macabeos", "..."));
        booksList.add(new BibliaLibros(21, "2 Macabeos", "..."));
        booksList.add(new BibliaLibros(22, "Salmos", "..."));
        booksList.add(new BibliaLibros(23, "Cantar de los Cantares", "..."));
        booksList.add(new BibliaLibros(24, "Lamentaciones", "..."));
        booksList.add(new BibliaLibros(25, "Job", "..."));
        booksList.add(new BibliaLibros(26, "Proverbios", "..."));
        booksList.add(new BibliaLibros(27, "Eclesiastés", "..."));
        booksList.add(new BibliaLibros(28, "Sabiduría", "..."));
        booksList.add(new BibliaLibros(29, "Eclesiástico", "..."));
        booksList.add(new BibliaLibros(30, "Isaías", "..."));
        booksList.add(new BibliaLibros(31, "Jeremías", "..."));
        booksList.add(new BibliaLibros(32, "Baruc", "..."));
        booksList.add(new BibliaLibros(33, "Ezequiel", "..."));
        booksList.add(new BibliaLibros(34, "Daniel", "..."));
        booksList.add(new BibliaLibros(35, "Oseas", "..."));
        booksList.add(new BibliaLibros(36, "Joel", "..."));
        booksList.add(new BibliaLibros(37, "Amós", "..."));
        booksList.add(new BibliaLibros(38, "Abdías", "..."));
        booksList.add(new BibliaLibros(39, "Jonás", "..."));
        booksList.add(new BibliaLibros(40, "Miqueas", "..."));
        booksList.add(new BibliaLibros(41, "Nahún", "..."));
        booksList.add(new BibliaLibros(42, "Habacuc", "..."));
        booksList.add(new BibliaLibros(43, "Sofonías", "..."));
        booksList.add(new BibliaLibros(44, "Ageo", "..."));
        booksList.add(new BibliaLibros(45, "Zacarías", "..."));
        booksList.add(new BibliaLibros(46, "Malaquías", "..."));
        booksList.add(new BibliaLibros(47, "Mateo", "..."));
        booksList.add(new BibliaLibros(48, "Marcos", "..."));
        booksList.add(new BibliaLibros(49, "Lucas", "..."));
        booksList.add(new BibliaLibros(50, "Juan", "..."));
        booksList.add(new BibliaLibros(51, "Hechos de los Apóstoles", "..."));
        booksList.add(new BibliaLibros(52, "Romanos", "..."));
        booksList.add(new BibliaLibros(53, "1 Corintios", "..."));
        booksList.add(new BibliaLibros(54, "2 Corintios", "..."));
        booksList.add(new BibliaLibros(55, "Gálatas", "..."));
        booksList.add(new BibliaLibros(56, "Efesios", "..."));
        booksList.add(new BibliaLibros(57, "Filipenses", "..."));
        booksList.add(new BibliaLibros(58, "Colosenses", "..."));
        booksList.add(new BibliaLibros(59, "1 Tesalonicenses", "..."));
        booksList.add(new BibliaLibros(60, "2 Tesalonicenses", "..."));
        booksList.add(new BibliaLibros(61, "1 Timoteo", "..."));
        booksList.add(new BibliaLibros(62, "2 Timoteo", "..."));
        booksList.add(new BibliaLibros(63, "Tito", "..."));
        booksList.add(new BibliaLibros(64, "Filemón", "..."));
        booksList.add(new BibliaLibros(65, "Hebreos", "..."));
        booksList.add(new BibliaLibros(66, "Santiago", "..."));
        booksList.add(new BibliaLibros(67, "1 Pedro", "..."));
        booksList.add(new BibliaLibros(68, "2 Pedro", "..."));
        booksList.add(new BibliaLibros(69, "1 Juan", "..."));
        booksList.add(new BibliaLibros(70, "2 Juan", "..."));
        booksList.add(new BibliaLibros(71, "3 Juan", "..."));
        booksList.add(new BibliaLibros(72, "Judas", "..."));
        booksList.add(new BibliaLibros(73, "Apocalipsis", "..."));
        mAdapter.notifyDataSetChanged();

        //adapter = new BibliaLibrosItemsAdapter(booksList, this);
    }

    private void pickOutDate() {
        Bundle bundle = getArguments();
        String dateString;
        if (bundle != null) {
            dateString = bundle.getString("FECHA") == null ? Utils.getHoy() : bundle.getString("FECHA");
        }else{
            dateString=Utils.getHoy();
        }
        ActionBar actionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        actionBar.setSubtitle(Utils.getTitleDate(dateString));
    }

}

