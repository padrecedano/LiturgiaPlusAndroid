package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

/**
 * Esta clase sirve para manejar la salmodia del Breviario.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 *
 * @see [Sortable]
 */
open class LHPsalmody(@Ignore open var antiphonae: MutableList<LHAntiphon>, var typus: Int) :
    Sortable {

    /**
     * Constructor secundario
     * @param psalmus Lista con los salmos
     * @param antiphonae Lista con las antífonas
     * @param typus El tipo de salmodia
     */
    constructor(
        psalmus: MutableList<LHPsalm>,
        antiphonae: MutableList<LHAntiphon>,
        typus: Int
    ) : this(
        antiphonae, typus
    ) {
        this.psalmus = psalmus
        //this.antiphonae = antiphonae
    }

    //var typus = 0

    @Ignore
    open var psalmus: MutableList<LHPsalm> = mutableListOf()

    //@Ignore
    //protected open var antiphonae: MutableList<LHAntiphon> = mutableListOf()

    /**
     * Obtiene todos los elementos del salmo formateados para la vista.
     * Desde aquí se llama a [sort] porque este método se invoca una sola vez.
     *
     * @since 2024.1
     *
     * @param hourIndex El identificador de la hora, para los casos de la Hora Intermedia.
     * @param calendarTime El tiempo litúrgico, para hacer los reemplazos correspondientes en las antífonas llamando a [LHAntiphon.normalizeByTime].
     *
     */
    open fun getAllForView(hourIndex: Int, calendarTime: Int): SpannableStringBuilder {
        sort()
        val sb = SpannableStringBuilder(header)
        sb.append(LS2)
        val antiphonBefore = SpannableStringBuilder()
        val antiphonAfter = SpannableStringBuilder()

        if (typus == 1) {
            if (psalmus.size == antiphonae.size) {
                antiphonae[hourIndex].normalizeByTime(calendarTime)
                antiphonBefore.append(antiphonae[hourIndex].getBeforeForView(false))
                antiphonAfter.append(antiphonae[hourIndex].afterForView)

            } else {
                antiphonae[0].normalizeByTime(calendarTime)
                antiphonBefore.append(antiphonae[0].getBeforeForView())
                antiphonAfter.append(antiphonae[0].afterForView)
            }
            sb.append(antiphonBefore)
            sb.append(LS2)
            for (s in psalmus) {
                if (s.pericopa != "") {
                    sb.append(s.quoteForView)
                    sb.append(LS2)
                }

                if (s.theme != null && s.theme != "") {
                    sb.append(Utils.toRed(s.theme))
                    sb.append(LS2)
                }
                if ((s.epigraph != null) && (s.epigraph != "")) {
                    sb.append(Utils.fromHtmlSmall(s.epigraph!!))
                    sb.append(LS2)
                }
                if ((s.thePart != null) && (s.thePart != 0)) {
                    sb.append(Utils.toRed(s.partForView))
                    sb.append(LS2)
                }

                sb.append(s.psalmForView)
                sb.append(LS2)
            }
            //sb.append(Utils.toRed("Ant. "))
            sb.append(antiphonAfter)
            sb.append(LS2)
        }

        if (typus == 0 && psalmus.size == antiphonae.size) {
            for (s in psalmus) {
                antiphonae[s.theOrder - 1].normalizeByTime(calendarTime)
                sb.append(antiphonae[s.theOrder - 1].getBeforeForView())
                sb.append(LS2)
                if (s.pericopa != "") {
                    sb.append(s.quoteForView)
                    sb.append(LS2)
                }
                if ((s.theme != null) && (s.theme != "")) {
                    sb.append(Utils.toRed(s.theme))
                    sb.append(LS2)
                }
                if ((s.epigraph != null) && (s.epigraph != "")) {
                    sb.append(Utils.fromHtmlSmall(s.epigraph!!))
                    sb.append(LS2)
                }
                if ((s.thePart != null) && (s.thePart != 0)) {
                    sb.append(Utils.toRed(s.partForView))
                    sb.append(LS2)
                }
                sb.append(s.psalmForView)
                sb.append(LS2)
                sb.append(antiphonae[s.theOrder - 1].afterForView)
                sb.append(LS2)
            }
        }
        return sb
    }


    /**
     * Obtiene el contenido de la salmodia formateado para la lectura de voz.
     *
     * @param hourIndex Un entero con el índice de la hora intermedia, según la convención siguiente:
     *
     * - **`0`** = **Tercia**,
     * - **`1`** = **Sexta**,
     * - **`2`** = **Nona**.
     *
     * Esto servirá para tomar el salmo correspondiente en las salmodias con antífonas del tipo:
     *
     *  **`1`**: una antífona única, que varía según la hora.
     *
     * Para las otras horas, se llamará a este método con el valor **`-1`**
     *
     * @return Un [StringBuilder] con el contenido.
     *
     * @since 2022.01
     *
     * @version 2025.1
     */
    fun getAllForRead(hourIndex: Int = -1): StringBuilder {
        val sb = StringBuilder(headerForRead)
        val uniqueAnt: String
        if (typus == 1) {
            uniqueAnt = if (psalmus.size == antiphonae.size) {
                antiphonae[hourIndex].antiphon

            } else {
                antiphonae[0].antiphon
            }
            sb.append(uniqueAnt)
            for (s in psalmus) {
                sb.append(s.psalmForRead)
            }
            sb.append(uniqueAnt)
        }

        if (typus == 0 && psalmus.size == antiphonae.size) {
            for (s in psalmus) {
                sb.append(antiphonae[s.theOrder - 1].antiphon)
                sb.append(s.psalmForRead)
                sb.append(antiphonae[s.theOrder - 1].antiphon)
            }
        }
        return sb
    }

    /**
     * Obtiene un salmo por el índice dado.
     * No se llama aquí a [sort] porque este método es invocado por cada salmo.
     *
     * @see [LHOfficiumPascua.forView]

     */
    fun getSalmosByIndex(index: Int, calendarTime: Int): SpannableStringBuilder {
        //sort()
        val sb = SpannableStringBuilder(header)
        sb.append(LS2)
        val s = psalmus[index]
        antiphonae[index].normalizeByTime(calendarTime)
        sb.append(antiphonae[index].afterForView)
        sb.append(LS2)

        if (s.pericopa != "") {
            sb.append(s.quoteForView)
            sb.append(LS2)
        }
        if (s.theme != null && s.theme != "") {
            sb.append(Utils.toRed(s.theme))
            sb.append(Utils.LS2)
        }
        if (s.epigraph != null && s.epigraph != "") {
            sb.append(Utils.fromHtmlSmall(s.epigraph!!))
            sb.append(Utils.LS2)
        }
        if (s.thePart != null && s.thePart != 0) {
            sb.append(Utils.toRed(s.partForView))
            sb.append(Utils.LS2)
        } else {
            //sb.append(Utils.LS2)
        }

        sb.append(s.psalmForView)
        sb.append(Utils.LS2)
        sb.append(antiphonae[index].afterForView)
        return sb
    }


    fun getSalmosByIndexForRead(index: Int): StringBuilder {
        val sb = StringBuilder(headerForRead)
        val s = psalmus[index]
        sb.append(antiphonae[index].antiphon)
        sb.append(s.psalmForRead)
        sb.append(antiphonae[index].antiphon)
        return sb
    }

    open val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PSALMODY)
    open val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PSALMODY)

    /**
     * Ordena los salmos y las antífonas de la salmodia.
     *
     * @since 2024.1
     */
    override fun sort() {
        psalmus = psalmus.sortedBy { it.theOrder } as MutableList<LHPsalm>
        antiphonae = antiphonae.sortedBy { it.theOrder } as MutableList<LHAntiphon>
        //antiphonae.sortBy { it.theOrder }
    }


}