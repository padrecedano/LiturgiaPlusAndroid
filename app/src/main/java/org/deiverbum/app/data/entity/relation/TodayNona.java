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
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Prayer;
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
            entity = LiturgyEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime feria;

    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "nHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "nPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmodyAll salmodia;

    @Relation(
            entity = LHPsalmodyEntity.class,
            parentColumn = "nPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "nBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "nPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;

    public Today getToday() {
        Today dm = new Today();
        dm.liturgyDay = feria.getDomainModel();
        dm.setTodayDate(today.getHoy());
        return dm;
    }

    public Liturgy getDomainModel() {
        Liturgy dm = feria.getDomainModel();
        BreviaryHour bh = new BreviaryHour();
        Intermedia hi = new Intermedia();
        dm.typeID = 5;
        //hi.setHourId(5);
        hi.setToday(getToday());
        hi.setHimno(getHimno());
        hi.setSalmodia(getSalmodia());
        hi.setLecturaBreve(getBiblica());
        hi.setOracion(getOracion());
        bh.setIntermedia(hi);
        dm.setBreviaryHour(bh);
        return dm;
    }

    public LHHymn getHimno() {
        return himno.getDomainModel();
    }

    public BiblicalShort getBiblica() {
        return biblica.getDomainModel(today.getTiempoId());
    }

    public org.deiverbum.app.model.LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public Today getDomainModelToday() {
        Liturgy dm = feria.getDomainModel();
        BreviaryHour bh = new BreviaryHour();
        Intermedia hi = new Intermedia();
        Today dmToday = getToday();
        dm.typeID = 5;
        hi.setTypeId(5);
        hi.setHimno(getHimno());
        hi.setSalmodia(getSalmodia());
        hi.setLecturaBreve(getBiblica());
        hi.setOracion(getOracion());
        bh.setIntermedia(hi);
        dm.setBreviaryHour(bh);
        dmToday.liturgyDay = dm;
        return dmToday;
    }

}
