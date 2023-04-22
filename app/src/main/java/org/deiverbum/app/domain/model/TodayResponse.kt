package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder

class TodayResponse(
    var dataForView: SpannableStringBuilder,
    val dataForRead: StringBuilder?
)