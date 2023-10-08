package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Representación de los himnos para exponerla en la capa de datos externa.
 */
data class LHHymnNew(
    val hymnID: Int,
    val hymn: String
) {
    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_HYMN)

    private val headerForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_HYMN)

    val textoSpan: Spanned
        get() = Utils.fromHtml(Utils.getFormato(hymn))

    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(Utils.getFormato(hymn))
            return sb
        }

    val allForRead: String
        get() = headerForRead +
                textoSpan
}