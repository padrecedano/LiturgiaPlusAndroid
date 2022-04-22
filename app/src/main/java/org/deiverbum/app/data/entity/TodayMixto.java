package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Evangelio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmo;
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
public class TodayMixto {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId" //liturgiaId
    )
    public SantoEntity santo;

    @Relation(
            entity = InvitatorioEntity.class,
            parentColumn = "invitatorioFK",
            entityColumn = "invitatorioId"
    )
    public InvitatorioWithAntifona invitatorio;

    @Relation(
            entity = HimnoEntity.class,
            parentColumn = "lHimnoFK",
            entityColumn = "himnoId"
    )
    public HimnoEntity himno;

    @Relation(
            entity = LHBiblicaBreveEntity.class,
            parentColumn = "lBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaBreveWithResponsorio biblica;


    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "lSalmodiaFK",
            entityColumn = "grupoId"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "lSalmodiaFK",
            entityColumn = "grupoFK"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHOficioResponsorioEntity.class,
            parentColumn = "oResponsorioFK",
            entityColumn = "responsorioId"
    )
    public LHOficioResponsorioEntity lhOficioResponsorio;

    @Relation(
            entity = LHBiblicaOficioEntity.class,
            parentColumn = "oBiblicaFK",
            entityColumn = "grupoFK"
    )
    public List<BiblicaOficioWithResponsorio> biblicaOficioWithResponsorio;


    @Relation(
            entity = LHPatristicaOficioEntity.class,
            parentColumn = "oPatristicaFK",
            entityColumn = "grupoFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;
/*
    @Relation(
            entity = LHPatristicaEntity.class,
            parentColumn = "oPatristicaFK",
            entityColumn = "patristicaId"
    )
    public LHPatristica lhPatristica;
*/
    @Relation(
            entity = LHPrecesJoinEntity.class,
            parentColumn = "lPrecesFK",
            entityColumn = "grupoId"

    )
    public LHPreces lhPreces;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "lOracionFK",
            entityColumn = "liturgiaId"
    )
    public LHOracion lhOracion;



    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaEntity previo;

    @Relation(
            entity = LHCanticoEvangelicoEntity.class,
            parentColumn = "lBenedictusFK",
            entityColumn = "grupoId"
    )
    public CanticoEvangelicoWithAntifona benedictus;

    @Relation(
            entity = MisaLecturaEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "liturgiaFK"
    )
    public MisaWithLecturas lecturas;

    public MisaLecturas getMisaLecturas(){
        MisaLecturas theModel=new MisaLecturas();
        List<Evangelio> listModel = new ArrayList<>();
/*
        for (MisaWithLecturas item : lecturas) {
            listModel.add(item.getDomainModel());
        }*/
        listModel.add(lecturas.getDomainModelEvangelio());

        theModel.setEvangelio(listModel);
        return theModel;
    }


    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        theModel.setColor(feria.colorFK);
        theModel.setIdHour(2);
        theModel.setCalendarTime(feria.colorFK);
        theModel.setHasSaint(true);
        theModel.setIdBreviario(feria.colorFK);
        theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(9);
        theModel.setIdTiempoPrevio(1);
        theModel.setTitulo(feria.nombre);
        return theModel;
    }
    public Himno getHimno(){
        Himno modelHimno = new Himno();
        modelHimno.setTexto(himno.getHimno());
        return modelHimno;
    }

    public String getOficioResponsorio(){
        return lhOficioResponsorio.getResponsorio();
    }

    public List<BiblicaOficio> getBiblicas() {
        final List<BiblicaOficio> theList = new ArrayList<>();
        for (BiblicaOficioWithResponsorio bi : biblicaOficioWithResponsorio) {
            final BiblicaOficio s = new BiblicaOficio();
            theList.add(bi.getDomainModelOficio(today.getTiempoId()));
            //bi.getDomainModelOficio()
        }
        return theList;
    }

    public List<Patristica> getPatristicas() {
        List<Patristica> theList = new ArrayList<>();
        for (PatristicaOficioWithResponsorio bi :
                patristicaOficioWithResponsorio) {
            //final BiblicaOficio s = new BiblicaOficio();
            theList.add(bi.getDomainModelOficio(today.getTiempoId()));
            //bi.getDomainModelOficio()
        }
        return theList;
    }
    public Biblica getOficioBiblica(){
        return null;//biblicaOficioWithResponsorio.getDomainModelOficio(today
        // .getTiempoId());
    }

    public Patristica getOficioPatristica(){
        return null;//lhPatristica.getDomainModel(today.getTiempoId());
    }

//TODO incluir algo como hasPriority en Today
    public BiblicaBreve getBiblicaBreve(){
        return  biblica.getDomainModelBreve(today.getTiempoId());
    }

    public CanticoEvangelico getBenedictus(){
        return  benedictus.getDomainModel(2);
    }

    public Preces getPreces(){
        return  lhPreces.getDomainModel();
    }

    public Santo getSanto(){
        return  santo.getDomainModel(false);
    }


    public Invitatorio getInvitatorio() {
        Invitatorio theModel=new Invitatorio();
        theModel.setId(invitatorio.getId());
        theModel.setAntifona(invitatorio.getAntifona());
        return theModel;
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Oracion getOracion() {
        return lhOracion.getDomainModel();
    }

    public TeDeum getTeDeum() {
        TeDeum theModel=new TeDeum();
        theModel.setStatus(today.getTeDeum());
        return theModel;
    }
}
