package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import static org.deiverbum.app.utils.Constants.TITLE_GOSPEL_CANTICLE;
import static org.deiverbum.app.utils.Constants.TITLE_SHORT_READING;
import static org.deiverbum.app.utils.Utils.LS2;

public class BiblicalShort extends Biblical {
    public String forma;
    private LHResponsoryShort responsorio;

    public BiblicalShort() {
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public SpannableStringBuilder getHeaderLectura() {
        String s=String.format("%s    %s",TITLE_SHORT_READING,getRefBreve());
        return Utils.formatTitle(s);
    }

    public String getHeaderForRead() {
        return "LECTURA BREVE.";
    }

    public void setResponsorio(LHResponsoryShort responsorio) {
        this.responsorio=responsorio;
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderLectura());
        sb.append(LS2);
        sb.append(getTexto());
        sb.append(LS2);
        sb.append(responsorio.getAll());
        //sb.append(getResponsorio().getAll());
        return sb;
    }


    public SpannableStringBuilder getAllWithHourCheck(int hourId) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderLectura());
        sb.append(LS2);
        sb.append(getTexto());
        sb.append(LS2);
        sb.append(responsorio.getAll(hourId));
        //sb.append(getResponsorio().getAll());
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(getTexto());
        sb.append(responsorio.getAllForRead());
        //sb.append(getResponsorioForRead());
        return sb;
    }
    public void normalizeByTime(int timeID){
        //this.responsorio=Utils.replaceByTime(this.responsorio,timeID);
    }


}