package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_OFICIO;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Oficio extends BreviaryHour {

    private LHInvitatory invitatorio;
    private LHIntercession preces;

    public Oficio() {
    }

    public LHIntercession getPreces() {
        return preces;
    }

    public void setPreces(LHIntercession preces) {
        this.preces = preces;
    }

    @SuppressWarnings("unused")
    public LHInvitatory getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(LHInvitatory invitatorio) {
        this.invitatorio = invitatorio;
    }

    public LHOfficeOfReading getOficioLecturas() {
        return oficioLecturas;
    }

    @SuppressWarnings("unused")
    public void setOficioLecturas(LHOfficeOfReading oficioLecturas) {
        this.oficioLecturas = oficioLecturas;
    }

    @SuppressWarnings("unused")
    public TeDeum getTeDeum() {
        return teDeum;
    }

    @SuppressWarnings("unused")
    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }

    public SpannableStringBuilder getTituloHora() {

        return Utils.toH1Red(TITLE_OFICIO);
    }

    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_OFICIO);
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            invitatorio.normalizeByTime(liturgyTime.getTiempoId());
            salmodia.normalizeByTime(liturgyTime.getTiempoId());
            oficioLecturas.normalizeByTime(liturgyTime.getTiempoId());
            sb.append(hoy.getAllForView());
            sb.append(LS2);
            if (hoy.getHasSaint()==1) {
                sb.append(santo.getVidaSmall());
                sb.append(LS2);
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
            sb.append(salmodia.getAll(1));
            sb.append(LS2);

            sb.append(oficioLecturas.getAll(liturgyTime.getTiempoId()));

            if (teDeum.status) {
                sb.append(teDeum.getAll());
            }

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
            sb.append(hoy.getAllForRead());
            if (hoy.getHasSaint()==1) {
                sb.append(santo.getVida());
            }
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoOficioForRead());
            sb.append(invitatorio.getAllForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(oficioLecturas.getAllForRead());

            if (teDeum.status) {
                sb.append(teDeum.getAllForRead());
            }
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
}