package org.deiverbum.app.core.model.data.breviarium

import androidx.room.ColumnInfo

class LHIntercession(
    var intro: String = "",
    @ColumnInfo(name = "intercession")
    var preces: String = ""
) {
    var intercessionID: Int? = null

}

fun LHIntercession.normalizeForRead(): String {
    return preces
        .replace("ƞ", " ")
        .replace("≠", " ")

}