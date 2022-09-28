package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.deiverbum.app.utils.Utils;

/**
 * <p>
 *     Esta clase recoge información valiosa sobre el día litúrgico.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@IgnoreExtraProperties
public class Today {
    private Integer todayDate;
    //@Ignore
    //private Integer weekDayFK;
    public Integer liturgyFK;

    private Integer massReadingFK;
    private Integer previousFK;
    private Integer timeID;
    //@Ignore
    private int hasSaint;

    @Ignore
    public int hourID;

    @Ignore
    public Liturgy liturgyDay;

    @Ignore
    public Liturgy liturgyPrevious;
    public Integer invitatoryFK;
    public Integer saintFK;

    public Integer weekDay;
    public Integer oHymnFK;
    public Integer oPsalmodyFK;
    public Integer oVerseFK;
    public Integer oBiblicalFK;
    public Integer oPatristicFK;
    public Integer oPrayerFK;
    public Integer oTeDeum;
    public Integer lHymnFK;
    public Integer lPsalmodyFK;
    public Integer lBiblicalFK;
    public Integer lBenedictusFK;
    public Integer lIntercessionsFK;
    public Integer lPrayerFK;
    public Integer tHymnFK;
    public Integer tPsalmodyFK;
    public Integer tBiblicalFK;
    public Integer tPrayerFK;
    public Integer sHymnFK;
    public Integer sPsalmodyFK;
    public Integer sBiblicalFK;
    public Integer sPrayerFK;
    public Integer nHymnFK;
    public Integer nPsalmodyFK;
    public Integer nBiblicalFK;
    public Integer nPrayerFK;
    public Integer vHymnFK;
    public Integer vPsalmodyFK;
    public Integer vBiblicalFK;
    public Integer vMagnificatFK;
    public Integer vIntercessionsFK;
    public Integer vPrayerFK;

    public Integer getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(Integer todayDate) {
        this.todayDate = todayDate;
    }



    public Integer getMassReadingFK() {
        return massReadingFK;
    }

    public void setMassReadingFK(Integer massReadingFK) {
        this.massReadingFK = massReadingFK;
    }

    public Integer getPreviousFK() {
        return previousFK;
    }

    public void setPreviousFK(Integer previousFK) {
        this.previousFK = previousFK;
    }

    public Integer getTimeID() {
        return timeID;
    }

    public void setTimeID(Integer timeID) {
        this.timeID = timeID;
    }

    public Today() {
    }

    public Integer getHoy() {
        return todayDate;
    }

    public void setHoy(Integer hoy) {
        this.todayDate = hoy;
    }

    public LiturgyTime getFinalTime(){
        return liturgyPrevious==null ? liturgyDay.getLiturgyTime() : liturgyPrevious.getLiturgyTime();
    }


    public Integer getMLecturasFK() {
        return massReadingFK;
    }

    public void setMLecturasFK(Integer mLecturasFK) {
        this.massReadingFK = mLecturasFK;
    }

    public Integer getPrevioId() {
        return previousFK;
    }

    public void setPrevioId(Integer previo_id) {
        this.previousFK = previo_id;
    }

    public Integer getTiempoId() {
        return timeID;
    }

    public void setTiempoId(Integer tiempo_id) {
        this.timeID = tiempo_id;
    }



    public String getTituloVisperas() {
        if (liturgyPrevious != null) {
            return liturgyPrevious.getNombre().replaceAll(" I Vísperas.| I Vísperas","");
        } else {
            return liturgyDay.getNombre();
        }
    }

    public String getTitulo() {
            return (hourID==6) ?
                    getTituloVisperas(): liturgyDay.getName();
    }


    public void setTitulo(String titulo) {

        //this.titulo = (titulo != null) ? titulo : "";
    }

    public Liturgy getLiturgiaFeria() {
        return null;//liturgiaFeria;
    }

    public void setLiturgiaFeria(Liturgy liturgiaFeria) {

        //this.liturgiaFeria = liturgiaFeria;
    }

    public Liturgy getLiturgiaPrevio() {
        return liturgyPrevious;
    }

    public void setLiturgiaPrevio(Liturgy liturgiaPrevio) {

        //this.liturgiaPrevio = liturgiaPrevio;
    }

    public String getFecha() {
        return (todayDate != null) ? Utils.getLongDate(String.valueOf(todayDate)) : "";
    }

    public void setFecha(String fecha) {
        //this.fecha = fecha;
    }

    public void setTiempo(int idTiempo) {
        //this.idTiempo = idTiempo;
    }

    public String getTiempo() {
        return (liturgyPrevious!=null) ?
                liturgyPrevious.getLiturgiaTiempo().getLiturgyName(): liturgyDay.getLiturgiaTiempo().getLiturgyName();
    }
    public String getTiempoForRead() {
        return Utils.pointAtEnd(getTiempo());
    }


    @SuppressWarnings("unused")
    public void setCalendarTime(int calendarTime) {
        //this.calendarTime = calendarTime;
    }

    @SuppressWarnings("unused")
    public int getCalendarTime() {
        return 0;//calendarTime != 0 ? calendarTime : idTiempo;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDay() {
        return (this.weekDay!=null) ? weekDay : 0;
    }

    @SuppressWarnings("unused")
    public int getSemana() {
        return 0;//idSemana;
    }

    @SuppressWarnings("unused")
    public void setSemana(int idSemana) {
        //this.idSemana = idSemana;
    }

    @SuppressWarnings("unused")
    public int getIdHour() {
        return 0;//idHour;
    }

    @SuppressWarnings("unused")
    public void setIdHour(int idHour) {
        //this.idHour = idHour;
    }

    public String getMensaje() {
        return "TODO";//mensaje;
    }

    public void setMensaje(String mensaje) {
        //this.mensaje = mensaje;
    }


    public int getColor() {
        return 0;//idColor;
    }

    public void setColor(int idColor) {
        //this.idColor = idColor;
    }


    public Integer getHasSaint() {
        return this.hasSaint;
    }

    @SuppressWarnings("unused")
    public void setHasSaint(Integer hasSaint) {
        this.hasSaint = hasSaint;
    }


    @SuppressWarnings("unused")
    public String getFechaForRead() {
        return Utils.pointAtEnd(getFecha());
    }

    public String getTituloForRead() {
        return Utils.pointAtEnd(getTitulo());
    }

    @SuppressWarnings("unused")
    public int getIdTiempo() {
        return 0;//idTiempo;
    }

    @SuppressWarnings("unused")
    public void setIdTiempo(int idTiempo) {
        //this.idTiempo = idTiempo;
    }

    @SuppressWarnings("unused")
    public int getIdTipo() {
        return 0;//idTipo;
    }

    @SuppressWarnings("unused")
    public void setIdTipo(int idTipo) {
        //this.idTipo = idTipo;
    }

    @SuppressWarnings("unused")
    public int getIdDia() {
        return 0;//idDia;
    }

    @SuppressWarnings("unused")
    public void setIdDia(int idDia) {
        ///this.idDia = idDia;
    }

    @SuppressWarnings("unused")
    public int getIdSemana() {
        return 0;//idSemana;
    }

    @SuppressWarnings("unused")
    public void setIdSemana(int idSemana) {
        //this.idSemana = idSemana;
    }

    @SuppressWarnings("unused")
    public int getIdColor() {
        return 0;//idColor;
    }

    @SuppressWarnings("unused")
    public void setIdColor(int idColor) {
        //this.idColor = idColor;
    }

    @SuppressWarnings("unused")
    public int getIdBreviario() {
        return 0;//idBreviario;
    }

    @SuppressWarnings("unused")
    public void setIdBreviario(int idBreviario) {
        //this.idBreviario = idBreviario;
    }

    @SuppressWarnings("unused")
    public int getIdLecturas() {
        return 0;//idLecturas;
    }

    @SuppressWarnings("unused")
    public void setIdLecturas(int idLecturas) {
        //this.idLecturas = idLecturas;
    }

    public int getIdPrevio() {
        return 0;//idPrevio;
    }

    @SuppressWarnings("unused")
    public void setIdPrevio(int idPrevio) {
        //this.idPrevio = idPrevio;
    }

    @SuppressWarnings("unused")
    public int getIdTiempoPrevio() {
        return 0;//idTiempoPrevio;
    }

    @SuppressWarnings("unused")
    public void setIdTiempoPrevio(int idTiempoPrevio) {
        //this.idTiempoPrevio = idTiempoPrevio;
    }

    @SuppressWarnings("unused")
    public String getTituloPrevio() {
        return "TODO";//tituloPrevio;
    }

    @SuppressWarnings("unused")
    public void setTituloPrevio(String tituloPrevio) {
        //this.tituloPrevio = tituloPrevio;
    }

    public void setFeria(Liturgy feria) {
        //this.weekDayFK=feria;

    }



    public void setPrevio(Liturgy previo) {
        //this.liturgiaPrevio=previo;
    }

    public SpannableStringBuilder getTimeForRead() {
        return liturgyDay.getAllForRead();
    }

    public SpannableStringBuilder getAllForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getFecha());
        sb.append(Utils.LS2);
        sb.append(Utils.toH2(getTiempo()));
        sb.append(Utils.LS2);
        sb.append(Utils.toH3(getTitulo()));
        return sb;
    }

    public String getAllForRead() {
        StringBuilder sb = new StringBuilder();
        sb.append(Utils.pointAtEnd(getFecha()));
        sb.append(getTiempoForRead());
        sb.append(getTituloForRead());

        //sb.append(liturgyDay.getAllForRead());
        return sb.toString();
    }

}




