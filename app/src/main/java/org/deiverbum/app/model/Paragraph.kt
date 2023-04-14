package org.deiverbum.app.model

import android.text.Spanned
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.deiverbum.app.utils.Utils

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Paragraph {
    @SerializedName("text")
    @Expose
    private var text: String? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param text Texto del párrafo
     */
    constructor(text: String?) : super() {
        this.text = text
    }

    fun getText(): Spanned {
        return Utils.fromHtml(text)
    }

    fun setText(text: String?) {
        this.text = text
    }
}