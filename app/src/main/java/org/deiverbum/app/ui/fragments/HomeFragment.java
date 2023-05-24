package org.deiverbum.app.ui.fragments;

import static org.deiverbum.app.utils.Constants.PREF_ACCEPT;
import static org.deiverbum.app.utils.Constants.PREF_INITIAL_SYNC;
import static org.deiverbum.app.utils.Constants.PREF_LAST_YEAR_CLEANED;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import org.deiverbum.app.presentation.home.adapter.HomeAdapter;
import org.deiverbum.app.databinding.FragmentHomeBinding;
import org.deiverbum.app.presentation.home.adapter.HomeItem;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.viewmodel.SyncViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * <p>
 * HomeFragment es el primer fragmento que se abre en la aplicación.
 * Contiene un <code>RecyclerView</code> con todas las opciones
 * disponibles para el usuario.
 * En las versiones anteriores estos elementos se presentaban
 * directamente desde <code>MainActivity</code>.
 * Con la implementación de fragmentos adaptamos al código al nuevo
 * estándar conocido como Componentes de Navegación, combinándolo con
 * los Componentes de Arquitectura, creando un código mucho más ligero
 * y mejor organizado.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<HomeItem> mList;
    private HomeAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.recyclerView;
        mList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        pickOutDate();
        setConfiguration();
        recyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareItems(String theme) {
        if (theme.equals("1")) {
            int imageColor = ContextCompat.getColor(requireActivity(), R.color.colorMainIcon);
            int colorBreviario = ContextCompat.getColor(requireActivity(), R.color.colorBreviario);
            int colorMisa = ContextCompat.getColor(requireActivity(), R.color.colorMisa);
            int colorHomilias = ContextCompat.getColor(requireActivity(), R.color.colorHomilias);

            int colorLecturas = ContextCompat.getColor(requireActivity(), R.color.colorMain_lecturas_img);
            int colorEvangelio = ContextCompat.getColor(requireActivity(), R.color.colorMain_evangelio_img);
            int colorSantos = ContextCompat.getColor(requireActivity(), R.color.colorSantos);

            int colorCalendario = ContextCompat.getColor(requireActivity(), R.color.colorCalendario);
            int colorOraciones = ContextCompat.getColor(requireActivity(), R.color.colorOraciones);
            int colorMas = ContextCompat.getColor(requireActivity(), R.color.colorMain_img_mas);
            int colorBiblia = ContextCompat.getColor(requireActivity(), R.color.colorBiblia);
            int colorPadres = ContextCompat.getColor(requireActivity(), R.color.colorPadres);
            int colorSacramentos = ContextCompat.getColor(requireActivity(), R.color.colorSacramentos);
            mList.add(new HomeItem("Breviario", 1, R.drawable.ic_breviario, colorBreviario, R.id.nav_breviario, imageColor));
            mList.add(new HomeItem("Misa", 2, R.drawable.ic_misa, colorMisa, R.id.nav_misa, imageColor));
            mList.add(new HomeItem("Homilías", 3, R.drawable.ic_homilias, colorHomilias, R.id.nav_homilias, imageColor));
            mList.add(new HomeItem("Santos", 4, R.drawable.ic_santos, colorSantos, R.id.nav_santo, imageColor));
            mList.add(new HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorLecturas, R.id.nav_lecturas, imageColor));
            mList.add(new HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorEvangelio, R.id.nav_comentarios, imageColor));
            mList.add(new HomeItem("Calendario", 7, R.drawable.ic_calendario, colorCalendario, R.id.nav_calendario, imageColor));
            mList.add(new HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorOraciones, R.id.nav_oraciones, imageColor));
            mList.add(new HomeItem("Biblia", 9, R.drawable.ic_biblia, colorBiblia, R.id.nav_biblia, imageColor));
            mList.add(new HomeItem("Patrística", 10, R.drawable.ic_patristica, colorMas, R.id.nav_patristica, imageColor));
            mList.add(new HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorSacramentos, R.id.nav_sacramentos, imageColor));
            mList.add(new HomeItem("Más...", 12, R.drawable.ic_mas, colorPadres, R.id.nav_mas, imageColor));

            mAdapter = new HomeAdapter(mList, ContextCompat.getColor(requireActivity(), R.color.transparent));
            binding.homeParent.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.colorSurface));


        } else if (theme.equals("2")) {
            mAdapter = new HomeAdapter(mList, ContextCompat.getColor(requireActivity(), R.color.transparent));
            int colorUnique = ContextCompat.getColor(requireActivity(), R.color.md_theme_dark_background);
            binding.homeParent.setBackgroundColor(colorUnique);

            int imageColor = ContextCompat.getColor(requireActivity(), R.color.color_nav_start);
            mList.add(new HomeItem("Breviario", 1, R.drawable.ic_breviario, colorUnique, R.id.nav_breviario, imageColor));
            mList.add(new HomeItem("Misa", 2, R.drawable.ic_misa, colorUnique, R.id.nav_misa, imageColor));
            mList.add(new HomeItem("Homilías", 3, R.drawable.ic_homilias, colorUnique, R.id.nav_homilias, imageColor));
            mList.add(new HomeItem("Santos", 4, R.drawable.ic_santos, colorUnique, R.id.nav_santo, imageColor));
            mList.add(new HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorUnique, R.id.nav_lecturas, imageColor));
            mList.add(new HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorUnique, R.id.nav_comentarios, imageColor));
            mList.add(new HomeItem("Calendario", 7, R.drawable.ic_calendario, colorUnique, R.id.nav_calendario, imageColor));
            mList.add(new HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorUnique, R.id.nav_oraciones, imageColor));
            mList.add(new HomeItem("Biblia", 9, R.drawable.ic_biblia, colorUnique, R.id.nav_biblia, imageColor));
            mList.add(new HomeItem("Patrística", 10, R.drawable.ic_patristica, colorUnique, R.id.nav_patristica, imageColor));
            mList.add(new HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorUnique, R.id.nav_sacramentos, imageColor));
            mList.add(new HomeItem("Más...", 12, R.drawable.ic_mas, colorUnique, R.id.nav_mas, imageColor));

        } else {
            int colorUnique = ContextCompat.getColor(requireActivity(), R.color.color_nav_start);
            int imageColor = ContextCompat.getColor(requireActivity(), R.color.colorBreviario);
            binding.homeParent.setBackgroundColor(colorUnique);
            mAdapter = new HomeAdapter(mList, ContextCompat.getColor(requireActivity(), R.color.transparent));

            mList.add(new HomeItem("Breviario", 1, R.drawable.ic_breviario, colorUnique, R.id.nav_breviario, imageColor));
            mList.add(new HomeItem("Misa", 2, R.drawable.ic_misa, colorUnique, R.id.nav_misa, imageColor));
            mList.add(new HomeItem("Homilías", 3, R.drawable.ic_homilias, colorUnique, R.id.nav_homilias, imageColor));
            mList.add(new HomeItem("Santos", 4, R.drawable.ic_santos, colorUnique, R.id.nav_santo, imageColor));
            mList.add(new HomeItem("Lecturas", 5, R.drawable.ic_lecturas, colorUnique, R.id.nav_lecturas, imageColor));
            mList.add(new HomeItem("Comentarios", 6, R.drawable.ic_comentarios, colorUnique, R.id.nav_comentarios, imageColor));
            mList.add(new HomeItem("Calendario", 7, R.drawable.ic_calendario, colorUnique, R.id.nav_calendario, imageColor));
            mList.add(new HomeItem("Oraciones", 8, R.drawable.ic_oraciones, colorUnique, R.id.nav_oraciones, imageColor));
            mList.add(new HomeItem("Biblia", 9, R.drawable.ic_biblia, colorUnique, R.id.nav_biblia, imageColor));
            mList.add(new HomeItem("Patrística", 10, R.drawable.ic_patristica, colorUnique, R.id.nav_patristica, imageColor));
            mList.add(new HomeItem("Sacramentos", 11, R.drawable.ic_sacramentos, colorUnique, R.id.nav_sacramentos, imageColor));
            mList.add(new HomeItem("Más...", 12, R.drawable.ic_mas, colorUnique, R.id.nav_mas, imageColor));
        }
    }

    private void pickOutDate() {
        Bundle bundle = getArguments();
        String todayDate;
        if (bundle != null) {
            todayDate = bundle.getString("FECHA") == null ? Utils.getHoy() : bundle.getString("FECHA");
        } else {
            todayDate = Utils.getHoy();
        }
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Objects.requireNonNull(actionBar).setSubtitle(Utils.getTitleDate(todayDate));
    }

    private void setConfiguration() {
        SyncViewModel mViewModel = new ViewModelProvider(this).get(SyncViewModel.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false);
        boolean isAccept = prefs.getBoolean(PREF_ACCEPT, false);
        String theme = prefs.getString("theme", "1");
        if (!isInitialSync && isAccept) {
            mViewModel.initialSync();
            mViewModel.getInitialSyncStatus().observe(getViewLifecycleOwner(),
                    data -> {
                        boolean isSuccess = data > 0;
                        prefs.edit().putBoolean("initialSync", isSuccess).apply();
                    });
        }
        int dayNumber = Integer.parseInt(Utils.getDay(Utils.getHoy()));
        if (dayNumber >= 30) {
            int lastYearCleaned = prefs.getInt(PREF_LAST_YEAR_CLEANED, 0);
            long systemTime = System.currentTimeMillis();
            SimpleDateFormat sdfY = new SimpleDateFormat("yyyy", new Locale("es", "ES"));
            Date theDate = new Date(systemTime);
            int currentYear = Integer.parseInt(sdfY.format(theDate));

            if (lastYearCleaned == 0 || lastYearCleaned == currentYear - 1) {
                SimpleDateFormat sdfMd = new SimpleDateFormat("MMdd", new Locale("es", "ES"));
                int mmDD = Integer.parseInt(sdfMd.format(theDate));
                if (mmDD >= 1225) {
                    mViewModel.cleanUp(currentYear - 1);
                    mViewModel.getYearClean().observe(getViewLifecycleOwner(),
                            data -> {
                                if (data > 0) {
                                    prefs.edit().putInt(PREF_LAST_YEAR_CLEANED, currentYear).apply();
                                }
                            });
                }
            }
        }
        prepareItems(theme);

    }
}
