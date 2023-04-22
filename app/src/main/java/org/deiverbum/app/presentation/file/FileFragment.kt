package org.deiverbum.app.presentation.file

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.databinding.FragmentHomiliasBinding
import org.deiverbum.app.presentation.base.BaseFileFragment
import timber.log.Timber

/**
 * <p>
 * Este Fragmento coordina la obtención del contenido proveniente de archivos locales.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.3
 */
@AndroidEntryPoint
class FileFragment : BaseFileFragment<FragmentHomiliasBinding>() {
    private val mViewModel: FileViewModel by viewModels()

    override fun constructViewBinding(): ViewBinding = FragmentHomiliasBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        mViewModel.loadData()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is FileViewModel.FileUiState.Loaded -> onLoaded(state.itemState)
                        is FileViewModel.FileUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(homeItemUiState: FileItemUiState) {
        homeItemUiState.run {
            getViewBinding().include.tvZoomable.text = allData
        }
    }

    private fun showLoading() {
        Timber.d("showLoading")
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}