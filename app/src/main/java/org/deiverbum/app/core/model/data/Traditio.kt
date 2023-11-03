package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
abstract class Traditio(typus: String) :
    LiturgiaTypus(typus) {
    //sealed interface Traditio : LiturgiaTypus {

    override fun forView(calendarTime: Int): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }

}