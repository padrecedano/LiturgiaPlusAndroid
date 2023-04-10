package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.LS;
import static org.deiverbum.app.utils.Constants.TITLE_LAUDES;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Laudes extends BreviaryHour {
    private LHInvitatory invitatorio;
    private BiblicalShort lecturaBreve;
    private LHGospelCanticle gospelCanticle;
    private LHIntercession preces;

    public Laudes() {
    }

    public SpannableStringBuilder getTituloHora() {
        return Utils.toH1Red(TITLE_LAUDES);
    }

    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_LAUDES);
    }

    @SuppressWarnings("unused")
    public LHInvitatory getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(LHInvitatory invitatorio) {
        this.invitatorio = invitatorio;
    }

    public BiblicalShort getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public LHGospelCanticle getGospelCanticle() {
        return gospelCanticle;
    }

    @SuppressWarnings("unused")
    public void setGospelCanticle(LHGospelCanticle gospelCanticle) {
        this.gospelCanticle = gospelCanticle;
    }

    public LHIntercession getPreces() {
        return preces;
    }

    public void setPreces(LHIntercession preces) {
        this.preces = preces;
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, boolean hasSaint) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            //TODO hacer esto en la clase LHResponsoryShort, revisar Completas y
            // demás horas
            //biblicaBreve.normalizeByTime(metaLiturgia.calendarTime);
            this.hasSaint = hasSaint;
            invitatorio.normalizeByTime(liturgyTime.getTimeID());
            salmodia.normalizeByTime(liturgyTime.getTimeID());

            //sb.append(today.getAllForView());
            sb.append(LS2);

            if (santo != null && this.hasSaint) {
                invitatorio.normalizeIsSaint(santo.theName);
                sb.append(santo.getVidaSmall());
                sb.append(LS);
            }

            sb.append(getTituloHora());
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(getSaludoOficio());
            sb.append(LS2);
            sb.append(invitatorio.getAll());
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);

            sb.append(salmodia.getAll());
            sb.append(LS2);

            sb.append(lecturaBreve.getAllWithHourCheck(2));
            sb.append(LS2);

            sb.append(gospelCanticle.getAll());
            sb.append(LS2);

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

    public StringBuilder getForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            //sb.append(today.getAllForRead());
            if (santo != null && this.hasSaint) {
                sb.append(santo.getVida());
            }
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoOficioForRead());
            sb.append(invitatorio.getAllForRead());

            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());

            sb.append(lecturaBreve.getAllForRead());

            sb.append(gospelCanticle.getAllForRead());

            sb.append(preces.getAllForRead());

            PadreNuestro padreNuestro = new PadreNuestro();
            sb.append(padreNuestro.getAllForRead());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
}