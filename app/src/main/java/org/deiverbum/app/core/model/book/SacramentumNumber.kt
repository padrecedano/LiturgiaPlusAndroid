package org.deiverbum.app.core.model.data.book

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

class SacramentumNumber {
    private var content: List<SacramentumContent>? = null
    private var n: String? = null

    fun getContent(): List<SacramentumContent>? {
        return content
    }

    fun setContent(content: List<SacramentumContent>?) {
        this.content = content
    }

    fun getAllForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH4(n))
        ssb.append(". ")
        for (c in content!!) {
            ssb.append(c.getAllForView())
        }
        ssb.append(LS2)
        return ssb
    }
}