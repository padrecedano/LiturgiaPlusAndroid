package org.deiverbum.app.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
class OrderedList
/**
 * @param item Título del item
 * @param text Texto descriptivo
 */(
    @SerializedName("item")
    @Expose var item: String?, @SerializedName("text")
    @Expose var text: String?
)