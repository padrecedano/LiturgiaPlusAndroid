package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.utils.Utils

class HomilyListDao   {
    @Ignore
    var padre = ""
    var homilyID = 0
    var homily = ""

    @Ignore
    var tema = ""
    var opusFK = 0
    var date = ""
    @Ignore
    var homilylies: List<Homily?>? = null

    @Ignore
    var obra = ""
/*
    constructor(padre: String, id: Int, texto: String, tema: String, obra: Int, fecha: String) {
        this.padre = padre
        homilyID = id
        homily = texto
        this.tema = tema
        opusFK = obra
        date = fecha
    }
*/
//    constructor() {}

     fun getAllForView(todayRequest: TodayRequest?): SpannableStringBuilder {


            val sb = SpannableStringBuilder()
            homilylies?.forEach {
                sb.append(it?.getAllForView(todayRequest))
            }
/*
            sb.append(Utils.toH3Red(padre))
            sb.append(Utils.LS2)
            sb.append(Utils.toH4Red(obra))
            sb.append(Utils.LS2)
            if (tema.isNotEmpty()) {
                sb.append(Utils.toRed(tema))
                sb.append(Utils.LS2)
            }
            if (date.isNotEmpty() && date != "0000-00-00") {
                sb.append(Utils.toRed(date))
                sb.append(Utils.LS2)
            }
            sb.append(Utils.fromHtml(homily))
            sb.append(Utils.LS2)*/
            return sb
        }
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(Utils.normalizeEnd(padre))
            sb.append(Utils.normalizeEnd(obra))
            if (tema.isNotEmpty()) {
                sb.append(Utils.normalizeEnd(tema))
            }
            if (date.isNotEmpty() && date != "0000-00-00") {
                sb.append(Utils.normalizeEnd(date))
            }
            sb.append(Utils.fromHtmlForRead(homily))
            return sb
        }
    @Suppress("unused")
    val temaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            if (tema.isNotEmpty()) {
                sb.append(Utils.toH4Red(tema))
                sb.append(Utils.LS2)
            }
            return sb
        }
}