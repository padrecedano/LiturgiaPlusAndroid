package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.util.Numerals
import org.deiverbum.app.util.Utils
import java.util.*

open class Liturgy() {
    /*constructor(data:Int) : this(liturgyType = null){
        id=data
    }*/
    constructor(liturgyType: LiturgiaTypus?, name: String = "", typeID: Int = 0) : this() {
        this.liturgyType = liturgyType
        this.name = name
        this.typeID = typeID
    }

    @JvmField
    @Ignore
    @PropertyName("typeId")
    var typeID = 0

    @Ignore
    var liturgyTime: LiturgyTime? = null

    @Ignore
    var liturgyType: LiturgiaTypus? = null

    @Ignore
    var calendarTime = 0

    @Ignore
    var saintFK = 0

    @JvmField
    @Ignore
    var santo: Sanctus? = null

    //protected val hasSaint

    protected open val hasS: Boolean
        get() = saintFK == 1

    @Ignore
    var saintLife: LHSanctus? = null

    @JvmField
    @Ignore
    var today: Today? = null

    @JvmField
    @Ignore
    protected var hasSaint = false
    var week: Int? = null
    var day: Int? = null
    var colorFK: Int? = null

    /*
        @Ignore
        var breviaryHour: BreviaryHour? = null
    */
    @Ignore
    var massReadingList: MissaeLectionumList? = null

    @Ignore
    var bibleCommentList: BibleCommentList? = null

    @Ignore
    open var homilyList: HomilyList? = null

    var liturgyID: Int? = null
    var typeFK: Int? = null
    var timeFK: Int = 0

    var name: String = ""
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
            timeFK = liturgyTime?.timeID!!
            if (timeFK >= 8 || timeFK == 1 && day!! > 16 || timeFK == 2 || timeFK == 3 && week == 0 || timeFK == 4 || timeFK == 5 || week!! >= 35 || timeFK == 6 && week == 6 && typeFK!! < 4 || timeFK == 6 && week == 1 && day != 1 || day == 0) {
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
    private val timeWithWeekAndDay: String
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
    private val timeWithTitleForRead: String
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