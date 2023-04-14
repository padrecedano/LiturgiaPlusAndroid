package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

/**
 *
 *
 * Reúne aquellos elementos que son comúnes a las diversas horas del Breviary.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
open class BreviaryHour : Liturgy() {
    //protected int typeID;
    protected var metaInfo: String? = null
        get() = if (field==null && field !="") "" else "<br><br>$field"
    @JvmField
    protected var himno: LHHymn? = null
    @JvmField
    protected var salmodia: LHPsalmody? = null
    @JvmField
    protected var oracion: Prayer? = null
    protected var lhOfficeOfReading: LHOfficeOfReading? = null

    //internal var teDeum: TeDeum? = null
    @JvmField
    protected var oficio: Oficio? = null
    @JvmField
    protected var oficioEaster: OficioEaster? = null
    @JvmField
    protected var laudes: Laudes? = null
    private var mixto: Mixto? = null
    private var intermedia: Intermedia? = null
    private var visperas: Visperas? = null
    private var completas: Completas? = null
    fun getCompletas(): Completas? {
        return completas
    }

    fun setCompletas(completas: Completas?) {
        this.completas = completas
    }


    fun getSaludoOficio(): SpannableStringBuilder {
        val sb = SpannableStringBuilder("")
        sb.append(Utils.formatTitle(Constants.TITLE_INITIAL_INVOCATION))
        sb.append(Utils.LS2)
        sb.append(Utils.toRed("V. "))
        sb.append("Señor, abre mis labios.")
        sb.append(Utils.LS)
        sb.append(Utils.toRed("R. "))
        sb.append("Y mi boca proclamará tu alabanza.")
        sb.append(Utils.LS2)
        sb.append(salmodia!!.finSalmo)
        return sb
    }

    fun getSaludoOficioForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append("Señor abre mis labios.")
        sb.append("Y mi boca proclamará tu alabanza.")
        sb.append("Gloria al Padre, y al Hijo, y al Espíritu Santo.")
        sb.append("Como era en el principio ahora y siempre, por los siglos de los siglos. Amén.")
        return sb
    }

    fun getSaludoDiosMio(): SpannableStringBuilder {
        val sb = SpannableStringBuilder("")
        sb.append(Utils.formatTitle(Constants.TITLE_INITIAL_INVOCATION))
        sb.append(Utils.LS2)
        sb.append(Utils.toRed("V. "))
        sb.append("Dios mío, ven en mi auxilio.")
        sb.append(Utils.LS)
        sb.append(Utils.toRed("R. "))
        sb.append("Señor, date prisa en socorrerme.")
        sb.append(Utils.LS2)
        sb.append(salmodia!!.finSalmo)
        return sb
    }

    open fun getHimno(): LHHymn? {
        return himno
    }

    fun setHimno(himno: LHHymn?) {
        this.himno = himno
    }

    fun getSalmodia(): LHPsalmody? {
        return salmodia
    }

    fun setSalmodia(salmodia: LHPsalmody?) {
        this.salmodia = salmodia
    }

    fun getOracion(): Prayer? {
        return oracion
    }

    fun setOracion(oracion: Prayer?) {
        this.oracion = oracion
    }

    fun setTypeId(typeID: Int) {
        this.typeID = typeID
    }

    fun setOfficeOfReading(oficioLecturas: LHOfficeOfReading?) {
        this.lhOfficeOfReading = oficioLecturas
    }

    fun getMixto(): Mixto? {
        return mixto
    }

    fun setMixto(mixto: Mixto?) {
        this.mixto = mixto
    }

    open fun getOficio(): Oficio? {
        return oficio
    }

    open fun setOficio(oficio: Oficio?) {
        this.oficio = oficio
    }

    fun getOficio(hasInvitatory: Boolean): Oficio? {
        oficio!!.invitatorio?.isMultiple = hasInvitatory
        return oficio
    }

    open fun getLaudes(): Laudes? {
        return laudes
    }

    open fun setLaudes(laudes: Laudes?) {
        this.laudes = laudes
    }

    fun getLaudes(hasInvitatory: Boolean): Laudes? {
        laudes!!.invitatorio?.isMultiple = hasInvitatory
        return laudes
    }

    fun getIntermedia(): Intermedia? {
        return intermedia
    }

    fun setIntermedia(intermedia: Intermedia?) {
        this.intermedia = intermedia
    }

    fun getVisperas(): Visperas? {
        return visperas
    }

    fun setVisperas(visperas: Visperas?) {
        this.visperas = visperas
    }

    fun getOficioEaster(): OficioEaster? {
        return oficioEaster
    }

    fun setOficioEaster(oficioEaster: OficioEaster?) {
        this.oficioEaster = oficioEaster
    }

    fun getMixtoForView(liturgyTime: LiturgyTime, hasSaint: Boolean): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            this.hasSaint = hasSaint
            val invitatory = oficio?.invitatorio
            invitatory?.normalizeByTime(liturgyTime.timeID!!)
            laudes!!.salmodia?.normalizeByTime(liturgyTime.timeID)
            sb.append(Utils.LS2)
            if (santo != null && this.hasSaint) {
                invitatory?.normalizeIsSaint(santo!!.theName)
                sb.append(santo?.vidaSmall)
                sb.append(Utils.LS)
            }
            sb.append(mixto!!.tituloHora)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(laudes!!.getSaludoOficio())
            sb.append(Utils.LS2)
            sb.append(oficio!!.invitatorio?.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.himno!!.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.salmodia?.all)
            sb.append(laudes!!.lecturaBreve?.getAllWithHourCheck(2))
            sb.append(Utils.LS2)
            sb.append(oficio!!.lhOfficeOfReading?.getAll(liturgyTime.timeID))
            sb.append(mixto!!.evangeliosForView)
            sb.append(Utils.LS)
            sb.append(laudes!!.gospelCanticle?.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.preces?.all)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.oracion?.all)
            sb.append(Utils.LS2)
            sb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    fun getMixtoForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            if (santo != null && hasSaint) {
                sb.append(santo?.vida)
            }
            sb.append(mixto!!.tituloHoraForRead)
            sb.append(laudes!!.getSaludoOficioForRead())
            sb.append(oficio!!.invitatorio?.allForRead)
            sb.append(laudes!!.himno?.allForRead)
            sb.append(laudes!!.salmodia?.all)
            sb.append(laudes!!.lecturaBreve?.getAllForRead())
            sb.append(oficio!!.lhOfficeOfReading?.allForRead)
            sb.append(mixto!!.evangeliosForRead)
            sb.append(laudes!!.gospelCanticle?.allForRead)
            sb.append(laudes!!.preces?.allForRead)
            sb.append(PadreNuestro.all)
            sb.append(laudes!!.getOracion()?.allForRead)
            sb.append(getConclusionHorasMayoresForRead())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    companion object {
        /**
         * @return El saludo listo para el lector de voz.
         * @since 2021.01
         */
        @JvmStatic
        fun getSaludoDiosMioForRead(): String {
            return "Dios mío, ven en mi auxilio." +
                    "Señor, date prisa en socorrerme." +
                    "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                    "Como era en el principio ahora y siempre, por los siglos de los siglos. Amén."
        }

        @JvmStatic
        fun getConclusionHorasMayores(): SpannableStringBuilder {
            val ssb = SpannableStringBuilder(Utils.formatTitle(Constants.TITLE_CONCLUSION))
            ssb.append(Utils.LS2)
            ssb.append(Utils.toRed("V. "))
            ssb.append("El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.")
            ssb.append(Utils.LS)
            ssb.append(Utils.toRed("R. "))
            ssb.append("Amén.")
            return ssb
        }

        /**
         * @return Texto con la conclusión de la hora, formateado para lectura
         * @since 2021.01
         */
        @JvmStatic
        fun getConclusionHorasMayoresForRead(): String {
            return "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna. Amén."
        }

        /**
         * Método que obtiene la conclusión de la hora
         *
         * @return Texto con la conclusión de la hora, formateado para vista
         * @since 2022.2
         */
        @JvmStatic
        fun getConclusionHoraMenor(): SpannableStringBuilder {
            val sb = SpannableStringBuilder()
            sb.append(Utils.formatTitle(Constants.TITLE_CONCLUSION))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed("V. "))
            sb.append("Bendigamos al Señor.")
            sb.append(Utils.LS)
            sb.append(Utils.toRed("R. "))
            sb.append("Demos gracias a Dios.")
            return sb
        }

        /**
         * Método que obtiene la conclusión de la hora
         *
         * @return Texto con la conclusión de la hora, formateado para lectura
         * @since 2022.2
         */
        @JvmStatic
        fun getConclusionHoraMenorForRead(): String {
            return "Bendigamos al Señor. Demos gracias a Dios."
        }
    }
}