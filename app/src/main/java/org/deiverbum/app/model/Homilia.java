package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Homilia {
    public String padre="";
    public int id;
    public String texto="";
    public String tema="";
    public String obra="";
    public String fecha="";

    @SuppressWarnings("unused")
    public Homilia(String padre, int id, String texto, String tema, String obra, String fecha) {
        this.padre = padre;
        this.id = id;
        this.texto = texto;
        this.tema = tema;
        this.obra = obra;
        this.fecha = fecha;

    }
    @SuppressWarnings("unused")
    public Homilia() {}

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }
    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    @SuppressWarnings("unused")
    public int getId_homilia() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId_homilia(int id) {
        this.id = id;
    }

    public String getHomilia() {
        return texto;
    }

    @SuppressWarnings("unused")
    public void setHomilia(String texto) {
        this.texto = texto;
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
