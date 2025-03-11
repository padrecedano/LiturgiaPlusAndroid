package org.deiverbum.app.core.model.biblia

import org.deiverbum.app.util.Utils

/**
 *
 * Nueva clase para manejar libros de la Biblia en el contexto litúrgico.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class BibleBook(
    var bookID: Int = 0,
    var bookType: Int = 0,
    var shortName: String = "",
    var longName: String = "",
    var liturgyName: String = "",
    var orderName: String = ""
) {

    fun getForRead(): String {
        return Utils.normalizeEnd(liturgyName)
    }
}