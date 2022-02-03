package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Breviario extends Liturgia {


    private MetaLiturgia metaLiturgia;

    private Santo santo;
    private Oficio oficio;
    private Laudes laudes;
    private Intermedia intermedia;
    private Visperas visperas;
    private Completas completas;
    private Mixto mixto;
    private String metaInfo;
    private Himno himno;
    private Salmodia salmodia;
    private String oracion;
    private final ArrayList<String> hours = new ArrayList<>(
            Arrays.asList("Laudes y Oficio","Oficio","Laudes",
                    "Hora Intermedia: Tercia",
                    "Hora Intermedia: Sexta",
                    "Hora Intermedia: Nona",
                    "Vísperas",
                    "Completas"));
    public Breviario() {
        //super();
    }

    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }
    public void setMetaInfo(String metaInfo){
        this.metaInfo=metaInfo;
    }

    //TODO checkthis
    public String getMetaInfo() {

        if (!metaInfo.equals("")) {
            return "<br><br>" + metaInfo;
        } else {
            return "";
        }
    }

    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
    
    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }

    public void setVisperas(Visperas visperas) {
        this.visperas = visperas;
    }


    public Oficio getOficio() {
        return oficio;
    }
    

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public Laudes getLaudes() {
        return laudes;
    }

    public void setLaudes(Laudes laudes) {
        this.laudes = laudes;
    }

    public Intermedia getIntermedia() {
        return intermedia;
    }

    public Visperas getVisperas() {
        return visperas;
    }

    public Completas getCompletas() {
        return completas;
    }

    public void setCompletas(Completas completas) {
        this.completas = completas;
    }

    public void setIntermedia(Intermedia intermedia) {
        this.intermedia = intermedia;
    }

    public Mixto getMixto() {
        return mixto;
    }

    public void setMixto(Mixto mixto) {
        this.mixto = mixto;
    }


    /**
     * Obtiene el título de la hora por su Id
     *
     * @param hourId ID de la hora
     * @return El título respectivo
     * @since 2022.1
     */
    public String getTitle(int hourId) {
        return hours.get(hourId);
    }
    

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public Salmodia getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }
    
    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public String getOracion() {
        return oracion;
    }
    

    /**
     * //TODO este método existe también en Liturgia, repensar el modelo
     *
     * @return Contenido formateado para la vista con la vida del santo
     */
    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (metaLiturgia.getHasSaint()) {
            ssb.append(santo.getVidaSmall());
        }
        return ssb;
    }

    public String getTitulo() {

        if (hasSaint) {
            return santo.getNombre() + LS2;
        } else {
            return metaLiturgia.getTitulo() + LS2;
        }
    }
    

}

