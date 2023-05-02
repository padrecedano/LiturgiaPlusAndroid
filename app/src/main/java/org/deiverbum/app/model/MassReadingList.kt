package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class MassReadingList : Liturgy() {
    @JvmField
    var type = 0
    var lecturas: List<MassReading?>? = null
    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red(Utils.toUpper(Constants.TITLE_MASS_READING))
    override val tituloForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_MASS_READING)
    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder("")
            for (b in lecturas!!) {
                sb.append(b?.getAll())
            }
            return sb
        }

    fun sort() {
        //Collections.sort(lecturas)
    }

    fun getForView(todayRequest:TodayRequest): SpannableStringBuilder {
        ColorUtils.isNightMode = todayRequest.isNightMode

        val sb = SpannableStringBuilder("")
        try {
            //sb.append(hoy.getForViewMisa());
            //sb.append(today?.singleForView)
            sb.append(Utils.LS2)
            sb.append(titulo)
            sb.append(Utils.LS2)
            for (l in lecturas!!) {
                sb.append(l?.getAll(type))
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //@Override
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(today?.getSingleForRead())
                sb.append(tituloForRead)
                for (l in lecturas!!) {
                    sb.append(l?.getAllForRead(type))
                }
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}