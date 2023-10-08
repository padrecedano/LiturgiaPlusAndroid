package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

//@Suppress("unused")
class Completas : BreviaryHour() {
    private var ritosIniciales: RitosIniciales? = null
    private var nuncDimitis: LHGospelCanticle? = null
    private var conclusion: Conclusion? = null
    var biblicalShort: LHLectioBrevis? = null

    private fun getRitosIniciales(): RitosIniciales? {
        return ritosIniciales
    }

    fun setRitosIniciales(ritosIniciales: RitosIniciales?) {
        this.ritosIniciales = ritosIniciales
    }

    fun setNuncDimitis(nuncDimitis: LHGospelCanticle?) {
        this.nuncDimitis = nuncDimitis
    }


    fun setConclusion(conclusion: Conclusion?) {
        this.conclusion = conclusion
    }

    fun getAllForView(calendarTime: Int): SpannableStringBuilder {
        return try {
            val sb = SpannableStringBuilder()
            biblicalShort!!.normalizeByTime(calendarTime)
            nuncDimitis!!.normalizeByTime(calendarTime)

            val ri = getRitosIniciales()
            val kyrie = ri!!.kyrie
            sb.append(Utils.LS2)
            sb.append(getTituloHora())
            sb.append(Utils.LS2)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            sb.append(kyrie?.all)
            sb.append(Utils.LS2)
            sb.append(lhHymn!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.getAllForView(-1, calendarTime))
            sb.append(Utils.LS)
            sb.append(biblicalShort!!.getAllWithHourCheck(7))
            sb.append(Utils.LS)
            sb.append(nuncDimitis!!.all)
            sb.append(Utils.LS2)
            sb.append(oracion!!.all)
            sb.append(Utils.LS2)
            sb.append(conclusion!!.getAll())
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
            val ri = getRitosIniciales()
            val kyrie = ri!!.kyrie
            //himno = getHimno()
            //salmodia = getCompletasDias()!![today?.weekDay!!].salmodia
            //val nuncDimitis = getNuncDimitis()
            //val conclusion = getConclusion()
            sb.append(getTituloHoraForRead())
            sb.append(initialInvocationForRead)
            sb.append(kyrie?.allForRead)
            sb.append(lhHymn!!.allForRead)
            sb.append(salmodia!!.getAllForRead())
            sb.append(biblicalShort!!.getAllForRead())

            //sb.append(getLecturaForRead())
            sb.append(nuncDimitis!!.allForRead)
            sb.append(oracion!!.allForRead)
            sb.append(conclusion!!.getAllForRead())
            sb
        } catch (e: Exception) {
            StringBuilder(e.toString())
        }
    }
}