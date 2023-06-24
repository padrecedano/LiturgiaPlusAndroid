package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class Intermedia : BreviaryHour() {
    var lecturaBreve: BiblicalShort? = null
    private val tituloHora: String
        get() = when (typeID) {
            3 -> Constants.TITLE_TERCIA
            4 -> Constants.TITLE_SEXTA
            5 -> Constants.TITLE_NONA
            else -> ""
        }
    private val tituloHoraForRead: String
        get() = Utils.pointAtEnd(tituloHora)
    private val tituloHoraForView: SpannableStringBuilder
        get() = Utils.toH1Red(tituloHora)

    fun getForView(liturgyTime: LiturgyTime, typeID: Int): SpannableStringBuilder {
        this.typeID = typeID
        val sb = SpannableStringBuilder()
        try {
            salmodia!!.normalizeByTime(liturgyTime.timeID)
            sb.append(Utils.LS2)
            sb.append(tituloHoraForView)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(getSaludoDiosMio())
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(himno?.all)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(salmodia!!.getAll(hourIndex))
            sb.append(Utils.LS)
            sb.append(lecturaBreve!!.getAllWithHourCheck(typeID))
            sb.append(Utils.LS)
            sb.append(oracion!!.all)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)

            sb.append(getConclusionHoraMenor())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //sb.append(today.getAllForRead());
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                //sb.append(today.getAllForRead());
                sb.append(tituloHoraForRead)
                sb.append(getSaludoDiosMioForRead())
                sb.append(himno?.allForRead)
                sb.append(salmodia!!.getAllForRead(hourIndex))
                sb.append(lecturaBreve!!.getAllForRead())
                sb.append(oracion!!.allForRead)
                sb.append(getConclusionHoraMenorForRead())
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    /**
     * Devuelve el índice de la hora para fines de LHPsalmody
     * por ejemplo, para determinar la antífona única en los
     * tiempos litúrgicos en que ésta aplica
     *
     * @return Un entero con el índice 0
     * @since 2022.1
     */
    private val hourIndex: Int
        get() = when (typeID) {
            4 -> 1
            5 -> 2
            3 -> 0
            else -> 0
        }
}