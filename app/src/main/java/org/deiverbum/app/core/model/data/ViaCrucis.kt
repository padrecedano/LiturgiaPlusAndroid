package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.inNomineForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.inNomineForView
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils

@Suppress("unused")
//@Suppress("")
class ViaCrucis {
    private var subTitulo: String? = null
    private var fecha: String? = null
    private var autor: String? = null
    private var introViaCrucis: IntroViaCrucis? = null
    private var adoramus: String? = null
    private var respuestas: List<String?>? = null
    private var estaciones: List<Estacion>? = null
    private var oracion: String? = null

    @Transient
    private var introitus: Introitus = Introitus()

    @Transient
    private var conclusionis: RitusConclusionis = RitusConclusionis()

    private fun getTitulo(): String {
        return "Vía Crucis"
    }

    private fun getSubTitulo(): String? {
        return subTitulo
    }

    fun setSubTitulo(subTitulo: String?) {
        this.subTitulo = subTitulo
    }

    private fun getFecha(): String? {
        return fecha
    }

    fun setFecha(fecha: String?) {
        this.fecha = fecha
    }

    private fun getAutor(): String? {
        return autor
    }

    fun setAutor(autor: String?) {
        this.autor = autor
    }

    fun getIntroViaCrucis(): IntroViaCrucis? {
        return introViaCrucis
    }

    fun setIntro(introViaCrucis: IntroViaCrucis?) {
        this.introViaCrucis = introViaCrucis
    }

    private fun getAdoramus(): SpannableStringBuilder {
        val textParts =
            adoramus!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val sb = SpannableStringBuilder("")
        sb.append(Utils.toRed("V/. "))
        sb.append(textParts[0])
        sb.append(Utils.LS2)
        sb.append(Utils.toRed("R/. "))
        sb.append(textParts[1])
        return sb //this.adoramus;
    }

    fun setAdoramus(adoramus: String?) {
        this.adoramus = adoramus
    }

    fun getRespuestas(): List<String?>? {
        return respuestas
    }

    fun setRespuestas(respuestas: List<String?>?) {
        this.respuestas = respuestas
    }

    private fun getAllEstaciones(): SpannableStringBuilder {
        val sb = SpannableStringBuilder("")
        for (e in estaciones!!) {
            sb.append(Utils.LS)
            sb.append(Utils.toH3Red(e.titulo))
            sb.append(Utils.LS2)
            sb.append(Utils.toH3(e.subtitulo))
            sb.append(Utils.LS2)
            sb.append(getAdoramus())
            sb.append(Utils.LS2)
            sb.append(e.textoBiblicoSpan)
            sb.append(Utils.LS2)
            sb.append(e.getMeditacionSpan(respuestas!!))
            sb.append(Utils.LS)
            sb.append(PadreNuestro.allForRead)
            sb.append(Utils.LS2)
            sb.append(Utils.toH4Red("Estrofa del Stabat Mater"))
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(Utils.getFormato(e.canto!!)))
            sb.append(Utils.LS2)
        }
        return sb
    }

    fun getAllEstacionesForRead(): StringBuilder {
        val sb = StringBuilder()
        val textParts =
            adoramus!!.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val txtAdoramus = textParts[0] + "." + textParts[1] + "."
        for (e in estaciones!!) {
            sb.append(e.titulo)
            sb.append(".")
            sb.append(e.subtitulo)
            sb.append(".")
            sb.append(txtAdoramus)
            sb.append(e.textoBiblico)
            sb.append(".")
            sb.append(e.getMeditacionSpan(respuestas!!))
            sb.append(PadreNuestro.allForRead)
            sb.append("Estrofa del Stabat Mater.")
            sb.append(Utils.fromHtml(Utils.getFormato(e.canto!!)))
        }
        return sb
    }

    fun getEstaciones(): List<Estacion>? {
        return estaciones
    }

    fun setEstaciones(estaciones: List<Estacion>?) {
        this.estaciones = estaciones
    }

    private fun getOracion(): String? {
        return oracion
    }

    fun setOracion(oracion: String?) {
        this.oracion = oracion
    }

    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = isNightMode
        val sb = SpannableStringBuilder("")
        sb.append(Utils.toH2Red(getTitulo()))
        sb.append(Utils.LS2)
        sb.append(Utils.toH3(getSubTitulo()))
        sb.append(Utils.LS)
        sb.append(Utils.toH4(getFecha()))
        sb.append(Utils.LS)
        sb.append(Utils.toH4Red(getAutor()))
        sb.append(Utils.LS2)
//        sb.append(Utils.getSaludoEnElNombre())
        sb.append(inNomineForView)

        sb.append(Utils.LS)
        sb.append(Utils.toH3Red("Preámbulo"))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(introViaCrucis?.intro!!))
        sb.append(Utils.LS2)
        sb.append(Utils.toH3Red("Oración inicial"))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(introViaCrucis?.oracion!!))
        sb.append(Utils.LS2)
        sb.append(getAllEstaciones())
        sb.append(Utils.toH3Red("Oración final"))
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(Utils.getFormato(getOracion()!!)))
        sb.append(Utils.LS2)
        sb.append(RitusConclusionis.titleForView)
        sb.append(Utils.LS2)
        sb.append(RitusConclusionis.viewDominusnosBenedicat)
        //sb.append(Utils.LS2)
        sb.append(Utils.toRed("Si la celebración la preside un ministro ordenado se concluye con la bendición, como habitualmente."))
        return sb
    }

    fun getForRead(): StringBuilder {
        val sb = StringBuilder()
        sb.append(getTitulo())
        sb.append(".")
        sb.append(getSubTitulo())
        sb.append(".")
        sb.append(getAutor())
        sb.append(".")
        sb.append(inNomineForRead)
        sb.append(Utils.toH3Red("Preámbulo."))
        sb.append(Utils.fromHtml(introViaCrucis?.intro!!))
        sb.append("Oración inicial.")
        sb.append(Utils.fromHtml(introViaCrucis?.oracion!!))
        sb.append(getAllEstaciones())
        sb.append("Oración final.")
        sb.append(Utils.fromHtml(Utils.getFormato(getOracion()!!)))
        sb.append(RitusConclusionis.titleForRead)
        sb.append(RitusConclusionis.horasMayoresForRead)
        return sb
    }
}