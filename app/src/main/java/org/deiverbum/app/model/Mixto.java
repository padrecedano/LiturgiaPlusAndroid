package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_MIXTO;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Mixto extends BreviaryHour {
    private List<MassReading> misaLecturas;

    public Mixto() {
    }

    public Laudes getLaudes() {
        return laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public List<MassReading> getMisaLecturas() {
        return misaLecturas;
    }

    @SuppressWarnings("unused")
    public void setMisaLecturas(List<MassReading> misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public SpannableStringBuilder getTituloHora() {
        return Utils.toH1Red(TITLE_MIXTO);
    }
    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_MIXTO);
    }

    public SpannableStringBuilder getEvangeliosForView(){
        SpannableStringBuilder ssb=new SpannableStringBuilder();
        for (MassReading item : misaLecturas) {
            ssb.append(item.getAll());
        }
        return ssb;
    }

    public StringBuilder getEvangeliosForRead(){
        StringBuilder sb=new StringBuilder();
        for (MassReading item : misaLecturas) {
            sb.append(item.getAllForRead());
        }
        return sb;
    }
}
