package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los tres horas intermedias del salterio:
 * **`Tercia`**, **`Sexta`** y **`Nona`** en la capa de datos externa.
 *
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property horaId Un entero para identificar la hora:
 *           `3` para `Tercia`, `4` para `Sexta` y `5` para `Nona`.
 */

data class LHIntermedia(
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var oratio: Oratio,
    var typeID: Int = 0
) : Breviarium {
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

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        //this.typeID = typeID
        val sb = SpannableStringBuilder()
        try {
            lectioBrevis.normalizeByTime(calendarTime)
            sb.append(tituloHoraForView)
            sb.append(Utils.LS2)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(hymnus.all)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(psalmodia.getAllForView(hourIndex, calendarTime))
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllWithHourCheck(typeID))
            sb.append(Utils.LS)
            sb.append(oratio.all)
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
            sb.append(RitusConclusionis.viewBenedicamusDomino)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //sb.append(today.getAllForRead());
    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(tituloHoraForRead)
            sb.append(initialInvocationForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead(hourIndex))
            sb.append(lectioBrevis.getAllForRead())
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readBenedicamusDomino)
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