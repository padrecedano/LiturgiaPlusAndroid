package org.deiverbum.app.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.databinding.FragmentBugreportBinding
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import java.util.Locale
import java.util.Objects

@AndroidEntryPoint
class BugReportFragment : Fragment() {
    private var binding: FragmentBugreportBinding? = null
    private val prefs: SharedPreferences by lazy {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBugreportBinding.inflate(inflater, container, false)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.subtitle = ""

        //val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val fontSize = prefs.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            prefs.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        val initialText = binding!!.bugInitial
        val checkText = binding!!.bugCheck
        val finalText = binding!!.bugFinal
        initialText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        initialText.typeface = tf
        checkText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        checkText.typeface = tf
        finalText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        finalText.typeface = tf
        val button: Button = binding!!.btnSend
        val root: View = binding!!.root
        button.setOnClickListener { _: View? ->
            val left = binding!!.leftSide
            val right = binding!!.rightSide
            val selected: MutableList<CharSequence?> = ArrayList()
            for (i in 0 until left.childCount) {
                val checkBox = left.getChildAt(i) as CheckBox
                if (checkBox.isChecked) {
                    selected.add(checkBox.text)
                }
            }
            for (i in 0 until right.childCount) {
                val checkBox = right.getChildAt(i) as CheckBox
                if (checkBox.isChecked) {
                    selected.add(checkBox.text)
                }
            }
            val textSelected = TextUtils.join(", ", selected)
            val msg = String.format(
                "Mensaje: \n\n%s\n\nEn:\n\n%s", Objects.requireNonNull(
                    binding!!.message.text
                ), textSelected
            )
            composeEmail(arrayOf(Configuration.MY_EMAIL), Constants.ERR_SUBJECT, msg)
        }
        return root
    }

    private fun composeEmail(addresses: Array<String?>?, subject: String?, body: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
        requireActivity().onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}