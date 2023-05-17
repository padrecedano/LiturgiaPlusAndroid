package org.deiverbum.app.presentation.legal

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentAcceptanceBinding
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.presentation.file.FileItemUiState
import org.deiverbum.app.presentation.file.FileViewModel
import org.deiverbum.app.presentation.sync.SyncItemUiState
import org.deiverbum.app.presentation.sync.SyncViewModel
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Constants.LS2
import org.deiverbum.app.utils.Constants.PREF_INITIAL_SYNC
import org.deiverbum.app.utils.Utils
import timber.log.Timber

/**
 *
 * Fragmento que muestra el diálogo inicial para la Aceptción de la
 * Política de Privacidad y los Términos y Condiciones de Uso.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@AndroidEntryPoint
class AcceptanceFragmentDialog : DialogFragment() {
    private var binding: FragmentAcceptanceBinding? = null
    private val mViewModel: FileViewModel by viewModels()
    private val syncViewModel: SyncViewModel by viewModels()
    private var textPrivacy: TextView? = null
    private var textTerms: TextView? = null
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }
//        val sp = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

     fun onCreateDialogg(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val ssb = SpannableStringBuilder()
            val intro = String.format(
                "%s %s%n%s",
                requireActivity().resources.getString(R.string.accept_intro),
                requireActivity().resources.getString(R.string.app_name),
                requireActivity().resources.getString(R.string.app_version_code_view)
            )
            ssb.append(Utils.toH3Red(intro))
            ssb.append(LS2)
            ssb.append(requireActivity().resources.getString(R.string.accept_info))
            builder.setMessage(ssb)
                .setPositiveButton(R.string.ok
                ) { _, _ ->
                    val syncRequest = SyncRequest(0, 1, isNightMode = false, isVoiceOn = false)
                    lifecycleScope.launch(Dispatchers.IO) {
                        syncViewModel.initialSync(syncRequest)
                        fetchDataSync()



                    }

                }
                .setNegativeButton(R.string.help
                ) { _, _ ->
                    // User cancelled the dialog
                }
                .apply {
                    this@AcceptanceFragmentDialog.isCancelable = true
                    setCancelable(true)
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    /*
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.accept_warning))
            .setPositiveButton(getString(R.string.ok)) { _,_ -> }
            .create()
*/




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        //mViewModel = ViewModelProvider(this)[FileViewModel::class.java]
        //syncViewModel = ViewModelProvider(this)[SyncViewModel::class.java]
        binding = FragmentAcceptanceBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        prepareView()
        //observeData()
        return root
    }

    private fun prepareView() {
        ColorUtils.isNightMode = isNightMode
        val sp = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val fontSize = sp.getString("font_size", "18")!!.toFloat()
        val textInitial = binding!!.textInitial
        textPrivacy = binding!!.textPrivacy
        textTerms = binding!!.textTerms
        val textFinal = binding!!.textFinal
        textInitial.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textPrivacy!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textTerms!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textFinal.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        val button: Button = binding!!.btnEmail
        val ssb = SpannableStringBuilder()
        val intro = String.format(
            "%s %s%n%s",
            requireActivity().resources.getString(R.string.accept_intro),
            requireActivity().resources.getString(R.string.app_name),
            requireActivity().resources.getString(R.string.app_version_code_view)
        )
        ssb.append(Utils.toH3Red(intro))
        ssb.append(LS2)
        ssb.append(requireActivity().resources.getString(R.string.accept_info))
        //textInitial.text = ssb
        val sb = SpannableStringBuilder()
        sb.append(Utils.toH2Red(requireActivity().resources.getString(R.string.title_acceptance)))
        sb.append(LS2)
        sb.append(requireActivity().resources.getString(R.string.accept_warning))
        //textFinal.setText(sb, TextView.BufferType.SPANNABLE)

        val termsRequest = FileRequest("raw/terms_202301.json", 1, 6, isNightMode, isVoiceOn = false, false)
        //mViewModel.loadData(termsRequest)
        //fetchData()

        val privacyRequest = FileRequest("raw/privacy_202301.json", 1, 6, isNightMode, isVoiceOn = false, false)
        //mViewModel.loadData(privacyRequest)
        //fetchData()
        button.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean(Constants.PREF_ACCEPT, true).apply()
            var isInitialSync = prefs.getBoolean(PREF_INITIAL_SYNC, false)

            //fetchDataSync()
            //isInitialSync=false
            if (!isInitialSync) {
                val syncRequest = SyncRequest(0, 1, isNightMode = false, isVoiceOn = false)
                lifecycleScope.launch(Dispatchers.IO) {
                    syncViewModel.initialSync(syncRequest)
                    fetchDataSync()
                }
                //fetchDataSync()

                //syncViewModel.initialSync(syncRequest)
                /*syncViewModel.initialSyncStatus.observe(
                    viewLifecycleOwner
                ) { data: Int ->
                    val isSuccess = data > 0
                    sp.edit().putBoolean("initialSync", isSuccess).apply()
                }*/
            }
            //dismiss()
        }
    }


    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is FileViewModel.FileUiState.Loaded -> onLoaded(listOf(state.itemState))
                        is FileViewModel.FileUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }
    private fun onLoaded(fileItemUiStates: List<FileItemUiState>) {
        fileItemUiStates.forEach { fileItemUiState ->
            if (fileItemUiState.allData.fileName == "raw/terms_202301.json") {
                //binding?.textTerms?.text = fileItemUiState.allData.text
            } else if (fileItemUiState.allData.fileName == "raw/privacy_202301.json") {
                //binding?.textPrivacy?.text = fileItemUiState.allData.text
            }
        }
    }

    private fun fetchDataSync() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoadedSync(state.itemState)
                        is SyncViewModel.SyncUiState.Error -> showError(state.message.toInt())
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoadedSync(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            if(allData.status==1){
                prefs.edit().putBoolean("initialSync", true).apply()
            }
            Timber.d(syncItemUiState.allData.dataForView.toString())
            dismiss()

            //binding.progressBar.visibility = View.GONE
            //getViewBinding().include.tvZoomable.text = allData.text
        }
    }

    private fun showLoading() {
        //mTextView.text = Constants.PACIENCIA
    }

    private fun showError(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    private fun observeData() {
        /*
        mViewModel.getBook(FILE_PRIVACY).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textPrivacy.setText(book.getForView(isNightMode()), TextView.BufferType.SPANNABLE);
                    } else {
                        textPrivacy.setText(Utils.fromHtml(data.getError()));
                    }
                });

        mViewModel.getBook(FILE_TERMS).observe(this,
                data -> {
                    if (data.status == DataWrapper.Status.SUCCESS) {
                        Book book = data.getData();
                        textTerms.setText(book.getForView(isNightMode()),
                                TextView.BufferType.SPANNABLE);
                    } else {
                        textTerms.setText(Utils.fromHtml(data.getError()));
                    }
                });
        */
    }

    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }

    companion object {
        private const val TAG = "AcceptanceFragmentDialog"
        fun display(fragmentManager: FragmentManager?) {
            val acceptanceFragmentDialog = AcceptanceFragmentDialog()
            acceptanceFragmentDialog.isCancelable = false
            acceptanceFragmentDialog.show(fragmentManager!!, TAG)
        }
    }
}