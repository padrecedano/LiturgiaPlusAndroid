package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

class LHSanctus(
    var nomen: String = "",
    var vitaBrevis: String = ""
) {

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