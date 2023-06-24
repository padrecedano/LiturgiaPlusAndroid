package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import java.util.*

class LHIntercession {
    var intercessionID: Int? = null
    var intro: String? = null
    var intercession: String? = null
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            val introArray =
                intro!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (introArray.size == 3) {
                sb.append(introArray[0])
                sb.append(Utils.LS2)
                sb.append(Utils.fromHtml(String.format(Locale("es"), "<i>%s</i>", introArray[1])))
                sb.append(Utils.LS2)
                sb.append(getIntercessionForView())
                sb.append(Utils.LS2)
                sb.append(introArray[2])
            } else {
                sb.append(intro)
                sb.append(Utils.LS2)
                sb.append(getIntercessionForView())
            }
            return sb
        }

    private fun getIntercessionForView(): Spanned {
        return Utils.fromHtml(Utils.getFormato(intercession))
    }


    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_INTERCESSIONS)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_INTERCESSIONS)
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(headerForRead)
            sb.append(Utils.LS2)
            val introArray =
                intro!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (introArray.size == 3) {
                sb.append(introArray[0])
                sb.append(introArray[1])
                sb.append(getIntercessionForView())
                sb.append(introArray[2])
            } else {
                sb.append(intro)
                sb.append(getIntercessionForView())
            }
            return sb
        }
}