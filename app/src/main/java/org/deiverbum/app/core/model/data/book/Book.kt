package org.deiverbum.app.core.model.data.book

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.ColorUtils
import org.deiverbum.app.util.Utils
import java.util.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@Suppress("unused")
@JsonClass(generateAdapter = true)
class Book {
    //@SerializedName("author")
    //@Expose
    var author: String = ""

    //@SerializedName("title")
    //@Expose
    var title: String = ""

    //@SerializedName("date")
    //@Expose
    var date: String? = null

    //@SerializedName("intro")
    //@Expose
    var intro: Intro? = null

    //@SerializedName("bookType")
    //@Expose
    var bookType = 0

    //@SerializedName("chapters")
    //@Expose
    var chapters: List<Chapter>? = null

    //@SerializedName("agreeYes")
    // @Expose
    var agreeYes: String? = null

    //@SerializedName("agreeNot")
    //@Expose
    var agreeNot: String? = null

    /*
    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String) {
        this.author = author
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
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
*/
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