package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Preces;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayLaudes {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId" //liturgiaId
    )
    public SantoWithAll santo;

    @Relation(
            entity = LHInvitatorioJoinEntity.class,
            parentColumn = "invitatorioFK",
            entityColumn = "grupoId"
    )
    public InvitatorioAll invitatorio;

    @Relation(
            entity = LHHimnoJoinEntity.class,
            parentColumn = "lHimnoFK",
            entityColumn = "grupoId"
    )
    public HimnoWithAll himno;

    @Relation(
            entity = LHBiblicaBreveEntity.class,
            parentColumn = "lBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaBreveWithResponsorio biblica;

    @Relation(
            entity = LHBiblicaBreveEntity.class,
            parentColumn = "lBiblicaFK",
            entityColumn = "grupoId"
    )
    public LHBiblicaBreveEntity biblicaa;

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
    public List<MisaWithLecturas> lecturas;

    public MisaLecturas getMisaLecturas(){
        MisaLecturas theModel=new MisaLecturas();
        List<BiblicaMisa> listModel = new ArrayList<>();

        for (MisaWithLecturas item : lecturas) {
            listModel.add(item.getDomainModel());
        }
            theModel.setLecturas(listModel);
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
        //Himno modelHimno = new Himno();
        //modelHimno.setTexto(himno.getDomainModel());
        return himno.getDomainModel();
    }

//TODO incluir algo como hasPriority en Today
    public BiblicaBreve getBiblica(){

        return  biblica.getDomainModelBreve(today.getTiempoId());
    }

    public CanticoEvangelico getBenedictus(){
        return  benedictus.getDomainModel(2);
    }

    public Preces getPreces(){
        return  lhPreces.getDomainModel();
    }

    public Santo getSanto(){
        return  santo.getDomainModel();
    }


    public Invitatorio getInvitatorio() {
        //Invitatorio theModel=new Invitatorio();
        //theModel.setId(invitatorio.getId());
        //theModel.setAntifona(invitatorio.getAntifona());
        return invitatorio.getDomainModel();
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Oracion getOracion() {
        return lhOracion.getDomainModel();
    }

    public Laudes getDomainModel(){
        Laudes dm=new Laudes();
        dm.setMetaLiturgia(getMetaLiturgia());
        dm.setSanto(santo.getDomainModel());

        dm.setInvitatorio(getInvitatorio());
        dm.setHimno(getHimno());

        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setBenedictus(getBenedictus());
        dm.setPreces(getPreces());
        dm.setOracion(getOracion());
        return dm;

    }
}
