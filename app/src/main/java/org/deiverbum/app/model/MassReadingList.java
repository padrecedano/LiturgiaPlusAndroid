package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_MASS_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class MassReadingList extends Liturgy {
    public List<Evangelio> evangelios;

    public List<Evangelio> getEvangelio() {
        return evangelios;
    }

    List<MassReading> lecturas;

    public List<MassReading> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<MassReading> lecturas) {
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
        return Utils.toH3Red(Utils.toUpper(TITLE_MASS_READING));
    }

    public String getTituloForRead() {
        return Utils.pointAtEnd(TITLE_MASS_READING);
    }

    @SuppressWarnings("unused")
    private MetaLiturgia metaliturgia;

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (MassReading b : lecturas) {
            sb.append(b.getAll());
        }
        return sb;
    }

    @SuppressWarnings("unused")
    public SpannableStringBuilder getAllEvangelioForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (MassReading b : lecturas) {
            if (b.getOrden() >= 40) {
                sb.append(b.getAll());
            }
        }
        return sb;
    }

    public SpannableStringBuilder getAllEvangelioForRead() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        for (MassReading b : lecturas) {
            if (b.getOrden() >= 40) {
                sb.append(b.getAllForRead());
            }
        }
        return sb;
    }

    public void sort() {
        Collections.sort(this.lecturas);
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            //sb.append(hoy.getForViewMisa());
            sb.append(hoy.getAllForView());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (MassReading l : lecturas) {
                sb.append(l.getAll());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    @Override
    public SpannableStringBuilder getAllForRead() {

        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            sb.append(hoy.getAllForRead());
            sb.append(getTituloForRead());
            for (MassReading l : lecturas) {
                sb.append(l.getAllForRead());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
}