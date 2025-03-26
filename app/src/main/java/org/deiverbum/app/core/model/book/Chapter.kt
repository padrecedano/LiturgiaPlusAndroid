package org.deiverbum.app.core.model.book

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils
import java.util.Locale

/**
 * Clase de datos para **capítulos** proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 * @see [Book]
 *
 */
data class Chapter(
    var id: Int = 0,
    var title: String? = null,
    var content: List<Content>? = null
) {
    fun getAllForView(bookType: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (bookType == 10) {
            val chapter = String.format(Locale("es"), "%s. %s", id, Utils.fromHtml(title!!))
            sb.append(Utils.toH2RedNew(chapter))
            sb.append(Utils.LS2)
        }
        if (bookType == 21) {
            sb.append("\t\t")
            sb.append(Utils.toH4Red(id.toString()))
            sb.append(Utils.toRed(".- "))
        }
        for (c in content!!) {
            sb.append(c.getByType())
        }
        return sb
    }

    fun getAllForHtml(bookType: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (bookType == 10) {
            val chapter = String.format(Locale("es"), "%s. %s", id, title)
            sb.append(Utils.toH2RedNew(chapter))
            sb.append(Utils.LS2)
        }
        if (bookType == 21) {
            sb.append("\t\t")
            sb.append(Utils.toH4Red(id.toString()))
            sb.append(Utils.toRed(".- "))
        }
        for (c in content!!) {
            sb.append(c.getHtmlByType())
        }
        return sb
    }
}