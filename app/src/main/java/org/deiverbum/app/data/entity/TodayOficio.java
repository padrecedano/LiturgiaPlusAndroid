package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.Biblica;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;

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
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId"
    )
    public SantoEntity santo;

    @Relation(
            entity = InvitatorioEntity.class,
            parentColumn = "invitatorioFK",
            entityColumn = "grupoId"
    )
    public InvitatorioWithAntifona invitatorio;

    @Relation(
            entity = HimnoEntity.class,
            parentColumn = "oHimnoFK",
            entityColumn = "himnoId"
    )
    public HimnoEntity himno;

    @Relation(
            entity = LHBiblicaOficioEntity.class,
            parentColumn = "oBiblicaFK",
            entityColumn = "grupoFK"
    )
    public LHBiblicaOficioEntity biblicaOficio;

    @Relation(
            entity = LHPatristicaEntity.class,
            parentColumn = "oPatristicaFK",
            entityColumn = "patristicaId"
    )
    public LHPatristica patristica;

    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "oSalmodiaFK",
            entityColumn = "grupoId"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "oSalmodiaFK",
            entityColumn = "grupoFK"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "oOracionFK",
            entityColumn = "liturgiaId"
    )
    public LHOracion lhOracion;

    @Relation(
            entity = LHOficioResponsorioEntity.class,
            parentColumn = "oResponsorioFK",
            entityColumn = "responsorioId"
    )
    public LHOficioResponsorioEntity lhOficioResponsorio;

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

    //@Embedded public LHOracion address;

    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.previoId!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.hoy));
        theModel.setColor(feria.colorFK);
        theModel.setHasSaint(true);
        theModel.setIdHour(1);
        theModel.setCalendarTime(feria.colorFK);
        theModel.setIdBreviario(feria.colorFK);
        theModel.setIdDia(feria.colorFK);
        theModel.setIdLecturas(today.mLecturasFK);
        theModel.setIdPrevio(1);
        theModel.setIdSemana(1);
        theModel.setIdTiempo(1);
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

    public Biblica getBiblicaOficio(){
        return null;//biblicaOficio.getDomainModelOficio(today.getTiempoId());
    }

    public Patristica getPatristica(){
        return patristica.getDomainModel(today.getTiempoId());
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
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

    public Santo getSanto(){
        return  santo.getDomainModel(false);
    }


}
