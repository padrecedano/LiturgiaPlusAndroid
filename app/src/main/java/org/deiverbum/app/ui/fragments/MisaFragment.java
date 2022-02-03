package org.deiverbum.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.FragmentMisaBinding;

import org.deiverbum.app.viewmodel.MisaViewModel;

import java.util.Calendar;

public class MisaFragment extends Fragment {

    private static final String TAG = "MisaFragment";

    private MisaViewModel mViewModel;
    private FragmentMisaBinding binding;
    private String mDate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MisaViewModel.class);
        binding = FragmentMisaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DatePicker datePicker = binding.datePicker;

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                datePicker.performLongClick();
                mDate = String.format("%d%02d%02d",year,month+1,dayOfMonth);
                Log.d("Date", mDate);

            }
        });
        this.registerForContextMenu(datePicker);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
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