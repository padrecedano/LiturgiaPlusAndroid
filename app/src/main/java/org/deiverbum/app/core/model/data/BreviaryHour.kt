package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Reúne aquellos elementos que son comunes a las diversas horas del Breviario.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
open class BreviaryHour : Liturgy() {
    protected var metaInfo: String = ""
        get() {
            if (field != "") {
                return "<br><br>$field"
            }
            return field
        }

    var lhHymn: LHHymn? = null
    var salmodia: LHPsalmody? = null

    @JvmField
    var oracion: Oratio? = null

    @JvmField
    var lhOfficeOfReading: LHOfficiumLectionis? = null

    @JvmField
    var oficio: LHOfficium? = null

    @JvmField
    var oficioEaster: OficioEaster? = null

    @JvmField
    var laudes: LHLaudes? = null
    @JvmField
    var LHMixtus: LHMixtus? = null
    @JvmField
    var intermedia: Intermedia? = null
    @JvmField
    var visperas: Visperas? = null
    lateinit var completas: Completas

    protected val introAbreMisLabiosView: SpannableStringBuilder =
        SpannableStringBuilder(Utils.formatTitle(Constants.TITLE_INITIAL_INVOCATION))
            .append(Utils.LS2)
            .append(Utils.toRed("V. "))
            .append("Señor, abre mis labios.")
            .append(Utils.LS)
            .append(Utils.toRed("R. "))
            .append("Y mi boca proclamará tu alabanza.")
            .append(Utils.LS2)
            .append(endPsalmForView)

    protected val introAbreMisLabiosForRead: String =
        "Señor, abre mis labios. " +
                "Y mi boca proclamará tu alabanza." +
                "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, por los siglos de los siglos. Amén."


    /*fun getCompletas(): Completas? {
        return completas
    }*/
    /*
        fun setCompletas(completas: Completas?) {
            this.completas = completas
        }
    */

    fun getOracion(): Oratio? {
        return oracion
    }

    fun setOracion(oracion: Oratio?) {
        this.oracion = oracion
    }


    fun setOfficeOfReading(oficioLecturas: LHOfficiumLectionis?) {
        this.lhOfficeOfReading = oficioLecturas
    }


    fun setMixto(LHMixtus: LHMixtus?) {
        this.LHMixtus = LHMixtus
    }

    open fun getOficio(): LHOfficium? {
        return oficio
    }

    fun getOficio(hasInvitatory: Boolean): LHOfficium? {
        //oficio!!.invitatorio?.isMultiple = hasInvitatory
        return oficio
    }

    open fun setOficio(oficio: LHOfficium?) {
        this.oficio = oficio
    }

    open fun getLaudes(): LHLaudes? {
        return laudes
    }

    open fun setLaudes(laudes: LHLaudes?) {
        this.laudes = laudes
    }

    fun getLaudes(hasInvitatory: Boolean): LHLaudes? {
        laudes!!.invitatorium.isMultiple = hasInvitatory
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

    fun setVisperas(visperas: Visperas) {
        this.visperas = visperas
    }

    fun getOficioEaster(): OficioEaster? {
        return oficioEaster
    }

    fun setOficioEaster(oficioEaster: OficioEaster?) {
        this.oficioEaster = oficioEaster
    }
    /*
    val mixtoView : SpannableStringBuilder =
        getMixtoForView(timeFK,false)

    val mixtoRead : StringBuilder =
        mixto!!.getMixtoForRead()

    val oficioView : SpannableStringBuilder =
        oficio!!.getForView(timeFK,false)

    val oficioRead : StringBuilder =
        oficio!!.forRead

    val laudesView : SpannableStringBuilder =
        laudes!!.getForView(timeFK,false)

    val laudesRead : StringBuilder =
        laudes!!.forRead

    val visperasView : SpannableStringBuilder =
        visperas!!.getForView(timeFK)

    val visperasRead : StringBuilder =
        visperas!!.getAllForRead()

    val completasView : SpannableStringBuilder =
        completas!!.getAllForView(timeFK)

    val completasRead : StringBuilder =
        completas!!.getForRead()

*/

    /**
     * Obtiene el contenido de **`Mixto`** formateado para la vista.
     *
     * @params [calendarTime] Identifica el tiempo litúrgico del calendario.
     * @param [hasSaint] Identifica si la celebración tiene santo.
     * @return Un [SpannableStringBuilder] con el contenido formateado.
     */
    fun getMixtoForView(calendarTimes: Int, hasSaints: Boolean): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            //this.hasSaint = hasSaint
            //oficio?.invitatorio!!.normalizeByTime(calendarTime)
            //invitatory!!.normalizeByTime(calendarTime)
            //laudes!!.salmodia?.normalizeByTime(calendarTime)
            /*laudes!!.lecturaBreve!!.normalizeByTime(calendarTime)
            oficio!!.lhOfficeOfReading?.normalizeByTime(calendarTime)
            laudes!!.gospelCanticle!!.normalizeByTime(calendarTime)

            sb.append(Utils.LS2)
            if (santo != null && hasSaint) {
                oficio?.invitatorio!!.normalizeIsSaint(santo!!.theName)
                sb.append(santo?.vidaSmall)
                sb.append(Utils.LS)
            }
            sb.append(LHMixtus!!.tituloHora)

            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(introAbreMisLabiosView)
            sb.append(Utils.LS2)
            sb.append(oficio!!.invitatorio?.getAllForView(-1,calendarTime))
            sb.append(Utils.LS2)

            sb.append(laudes!!.lhHymn!!.all)
            sb.append(Utils.LS2)

            sb.append(laudes!!.salmodia?.getAllForView(-1,calendarTime))

            sb.append(laudes!!.lecturaBreve?.getAllWithHourCheck(2))
            sb.append(Utils.LS)

            sb.append(oficio!!.lhOfficeOfReading?.allForView)
            sb.append(LHMixtus!!.evangeliosForView)

            sb.append(laudes!!.gospelCanticle?.all)
            sb.append(Utils.LS2)

            sb.append(laudes!!.preces?.all)
            sb.append(Utils.LS2)

            sb.append(PadreNuestro.all)
            sb.append(Utils.LS2)

            sb.append(laudes!!.oracion?.all)
            sb.append(Utils.LS2)

            sb.append(getConclusionHorasMayores())*/
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    fun getMixtoForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            if (santo != null && hasSaint) {
                sb.append(santo?.vidaSmall)
            }
            /*sb.append(LHMixtus!!.tituloHoraForRead)
            sb.append(introAbreMisLabiosForRead)
            sb.append(oficio!!.invitatorio?.allForRead)
            sb.append(laudes!!.lhHymn?.allForRead)
            sb.append(laudes!!.salmodia?.getAllForRead(-1))
            sb.append(laudes!!.lecturaBreve?.getAllForRead())
            sb.append(oficio!!.lhOfficeOfReading?.allForRead)
            sb.append(LHMixtus!!.evangeliosForRead)
            sb.append(laudes!!.gospelCanticle?.allForRead)
            sb.append(laudes!!.preces?.allForRead)
            sb.append(PadreNuestro.all)
            sb.append(laudes!!.getOracion()?.allForRead)
            sb.append(getConclusionHorasMayoresForRead())*/
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }


    @Deprecated("Trasladar a RitusConclusionis")
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
            val ssb = SpannableStringBuilder(
                Utils.formatTitle(
                    Constants.TITLE_CONCLUSION))
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