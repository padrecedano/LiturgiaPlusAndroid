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
@SuppressWarnings("SameReturnValue")
@IgnoreExtraProperties
public class Today {
    private Integer todayDate;
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

    public Today() {
    }

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

    public void setHasSaint(int hasSaint) {
        this.hasSaint = hasSaint;
    }

    public Integer getHoy() {
        return todayDate;
    }

    public void setHoy(Integer hoy) {
        this.todayDate = hoy;
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

    public Liturgy getLiturgiaPrevio() {
        return liturgyPrevious;
    }

    public String getFecha() {
        return (todayDate != null) ? Utils.getLongDate(String.valueOf(todayDate)) : "";
    }

    public String getTiempo() {
        return (liturgyPrevious!=null) ?
                liturgyPrevious.getLiturgiaTiempo().getLiturgyName(): liturgyDay.getLiturgiaTiempo().getLiturgyName();
    }
    public String getTiempoForRead() {
        return Utils.pointAtEnd(getTiempo());
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDay() {
        return (this.weekDay!=null) ? weekDay : 0;
    }

    public Integer getHasSaint() {
        return this.hasSaint;
    }

    public String getTituloForRead() {
        return Utils.pointAtEnd(getTitulo());
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
        return Utils.pointAtEnd(getFecha()) +
                getTiempoForRead() +
                getTituloForRead();
    }
}



