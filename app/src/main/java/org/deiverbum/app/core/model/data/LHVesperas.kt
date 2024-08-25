package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils


/**
 * Clase que representa las **`Vísperas`** en la capa de datos externa.
 *
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property lectioBrevis Un objeto [LHLectioBrevis] con la lectura breve.
 * @property canticumEvangelicum Un objeto [LHGospelCanticle] con el Magníficat.
 * @property preces Un objeto [LHIntercession] con las preces.
 * @property oratio Un objeto [Oratio] con la oración final.
 * @property sanctus Un objeto [LHSanctus] con la vida breve del santo del día cuando aplique.
 * @property isPrimaVesperas Será `true` cuando la celebración sea Primeras Vísperas.
 *
 * @author A. Cedano
 * @see [Breviarium]
 */

class LHVesperas(
    var hasSaint: Boolean = false,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio,
    var isPrimaVesperas: Boolean, override var typus: String = "vesperas",
    //override var tempore: LiturgyTime
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null

    fun forView(calendarTime: Int): SpannableStringBuilder {
        //this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {
            if (sanctus != null && hasSaint) {
                //invitatorium.normalizeIsSaint(sanctus!!.nomen)
                //ssb.append(sanctus!!.vitaBrevis)
                //ssb.append(Constants.LS)
            }
            ssb.append(titleForView)
            ssb.append(Utils.LS2)
            ssb.append(Introitus.viewDomineLabiaMeaAperis)
            ssb.append(Utils.LS2)
            //ssb.append(invitatorium.getAllForView(-1, calendarTime))
            ssb.append(hymnus.all)
            ssb.append(Utils.LS2)
            ssb.append(psalmodia.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS)
            //ssb.append(Utils.LS)
            ssb.append(lectioBrevis.getAllWithHourCheck(2))
            ssb.append(Utils.LS)
            ssb.append(canticumEvangelicum.getSalmosByIndex(0, calendarTime))
            ssb.append(Utils.LS2)
            ssb.append(preces.all)
            ssb.append(Utils.LS2)
            ssb.append(PadreNuestro.all)
            ssb.append(Utils.LS2)
            ssb.append(oratio.all)
            ssb.append(Utils.LS2)
            ssb.append(RitusConclusionis.viewDominusNosBenedicat)
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            if (sanctus != null && hasSaint) {
                sb.append(sanctus!!.vitaBrevis)
            }
            sb.append(titleForRead)
            sb.append(Introitus.readDomineLabiaMeaAperis)
            //sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllForRead())
            sb.append(Utils.LS)
            sb.append(canticumEvangelicum.getSalmosByIndexForRead(0))
            sb.append(Utils.LS2)
            sb.append(preces.allForRead)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.allForRead)
            sb.append(Utils.LS2)
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readDominusNosBenedicat)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }


    val titleForView: SpannableStringBuilder
        get() =
            if (isPrimaVesperas) Utils.toH1Red(Constants.TITLE_I_VISPERAS) else Utils.toH1Red(
                Constants.TITLE_VISPERAS
            )

    val titleForRead: String
        get() =
            if (isPrimaVesperas) Utils.pointAtEnd(Constants.TITLE_I_VISPERAS) else Utils.pointAtEnd(
                Constants.TITLE_VISPERAS
            )
    override fun sort() {
        psalmodia.sort()
    }

}