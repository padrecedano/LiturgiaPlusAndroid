package org.deiverbum.app.core.presentation.today

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.TypedValue
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.presentation.base.BaseFragment
import org.deiverbum.app.databinding.FragmentTodayBinding
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.PACIENCIA
import org.deiverbum.app.util.TtsManager
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.ZoomTextView
import java.util.Locale

/**
 * Este Fragmento coordina la obtención de la liturgia del día sin importar el módulo, el cual se determinará por el valor de un id proveniente en un argumento y con la ayuda de [org.deiverbum.app.util.LiturgyHelper].
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */

@AndroidEntryPoint
class TodayFragment : BaseFragment<FragmentTodayBinding>() {
    private val mViewModel: TodayViewModel by viewModels()
    private lateinit var mTextView: ZoomTextView
    private var progressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null
    private var isReading = false
    private var isVoiceOn = false
    private var hasInvitatory = false
    private var sbReader: StringBuilder? = null
    private var audioMenu: Menu? = null
    private var voiceItem: MenuItem? = null
    private var mTtsManager: TtsManager? = null
    private var mainMenu: Menu? = null
    private var mActionMode: ActionMode? = null
    private lateinit var todayRequest: TodayRequest

    val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            val menuItem = item.itemId
            if (menuItem == R.id.audio_play) {
                readText()
                audioMenu?.findItem(R.id.audio_pause)?.isVisible = true
                audioMenu?.findItem(R.id.audio_stop)?.isVisible = true
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_pause) {
                mTtsManager?.pause()
                audioMenu?.findItem(R.id.audio_resume)?.isVisible = true
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_resume) {
                mTtsManager?.resume()
                audioMenu?.findItem(R.id.audio_pause)?.isVisible = true
                item.isVisible = false
                return true
            }
            if (menuItem == R.id.audio_stop) {
                mTtsManager?.stop()
                audioMenu?.findItem(R.id.audio_play)?.isVisible = true
                audioMenu?.findItem(R.id.audio_pause)?.isVisible = false
                audioMenu?.findItem(R.id.audio_resume)?.isVisible = false
                item.isVisible = false
                return true
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.contextual_action_bar, menu)
            audioMenu = menu
            @SuppressLint("InflateParams") val view: View =
                LayoutInflater.from(context).inflate(R.layout.seekbar, null)
            mode.customView = view
            seekBar = view.findViewById(R.id.seekbar)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            mActionMode = null
            cleanTTS()
            setPlayerButton()
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }
    }

    override fun constructViewBinding(): ViewBinding = FragmentTodayBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        setConfiguration()
        setMenu()
        mViewModel.loadData(todayRequest)
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is TodayViewModel.TodayUiStateOldVersion.Loaded -> onLoaded(state.itemState)
                        is TodayViewModel.TodayUiStateOldVersion.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(todayItemUiState: TodayItemUiState) {
        todayItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            if (todayResponse.success) {
                mTextView.text = todayResponse.dataModel.getAllForView(todayRequest)
                //mTextView.text = todayResponse.dataModel.getAllForView(false,false)

                if (isVoiceOn) {
                    sbReader = todayResponse.dataModel.getAllForRead()
                }
            } else {
                val msgNoData = activity?.resources?.getString(R.string.err_no_data)
                mTextView.text = msgNoData
            }
        }
    }

    private fun showLoading() {
        mTextView.text = PACIENCIA
    }

    private fun showError(stringRes: String) {
        mTextView.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
                mainMenu = menu
                voiceItem = menu.findItem(R.id.item_voz)
                voiceItem!!.isVisible = isVoiceOn
                if (isReading) {
                    voiceItem!!.isVisible = false
                }

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        navController.popBackStack()
                        true
                    }
                    R.id.item_voz -> {
                        if (mActionMode == null) {
                            mActionMode =
                                requireActivity().startActionMode(mActionModeCallback)
                        }
                        readText()
                        isReading = true
                        voiceItem?.isVisible = false
                        requireActivity().invalidateOptionsMenu()
                        true
                    }
                    else -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        NavigationUI.onNavDestinationSelected(menuItem, navController)
                    }
                }

            }
        }, viewLifecycleOwner)
    }

    private fun setConfiguration() {
        //val args: TodayFragmentArgs by navArgs()
        mTextView = getViewBinding().tvZoomable
        progressBar = getViewBinding().progressBar
        val sp = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val fontSize = sp?.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            sp.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView.typeface = tf
        hasInvitatory = sp.getBoolean("invitatorio", false)
        isVoiceOn = sp.getBoolean("voice", true)
        todayRequest =
            TodayRequest(pickOutDate(), 1, isNightMode(), hasInvitatory)
    }

    private fun setPlayerButton() {
        voiceItem!!.isVisible = isVoiceOn
    }

    private fun readText() {
        mTtsManager = TtsManager(
            context,
            sbReader.toString(),
            Constants.SEPARADOR
        ) { current: Int, max: Int ->
            seekBar!!.progress = current
            seekBar!!.max = max
        }
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mTtsManager == null) return
                mTtsManager!!.changeProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        mTtsManager!!.start()
    }

    //fun onError() {}

    private fun cleanTTS() {
        mTtsManager?.close()
    }

    private fun pickOutDate(): Int {
        val bundle = arguments
        val mDate = if (bundle != null && bundle.containsKey("FECHA")) {
            bundle.getInt("FECHA")
        } else {
            Utils.hoy.toInt()
        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = Utils.formatDate(mDate.toString(), "yyyyMMdd", "d '-' MMMM yyyy")
        return mDate
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        cleanTTS()
    }

}