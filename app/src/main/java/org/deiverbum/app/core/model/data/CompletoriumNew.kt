package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 * Representación de las **`Completas`** para exponerla en la capa de datos externa.
 */
data class CompletoriumNew(val kyrie: Kyrie, val lhHymn: LHHymnNew) : BreviariumNew {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.LS2)
        ssb.append(Introitus.initialInvocationForView)
        ssb.append(Utils.LS2)
        ssb.append(kyrie.all)
        ssb.append(Utils.LS2)
        ssb.append(lhHymn.all)
        ssb.append(Utils.LS2)
        return ssb
    }
}