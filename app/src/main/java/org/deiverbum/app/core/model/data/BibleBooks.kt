package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils

class BibleBooks(var id: Int=0, var name: String="", var description: String="") {
    var intro: String = ""

    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = isNightMode
        val sb = SpannableStringBuilder()
        sb.append(Utils.fromHtml(intro))
        return sb
    }

    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(
                Utils.fromHtml(
                    Utils.stripQuotation(
                        intro
                    )
                )
            )
            return sb
        }
}