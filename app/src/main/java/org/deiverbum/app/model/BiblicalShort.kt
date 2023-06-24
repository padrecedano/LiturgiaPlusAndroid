package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class BiblicalShort : Biblical() {
    //public String forma;
    private var responsorio: LHResponsoryShort? = null
    fun getHeaderLectura(): SpannableStringBuilder {
        //String s=String.format(new Locale("es"),"%s    %s",TITLE_SHORT_READING,getRefBreve());
        val ssb = SpannableStringBuilder(
            Utils.formatTitle(
                Constants.TITLE_SHORT_READING))
        ssb.append("    ")
        ssb.append(Utils.toRed(getRefBreve()))
        return ssb
    }

    fun getHeaderForRead(): String {
        return "LECTURA BREVE."
    }

    fun setResponsorio(responsorio: LHResponsoryShort?) {
        this.responsorio = responsorio
    }

    fun getAllWithHourCheck(hourId: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderLectura())
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        sb.append(Utils.LS2)
        sb.append(responsorio!!.getAll(hourId))
        //sb.append(getResponsorio().getAll());
        return sb
    }

    override fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderForRead())
        sb.append(textoForRead)
        sb.append(responsorio!!.allForRead)
        //sb.append(getResponsorioForRead());
        return sb
    }
}