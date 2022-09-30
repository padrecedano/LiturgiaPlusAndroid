package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class BibleComment {
    private PaterOpus obras;
    private String tema = "";
    private String refBiblia = "";
    private String refFuente = "";
    private String texto;
    private String padre;
    private String obra;
    private String cita = "";
    private String ref = "";
    private String fecha;
    private MassReading biblica;

    public BibleComment() {
    }

    public String getCita() {
        return this.cita;
    }

    public void setCita(String cita) {
        this.cita = cita;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getObra() {
        return this.obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getTema() {
        return this.tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getRefBiblia() {
        return this.refBiblia;
    }

    public void setRefBiblia(String refBiblia) {
        this.refBiblia = refBiblia;
    }

    public String getRefFuente() {
        return this.refFuente;
    }

    public void setRefFuente(String refFuente) {
        this.refFuente = refFuente;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public MassReading getBiblica() {
        return biblica;
    }

    public void setBiblica(MassReading biblica) {
        this.biblica = biblica;
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        sb.append(Utils.toH2Red(getPadre()));
        sb.append(Utils.LS);
        sb.append(Utils.toH3Red(getObra()));

        if (!getTema().equals("")) {
            sb.append(LS2);
            sb.append(Utils.toH4Red(getTema()));
        }

        if (!getRef().equals("")) {
            sb.append(Utils.LS2);
            sb.append(Utils.toRed(getRef()));
        }

        if (!getCita().equals("")) {
            sb.append(LS2);
            sb.append(Utils.toRed(getCita()));
        }
        if (!fecha.isEmpty() && !fecha.equals("0000-00-00") && !fecha.equals("0")) {
            sb.append(LS2);
            sb.append(Utils.toRed(fecha));
        }
        sb.append(LS2);
        sb.append(Utils.fromHtml(getTexto()));
        sb.append(LS2);
        return sb;
    }

    public StringBuilder getAllForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPadre());
        sb.append(".");
        sb.append(getObra());
        sb.append(".");

        if (!getTema().equals("")) {
            sb.append(getTema());
            sb.append(".");
        }

        if (!getRef().equals("")) {
            sb.append(getRef());
            sb.append(".");
        }

        //TODO
        /*
        Revisar esta nomenclatura
         */
        if (!getCita().equals("")) {
            sb.append(Utils.toRed(getCita()));
            sb.append(".");
        }

        sb.append(Utils.fromHtml(getTexto()));
        return sb;
    }
}
