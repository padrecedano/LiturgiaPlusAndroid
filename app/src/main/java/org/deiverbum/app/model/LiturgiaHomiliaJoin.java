package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class LiturgiaHomiliaJoin {
    public Integer liturgiaFK=0;
    public Integer homiliaFK=0;
    public String tema="";

    @SuppressWarnings("unused")
    public LiturgiaHomiliaJoin() {}
    public Integer getLiturgiaFK() {
        return liturgiaFK;
    }

    public void setLiturgiaFK(Integer liturgiaFK) {
        this.liturgiaFK = liturgiaFK;
    }

    public Integer getHomiliaFK() {
        return homiliaFK;
    }

    public void setHomiliaFK(Integer homiliaFK) {
        this.homiliaFK = homiliaFK;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

}
