package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.List;

public class Mixto extends BreviaryHour {
    //private MetaLiturgia metaLiturgia;
    //private Laudes laudes;
    //private Oficio oficio;
    //private Mass misa;
    private LHInvitatory invitatorio;
    private final PadreNuestro padreNuestro;
    private MassReadingList misaLecturas;
    //private List<MassReading> evangelios;
    //private LHOfficeOfReading oficioLecturas;

/*
    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }
    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
*/

    public Mixto() {
        this.padreNuestro = new PadreNuestro();
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

    public void setMisaLecturas(MassReadingList misaLecturas) {
        this.misaLecturas = misaLecturas;
    }

    public String getTituloHora() {
        return "LAUDES y OFICIO";
    }

    public String getTituloHoraForRead() {
        return "LAUDES y OFICIO.";
    }

    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, boolean isVariable) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            //Saint santo = laudes.getSanto();
            invitatorio.normalizeByTime(liturgyTime.getTiempoId());
            laudes.salmodia.normalizeByTime(liturgyTime.getTiempoId());
            oficio.getOficioLecturas().normalizeByTime(liturgyTime.getTiempoId());

            sb.append(hoy.getAllForView());

            LHHymn himno = laudes.getHimno();

            LHPsalmody salmodia = laudes.getSalmodia();

            LHOfficeOfReading oficioLecturas = oficio.getOficioLecturas();
            Biblical lecturaBreve = laudes.getLecturaBreve();
            LHGospelCanticle_ ce = laudes.getBenedictus();
            LHIntercession preces = laudes.getPreces();
            //Evangelio misaEvangelio = misa.getMisaLecturas().getEvangelio();

            //sb.append(metaLiturgia.getAll());
            sb.append(Utils.LS2);


            if (hoy.getHasSaint()) {
                sb.append(santo.getVida());
                sb.append(LS2);
            }

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(laudes.getSaludoOficio());
            sb.append(Utils.LS2);

            sb.append(invitatorio.getAll(isVariable));
            sb.append(Utils.LS2);

            sb.append(himno.getAll());
            sb.append(Utils.LS2);

            sb.append(salmodia.getAll());
            //sb.append(Utils.LS2);

            sb.append(lecturaBreve.getAll());
            sb.append(Utils.LS2);
            sb.append(oficioLecturas.getAll(liturgyTime.getTiempoId()));
            sb.append(Utils.LS2);

            sb.append(Utils.formatSubTitle("evangelio del día"));
            sb.append(Utils.LS2);

            //sb.append(misaLecturas.getAllEvangelioForView());
            for (MassReading item : evangelios) {
                sb.append(item.getAll());
            }
            sb.append(Utils.LS2);

            sb.append(ce.getAll());
            sb.append(Utils.LS2);

            sb.append(preces.getAll());
            sb.append(LS2);

            sb.append(padreNuestro.getAll());
            sb.append(LS2);

            sb.append(laudes.oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHorasMayores());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getForRead(boolean isVariable) {
        StringBuilder sb = new StringBuilder();

        try {
            //Saint santo=laudes.getSanto();
            LHHymn himno = laudes.getHimno();
            LHPsalmody salmodia = laudes.getSalmodia();
            LHOfficeOfReading oficioLecturas = oficio.getOficioLecturas();
            Biblical lecturaBreve = laudes.getLecturaBreve();
            LHGospelCanticle_ ce = laudes.getBenedictus();
            LHIntercession preces = laudes.getPreces();
            sb.append(hoy.getAllForRead());

            if (hoy.getHasSaint()) {
                sb.append(santo.getVida());
            }

            sb.append(getTituloHoraForRead());
            sb.append(laudes.getSaludoOficioForRead());

            sb.append(invitatorio.getAllForRead(isVariable));

            sb.append(himno.getAllForRead());

            sb.append(salmodia.getAllForRead());

            sb.append(lecturaBreve.getAllForRead());

            sb.append(oficioLecturas.getAllForRead());

            sb.append(Utils.formatSubTitle("Evangelio del día."));

            sb.append(misaLecturas.getAllEvangelioForRead());

            sb.append(ce.getAllForRead());

            sb.append(preces.getAllForRead());

            sb.append(padreNuestro.getAllForRead());

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
