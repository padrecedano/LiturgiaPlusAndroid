package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

sealed interface Traditio : LiturgiaTypus {
    data class Comment(val comments: String) : Traditio {
        override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
            return SpannableStringBuilder(comments)
        }
    }

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

}