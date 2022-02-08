package org.deiverbum.app.ui.fragments;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.FragmentMisaBinding;
import org.deiverbum.app.viewmodel.MisaViewModel;

public class MisaFragment extends Fragment {


    private MisaViewModel mViewModel;
    private FragmentMisaBinding binding;
    private String mDate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MisaViewModel.class);
        binding = FragmentMisaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.calendario_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("FECHA", mDate);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(item.getItemId(),bundle);
        return true;
    }
}