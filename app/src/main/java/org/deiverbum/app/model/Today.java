package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.Utils;

/**
 * <p>
 *     Esta clase recoge información valiosa sobre el día litúrgico.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

//@SuppressWarnings("SameReturnValue")
public class Today {
    public Integer todayDate;
    public Integer weekDay;
    public Integer timeID;
    @Ignore
    public Integer weekDayFK;
    public Integer liturgyFK;
    public Integer previousFK;
    public Integer massReadingFK;
    public Integer invitatoryFK;

    //@Ignore
    public Integer hasSaint;
    public Integer saintFK;


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

    @Ignore
    public Liturgy liturgyDay;
    @Ignore
    public Liturgy liturgyPrevious;
    @Ignore
    public LiturgyTime liturgyTime;

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
            return (liturgyDay.typeID==6) ?
                    getTituloVisperas(): liturgyDay.getName();
    }

    public String getTituloForRead() {
        return (liturgyDay.typeID==6) ?
                getTituloVisperas(): liturgyDay.getTitleForRead();
    }

    public String getFecha() {
        return (todayDate != null) ? Utils.getLongDate(String.valueOf(todayDate)) : "";
    }

    public String getTiempo() {
        return (liturgyDay.typeID==6 && liturgyPrevious!=null) ?
                liturgyPrevious.getLiturgiaTiempo().getLiturgyName(): liturgyDay.getLiturgiaTiempo().getLiturgyName();
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getWeekDay() {
        return (this.weekDay!=null) ? weekDay : 0;
    }

    public int getHasSaint() {
        return this.hasSaint;
    }

    public boolean hasSaintToday() {
        return this.hasSaint == 1;
    }


    public SpannableStringBuilder getAllForView(boolean hasInvitatory) {
        liturgyDay.setHasSaint(hasSaintToday());
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(Utils.LS);
            sb.append(getFecha());
            sb.append(Utils.LS2);
            sb.append(Utils.toH2(getTiempo()));
            sb.append(Utils.LS2);
            sb.append(Utils.toH3(getTitulo()));
            //liturgyDay.today.previousFK
            //sb.append(liturgyDay.getForView(hasInvitatory,previousFK));
            if (liturgyDay.typeID == 0) {
                if (oBiblicalFK==600010101){
                    sb.append(liturgyDay.getBreviaryHour().getOficioEaster().getForView());
                } else {
                    sb.append(liturgyDay.getBreviaryHour().getMixtoForView(liturgyDay.liturgyTime));//.getForView(liturgyDay.liturgyTime));
                }
            }
            if (liturgyDay.typeID == 1) {
                if (oBiblicalFK==600010101){
                    sb.append(liturgyDay.getBreviaryHour().getOficioEaster().getForView());
                }else {
                    sb.append(liturgyDay.getBreviaryHour().getOficio(hasInvitatory).getForView(liturgyDay.liturgyTime, hasSaintToday()));
                }
            }
            if (liturgyDay.typeID == 2) {
                sb.append(liturgyDay.getBreviaryHour().getLaudes(hasInvitatory).getForView(liturgyDay.liturgyTime));
            }
            if (liturgyDay.typeID == 3 || liturgyDay.typeID == 4 || liturgyDay.typeID == 5) {
                sb.append(liturgyDay.getBreviaryHour().getIntermedia().getForView(liturgyDay.liturgyTime,liturgyDay.typeID));
            }
            if (liturgyDay.typeID == 6) {
                sb.append(liturgyDay.getBreviaryHour().getVisperas().getForView(liturgyDay.liturgyTime));
            }
            if (liturgyDay.typeID == 7) {
                sb.append(liturgyDay.getBreviaryHour().getCompletas().getAllForView(liturgyDay.liturgyTime));
            }

        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public SpannableStringBuilder getSingleForView() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            sb.append(Utils.LS);
            sb.append(getFecha());
            sb.append(Utils.LS2);
            sb.append(Utils.toH2(getTiempo()));
            sb.append(Utils.LS2);
            sb.append(Utils.toH3(getTitulo()));
            //liturgyDay.today.previousFK
            //sb.append(liturgyDay.getAllForView());
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getAllForRead(boolean hasInvitatory) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(Utils.pointAtEnd(getFecha()));
            sb.append(getTituloForRead());
            //sb.append(liturgyDay.getForRead());
            if (liturgyDay.typeID == 0) {
                if (oBiblicalFK==600010101){
                    sb.append(liturgyDay.getBreviaryHour().getOficioEaster().getForRead());
                } else {
                    //TODO
                    sb.append(liturgyDay.getBreviaryHour().getMixtoForRead());
                }
            }
            if (liturgyDay.typeID == 1) {
                if (oBiblicalFK==600010101){
                    sb.append(liturgyDay.getBreviaryHour().getOficioEaster().getForRead());
                }else {
                    sb.append(liturgyDay.getBreviaryHour().getOficio(hasInvitatory).getForRead());
                }
            }
            if (liturgyDay.typeID == 2) {
                sb.append(liturgyDay.getBreviaryHour().getLaudes(hasInvitatory).getForRead());
            }
            if (liturgyDay.typeID == 3 || liturgyDay.typeID == 4 || liturgyDay.typeID == 5) {
                sb.append(liturgyDay.getBreviaryHour().getIntermedia().getForRead());
            }
            if (liturgyDay.typeID == 6) {
                sb.append(liturgyDay.getBreviaryHour().getVisperas().getAllForRead(previousFK));
            }
            if (liturgyDay.typeID == 7) {
                sb.append(liturgyDay.getBreviaryHour().getCompletas().getAllForView(liturgyDay.liturgyTime));
            }

        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getSingleForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(Utils.pointAtEnd(getFecha()));
            sb.append(getTituloForRead());
            //sb.append(liturgyDay.getForRead());

        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }
}



