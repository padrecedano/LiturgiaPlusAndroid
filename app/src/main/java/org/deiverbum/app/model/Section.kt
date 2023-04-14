package org.deiverbum.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class Section {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("orderedlist")
    @Expose
    var orderedlist: List<OrderedList>? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("subsections")
    @Expose
    var subsections: List<SubSection>? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param subsections Subsección
     * @param orderedlist Lista
     * @param id          Id
     * @param title       Título
     */
    constructor(
        id: Int?, orderedlist: List<OrderedList>?, title: String?,
        subsections: List<SubSection>?
    ) : super() {
        this.id = id
        this.orderedlist = orderedlist
        this.title = title
        this.subsections = subsections
    }
}