package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.utils.Numerals
import org.deiverbum.app.utils.Utils
import java.util.*

open class Liturgy {

    @JvmField
    @Ignore
    @PropertyName("typeId")
    var typeID = 0

    @Ignore
    var liturgyTime: LiturgyTime? = null

    @JvmField
    @Ignore
    var santo: Saint? = null

    @JvmField
    @Ignore
    var today: Today? = null

    @JvmField
    @Ignore
    protected var hasSaint = false
    var week: Int? = null
    var day: Int? = null
    var colorFK: Int? = null
        get() = field
        set(colorFK) {
            field = colorFK
        }

    @Ignore
    var breviaryHour: BreviaryHour? = null
    var liturgyID: Int? = null
        get() = field
        set(liturgiaId) {
            field = liturgiaId
        }
    var typeFK: Int? = null
        get() = field
        set(tipoFK) {
            field = tipoFK
        }
    var timeFK: Int? = null
        get() = field
        set(tiempoFK) {
            field = tiempoFK
        }

    var name: String = ""
        get() = if (field != null) field else "***"
        set(name) {
            field = name
        }
    var dia: Int?
        get() = day
        set(dia) {
            day = dia
        }
    var semana: Int?
        get() = week
        set(semana) {
            week = semana
        }
    val titleForRead: String
        get() = try {
            timeFK = liturgyTime?.timeID
            if (timeFK!! >= 8 || timeFK == 1 && day!! > 16 || timeFK == 2 || timeFK == 3 && week == 0 || timeFK == 4 || timeFK == 5 || week!! >= 35 || timeFK == 6 && week == 6 && typeFK!! < 4 || timeFK == 6 && week == 1 && day != 1 || day == 0) {
                timeWithTitleForRead
            } else {
                timeWithWeekAndDay
            }
        } catch (e: Exception) {
            Utils.createErrorMessage(e.message)
        }

    override fun toString(): String {
        return String.format(Locale("es"), "Liturgy:%n%d\t%s", liturgyID, name)
    }

    open val tituloForRead: String?
        get() = String.format(Locale("es"), "%s%s", name, ".")
    val timeWithWeekAndDay: String
        get() {
            val ns = Numerals(false, false, false)
            return String.format(
                Locale("es"),
                "%s. %s de la %s semana.",
                liturgyTime?.liturgyName,
                Utils.getDayName(
                    day!!
                ),
                ns.toWords(week!!, true)
            )
        }
    val timeWithTitleForRead: String
        get() = String.format(
            Locale("es"),
            "%s. %s",
            liturgyTime?.liturgyName,
            tituloForRead
        )



    /**
     * Devuelve el saludo inicial de la liturgia
     *
     * @return El texto formateado para la vista
     * @since 2023.1
     */
    val saludoInicial: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder("")
            ssb.append(Utils.toRed("V/. "))
            ssb.append("En el nombre del Padre, y del Hijo, y del Espíritu Santo.")
            ssb.append(Utils.LS)
            ssb.append(Utils.toRed("R/. "))
            ssb.append("Amén.")
            ssb.append(Utils.LS2)
            return ssb
        }

    fun setHasSaint(hasSaint: Boolean) {
        this.hasSaint = hasSaint
    }
}