package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class LHPsalm implements Comparable<LHPsalm>{
    private String orden;
    protected String antifona;
    public String quote;
    private String tema;
    private String epigrafe;
    private String parte;
    private String salmo;

    public String getOrden() {
        return (orden != null ) ?  orden : "";
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getAntifona() {
        return (antifona != null ) ?  antifona : "";
    }


    public String getAntifonaForRead() {
        return (antifona != null ) ?  Utils.normalizeEnd(antifona)  : "";
    }


    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public SpannableStringBuilder getRef() {
        if (quote != null) {
            return new SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(quote)));//Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            return  new SpannableStringBuilder("");
        }
    }


    public void setRef(String ref) {
        this.quote = ref;
    }

    public String getTema() {
        return (tema != null ) ? tema : "";
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEpigrafe() {

        return (epigrafe != null ) ? epigrafe:"";
    }

    public void setEpigrafe(String epigrafe) {
        this.epigrafe = epigrafe;
    }

    public String getParte() {
        return (parte != null) ? parte: "";
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getSalmo() {
        return salmo;
    }

    public void setSalmo(String salmo) {
        this.salmo = salmo;
    }

    public Spanned getTextos() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        Spanned str = Utils.fromHtml(Utils.getFormato(epigrafe));
        ssb.append(str);
        return Utils.ssbSmallSize(ssb);
    }

    /**
     *
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
        return this.getOrden().compareTo(e.getOrden());
    }

    /**
     * Método que normaliza el contenido de las antífonas según el tiempo litúrgico del calendario
     * @param calendarTime Un entero con el Id del tiempo del calendario
     */

    public void normalizeByTime(int calendarTime) {
            this.antifona=Utils.replaceByTime(getAntifona(),calendarTime);
    }

}