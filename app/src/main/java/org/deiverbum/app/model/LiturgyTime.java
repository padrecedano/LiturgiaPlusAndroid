package org.deiverbum.app.model;

/**
 * <p>Clase para expresar los tiempos litúrgicos.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LiturgyTime {

    private Integer tiempoId;
    private String tiempo;
    private String liturgyName;

    public Integer getTiempoId() {
        return tiempoId;
    }

    public void setTiempoId(Integer tiempoId) {
        this.tiempoId = tiempoId;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getLiturgyName() {
        return liturgyName;
    }

    public void setLiturgyName(String liturgyName) {
        this.liturgyName = liturgyName;
    }
    public String getAllForRead() {
        return getLiturgyName();
    }

    }
