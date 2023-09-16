package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.util.SparseIntArray
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

@Suppress("unused")
class Completas : BreviaryHour() {
    var completasHimno: List<CompletasHimno>? = null
    private var ritosIniciales: RitosIniciales? = null
    private var nuncDimitis: LHGospelCanticle? = null
    private var conclusion: Conclusion? = null
    private var completasDias: List<CompletasDia>? = null
    private var responsorio: List<LHResponsoryShort>? = null
    var biblicalShort: BiblicalShort? = null

    fun getResponsorio(): List<LHResponsoryShort>? {
        return responsorio
    }

    fun setResponsorio(responsorio: List<LHResponsoryShort>?) {
        this.responsorio = responsorio
    }

    fun getRitosIniciales(): RitosIniciales? {
        return ritosIniciales
    }

    fun setRitosIniciales(ritosIniciales: RitosIniciales?) {
        this.ritosIniciales = ritosIniciales
    }

    fun getOracionByDay(): Prayer? {
        return completasDias!![today?.weekDay!!].oracion
    }

    fun getNuncDimitis(): LHGospelCanticle? {
        return nuncDimitis
    }

    fun setNuncDimitis(nuncDimitis: LHGospelCanticle?) {
        this.nuncDimitis = nuncDimitis
    }

    fun setCompletasDia(completasDias: List<CompletasDia>?) {
        this.completasDias = completasDias
    }

    fun getConclusion(): Conclusion? {
        return conclusion
    }

    fun setConclusion(conclusion: Conclusion?) {
        this.conclusion = conclusion
    }

    override fun getHimno(): LHHymn? {
        val mMap = SparseIntArray()
        when (today?.timeID) {
            1, 2 -> {
                mMap.put(0, 0)
                mMap.put(1, 1)
                mMap.put(2, 0)
                mMap.put(3, 1)
                mMap.put(4, 0)
                mMap.put(5, 1)
                mMap.put(6, 0)
                mMap.put(7, 0)
            }
            3, 4, 5 -> {
                mMap.put(0, 2)
                mMap.put(1, 3)
                mMap.put(2, 2)
                mMap.put(3, 3)
                mMap.put(4, 2)
                mMap.put(5, 3)
                mMap.put(6, 2)
                mMap.put(7, 2)
            }
            6 -> {
                mMap.put(0, 4)
                mMap.put(1, 5)
                mMap.put(2, 4)
                mMap.put(3, 5)
                mMap.put(4, 4)
                mMap.put(5, 5)
                mMap.put(6, 4)
                mMap.put(7, 4)
            }
            else -> {
                mMap.put(0, 0)
                mMap.put(1, 6)
                mMap.put(2, 0)
                mMap.put(3, 6)
                mMap.put(4, 0)
                mMap.put(5, 6)
                mMap.put(6, 0)
                mMap.put(7, 0)
            }
        }
        return completasHimno!![today?.weekDay!!].getHimno()
    }

    fun getCompletasDias(): List<CompletasDia>? {
        return completasDias
    }

    /**
     * Devuelve Lectura Breve y Responsorio Breve formateados para vista
     * Para el responsorio, determina el que corresponda, según sea o no
     * tiempo de Pascua (timeID=6)
     *
     * @return una cadena formateada con la Lectura Breve y el Responsorio
     */
    fun getLecturaSpan(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        val mIndex = if (today?.timeID == 6) 1 else 0
        val mResponsorio = responsorio!![mIndex]
        val mLectura = completasDias!![today?.weekDay!!].getLecturaBreve()
        mLectura!!.setResponsorio(mResponsorio)
        ssb.append(mLectura.getAllWithHourCheck(7))
        return ssb
    }

    fun getLecturaForRead(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        val mLectura = completasDias!![today?.weekDay!!].getLecturaBreve()
        ssb.append(mLectura!!.getAllForRead())
        return ssb
    }

    fun getAllForView(): SpannableStringBuilder {
        return try {
            val sb = SpannableStringBuilder()
            val ri = getRitosIniciales()
            val kyrie = ri!!.kyrie
            //himno = getHimno()
            /*salmodia = getCompletasDias()!![today?.weekDay!!].salmodia
            val nuncDimitis = getNuncDimitis()
            val conclusion = getConclusion()*/
            sb.append(Utils.LS2)
            sb.append(getTituloHora())
            sb.append(Utils.LS2)
            sb.append(getSaludoDiosMio())
            sb.append(Utils.LS2)
            sb.append(kyrie?.all)
            sb.append(Utils.LS2)
            sb.append(himno!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.all)
            sb.append(Utils.LS2)
            //sb.append(getLecturaSpan())
            sb.append(biblicalShort!!.getAllWithHourCheck(7))

            sb.append(Utils.LS2)
            sb.append(nuncDimitis!!.getAll(today!!.liturgyDay.liturgyTime!!.timeID))
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

    fun getTituloHora(): SpannableStringBuilder {
        return Utils.toH1Red(Constants.TITLE_COMPLETAS)
    }

    fun getTituloHoraForRead(): String {
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
            sb.append(getSaludoDiosMioForRead())
            sb.append(kyrie?.allForRead)
            sb.append(himno!!.allForRead)
            sb.append(salmodia!!.allForRead)
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