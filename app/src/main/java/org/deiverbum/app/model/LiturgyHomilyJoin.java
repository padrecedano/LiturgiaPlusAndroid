package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class LiturgyHomilyJoin {
    public Integer liturgyFK=0;
    public Integer homilyFK=0;
    public String theme="";

    @SuppressWarnings("unused")
    public LiturgyHomilyJoin() {}
    public Integer getLiturgiaFK() {
        return liturgyFK;
    }

    public void setLiturgiaFK(Integer liturgiaFK) {
        this.liturgyFK = liturgiaFK;
    }

    public Integer getHomiliaFK() {
        return homilyFK;
    }

    public void setHomiliaFK(Integer homiliaFK) {
        this.homilyFK = homiliaFK;
    }

    public String getTema() {
        return theme;
    }

    public void setTema(String tema) {
        this.theme = tema;
    }

}
