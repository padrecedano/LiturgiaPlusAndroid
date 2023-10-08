package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils

class LHSanctus(
    var nomen: String = "",
    var vitaBrevis: String = ""
) {

    fun getForView(nightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = nightMode
        val sb = SpannableStringBuilder()
        try {

            sb.append(vitaBrevis)

            //sb.append(Utils.fromHtmlSmall(theSource))
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val forRead: StringBuilder
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