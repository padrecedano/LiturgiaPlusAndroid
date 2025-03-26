package org.deiverbum.app.core.model.book

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils
import java.util.Locale

/**
 * Clase de datos para contenido proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 * @see [Book]
 *
 */

data class Content(
    var type: Int = 0,
    var item: String? = null,
    var text: List<String>? = null,
    var title: String? = null,
    var link: BookLink? = null
) {

    fun getByType(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        when (type) {
            10 -> {
                val txt = Utils.fromHtml(
                    String.format(
                        Locale("es"), "%s<b>%s</b> %s",
                        Constants.NBSP_4, item,
                        getTextForView()
                    )
                )
                sb.append(txt)
                sb.append(Utils.LS2)
            }

            2 -> {
                sb.append(Utils.toH3(title))
                sb.append(Utils.LS2)
                sb.append(getTextForView())
            }

            3 -> {
                sb.append(Utils.toH4(title))
                sb.append(Utils.LS2)
                sb.append(getTextForView())
            }

            4 -> {
                sb.append(Utils.toH3Red(title))
                sb.append(Utils.LS2)
            }

            5 -> {
                sb.append(Utils.toH4Red(title))
                sb.append(Utils.LS2)
            }

            11 -> {
                sb.append(getTextForView())
            }

            12 -> {
                sb.append(getTextForView())
            }

            13 -> {
                sb.append(getNumberedList())
            }

            else -> {
                sb.append(getTextForView())
            }
        }
        return sb
    }

    private fun getNumberedList(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        var i = 1
        for (s in text!!) {
            val tmp = String.format(
                Locale("es"), "\t\t%d. %s", i,
                s
            )
            sb.append(Utils.fromHtml(tmp))
            sb.append(Utils.LS2)
            i++
        }
        return sb
    }

    private fun getTextForView(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (s in text!!) {
            if (type == 11) {
                sb.append("\t\t\t\t")
                sb.append("- ")
            }
            if (type < 4) {
                sb.append("\t\t")
            }
            if (type == 20) {
                sb.append(Utils.toH3Red(title))
                sb.append(Utils.LS2)
            }
            sb.append(Utils.fromHtml(s))
            sb.append(Utils.LS2)
        }
        return sb
    }

    fun getHtmlByType(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (type == 10) {
            val txt = String.format(
                Locale("es"), "%s<b>%s</b> %s",
                Constants.NBSP_4, item,
                getTextHtml()
            )
            sb.append(txt)
            sb.append(Utils.LS2)
        } else if (type == 2) {
            sb.append(Utils.toH3(title))
            sb.append(Utils.LS2)
            sb.append(getTextForView())
        } else if (type == 3) {
            sb.append(Utils.toH4(title))
            sb.append(Utils.LS2)
            sb.append(getTextHtml())
        } else if (type == 11) {
            sb.append(getTextHtml())
        } else if (type == 12) {
            sb.append(getTextHtml())
        } else if (type == 13) {
            sb.append(getNumberedList())
        } else {
            sb.append(getTextHtml())
        }
        return sb
    }

    private fun getTextHtml(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (s in text!!) {
            if (type == 11) {
                sb.append("\t\t\t\t")
                sb.append("- ")
            }
            if (type < 4) {
                sb.append("\t\t")
            }
            if (type == 20) {
                sb.append(Utils.toH3Red(title))
                sb.append(Utils.LS2)
            }
            sb.append(s)
            sb.append(Utils.LS2)
        }
        return sb
    }
}

