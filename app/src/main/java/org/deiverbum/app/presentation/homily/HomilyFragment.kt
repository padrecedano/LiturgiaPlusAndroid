package org.deiverbum.app.presentation.homily

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
import org.deiverbum.app.presentation.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class HomilyFragment : BaseFragment<FragmentHomiliasBinding>() {
    private val mViewModel: HomilyViewModel by viewModels()

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
                        is HomilyViewModel.HomilyUiState.Loaded -> onLoaded(state.itemState)
                        is HomilyViewModel.HomilyUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(homeItemUiState: BiblicalCommentItemUiState) {
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