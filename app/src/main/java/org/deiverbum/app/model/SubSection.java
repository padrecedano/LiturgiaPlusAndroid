package org.deiverbum.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class SubSection {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("paragraphs")
    @Expose
    private List<Paragraph> paragraphs = null;

    /**
     * No args constructor for use in serialization
     */
    @SuppressWarnings("unused")
    public SubSection() {
    }

    /**
     * @param title      Título
     * @param paragraphs Conjunto de párrafos
     */
    @SuppressWarnings("unused")
    public SubSection(String title, List<Paragraph> paragraphs) {
        super();
        this.title = title;
        this.paragraphs = paragraphs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    @SuppressWarnings("unused")
    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

}