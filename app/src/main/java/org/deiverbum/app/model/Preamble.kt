package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Preamble
/**
 * No args constructor for use in serialization
 */
    : Content() {
    @SerializedName("paragraphs")
    @Expose
    var paragraphs: List<Paragraph>? = null
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (p in paragraphs!!) {
                sb.append(p.getText())
            }
            return sb
        }
    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            for (p in paragraphs!!) {
                sb.append(p.getText())
            }
            return sb
        }
}