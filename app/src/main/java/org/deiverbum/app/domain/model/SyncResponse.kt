package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.model.Today

/**
 * <p>Maneja las respuestas destinadas al módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class SyncResponse(
    var dataForView: SpannableStringBuilder,
    //var dataForRead: StringBuilder?,
    var allToday: List<Today>?
) {
    //constructor() : this(SpannableStringBuilder(),StringBuilder())

}