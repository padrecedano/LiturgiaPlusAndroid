package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.model.Today
import org.deiverbum.app.util.Source

/**
 * Maneja las respuestas destinadas al módulo Today.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class TodayResponse(
    var dataModel: Today,
    var source: Source,
    var success: Boolean
) {
    constructor() : this(Today(),Source.LOCAL,success=true)
}