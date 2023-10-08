package org.deiverbum.app.core.presentation.biblia

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.TypedValue
import android.view.*
import android.widget.ProgressBar
import android.widget.SeekBar
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
import org.deiverbum.app.core.model.BibleBookRequest
import org.deiverbum.app.core.presentation.base.BaseFragment
import org.deiverbum.app.databinding.FragmentTextBinding
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.TtsManager
import org.deiverbum.app.util.ZoomTextView
import java.util.*


/**
 * Este Fragmento coordina la obtención de los datos del módulo **Biblia**.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */

@AndroidEntryPoint
class BibliaDataFragment : BaseFragment<FragmentTextBinding>() {
    private val mViewModel: BibliaViewModel by viewModels()
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
    private lateinit var mRequest: BibleBookRequest
    private var mActionMode: ActionMode?=null

    override fun constructViewBinding(): ViewBinding = FragmentTextBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        setConfiguration()
        setMenu()
        mViewModel.loadData(mRequest)
        fetchData()
    }

    private fun setConfiguration() {
        updateSubTitle()
        val hourId = requireArguments().getInt("bookId")
        mTextView = getViewBinding().include.tvZoomable
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
        mRequest = BibleBookRequest(hourId)
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is BibliaViewModel.BibliaUiState.Loaded -> onLoaded(state.itemState)
                        is BibliaViewModel.BibliaUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(todayItemUiState: BibliaItemUiState) {
        todayItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            mTextView.text = bibleBookResponse.getForView(isNightMode())
        }
    }

    private fun showLoading() {
        mTextView.text = Constants.PACIENCIA
    }

    private fun showError(stringRes: String) {
        mTextView.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

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
        seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mTtsManager == null) return
                mTtsManager!!.changeProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        mTtsManager!!.start()
    }

    fun onError() {}

    private fun cleanTTS() {
        mTtsManager?.close()
    }

    private fun updateSubTitle() {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        cleanTTS()
    }

}