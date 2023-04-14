package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_GOSPEL_CANTICLE;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class NuncDimitis extends LHPsalm {

    //private String antiphonEntity;
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
        ssb.append(Utils.replaceByTime(antiphon, timeID));
        return ssb;
    }

    public String getAntifonaForRead() {
        return antiphon;
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle(TITLE_GOSPEL_CANTICLE);
    }

    public String getHeaderForRead() {
        return Utils.pointAtEnd(TITLE_GOSPEL_CANTICLE);
    }

    public SpannableStringBuilder getAll(int idTiempo) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
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
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(".");
        sb.append(getAntifonaForRead());
        sb.append(Utils.fromHtml(getTexto()));
        sb.append(getFinSalmoForRead());
        sb.append(getAntifonaForRead());
        return sb;
    }
}