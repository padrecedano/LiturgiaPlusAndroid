package org.deiverbum.app.core.model.book

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class BookSacramentum(
    var cic: String = "",
    var numbers: List<SacramentumNumber>? = null,
    var title: String = ""
) {
    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = isNightMode

        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH2Red(title))
        ssb.append(LS2)

        //ssb.append(Utils.toH1Red("Código de Derecho Canónico"))
        //ssb.append(Utils.fromHtmlWithOutFormat(cic))
        for (c in numbers!!) {
            ssb.append(c.getAllForView())
        }
        return ssb
    }
}