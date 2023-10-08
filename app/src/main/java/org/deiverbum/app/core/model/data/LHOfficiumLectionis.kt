package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

open class LHOfficiumLectionis(
    open var biblica: MutableList<LHOfficiumLectioPrior>,
    open var patristica: MutableList<LHOfficiumLectioAltera>,
    open var responsorio: String = "",
    open var hasTeDeum: Boolean = false
) {
    //var responsorio: String = ""
    @get:Ignore
    private val responsorioForRead: String
        get() {
            val r: String = if (responsorio.contains("|")) {
                responsorio.replace("\\|".toRegex(), "")
            } else {
                responsorio
            }
            return Utils.pointAtEnd(r)
        }

    @get:Ignore
    private val responsorioSpan: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            if (responsorio.contains("|")) {
                val textParts = responsorio.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
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

    @get:Ignore
    open val header: SpannableStringBuilder?
        get() = Utils.formatSubTitle(
            Utils.toLower(
                Constants.TITLE_OFFICE_OF_READING
            )
        )

    @get:Ignore
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

    @get:Ignore
    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(responsorioSpan)
            sb.append(Utils.LS2)
            sb.append(allBiblicaForView)
            sb.append(allPatristicaForView)
            if (hasTeDeum) {
                sb.append(TeDeum().all)
                sb.append(Utils.LS2)
            }
            return sb
        }

    @get:Ignore
    open val allBiblicaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (oneBiblica in biblica) {
                //oneBiblica?.responsorioLargo?.all
                sb.append(oneBiblica.getAll())
            }
            return sb
        }

    open fun getAllBiblica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (oneBiblica in biblica) {
            //oneBiblica?.responsorioLargo?.normalizeByTime(calendarTime)
            sb.append(oneBiblica.getAll())
        }
        return sb
    }

    @get:Ignore
    open val allBiblicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (oneBiblica in biblica) {
                sb.append(oneBiblica.getAllForRead())
            }
            return sb
        }

    @get:Ignore
    private val allPatristicaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (theModel in patristica) {
                sb.append(theModel.all)
            }
            return sb
        }

    private fun getAllPatristica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (theModel in patristica) {
            theModel.responsorioLargo?.normalizeByTime(calendarTime)
            sb.append(theModel.all)
        }
        return sb
    }

    @get:Ignore
    private val allPatristicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (theModel in patristica) {
                sb.append(theModel.allForRead)
            }
            return sb
        }

    @get:Ignore
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
        for (oneBiblica in biblica) {
            oneBiblica.responsorioLargo.normalizeByTime(calendarTime)
        }
        responsorio = Utils.replaceByTime(responsorio, calendarTime)
    }
}