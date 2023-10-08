package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

sealed interface LiturgiaTypus {
    fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

    fun forRead(): StringBuilder {
        return StringBuilder("")
    }

}