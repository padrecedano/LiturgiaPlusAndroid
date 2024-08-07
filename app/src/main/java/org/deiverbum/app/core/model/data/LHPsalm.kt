package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.room.ColumnInfo
import androidx.room.Ignore
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForRead
import org.deiverbum.app.util.LiturgyHelper.Companion.endPsalmForView
import org.deiverbum.app.util.Utils

/**
 * Representa un salmo para la capa de datos externa.
 * @property theOrder El orden del salmo en la salmodia
 * @property pericopa La referencia bíblica
 * @property theme El tema (puede ser nulo)
 * @property epigraph El texto del epígrafe (puede ser nulo)
 * @property thePart El valor para la parte del salmo (puede ser nulo)
 * @property psalmus El texto del salmo
 *
 */
open class LHPsalm(
    @Ignore var theOrder: Int = 0,
    @ColumnInfo(name = "quote")
    var pericopa: String = "",
    @ColumnInfo(name = "psalm")
    var psalmus: String = "",
) {
    constructor(
        theOrder: Int = 0,
        pericopa: String = "",
        theme: String = "",
        epigraph: String = "",
        thePart: Int = 0,
        psalmus: String
    ) : this(theOrder, pericopa, psalmus) {
        this.theme = theme
        this.epigraph = epigraph
        this.thePart = thePart
    }

    constructor(
        theOrder: Int = 0,
        pericopa: String = "",
        theme: String = "",
        psalmus: String
    ) : this(theOrder, pericopa, psalmus) {
        this.theme = theme
    }

    init {
        if (this.pericopa.contains("¦")) {
            this.pericopa = this.pericopa.replace("¦", "  ")
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
        get() = Utils.toRoman(thePart)

    fun thePartComposable(rubricColor: Color): AnnotatedString {
        return Utils.toRedCompose(Utils.toRoman(thePart), rubricColor)
    }

    val quoteForView: SpannableStringBuilder
        get() = Utils.toRed(pericopa)

    fun pericopaComposable(rubricColor: Color): AnnotatedString {
        return Utils.toRedCompose(pericopa, rubricColor)
    }

    fun themeComposable(rubricColor: Color): AnnotatedString {
        return Utils.toRedCompose(theme, rubricColor)
    }

    fun epigraphComposable(): AnnotatedString {
        return Utils.composeSmallText(epigraph!!)
    }


    fun psalmComposable(): AnnotatedString {
        val asb = AnnotatedString.Builder()
        if (psalmus.endsWith("∸")) {
            asb.append(
                Utils.fromHtml(psalmus)
            ).append(noGloria)
        } else {
            asb.append(Utils.fromHtml(psalmus))
            asb.append(Utils.LS2)
            asb.append(endPsalmForView)
        }
        return asb.toAnnotatedString()
    }

    val psalmForView: SpannableStringBuilder
        get() = if (psalmus.endsWith("∸")) {
            SpannableStringBuilder(
                Utils.fromHtml(psalmus)
            ).append(noGloria)
        } else {
            SpannableStringBuilder(
                Utils.fromHtml(psalmus)
            ).append(Utils.LS2).append(endPsalmForView)
        }


    val psalmForRead: SpannableStringBuilder
        get() = if (psalmus.endsWith("∸")) {
            SpannableStringBuilder(
                Utils.getFormatoForRead(psalmus)
            )
        } else {
            SpannableStringBuilder(
                Utils.getFormatoForRead(psalmus)
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