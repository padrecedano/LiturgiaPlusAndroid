package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_SHORT_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class BiblicalShort extends Biblical {
    //public String forma;
    private LHResponsoryShort responsorio;

    public BiblicalShort() {
    }
public SpannableStringBuilder getHeaderLectura() {
        //String s=String.format(new Locale("es"),"%s    %s",TITLE_SHORT_READING,getRefBreve());
        SpannableStringBuilder ssb=new SpannableStringBuilder(Utils.formatTitle(TITLE_SHORT_READING));
        ssb.append("    ");
        ssb.append(Utils.toRed(getRefBreve()));
        return ssb;
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

}