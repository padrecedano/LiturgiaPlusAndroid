package org.deiverbum.app.presentation.today

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.databinding.FragmentBiblicalcommentBinding
import org.deiverbum.app.databinding.FragmentTodayBinding
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.presentation.homily.BiblicalCommentItemUiState
import org.deiverbum.app.presentation.homily.BiblicalCommentViewModel
import org.deiverbum.app.presentation.homily.TodayItemUiState
import org.deiverbum.app.presentation.homily.TodayViewModel
import org.deiverbum.app.utils.Utils
import timber.log.Timber

@AndroidEntryPoint
class TodayFragment : BaseFragment<FragmentTodayBinding>() {
    private val mViewModel: TodayViewModel by viewModels()

    override fun constructViewBinding(): ViewBinding = FragmentTodayBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        val args: TodayFragmentArgs by navArgs()
        Timber.d(args.hourId.toString())
        Log.d("XYZ",args.hourId.toString())
        mViewModel.loadData()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is TodayViewModel.TodayUiState.Loaded -> onLoaded(state.itemState)
                        is TodayViewModel.TodayUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(todayItemUiState: TodayItemUiState) {
        todayItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
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