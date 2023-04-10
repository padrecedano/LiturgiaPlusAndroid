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
public class TodayCompletas {

    @Embedded
    public TodayEntity today;


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
    public LHPsalmodyAll salmodia;

    @Relation(
            entity = LHPsalmodyEntity.class,
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

    public Today getToday() {
        Today dm = new Today();
        int wd = today.weekDay;
        dm.setWeekDay(wd - 1);
        dm.liturgyDay = feria.getDomainModel();
        dm.liturgyPrevious = today.previoId > 1 ? previo.getDomainModel() : null;
        dm.setTimeID(today.tiempoId);
        dm.setTodayDate(today.getHoy());
        return dm;
    }

    public Liturgy getDomainModel() {
        Liturgy dm = feria.getDomainModel();
        dm.typeID = 7;
        dm.setToday(getToday());
        return dm;
    }

    public LHHymn getHimno() {
        return himno.getDomainModel();
    }

    public BiblicalShort getBiblica() {
        return biblica.getDomainModel(today.getTiempoId());
    }

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

}
