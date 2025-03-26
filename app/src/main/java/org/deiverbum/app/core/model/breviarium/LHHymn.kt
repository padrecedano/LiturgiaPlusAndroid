package org.deiverbum.app.core.model.breviarium

import androidx.room.ColumnInfo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LHHymn(
    @ColumnInfo(name = "hymn")
    var hymnus: String = ""
) {
    var hymnID: Int = 0
}

