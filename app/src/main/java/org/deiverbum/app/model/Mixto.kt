package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class Mixto : BreviaryHour() {
    var misaLecturas: List<MassReading?>? = null
    override fun getLaudes(): Laudes? {
        return laudes
    }

    override fun setLaudes(laudes: Laudes?) {
        this.laudes = laudes
    }

    override fun getOficio(): Oficio? {
        return oficio
    }

    override fun setOficio(oficio: Oficio?) {
        this.oficio = oficio
    }

    val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_MIXTO)
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_MIXTO)
    val evangeliosForView: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            for (item in misaLecturas!!) {
                ssb.append(item?.getAll(0))
            }
            return ssb
        }
    val evangeliosForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            for (item in misaLecturas!!) {
                sb.append(item?.getAllForRead())
            }
            return sb
        }
}