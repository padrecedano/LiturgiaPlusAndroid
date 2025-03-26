package org.deiverbum.app.core.model.breviarium

import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.biblia.LectioBiblica
import org.deiverbum.app.util.Utils

data class LHLectioBrevis(
    override var pericopa: String = "",
    override var biblica: String = "",
    var responsorium: LHResponsoriumBrevis
) : LectioBiblica(pericopa, biblica) {
    constructor(
        book: BibleBook,
        pericopa: String = "",
        biblica: String = "",
        responsorium: LHResponsoriumBrevis
    ) : this(pericopa, biblica, responsorium) {
        this.book = book
    }


    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        responsorium.responsorium = Utils.replaceByTime(responsorium.responsorium, calendarTime)
    }
}