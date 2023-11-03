package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Commentarii(
    val biblicaWithComments: MutableList<BiblicaWithComments>,
//    val biblicaWithComments: MutableList<List<BiblicaWithComments>>,

    //var homiliae: MutableList<List<BibleComment?>> = ArrayList(),

    override var typus: String = "commentarii"
    //,override var tempore: LiturgyTime
) : Traditio(typus) {
    override fun forView(calendarTime: Int): SpannableStringBuilder {
        var ssb = SpannableStringBuilder();
        ssb.append(Utils.toH1Red(Constants.TITLE_BIBLE_COMMENTS))
        //ssb.append(Utils.LS2)
        biblicaWithComments.forEach {
            if (it.homiliae.isNotEmpty()) {
                ssb.append(it.biblica.getAll(0))
                it.homiliae.forEach {
                    ssb.append(it?.getAllForView())
                }
            }
        }
        // ...


        return ssb
    }
}