package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

sealed interface Sacramentis : LiturgiaTypus {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}

