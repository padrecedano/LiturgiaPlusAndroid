package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils
import java.util.*

class Rosario {
    var saludo: String? = null
    var padrenuestro: String? = null
    var avemaria: String? = null
    var gloria: String? = null
    var letanias: String? = null
    var oracion: String? = null
    var salve: String? = null
    private var misterios: List<Misterio>? = null
    var day = 0

    /*
        @TODO
        - Arreglar esto de otro modo
       */
    val byDay: String
        get() = when (day) {
            1 -> "Misterios Gloriosos"
            2 -> "Misterios Gozosos"
            3 -> "Misterios Dolorosos"
            4 -> "Misterios Luminosos"
            else -> "*"
        }

    fun getSaludoForView(): Spanned {
        return Utils.fromHtml(saludo)
    }


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

    fun getLetaniasForView(): Spanned {
        return Utils.fromHtml(letanias)
    }


    fun getOracionForView(): Spanned {
        return Utils.fromHtml(oracion)
    }


    fun getSalveForView(): Spanned {
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

    fun getMisterios(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val m = misterios!![day - 1]
        sb.append(Utils.LS)
        sb.append(Utils.toH2Red(m.titulo))
        sb.append(Utils.LS2)
        var x = 0
        for (s in m.contenido!!) {
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
            x++
        }
        return sb
    }

    fun setMisterios(misterios: List<Misterio>?) {
        this.misterios = misterios
    }

    val misteriosBrevis: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            val m = misterios!![day - 1]
            sb.append(Utils.LS)
            sb.append(Utils.toH2Red(m.titulo))
            sb.append(Utils.LS2)
            var x = 0
            for (s in m.contenido!!) {
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
                x++
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
    val misteriosForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            val m = misterios!![day - 1]
            sb.append(m.titulo)
            sb.append(".")
            var x = 0
            for (s in m.contenido!!) {
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
                x++
            }
            return sb
        }
    private val titleForRead: String
        private get() = "Santo Rosario."
    val subTitle: String
        get() = byDay
}