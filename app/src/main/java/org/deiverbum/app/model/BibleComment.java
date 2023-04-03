package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;
@SuppressWarnings("unused")
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

    public Spanned getTextoForRead() {
        return Utils.fromHtmlForRead(this.texto);
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

    private String getCitaForRead() {
        return (!getCita().equals("")) ? Utils.normalizeEnd(getCita()) : "";
    }

    private String getPadreForRead() {
        return (!getPadre().equals("")) ? Utils.normalizeEnd(getPadre()) : "";
    }

    private String getObraForRead() {
        return (!getObra().equals("")) ? Utils.normalizeEnd(getObra()) : "";
    }

    private String getTemaForRead() {
        return (!getTema().equals("")) ? Utils.normalizeEnd(getTema()) : "";
    }

    private String getRefForRead() {
        return (!getRef().equals("")) ? Utils.normalizeEnd(getRef()) : "";
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        //sb.append(today.getSingleForView());

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
        sb.append(getPadreForRead());
        sb.append(getObraForRead());
        sb.append(getTemaForRead());
        sb.append(getRefForRead());
        sb.append(getCitaForRead());
        sb.append(getTextoForRead());
        return sb;
    }


}
