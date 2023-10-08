package org.deiverbum.app.core.presentation.calendar

import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentCalendarioBinding
import org.deiverbum.app.util.Utils.formatDate
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CalendarioFragment : Fragment() {
    private var binding: FragmentCalendarioBinding? = null
    private var mDate: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentCalendarioBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val datePicker = binding!!.datePicker
        val loc = Locale("es", "ES")
        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ) { datePicker1: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            datePicker1.performLongClick()
            mDate = String.format(
                loc, "%d%02d%02d", year,
                month + 1, dayOfMonth
            )
            updateActionBar()
        }
        registerForContextMenu(datePicker)
        return root
    }

    private fun updateActionBar() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle =
            formatDate(mDate, "yyyyMMdd", "d '-' MMMM yyyy")
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.calendario_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 0) {
            return true
        }
        val bundle = Bundle()
        bundle.putInt("FECHA", mDate!!.toInt())
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(item.itemId, bundle)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}