package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class BibleCommentList {
    var padre: String? = null
    var allComentarios: MutableList<List<BibleComment?>> = ArrayList()

    @get:PropertyName("comentarios")
    @set:PropertyName("comentarios")
    @PropertyName("comentarios")
    var comentarios: List<BibleComment>? = null
    private var today: Today? = null
    var biblica: MassReading? = null
    var type = 0


    private val titulo: SpannableStringBuilder
        get() = Utils.toH3Red(Constants.TITLE_BIBLE_COMMENTS)
    private val tituloForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_BIBLE_COMMENTS)

    fun getAllForView(todayRequest: TodayRequest): SpannableStringBuilder {
        ColorUtils.isNightMode = todayRequest.isNightMode
        val sb = SpannableStringBuilder()
        try {
            sb.append(today!!.singleForView)
            sb.append(Utils.LS2)
            sb.append(titulo)
            sb.append(Utils.LS2)
            for (subList in allComentarios) {
                if (subList.isNotEmpty()) {
                    var x = 1
                    for (item in subList) {
                        if (x++ == 1) {
                            sb.append(item?.biblica!!.getAll(type))
                            sb.append(Utils.LS2)
                            sb.append(
                                Utils.formatTitle(
                                    Constants.TITLE_BIBLE_COMMENTS))
                            sb.append(Utils.LS2)
                        }
                        sb.append(item?.allForView)
                    }
                }
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val getAllForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                sb.append(today!!.getSingleForRead())
                sb.append(tituloForRead)
                for (subList in allComentarios) {
                    if (subList.isNotEmpty()) {
                        var x = 1
                        for (item in subList) {
                            if (x++ == 1) {
                                sb.append(item?.biblica!!.getAllForRead(type))
                                sb.append(
                                    Utils.pointAtEnd(
                                        Constants.TITLE_BIBLE_COMMENTS))
                            }
                            sb.append(item?.allForRead)
                        }
                    }
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