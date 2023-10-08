package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils
import java.util.*

class Rosario {
    private var saludo: String = ""
    private var padrenuestro: String = ""
    private var avemaria: String = ""
    var gloria: String = ""
    private var letanias: String = ""
    var oracion: String = ""
    private var salve: String = ""
    private var misterios: List<Misterio> = emptyList()
    var day = 0

    /*
        @TODO
        - Arreglar esto de otro modo
       */
    private val byDay: String
        get() = when (day) {
            1 -> "Misterios Gloriosos"
            2 -> "Misterios Gozosos"
            3 -> "Misterios Dolorosos"
            4 -> "Misterios Luminosos"
            else -> "*"
        }

    private fun getSaludoForView(): Spanned {
        return Utils.fromHtml(saludo)
    }

    @Suppress("unused")
    fun misterioCompleto(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.fromHtml(padrenuestro))
        sb.append(Utils.LS2)
        for (i in 0..9) {
            sb.append(Utils.fromHtml(avemaria))
            sb.append(Utils.LS2)
        }
        sb.append(Utils.fromHtml(gloria))
        sb.append(Utils.LS2)
        return sb
    }

    private fun getLetaniasForView(): Spanned {
        return Utils.fromHtml(letanias)
    }


    private fun getOracionForView(): Spanned {
        return Utils.fromHtml(oracion)
    }


    private fun getSalveForView(): Spanned {
        return Utils.fromHtml(salve)
    }


    fun getForView(isBrevis: Boolean, nightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = nightMode
        val sb = SpannableStringBuilder()
        sb.append(Utils.toH3Red("INVOCACIÓN INICIAL"))
        sb.append(Utils.LS2)
        sb.append(getSaludoForView())
        sb.append(Utils.LS2)
        if (isBrevis) {
            sb.append(misteriosBrevis)
        } else {
            sb.append(getMisterios())
        }
        sb.append(Utils.toH3Red("LETANÍAS DE NUESTRA SEÑORA"))
        sb.append(Utils.LS2)
        sb.append(getLetaniasForView())
        sb.append(Utils.LS2)
        sb.append(Utils.toH3Red("SALVE"))
        sb.append(Utils.LS2)
        sb.append(getSalveForView())
        sb.append(Utils.LS2)
        sb.append(Utils.toH3Red("ORACIÓN"))
        sb.append(Utils.LS2)
        sb.append(getOracionForView())
        return sb
    }

    private fun getMisterios(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val m = misterios[day - 1]
        sb.append(Utils.LS)
        sb.append(Utils.toH2Red(m.titulo))
        sb.append(Utils.LS2)
        for ((x, s) in m.contenido!!.withIndex()) {
            sb.append(Utils.toH4Red(m.ordinalName[x]))
            sb.append(Utils.LS)
            sb.append(Utils.toH3Red(s))
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(padrenuestro))
            sb.append(Utils.LS2)
            for (i in 0..9) {
                sb.append(
                    Utils.toRed(
                        String.format(
                            Locale.getDefault(), "%d" +
                                    "%s", i + 1, ".-"
                        )
                    )
                )
                sb.append(Utils.LS)
                sb.append(Utils.fromHtml(avemaria))
                sb.append(Utils.LS2)
            }
            sb.append(Utils.LS)
            sb.append(Utils.fromHtml(gloria))
            sb.append(Utils.LS2)
            sb.append(Utils.LS)
        }
        return sb
    }

    @Suppress("unused")
    fun setMisterios(misterios: List<Misterio>) {
        this.misterios = misterios
    }

    private val misteriosBrevis: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            val m = misterios[day - 1]
            sb.append(Utils.LS)
            sb.append(Utils.toH2Red(m.titulo))
            sb.append(Utils.LS2)
            for ((x, s) in m.contenido!!.withIndex()) {
                sb.append(Utils.toH4Red(m.ordinalName[x]))
                sb.append(Utils.LS)
                sb.append(Utils.toH3Red(s))
                sb.append(Utils.LS2)
                sb.append("Padre Nuestro ...")
                sb.append(Utils.LS2)
                sb.append("10 Ave María")
                sb.append(Utils.LS2)
                sb.append("Gloria ...")
                sb.append(Utils.LS2)
                sb.append(Utils.LS)
            }
            return sb
        }
    val forRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(titleForRead)
            sb.append(getSaludoForView())
            sb.append(misteriosForRead)
            sb.append("LETANÍAS DE NUESTRA SEÑORA.")
            sb.append(getLetaniasForView())
            sb.append("SALVE.")
            sb.append(getSalveForView())
            sb.append("ORACIÓN.")
            sb.append(getOracionForView())
            return sb
        }
    private val misteriosForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            val m = misterios[day - 1]
            sb.append(m.titulo)
            sb.append(".")
            for ((x, s) in m.contenido!!.withIndex()) {
                sb.append(m.ordinalName[x])
                sb.append(".")
                sb.append(s)
                sb.append(".")
                sb.append(Utils.fromHtml(padrenuestro))
                sb.append(".")
                for (i in 0..9) {
                    sb.append(Utils.fromHtml(avemaria))
                }
                sb.append(Utils.fromHtml(gloria))
            }
            return sb
        }
    private val titleForRead: String
        get() = "Santo Rosario."
    val subTitle: String
        get() = byDay
}