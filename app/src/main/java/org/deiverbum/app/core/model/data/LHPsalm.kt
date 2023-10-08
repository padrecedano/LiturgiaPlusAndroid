package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForRead
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.replaceByTime

/**
 * Representa un salmo para la capa de datos externa.
 * @property theOrder El orden del salmo en la salmodia
 * @property quote La referencia bíblica
 * @property theme El tema (puede ser nulo)
 * @property epigraph El texto del epígrafe (puede ser nulo)
 * @property thePart El valor para la parte del salmo (puede ser nulo)
 * @property psalm El texto del salmo
 *
 */
open class LHPsalm(
    @Ignore var theOrder: Int = 0,
    var quote: String = "",
    @Ignore var theme: String? = null,
    @Ignore var epigraph: String? = null,
    @Ignore var thePart: Int? = null,
    var psalm: String = "",
) /*: Comparable<LHPsalm>*/ {
    var psalmID = 0
    var readingID = 0
    //var psalm = ""

    @Deprecated("")
    @Ignore
    var lhAntiphon: LHAntiphon? = null

    //@Ignore
    //var theOrder: Int = 0


    //@Ignore
    //var theme: String = ""

    //@Ignore
    //var epigraph: String = ""

    //@Ignore
    //var part: Int = 0

    @Ignore
    var quotee = ""
        set(value) {
            field = if (value.contains("¦")) {
                value.replace("¦", "  ")
            } else {
                value
            }
        }

    val partForView: String
        get() = ""//if (part != 0) Utils.toRoman(part) else ""

    val quoteForView: SpannableStringBuilder
        get() = Utils.toRed(quote)

    @Deprecated("Se traslada a LHAntiphon", replaceWith = ReplaceWith("LHAntiphon.forRead"))
    open val antifonaForRead: String
        get() = if (lhAntiphon != null && lhAntiphon!!.antiphon != "") Utils.normalizeEnd(lhAntiphon!!.antiphon) else ""

    @Deprecated("Se traslada a LHAntiphon")
    open val antiphonForView: String
        get() = if (lhAntiphon != null && lhAntiphon!!.antiphon != "") lhAntiphon!!.antiphon else ""

    val ref: SpannableStringBuilder
        get() = if (quote != "") {
            SpannableStringBuilder(
                Utils.toRedHtml(
                    Utils.getFormato(quote)
                )
            )
        } else {
            SpannableStringBuilder("")
        }

    val psalmForView: SpannableStringBuilder
        get() = if (psalm.endsWith("∸")) {
            SpannableStringBuilder(
                Utils.fromHtml(psalm)
            ).append(noGloria)
        } else {
            SpannableStringBuilder(
                Utils.fromHtml(psalm)
            ).append(Utils.LS2).append(endPsalmForView)
        }

    val psalmForRead: SpannableStringBuilder
        get() = if (psalm.endsWith("∸")) {
            SpannableStringBuilder(
                Utils.getFormatoForRead(psalm)
            )
        } else {
            SpannableStringBuilder(
                Utils.getFormatoForRead(psalm)
            ).append(endPsalmForRead)
        }


    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario.
     * No se traslada este método a la fuente local (entidades de la base de datos), porque debe resolverse en el modelo,
     * ya sea que los datos vengan de una fuente local, remota u otra.
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    open fun normalizeByTime(calendarTime: Int) {
        this.lhAntiphon!!.antiphon =
            if (this.lhAntiphon!!.antiphon.isEmpty()) "" else replaceByTime(
                this.lhAntiphon!!.antiphon,
                calendarTime
            )
    }

    /**
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @since 2022.01
     */
    private val noGloria: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder("No se dice Gloria")
            return Utils.toRedNew(sb)
        }


}