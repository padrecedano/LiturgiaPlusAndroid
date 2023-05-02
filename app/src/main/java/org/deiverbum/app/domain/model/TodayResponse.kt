package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder

class TodayResponse(
    var dataForView: SpannableStringBuilder,
    var dataForRead: StringBuilder?
) {
    constructor() : this(SpannableStringBuilder(),java.lang.StringBuilder())
}