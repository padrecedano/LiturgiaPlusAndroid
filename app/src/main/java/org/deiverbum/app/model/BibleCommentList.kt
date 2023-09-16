package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.ERR_NO_COMMENT
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


    fun getAllForView(todayRequest: TodayRequest): SpannableStringBuilder {
        ColorUtils.isNightMode = todayRequest.isNightMode
        val sb = SpannableStringBuilder()
        try {

            //sb.append(Utils.LS2)

            for (subList in allComentarios) {
                if (subList.isNotEmpty()) {
                    var x = 1
                    for (item in subList) {
                        if (item!!.biblica!!.order!! > 39) {
                        if (x++ == 1) {
                            sb.append(item.biblica!!.getAll(type))
                            sb.append(Utils.LS2)
                            sb.append(
                                Utils.toH1Red(
                                    Constants.TITLE_BIBLE_COMMENTS))
                            sb.append(Utils.LS2)
                        }
                        sb.append(item.allForView)

                    }
                    }

                }
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        if(sb.isEmpty()){
            sb.append(ERR_NO_COMMENT)
        }
        return sb
    }

    val getAllForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                //sb.append(today!!.getSingleForRead())
                //sb.append(tituloForRead)
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