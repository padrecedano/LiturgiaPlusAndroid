package org.deiverbum.app.ui.fragments;

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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.deiverbum.app.R;
import org.deiverbum.app.databinding.FragmentCalendarioBinding;

import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CalendarioFragment extends Fragment {
    private FragmentCalendarioBinding binding;
    private String mDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCalendarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DatePicker datePicker = binding.datePicker;

        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), (datePicker1, year, month, dayOfMonth) -> {
            datePicker1.performLongClick();
            mDate = String.format(Locale.getDefault(),"%d%02d%02d",year,
                    month+1,dayOfMonth);
            Log.d("Date", mDate);
        });
        this.registerForContextMenu(datePicker);
        return root;
    }

    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.calendario_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putString("FECHA", mDate);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(item.getItemId(),bundle);
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}