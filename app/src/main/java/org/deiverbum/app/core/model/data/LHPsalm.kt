package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForRead
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.Utils

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
    var psalm: String = "",
) {
    constructor(
        theOrder: Int = 0,
        quote: String = "",
        theme: String = "",
        epigraph: String = "",
        thePart: Int = 0,
        psalm: String
    ) : this(theOrder, quote, psalm) {
        this.theme = theme
        this.epigraph = epigraph
        this.thePart = thePart
    }

    constructor(
        theOrder: Int = 0,
        quote: String = "",
        theme: String = "",
        psalm: String
    ) : this(theOrder, quote, psalm) {
        this.theme = theme
    }

    init {
        if (this.quote.contains("¦")) {
            this.quote = this.quote.replace("¦", "  ")
        }

    }

    @Ignore
    var theme: String? = null

    @Ignore
    var epigraph: String? = null

    @Ignore
    var thePart: Int? = null

    var psalmID = 0
    var readingID = 0
    //var psalm = ""

    @Deprecated("")
    @Ignore
    var lhAntiphon: LHAntiphon? = null

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
     * @return La rúbrica cuando no se dice Gloria en los salmos.
     * @since 2022.01
     */
    private val noGloria: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder("No se dice Gloria")
            return Utils.toRedNew(sb)
        }
}