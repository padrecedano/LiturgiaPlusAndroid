package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class HomilyList {
    @Ignore
    public String padre = "";
    public int homilyID;
    public String homily = "";
    @Ignore
    public String theme = "";
    public Integer opusFK = 0;
    public String date = "";
    @Ignore
    public String opus = "";

    @SuppressWarnings("unused")
    public HomilyList(String padre, int id, String texto, String tema, Integer obra, String fecha) {
        this.padre = padre;
        this.homilyID = id;
        this.homily = texto;
        this.theme = tema;
        this.opusFK = obra;
        this.date = fecha;
    }

    @SuppressWarnings("unused")
    public HomilyList() {
    }

    public String getTema() {
        return theme;
    }

    public void setTema(String tema) {
        this.theme = tema;
    }

    public String getObra() {
        return opus;
    }

    public void setObra(String obra) {
        this.opus = obra;
    }

    public Integer getObraFK() {
        return opusFK;
    }

    public void setObraFK(Integer obra) {
        this.opusFK = obra;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    @SuppressWarnings("unused")
    public int getHomiliaId() {
        return homilyID;
    }

    @SuppressWarnings("unused")
    public void setHomiliaId(int id) {
        this.homilyID = id;
    }

    public String getHomilia() {
        return homily;
    }

    @SuppressWarnings("unused")
    public void setHomilia(String texto) {
        this.homily = texto;
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.toH3Red(getPadre()));
        sb.append(LS2);
        sb.append(Utils.toH4Red(getObra()));
        sb.append(LS2);
        if (!theme.isEmpty()) {
            sb.append(Utils.toRed(theme));
            sb.append(LS2);
        }
        if (!date.isEmpty() && !date.equals("0000-00-00")) {
            sb.append(Utils.toRed(date));
            sb.append(LS2);
        }
        sb.append(Utils.fromHtml(getHomilia()));
        sb.append(LS2);
        return sb;
    }

    public StringBuilder getAllForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.normalizeEnd(getPadre()));
        sb.append(Utils.normalizeEnd(getObra()));
        if (!theme.isEmpty()) {
            sb.append(Utils.normalizeEnd(theme));
        }
        if (!date.isEmpty() && !date.equals("0000-00-00")) {
            sb.append(Utils.normalizeEnd(date));
        }
        sb.append(Utils.fromHtmlForRead(getHomilia()));
        return sb;
    }

    @SuppressWarnings("unused")
    public SpannableStringBuilder getTemaForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (!theme.isEmpty()) {
            sb.append(Utils.toH4Red(theme));
            sb.append(LS2);
        }
        return sb;
    }
}