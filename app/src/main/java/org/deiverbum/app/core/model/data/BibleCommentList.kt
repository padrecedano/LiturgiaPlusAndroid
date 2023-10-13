package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import com.google.firebase.firestore.PropertyName
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.ERR_NO_COMMENT
import org.deiverbum.app.util.Utils

/**
 * Esta clase maneja una lista de comentarios bíblicos.
 */
class BibleCommentList {
    var padre: String? = null
    var commentarii: MutableList<List<BibleComment?>> = ArrayList()

    @get:PropertyName("comentarios")
    @set:PropertyName("comentarios")
    @PropertyName("comentarios")
    var comentarios: List<BibleComment>? = null
    private var today: Today? = null
    var biblica: MissaeLectionum? = null
    var type = 0

    fun getAllForView(): SpannableStringBuilder {
        //ColorUtils.isNightMode = todayRequest.isNightMode
        val sb = SpannableStringBuilder()
        try {
            for (subList in commentarii) {
                if (subList.isNotEmpty()) {
                    var x = 1
                    for (item in subList) {
                        if (item!!.biblica!!.theOrder > 39) {
                            if (x++ == 1) {
                                sb.append(item.biblica!!.getAll(type))
                                sb.append(Utils.LS2)
                                sb.append(
                                    Utils.toH1Red(
                                        Constants.TITLE_BIBLE_COMMENTS
                                    )
                                )
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
        if (sb.isEmpty()) {
            sb.append(ERR_NO_COMMENT)
        }
        return sb
    }

    val getAllForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                for (subList in commentarii) {
                    if (subList.isNotEmpty()) {
                        var x = 1
                        for (item in subList) {
                            if (item!!.biblica!!.theOrder > 39) {
                                if (x++ == 1) {
                                    sb.append(item.biblica!!.getAll(type))
                                    sb.append(Utils.LS2)
                                    sb.append(
                                        Utils.toH1Red(
                                            Constants.TITLE_BIBLE_COMMENTS
                                        )
                                    )
                                    sb.append(Utils.LS2)
                                }
                                sb.append(item.allForRead)

                            }
                        }

                    }
                }
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}