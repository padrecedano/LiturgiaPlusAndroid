package org.deiverbum.app.domain.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.model.Today

/**
 * <p>Maneja las respuestas destinadas al módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class SyncResponse(
    var dataForView: SpannableStringBuilder,
    var allToday: List<Today?>,
    var status: Int =0 //1:NetWork, 2:Firebase, 0:Error
) {
    //constructor() : this(SpannableStringBuilder(),StringBuilder())

}