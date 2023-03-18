package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LHHymnJoinEntity;
import org.deiverbum.app.data.entity.LHInvitatoryJoinEntity;
import org.deiverbum.app.data.entity.LHOfficeBiblicalJoinEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHOfficeVerseJoinEntity;
import org.deiverbum.app.data.entity.LHPrayerEntity;
import org.deiverbum.app.data.entity.LHPsalmodyJoinEntity;
import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgySaintJoinEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.LHHymn;
import org.deiverbum.app.model.LHInvitatory;
import org.deiverbum.app.model.LHOfficeBiblical;
import org.deiverbum.app.model.LHOfficeOfReading;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioEaster;
import org.deiverbum.app.model.TeDeum;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodayOficio {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID"
    )
    public LiturgyWithTime feria;

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
            parentColumn = "oHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "oPsalmodyFK",
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
    //public LHOfficeEasterJoin biblicasE;
    public LHOfficeEasterJoin biblicasE;


    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public LHOfficePatristic patristica;

    @Relation(
            entity = LHOfficePatristicEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "oPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;


    public LHInvitatory getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public LHHymn getHimno(){
        return himno.getDomainModel();
    }

    public LHPsalmody getSalmodia() {
        return salmodia.getDomainModel();
    }

    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        //dm.setMLecturasFK(today.mLecturasFK);
        dm.oBiblicalFK=today.oBiblicaFK;
        return dm;
    }

    public List<LHOfficeBiblical> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<org.deiverbum.app.model.LHOfficePatristic> getPatristicas() {
        List<org.deiverbum.app.model.LHOfficePatristic> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }

    public Today getDomainModelToday() {
        Liturgy dm = feria.getDomainModel();
        dm.typeID = 1;
        Today dmToday = getToday();
        dm.setToday(dmToday);
        BreviaryHour bh = new BreviaryHour();
        if (today.oBiblicaFK==600010101){
            OficioEaster oEaster = new OficioEaster();
            oEaster.setOficioLecturas(biblicasE.getDomainModel());
            bh.setOficioEaster(oEaster);
        }else {
            Oficio oficio = new Oficio();
            LHOfficeOfReading ol=new LHOfficeOfReading();
            ol.setBiblica(getBiblicas());
            ol.setPatristica(getPatristicas());
            ol.setResponsorio(getOficioVerso());
            if(dmToday.getHasSaint()==1 && saint!=null){
                oficio.setSanto(saint.getDomainModel());
            }
            oficio.setInvitatorio(getInvitatorio());
            oficio.setHimno(getHimno());
            oficio.setOficioLecturas(ol);
            oficio.setSalmodia(getSalmodia());
            oficio.setTeDeum(new TeDeum(today.oTeDeum));
            oficio.setOracion((lhPrayerAll.getDomainModel()));
            bh.setOficio(oficio);
        }
        dm.setBreviaryHour(bh);
        dmToday.liturgyDay=dm;
        return dmToday;
    }

}