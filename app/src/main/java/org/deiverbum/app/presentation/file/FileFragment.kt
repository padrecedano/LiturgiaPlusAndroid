package org.deiverbum.app.presentation.file

import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.databinding.FragmentFileBinding
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.presentation.base.BaseFileFragment
import org.deiverbum.app.utils.Constants
import java.util.*

/**
 * <p>
 * Este Fragmento coordina la obtención de datos que provienen de archivos
 * guardados en el directorio res/raw.
 * </p>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */

@AndroidEntryPoint
class FileFragment : BaseFileFragment<FragmentFileBinding>() {
    private val mViewModel: FileViewModel by viewModels()

    override fun constructViewBinding(): ViewBinding =
        FragmentFileBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        setConfiguration()
        fetchData()
    }

    private fun setConfiguration() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val args: FileFragmentArgs by navArgs()
        val filePath = args.rawPath
        val fileRequest = FileRequest(filePath, 1, 6, isNightMode(), true, true)

        if (filePath == "raw/rosario.json") {
            fileRequest.dayOfWeek = requireArguments().getInt("id")
            fileRequest.isBrevis = sp.getBoolean("brevis", true)
        }
        val fontSize = sp?.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            sp.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView.typeface = tf
        mViewModel.loadData(fileRequest)
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

    private fun onLoaded(fileItemUiState: FileItemUiState) {
        fileItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            getViewBinding().include.tvZoomable.text = allData.text
        }
    }

    private fun showLoading() {
        mTextView.text = Constants.PACIENCIA

    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }
}