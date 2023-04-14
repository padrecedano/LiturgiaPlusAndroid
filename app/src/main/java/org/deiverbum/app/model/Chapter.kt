package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.Utils
import java.util.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Chapter {
    @SerializedName("id")
    @Expose
    private var id = 0

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("content")
    @Expose
    private var content: List<Content>? = null
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getContent(): List<Content>? {
        return content
    }

    fun setContent(content: List<Content>?) {
        this.content = content
    }

    fun getAllForView(bookType: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        if (bookType == 10) {
            val chapter = String.format(Locale("es"), "%s. %s", id, Utils.fromHtml(title))
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