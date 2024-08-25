package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
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
@JsonClass(generateAdapter = true)
data class LHIntermedia(
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var oratio: Oratio,
    var typusID: Int = 0,
    //@Json(ignore = true)
    override var typus: String = "intermedia"
) : Breviarium(typus) {
    val tituloHora: String
        get() = when (typusID) {
            4 -> Constants.TITLE_TERCIA
            5 -> Constants.TITLE_SEXTA
            6 -> Constants.TITLE_NONA
            else -> ""
        }
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(tituloHora)
    val tituloHoraForView: SpannableStringBuilder
        get() = Utils.toH1Red(tituloHora)

    fun forView(calendarTime: Int): SpannableStringBuilder {
        //this.typeID = typeID
        val sb = SpannableStringBuilder(tituloHoraForView)
        sb.append(Utils.LS2)

        //super.forView()
        //sb.append(this.li)
        //sb.append(parent.forView())
        try {
            lectioBrevis.normalizeByTime(calendarTime)
            //sb.append(tituloHoraForView)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            //sb.append(Utils.LS)
            sb.append(hymnus.all)
            sb.append(Utils.LS2)
            //sb.append(Utils.LS)
            sb.append(psalmodia.getAllForView(hourIndex, calendarTime))
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllWithHourCheck(typusID))
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

    override fun sort() {
        psalmodia.sort()
    }

    /**
     * Devuelve el índice de la hora para fines de LHPsalmody
     * por ejemplo, para determinar la antífona única en los
     * tiempos litúrgicos en que ésta aplica
     *
     * @return Un entero con el índice 0
     * @since 2022.1
     */
    val hourIndex: Int
        get() = when (typusID) {
            4 -> 1
            5 -> 2
            3 -> 0
            else -> 0
        }
}