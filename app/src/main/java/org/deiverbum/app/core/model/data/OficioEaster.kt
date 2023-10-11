package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class OficioEaster {
    var lhOfficeOfReadingEaster: LHOfficiumPascua? = null

    private val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_OFICIO)

    @Suppress("unused")
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFICIO)


    val forView: SpannableStringBuilder
        get() {
            //lhOfficeOfReadingEaster!!.lhPsalmody.sort()
            val sb = SpannableStringBuilder()
            try {

                sb.append(Utils.LS2)
                sb.append(tituloHora)
                //sb.append(Utils.fromHtmlToSmallRed(metaInfo))
                sb.append(Utils.LS2)
                //sb.append(lhOfficeOfReadingEaster!!.getAll(6))
                       } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                //sb.append(lhOfficeOfReadingEaster!!.allForRead)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}