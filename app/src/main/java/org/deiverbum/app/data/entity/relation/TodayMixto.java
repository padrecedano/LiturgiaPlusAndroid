package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHGospelCanticleEntity;
import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHIntercessionsJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LHReadingShortJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BiblicalShort;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHGospelCanticle;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHIntercession;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.LHOfficeBiblical;
import org.deiverbum.app.model.LHOfficeOfReading;
import org.deiverbum.app.model.LHOfficePatristic;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioEaster;
import org.deiverbum.app.model.Prayer;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodayMixto {

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
            entity = LiturgySaintJoinEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyFK"
    )
    public SaintShortWithAll saint;

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
            entity = LHOfficeVerseJoinEntity.class,
            parentColumn = "oVerseFK",
            entityColumn = "groupID"
    )
    public OficceVerseAll oficioVerso;

    @Relation(
            entity = LHOfficeBiblicalJoinEntity.class,
            parentColumn = "oBiblicalFK",
            entityColumn = "groupID"
    )
    public LHOfficeBiblicalAll biblicas;

    @Relation(
            entity = LHOfficeBiblicalJoinEntity.class,
            parentColumn = "oBiblicalFK",
            entityColumn = "groupID"
    )
    public LHOfficeEasterJoin biblicasE;

    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public LHOfficePatristicWithAll patristica;

    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;

    @Relation(
            entity = LHGospelCanticleEntity.class,
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

    public List<MassReading> getEvangelios() {
        List<MassReading> listModel = new ArrayList<>();
        for (MisaWithLecturas item : lecturas) {
            if (item.misaLectura.getOrden() >= 40) {
                listModel.add(item.getDomainModel());
            }
        }
        return listModel;
    }

    public Today getToday() {
        Today dm = new Today();
        dm.liturgyDay = feria.getDomainModel();
        dm.liturgyPrevious = today.previoId > 1 ? previo.getDomainModel() : null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        dm.oBiblicalFK = today.oBiblicaFK;
        return dm;
    }

    public LHHymn getHimno() {
        return himno.getDomainModel();
    }

    //TODO incluir algo como hasPriority en TodayEntity
    public BiblicalShort getBiblica() {
        return biblica.getDomainModel(today.getTiempoId());
    }

    public LHGospelCanticle getBenedictus() {
        return benedictus.getDomainModel(2);
    }

    public LHIntercession getPreces() {
        return lhIntercessionsDM.getDomainModel();
    }

    public LHInvitatory getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Prayer getOracion() {
        return lhPrayerAll.getDomainModel();
    }

    public List<LHOfficeBiblical> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<LHOfficePatristic> getPatristicas() {
        List<org.deiverbum.app.model.LHOfficePatristic> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }

    public String getOficioVerso() {
        return oficioVerso.getDomainModel();
    }

    public Today getDomainModelToday() {
        Liturgy dm = feria.getDomainModel();
        dm.typeID = 0;
        Today dmToday = getToday();
        dm.setToday(dmToday);
        BreviaryHour bh = new BreviaryHour();
        if (today.oBiblicaFK == 600010101) {
            OficioEaster oEaster = new OficioEaster();
            oEaster.setOficioLecturas(biblicasE.getDomainModel());
            bh.setOficioEaster(oEaster);
        } else {
            Mixto mixto = new Mixto();
            Oficio oficio = new Oficio();
            Laudes laudes = new Laudes();
            mixto.setTypeId(0);
            if (dmToday.getHasSaint() == 1 && saint != null) {
                bh.setSanto(saint.getDomainModel());
            }
            mixto.setInvitatorio(getInvitatorio());
            oficio.setInvitatorio(getInvitatorio());

            laudes.setHimno(getHimno());
            laudes.setSalmodia(getSalmodia());
            laudes.setLecturaBreve(getBiblica());
            laudes.setGospelCanticle(getBenedictus());
            laudes.setPreces(getPreces());
            laudes.setOracion(getOracion());
            LHOfficeOfReading ol = new LHOfficeOfReading();
            ol.setBiblica(getBiblicas());
            ol.setPatristica(getPatristicas());
            ol.setResponsorio(getOficioVerso());
            oficio.setOficioLecturas(ol);
            oficio.setTeDeum(new TeDeum(today.oTeDeum));
            laudes.setLecturaBreve(getBiblica());
            laudes.setGospelCanticle(getBenedictus());
            laudes.setPreces(getPreces());
            laudes.setOracion(getOracion());
            mixto.setOficio(oficio);
            mixto.setLaudes(laudes);
            mixto.setMisaLecturas(getEvangelios());
            bh.setMixto(mixto);
            bh.setOficio(oficio);
            bh.setLaudes(laudes);
        }
        dm.setBreviaryHour(bh);
        dmToday.liturgyDay = dm;
        return dmToday;
    }
}