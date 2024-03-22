package org.deiverbum.app.core.model.data.cic

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class IurisCanonici(
    var title: String = "",

    var titles: List<Title>

) {
    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH2Red(title))
        ssb.append(Constants.LS2)
        for (t in titles) {
            ssb.append(t.getForView(isNightMode))
        }
        ssb.append(Constants.LS2)
        return ssb
    }
}