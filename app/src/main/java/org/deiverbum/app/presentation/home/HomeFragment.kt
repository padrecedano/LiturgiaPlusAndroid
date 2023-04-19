package org.deiverbum.app.presentation.home

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.databinding.FragmentHomebisBinding
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.presentation.home.adapter.HomeAdapter
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomebisBinding>() {

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun constructViewBinding(): ViewBinding = FragmentHomebisBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        homeViewModel.getData()
        initUi()
        fetchData()
    }

    private fun initUi() {
        getViewBinding().homeRv.run {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState.collect { state ->
                    when (state) {
                        is HomeViewModel.HomeUiState.Loaded -> onLoaded(state.itemState)
                        is HomeViewModel.HomeUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(homeItemUiState: HomeItemUiState) {
        homeItemUiState.run {
            getViewBinding().titleTv.text = title
            //homeAdapter.update(allData)
        }
    }

    private fun showLoading() {
        Timber.d("showLoading")
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}