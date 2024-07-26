package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.util.Constants
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

    private fun getHeaderLectura(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(
            Utils.formatTitle(
                Constants.TITLE_SHORT_READING
            )
        )
        ssb.append("    ")
        ssb.append(Utils.toRed(getRefBreve()))
        return ssb
    }

    private fun getHeaderForRead(): String {
        return "LECTURA BREVE."
    }

    @Composable
    fun getComposable(hourIndex: Int, userData: UserDataDynamic): AnnotatedString {
        ContentTitle(
            text = Constants.TITLE_SHORT_READING.uppercase(),
            level = 2,
            userData = userData
        ).getComposable()
        return buildAnnotatedString {
            append(textoSpan)
        }
    }

    fun getAllWithHourCheck(hourId: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(getHeaderLectura())
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        sb.append(Utils.LS2)
        sb.append(responsorium.getAll(hourId))
        return sb
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