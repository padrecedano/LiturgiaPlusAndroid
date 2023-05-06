package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder
/**
 * <p>Maneja las respuestas destinadas al módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class TodayResponse(
    var dataForView: SpannableStringBuilder,
    var dataForRead: StringBuilder?
) {
    constructor() : this(SpannableStringBuilder(),java.lang.StringBuilder())
}