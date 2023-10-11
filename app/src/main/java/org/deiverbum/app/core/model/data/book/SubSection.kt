package org.deiverbum.app.core.model.data.book

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
    //@SerializedName("title")
    //@Expose
    var title: String?,
    //@SerializedName("paragraphs")
    //@Expose
    var paragraphs: List<Paragraph>?
)