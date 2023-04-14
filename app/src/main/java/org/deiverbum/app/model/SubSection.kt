package org.deiverbum.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class SubSection {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("paragraphs")
    @Expose
    var paragraphs: List<Paragraph>? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param title      Título
     * @param paragraphs Conjunto de párrafos
     */
    constructor(title: String?, paragraphs: List<Paragraph>?) : super() {
        this.title = title
        this.paragraphs = paragraphs
    }
}