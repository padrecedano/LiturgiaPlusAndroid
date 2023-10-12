package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

//@Suppress("unused")
data class LHCompletorium(
    var kyrie: Kyrie,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var oratio: Oratio,
    var conclusio: Conclusion
) : Breviarium {
    /*var ritosIniciales: RitosIniciales? = null,
      var nuncDimitis: LHGospelCanticle? = null,
      var conclusion: Conclusion? = null,
      var biblicalShort: LHLectioBrevis? = null */


    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return try {
            val sb = SpannableStringBuilder()
            lectioBrevis.normalizeByTime(calendarTime)
            //canticumEvangelicum!!.normalizeByTime(calendarTime)

            val ri = RitosIniciales()
            //val kyrie = ri!!.kyrie
            sb.append(Utils.LS2)
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
            sb.append(canticumEvangelicum.all)
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

    fun getForRead(): StringBuilder {
        return try {
            val sb = StringBuilder()
            //val ri = getRitosIniciales()
            //val kyrie = ri!!.kyrie
            //himno = getHimno()
            //salmodia = getCompletasDias()!![today?.weekDay!!].salmodia
            //val nuncDimitis = getNuncDimitis()
            //val conclusion = getConclusion()
            sb.append(getTituloHoraForRead())
            sb.append(initialInvocationForRead)
            sb.append(kyrie.allForRead)
            /*sb.append(lhHymn!!.allForRead)
            sb.append(salmodia!!.getAllForRead())
            sb.append(biblicalShort!!.getAllForRead())

            //sb.append(getLecturaForRead())
            sb.append(nuncDimitis!!.allForRead)
            sb.append(oracion!!.allForRead)*/
            //sb.append(conclusion!!.getAllForRead())
            sb
        } catch (e: Exception) {
            StringBuilder(e.toString())
        }
    }
}