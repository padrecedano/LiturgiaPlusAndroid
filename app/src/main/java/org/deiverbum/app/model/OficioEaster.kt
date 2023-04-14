package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class OficioEaster : BreviaryHour() {
    var lhOfficeOfReadingEaster: LHOfficeOfReadingEaster? = null

    val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_OFICIO)
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFICIO)


    val forView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            try {

                sb.append(Utils.LS2)
                sb.append(tituloHora)
                sb.append(Utils.fromHtmlToSmallRed(metaInfo))
                sb.append(Utils.LS2)
                sb.append(lhOfficeOfReadingEaster!!.getAll(6))
                       } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(lhOfficeOfReadingEaster!!.allForRead)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}