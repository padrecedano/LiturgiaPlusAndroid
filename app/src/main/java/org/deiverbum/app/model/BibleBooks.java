package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class BibleBooks {
    private int id;
    private String name;
    private String description;
    private String intro;

    public BibleBooks() {
    }

    public BibleBooks(int id, String name, String description) {
        this.name = name;
        this.id = id;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.fromHtml(getIntro()));
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(Utils.fromHtml(Utils.stripQuotation(getIntro())));
        return sb;
    }
}