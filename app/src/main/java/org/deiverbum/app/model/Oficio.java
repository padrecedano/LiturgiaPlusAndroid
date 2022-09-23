package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Oficio extends BreviaryHour {

    private LHInvitatory invitatorio;
    //private LHOfficeOfReading oficioLecturas;
    private LHIntercession preces;
    //private TeDeum teDeum;

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

    public String getTituloHora() {
        return "OFICIO DE LECTURA";
    }

    public String getTituloHoraForRead() {
        return "OFICIO DE LECTURA.";
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            invitatorio.normalizeByTime(liturgyTime.getTiempoId());
            salmodia.normalizeByTime(liturgyTime.getTiempoId());
            oficioLecturas.normalizeByTime(liturgyTime.getTiempoId());

            sb.append(hoy.getAllForView());
            //sb.append(metaLiturgia.getAll());
            sb.append(LS2);
            if (hoy.getHasSaint()) {
                sb.append(santo.getVidaSmall());
                sb.append(LS2);
            }

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(getSaludoOficio());
            sb.append(LS2);
            //LiturgyTime liturgyTime=getLiturgyTime();


            sb.append(invitatorio.getAll(hasInvitatorio));
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);
            sb.append(salmodia.getAll(1));
            sb.append(LS2);

            sb.append(oficioLecturas.getAll(liturgyTime.getTiempoId()));
            //sb.append(LS2);

            if (teDeum.status) {
                sb.append(teDeum.getAll());
            }

            sb.append(oracion.getAll());
            sb.append(LS2);
            sb.append(getConclusionHorasMayores());
        } catch (Exception e) {
/*            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            sb.append(sw.toString());
*/
            //sb.append(e.getMessage());
        }

        return sb;
    }


    public StringBuilder getForRead(boolean hasInvitatorio) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(hoy.getAllForRead());
            if (hoy.getHasSaint()) {
                sb.append(santo.getVida());
            }
            sb.append(getTituloHoraForRead());
            sb.append(getSaludoOficioForRead());
            sb.append(invitatorio.getAllForRead(hasInvitatorio));
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(oficioLecturas.getAllForRead());

            if (teDeum.status) {
                sb.append(teDeum.getAllForRead());
            }
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;
    }
}