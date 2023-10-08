package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

sealed interface BreviariumNew : LiturgiaTypus {
//var sanctus : LHSanctus
    //data class Laudes(val invitatorio: String, val himno: String, val gospelC: String) : BreviariumNew

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}

