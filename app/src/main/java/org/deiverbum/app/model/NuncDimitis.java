package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class NuncDimitis extends Salmo{

    //private String antifonaEntity;
    private String texto;

    public NuncDimitis() {
    }

    public String getTexto() {
        return Utils.getFormato(texto);
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public SpannableStringBuilder getAntifonaSpan(int timeID) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        ssb.append(Utils.toRed("Ant. "));
        ssb.append(Utils.replaceByTime(antifona, timeID));
        return ssb;
    }

    public String getAntifonaForRead() {
        return antifona;
    }



    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("CÁNTICO EVANGÉLICO");
    }

    public String getHeaderForRead() {
        return "CÁNTICO EVANGÉLICO";
    }

    public SpannableStringBuilder getAll(int idTiempo) {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(Utils.LS2);
        sb.append(getAntifonaSpan(idTiempo));
        sb.append(Utils.LS2);
        sb.append(Utils.fromHtml(getTexto()));
        sb.append(Utils.LS2);
        sb.append(getFinSalmo());
        sb.append(Utils.LS2);
        sb.append(getAntifonaSpan(idTiempo));
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(".");
        sb.append(getAntifonaForRead());
        sb.append(Utils.fromHtml(getTexto()));
        sb.append(getFinSalmoForRead());
        sb.append(getAntifonaForRead());
        return sb;
    }
}

