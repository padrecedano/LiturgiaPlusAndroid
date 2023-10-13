package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

class HomilyList    {
    @Ignore
    lateinit var today: Today

    @Ignore
    var homilyes: MutableList<Homily> = ArrayList()


    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red("HOMILÍAS")
    private val tituloForRead: String
        get() = "Homilías."

    fun getAllForView(todayRequest: TodayRequest?): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(LS2)
        sb.append(titulo)
        sb.append(Utils.LS2)
        homilyes.forEach {
            sb.append(it.getAllForView())
        }
        return sb
    }

    fun getAllForView(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(LS2)
        sb.append(titulo)
        sb.append(Utils.LS2)
        homilyes.forEach {
            sb.append(it.getAllForView())
        }
        return sb
    }

    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(tituloForRead)
            homilyes.forEach {
                sb.append(it.getAllForRead)
            }
            return sb
        }
}