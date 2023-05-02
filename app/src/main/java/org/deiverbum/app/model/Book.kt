package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils
import java.util.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@Suppress("unused")
class Book {
    @SerializedName("author")
    @Expose
    private var author: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("intro")
    @Expose
    private var intro: Intro? = null

    @SerializedName("bookType")
    @Expose
    private var bookType = 0

    @SerializedName("chapters")
    @Expose
    private var chapters: List<Chapter>? = null

    @SerializedName("agreeYes")
    @Expose
    private var agreeYes: String? = null

    @SerializedName("agreeNot")
    @Expose
    private var agreeNot: String? = null
    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getBookType(): Int {
        return bookType
    }

    fun setBookType(bookType: Int) {
        this.bookType = bookType
    }

    fun getIntro(): Intro? {
        return intro
    }

    fun setIntro(intro: Intro?) {
        this.intro = intro
    }

    fun getChapters(): List<Chapter>? {
        return chapters
    }

    fun setChapters(chapters: List<Chapter>?) {
        this.chapters = chapters
    }

    fun getAgreeYes(): String? {
        return agreeYes
    }

    fun setAgreeYes(agreeYes: String?) {
        this.agreeYes = agreeYes
    }

    fun getAgreeNot(): String? {
        return agreeNot
    }

    fun setAgreeNot(agreeNot: String?) {
        this.agreeNot = agreeNot
    }

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
            val dateString = String.format(Locale("es"), "<b>%s</b>", Utils.getTitleDate(date))
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
            val dateString = String.format(Locale("es"), "<b>%s</b>", Utils.getTitleDate(date))
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