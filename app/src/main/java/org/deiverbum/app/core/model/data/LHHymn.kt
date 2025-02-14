package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class LHHymn(
    @ColumnInfo(name = "hymn")
    var hymnus: String = ""
) {

    var hymnID: Int = 0

    @Json(ignore = true)
    private val textoSpan: Spanned
        get() = Utils.fromHtml(hymnus)
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_HYMN)

    @Json(ignore = true)
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

