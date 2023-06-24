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
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.ZoomTextView
import java.util.*

/**
 * <p>Fragmento base para el manejo de archivos.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
abstract class BaseFileFragment<T> : Fragment() {

    private var viewBinding: ViewBinding? = null
    protected lateinit var todayRequest: TodayRequest
    protected lateinit var mTextView: ZoomTextView
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private var progressBar: ProgressBar? = null
    private var mActionMode: ActionMode? = null

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

        setConfiguration()


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

    private fun setConfiguration() {
        val args: TodayFragmentArgs by navArgs()
        //Timber.d(args.hourId.toString())

        mTextView = _binding!!.include.tvZoomable
        progressBar = _binding?.progressBar
        val sp = activity?.getPreferences(Context.MODE_PRIVATE)


        val fontSize = sp?.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            sp.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView.typeface = tf
        val hasInvitatory = sp.getBoolean("invitatorio", false)
        val isVoiceOn = sp.getBoolean("voice", true)
        todayRequest =
            TodayRequest(pickOutDatec(), args.hourId, isNightMode(), isVoiceOn, hasInvitatory)

        //pickOutDate()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    abstract fun constructViewBinding(): ViewBinding
    abstract fun init(viewBinding: ViewBinding)
}