package org.deiverbum.app.model

import org.deiverbum.app.util.Utils

/**
 *
 * Nueva clase para manejar libros de la Biblia en el contexto litúrgico.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
class BibleBook {
    var id: Int? = null
    var name: String? = null
    var liturgyName: String? = null
    var shortName: String? = null

    fun getForRead(): String {
        return Utils.normalizeEnd(liturgyName)
    }
}