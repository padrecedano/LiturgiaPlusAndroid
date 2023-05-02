package org.deiverbum.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class SubSection
/**
 * @param title      Título
 * @param paragraphs Conjunto de párrafos
 */(
    @SerializedName("title")
    @Expose var title: String?, @SerializedName("paragraphs")
    @Expose var paragraphs: List<Paragraph>?
)