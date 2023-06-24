package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class LHPsalmody {
    var groupFK: Int? = null
    var readingFK: Int? = null
    var order: Int? = null
    var antiphonFK: Int? = null
    var themeFK: Int? = null
    var epigraphFK: Int? = null
    var part: Int? = null

    @Ignore
    var tipo = 0

    //@ColumnInfo(name = "salmos")
    @Ignore
    private var salmos: MutableList<LHPsalm> = mutableListOf()
    fun sort() {
        salmos.sortBy { it.theOrder }
    }

    private val salmosForRead: SpannableStringBuilder
        get() = getSalmosForRead(-1)

    private fun getSalmos(): SpannableStringBuilder {
        return getSalmos(-1)
    }

    fun setSalmos(salmos: MutableList<LHPsalm>) {
        this.salmos = salmos
    }

    private fun getSalmos(hourIndex: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder("")
        var salmo: String?
        val preAntifona = "Ant. "
        var antUnica: String?
        if (tipo == 1) {
            sb.append(Utils.toRed(preAntifona))
            antUnica = salmos!![hourIndex].antiphon
            sb.append(antUnica)
        }
        if (tipo == 2) {
            sb.append(Utils.toRed(preAntifona))
            antUnica = salmos!![0].antiphon
            sb.append(antUnica)
        }
        for (s in salmos!!) {
            val tema = SpannableStringBuilder("")
            val parte = SpannableStringBuilder("")
            val intro = SpannableStringBuilder("")
            val ref = SpannableStringBuilder("")
            val preRef = s.ref.toString()
            if (tipo == 0) {
                sb.append(Utils.toRed(preAntifona + s.theOrder + ". "))
                sb.append(Utils.fromHtml(s.antiphon))
            }
            if (s.theme != "") {
                tema.append(Utils.toRed(s.theme))
                tema.append(Utils.LS2)
            }
            if (s.epigraph != "") {
                intro.append(Utils.fromHtmlSmall(s.epigraph))
                intro.append(Utils.LS2)
            }
            if (s.part != "") {
                parte.append(Utils.toRed(s.part))
                parte.append(Utils.LS2)
            }
            if (preRef.isNotEmpty()) {
                ref.append(Utils.LS)
                ref.append(s.ref)
                ref.append(Utils.LS2)
            }
            sb.append(Utils.LS2)
            sb.append(ref)
            sb.append(tema)
            sb.append(intro)
            sb.append(parte)
            salmo = Utils.getFormato(s.psalm)
            sb.append(Utils.fromHtml(salmo))
            if (s.psalm.endsWith("∸")) {
                sb.append(Utils.LS)
                sb.append(noGloria)
            } else {
                sb.append(Utils.LS2)
                sb.append(finSalmo)
            }
            if (tipo == 0) {
                sb.append(Utils.LS2)
                sb.append(Utils.toRed(preAntifona))
                sb.append(getAntifonaLimpia(s.antiphon))
                sb.append(Utils.LS2)
            }
        }
        if (tipo == 1) {
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(preAntifona))
            antUnica = getAntifonaLimpia(salmos!![hourIndex].antiphon)
            sb.append(antUnica)
            sb.append(Utils.LS2)
        }
        if (tipo == 2) {
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(preAntifona))
            antUnica = getAntifonaLimpia(salmos!![0].antiphon)
            sb.append(antUnica)
            sb.append(Utils.LS2)
        }
        return sb
    }

    fun getSalmosByIndex(index: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder("")
        val preAntifona = "Ant. "
        sb.append(Utils.toRed(preAntifona))
        //sb.append(salmos.get(index).getAntiphon());
        val s = salmos!![index]
        sb.append(Utils.fromHtml(s.antiphon))
        val tema = SpannableStringBuilder("")
        val parte = SpannableStringBuilder("")
        val intro = SpannableStringBuilder("")
        val ref = SpannableStringBuilder("")
        val preRef = s.ref.toString()

        //sb.append(Utils.toRed(preAntifona + s.getTheOrder() + ". "));
        if (s.theme != "") {
            tema.append(Utils.toRed(s.theme))
            tema.append(Utils.LS2)
        }
        if (s.epigraph != "") {
            intro.append(Utils.fromHtmlSmall(s.epigraph))
            intro.append(Utils.LS2)
        }
        if (s.part != "") {
            parte.append(Utils.toRed(s.part))
            parte.append(Utils.LS2)
        }
        if (preRef.isNotEmpty()) {
            ref.append(Utils.LS)
            ref.append(s.ref)
            ref.append(Utils.LS2)
        }
        sb.append(Utils.LS2)
        sb.append(ref)
        sb.append(tema)
        sb.append(intro)
        sb.append(parte)
        //salmo = Utils.getFormato(s.getSalmo());
        sb.append(Utils.fromHtml(Utils.getFormato(s.psalm)))
        if (s.psalm.endsWith("∸")) {
            sb.append(Utils.LS)
            sb.append(noGloria)
        } else {
            sb.append(Utils.LS2)
            sb.append(finSalmo)
        }
        sb.append(Utils.LS2)
        sb.append(Utils.toRed(preAntifona))
        sb.append(getAntifonaLimpia(s.antiphon))
        sb.append(Utils.LS2)
        return sb
    }

    private fun getSalmosForRead(hourIndex: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        var salmo: String?
        var antUnica = ""
        if (tipo == 1) {
            antUnica = getAntifonaLimpia(salmos!![hourIndex].antifonaForRead)
            sb.append(antUnica)
        }
        if (tipo == 2) {
            antUnica = getAntifonaLimpia(salmos!![0].antifonaForRead)
            //antUnica = salmoCompleto.get(0).getAntifona();
            sb.append(antUnica)
        }
        for (s in salmos!!) {
            if (tipo == 0) {
                sb.append(Utils.fromHtml(s.antifonaForRead))
            }
            sb.append(Utils.LS2)
            salmo = Utils.getFormatoForRead(s.psalm)
            sb.append(Utils.fromHtml(salmo))
            if (!s.psalm.endsWith("∸")) {
                sb.append(finSalmoForRead)
            }
            sb.append(Utils.LS2)
            if (tipo == 0) {
                sb.append(getAntifonaLimpia(s.antifonaForRead))
                sb.append(Utils.LS2)
            }
        }
        if (tipo == 1) {
            sb.append(getAntifonaLimpia(antUnica))
            sb.append(Utils.LS2)
        }
        if (tipo == 2) {
            sb.append(getAntifonaLimpia(antUnica))
            sb.append(Utils.LS2)
        }
        return sb
    }

    fun getSalmosByIndexForRead(index: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val salmo: String
        val s = salmos!![index]
        sb.append(Utils.fromHtml(s.antifonaForRead))
        sb.append(Utils.LS2)
        salmo = Utils.getFormatoForRead(s.psalm)
        sb.append(Utils.fromHtml(salmo))
        if (!s.psalm.endsWith("∸")) {
            sb.append(finSalmoForRead)
        }
        sb.append(Utils.LS2)
        sb.append(getAntifonaLimpia(s.antifonaForRead))
        sb.append(Utils.LS2)
        return sb
    }

    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PSALMODY)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PSALMODY)

    /**
     *
     * Obtiene el contenido de la salmodia formateado, para la vista.
     *
     * @return Un [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(getSalmos())
            return sb
        }

    /**
     *
     * Obtiene el contenido de la salmodia formateado, para la vista.
     *
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    fun getAll(hourIndex: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(header)
        sb.append(Utils.LS2)
        sb.append(getSalmos(hourIndex))
        return sb
    }

    /**
     *
     * Obtiene el contenido de la salmodia formateado, para la vista.
     *
     * @return Un [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    val allForRead: String
        get() = headerForRead +
                salmosForRead

    /**
     *
     * Obtiene el contenido de la salmodia formateado, para la vista.
     *
     * @param hourIndex Un entero con el índice de la hora intermedia
     * @return Un [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    fun getAllForRead(hourIndex: Int): String {
        return headerForRead +
                getSalmosForRead(hourIndex)
    }

    /**
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    val finSalmo: Spanned
        get() {
            val fin = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." + Constants.BR
                    + Constants.NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                    + Constants.NBSP_SALMOS + "por los siglos de los siglos. Amén.")
            return Utils.fromHtml(fin)
        }
    private val finSalmoForRead: String
        get() = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.")

    /**
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @since 2022.01
     */
    private val noGloria: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder("No se dice Gloria")
            return Utils.toRedNew(sb)
        }

    /**
     * Método que limpia la segunda parte de la antífona, en el caso del símblo †
     *
     * @param sAntifona Una cadena con el texto de la antífona
     * @return La misma cadena, pero sin el referido símbolo
     */
    private fun getAntifonaLimpia(sAntifona: String?): String {
        return sAntifona!!.replace("†", "")
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        for (s in salmos!!) {
            s.normalizeByTime(calendarTime)
        }
    }
}