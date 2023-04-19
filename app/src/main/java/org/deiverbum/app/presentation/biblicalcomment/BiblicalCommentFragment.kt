package org.deiverbum.app.presentation.biblicalcomment

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.databinding.FragmentBiblicalcommentBinding
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.presentation.homily.BiblicalCommentItemUiState
import org.deiverbum.app.presentation.homily.BiblicalCommentViewModel
import org.deiverbum.app.utils.Utils
import timber.log.Timber

@AndroidEntryPoint
class BiblicalCommentFragment : BaseFragment<FragmentBiblicalcommentBinding>() {
    private val mViewModel: BiblicalCommentViewModel by viewModels()

    override fun constructViewBinding(): ViewBinding = FragmentBiblicalcommentBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        mViewModel.loadData()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is BiblicalCommentViewModel.BiblicalCommentUiState.Loaded -> onLoaded(state.itemState)
                        is BiblicalCommentViewModel.BiblicalCommentUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(homeItemUiState: BiblicalCommentItemUiState) {
        homeItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            getViewBinding().include.tvZoomable.text = Utils.fromHtml(allData.toString())
        }
    }

    private fun showLoading() {
        Timber.d("showLoading")


    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}