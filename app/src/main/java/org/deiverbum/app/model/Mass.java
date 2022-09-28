package org.deiverbum.app.model;


import android.text.SpannableStringBuilder;

import java.util.List;

public class Mass {
    private List<MassReading> misaLecturas;

    public Mass() {
    }

    public List<MassReading> getMisaLecturas() {
        return misaLecturas;
    }

    @SuppressWarnings("unused")
    public void setMisaLecturas(List<MassReading> misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public SpannableStringBuilder getAllEvangelioForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (MassReading b : misaLecturas) {
            if (b.getOrden() >= 40){
                sb.append(b.getAll());
            }
        }
        return sb;
    }
    public SpannableStringBuilder getAllEvangelio() {
        //return misaLecturas.getAllEvangelioForView();
        SpannableStringBuilder sb = new SpannableStringBuilder();
        /*
        for (Evangelio e : misaLecturas.getEvangelio()) {
            sb.append(e.getAll());
        }*/
        return sb;
    }

    public SpannableStringBuilder getAllEvangelioForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        /*for (Evangelio e : misaLecturas.getEvangelio()) {
            sb.append(e.getEvangelioForRead());
        }*/
        for (MassReading b : misaLecturas) {
            if (b.getOrden() >= 40){
                sb.append(b.getAllForRead());
            }
        }
        return sb;
    }

}
