package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.util.Utils

class LHSanctus(
    var nomen: String = "",
    var vitaBrevis: String = ""
) {

    fun getComposable(): AnnotatedString {
        return buildAnnotatedString {
            append(Utils.toSmallSize(vitaBrevis))
            append(Utils.LS2)
        }
    }

    val forViewVitaBrevis: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            try {
                ssb.append(Utils.toSmallSize(vitaBrevis))
                ssb.append(Utils.LS2)
            } catch (e: Exception) {
                ssb.append(Utils.createErrorMessage(e.message))
            }
            return ssb
        }


    val forReadVitaBrevis: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(vitaBrevis)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    private val martirologioTitleForRead: String
        get() = "Martirologio Romano."


}