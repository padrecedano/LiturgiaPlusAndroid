package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

interface Alteri : LiturgiaTypus {
    data class Sancti(val sanctus: Sanctus) : Alteri {
        override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
            return SpannableStringBuilder(sanctus.forView)
        }
    }

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

}