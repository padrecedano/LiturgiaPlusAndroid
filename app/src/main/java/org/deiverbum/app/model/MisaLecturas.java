package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import java.util.List;

public class MisaLecturas {
    public List<Evangelio> evangelios;

    public List<Evangelio> getEvangelio() {
        return evangelios;
    }
    List<BiblicaMisa> lecturas;

    public List<BiblicaMisa> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<BiblicaMisa> lecturas) {
        this.lecturas = lecturas;
    }

    public void setEvangelio(List<Evangelio> evangelios) {
        this.evangelios = evangelios;
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa b : lecturas) {
            sb.append(b.getAll());
        }
        return sb;
    }



        }
