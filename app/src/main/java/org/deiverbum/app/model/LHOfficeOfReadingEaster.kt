package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class LHOfficeOfReadingEaster : LHOfficeOfReading() {
    @JvmField
    var lhPsalmody: LHPsalmody? = null

    @Ignore

    var biblical: MutableList<LHOfficeBiblicalEaster?>? = null
        private set
    private val metaInfoForView: String
        get() = String.format(
            "%s%s%s%s%s",
            "Hoy, la celebración solemne de la Vigilia pascual reemplaza el Oficio de lectura.",
            Utils.LS2,
            "Quienes no hayan participado en la celebración de la Vigilia pascual usarán, para el Oficio de lectura, al menos cuatro de las lecturas de la referida Vigilia pascual, con sus cantos y oraciones. Es muy conveniente elegir, de entre las lecturas de la Vigilia pascual, las que se proponen a continuación.",
            Utils.LS2,
            "Este Oficio empieza directamente con las lecturas."
        )
    private val metaInfoForRead: String
        get() = "Hoy, la celebración solemne de la Vigilia pascual reemplaza el Oficio de lectura. Quienes no hayan participado en la celebración de la Vigilia pascual usarán, para el Oficio de lectura, al menos cuatro de las lecturas de la referida Vigilia pascual, con sus cantos y oraciones. Es muy conveniente elegir, de entre las lecturas de la Vigilia pascual, las que se proponen a continuación. Este Oficio empieza directamente con las lecturas."

    fun setBiblicalE(biblical: MutableList<LHOfficeBiblicalEaster?>) {
        this.biblical = biblical
    }

    override val header: SpannableStringBuilder?
        get() = Utils.formatSubTitle(Utils.toLower(Constants.TITLE_OFFICE_OF_READING))
    override val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFFICE_OF_READING)

    override fun getAll(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.toSmallSizeRed(metaInfoForView))
        sb.append(getAllBiblica(calendarTime))
        return sb
    }

    override fun getAllBiblica(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        this.teDeum = TeDeum()
        for ((i, oneBiblica) in biblical!!.withIndex()) {
            if (i <= 1) {
                sb.append(Utils.LS2)
                sb.append(oneBiblica?.biblical)
                sb.append(Utils.LS2)
                sb.append(lhPsalmody!!.getSalmosByIndex(i))
                sb.append(Utils.LS2)
                sb.append(oneBiblica?.prayer?.all)
                sb.append(Utils.LS2)
            }
            if (i == 2) {
                sb.append(Utils.LS2)
                sb.append(oneBiblica?.biblical)
                sb.append(Utils.LS2)
                sb.append(lhPsalmody!!.getSalmosByIndex(i))
                sb.append(Utils.LS2)
                //sb.append(oneBiblica.getPrayer().getAll());
            }
            if (i == 3) {
                sb.append(oneBiblica?.biblical)
                sb.append(Utils.LS2)
                sb.append(this.teDeum!!.all)
                sb.append(Utils.LS2)
                sb.append(oneBiblica?.prayer?.all)
            }
        }
        return sb
    }

    //sb.append(oneBiblica.getPrayer().getAll());
    override val allBiblicaForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for ((i, oneBiblica) in biblical!!.withIndex()) {
                if (i <= 1) {
                    sb.append(Utils.LS2)
                    sb.append(oneBiblica?.biblicalForRead)
                    sb.append(Utils.LS2)
                    sb.append(lhPsalmody!!.getSalmosByIndexForRead(i))
                    sb.append(Utils.LS2)
                    sb.append(oneBiblica?.prayer?.allForRead)
                    sb.append(Utils.LS2)
                }
                if (i == 2) {
                    sb.append(Utils.LS2)
                    sb.append(oneBiblica?.biblicalForRead)
                    sb.append(Utils.LS2)
                    sb.append(lhPsalmody!!.getSalmosByIndexForRead(i))
                    sb.append(Utils.LS2)
                    //sb.append(oneBiblica.getPrayer().getAll());
                }
                if (i == 3) {
                    sb.append(oneBiblica?.biblicalForRead)
                    sb.append(Utils.LS2)
                    sb.append(this.teDeum!!.allForRead)
                    sb.append(Utils.LS2)
                    sb.append(oneBiblica?.prayer?.allForRead)
                }
            }
            return sb
        }
    override val allForRead: String
        get() = metaInfoForRead +
                allBiblicaForRead

    fun sort() {
        //Collections.sort(biblical)
    }
}