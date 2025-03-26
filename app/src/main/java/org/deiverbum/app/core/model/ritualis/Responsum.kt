package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Responsum(
    //override var type: String = "vr",

    var v: String,
    var r: String? = null

) /*: Content(type)*/ {
    fun forView(code: String?): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        //if(code=="vr") {
        ssb.append(Utils.toRed("℣. "))
        ssb.append(v)
        ssb.append(Constants.LS)
        if (r != null) {
            ssb.append(Utils.toRed("℟. "))
            ssb.append(r)
            //ssb.append(Constants.LS)
        }
        //}
        return ssb
    }

}
