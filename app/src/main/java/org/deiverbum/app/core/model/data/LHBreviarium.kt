package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 * Representación de **`Breviario`** para exponerla en la capa de datos externa.
 */
data class LHBreviarium(
    val lhHymn: LHHymnNew
) {
    fun getForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(lhHymn.hymn)
        return ssb
    }

    fun forRead(): StringBuilder {
        return StringBuilder("")
    }
}

/**
open class LHBreviarium {
//val himnoId: Int,
//val himno: String
lateinit var lhHymn: LHHymn

}
 */