package org.deiverbum.app.model;

/**
 * <p>Clase para expresar los tiempos litúrgicos.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LiturgyTime {

    private Integer timeID;
    private String tiempo;
    private String liturgyName;

    public Integer getTimeID() {
        return timeID;
    }

    public void setTimeID(Integer timeID) {
        this.timeID = timeID;
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
