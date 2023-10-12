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
open class LHPsalmody() : Sortable {

    /**
     * Constructor secundario
     * @param mPsalms Lista con los salmos
     * @param mAntiphons Lista con las antífonas
     */
    constructor(mPsalms: MutableList<LHPsalm>, mAntiphons: MutableList<LHAntiphon>) : this() {
        psalms = mPsalms
        antiphons = mAntiphons
    }

    var theType = 0

    @Ignore
    protected var psalms: MutableList<LHPsalm> = mutableListOf()

    @Ignore
    protected open var antiphons: MutableList<LHAntiphon> = mutableListOf()

    /**
     * Obtiene todos los elementos del salmo formateados para la vista.
     * Desde aquí se llama a [sort] porque este método se invoca una sola vez.
     *
     * @since 2023.1.3
     *
     * @param hourIndex El identificador de la hora, para los casos de la Hora Intermedia.
     * @param calendarTime El tiempo litúrgico, para hacer los reemplazos correspondientes en las antífonas llamando a [LHAntiphon.normalizeByTime].
     *
     */
    open fun getAllForView(hourIndex: Int, calendarTime: Int): SpannableStringBuilder {
        sort()
        val sb = SpannableStringBuilder(header)
        sb.append(LS2)
        val antUnique = SpannableStringBuilder(Utils.toRed("Ant. "))

        if (theType == 1) {
            if (psalms.size == antiphons.size) {
                antiphons[hourIndex].normalizeByTime(calendarTime)
                antUnique.append(antiphons[hourIndex].antiphon)

            } else {
                antiphons[0].normalizeByTime(calendarTime)
                antUnique.append(antiphons[0].antiphon)
            }
            sb.append(antUnique)
            sb.append(LS2)
            for (s in psalms) {
                if (s.quote != "") {
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
            sb.append(Utils.toRed("Ant. "))
            sb.append(antUnique)
            sb.append(LS2)
        }

        if (theType == 0 && psalms.size == antiphons.size) {
            for (s in psalms) {
                antiphons[s.theOrder - 1].normalizeByTime(calendarTime)
                sb.append(antiphons[s.theOrder - 1].getBeforeForView())
                sb.append(LS2)
                if (s.quote != "") {
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
                sb.append(antiphons[s.theOrder - 1].afterForView)
                sb.append(LS2)
            }
        }
        return sb
    }


    /**
     *
     * Obtiene el contenido de la salmodia formateado para la lectura de voz.
     *
     * @param hourIndex Un entero con el índice de la hora intermedia, según la convención siguiente:
     * **`0`** = **Tercia**, **`1`** = **Sexta**, **`2`** = **Nona**.
     * Esto servirá para tomar el salmo correspondiente en las salmodias con antífonas del tipo **`1`**:
     * una antífona única, que varía según la hora.
     * Para las otras horas, se llamará a este método con el valor -1
     * @return Un [StringBuilder] con el contenido.
     * @since 2022.01
     * @version 2023.1.3
     */
    fun getAllForRead(hourIndex: Int = -1): StringBuilder {
        val sb = StringBuilder(headerForRead)
        val uniqueAnt: String
        if (theType == 1) {
            uniqueAnt = if (psalms.size == antiphons.size) {
                antiphons[hourIndex].antiphon

            } else {
                antiphons[0].antiphon
            }
            sb.append(uniqueAnt)
            for (s in psalms) {
                sb.append(s.psalmForRead)
            }
            sb.append(uniqueAnt)
        }

        if (theType == 0 && psalms.size == antiphons.size) {
            for (s in psalms) {
                sb.append(antiphons[s.theOrder - 1].antiphon)
                sb.append(s.psalmForRead)
                sb.append(antiphons[s.theOrder - 1].antiphon)
            }
        }
        return sb
    }

    /**
     * Obtiene un salmo por el índice dado.
     * No se llama aquí a [sort] porque este método es invocado por cada salmo.
     *
     * @see [LHOfficeOfReadingEaster.getAllBiblica]

     */
    fun getSalmosByIndex(index: Int): SpannableStringBuilder {
        //sort()
        val sb = SpannableStringBuilder(header)
        sb.append(LS2)
        val s = psalms[index]

        sb.append(antiphons[index].afterForView)
        sb.append(LS2)

        if (s.quote != "") {
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

        //sb.append(Utils.LS2)
        sb.append(s.psalmForView)
        sb.append(Utils.LS2)
        sb.append(antiphons[index].afterForView)
        //sb.append(Utils.LS2)
        return sb
    }


    fun getSalmosByIndexForRead(index: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val s = psalms[index]
        sb.append(antiphons[index].antiphon)
        sb.append(s.psalmForRead)
        sb.append(Utils.LS2)
        sb.append(antiphons[index].antiphon)
        sb.append(Utils.LS2)
        return sb
    }

    open val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PSALMODY)
    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PSALMODY)

    /**
     * Ordena los salmos y las antífonas de la salmodia.
     *
     * @since 2023.1.3
     */
    override fun sort() {
        psalms.sortBy {
            it.theOrder
        }
        antiphons.sortBy {
            it.theOrder
        }
    }


}