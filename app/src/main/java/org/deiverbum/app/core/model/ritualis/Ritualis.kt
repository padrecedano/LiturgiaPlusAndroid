package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class Ritualis(
    var title: String = "",

    var sections: List<Section>

) {
    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH2Red(title))
        ssb.append(Constants.LS2)
        for (s in sections) {
            ssb.append(s.getForView(isNightMode))
        }
        ssb.append(Constants.LS2)
        return ssb
    }
}