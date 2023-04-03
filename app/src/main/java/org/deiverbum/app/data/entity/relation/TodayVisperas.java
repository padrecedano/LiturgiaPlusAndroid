package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.Visperas;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodayVisperas {

    @Embedded
    public TodayEntity today;

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

    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "vHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "vBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "vPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmodyAll salmodia;

    @Relation(
            entity = LHIntercessionsJoinEntity.class,
            parentColumn = "vIntercessionsFK",
            entityColumn = "groupID"

    )
    public LHIntercessionsDM lhIntercessionsDM;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "vPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;

    @Relation(
            entity = LHGospelCanticleEntity.class,
            parentColumn = "vMagnificatFK",
            entityColumn = "groupID"
    )
    public LHGospelCanticleWithAntiphon magnificat;

    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

//TODO incluir algo como hasPriority en TodayEntity
    public BiblicalShort getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public LHGospelCanticle getMagnificat(){
        return  magnificat.getDomainModel(6);
    }

    public LHIntercession getPreces(){
        return  lhIntercessionsDM.getDomainModel();
    }

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyDay.typeID=6;
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        int previousFK=today.getPrevioId();
        previousFK = previousFK == 1 ? 0 : previousFK;
        dm.setPreviousFK(previousFK);
        return dm;
    }

    public Today getDomainModelToday(){
        Liturgy dm= feria.getDomainModel();
        Today dmToday=getToday();
        dm.typeID=6;
        dm.setToday(getToday());
        BreviaryHour bh=new BreviaryHour();
        Visperas visperas=new Visperas();
        visperas.setIsPrevious(dmToday.previousFK);
        visperas.setToday(getToday());
        visperas.setHimno(getHimno());
        visperas.setSalmodia(getSalmodia());
        visperas.setLecturaBreve(getBiblica());
        visperas.setGospelCanticle(getMagnificat());
        visperas.setPreces(getPreces());
        visperas.setOracion(getOracion());
        bh.setVisperas(visperas);

        dm.setBreviaryHour(bh);
        dmToday.liturgyDay=dm;
        return dmToday;
    }

}
