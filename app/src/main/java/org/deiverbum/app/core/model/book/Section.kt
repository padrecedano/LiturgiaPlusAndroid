package org.deiverbum.app.core.model.book

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Section
/**
 * @param subsections Subsección
 * @param orderedlist Lista
 * @param id          Id
 * @param title       Título
 */(
    //@SerializedName("id")
    //@Expose
    var id: Int?,
    //@SerializedName("orderedlist")
    //@Expose
    var orderedlist: List<OrderedList>?,
    //@SerializedName("title")
    //@Expose
    var title: String?,
    //@SerializedName("subsections")
    //@Expose
    var subsections: List<SubSection>?
)