package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;
import org.deiverbum.app.model.TeDeum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayOficio {

    @Embedded
    public Today today;

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
            parentColumn = "oHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "oPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "oPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHOficceVerseJoinEntity.class,
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

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        //theModel.setColor(feria.colorFK);
        theModel.setHasSaint(true);
        theModel.setIdHour(1);
        //theModel.setCalendarTime(feria.colorFK);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(1);
        theModel.setIdTiempoPrevio(1);
        //theModel.setTitulo(feria.nombre);
        return theModel;
    }

    public Santo getSanto(){
        return  santo.getDomainModelLH();
    }

    public Invitatorio getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public Himno getHimno(){
        return himno.getDomainModel();
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }

    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Hoy getToday(){
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

    public List<BiblicaOficio> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }

    public List<Patristica> getPatristicas() {
        List<Patristica> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }
        return theList;
    }

    public Oficio getDomainModel(){
        Oficio dm=new Oficio();
        dm.setHoy(getToday());
        dm.setSanto(getSanto());
        dm.setInvitatorio(getInvitatorio());
        dm.setHimno(getHimno());
        OficioLecturas ol=new OficioLecturas();
        ol.setBiblica(getBiblicas());
        ol.setPatristica(getPatristicas());
        ol.setResponsorio(getOficioVerso());
        dm.setOficioLecturas(ol);
        dm.setSalmodia(getSalmodia());
        dm.setTeDeum(new TeDeum(today.oTeDeum));
        dm.setOracion((lhPrayerAll.getDomainModel()));
        return dm;
    }

}
