package org.deiverbum.app.model;


import android.text.SpannableStringBuilder;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;

import java.util.List;

public class Misa {
    private List<BiblicaMisa> misaLecturas;
    @SuppressWarnings("unused")
    private LiturgiaPalabra liturgiaPalabra;

    public Misa() {
    }

    public List<BiblicaMisa> getMisaLecturas() {
        return misaLecturas;
    }

    @SuppressWarnings("unused")
    public void setMisaLecturas(List<BiblicaMisa> misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public SpannableStringBuilder getAllEvangelioForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa b : misaLecturas) {
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
        for (BiblicaMisa b : misaLecturas) {
            if (b.getOrden() >= 40){
                sb.append(b.getAllForRead());
            }
        }
        return sb;
    }

}
