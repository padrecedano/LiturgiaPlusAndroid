package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants.VOICE_INI
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Esta clase recoge toda la información sobre el día litúrgico de una fecha dada.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */

class Universalis(
    var todayDate: Int = 0,
    var timeFK: Int = 0,
    @Ignore
    var weekDayFK: Int = 0,
    var liturgyFK: Int = 0,
    var previousFK: Int = 0,
    var massReadingFK: Int = 0,
    var invitatoryFK: Int = 0,

    //@Ignore
    var hasSaint: Int = 0,
    var saintFK: Int = 0,
    var oHymnFK: Int = 0,
    var oAntiphonFK: Int = 0,
    var oPsalmFK: Int = 0,
    var oVerseFK: Int = 0,
    var oBiblicalFK: Int = 0,
    var oPatristicFK: Int = 0,
    var oPrayerFK: Int = 0,
    var oTeDeum: Int = 0,
    var lHymnFK: Int = 0,
    var lAntiphonFK: Int = 0,
    var lPsalmFK: Int = 0,
    var lBiblicalFK: Int = 0,
    var lBenedictusFK: Int = 0,
    var lIntercessionsFK: Int = 0,
    var lPrayerFK: Int = 0,
    var tHymnFK: Int = 0,
    var tAntiphonFK: Int = 0,
    var tPsalmFK: Int = 0,
    var tBiblicalFK: Int = 0,
    var tPrayerFK: Int = 0,
    var sHymnFK: Int = 0,
    var sAntiphonFK: Int = 0,
    var sPsalmFK: Int = 0,
    var sBiblicalFK: Int = 0,
    var sPrayerFK: Int = 0,
    var nHymnFK: Int = 0,
    var nAntiphonFK: Int = 0,
    var nPsalmFK: Int = 0,
    var nBiblicalFK: Int = 0,
    var nPrayerFK: Int = 0,
    var vHymnFK: Int = 0,
    var vAntiphonFK: Int = 0,
    var vPsalmFK: Int = 0,
    var vBiblicalFK: Int = 0,
    var vMagnificatFK: Int = 0,
    var vIntercessionsFK: Int = 0,
    var vPrayerFK: Int = 0,
    var nightPrayerFK: Int = 71,

    @Ignore
    var liturgyDay: Liturgy = Liturgy(),

    //@Ignore
    //var liturgy: LiturgiaNew? = null,
    //org.deiverbum.app.core.model.data.Liturgy(),

    @Ignore
    var liturgyPrevious: Liturgy? = null,

    @Ignore
    var liturgyTime: LiturgyTime? = null
) {
    constructor(todayDate: Int, liturgyTime: LiturgyTime?, timeFK: Int) : this() {
        this.todayDate = todayDate
        this.liturgyTime = liturgyTime
        this.timeFK = timeFK
    }

    private val tituloVisperas: String
        get() = if (liturgyPrevious != null) {
            liturgyPrevious!!.name.replace(" I Vísperas.| I Vísperas".toRegex(), "")
        } else {
            liturgyDay.name
        }
    val titulo: String
        get() =
            if (liturgyDay.typeID == 6) tituloVisperas else liturgyDay.name

    private val tituloForRead: String
        get() = if (liturgyDay.typeID == 6) tituloVisperas else liturgyDay.titleForRead

    val fecha: String
        get() = Utils.formatDate(todayDate.toString(), "yyyyMMdd", "EEEE d 'de' MMMM 'de' yyyy")

    val tiempo: String?
        get() = if (liturgyDay.typeID == 6 && liturgyPrevious != null) liturgyPrevious!!.liturgyTime!!.liturgyName else liturgyTime?.liturgyName

    private fun hasSaintToday(): Boolean {
        return hasSaint == 1
    }

    fun getAllForView(todayRequest: TodayRequest): SpannableStringBuilder {

        ColorUtils.isNightMode = todayRequest.isNightMode
        val ssb = SpannableStringBuilder()
        try {
            ssb.append(fecha)
            ssb.append(Utils.LS2)
            ssb.append(Utils.toH2(tiempo))
            ssb.append(Utils.LS2)
            ssb.append(Utils.toH3(titulo))
            ssb.append(Utils.LS2)
            ssb.append(liturgyDay.liturgyType?.forView(timeFK, hasSaint == 1))
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }

        return ssb


    }

    fun getAllForRead(hasInvitatory: Boolean): StringBuilder {
        val sb = StringBuilder(VOICE_INI)
        sb.append(Utils.pointAtEnd(fecha))
        sb.append(Utils.pointAtEnd(tiempo))
        sb.append(Utils.pointAtEnd(titulo))
        sb.append(liturgyDay.liturgyType!!.forRead())

        return sb

    }

}