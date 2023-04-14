package org.deiverbum.app.model;

import org.deiverbum.app.utils.Utils;

public class MetaLiturgia {
    protected String fecha;
    protected int idTiempo;

    protected int idSemana;
    protected int idColor;
    protected int idLecturas;
    protected int idHour = 0;
    protected String mensaje;
    protected String titulo;

    protected int idPrevio;
    protected int idTiempoPrevio;

    protected boolean hasSaint;
    protected int weekDay;

    public MetaLiturgia() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = (titulo != null) ? titulo : "";
    }

    public String getFecha() {
        return (fecha != null) ? Utils.getLongDate(fecha) : "";
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTiempo() {
        return idTiempo;
    }

    public void setTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    public int getWeekDay() {
        return this.weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getSemana() {
        return idSemana;
    }

    public void setSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public void setIdHour(int idHour) {
        this.idHour = idHour;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getColor() {
        return idColor;
    }

    public void setColor(int idColor) {
        this.idColor = idColor;
    }

    public boolean getHasSaint() {
        return hasSaint;
    }

    public void setHasSaint(boolean hasSaint) {
        this.hasSaint = hasSaint;
    }

    public void setIdTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    public void setIdSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public void setIdLecturas(int idLecturas) {
        this.idLecturas = idLecturas;
    }

    public void setIdPrevio(int idPrevio) {
        this.idPrevio = idPrevio;
    }

    public void setIdTiempoPrevio(int idTiempoPrevio) {
        this.idTiempoPrevio = idTiempoPrevio;
    }
}

