package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Breviary extends Liturgy {


    //private MetaLiturgia metaLiturgia;

    private Saint santos;
    private Oficio oficio;
    private Laudes laudes;
    private Intermedia intermedia;
    private Visperas visperas;
    private Completas completas;
    private Mixto mixto;
    private String metaInfo;
    private LHHymn himno;
    private LHPsalmody salmodia;
    private String oracion;
    private final ArrayList<String> hours = new ArrayList<>(
            Arrays.asList("Laudes y Oficio","Oficio","Laudes",
                    "Hora Intermedia: Tercia",
                    "Hora Intermedia: Sexta",
                    "Hora Intermedia: Nona",
                    "Vísperas",
                    "Completas"));
    public Breviary() {
        //super();
    }

    /*
    public MetaLiturgia getMetaLiturgia() {
        return metaLiturgia;
    }

     */
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
/*
    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }
*/
    public Saint getSanto() {
        return santos;
    }

    public void setSanto(Saint santo) {
        this.santos = santo;
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
    

    public LHHymn getHimno() {
        return himno;
    }

    public void setHimno(LHHymn himno) {
        this.himno = himno;
    }

    public LHPsalmody getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(LHPsalmody salmodia) {
        this.salmodia = salmodia;
    }
    
    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public String getOracion() {
        return oracion;
    }
    

    /**
     * //TODO este método existe también en Liturgy, repensar el modelo
     *
     * @return Contenido formateado para la vista con la vida del santo
     */
    public SpannableStringBuilder getVida() {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        if (getMetaLiturgia().getHasSaint()) {
            ssb.append(santos.getVidaSmall());
        }
        return ssb;
    }

    public String getTitulo() {

        if (hasSaint) {
            return santos.getNombre() + LS2;
        } else {
            return getMetaLiturgia().getTitulo() + LS2;
        }
    }
    

}

