package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.room.Ignore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Esta clase recoge toda la información sobre el día litúrgico de una fecha dada.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */

@JsonClass(generateAdapter = true)
data class Universalis(
    @Json(name = "todayDate") var todayDate: Int = 0,
    var timeFK: Int = 0,
    @Ignore
    var info: String? = null,
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
    var lCanticumFK: Int = 0,
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
    var vCanticumFK: Int = 0,
    var vIntercessionsFK: Int = 0,
    var vPrayerFK: Int = 0,
    var nightPrayerFK: Int = 71,

    @Ignore
    var liturgia: Liturgy? = null,

    //@Ignore
    //var liturgy: LiturgiaNew? = null,
    //org.deiverbum.app.core.model.data.Liturgy(),

    @Ignore
    var liturgyPrevious: Liturgy? = null,

    //@Ignore
    //var tempus: LiturgyTime? = null
) {
    /*constructor(todayDate: Int, liturgyTime: LiturgyTime?, timeFK: Int) : this() {
        this.todayDate = todayDate
        this.liturgyTime = liturgyTime
        this.timeFK = timeFK
    }*/

    constructor(todayDate: Int, liturgia: Liturgy, info: String) : this() {
        this.todayDate = todayDate
        this.liturgia = liturgia
        this.info = info
    }

    constructor(todayDate: Int, liturgia: Liturgy) : this() {
        this.todayDate = todayDate
        this.liturgia = liturgia
        //this.info = info
    }

    constructor(liturgia: Liturgy) : this() {
        this.liturgia = liturgia
    }

    val fecha: String
        get() = Utils.formatDate(todayDate.toString(), "yyyyMMdd", "EEEE d 'de' MMMM 'de' yyyy")


    fun getAllForView(): AnnotatedString {
        var s = LoremIpsum(5)
        val annotatedString = buildAnnotatedString {
            append("\n")
            append("\n")
            append("\n")
            append("\n")
            append("\n")
            append("\n")

            append(fecha)
            append("\n")
            append(todayDate.toString())
            append("\n")

            withStyle(style = SpanStyle(color = Red)) {
                append("Hello, ")
            }
            append("World!")
            //append(s.values.last())
            append(liturgia!!.liturgiaTypus!!.typus)
            //append(getAllForView(TodayRequest(1, 1, true, true)))

        }

        return annotatedString
    }


    fun getAllForView(todayRequest: TodayRequest): SpannableStringBuilder {

        ColorUtils.isNightMode = todayRequest.isNightMode
        val ssb = SpannableStringBuilder()
        if (info != null) {
            ssb.append(Utils.toH3Red(info!!))
            ssb.append(LS2)
        }
        try {
            if (liturgia?.liturgiaTypus?.typus != "sanctii") {
                ssb.append(fecha)
                ssb.append(Utils.LS2)
                ssb.append(Utils.toH2(liturgia?.tempus?.externus))
                ssb.append(Utils.LS2)
                ssb.append(Utils.toH3(liturgia?.nomen))
                ssb.append(Utils.LS2)
            }
            //ssb.append(liturgia?.liturgiaTypus?.forView(timeFK))
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }

        return ssb


    }

    fun getAllForRead(): StringBuilder {
        val sb = StringBuilder()
        try {

            if (liturgia?.liturgiaTypus?.typus != "sanctii") {
                sb.append(Utils.pointAtEnd(fecha))
                //sb.append(Utils.pointAtEnd(liturgia?.tempore?.liturgyName))
                sb.append(Utils.pointAtEnd(liturgia?.titleForRead))
            }
            sb.append(liturgia?.liturgiaTypus!!.forRead())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb

    }

}

fun Universalis.asEntity() = UniversalisEntity(
    todayDate = todayDate,
    timeFK = timeFK,
    liturgyFK = liturgyFK,
    previousFK = previousFK,
    massReadingFK = massReadingFK,
    invitatoryFK = invitatoryFK,
    hasSaint = hasSaint,
    saintFK = saintFK,
    oHymnFK = oHymnFK,
    oPsalmFK = oPsalmFK,
    oAntiphonFK = oAntiphonFK,
    oVerseFK = oVerseFK,
    oBiblicalFK = oBiblicalFK,
    oPatristicFK = oPatristicFK,
    oPrayerFK = oPrayerFK,
    oTeDeum = oTeDeum,
    lHymnFK = lHymnFK,
    lPsalmFK = lPsalmFK,
    lAntiphonFK = lAntiphonFK,
    lBiblicalFK = lBiblicalFK,
    lCanticumFK = lCanticumFK,
    lIntercessionsFK = lIntercessionsFK,
    lPrayerFK = lPrayerFK,
    tHymnFK = tHymnFK,
    tAntiphonFK = tAntiphonFK,
    tPsalmFK = tPsalmFK,
    tBiblicalFK = tBiblicalFK,
    tPrayerFK = tBiblicalFK,
    sHymnFK = sHymnFK,
    sAntiphonFK = sAntiphonFK,
    sPsalmFK = sPsalmFK,
    sBiblicalFK = sBiblicalFK,
    sPrayerFK = sPrayerFK,
    nHymnFK = nHymnFK,
    nAntiphonFK = nAntiphonFK,
    nPsalmFK = nPsalmFK,
    nBiblicalFK = nBiblicalFK,
    nPrayerFK = nPrayerFK,
    vHymnFK = vHymnFK,
    vAntiphonFK = vAntiphonFK,
    vPsalmFK = vPsalmFK,
    vBiblicalFK = vBiblicalFK,
    vCanticumFK = vCanticumFK,
    vIntercessionsFK = vIntercessionsFK,
    vPrayerFK = vPrayerFK,
    nightPrayerFK = nightPrayerFK
)
