package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Visperas extends BreviaryHour {
    private BiblicalShort lecturaBreve;
    private LHGospelCanticle_ magnificat;
    private LHIntercession preces;
    private final PadreNuestro padreNuestro;

    public Visperas() {
        this.padreNuestro = new PadreNuestro();
    }

    @SuppressWarnings("unused")
    public BiblicalShort getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public LHIntercession getPreces() {
        return preces;
    }

    public void setPreces(LHIntercession preces) {
        this.preces = preces;
    }

    @SuppressWarnings("unused")
    public void setMagnificat(LHGospelCanticle_ magnificat) {
        this.magnificat = magnificat;
    }

    @SuppressWarnings("unused")
    public LHGospelCanticle_ getMagnificat() {
        return magnificat;
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {

            lecturaBreve.normalizeByTime(hoy.getCalendarTime());
            sb.append(hoy.getAllForView());
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(Utils.LS2);

            sb.append(getSaludoDiosMio());
            sb.append(Utils.LS2);

            sb.append(himno.getAll());
            sb.append(Utils.LS2);

            salmodia.normalizeByTime(hoy.getCalendarTime());

            sb.append(salmodia.getAll());
            sb.append(Utils.LS2);

            sb.append(lecturaBreve.getAll());
            sb.append(Utils.LS2);

            sb.append(magnificat.getAll());
            sb.append(Utils.LS2);

            sb.append(preces.getAll());
            sb.append(LS2);

            sb.append(padreNuestro.getAll());
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHorasMayores());

        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;
    }

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(hoy.getAllForRead());
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoDiosMioForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(lecturaBreve.getAllForRead());
            sb.append(magnificat.getAllForRead());
            sb.append(preces.getAllForRead());
            sb.append(padreNuestro.getAllForRead());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;
    }

    private String getTituloHora() {
        return (hoy.getLiturgiaPrevio() == null) ? "VÍSPERAS" : "I VÍSPERAS";
    }

    private String getTituloHoraForRead() {
        return (hoy.getLiturgiaPrevio() == null) ? "Vísperas." : "Primeras Vísperas.";
    }

}
