package org.deiverbum.app.model

import androidx.room.Ignore
import java.text.MessageFormat

class PaterOpus {
    var opusID: Int? = null
    @JvmField
    var opusName: String? = null
    @JvmField
    var liturgyName: String? = null
    var subTitle: String? = null
    var opusDate: String? = null
    var volume: String? = null
    var editorial: String? = null
    var city: String? = null
    var opusYear: String? = null
    var paterFK: Int? = null
    var typeFK: Int? = null
    var collectionFK: Int? = null

    @Ignore
    var pater: Pater? = null
    val opusForView: String
        get() = if (liturgyName != null && liturgyName != "") liturgyName!! else MessageFormat.format(
            "{0}, {1}",
            pater?.pater,
            opusName
        )

    val paterForView: String
        get() = if (pater?.liturgyName!=null && pater?.liturgyName!="") pater?.liturgyName!! else pater?.pater!!

    val singleName: String
        get() = if (opusName != null) opusName!! else ""
}