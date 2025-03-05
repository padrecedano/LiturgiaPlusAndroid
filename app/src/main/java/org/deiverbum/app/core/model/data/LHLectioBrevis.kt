package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
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



    private fun getHeaderForRead(): String {
        return "LECTURA BREVE."
    }



    override fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderForRead())
        sb.append(textoForRead)
        sb.append(responsorium.allForRead)
        return sb
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