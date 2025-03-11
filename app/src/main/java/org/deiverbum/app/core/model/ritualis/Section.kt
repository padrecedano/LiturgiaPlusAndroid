package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class Section(
    var title: String = "",
    var subTitle: String? = null,
    var level: Int? = null,
    var numbers: List<Number>
    /*var intro: List<Intro>,
    var content: List<Content>,*/

) {
    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        if (title != "") {
            if (level != null && level == 2) {
                ssb.append(Utils.toH2Red(title))
            } else {
                ssb.append(Utils.toH3Red(title))
            }

            ssb.append(Constants.LS2)

            if (subTitle != null) {
                ssb.append(Utils.toH4(subTitle))
                ssb.append(Constants.LS2)
            }
        }
        for (i in numbers) {
            ssb.append(i.getForView())
        }/*
        for (c in content) {
            ssb.append(c.getAllForView())
        }*/
        return ssb
    }
}