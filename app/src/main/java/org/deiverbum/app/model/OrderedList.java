package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 26/1/22
 * @since
 */
public class OrderedList {

    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrderedList() {
    }

    /**
     *
     * @param item
     * @param text
     */
    public OrderedList(String item, String text) {
        super();
        this.item = item;
        this.text = text;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
