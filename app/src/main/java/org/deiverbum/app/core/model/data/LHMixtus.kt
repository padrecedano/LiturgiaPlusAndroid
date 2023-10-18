package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

data class LHMixtus(
    var hymnus: LHHymn,
    override var typus: String = "mixtum"
    //,override var tempore: LiturgyTime
) : Breviarium(typus) {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.LS2)
        ssb.append(Introitus.initialInvocationForView)
        ssb.append(hymnus.all)
        ssb.append(Utils.LS2)
        return ssb
    }

    override fun forRead(): StringBuilder {
        return StringBuilder("")
    }
}

/*
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
}*/