package org.deiverbum.app.core.model.data

import androidx.room.Ignore
import java.text.MessageFormat

class PaterOpus(
    var opusName: String = "",
    var liturgyName: String = ""

) {

    constructor(opusName: String, liturgyName: String, pater: Pater) : this(opusName, liturgyName) {
        this.pater = pater
    }

    var opusID: Int? = null

    @Ignore
    var pater: Pater? = null
    var subTitle: String? = null
    var opusDate: String? = null
    var volume: String? = null
    var editorial: String? = null
    var city: String? = null
    var opusYear: String? = null
    var paterFK: Int? = null
    var typeFK: Int? = null
    var collectionFK: Int? = null


    val opusForView: String
        get() =
            if (liturgyName != "") liturgyName else MessageFormat.format(
                "{0}, {1}",
                paterForView,
                opusName
            )

    val paterForView: String
        get() =
            if (pater?.liturgyName != null && pater?.liturgyName != "") pater?.liturgyName!! else pater?.pater!!

    val singleName: String
        get() = opusName
}