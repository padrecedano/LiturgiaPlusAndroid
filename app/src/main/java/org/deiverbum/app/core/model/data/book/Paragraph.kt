package org.deiverbum.app.core.model.data.book

import android.text.Spanned
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
    //@SerializedName("text")
    //@Expose
    private var text: String = ""
) {

    fun getText(): Spanned {
        return Utils.fromHtml(text)
    }

    @Suppress("unused")
    fun setText(text: String) {
        this.text = text
    }
}