package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils

class BibleBooks {
    var id = 0
    var name: String? = null
    var description: String? = null
    var intro: String? = null

    constructor() {}
    constructor(id: Int, name: String?, description: String?) {
        this.name = name
        this.id = id
        this.description = description
    }

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