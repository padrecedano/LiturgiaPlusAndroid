package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.utils.Utils.replaceByTime

open class LHPsalm : Comparable<LHPsalm> {
    var psalmID = 0
    var readingID = 0
    var quote: String? = ""

    @Ignore
    var antiphon: String? = null
    get() = if (field != null) field else ""
    var psalm = ""

    @Ignore
    var theOrder: String? = null
        get() = if (field != null) field else ""

    @Ignore
    var theme: String? = null
        get() = if (field != null) field else ""

    @Ignore
    var epigraph: String? = null
        get() = if (field != null) field else ""

    @Ignore
    var part: String? = null
        get() = if (field != null) field else ""


    open val antifonaForRead: String?
        get() = if (antiphon != null) Utils.normalizeEnd(antiphon) else ""

    //Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
    val ref: SpannableStringBuilder
        get() = if (quote != null) {
            SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(quote))) //Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            SpannableStringBuilder("")
        }

    fun setRef(ref: String?) {
        quote = ref
    }

    /**
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    val finSalmo: Spanned
        get() {
            val fin = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." + Constants.BR
                    + Constants.NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                    + Constants.NBSP_SALMOS + "por los siglos de los siglos. Amén.")
            return Utils.fromHtml(fin)
        }
    val finSalmoForRead: String
        get() = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.")

    override fun compareTo(other: LHPsalm): Int {
        return theOrder!!.compareTo(other.theOrder!!)
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        antiphon = if(antiphon.isNullOrEmpty()) "" else replaceByTime(antiphon, calendarTime)
    }
}