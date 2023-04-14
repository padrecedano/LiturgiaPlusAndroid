package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class LHHymn {
    var hymnID: Int? = null

    var hymn: String? = null
        set(value) {
            field = value
        }
        get() = field


    constructor() {}
    constructor(hymn: String?) {
        this.hymn = hymn
    }

    val textoSpan: Spanned
        get() = Utils.fromHtml(Utils.getFormato(hymn))
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_HYMN)
    val headerForRead: String
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