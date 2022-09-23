package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayNona {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SaintEntity santo;


    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "nHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "nBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "nPsalmodyFK",
            entityColumn = "groupID"
    )
    public org.deiverbum.app.data.entity.LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "nPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "nPrayerFK",
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
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        return dm;
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        BreviaryHour bh=new BreviaryHour();
        Intermedia hi=new Intermedia();
        dm.typeID=5;
        hi.setHourId(5);
        hi.setHoy(getToday());
        hi.setHimno(getHimno());
        hi.setSalmodia(getSalmodia());
        hi.setLecturaBreve(getBiblica());
        hi.setOracion(getOracion());
        bh.setIntermedia(hi);
        dm.setBreviaryHour(bh);
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


    public org.deiverbum.app.model.LHPsalmody getSalmodia() {

        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }


}
