package org.deiverbum.app.core.model.data.alteri

import android.text.SpannableStringBuilder
import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

open class Sanctus(
    @ColumnInfo(name = "theMonth")
    var mensis: Int = 0,
    @ColumnInfo(name = "theDay")
    var dies: Int = 0,
    @ColumnInfo(name = "theName")
    var nomen: String = "",
    @ColumnInfo(name = "martirologio")
    @Ignore val martyrologyum: String = "",
    @Ignore
    var vita: String = ""
) {
    var saintID: Int? = null

    @Ignore
    var liturgyFK: Int? = null
    var typeFK: Int? = null

    @Ignore
    var crg = false

    private val martirologioSpan: SpannableStringBuilder
        get() = Utils.toSmallSize(martyrologyum)
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
                Utils.fromHtml(
                    vita.replace(
                        Constants.OLD_SEPARATOR.toRegex(), ""
                    )
                )
            )
            return sb
        }
    open val vidaForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append("VIDA.")
            sb.append(
                Utils.fromHtml(
                    vita.replace(
                        Constants.OLD_SEPARATOR.toRegex(), ""
                    )
                )
            )
            return sb
        }
    open val forView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            try {
                sb.append(Utils.toH3Red(monthName))
                sb.append(Utils.LS2)
                sb.append(Utils.toH2Red(nomen))
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
    open val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(monthName)
                sb.append(".")
                sb.append(nomen)
                sb.append(".")
                sb.append(martyrologyum)
                sb.append(martirologioTitleForRead)
                sb.append(vidaForRead)
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    val monthName: String
        get() {
            val monthNames = mapOf(
                1 to "Enero",
                2 to "Febrero",
                3 to "Marzo",
                4 to "Abril",
                5 to "Mayo",
                6 to "Junio",
                7 to "Julio",
                8 to "Agosto",
                9 to "Septiembre",
                10 to "Octubre",
                11 to "Noviembre",
                12 to "Diciembre"
            )
            return "$dies de ${monthNames.getValue(mensis)}"
        }

    fun forRead(): StringBuilder {
        val sb = StringBuilder("Lorem")
        return sb
    }

}