package org.deiverbum.app.core.model.data

import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class LHCompletorium(
    var kyrie: Kyrie,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var oratio: Oratio,
    var conclusio: ConclusioCompletorium,
    override var typus: String = "completorium"
    //, override var tempore: LiturgyTime

) : Breviarium(typus) {


    private fun getTituloHoraForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_COMPLETAS)
    }

    override fun forRead(): StringBuilder {
        return try {
            val sb = StringBuilder()
            sb.append(getTituloHoraForRead())
            sb.append(initialInvocationForRead)
            sb.append(kyrie.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead(-1))
            sb.append(lectioBrevis.getAllForRead())
            sb.append(canticumEvangelicum.getSalmosByIndexForRead(0))
            sb.append(oratio.allForRead)
            //sb.append(conclusio.getAllForRead())
            sb
        } catch (e: Exception) {
            StringBuilder(e.toString())
        }
    }

    override fun sort() {
        psalmodia.sort()
    }
    override fun normalizeByTime(calendarTime: Int) {
        psalmodia.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }
}