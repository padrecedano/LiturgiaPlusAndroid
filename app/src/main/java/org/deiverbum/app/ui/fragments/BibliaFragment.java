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

import org.deiverbum.app.data.adapters.BibliaAdapter;
import org.deiverbum.app.databinding.FragmentBibliaBinding;
import org.deiverbum.app.model.BibleBooks;
import org.deiverbum.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     Este Fragmento maneja lo relativo al módulo <b>Biblia</b>.
 *     Muestra un <code>RecyclerView</code> con las diferentes oraciones
 *     de la Liturgy de las Horas.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class BibliaFragment extends Fragment{
    private FragmentBibliaBinding binding;
    RecyclerView recyclerView;
    private List<BibleBooks> booksList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBibliaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        booksList = new ArrayList<>();
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

        booksList.add(new BibleBooks(1, "Génesis", "En el principio creó Dios el cielo y la tierra"));
        booksList.add(new BibleBooks(2, "Éxodo", "...los israelitas que fueron a Egipto con Jacob"));
        booksList.add(new BibleBooks(3, "Levítico", "Yahvé llamó a Moisés y le habló así..."));
        booksList.add(new BibleBooks(4, "Números", "Yahvé habló a Moisés en el desierto..."));
        booksList.add(new BibleBooks(5, "Deuteronomio", "..."));
        booksList.add(new BibleBooks(6, "Josué", "..."));
        booksList.add(new BibleBooks(7, "Jueces", "..."));
        booksList.add(new BibleBooks(8, "Rut", "..."));
        booksList.add(new BibleBooks(9, "1 Samuel", "..."));
        booksList.add(new BibleBooks(10, "2 Samuel", "..."));
        booksList.add(new BibleBooks(11, "1 Reyes", "..."));
        booksList.add(new BibleBooks(12, "2 Reyes", "..."));
        booksList.add(new BibleBooks(13, "1 Crónicas", "..."));
        booksList.add(new BibleBooks(14, "2 Crónicas", "..."));
        booksList.add(new BibleBooks(15, "Esdras", "..."));
        booksList.add(new BibleBooks(16, "Nehemías", "..."));
        booksList.add(new BibleBooks(17, "Tobías", "..."));
        booksList.add(new BibleBooks(18, "Judit", "..."));
        booksList.add(new BibleBooks(19, "Ester", "..."));
        booksList.add(new BibleBooks(20, "1 Macabeos", "..."));
        booksList.add(new BibleBooks(21, "2 Macabeos", "..."));
        booksList.add(new BibleBooks(22, "Salmos", "..."));
        booksList.add(new BibleBooks(23, "Cantar de los Cantares", "..."));
        booksList.add(new BibleBooks(24, "Lamentaciones", "..."));
        booksList.add(new BibleBooks(25, "Job", "..."));
        booksList.add(new BibleBooks(26, "Proverbios", "..."));
        booksList.add(new BibleBooks(27, "Eclesiastés", "..."));
        booksList.add(new BibleBooks(28, "Sabiduría", "..."));
        booksList.add(new BibleBooks(29, "Eclesiástico", "..."));
        booksList.add(new BibleBooks(30, "Isaías", "..."));
        booksList.add(new BibleBooks(31, "Jeremías", "..."));
        booksList.add(new BibleBooks(32, "Baruc", "..."));
        booksList.add(new BibleBooks(33, "Ezequiel", "..."));
        booksList.add(new BibleBooks(34, "Daniel", "..."));
        booksList.add(new BibleBooks(35, "Oseas", "..."));
        booksList.add(new BibleBooks(36, "Joel", "..."));
        booksList.add(new BibleBooks(37, "Amós", "..."));
        booksList.add(new BibleBooks(38, "Abdías", "..."));
        booksList.add(new BibleBooks(39, "Jonás", "..."));
        booksList.add(new BibleBooks(40, "Miqueas", "..."));
        booksList.add(new BibleBooks(41, "Nahún", "..."));
        booksList.add(new BibleBooks(42, "Habacuc", "..."));
        booksList.add(new BibleBooks(43, "Sofonías", "..."));
        booksList.add(new BibleBooks(44, "Ageo", "..."));
        booksList.add(new BibleBooks(45, "Zacarías", "..."));
        booksList.add(new BibleBooks(46, "Malaquías", "..."));
        booksList.add(new BibleBooks(47, "Mateo", "..."));
        booksList.add(new BibleBooks(48, "Marcos", "..."));
        booksList.add(new BibleBooks(49, "Lucas", "..."));
        booksList.add(new BibleBooks(50, "Juan", "..."));
        booksList.add(new BibleBooks(51, "Hechos de los Apóstoles", "..."));
        booksList.add(new BibleBooks(52, "Romanos", "..."));
        booksList.add(new BibleBooks(53, "1 Corintios", "..."));
        booksList.add(new BibleBooks(54, "2 Corintios", "..."));
        booksList.add(new BibleBooks(55, "Gálatas", "..."));
        booksList.add(new BibleBooks(56, "Efesios", "..."));
        booksList.add(new BibleBooks(57, "Filipenses", "..."));
        booksList.add(new BibleBooks(58, "Colosenses", "..."));
        booksList.add(new BibleBooks(59, "1 Tesalonicenses", "..."));
        booksList.add(new BibleBooks(60, "2 Tesalonicenses", "..."));
        booksList.add(new BibleBooks(61, "1 Timoteo", "..."));
        booksList.add(new BibleBooks(62, "2 Timoteo", "..."));
        booksList.add(new BibleBooks(63, "Tito", "..."));
        booksList.add(new BibleBooks(64, "Filemón", "..."));
        booksList.add(new BibleBooks(65, "Hebreos", "..."));
        booksList.add(new BibleBooks(66, "Santiago", "..."));
        booksList.add(new BibleBooks(67, "1 Pedro", "..."));
        booksList.add(new BibleBooks(68, "2 Pedro", "..."));
        booksList.add(new BibleBooks(69, "1 Juan", "..."));
        booksList.add(new BibleBooks(70, "2 Juan", "..."));
        booksList.add(new BibleBooks(71, "3 Juan", "..."));
        booksList.add(new BibleBooks(72, "Judas", "..."));
        booksList.add(new BibleBooks(73, "Apocalipsis", "..."));
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

