package org.deiverbum.app.core.model.cic

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.ritualis.Content
import org.deiverbum.app.core.model.ritualis.ContentBody
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

data class Canon(
    var n: Int = 0,
    override var type: String = "",
    var txt: List<String>? = null,
    var list: List<String>? = null,
    var paragraphMix: List<Body>? = null,

    ) : ContentBody(type) {
    fun getForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        if (type == "body") {
            ssb.append(Utils.toRed("$n. "))
            for (t in txt!!) {
                ssb.append(t)
                ssb.append(LS2)
            }
        }

        if (type == "paragraph") {
            ssb.append(Utils.toRed("$n. "))
            txt!!.forEachIndexed { i, s ->
                ssb.append(Utils.toRed("§ ${i + 1}. "))
                ssb.append(s)
                ssb.append(LS2)
            }
        }
        if (type == "paragraphMix") {
            ssb.append(Utils.toRed("$n. "))
            for (t in paragraphMix!!) {
                //ssb.append(t.getForView())
                //ssb.append(LS2)
            }
            paragraphMix!!.forEachIndexed { i, s ->
                ssb.append(Utils.toRed("§ ${i + 1}. "))
                ssb.append(s.getForView())
                //ssb.append(LS2)
            }
        }
        if (type == "bodyAndList") {
            ssb.append(Utils.toRed("$n. "))
            for (t in txt!!) {
                ssb.append(t)
                ssb.append(LS2)
            }
            list!!.forEachIndexed { i, s ->
                ssb.append(Utils.toRed("${i + 1}. "))
                ssb.append(s)
                ssb.append(LS2)
            }

        }
        return ssb
    }
}

data class CanonBody(override var type: String = "body", val txt: List<String>) : Content(type)

