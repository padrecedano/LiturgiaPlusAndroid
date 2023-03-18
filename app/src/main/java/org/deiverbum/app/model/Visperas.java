package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_I_VISPERAS;
import static org.deiverbum.app.utils.Constants.TITLE_I_VISPERAS_READ;
import static org.deiverbum.app.utils.Constants.TITLE_VISPERAS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Visperas extends BreviaryHour {
    private BiblicalShort lecturaBreve;
    private LHGospelCanticle magnificat;
    private LHIntercession preces;

    public Visperas() {
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
    public void setMagnificat(LHGospelCanticle magnificat) {
        this.magnificat = magnificat;
    }

    @SuppressWarnings("unused")
    public LHGospelCanticle getMagnificat() {
        return magnificat;
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, Integer previousFK) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            //¿?lecturaBreve.normalizeByTime(hoy.getCalendarTime());
            salmodia.normalizeByTime(liturgyTime.getTimeID());

            //sb.append(today.getAllForView());
            sb.append(Utils.LS2);

            sb.append(getTituloHora(previousFK));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(Utils.LS2);

            sb.append(getSaludoDiosMio());
            sb.append(Utils.LS2);

            sb.append(himno.getAll());
            sb.append(Utils.LS2);

            sb.append(salmodia.getAll());
            sb.append(Utils.LS2);

            sb.append(lecturaBreve.getAllWithHourCheck(6));
            sb.append(Utils.LS2);

            sb.append(magnificat.getAll());
            sb.append(Utils.LS2);

            sb.append(preces.getAll());
            sb.append(LS2);

            sb.append(PadreNuestro.getAll());
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHorasMayores());

        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    //@Override
    public StringBuilder getAllForRead(Integer previousFK) {
        StringBuilder sb = new StringBuilder();
        try {
            //sb.append(today.getAllForRead());
            sb.append(getTituloHoraForRead(previousFK));
            sb.append(getSaludoDiosMioForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(lecturaBreve.getAllForRead());
            sb.append(magnificat.getAllForRead());
            sb.append(preces.getAllForRead());
            sb.append(PadreNuestro.getAll());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    private SpannableStringBuilder getTituloHora(Integer previousFK) {
        String s = (previousFK == 0) ? TITLE_VISPERAS : TITLE_I_VISPERAS;
        return Utils.toH1Red(s);
    }

    private String getTituloHoraForRead(Integer previousFK) {
        String s = (previousFK == 0) ? TITLE_VISPERAS : TITLE_I_VISPERAS_READ;

        //String s = (today.getLiturgiaPrevio() == null) ? TITLE_VISPERAS : TITLE_I_VISPERAS_READ;
        return Utils.pointAtEnd(s);
    }

}