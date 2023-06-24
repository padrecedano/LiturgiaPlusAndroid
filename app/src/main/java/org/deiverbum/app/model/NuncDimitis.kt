package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class NuncDimitis : LHPsalm() {
    //private String antiphonEntity;
    var texto: String? = null
        get() = Utils.getFormato(field)

    private fun getAntifonaSpan(timeID: Int): SpannableStringBuilder {
        val ssb = SpannableStringBuilder("")
        ssb.append(Utils.toRed("Ant. "))
        ssb.append(Utils.replaceByTime(antiphon, timeID))
        return ssb
    }

    override val antifonaForRead: String
        get() = antiphon!!
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_GOSPEL_CANTICLE)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_GOSPEL_CANTICLE)

    fun getAll(idTiempo: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(header)
        sb.append(Utils.LS2)
        sb.append(getAntifonaSpan(idTiempo))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(texto))
        sb.append(Utils.LS2)
        sb.append(finSalmo)
        sb.append(Utils.LS2)
        sb.append(getAntifonaSpan(idTiempo))
        return sb
    }

    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(headerForRead)
            sb.append(".")
            sb.append(antifonaForRead)
            sb.append(Utils.fromHtml(texto))
            sb.append(finSalmoForRead)
            sb.append(antifonaForRead)
            return sb
        }
}