package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Clase que representa el **`Oficio de Lecturas`** del día de Pascua en la capa de datos externa.
 *
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property officiumLectionis Un objeto [LHOfficiumLectionis] con las lecturas largas.
 * @property oratio Un objeto [LHOratio] con la oración final.
 */

data class LHOfficiumPascua(
    val psalmodia: LHPsalmody,
    var officiumLectionis: MutableList<LHOfficeBiblicalEaster>,
    var oratio: MutableList<Oratio>
) : Breviarium, Sortable {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(header)
        try {
            sort()
            ssb.append(Utils.LS2)
            ssb.append(Utils.toSmallSizeRed(metaInfoForView))
            for ((i, oneBiblica) in officiumLectionis.withIndex()) {
                if (i <= 1) {
                    ssb.append(Utils.LS2)
                    ssb.append(oneBiblica.biblical)
                    ssb.append(Utils.LS2)
                    ssb.append(psalmodia.getSalmosByIndex(i, calendarTime))
                    ssb.append(Utils.LS2)
                    ssb.append(oratio[i].all)
                    ssb.append(Utils.LS)
                }
                if (i == 2) {
                    ssb.append(Utils.LS)
                    ssb.append(oneBiblica.biblical)
                    ssb.append(Utils.LS2)
                    ssb.append(psalmodia.getSalmosByIndex(i, calendarTime))
                    ssb.append(Utils.LS2)
                    ssb.append(oratio[i].all)
                    ssb.append(Utils.LS2)
                }
                if (i == 3) {
                    ssb.append(Utils.LS)
                    ssb.append(oneBiblica.biblical)
                    ssb.append(Utils.LS2)
                    ssb.append(TeDeum().all)
                    ssb.append(Utils.LS)
                    ssb.append(oratio[i - 1].all)
                }
            }

        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder(headerForRead)
        try {
            sb.append(metaInfoForRead)
            for ((i, oneBiblica) in officiumLectionis.withIndex()) {
                if (i <= 1) {
                    sb.append(oneBiblica.biblicalForRead)
                    sb.append(psalmodia.getSalmosByIndexForRead(i))
                    sb.append(oratio[i].allForRead)
                }
                if (i == 2) {
                    sb.append(oneBiblica.biblicalForRead)
                    sb.append(psalmodia.getSalmosByIndexForRead(i))
                    sb.append(oratio[i].allForRead)
                }
                if (i == 3) {
                    sb.append(oneBiblica.biblicalForRead)
                    sb.append(TeDeum().allForRead)
                    sb.append(oratio[i - 1].allForRead)
                }
            }

        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

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


    val header: SpannableStringBuilder
        get() = Utils.formatSubTitle(Utils.toLower(Constants.TITLE_OFFICE_OF_READING))

    val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFFICE_OF_READING)

    /**
     * Ordena la salmodia y las lecturas del oficio.
     * Desde aquí se llama a [LHPsalmody.sort], porque se usa el método [LHPsalmody.getSalmosByIndex]
     * que es llamado varias veces.
     *
     */
    override fun sort() {
        psalmodia.sort()
        officiumLectionis.sortBy { it.theOrder }
    }
}