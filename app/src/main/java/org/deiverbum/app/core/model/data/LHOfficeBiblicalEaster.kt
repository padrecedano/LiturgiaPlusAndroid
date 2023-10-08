package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class LHOfficeBiblicalEaster : LectioBiblica(), Comparable<LHOfficeBiblicalEaster> {
    var psalm: LHPsalm? = null
    var theme: String? = null

    //public int theOrder;
    @Ignore
    var prayer: Oratio? = null
    override fun getHeader(): String {
        var header = ""
        if (order == 1) {
            header = "PRIMERA LECTURA"
        }
        if (order == 2) {
            header = "SEGUNDA LECTURA"
        }
        if (order == 3) {
            header = "TERCERA LECTURA"
        }
        if (order == 4) {
            header = "CUARTA LECTURA"
        }
        return header
    }

    override fun getResponsorioHeaderForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_RESPONSORY)
    }

    val biblical: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.formatTitle(getHeader()))
            sb.append(Utils.LS2)
            sb.append(book!!.liturgyName)
            sb.append("    ")
            sb.append(Utils.toRed(quote))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(theme))
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS)
            return sb
        }

    //sb.append(responsorioLargo.getAll());
    val biblicalForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(getHeader())
            sb.append(Utils.LS2)
            sb.append(book!!.liturgyName)
            sb.append("    ")
            sb.append(quote)
            sb.append(Utils.LS2)
            sb.append(theme)
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS)
            //sb.append(responsorioLargo.getAll());
            return sb
        }

    /**
     *
     * Obtiene la lectura bíblica completa formateada para la lectura de voz.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    override fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.pointAtEnd(getHeader()))
        sb.append(biblicalForRead)
        return sb
    }

    override fun getOrden(): Int? {
        return order
    }

    override fun setOrden(orden: Int?) {
        order = orden
    }

    override fun compareTo(other: LHOfficeBiblicalEaster): Int {
        return getOrden()!!.compareTo(other.getOrden()!!)
    }
}