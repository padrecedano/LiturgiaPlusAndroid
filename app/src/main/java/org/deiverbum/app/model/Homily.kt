package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils

class Homily {
    var homilyID: Int? = null
    var opusFK: Int? = null
    var date: String? = null
    var book: Int? = null
    var chapter: Int? = null
    var number: Int? = null
    var paragraph: Int? = null
    var collectionFK: Int? = null
    var colNumber: Int? = null
    var colParagraph: Int? = null

    @Ignore
    var padre: String? = null
    var homily: String? = null

    @Ignore
    var homilias: List<HomilyList?>? = null

    @Ignore
    private var today: Today? = null
    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red("HOMILÍAS")
    private val tituloForRead: String
        get() = "Homilías."

    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = isNightMode
        val sb = SpannableStringBuilder()
        try {
            //sb.append(LS2);
            sb.append(today!!.singleForView)
            sb.append(Utils.LS2)
            sb.append(titulo)
            sb.append(Utils.LS2)
            for (h in homilias!!) {
                sb.append(h?.allForView)
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(today!!.getSingleForRead())
                sb.append(tituloForRead)
                for (s in homilias!!) {
                    sb.append(s?.allForRead)
                }
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    fun setHoy(today: Today?) {
        this.today = today
    }
}