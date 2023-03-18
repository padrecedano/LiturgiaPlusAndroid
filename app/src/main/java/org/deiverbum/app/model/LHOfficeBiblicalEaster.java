package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class LHOfficeBiblicalEaster extends Biblical implements Comparable<LHOfficeBiblicalEaster>{
    private String theme;
    @Ignore
    private Prayer prayer;

    public LHPsalm psalm;
    //public int theOrder;

    public Prayer getPrayer() {
        return prayer;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

@Override
    public String getHeader() {
        String header="";
        if (this.order == 1) {
            header="PRIMERA LECTURA";
        }
        if (this.order == 2) {
            header="SEGUNDA LECTURA";
        }
        if (this.order == 3) {
            header="TERCERA LECTURA";
        }
        if (this.order == 4) {
            header="CUARTA LECTURA";
        }
        return header;
    }

    public String getResponsorioHeaderForRead() {
        return Utils.pointAtEnd(TITLE_RESPONSORY);
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getTextoSpan());
        //sb.append(getTheme());
        sb.append(LS2);
        sb.append(prayer.getPrayer());
        sb.append(LS2);
//sb.append(psalmm);

        /*sb.append(getHeader());
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);*/
        //sb.append(Utils.toRed(getTheme()));
        sb.append(LS2);/*
        sb.append(getTextoSpan());*/
        sb.append(Utils.LS);
        //sb.append(responsorioLargo.getAll());
        return sb;
    }
    public SpannableStringBuilder getBiblical() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(Utils.formatTitle(getHeader()));
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTheme()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        return sb;
    }

    public SpannableStringBuilder getBiblicalForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTheme()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        //sb.append(responsorioLargo.getAll());
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.pointAtEnd(getHeader()));
        sb.append(getBiblicalForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.order;
    }

    public void setOrden(Integer orden) {
        this.order=orden;
    }

    @Override
    public int compareTo(LHOfficeBiblicalEaster e) {
        return this.getOrden().compareTo(e.getOrden());
    }

}