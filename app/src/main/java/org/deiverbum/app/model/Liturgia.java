package org.deiverbum.app.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Ignore;


public class Liturgia {

    private Integer liturgiaId;
    private Integer tipoFK;

    public Integer getTiempoFK() {
        return tiempoFK;
    }

    public Integer getTipoFK() {
        return tipoFK;
    }

    public void setTipoFK(Integer tipoFK) {
        this.tipoFK = tipoFK;
    }

    public void setTiempoFK(Integer tiempoFK) {
        this.tiempoFK = tiempoFK;
    }

    public Integer getColorFK() {
        return colorFK;
    }

    public void setColorFK(Integer colorFK) {
        this.colorFK = colorFK;
    }

    //@Embedded
    private Integer tiempoFK;
    @Ignore
    private LiturgiaTiempo liturgiaTiempo;

    public LiturgiaTiempo getLiturgiaTiempo() {
        return liturgiaTiempo;
    }

    public void setLiturgiaTiempo(LiturgiaTiempo liturgiaTiempo) {
        this.liturgiaTiempo = liturgiaTiempo;
    }

    private Integer semana;
    private Integer dia;
    private Integer colorFK;

    private String nombre;
    //@Embedded

    @Ignore
    protected Santo santo;

    //@Embedded
@Ignore
    protected Hoy hoy;
    //@Embedded
    @Ignore

    protected MetaLiturgia metaLiturgia;
    @Ignore

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

    public void setTiempoFKK(LiturgiaTiempo tiempoFK) {
        //this.tiempoFK=tiempoFK;
    }
    public LiturgiaTiempo getTiempoFKK() {
        return null;
    }


    public Santo getSanto() {
        return santo;
    }

    public void setSanto(Santo santo) {

        this.santo = santo;
    }


    @NonNull
    public String toString() {
        return
                String.format("Liturgia:%n%d\t%d\t%s", liturgiaId,tiempoFK,nombre);
    }


}

