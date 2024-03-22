package org.deiverbum.app.core.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.databinding.FragmentTodayBinding
import java.util.*

/**
 * <p>Fragmento base para el manejo de archivos.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
abstract class BaseFileFragment<T> : Fragment() {

    private var viewBinding: ViewBinding? = null
    protected lateinit var todayRequest: TodayRequest
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private var progressBar: ProgressBar? = null
    private var mActionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater,container,false)
        viewBinding = constructViewBinding()
        viewBinding?.let { init(it) }
        return viewBinding?.root
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewBinding(): T = viewBinding as T

    open fun isNightMode(): Boolean {
        val nightModeFlags =
            requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    abstract fun constructViewBinding(): ViewBinding
    abstract fun init(viewBinding: ViewBinding)
}