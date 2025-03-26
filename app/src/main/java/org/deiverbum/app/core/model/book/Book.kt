package org.deiverbum.app.core.model.book

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils
import java.util.Locale

/**
 * Clase de datos para preparar contenido proveniente de archivos locales en forma de libro.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2025.1
 */
@Suppress("unused")
@JsonClass(generateAdapter = true)
data class Book(
    var author: String = "",
    var title: String = "",
    var shortTitle: String = "",
    var date: String? = null,
    var intro: Intro? = null,
    var bookType: Int = 0,
    var chapters: List<Chapter>? = null,
    var agreeYes: String? = null,
    var agreeNot: String? = null
) {


    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = isNightMode
        val sb = SpannableStringBuilder()
        if (bookType == 2) {
            for (c in chapters!!) {
                sb.append(c.getAllForView(bookType))
            }
        } else {
            sb.append(Utils.toH2(title))
            sb.append(Utils.LS2)
            sb.append("Fecha efectiva: ")
            val dateString = String.format(
                Locale("es"),
                "<b>%s</b>",
                Utils.formatDate(date, "yyyyMMdd", "d '-' MMMM yyyy")
            )
            sb.append(Utils.fromHtml(dateString))
            //sb.append(_abstract.getAllForView());
            sb.append(Utils.LS2)
            if (intro != null) {
                for (ci in intro!!.content!!) {
                    sb.append(ci.getByType())
                }
                sb.append(Utils.LS2)
            }
            for (c in chapters!!) {
                sb.append(c.getAllForView(bookType))
            }
        }
        //saveHtmlFile(sb.toString());
        return sb
    }

    fun getForHtml(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (bookType == 2) {
            for (c in chapters!!) {
                sb.append(c.getAllForHtml(bookType))
            }
        } else {
            sb.append(Utils.toH2(title))
            sb.append(Utils.LS2)
            sb.append("Fecha efectiva: ")
            val dateString = String.format(
                Locale("es"),
                "<b>%s</b>",
                Utils.formatDate(date, "yyyyMMdd", "d '-' MMMM yyyy")
            )
            sb.append(dateString)
            //sb.append(_abstract.getAllForView());
            sb.append(Utils.LS2)
            if (intro != null) {
                for (ci in intro!!.content!!) {
                    sb.append(ci.getHtmlByType())
                }
                sb.append(Utils.LS2)
            }
            for (c in chapters!!) {
                sb.append(c.getAllForHtml(bookType))
            }
        }
        return sb
    }
}