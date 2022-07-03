package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class MisaLecturas extends Liturgia{
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


    public MetaLiturgia getMetaLiturgia() {
        return metaliturgia;
    }

    public void setMetaLiturgia(MetaLiturgia metaliturgia) {
        this.metaliturgia = metaliturgia;
    }


    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("LECTURAS DE LA MISA");
    }

    private String getTituloForRead() {
        return "Lecturas de la Misa.";
    }

    @SuppressWarnings("unused")
    private MetaLiturgia metaliturgia;
    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa b : lecturas) {
            sb.append(b.getAll());
        }
        return sb;
    }

    public SpannableStringBuilder getAllEvangelioForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa b : lecturas) {
            if (b.getOrden() >= 40) {
                sb.append(b.getAll());
            }
        }
        return sb;
    }

    public SpannableStringBuilder getAllEvangelioForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (BiblicaMisa b : lecturas) {
            if (b.getOrden() >= 40) {
                sb.append(b.getAllForRead());
            }
        }
        return sb;
    }

    public void sort(){
        Collections.sort(this.lecturas);
    }


    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            sb.append(hoy.getForViewMisa());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (BiblicaMisa l : lecturas) {
                sb.append(l.getAll());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public SpannableStringBuilder getAllForRead() {

        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
        sb.append(hoy.getAllForRead());
        sb.append(getTituloForRead());
        for (BiblicaMisa l : lecturas) {
            sb.append(l.getAllForRead());
        }
    } catch (Exception e){
        sb.append(e.getMessage());
    }
        return sb;
}

}
