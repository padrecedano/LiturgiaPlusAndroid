package org.deiverbum.app.model;

import androidx.room.Ignore;

import java.text.MessageFormat;

public class PaterOpus {
    public Integer opusID;
    public String opusName;
    public String liturgyName;
    public String subTitle;
    public String opusDate;
    public String volume;
    public String editorial;
    public String city;
    public String opusYear;
    public Integer paterFK;
    public Integer typeFK;
    public Integer collectionFK;

    @Ignore
    private Pater pater;

    public PaterOpus() {
    }

    public String getOpusName() {
        return this.opusName;
    }

    public void setOpusName(String opusName) {
        this.opusName = opusName;
    }

    public Pater getPater() {
        return this.pater;
    }

    public void setPater(Pater pater) {
        this.pater = pater;
    }

    public String getOpusForView() {
        return liturgyName!=null && !liturgyName.equals("") ? liturgyName : MessageFormat.format("{0}, {1}",pater.getPater(), opusName);
    }
}