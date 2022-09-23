package org.deiverbum.app.data.entity;

import android.util.Log;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHGospelCanticle_;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.Visperas;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayVisperas {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SaintWithAll santo;

    @Relation(
            entity = LHInvitatoryJoinEntity.class,
            parentColumn = "invitatoryFK",
            entityColumn = "groupID"
    )
    public LHInvitatoryAll invitatorio;

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
    public org.deiverbum.app.data.entity.LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "vPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

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
            entity = LHGospelCanticleEntity.class,
            parentColumn = "vMagnificatFK",
            entityColumn = "groupID"
    )
    public LHGospelCanticleWithAntiphon magnificat;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithLecturas> lecturas;



    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

//TODO incluir algo como hasPriority en TodayEntity
    public BiblicalShort getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public LHGospelCanticle_ getMagnificat(){
        return  magnificat.getDomainModel(6);
    }

    public LHIntercession getPreces(){
        return  lhIntercessionsDM.getDomainModel();
    }

    public Saint getSanto(){
        return  santo.getDomainModelLH();
    }

    public LHInvitatory getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public org.deiverbum.app.model.LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public Today getToday(){
        Today dm = new Today();
        dm.hourID=6;
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(false);
        return dm;
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        dm.typeID=6;
        dm.setHoy(getToday());
        BreviaryHour bh=new BreviaryHour();
        Visperas visperas=new Visperas();
        visperas.setHoy(getToday());
        visperas.setInvitatorio(getInvitatorio());
        visperas.setSanto(getSanto());
        visperas.setHimno(getHimno());
        visperas.setSalmodia(getSalmodia());
        visperas.setLecturaBreve(getBiblica());
        visperas.setMagnificat(getMagnificat());
        visperas.setPreces(getPreces());
        visperas.setOracion(getOracion());
        bh.setVisperas(visperas);
        dm.setBreviaryHour(bh);
        return dm;
    }

/*
    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
dm.hourID=6;
        dm.setTodayDate(today.getHoy());
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

    public Visperas getDomainModel(){
        Visperas dm=new Visperas();
        dm.setHourId(6);
        dm.setHoy(getToday());
        //dm.setSanto(santo.getDomainModelLH());
        dm.setHimno(getHimno());
        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setMagnificat(getMagnificat());
        dm.setPreces(getPreces());
        dm.setOracion(getOracion());
        return dm;
    }
    */
}
