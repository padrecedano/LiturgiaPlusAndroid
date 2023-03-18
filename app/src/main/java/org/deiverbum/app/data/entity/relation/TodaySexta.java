package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Today;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodaySexta {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "sHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "sBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "sPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmodyAll salmodia;

    @Relation(
            entity = LHPsalmodyEntity.class,
            parentColumn = "sPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "sPrayerFK",
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
        dm.typeID=4;
        dm.setToday(getToday());

        hi.setHourId(4);
        hi.setToday(getToday());
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

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public Today getDomainModelToday(){
        Liturgy dm= feria.getDomainModel();
        BreviaryHour bh=new BreviaryHour();
        Intermedia hi=new Intermedia();
        Today dmToday=getToday();
        dm.typeID=4;
        hi.setTypeId(4);
        hi.setHimno(getHimno());
        hi.setSalmodia(getSalmodia());
        hi.setLecturaBreve(getBiblica());
        hi.setOracion(getOracion());
        bh.setIntermedia(hi);
        dm.setBreviaryHour(bh);
        dmToday.liturgyDay=dm;
        return dmToday;
    }

}
