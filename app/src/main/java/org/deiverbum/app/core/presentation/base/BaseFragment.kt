package org.deiverbum.app.core.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.work.WorkInfo
import androidx.work.WorkManager
import org.deiverbum.app.databinding.FragmentTodayBinding
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants
import java.util.*
import java.util.concurrent.ExecutionException

/**
 * Fragmento base para el módulo Today.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

abstract class BaseFragment<T> : Fragment() {

    private var viewBinding: ViewBinding? = null

    //protected lateinit var todayRequest: TodayRequest
    private var _binding: FragmentTodayBinding? = null

    //private var progressBar: ProgressBar? = null
    private var mActionModee: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //ContextCompat.getColor(requireActivity().applicationContext, R.color.colorRubrica)

        //applicationContext .getColor(R.color.redish));
        //requireActivity().applicationContext.resources.getColor(R.color.colorRubrica)
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
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

    protected val isWorkScheduled: Boolean
        get() {
            val instance =
                WorkManager.getInstance(requireActivity().applicationContext)
            val statuses = instance.getWorkInfosByTag(Constants.SYNC_TAG)
            return try {
                var running = false
                val workInfoList = statuses.get()
                for (workInfo in workInfoList) {
                    val state = workInfo.state
                    running =
                        (state == WorkInfo.State.RUNNING) or (state == WorkInfo.State.ENQUEUED)
                }
                running
            } catch (e: ExecutionException) {
                false
            } catch (e: InterruptedException) {
                false
            }
        }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                ColorUtils.isNightMode = false
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                ColorUtils.isNightMode = true
            }
        }
    }

}