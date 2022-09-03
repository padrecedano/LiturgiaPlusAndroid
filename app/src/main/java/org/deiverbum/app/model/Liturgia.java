package org.deiverbum.app.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Ignore;


public class Liturgia {
    public Integer getLiturgyID() {
        return liturgyID;
    }

    public void setLiturgyID(Integer liturgyID) {
        this.liturgyID = liturgyID;
    }

    public Integer getTypeFK() {
        return typeFK;
    }

    public void setTypeFK(Integer typeFK) {
        this.typeFK = typeFK;
    }

    public Integer getTimeFK() {
        return timeFK;
    }

    public void setTimeFK(Integer timeFK) {
        this.timeFK = timeFK;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer liturgyID;
    private Integer typeFK;
    private Integer timeFK;
    @Ignore
    private LiturgiaTiempo liturgiaTiempo;
    private Integer week;
    private Integer day;
    private Integer colorFK;

    private String name;
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
    public Integer getTiempoFK() {
        return timeFK;
    }

    public Integer getTipoFK() {
        return typeFK;
    }

    public void setTipoFK(Integer tipoFK) {
        this.typeFK = tipoFK;
    }

    public void setTiempoFK(Integer tiempoFK) {
        this.timeFK = tiempoFK;
    }

    public Integer getColorFK() {
        return colorFK;
    }

    public void setColorFK(Integer colorFK) {
        this.colorFK = colorFK;
    }

    //@Embedded


    public LiturgiaTiempo getLiturgiaTiempo() {
        return liturgiaTiempo;
    }

    public void setLiturgiaTiempo(LiturgiaTiempo liturgiaTiempo) {
        this.liturgiaTiempo = liturgiaTiempo;
    }



    public Liturgia() {
    }


    public Integer getLiturgiaId() {
        return liturgyID;
    }

    public void setLiturgiaId(Integer liturgiaId) {
        this.liturgyID = liturgiaId;
    }

    public Integer getColorId() {
        return colorFK;
    }

    public void setColorId(Integer colorFK) {
        this.colorFK = colorFK;
    }

    public String getNombre() {

        return name!=null ? name : "***";
    }

    public void setNombre(String nombre) {
        this.name = nombre;
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
        this.day=dia;
    }

    public void setSemana(Integer semana) {
        this.week=semana;
    }

    public int getDia() {
        return this.day;
    }
    public int getSemana() {
        return this.week;
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
                String.format("Liturgia:%n%d\t%d\t%s", liturgyID,timeFK,name);
    }


}

