package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class Prayer {
    var prayerID: Int? = null
    var order: Int? = null
    var prayer: String? = null
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PRAYER)
    val all: Spanned
        get() {
            val sb = SpannableStringBuilder("")
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(prayer))
            return sb
        }
    val allForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PRAYER) +
                Utils.fromHtml(prayer)
}