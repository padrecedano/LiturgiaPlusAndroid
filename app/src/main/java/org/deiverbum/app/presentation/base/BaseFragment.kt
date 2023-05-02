package org.deiverbum.app.presentation.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentTodayBinding
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.presentation.today.TodayFragmentArgs
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.utils.ZoomTextView
import java.util.*

abstract class BaseFragment<T> : Fragment() {

    private var viewBinding: ViewBinding? = null
    protected lateinit var todayRequest: TodayRequest
    protected lateinit var mTextView: ZoomTextView
    private var _binding: FragmentTodayBinding? = null
    //private var binding get() = _binding!!;
    private var progressBar: ProgressBar? = null
    private var mActionModee: ActionMode? = null
    var mActionMode: ActionMode? = null

    private val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            val menuItem = item.itemId
            if (menuItem == R.id.audio_play) {
                /*readText()
                audioMenu.findItem(R.id.audio_pause).setVisible(true)
                audioMenu.findItem(R.id.audio_stop).setVisible(true)*/
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_pause) {
                /*mTtsManager.pause()
                audioMenu.findItem(R.id.audio_resume).setVisible(true)*/
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_resume) {
                /*mTtsManager.resume()
                audioMenu.findItem(R.id.audio_pause).setVisible(true)*/
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_stop) {
                /*mTtsManager.stop()
                audioMenu.findItem(R.id.audio_play).setVisible(true)
                audioMenu.findItem(R.id.audio_pause).setVisible(false)
                audioMenu.findItem(R.id.audio_resume).setVisible(false)*/
                item.isVisible = false
                return true
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.contextual_action_bar, menu)
            //audioMenu = menu
            @SuppressLint("InflateParams") val view =
                LayoutInflater.from(context).inflate(R.layout.seekbar, null)
            mode.customView = view
            //seekBar = view.findViewById<SeekBar>(R.id.seekbar)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
            //cleanTTS()
            //setPlayerButton()
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater,container,false)

        //setConfiguration()


        viewBinding = constructViewBinding()
        viewBinding?.let { init(it) }
        return viewBinding?.root
    }

    @Suppress("UNCHECKED_CAST")
    fun getViewBinding(): T = viewBinding as T

    private fun pickOutDatec(): Int {
        val bundle = arguments
        val mDate = if (bundle != null && bundle.containsKey("FECHA")) {
            bundle.getInt("FECHA")
        } else {
            Utils.getHoy().toInt()
        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        Objects.requireNonNull<ActionBar?>(actionBar).subtitle =
            Utils.getTitleDate(mDate.toString())
        return mDate
    }

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