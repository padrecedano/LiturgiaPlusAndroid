package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class LHCompletorium(
    var kyrie: Kyrie,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var oratio: Oratio,
    var conclusio: Conclusion
) : Breviarium {

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return try {
            val sb = SpannableStringBuilder()
            lectioBrevis.normalizeByTime(calendarTime)
            sb.append(getTituloHora())
            sb.append(Utils.LS2)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            sb.append(kyrie.all)
            sb.append(Utils.LS2)
            sb.append(hymnus.all)
            sb.append(Utils.LS2)
            sb.append(psalmodia.getAllForView(-1, calendarTime))
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllWithHourCheck(7))
            sb.append(Utils.LS)
            sb.append(canticumEvangelicum.getSalmosByIndex(0, calendarTime))
            sb.append(Utils.LS2)
            sb.append(oratio.all)
            sb.append(Utils.LS2)
            sb.append(conclusio.getAll())
            sb.append(Utils.LS2)
            sb
        } catch (e: Exception) {
            SpannableStringBuilder(e.toString())
        }
    }

    private fun getTituloHora(): SpannableStringBuilder {
        return Utils.toH1Red(Constants.TITLE_COMPLETAS)
    }

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
            sb.append(conclusio.getAllForRead())
            sb
        } catch (e: Exception) {
            StringBuilder(e.toString())
        }
    }
}