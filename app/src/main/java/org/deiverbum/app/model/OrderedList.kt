package org.deiverbum.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class OrderedList {
    @SerializedName("item")
    @Expose
    var item: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null

    /**
     * No args constructor for use in serialization
     */
    constructor() {}

    /**
     * @param item Título del item
     * @param text Texto descriptivo
     */
    constructor(item: String?, text: String?) : super() {
        this.item = item
        this.text = text
    }
}