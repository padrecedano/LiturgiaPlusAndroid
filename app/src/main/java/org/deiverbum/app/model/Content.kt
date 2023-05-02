package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import java.util.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
open class Content {
    @SerializedName("type")
    @Expose
    private var type = 0

    @SerializedName("item")
    @Expose
    private var item: String? = null

    @SerializedName("text")
    @Expose
    private var text: List<String>? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null
    fun getType(): Int {
        return type
    }

    fun setType(type: Int) {
        this.type = type
    }

    @Suppress("unused")
    fun getItem(): String? {
        return item
    }

    @Suppress("unused")
    fun setItem(item: String?) {
        this.item = item
    }

    fun getText(): List<String>? {
        return text
    }

    fun setText(text: List<String>?) {
        this.text = text
    }

    private fun getTitle(): String? {
        return title
    }

    @Suppress("unused")
    fun setTitle(title: String?) {
        this.title = title
    }

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
                sb.append(Utils.toH3Red(getTitle()))
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
                sb.append(Utils.toH3Red(getTitle()))
                sb.append(Utils.LS2)
            }
            sb.append(s)
            sb.append(Utils.LS2)
        }
        return sb
    }
}