package org.deiverbum.app.core.presentation.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.util.*

/**
 * Fragmento base para el manejo de las páginas iniciales.
 *
 * @author A. Cedano
 * @since 2024.1
 */
abstract class BaseHomeFragment<T> : Fragment() {
    private var viewBinding: ViewBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = constructViewBinding()
        viewBinding?.let { init(it) }
        return viewBinding?.root
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewBinding(): T = viewBinding as T

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    abstract fun constructViewBinding(): ViewBinding
    abstract fun init(viewBinding: ViewBinding)
}