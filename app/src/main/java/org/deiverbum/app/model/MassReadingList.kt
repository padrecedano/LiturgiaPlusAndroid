package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_MASS_READING;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

import java.util.Collections;
import java.util.List;

public class MassReadingList extends Liturgy {
    public List<Evangelio> evangelios;
    public int type;
    List<MassReading> lecturas;

    @SuppressWarnings("unused")
    public List<Evangelio> getEvangelio() {
        return evangelios;
    }

    @SuppressWarnings("unused")
    public void setEvangelio(List<Evangelio> evangelios) {
        this.evangelios = evangelios;
    }

    public List<MassReading> getLecturas() {
        return lecturas;
    }

    public void setLecturas(List<MassReading> lecturas) {
        this.lecturas = lecturas;
    }

    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red(Utils.toUpper(TITLE_MASS_READING));
    }

    public String getTituloForRead() {
        return Utils.pointAtEnd(TITLE_MASS_READING);
    }

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
        for (MassReading b : evangelios) {
            if (b.getOrden() >= 40) {
                sb.append(b.getAll());
            }
        }
        return sb;
    }

    @SuppressWarnings("unused")
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

    public SpannableStringBuilder getForView(boolean nightMode) {
        ColorUtils.isNightMode = nightMode;
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        try {
            //sb.append(hoy.getForViewMisa());
            sb.append(today.getSingleForView());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (MassReading l : lecturas) {
                sb.append(l.getAll(type));
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    //@Override
    public StringBuilder getAllForRead() {

        StringBuilder sb = new StringBuilder();
        try {
            sb.append(today.getSingleForRead());
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