package org.deiverbum.app.core.model.data.traditio

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Homilieae(
    val biblicaWithComments: MutableList<Homily>?,


    override var typus: String = "homilieae"
) : Traditio(typus) {
    fun forView(calendarTime: Int): SpannableStringBuilder {
        var ssb = SpannableStringBuilder()
        ssb.append(Utils.toH1Red(Constants.TITLE_BIBLE_COMMENTS))
        //ssb.append(Utils.LS2)
        biblicaWithComments!!.forEach {

            ssb.append(it.getAllForView())
        }


        // ...


        return ssb
    }

    override fun sort() {
        biblicaWithComments!!.forEach {
            /*if (it.homiliae.isNotEmpty()) {
                it.sort()
            }*/
        }
    }
}