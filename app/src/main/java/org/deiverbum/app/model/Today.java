package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private Integer weekDayFK;
    public Integer liturgyFK;

    private Integer massReadingFK;
    private Integer previousFK;
    private Integer timeID;
    @Ignore
    private Integer version;
    public Integer invitatoryFK;
    public Integer saintFK;
    @Ignore
    public Integer oficioFK;
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

    public Integer getWeekDayFK() {
        return weekDayFK;
    }

    public void setWeekDayFK(Integer weekDayFK) {
        this.weekDayFK = weekDayFK;
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
//    private Integer olSalmos;

    //private HashMap<String,String> lh;

    public Today() {
    }

    public Integer getHoy() {
        return todayDate;
    }

    public void setHoy(Integer hoy) {
        this.todayDate = hoy;
    }

    public Integer getFeriaFK() {
        return weekDayFK;
    }

    public void setFeriaFK(Integer feriaFK) {
        this.weekDayFK = feriaFK;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

 /*
    public Integer getOlSalmos() {
        return olSalmos;
    }

    public void setOlSalmos(Integer olSalmos) {
        this.olSalmos = olSalmos;
    }
*/
    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append("getTitulo()");
        sb.append(LS2);
        sb.append(todayDate.toString());
        sb.append(LS2);
        sb.append(weekDayFK.toString());
        return sb;
    }
}




