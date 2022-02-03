package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class IntermediaHora extends BreviarioHora{
    private MetaLiturgia metaLiturgia;
    private int hourId;
    private LecturaBreve lecturaBreve;
    private Santo santo;
    //private MetaLiturgia metaLiturgia;


    //private int hora;
    public IntermediaHora() {
    }
/*
    public void setMetaBreviario(MetaBreviario metaBreviario) {
        this.metaBreviario = metaBreviario;
    }*/


    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
    public void setSanto(Santo santo) {
        this.santo = santo;
    }

    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }


    public void setHourId(int hourId){
        this.hourId=hourId;
    }

    public int getHourId(){return hourId;}

    public String getTitulo() {

        if (metaBreviario.meta.getHasSaint()) {
            return metaBreviario.getSanto().getNombre() + LS2;
        } else {
            return metaBreviario.meta.getTitulo() + LS2;
        }
    }

    //TODO Obtener el título de la hora: Agregar el id en la respuesta de la API
    public String getTituloHora() {
        String titulo="";
        switch (hourId) {
            case 3:
                titulo="Tercia";
                break;
            case 4:
                titulo="Sexta";
                break;
            case 5:
                titulo="Nona";
                break;

            default:
                break;

        }
        return titulo;
    }

    public SpannableStringBuilder getForView(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        Himno himno = getHimno();
        Salmodia salmodia = getSalmodia();
        LecturaBreve lecturaBreve = getLecturaBreve();
MetaBreviario metaLiturgia=metaBreviario;
        sb.append(metaBreviario.getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(metaLiturgia.getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(getTitulo()));
        sb.append(Utils.toH3Red(getTituloHora()));
        //sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));

        sb.append(LS2);
        sb.append(Utils.getSaludoDiosMio());
        sb.append(LS2);

        sb.append(himno.getHeader());
        sb.append(LS2);
        sb.append(himno.getTextoSpan());
        sb.append(LS2);

        sb.append(salmodia.getHeader());
        sb.append(LS2);
        sb.append(salmodia.getSalmos(0));

        sb.append(LS);
        sb.append(lecturaBreve.getHeaderLectura());
        sb.append(LS2);
        sb.append(lecturaBreve.getTexto());
        sb.append(LS2);
        sb.append(lecturaBreve.getResponsorioSpan());
        sb.append(LS);
        sb.append(Utils.formatTitle("ORACIÓN"));
        sb.append(LS2);
        //sb.append(Utils.fromHtml(getOracion()));
        sb.append(LS2);
        sb.append(Utils.getConclusionIntermedia());
        return sb;

    }

    public String getById(int mHourId, boolean hasInvitatorio) {
        return "Lorem ipsum";
    }
/*
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
    */
}