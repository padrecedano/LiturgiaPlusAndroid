package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.LS;
import static org.deiverbum.app.utils.Constants.TITLE_OFICIO;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Oficio extends BreviaryHour
{

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

    @SuppressWarnings("unused")
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

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, boolean hasSaint, boolean nightMode) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        this.hasSaint=hasSaint;
        try {
            invitatorio.normalizeByTime(liturgyTime.getTimeID());
            salmodia.normalizeByTime(liturgyTime.getTimeID());
            oficioLecturas.normalizeByTime(liturgyTime.getTimeID());
            sb.append(LS2);
            if (santo !=null && this.hasSaint) {
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
            sb.append(salmodia.getAll(1));
            sb.append(LS2);

            sb.append(oficioLecturas.getAll(liturgyTime.getTimeID()));

            if (teDeum.isStatus()) {
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
            if (santo != null && this.hasSaint) {
                sb.append(santo.getVida());
            }
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoOficioForRead());
            sb.append(invitatorio.getAllForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(oficioLecturas.getAllForRead());

            if (teDeum.isStatus()) {
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