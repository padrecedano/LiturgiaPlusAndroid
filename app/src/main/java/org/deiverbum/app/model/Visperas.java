package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Visperas extends BreviarioHora {
    private LecturaBreve lecturaBreve;
    private Magnificat magnificat;
    private Preces preces;
    private final PadreNuestro padreNuestro;

    public Visperas() {
        this.padreNuestro=new PadreNuestro();
    }

    @SuppressWarnings("unused")
    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public Preces getPreces() {
        return preces;
    }

    public void setPreces(Preces preces) {
        this.preces = preces;
    }

    @SuppressWarnings("unused")
    public void setMagnificat(Magnificat magnificat) {
        this.magnificat = magnificat;
    }

    @SuppressWarnings("unused")
    public Magnificat getMagnificat() {
        return magnificat;
    }

    public SpannableStringBuilder getForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {

            lecturaBreve.normalizeByTime(metaLiturgia.calendarTime);
            sb.append(metaLiturgia.getAll());
            sb.append(Utils.LS2);

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(Utils.LS2);

            sb.append(getSaludoDiosMio());
            sb.append(Utils.LS2);

            sb.append(himno.getAll());
            sb.append(Utils.LS2);

            salmodia.normalizeByTime(metaLiturgia.calendarTime);

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
            sb.append(metaLiturgia.getAllForRead());
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
        return (metaLiturgia.getIdPrevio() == 0) ? "VÍSPERAS" : "I VÍSPERAS";
    }

    private String getTituloHoraForRead() {
        return (metaLiturgia.getIdPrevio() == 0) ? "Vísperas." : "Primeras Vísperas.";
    }

}
