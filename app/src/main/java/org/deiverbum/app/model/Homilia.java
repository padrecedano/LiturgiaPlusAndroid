package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class Homilia {
    @Ignore
    public String padre="";
    public int homiliaId;
    public String homilia="";
    @Ignore
    public String tema="";
    public Integer obraFK=0;
    public String fecha="";
    @Ignore
    public String obra="";

    @SuppressWarnings("unused")
    public Homilia(String padre, int id, String texto, String tema, Integer obra, String fecha) {
        this.padre = padre;
        this.homiliaId = id;
        this.homilia = texto;
        this.tema = tema;
        this.obraFK = obra;
        this.fecha = fecha;

    }
    @SuppressWarnings("unused")
    public Homilia() {}

    public String getTema() {
        return tema;
    }
    public String getObra() {
        return obra;
    }
    public void setObra(String obra) {
        this.obra = obra;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getObraFK() {
        return obraFK;
    }

    public void setObraFK(Integer obra) {
        this.obraFK = obra;
    }
    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    @SuppressWarnings("unused")
    public int getHomiliaId() {
        return homiliaId;
    }

    @SuppressWarnings("unused")
    public void setHomiliaId(int id) {
        this.homiliaId = id;
    }

    public String getHomilia() {
        return homilia;
    }

    @SuppressWarnings("unused")
    public void setHomilia(String texto) {
        this.homilia = texto;
    }

    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toH3Red(getPadre()));
        sb.append(LS2);
        sb.append(Utils.toH4Red(getObra()));
        sb.append(LS2);
        if(!tema.isEmpty()) {
            sb.append(Utils.toRed(tema));
            sb.append(LS2);
        }
        if(!fecha.isEmpty() && !fecha.equals("0000-00-00")) {
            sb.append(Utils.toRed(fecha));
            sb.append(LS2);
        }
        sb.append(Utils.fromHtml(getHomilia()));
        sb.append(LS2);
        return sb;
    }

    public StringBuilder getAllForRead(){
        StringBuilder sb=new StringBuilder();
        sb.append(Utils.normalizeEnd(getPadre()));
        sb.append(Utils.normalizeEnd(getObra()));
        if(!tema.isEmpty()) {
            sb.append(Utils.normalizeEnd(tema));
        }
        if(!fecha.isEmpty() && !fecha.equals("0000-00-00")) {
            sb.append(Utils.normalizeEnd(fecha));
        }
        sb.append(Utils.fromHtml(getHomilia()));
        return sb;
    }
    @SuppressWarnings("unused")
    public SpannableStringBuilder getTemaForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        if(!tema.isEmpty()) {
            sb.append(Utils.toH4Red(tema));
            sb.append(LS2);
        }
        return sb;
    }
}
