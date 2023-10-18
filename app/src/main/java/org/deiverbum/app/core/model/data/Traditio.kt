package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
abstract class Traditio(typus: String) :
    LiturgiaTypus(typus) {
    //sealed interface Traditio : LiturgiaTypus {
    data class Comment(
        val comments: BibleCommentList,
        override var typus: String = "comment"
        //,override var tempore: LiturgyTime
    ) : Traditio(typus) {
        override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
            return SpannableStringBuilder(comments.getAllForView())
        }
    }

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

}