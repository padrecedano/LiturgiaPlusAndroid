package org.deiverbum.app.core.model.data.book

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.LS
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils
import java.util.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
open class SacramentumContent {
    //@SerializedName("type")
    //@Expose
    private var type = ""

    //@SerializedName("item")
    //@Expose
    private var item: String? = null
    private var intro: String? = null
    private var response: String? = null

    //@SerializedName("text")
    //@Expose
    private var text: List<String>? = null
    private var txt: List<String>? = null
    private var dupla: List<Dupla>? = null

    //@SerializedName("title")
    //@Expose
    private var title: String? = null
    fun getType(): String {
        return type
    }

    fun setType(type: String) {
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
            "10" -> {
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

            "2" -> {
                sb.append(Utils.toH3(title))
                sb.append(Utils.LS2)
                sb.append(getTextForView())
            }

            "3" -> {
                sb.append(Utils.toH4(title))
                sb.append(Utils.LS2)
                sb.append(getTextForView())
            }

            "4" -> {
                sb.append(Utils.toH3Red(title))
                sb.append(Utils.LS2)
            }

            "5" -> {
                sb.append(Utils.toH4Red(title))
                sb.append(Utils.LS2)
            }

            "11" -> {
                sb.append(getTextForView())
            }

            "12" -> {
                sb.append(getTextForView())
            }

            "13" -> {
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

    fun getAllForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        if (type == "rubrica") {
            var i = 0

            for (s in txt!!) {
                //var tmp=Utils.to
                ssb.append(Utils.fromHtml(Utils.toRedFont(s)))
                if (i++ != txt!!.size - 1) {
                    //ssb.append(LS2)

                    // Last iteration
                }
                ssb.append(LS2)
            }
        }
        if (type == "prayerWithResponse") {
            ssb.append(intro)
            ssb.append(LS2)
            for (s in txt!!) {
                ssb.append("— ")
                ssb.append(s)
                ssb.append(LS2)
                ssb.append(Utils.toRed("℟ "))
                ssb.append(response)
                //ssb.append(LS2)
            }
        }
        if (type == "prayer") {

            for (s in txt!!) {
                ssb.append(Utils.fromHtml(s))
                ssb.append(LS2)
            }
            //ssb.append(LS2)
            ssb.append(Utils.toRed("℟ "))
            ssb.append(response)
            //ssb.append(LS2)
        }
        if (type == "body") {
            for (s in txt!!) {
                ssb.append(Utils.fromHtml(s))
                ssb.append(LS)
            }
            //ssb.append(LS2)
        }
        if (type == "bodyWithRubrica") {
            var i = 0

            for (s in dupla!!) {
                ssb.append(Utils.fromHtml(s.txt!!))
                ssb.append(LS)
                ssb.append(Utils.toRed(s.response))
                if (i++ != dupla!!.size - 1) {
                    ssb.append(LS2)

                    // Last iteration
                }
                //ssb.append(LS2)

            }
            //ssb.append(LS2)
        }
        if (type == "intro") {
            for (s in txt!!) {
                ssb.append(Utils.fromHtml(s))
                //ssb.append(LS)
            }
            ssb.append(LS2)
        }
        if (type == "dialog") {
            var i = 0
            for (s in txt!!) {
                ssb.append(Utils.fromHtml(s))
                ssb.append(LS2)
                ssb.append(Utils.fromHtml(response!!))
                if (i++ != txt!!.size - 1) {
                    ssb.append(LS2)

                    // Last iteration
                } else {
                    //ssb.append(LS2)
                }

            }
        }
        return ssb

    }

    private fun getTextForView(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (s in text!!) {
            if (type == "11") {
                sb.append("\t\t\t\t")
                sb.append("- ")
            }
            /*if (type < 4) {
                sb.append("\t\t")
            }*/
            if (type == "20") {
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
        if (type == "10") {
            val txt = String.format(
                Locale("es"), "%s<b>%s</b> %s",
                Constants.NBSP_4, item,
                getTextHtml()
            )
            sb.append(txt)
            sb.append(Utils.LS2)
        } else if (type == "2") {
            sb.append(Utils.toH3(title))
            sb.append(Utils.LS2)
            sb.append(getTextForView())
        } else if (type == "3") {
            sb.append(Utils.toH4(title))
            sb.append(Utils.LS2)
            sb.append(getTextHtml())
        } else if (type == "11") {
            sb.append(getTextHtml())
        } else if (type == "12") {
            sb.append(getTextHtml())
        } else if (type == "13") {
            sb.append(getNumberedList())
        } else {
            sb.append(getTextHtml())
        }
        return sb
    }

    private fun getTextHtml(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        for (s in text!!) {
            if (type == "11") {
                sb.append("\t\t\t\t")
                sb.append("- ")
            }
            /*if (type < 4) {
                sb.append("\t\t")
            }*/
            if (type == "20") {
                sb.append(Utils.toH3Red(getTitle()))
                sb.append(Utils.LS2)
            }
            sb.append(s)
            sb.append(Utils.LS2)
        }
        return sb
    }
}