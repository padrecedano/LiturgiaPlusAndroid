package org.deiverbum.app.core.model.breviarium

import androidx.room.ColumnInfo
import org.deiverbum.app.util.Utils

open class LHResponsoriumBrevis(
    @ColumnInfo(name = "text")
    open var responsorium: String = "",
    @ColumnInfo(name = "type")
    open var typus: Int = 0
) {
    var responsoryID: Int = 0

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        responsorium = Utils.replaceByTime(responsorium, calendarTime)
    }
}
