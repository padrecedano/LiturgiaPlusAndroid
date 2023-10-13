package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class LHLectioBrevis(
    //override var book:BibleBook,
    override var quote: String = "",
    override var text: String = "",
    var responsorium: LHResponsoriumBrevis
) : LectioBiblica(quote, text) {
    //public String forma;
    //private var responsorio: LHResponsoriumBrevis? = null
    private fun getHeaderLectura(): SpannableStringBuilder {
        //String s=String.format(new Locale("es"),"%s    %s",TITLE_SHORT_READING,getRefBreve());
        val ssb = SpannableStringBuilder(
            Utils.formatTitle(
                Constants.TITLE_SHORT_READING
            )
        )
        ssb.append("    ")
        ssb.append(Utils.toRed(getRefBreve()))
        return ssb
    }

    private fun getHeaderForRead(): String {
        return "LECTURA BREVE."
    }

    fun setResponsorio(responsorio: LHResponsoriumBrevis?) {
    }

    fun getAllWithHourCheck(hourId: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderLectura())
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        sb.append(Utils.LS2)
        sb.append(responsorium.getAll(hourId))
        //sb.append(getResponsorio().getAll());
        return sb
    }

    override fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderForRead())
        sb.append(textoForRead)
        sb.append(responsorium.allForRead)
        //sb.append(getResponsorioForRead());
        return sb
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        responsorium.text = Utils.replaceByTime(responsorium.text, calendarTime)
    }
}