package org.deiverbum.app.model;


import android.text.SpannableStringBuilder;

import org.deiverbum.app.data.entity.SalmodiaWithSalmos;

public class Misa {
    private MisaLecturas misaLecturas;
    @SuppressWarnings("unused")
    private LiturgiaPalabra liturgiaPalabra;

    public Misa() {
    }

    public MisaLecturas getMisaLecturas() {
        return misaLecturas;
    }

    @SuppressWarnings("unused")
    public void setMisaLecturas(MisaLecturas misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public SpannableStringBuilder getAllEvangelio() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (Evangelio e : misaLecturas.getEvangelio()) {
            sb.append(e.getAll());
        }
        return sb;
    }

    public SpannableStringBuilder getAllEvangelioForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for (Evangelio e : misaLecturas.getEvangelio()) {
            sb.append(e.getEvangelioForRead());
        }
        return sb;
    }

}
