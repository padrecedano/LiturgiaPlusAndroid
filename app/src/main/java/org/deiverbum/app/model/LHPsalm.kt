package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

public class LHPsalm implements Comparable<LHPsalm> {
    public Integer psalmID = 0;
    public Integer readingID = 0;
    public String quote = "";
    @Ignore
    protected String antiphon;
    private String psalm = "";
    @Ignore
    private String theOrder;
    @Ignore
    private String theme;
    @Ignore
    private String epigraph;
    @Ignore
    private String part;

    public String getPsalm() {
        return psalm;
    }

    public void setPsalm(String psalm) {
        this.psalm = psalm;
    }

    public String getTheOrder() {
        return (theOrder != null) ? theOrder : "";
    }

    public void setTheOrder(String theOrder) {
        this.theOrder = theOrder;
    }

    public String getAntiphon() {
        return (antiphon != null) ? antiphon : "";
    }

    public void setAntiphon(String antiphon) {
        this.antiphon = antiphon;
    }

    public String getAntifonaForRead() {
        return (antiphon != null) ? Utils.normalizeEnd(antiphon) : "";
    }

    public SpannableStringBuilder getRef() {
        if (quote != null) {
            return new SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(quote)));//Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            return new SpannableStringBuilder("");
        }
    }

    public void setRef(String ref) {
        this.quote = ref;
    }

    public String getTheme() {
        return (theme != null) ? theme : "";
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEpigraph() {
        return (epigraph != null) ? epigraph : "";
    }

    public void setEpigraph(String epigraph) {
        this.epigraph = epigraph;
    }

    public String getPart() {
        return (part != null) ? part : "";
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getSalmo() {
        return psalm;
    }

    public void setSalmo(String salmo) {
        this.psalm = salmo;
    }

    /**
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    public Spanned getFinSalmo() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return Utils.fromHtml(fin);
    }

    public String getFinSalmoForRead() {
        return "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.";
    }

    @Override
    public int compareTo(LHPsalm e) {
        return this.getTheOrder().compareTo(e.getTheOrder());
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     *
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
        this.antiphon = Utils.replaceByTime(getAntiphon(), calendarTime);
    }
}