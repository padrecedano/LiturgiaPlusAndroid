package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class LHHymn(
    var hymn: String = ""
) {
    var hymnID: Int = 0
    private val textoSpan: Spanned
        get() = Utils.fromHtml(hymn)
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_HYMN)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_HYMN)
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            return sb
        }
    val allForRead: String
        get() = headerForRead +
                textoSpan
}