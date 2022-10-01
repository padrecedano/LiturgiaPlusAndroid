package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHGospelCanticleJoinEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.Laudes;
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
public class TodayLaudes {

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
            parentColumn = "lHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "lBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "lPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmodyAll salmodia;

    @Relation(
            entity = LHIntercessionsJoinEntity.class,
            parentColumn = "lIntercessionsFK",
            entityColumn = "groupID"

    )
    public LHIntercessionsDM lhIntercessionsDM;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "lPrayerFK",
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
            entity = LHGospelCanticleJoinEntity.class,
            parentColumn = "lBenedictusFK",
            entityColumn = "groupID"
    )
    public LHGospelCanticleWithAntiphon benedictus;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public List<MisaWithLecturas> lecturas;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK"
    )
    public MisaWithComentariosRename comentarios;

    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

    public BiblicalShort getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public LHGospelCanticle getBenedictus(){
        return  benedictus.getDomainModel(2);
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
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        return dm;
    }

    public Liturgy getDomainModel(){
        Liturgy dm= feria.getDomainModel();
        dm.typeID=2;
        dm.setHoy(getToday());
        BreviaryHour bh=new BreviaryHour();
        Laudes laudes=new Laudes();
        laudes.setHoy(getToday());
        laudes.setInvitatorio(getInvitatorio());
        laudes.setSanto(getSanto());
        laudes.setHimno(getHimno());
        laudes.setSalmodia(getSalmodia());
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        bh.setLaudes(laudes);
        dm.setBreviaryHour(bh);
        return dm;
    }
}
