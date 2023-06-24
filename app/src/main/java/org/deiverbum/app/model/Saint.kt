package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class Saint {
    var saintID: Int? = null

    @Ignore
    var liturgyFK: Int? = null
    var typeFK: Int? = null
    var theDay: String? = null
    var theMonth: String? = null

    //@Ignore
    var theName: String = ""

    @Ignore
    var vida: String? = null

    //private String martirologio;
    @Ignore
    var crg = false
    val vidaSmall: Spanned
        get() = if (vida != "") {
            Utils.fromHtml("<p><small>$vida</small></p>")
        } else {
            Utils.fromHtml("")
        }

    @Suppress("unused")
    fun getVidaForView(): Spanned {
        return Utils.fromHtml(vida)
    }


    //martirologio;
    private val martirologio: String
        get() = "" //martirologio;

    fun setDay(dia: String?) {
        theDay = dia
    }

    fun setMonth(mes: String?) {
        theMonth = mes
    }

    private val martirologioSpan: SpannableStringBuilder
        get() = Utils.toSmallSize("martirologio")
    private val martirologioTitleSpan: SpannableStringBuilder
        get() = Utils.toSmallSize("(Martirologio Romano)")
    private val martirologioTitleForRead: String
        get() = "Martirologio Romano."
    private val vidaSpan: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.fromHtml("<hr>"))
            sb.append(Utils.toH3Red("Vida"))
            sb.append(Utils.LS2)
            sb.append(
                Utils.fromHtml(vida!!.replace(
                    Constants.OLD_SEPARATOR.toRegex(), "")))
            return sb
        }
    private val vidaForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append("VIDA.")
            sb.append(
                Utils.fromHtml(vida!!.replace(
                    Constants.OLD_SEPARATOR.toRegex(), "")))
            return sb
        }
    val forView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            try {
                sb.append(Utils.toH3Red(monthName))
                sb.append(Utils.LS2)
                sb.append(Utils.toH2Red(theName))
                sb.append(Utils.LS2)
                sb.append(martirologioSpan)
                sb.append(Utils.LS)
                sb.append(martirologioTitleSpan)
                sb.append(Utils.LS2)
                sb.append(vidaSpan)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(monthName)
                sb.append(".")
                sb.append(theName)
                sb.append(".")
                sb.append(martirologio)
                sb.append(martirologioTitleForRead)
                sb.append(vidaForRead)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    private val monthName: String
        get() {
            val monthNames = HashMap<String?, String>()
            monthNames["01"] = "Enero"
            monthNames["02"] = "Febrero"
            monthNames["03"] = "Marzo"
            monthNames["04"] = "Abril"
            monthNames["05"] = "Mayo"
            monthNames["06"] = "Junio"
            monthNames["07"] = "Julio"
            monthNames["08"] = "Agosto"
            monthNames["09"] = "Septiembre"
            monthNames["10"] = "Octubre"
            monthNames["11"] = "Noviembre"
            monthNames["12"] = "Diciembre"
            return String.format("%s de %s", theDay, monthNames[theMonth])
        }

    @Suppress("unused")
    fun getMonthName(mes: String?): String {
        val monthNames = HashMap<Int, String>()
        monthNames[1] = "Enero"
        monthNames[2] = "Febrero"
        monthNames[3] = "Marzo"
        monthNames[4] = "Abril"
        monthNames[5] = "Mayo"
        monthNames[6] = "Junio"
        monthNames[7] = "Julio"
        monthNames[8] = "Agosto"
        monthNames[9] = "Septiembre"
        monthNames[10] = "Octubre"
        monthNames[11] = "Noviembre"
        monthNames[12] = "Diciembre"
        val theMonth = Integer.valueOf(mes!!)
        return String.format("%s de %s", theDay, monthNames[theMonth])
    }
}