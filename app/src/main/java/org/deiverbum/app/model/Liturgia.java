package org.deiverbum.app.model;

import androidx.annotation.NonNull;


public class Liturgia {

    private Integer liturgiaId;
    private Integer tipoFK;
    private LiturgiaTiempo tiempoFK;
    private Integer semana;
    private Integer dia;
    private Integer colorFK;

    private String nombre;
    protected Santo santo;

    protected Hoy hoy;
    protected MetaLiturgia metaLiturgia;

    public boolean hasSaint = false;


    public Liturgia() {
    }


    public Integer getLiturgiaId() {
        return liturgiaId;
    }

    public void setLiturgiaId(Integer liturgiaId) {
        this.liturgiaId = liturgiaId;
    }

    public Integer getColorId() {
        return colorFK;
    }

    public void setColorId(Integer colorFK) {
        this.colorFK = colorFK;
    }

    public String getNombre() {

        return nombre!=null ? nombre : "***";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MetaLiturgia getMetaLiturgia() {
        return this.metaLiturgia;
    }

    public Hoy getHoy() {
        return hoy;
    }

    public void setHoy(Hoy hoy) {
        this.hoy = hoy;
    }
    @SuppressWarnings("unused")
    public void setMetaLiturgia(MetaLiturgia metaLiturgia) {
        this.metaLiturgia = metaLiturgia;
    }


    public void setDia(Integer dia) {
        this.dia=dia;
    }

    public void setSemana(Integer semana) {
        this.semana=semana;
    }

    public int getDia() {
        return this.dia;
    }
    public int getSemana() {
        return this.semana;
    }

    public void setTiempoFK(LiturgiaTiempo tiempoFK) {
        this.tiempoFK=tiempoFK;
    }
    public LiturgiaTiempo getTiempoFK() {
        return tiempoFK;
    }


    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {
        this.santo = santo;
    }


    @NonNull
    public String toString() {
        return "This is the data*: " + getMetaLiturgia().getFecha();
    }


}

