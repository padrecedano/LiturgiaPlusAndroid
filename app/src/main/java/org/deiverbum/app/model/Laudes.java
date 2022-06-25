package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Laudes extends BreviarioHora{
    private Invitatorio invitatorio;
    private BiblicaBreve lecturaBreve;
    private CanticoEvangelico benedictus;
    private Preces preces;
    private final PadreNuestro padreNuestro;

    public Laudes(){

        this.padreNuestro=new PadreNuestro();

    }

    public String getTituloHora() {
        return "LAUDES";
    }

    @SuppressWarnings("unused")
    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(Invitatorio invitatorio) {
        this.invitatorio = invitatorio;
    }

    public BiblicaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(BiblicaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public CanticoEvangelico getBenedictus() {
        return benedictus;
    }

    @SuppressWarnings("unused")
    public void setBenedictus(CanticoEvangelico benedictus) {
        this.benedictus = benedictus;
        //this.benedictus.setTipo(2);

    }

    public Preces getPreces() {
        return preces;
    }

    public void setPreces(Preces preces) {
        this.preces = preces;
    }

    
    public SpannableStringBuilder getForView(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        //PadreNuestro padreNuestro=new PadreNuestro();
        try {
            //TODO hacer esto en la clase Responsorio, revisar Completas y
            // demás horas
            //biblicaBreve.normalizeByTime(metaLiturgia.calendarTime);
            sb.append(hoy.getAll());
            sb.append(LS2);

            if (hoy.getHasSaint()) {
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
            salmodia.normalizeByTime(hoy.idTiempo);

            sb.append(salmodia.getAll());
            sb.append(Utils.LS2);

            sb.append(lecturaBreve.getAllWithHourCheck(2));
            sb.append(Utils.LS2);

            sb.append(benedictus.getAll());
            sb.append(LS2);

            sb.append(preces.getAll());
            sb.append(LS2);

            sb.append(padreNuestro.getAll());
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHorasMayores());
        }catch (Exception e){
            sb.append(e.getMessage());

        }
        return sb;
    }
    
public StringBuilder getForRead(boolean hasInvitatorio){
    StringBuilder sb = new StringBuilder();
    try {
    sb.append(hoy.getAllForRead());
    if(hoy.getHasSaint()) {
        sb.append(santo.getVida());
    }
    sb.append(getTituloHoraForRead());
    sb.append(getSaludoOficioForRead());
    sb.append(invitatorio.getAllForRead(hasInvitatorio));

    sb.append(himno.getAllForRead());
    sb.append(salmodia.getAllForRead());

    sb.append(lecturaBreve.getAllForRead());

    sb.append(benedictus.getAllForRead());

    sb.append(preces.getAllForRead());

    PadreNuestro padreNuestro=new PadreNuestro();
    sb.append(padreNuestro.getAllForRead());
    sb.append(oracion.getAllForRead());
    sb.append(getConclusionHorasMayoresForRead());
    }catch (Exception e){
        sb.append(e.getMessage());
    }
    return sb;
}



    private String getTituloHoraForRead() {
        return "LAUDES.";
    }

}
