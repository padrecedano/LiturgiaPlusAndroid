package org.deiverbum.app.core.model.data.breviarium

import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
open class LHOfficiumLectionis(
    open var lectioPrior: MutableList<LHOfficiumLectioPrior>,
    open var lectioAltera: MutableList<LHOfficiumLectioAltera>,
    open var responsorium: String = "",
    open var hasTeDeum: Boolean = false
) {
    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */
    fun normalizeByTime(calendarTime: Int) {
        for (oneBiblica in lectioPrior) {
            oneBiblica.responsorium.normalizeByTime(calendarTime)
        }
        responsorium = Utils.replaceByTime(responsorium, calendarTime)
    }

    fun sort() {
        lectioPrior = lectioPrior.sortedBy { it.theOrder } as MutableList<LHOfficiumLectioPrior>
        lectioAltera = lectioAltera.sortedBy { it.theOrder } as MutableList<LHOfficiumLectioAltera>
    }
}