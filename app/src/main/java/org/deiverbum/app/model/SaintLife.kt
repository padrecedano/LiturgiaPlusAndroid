package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class SaintLife {
    @get:PropertyName("vida")
    @set:PropertyName("vida")
    @PropertyName("vida")
    var longLife: String? = null

    @Ignore
    @PropertyName("nombre")
    var name: String? = null
    var saintFK: Int? = null
    var theSource: String? = ""

    @get:PropertyName("martirologio")
    @set:PropertyName("martirologio")
    @PropertyName("martirologio")
    var martyrology: String? = null


    @Ignore
    @PropertyName("dia")
    var dia: String? = null

    @Ignore
    var mes: String? = null

    //@PropertyName("vida")
    //@PropertyName("vida")
    @Ignore
    var shortLife: String? = null
    private val martirologioSpan: SpannableStringBuilder
        get() = Utils.toSmallSize(martyrology)
    private val vidaSpan: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.fromHtml("<hr>"))
            sb.append(Utils.toH3Red("Vida"))
            sb.append(Utils.LS2)
            sb.append(
                Utils.fromHtml(longLife!!.replace(
                    Constants.OLD_SEPARATOR.toRegex(), "")))
            return sb
        }
    private val lifeForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append("VIDA.")
            sb.append(
                Utils.fromHtml(longLife!!.replace(
                    Constants.OLD_SEPARATOR.toRegex(), "")))
            return sb
        }

    fun getForView(nightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = nightMode
        val sb = SpannableStringBuilder()
        try {
            sb.append(Utils.toH3Red(getMonthName(mes)))
            sb.append(Utils.LS2)
            sb.append(Utils.toH2Red(name))
            sb.append(Utils.LS2)
            sb.append(martirologioSpan)
            sb.append(Utils.LS)
            sb.append(martirologioTitleSpan)
            sb.append(Utils.LS2)
            sb.append(vidaSpan)
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtmlSmall(theSource))
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    private val martirologioTitleSpan: SpannableStringBuilder
        get() = Utils.toSmallSize("(Martirologio Romano)")
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(getMonthName(mes))
                sb.append(".")
                sb.append(name)
                sb.append(".")
                sb.append(martyrology)
                sb.append(martirologioTitleForRead)
                sb.append(lifeForRead)
                sb.append(theSource)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
    private val martirologioTitleForRead: String
        get() = "Martirologio Romano."

    private fun getMonthName(mes: String?): String {
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
        return String.format("%s de %s", dia, monthNames[theMonth])
    }
}