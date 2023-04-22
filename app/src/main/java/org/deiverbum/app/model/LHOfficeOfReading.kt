package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

open class LHOfficeOfReading {
    var responsorio: String? = null
    var biblica: List<LHOfficeBiblical?>? = null
    var patristica: List<LHOfficePatristic?>? = null
    var teDeum: TeDeum? = null
    val responsorioForRead: String
        get() {
            val r: String? = if (responsorio!!.contains("|")) {
                responsorio!!.replace("\\|".toRegex(), "")
            } else {
                responsorio
            }
            return Utils.pointAtEnd(r)
        }
    val responsorioSpan: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            if (responsorio!!.contains("|")) {
                val textParts = responsorio!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (textParts.size == 2) {
                    ssb.append(Utils.toRed("V. "))
                    ssb.append(textParts[0])
                    ssb.append(Utils.LS)
                    ssb.append(Utils.toRed("R. "))
                    ssb.append(textParts[1])
                } else {
                    ssb.append(responsorio)
                }
            } else {
                ssb.append(responsorio)
            }
            return ssb
        }
    open val header: SpannableStringBuilder?
        get() = Utils.formatSubTitle(Utils.toLower(Constants.TITLE_OFFICE_OF_READING))
    open val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFFICE_OF_READING)

    open fun getAll(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(header)
        sb.append(Utils.LS2)
        sb.append(responsorioSpan)
        sb.append(Utils.LS2)
        sb.append(getAllBiblica(calendarTime))
        sb.append(getAllPatristica(calendarTime))
        return sb
    }

    open fun getAllBiblica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (oneBiblica in biblica!!) {
            oneBiblica?.responsorioLargo?.normalizeByTime(calendarTime)
            sb.append(oneBiblica?.getAll())
        }
        return sb
    }

    open val allBiblicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (oneBiblica in biblica!!) {
                sb.append(oneBiblica?.getAllForRead())
            }
            return sb
        }

    fun getAllPatristica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (theModel in patristica!!) {
            theModel?.responsorioLargo?.normalizeByTime(calendarTime)
            sb.append(theModel?.all)
        }
        return sb
    }

    val allPatristicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (theModel in patristica!!) {
                sb.append(theModel?.allForRead)
            }
            return sb
        }
    open val allForRead: String
        get() = headerForRead +
                responsorioForRead +
                allBiblicaForRead +
                allPatristicaForRead

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        for (oneBiblica in biblica!!) {
            oneBiblica?.responsorioLargo?.normalizeByTime(calendarTime)
        }
        responsorio = Utils.replaceByTime(responsorio, calendarTime)
    }
}