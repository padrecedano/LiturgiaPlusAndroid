package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Numerals;
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
    protected int idHour = 0;


    protected String mensaje;
    protected String titulo;

    protected int idPrevio;
    protected int idTiempoPrevio;
    protected String tituloPrevio;

    protected boolean hasSaint;
    protected int weekDay;

    private Liturgia liturgiaFeria;
    private Liturgia liturgiaPrevio;

    public MetaLiturgia() {
    }

    public String getTituloVisperas() {
        if (idPrevio == 0) {
            return titulo;
        } else {
            return tituloPrevio;
        }
    }

    public String getTitulo() {
        return idHour == 6 ? getTituloVisperas() : titulo;
        /*
        return idHour==6 && liturgiaPrevio!=null ?
                liturgiaPrevio.getNombre() : liturgiaFeria.getNombre();*/
    }

    public void setTitulo(String titulo) {
        this.titulo = (titulo != null) ? titulo : "";
    }

    public Liturgia getLiturgiaFeria() {
        return liturgiaFeria;
    }

    public void setLiturgiaFeria(Liturgia liturgiaFeria) {
        this.liturgiaFeria = liturgiaFeria;
    }

    public Liturgia getLiturgiaPrevio() {
        return liturgiaPrevio;
    }

    public void setLiturgiaPrevio(Liturgia liturgiaPrevio) {
        this.liturgiaPrevio = liturgiaPrevio;
    }

    public String getFecha() {
        return (fecha != null) ? Utils.getLongDate(fecha) : "";
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

    @SuppressWarnings("unused")
    public void setCalendarTime(int calendarTime) {
        this.calendarTime = calendarTime;
    }

    @SuppressWarnings("unused")
    public int getCalendarTime() {
        return calendarTime != 0 ? calendarTime : idTiempo;
    }

    @SuppressWarnings("unused")
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

    public String getTimeWithWeek() {
        Numerals ns = new Numerals(false, false, false);
        return String.format("%s. %s semana.", getTiempoNombre(), ns.toWords(idSemana, true));
    }

    public String getWeek() {
        Numerals ns = new Numerals(false, false, false);
        return String.format("%s semana.", ns.toWords(idSemana, true));
    }


    public String getTimeWithWeekAndDay() {
        return String.format("%s. %s de la %s", getTiempoNombre(), Utils.getDayName(this.idDia), getWeek());
    }

    public String getTimeWithTitleForRead() {
        return String.format("%s. %s", getTiempoNombre(), getTituloForRead());
    }

    public String getTiempoNombre() {
        return getTiempoNombre(false);

        /*
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
        }*/
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDay() {
        return this.weekDay;
    }

    @SuppressWarnings("unused")
    public int getSemana() {
        return idSemana;
    }

    @SuppressWarnings("unused")
    public void setSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    @SuppressWarnings("unused")
    public int getIdHour() {
        return idHour;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public void setHasSaint(boolean hasSaint) {
        this.hasSaint = hasSaint;
    }

    /**
     * <p>Obtiene la información Meta completa para presentar en pantalla.</p>
     *
     * @since 2021.01
     */
    public SpannableStringBuilder getForViewMisa() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(getTiempoNombre()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(getTitulo()));
        //sb.append(Utils.LS2);
        return sb;
    }

    @SuppressWarnings("unused")
    public Spanned getFechaForRead() {
        return Utils.fromHtml("<p>" + getFecha() + ".</p>");
    }

    public String getTituloForRead() {
        return String.format("%s%s", getTitulo(), ".");
    }

    @SuppressWarnings("unused")
    public int getIdTiempo() {
        return idTiempo;
    }

    @SuppressWarnings("unused")
    public void setIdTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    @SuppressWarnings("unused")
    public int getIdTipo() {
        return idTipo;
    }

    @SuppressWarnings("unused")
    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    @SuppressWarnings("unused")
    public int getIdDia() {
        return idDia;
    }

    @SuppressWarnings("unused")
    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    @SuppressWarnings("unused")
    public int getIdSemana() {
        return idSemana;
    }

    @SuppressWarnings("unused")
    public void setIdSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    @SuppressWarnings("unused")
    public int getIdColor() {
        return idColor;
    }

    @SuppressWarnings("unused")
    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    @SuppressWarnings("unused")
    public int getIdBreviario() {
        return idBreviario;
    }

    @SuppressWarnings("unused")
    public void setIdBreviario(int idBreviario) {
        this.idBreviario = idBreviario;
    }

    @SuppressWarnings("unused")
    public int getIdLecturas() {
        return idLecturas;
    }

    @SuppressWarnings("unused")
    public void setIdLecturas(int idLecturas) {
        this.idLecturas = idLecturas;
    }

    public int getIdPrevio() {
        return idPrevio;
    }

    @SuppressWarnings("unused")
    public void setIdPrevio(int idPrevio) {
        this.idPrevio = idPrevio;
    }

    @SuppressWarnings("unused")
    public int getIdTiempoPrevio() {
        return idTiempoPrevio;
    }

    @SuppressWarnings("unused")
    public void setIdTiempoPrevio(int idTiempoPrevio) {
        this.idTiempoPrevio = idTiempoPrevio;
    }

    @SuppressWarnings("unused")
    public String getTituloPrevio() {
        return tituloPrevio;
    }

    @SuppressWarnings("unused")
    public void setTituloPrevio(String tituloPrevio) {
        this.tituloPrevio = tituloPrevio;
    }

    public String getTimeForRead() {
        if (
                this.idTiempo >= 8
                        || (this.idTiempo == 1 && this.idDia >16)
                        || this.idTiempo == 2
                        || (this.idTiempo == 3 && this.idSemana == 0)

                        || this.idTiempo == 4
                        || this.idTiempo == 5
                        || this.idSemana >= 35
                        || (this.idTiempo == 6 && this.idSemana == 6 && this.idTipo < 4)
                        || (this.idTiempo == 6 && this.idSemana == 1)
                        || (this.idDia == 0)
                        || (this.idTiempo >= 1 && this.idTiempo <= 7 && this.idDia == 1)

        ) {
            return getTimeWithTitleForRead();
        }
        return getTimeWithWeekAndDay();

    }

    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (this != null) {
            sb.append(getFecha());
            sb.append(Utils.LS2);
            sb.append(Utils.toH2(getTiempoNombre()));
            sb.append(Utils.LS2);
            sb.append(Utils.toH3(getTitulo()));
        }
        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFecha());
        sb.append(".");
        /*sb.append(getTiempoNombre());
        sb.append(".");
        sb.append(getTituloForRead());*/
        sb.append(getTimeForRead());
        return sb.toString();
    }
}




