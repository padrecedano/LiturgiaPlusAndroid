package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
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
            entity = LiturgiaEntity.class,
            parentColumn = "weekDayFK",
            entityColumn = "liturgyID"
    )
    public LiturgiaWithTiempo feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previousFK",
            entityColumn = "liturgyID"
    )
    public LiturgiaWithTiempo previo;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SantoWithAll santo;

    @Relation(
            entity = LHInvitatorioJoinEntity.class,
            parentColumn = "invitatoryFK",
            entityColumn = "groupID"
    )
    public InvitatorioAll invitatorio;

    @Relation(
            entity = LHHimnoJoinEntity.class,
            parentColumn = "oHymnFK",
            entityColumn = "groupID"
    )
    public HimnoWithAll himno;

    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "oPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "oPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHOficioVersoJoinEntity.class,
            parentColumn = "oVerseFK",
            entityColumn = "groupID"
    )
    public OficioVersoAll oficioVerso;


    @Relation(
            entity = LHBiblicaOficioJoinEntity.class,
            parentColumn = "oBiblicalFK",
            entityColumn = "groupID"
    )
    public BiblicaOficioAll biblicas;
/*
    @Relation(
            entity = LHPatristicaOficioEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupFK"
    )
    public LHPatristica patristica;
*/
/*
    @Relation(
            entity = LHPatristicaOficioJoinEntity.class,
            parentColumn = "oPatristicFK",
            entityColumn = "groupID"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;
*/
    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "oPrayerFK",
            entityColumn = "groupID"
    )
    public LHOracion lhOracion;

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

    public List<BiblicaOficio> getBiblicas() {
        return biblicas.getDomainModel(today.getTiempoId());
    }
    public Patristica getPatristica(){
        return null;//patristica.getDomainModel(today.getTiempoId());
    }

    public List<Patristica> getPatristicas() {
        List<Patristica> theList = new ArrayList<>();
        /*
        for (PatristicaOficioWithResponsorio item :
                patristicaOficioWithResponsorio) {
            theList.add(item.getDomainModelOficio(today.getTiempoId()));
        }*/
        return theList;
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
        //TeDeum teDeum=new TeDeum(theEntity.today.oTeDeum);
        dm.setSalmodia(getSalmodia());

        dm.setTeDeum(new TeDeum(today.oTeDeum));
        dm.setOracion((lhOracion.getDomainModel()));

        //dm.setLecturaBreve(getBiblica());
        //dm.setBenedictus(getBenedictus());
        //dm.setPreces(getPreces());
        //dm.setOracion("abc");
        return dm;
    }

}
