package org.deiverbum.app.model;

import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Paragraph {
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * No args constructor for use in serialization
     */
    @SuppressWarnings("unused")
    public Paragraph() {
    }

    /**
     * @param text Texto del párrafo
     */
    @SuppressWarnings("unused")
    public Paragraph(String text) {
        super();
        this.text = text;
    }

    public Spanned getText() {
        return Utils.fromHtml(text);
    }

    public void setText(String text) {
        this.text = text;
    }
}