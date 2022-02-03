package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Oficio extends BreviarioHora {

    private Invitatorio invitatorio;
    private OficioLecturas oficioLecturas;
    private Preces preces;
    private TeDeum teDeum;

    public Oficio() {
    }

    public Preces getPreces() {
        return preces;
    }

    public void setPreces(Preces preces) {
        this.preces = preces;
    }

    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    public void setInvitatorio(Invitatorio invitatorio) {
        this.invitatorio = invitatorio;
    }

    public OficioLecturas getOficioLecturas() {
        return oficioLecturas;
    }

    public void setOficioLecturas(OficioLecturas oficioLecturas) {
        this.oficioLecturas = oficioLecturas;
    }

    public TeDeum getTeDeum() {
        return teDeum;
    }

    public void setTeDeum(TeDeum teDeum) {
        this.teDeum = teDeum;
    }

    public String getTituloHora() {
        return "OFICIO DE LECTURA";
    }

    public String getTituloHoraForRead() {
        return "OFICIO DE LECTURA.";
    }

    public SpannableStringBuilder getForView(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {

            sb.append(metaLiturgia.getAll());
            sb.append(LS2);
            if (metaLiturgia.getHasSaint()) {
            sb.append(santo.getVida());
            sb.append(LS2);
        }

        sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
        sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
        sb.append(LS2);

        sb.append(getSaludoOficio());
        sb.append(LS2);
        sb.append(invitatorio.getAll(hasInvitatorio));
        sb.append(LS2);

        sb.append(himno.getAll());
        sb.append(LS2);

        sb.append(salmodia.getAll());
        sb.append(LS2);

        sb.append(oficioLecturas.getAll());
        sb.append(LS2);

        sb.append(teDeum.getAll());
        sb.append(LS2);

        sb.append(oracion.getAll());
        sb.append(LS2);
        sb.append(getConclusionHorasMayores());
        }catch (Exception e){
            sb.append(e.getMessage());
        }
        return sb;
    }


    public StringBuilder getForRead(boolean hasInvitatorio) {
        StringBuilder sb = new StringBuilder();
        try {
        sb.append(metaLiturgia.getAllForRead());
        if (metaLiturgia.getHasSaint()) {
            sb.append(santo.getVida());
        }
        sb.append(getTituloHoraForRead());
        sb.append(getSaludoOficioForRead());
        sb.append(invitatorio.getAllForRead(hasInvitatorio));
        sb.append(himno.getAllForRead());
        sb.append(salmodia.getAllForRead());
        sb.append(oficioLecturas.getAllForRead());

        sb.append(teDeum.getAllForRead());
        sb.append(oracion.getAllForRead());
        sb.append(getConclusionHorasMayoresForRead());
    }catch (Exception e){
        sb.append(e.getMessage());

    }
        return sb;
    }
}