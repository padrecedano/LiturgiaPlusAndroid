package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Mixto extends BreviarioHora{
    //private MetaLiturgia metaLiturgia;
    private Laudes laudes;
    private Oficio oficio;
    private Misa misa;
    private Invitatorio invitatorio;
    private final PadreNuestro padreNuestro;

/*
    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }
    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
*/

public Mixto(){
    this.padreNuestro=new PadreNuestro();
}

    @SuppressWarnings("unused")
    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(Invitatorio invitatorio) {
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

    public Misa getMisa() {
        return misa;
    }
    public void setMisa(Misa misa) {
        this.misa = misa;
    }

    public String getTituloHora() {
        return "LAUDES y OFICIO";
    }

    public String getTituloHoraForRead() {
        return "LAUDES y OFICIO.";
    }

    public SpannableStringBuilder getForView(boolean isVariable) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            //Santo santo = laudes.getSanto();
            Himno himno = laudes.getHimno();
            Salmodia salmodia = laudes.getSalmodia();
            OficioLecturas oficioLecturas = oficio.getOficioLecturas();
            LecturaBreve lecturaBreve = laudes.getLecturaBreve();
            Benedictus ce = laudes.getBenedictus();
            Preces preces = laudes.getPreces();
            Evangelio misaEvangelio = misa.getMisaLecturas().getEvangelio();

            sb.append(metaLiturgia.getAll());
            sb.append(Utils.LS2);

            if (metaLiturgia.getHasSaint()) {
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

            sb.append(oficioLecturas.getAll());
            sb.append(Utils.LS2);

            sb.append(misaEvangelio.getAll());
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
        } catch (Exception e){
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
    
    public StringBuilder getForRead(boolean isVariable){
        StringBuilder sb = new StringBuilder();

        try {
        Santo santo=laudes.getSanto();
        Himno himno = laudes.getHimno();
        Salmodia salmodia = laudes.getSalmodia();
        OficioLecturas oficioLecturas=oficio.getOficioLecturas();
        LecturaBreve lecturaBreve = laudes.getLecturaBreve();
        Benedictus ce = laudes.getBenedictus();
        Preces preces = laudes.getPreces();
        MisaLecturas misaLecturas = misa.getMisaLecturas();
        Evangelio misaEvangelio = misaLecturas.getEvangelio();

        sb.append(metaLiturgia.getAllForRead());

        if(metaLiturgia.getHasSaint()) {
            sb.append(santo.getVida());
        }

        sb.append(getTituloHoraForRead());
        sb.append(laudes.getSaludoOficioForRead());

        sb.append(invitatorio.getAllForRead(isVariable));

        sb.append(himno.getAllForRead());

        sb.append(salmodia.getAllForRead());

        sb.append(lecturaBreve.getAllForRead());

        sb.append(oficioLecturas.getAllForRead());

        sb.append(misaEvangelio.getAllForRead());

        sb.append(ce.getAllForRead());

        sb.append(preces.getAllForRead());

        sb.append(padreNuestro.getAllForRead());

        sb.append(laudes.getOracion().getAllForRead());
        sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e){
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

}
