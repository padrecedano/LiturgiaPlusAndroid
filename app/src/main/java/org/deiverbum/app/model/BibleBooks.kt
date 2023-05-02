package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils

class BibleBooks(var id: Int, var name: String?, var description: String?) {
    var intro: String? = null

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