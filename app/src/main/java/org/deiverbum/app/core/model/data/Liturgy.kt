package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.ColumnInfo
import androidx.room.Ignore
import com.google.firebase.firestore.PropertyName
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Numerals
import org.deiverbum.app.util.Utils
import java.util.*

@JsonClass(generateAdapter = true)
open class Liturgy(
    //@Json(ignore = true)
    @ColumnInfo(name = "name")
    var liturgyName: String = ""
) {
    constructor(week: Int = 0, day: Int = 0, colorFK: Int = 0, liturgyName: String) : this(
        liturgyName
    ) {
        this.week = week
        this.day = day
        this.colorFK = colorFK
    }

    constructor(
        week: Int = 0,
        day: Int = 0,
        liturgyName: String,
        tempore: LiturgyTime
    ) : this(liturgyName) {
        this.week = week
        this.day = day
        this.tempore = tempore

    }

    constructor(
        week: Int = 0,
        day: Int = 0,
        liturgyName: String,
        tempore: LiturgyTime,
        typus: LiturgiaTypus?
    ) : this(liturgyName) {
        this.week = week
        this.day = day
        this.tempore = tempore
        this.typus = typus

    }

    constructor(liturgyName: String = "", typus: LiturgiaTypus?) : this(liturgyName) {
        this.typus = typus
        //this.typeID = typeID
    }

    @JvmField
    @Ignore
    @PropertyName("typeId")
    var typeID = 0

    @Ignore
    var tempore: LiturgyTime? = null

    @Ignore
    var typus: LiturgiaTypus? = null

    @Ignore
    var calendarTime = 0

    @Ignore
    var saintFK = 0

    @JvmField
    @Ignore
    var santo: Sanctus? = null

    //protected val hasSaint



    @Ignore
    var saintLife: LHSanctus? = null

    @JvmField
    @Ignore
    var today: Today? = null

    @JvmField
    @Ignore
    var hasSaint = false
    var week: Int = 0
    var day: Int = 0
    var colorFK: Int = 0

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

    //var name: String = ""
    /*var dia: Int?
        get() = day
        set(dia) {
            day = dia
        }*/
    /*var semana: Int?
        get() = week
        set(semana) {
            week = semana
        }*/
    val titleForRead: String
        get() = try {
            timeFK = tempore?.timeID!!
            if (timeFK >= 8 || timeFK == 1 && day > 16 || timeFK == 2 || timeFK == 3 && week == 0 || timeFK == 4 || timeFK == 5 || week >= 35 || timeFK == 6 && week == 6 && typeFK!! < 4 || timeFK == 6 && week == 1 && day != 1 || day == 0) {
                timeWithTitleForRead
            } else {
                timeWithWeekAndDay
            }
        } catch (e: Exception) {
            Utils.createErrorMessage(e.message)
        }

    override fun toString(): String {
        return String.format(Locale("es"), "Liturgy:%n%d\t%s", liturgyID, liturgyName)
    }

    open val tituloForRead: String?
        get() = String.format(Locale("es"), "%s%s", liturgyName, ".")
    val timeWithWeekAndDay: String
        get() {
            val ns = Numerals(false, false, false)
            return String.format(
                Locale("es"),
                "%s. %s de la %s semana.",
                tempore?.liturgyName,
                Utils.getDayName(
                    day
                ),
                ns.toWords(week, true)
            )
        }
    val timeWithTitleForRead: String
        get() = String.format(
            Locale("es"),
            "%s. %s",
            tempore?.liturgyName,
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