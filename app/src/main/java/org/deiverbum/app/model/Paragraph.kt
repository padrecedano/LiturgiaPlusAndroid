package org.deiverbum.app.model

import android.text.Spanned
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.util.Utils

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Paragraph
/**
 * @param text Texto del párrafo
 */(
    @SerializedName("text")
    @Expose
    private var text: String?
) {

    fun getText(): Spanned {
        return Utils.fromHtml(text)
    }

    fun setText(text: String?) {
        this.text = text
    }
}