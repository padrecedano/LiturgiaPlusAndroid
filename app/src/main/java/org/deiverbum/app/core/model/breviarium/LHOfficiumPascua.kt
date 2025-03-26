package org.deiverbum.app.core.model.breviarium

import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.util.Utils

/**
 * Clase que representa el **`Oficio de Lecturas`** del día de Pascua en la capa de datos externa.
 *
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property officiumLectionis Un objeto [LHOfficiumLectionis] con las lecturas largas.
 * @property oratio Un objeto [LHOratio] con la oración final.
 */

data class LHOfficiumPascua(
    var psalmodia: LHPsalmody,
    var officiumLectionis: MutableList<LHOfficeBiblicalEaster>,
    var oratio: MutableList<Oratio>,
    override val typus: String = "officiumPascua"
    //, override var tempore: LiturgyTime
) : Breviarium(typus) {
    var metaInfo: List<String> = listOf(
        "Hoy, la celebración solemne de la Vigilia pascual reemplaza el Oficio de lectura.",
        "Quienes no hayan participado en la celebración de la Vigilia pascual usarán, para el Oficio de lectura, al menos cuatro de las lecturas de la referida Vigilia pascual, con sus cantos y oraciones. Es muy conveniente elegir, de entre las lecturas de la Vigilia pascual, las que se proponen a continuación.",
        "Este Oficio empieza directamente con las lecturas."
    )

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            for ((i, oneBiblica) in officiumLectionis.withIndex()) {
                if (i <= 1) {
                    sb.append(oneBiblica.biblicalForRead)
                    //sb.append(psalmodia.getSalmosByIndexForRead(i))
                    sb.append(oratio[i].allForRead)
                }
                if (i == 2) {
                    sb.append(oneBiblica.biblicalForRead)
                    //sb.append(psalmodia.getSalmosByIndexForRead(i))
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

    /**
     * Ordena la salmodia y las lecturas del oficio.
     * Desde aquí se llama a [LHPsalmody.sort], porque se usa el método [LHPsalmody.getSalmosByIndex]
     * que es llamado varias veces.
     *
     */
    override fun sort() {
        psalmodia.sort()
        officiumLectionis =
            officiumLectionis.sortedBy { it.theOrder } as MutableList<LHOfficeBiblicalEaster>
    }
}