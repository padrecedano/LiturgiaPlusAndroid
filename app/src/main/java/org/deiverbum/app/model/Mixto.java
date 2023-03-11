package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_MIXTO;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Mixto extends BreviaryHour {
    private LHInvitatory invitatorio;
    private MassReadingList misaLecturas;

    public Mixto() {
    }

    @SuppressWarnings("unused")
    public LHInvitatory getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(LHInvitatory invitatorio) {
        this.invitatorio = invitatorio;
    }

    public Laudes getLaudes() {
        return laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }



    public MassReadingList getMisaLecturas() {
        return misaLecturas;
    }

    @SuppressWarnings("unused")
    public void setMisaLecturas(MassReadingList misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public SpannableStringBuilder getTituloHora() {
        return Utils.toH1Red(TITLE_MIXTO);
    }
    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_MIXTO);
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            invitatorio.normalizeByTime(liturgyTime.getTimeID());
            laudes.salmodia.normalizeByTime(liturgyTime.getTimeID());
            //oficio.getOficioLecturas().normalizeByTime(liturgyTime.getTiempoId());
            //sb.append(today.getAllForView());
            LHHymn himno = laudes.getHimno();
            LHPsalmody salmodia = laudes.getSalmodia();
            LHOfficeOfReading oficioLecturas = oficio.getOficioLecturas();
            Biblical lecturaBreve = laudes.getLecturaBreve();
            LHGospelCanticle ce = laudes.getBenedictus();
            LHIntercession preces = laudes.getPreces();
            sb.append(Utils.LS2);

            if (santo !=null) {
                invitatorio.normalizeIsSaint(santo.theName);
                sb.append(santo.getVidaSmall());
                sb.append(LS2);
            }

            sb.append(getTituloHora());
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);
            sb.append(laudes.getSaludoOficio());
            sb.append(Utils.LS2);
            sb.append(invitatorio.getAll());
            sb.append(Utils.LS2);
            sb.append(himno.getAll());
            sb.append(Utils.LS2);
            sb.append(salmodia.getAll());
            sb.append(lecturaBreve.getAll());
            sb.append(Utils.LS2);
            sb.append(oficioLecturas.getAll(liturgyTime.getTimeID()));
            sb.append(Utils.LS2);
            sb.append(Utils.formatSubTitle("evangelio del día"));
            sb.append(Utils.LS2);
            for (MassReading item : evangelios) {
                sb.append(item.getAll());
            }
            sb.append(Utils.LS2);
            sb.append(ce.getAll());
            sb.append(Utils.LS2);
            sb.append(preces.getAll());
            sb.append(LS2);
            sb.append(PadreNuestro.getAll());
            sb.append(LS2);
            sb.append(laudes.oracion.getAll());
            sb.append(LS2);
            sb.append(getConclusionHorasMayores());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getForReadd() {
        StringBuilder sb = new StringBuilder();

        try {
            LHHymn himno = laudes.getHimno();
            LHPsalmody salmodia = laudes.getSalmodia();
            LHOfficeOfReading oficioLecturas = oficio.getOficioLecturas();
            Biblical lecturaBreve = laudes.getLecturaBreve();
            LHGospelCanticle ce = laudes.getBenedictus();
            LHIntercession preces = laudes.getPreces();
            //sb.append(today.getAllForRead());
/*
            if (today.getHasSaint()==1) {
                sb.append(santo.getVida());
            }
*/
            sb.append(getTituloHoraForRead());
            sb.append(laudes.getSaludoOficioForRead());
            sb.append(invitatorio.getAllForRead());
            sb.append(himno.getAllForRead());
            sb.append(salmodia.getAllForRead());
            sb.append(lecturaBreve.getAllForRead());
            sb.append(oficioLecturas.getAllForRead());
            sb.append(Utils.formatSubTitle("Evangelio del día."));
            for (MassReading item : evangelios) {
                sb.append(item.getAllForRead());
            }
            //sb.append(misaLecturas.getAllEvangelioForRead());
            sb.append(ce.getAllForRead());
            sb.append(preces.getAllForRead());
            sb.append(PadreNuestro.getAll());
            sb.append(laudes.getOracion().getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public void setEvangelios(List<MassReading> evangelios) {
        this.evangelios=evangelios;
    }
}
