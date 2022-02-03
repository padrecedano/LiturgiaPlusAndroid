package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

import java.util.HashMap;
/*
{
  "fecha":"20211218",
  "idTiempo":1,
  "idTipo":4,
  "idDia":18,
  "idSemana":5,
  "titulo":"Ferias Mayores de Adviento: 18 de Diciembre",
  "idColor":11,
  "idBreviario":383,
  "idLecturas":383,
  "idVisperas":945,
  "tituloVisperas":"Domingo IV del Tiempo de Adviento",
  "idTiempoVisperas":1,
  "weekDay":7
}

 */
public class MetaLiturgia {
    protected String fecha;
    protected int idTiempo;
    protected int calendarTime;

    protected int idTipo;
    protected int idDia;
    protected int idSemana;
    protected int idColor;
    protected int idBreviario;
    protected int idLecturas;
    protected int idHour=0;


    protected String mensaje;
    protected String titulo;

    protected int idPrevio;
    protected int idTiempoPrevio;
    protected String tituloPrevio;

    protected boolean hasSaint;
    protected int weekDay;

    public MetaLiturgia() {
    }

    public String getTituloVisperas() {
        if (idPrevio==0) {
            return titulo;
        } else {
            return tituloPrevio;
        }
    }
    public String getTitulo() {
        return idHour==6 ? getTituloVisperas() : titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getFecha() {
        return Utils.getLongDate(fecha);
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public void setTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    public int getTiempo() {
        return idTiempo;
    }

    public void setCalendarTime(int calendarTime) {
        this.calendarTime = calendarTime;
    }

    public int getCalendarTime() {
        return calendarTime;
    }

    public String getTiempoNombre(boolean isVisperas) {
        int theTime = this.getTiempo();
        if (theTime < 15) {
            HashMap<Integer, String> mapTiempos = new HashMap<>();
            mapTiempos.put(0, "***");
            mapTiempos.put(1, "Tiempo de Adviento");
            mapTiempos.put(2, "Tiempo de Navidad");
            mapTiempos.put(3, "Tiempo de Cuaresma");
            mapTiempos.put(4, "Semana Santa");
            mapTiempos.put(5, "Santo Triduo Pascual");
            mapTiempos.put(6, "Tiempo de Pascua");
            mapTiempos.put(7, "Tiempo Ordinario");
            mapTiempos.put(8, "***");
            mapTiempos.put(9, "Propio de los Santos");
            mapTiempos.put(10, "Oficios Comunes");
            mapTiempos.put(11, "Misas Rituales");
            mapTiempos.put(12, "Diversas Necesidades");
            mapTiempos.put(13, "Misas Votivas");
            mapTiempos.put(14, "Oficio de Difuntos");
            mapTiempos.put(15, "***");
            mapTiempos.put(16, "***");
            mapTiempos.put(17, "***");
            mapTiempos.put(18, "***");

            return mapTiempos.get(theTime);
        } else {
            return "***";
        }
    }

    public String getTiempoNombre() {
        if (idTiempo < 15) {
            HashMap<Integer, String> mapTiempos = new HashMap<>();
            mapTiempos.put(0, "***");
            mapTiempos.put(1, "Tiempo de Adviento");
            mapTiempos.put(2, "Tiempo de Navidad");
            mapTiempos.put(3, "Tiempo de Cuaresma");
            mapTiempos.put(4, "Semana Santa");
            mapTiempos.put(5, "Santo Triduo Pascual");
            mapTiempos.put(6, "Tiempo de Pascua");
            mapTiempos.put(7, "Tiempo Ordinario");
            mapTiempos.put(8, "***");
            mapTiempos.put(9, "Propio de los Santos");
            mapTiempos.put(10, "Oficios Comunes");
            mapTiempos.put(11, "Misas Rituales");
            mapTiempos.put(12, "Diversas Necesidades");
            mapTiempos.put(13, "Misas Votivas");
            mapTiempos.put(14, "Oficio de Difuntos");
            mapTiempos.put(15, "***");
            mapTiempos.put(16, "***");
            mapTiempos.put(17, "***");
            mapTiempos.put(18, "***");

            return mapTiempos.get(idTiempo);
        } else {
            return "***";
        }
    }
    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDay() {
        return this.weekDay;
    }
    public int getSemana() {
        return idSemana;
    }

    public void setSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public int getIdHour() {
        return idHour;
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

    @Deprecated
    public String getSalterio() {
        return "";
    }


    public void setSalterio(String salterio) {
        //this.salterio = salterio;
    }

    public int getColor() {
        return idColor;
    }

    public void setColor(int idColor) {
        this.idColor = idColor;
    }

    /*public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
*/
    public boolean getHasSaint() {
        return hasSaint;
    }

    public void setHasSaint(boolean hasSaint) {
        this.hasSaint = hasSaint;
    }

    /**
     * <p>Obtiene la información Meta completa para presentar en pantalla.</p>
     * @since 2021.01
     */
    public SpannableStringBuilder getForViewMisa() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(getTitulo()));
        //sb.append(Utils.LS2);
        return sb;
    }

    public Spanned getFechaForRead() {
        return Utils.fromHtml("<p>" + getFecha() + ".</p>");
    }

    public String getTituloForRead() {
        return String.format("%s%s",getTitulo(), ".");
    }

    public int getIdTiempo() {
        return idTiempo;
    }

    public void setIdTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public int getIdSemana() {
        return idSemana;
    }

    public void setIdSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public int getIdBreviario() {
        return idBreviario;
    }

    public void setIdBreviario(int idBreviario) {
        this.idBreviario = idBreviario;
    }

    public int getIdLecturas() {
        return idLecturas;
    }

    public void setIdLecturas(int idLecturas) {
        this.idLecturas = idLecturas;
    }

    public int getIdPrevio() {
        return idPrevio;
    }

    public void setIdPrevio(int idPrevio) {
        this.idPrevio = idPrevio;
    }

    public int getIdTiempoPrevio() {
        return idTiempoPrevio;
    }

    public void setIdTiempoPrevio(int idTiempoPrevio) {
        this.idTiempoPrevio = idTiempoPrevio;
    }



    public String getTituloPrevio() {
        return tituloPrevio;
    }

    public void setTituloPrevio(String tituloPrevio) {
        this.tituloPrevio = tituloPrevio;
    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(getTitulo()));

        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb=new StringBuilder();
        sb.append(getFecha());
        sb.append(".");
        sb.append(getTiempoNombre());
        sb.append(".");
        sb.append(getTituloForRead());
        return sb.toString();
    }
}



