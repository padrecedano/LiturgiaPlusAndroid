package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.utils.Utils;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayCompletas {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID" //liturgiaId
    )
    public SaintEntity santo;


    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "tHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "tBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "tPsalmodyFK",
            entityColumn = "groupID"
    )
    public org.deiverbum.app.data.entity.LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "tPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "tPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;



    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime feria;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "previousFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime previo;


    public Today getToday(){
        Today dm = new Today();
        int wd=today.weekDay==null?Utils.getDayOfWeek(today.getHoy()):today.weekDay;
        dm.setWeekDay(wd-1);
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        return dm;
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        //Completas c=new Completas();
        dm.typeID=7;
        //c.setHourId(7);

        dm.setHoy(getToday());
//bh.setCompletas(c);
        //dm.setBreviaryHour(bh);
        return dm;
    }

    public LHHymn getHimno(){
        return himno.getDomainModel();
    }



    public BiblicalShort getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }



    public Saint getSanto(){
        return  santo.getDomainModel(false);
    }


    public LHPsalmody getSalmodia() {

        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }


}
