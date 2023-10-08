package org.deiverbum.app.core.model.data

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
    var bookID: Int? = null
    var bookType: Int? = null
    var shortName: String? = null
    var longName: String? = null
    var liturgyName: String? = null
    var orderName: String? = null

    fun getForRead(): String {
        return Utils.normalizeEnd(liturgyName!!)
    }
}