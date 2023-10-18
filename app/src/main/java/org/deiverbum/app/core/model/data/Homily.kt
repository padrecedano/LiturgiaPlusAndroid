package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.LS2
import org.deiverbum.app.util.Utils.normalizeEnd

class Homily(
    var homily: String = "",
    var date: Int = 0,
    @Ignore
    var paterOpus: PaterOpus

) {
    constructor(homily: String, date: Int, paterOpus: PaterOpus, tema: String)
            : this(homily, date, paterOpus) {
        this.tema = tema
    }

    var homilyID: Int? = null
    var opusFK: Int? = null
    var book: Int? = null
    var chapter: Int? = null
    var number: Int? = null
    var paragraph: Int? = null
    var collectionFK: Int? = null
    var colNumber: Int? = null
    var colParagraph: Int? = null


    @Ignore
    var tema = ""

    @Ignore
    private var today: Today? = null

    fun getAllForView(): SpannableStringBuilder {
        //ColorUtils.isNightMode = todayRequest!!.isNightMode
        val sb = SpannableStringBuilder()
        try {
            sb.append(Utils.toH3Red(paterOpus.paterForView))
            sb.append(LS2)
            sb.append(Utils.toH4Red(paterOpus.singleName))
            sb.append(LS2)
            if (tema.isNotEmpty()) {
                sb.append(Utils.toRed(tema))
                sb.append(LS2)
            }
                if (date != 0) {
                    sb.append(
                        Utils.toRed(
                            Utils.formatDate(
                                date.toString(),
                                "yyyyMMdd",
                                "d 'de' MMMM 'de' yyyy"
                            )
                        )
                    )
                    sb.append(LS2)
                }

            sb.append(Utils.fromHtml(homily))

        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val getAllForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(normalizeEnd(paterOpus.paterForView))
                sb.append(normalizeEnd(paterOpus.singleName))
                if (tema.isNotEmpty()) {
                    sb.append(normalizeEnd(tema))
                }
                sb.append(Utils.fromHtml(homily))
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

}