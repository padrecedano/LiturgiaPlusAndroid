package org.deiverbum.app.presentation.legal

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.databinding.FragmentLegalBinding
import org.deiverbum.app.model.Book
import org.deiverbum.app.utils.Configuration
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.presentation.legal.LegalViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 *
 *
 * Este Fragmento maneja lo relativo a documentos legales.
 * Extrae el contenido de un archivo .json y muestra la Política de
 * Privacidad o los Términos y Condiciones de Uso, según el caso.
 *
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@AndroidEntryPoint
class LegalFragment : Fragment() {
    private var mViewModel: LegalViewModel? = null
    private var binding: FragmentLegalBinding? = null
    private var mTextView: TextView? = null
    private var textAgree: TextView? = null
    private var textContacto: TextView? = null
    private var acceptLegal = false
    private var bottomLayout: LinearLayout? = null
    private var agreeYes: String? = null
    private var agreeNot: String? = null
    private var progressBar: ProgressBar? = null
    private var switchAccept: SwitchMaterial? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLegalBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        prepareView()
        observeBook()
        return root
    }

    private fun prepareView() {
        mViewModel = ViewModelProvider(this).get(LegalViewModel::class.java)
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            activity
        )
        acceptLegal = sp.getBoolean(Constants.PREF_ACCEPT, false)
        val fontSize: Float = sp.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            sp.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        progressBar = binding!!.progressBar
        switchAccept = binding!!.switchAccept
        /*switchAccept.setChecked(acceptLegal)
        switchAccept.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            acceptLegal = isChecked
            setAcceptText()
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putBoolean(Constants.PREF_ACCEPT, isChecked)
            editor.apply()
        })*/
        mTextView = binding!!.textLegal
        //mTextView.setMovementMethod(LinkMovementMethod.getInstance())
       // mTextView.setClickable(true)
        textAgree = binding!!.textAgree
        textContacto = binding!!.textContacto
        bottomLayout = binding!!.bottomLayout
        /*mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView.setTypeface(tf)
        textAgree.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textAgree.setTypeface(tf)
        textContacto.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        textContacto.setTypeface(tf)
        bottomLayout.setVisibility(View.GONE)*/
        val button: Button = binding!!.btnEmail
        button.setOnClickListener { v: View? ->
            val subject = String.format(
                Locale.getDefault(), "Dudas " +
                        "Política Privacidad y/o " +
                        "Términos y Condiciones Liturgia+ v. %d", Constants.VERSION_CODE
            )
            composeEmail(arrayOf(Configuration.MY_EMAIL), subject)
        }
    }

    private fun observeBook() {
/*        progressBar.setVisibility(View.VISIBLE)
        mTextView.setText(Constants.PACIENCIA)
        if (arguments != null) {
            val filePath = arguments!!.getString("rawPath")
            mViewModel.getBook(filePath).observe(
                viewLifecycleOwner
            ) { data ->
                progressBar.setVisibility(View.GONE)
                if (data.status === DataWrapper.Status.SUCCESS) {
                    val book: Book = data.getData()
                    mTextView.setText(book.getForView(isNightMode), TextView.BufferType.SPANNABLE)
                    //saveHtmlFile(book.getForHtml().toString());
                    agreeYes = book.getAgreeYes()
                    agreeNot = book.getAgreeNot()
                    //footLayout.setVisibility(View.VISIBLE);
                } else {
                    mTextView.setText(Utils.fromHtml(data.getError()))
                }
                textContacto.setText(Constants.MSG_LEGAL)
                bottomLayout.setVisibility(View.VISIBLE)
                setAcceptText()
            }
        }*/
    }

    private fun saveHtmlFile(html: String) {
        val path = requireActivity().getExternalFilesDir(null)!!.absolutePath
        var fileName =
            DateFormat.format("dd_MM_yyyy_hh_mm_ss", System.currentTimeMillis()).toString()
        fileName = "$fileName.html"
        val file = File(path, fileName)
        try {
            val out = FileOutputStream(file)
            val data = html.toByteArray()
            out.write(data)
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setAcceptText() {
        val acceptText = if (acceptLegal) agreeYes else agreeNot
        textAgree?.setText(acceptText)
        if (!acceptLegal) {
            showConfirm()
        }
    }

    private fun showConfirm() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireActivity())
        materialAlertDialogBuilder.setTitle(Constants.DIALOG_LEGAL_TITLE)
        materialAlertDialogBuilder.setMessage(Constants.DIALOG_LEGAL_BODY)
        materialAlertDialogBuilder.setPositiveButton(
            Constants.DIALOG_LEGAL_OK,
            DialogInterface.OnClickListener { dialogInterface: DialogInterface?, i: Int -> closeApp() })
        materialAlertDialogBuilder.setNegativeButton(
            Constants.DIALOG_LEGAL_CANCEL,
            DialogInterface.OnClickListener { dialogInterface: DialogInterface?, i: Int -> updateSwitch() })
        materialAlertDialogBuilder.show()
    }

    private fun closeApp() {
        requireActivity().finishAndRemoveTask()
    }

    private fun updateSwitch() {
        switchAccept?.setChecked(true)
    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, "Plantea a continuación tu duda: \n\n")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
        requireActivity().onBackPressed()
    }

    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}