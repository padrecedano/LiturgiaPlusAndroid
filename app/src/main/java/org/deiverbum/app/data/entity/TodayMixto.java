package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.BiblicaOficio;
import org.deiverbum.app.model.CanticoEvangelico;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Invitatorio;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.MisaLecturas;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.OficioLecturas;
import org.deiverbum.app.model.Oracion;
import org.deiverbum.app.model.Patristica;
import org.deiverbum.app.model.Preces;
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
            entity = LiturgiaEntity.class,
            parentColumn = "feriaFK",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo feria;

    @Relation(
            entity = LiturgiaEntity.class,
            parentColumn = "previoId",
            entityColumn = "liturgiaId"
    )
    public LiturgiaWithTiempo previo;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId"
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
            entity = LHBiblicaBreveJoinEntity.class,
            parentColumn = "lBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaBreveAll biblica;

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
            entity = LHOficioVersoJoinEntity.class,
            parentColumn = "oVersoFK",
            entityColumn = "grupoId"
    )
    public OficioVersoAll oficioVerso;


    @Relation(
            entity = LHBiblicaOficioJoinEntity.class,
            parentColumn = "oBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaOficioAll biblicas;

    @Relation(
            entity = LHPatristicaEntity.class,
            parentColumn = "oPatristicaFK",
            entityColumn = "patristicaId"
    )
    public LHPatristica patristica;


    @Relation(
            entity = LHPatristicaOficioEntity.class,
            parentColumn = "oPatristicaFK",
            entityColumn = "grupoFK"
    )
    public List<PatristicaOficioWithResponsorio> patristicaOficioWithResponsorio;

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


    @Relation(
            entity = LHPrecesJoinEntity.class,
            parentColumn = "lPrecesFK",
            entityColumn = "grupoId"

    )
    public LHPreces lhPreces;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "lOracionFK",
            entityColumn = "grupoId"
    )
    public LHOracion lhOracion;





    public MisaLecturas getMisaLecturas(){
        MisaLecturas theModel=new MisaLecturas();
        List<BiblicaMisa> listModel = new ArrayList<>();

        for (MisaWithLecturas item : lecturas) {
            if(item.getDomainModel().getOrden()>=40){
                listModel.add(item.getDomainModel());
            }
        }
        //listModel.add(lecturas.getDomainModelEvangelio());

        theModel.setLecturas(listModel);
        return theModel;
    }

    public Hoy getToday(){
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        //theModel.setColor(feria.getColorFK());
        dm.setIdHour(0);
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());//.setIdPrevio(1);
        //theModel.setIdSemana(1);
        //theModel.setIdTiempo(9);
        //theModel.setIdTiempoPrevio(1);
        dm.setTitulo(feria.getDomainModel().getNombre());
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        return dm;
    }


    public Himno getHimno(){
        return himno.getDomainModel();
    }

    //TODO incluir algo como hasPriority en Today
    public BiblicaBreve getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }

    public CanticoEvangelico getBenedictus(){
        return  benedictus.getDomainModel(2);
    }

    public Preces getPreces(){
        return  lhPreces.getDomainModel();
    }

    public Santo getSanto(){
        return  santo.getDomainModelLH();
    }


    public Invitatorio getInvitatorio() {
        return invitatorio.getDomainModel();
    }

    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }

    public Oracion getOracion() {
        return lhOracion.getDomainModel();
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
    public String getOficioVerso(){
        return oficioVerso.getDomainModel();
    }

    public Mixto getDomainModel(){
        Mixto dm=new Mixto();
        Laudes laudes=new Laudes();
        Oficio oficio=new Oficio();
        dm.setHoy(getToday());
        laudes.setHoy(getToday());

        if(santo!=null) {
            dm.setSanto(santo.getDomainModelLH());
        }
        dm.setInvitatorio(getInvitatorio());
        laudes.setHimno(getHimno());
        laudes.setSalmodia(getSalmodia());
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        OficioLecturas ol=new OficioLecturas();
        ol.setBiblica(getBiblicas());
        ol.setPatristica(getPatristicas());
        ol.setResponsorio(getOficioVerso());
        oficio.setOficioLecturas(ol);
        oficio.setTeDeum(new TeDeum(today.oTeDeum));
        laudes.setLecturaBreve(getBiblica());
        laudes.setBenedictus(getBenedictus());
        laudes.setPreces(getPreces());
        laudes.setOracion(getOracion());
        dm.setOficio(oficio);
        dm.setLaudes(laudes);
        dm.setMisaLecturas(getMisaLecturas());
        return dm;
    }

}
