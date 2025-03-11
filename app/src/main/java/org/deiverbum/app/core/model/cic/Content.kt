package org.deiverbum.app.core.model.data.cic

import android.text.SpannableStringBuilder

data class Content(
    var chapters: List<Chapter>,

    ) {
    fun getAllForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        for (c in chapters) {
            ssb.append(c.getAllForView())
        }
        return ssb
    }
}
