package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class Preamble extends Content{

@SerializedName("paragraphs")
@Expose
private List<Paragraph> paragraphs = null;
    /**
     * No args constructor for use in serialization
     *
     */
    public Preamble() {
    }

    @SuppressWarnings("unused")
    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    @SuppressWarnings("unused")
    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public SpannableStringBuilder getAll(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        for(Paragraph p : paragraphs) {
            sb.append(p.getText());
        }
        return sb;
    }

    @SuppressWarnings("unused")
    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        for(Paragraph p : paragraphs) {
            sb.append(p.getText());
        }
        return sb;
    }
}