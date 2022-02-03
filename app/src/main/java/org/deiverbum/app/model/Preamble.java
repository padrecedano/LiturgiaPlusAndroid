package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 26/1/22
 * @since
 */
public class Preamble extends Content{
/*
    @SerializedName("paragraphs")
    @Expose
    private List<Paragraph> paragraphs = null;
    @SerializedName("singlelist")
    @Expose
    private List<String> singlelist = null;
*/
@SerializedName("paragraphs")
@Expose
private List<Paragraph> paragraphs = null;
    /**
     * No args constructor for use in serialization
     *
     */
    public Preamble() {
    }

    /**
     *

     */
    /*
    public Preamble(List<Paragraph> paragraphs, List<String> singlelist) {
        super();
        this.paragraphs = paragraphs;
        this.singlelist = singlelist;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public List<String> getSinglelist() {
        return singlelist;
    }

    public void setSinglelist(List<String> singlelist) {
        this.singlelist = singlelist;
    }
*/

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

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

    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        for(Paragraph p : paragraphs) {
            sb.append(p.getText());
        }
        return sb;
    }

    public SpannableStringBuilder getXYZ(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        for(Paragraph p : paragraphs) {
            sb.append(p.getText());
        }
        return sb;
    }

}