package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Santo;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayTercia {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "santoFK",
            entityColumn = "santoId" //liturgiaId
    )
    public SantoEntity santo;


    @Relation(
            entity = HimnoEntity.class,
            parentColumn = "tHimnoFK",
            entityColumn = "himnoId"
    )
    public HimnoEntity himno;

    @Relation(
            entity = LHBiblicaBreveEntity.class,
            parentColumn = "tBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaBreveWithResponsorio biblica;

    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "tSalmodiaFK",
            entityColumn = "grupoId"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "tSalmodiaFK",
            entityColumn = "grupoFK"
    )
    public List<SalmodiaWithSalmos> salmos;


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


    public MetaLiturgia getMetaLiturgia(){
        MetaLiturgia theModel = new MetaLiturgia();
        theModel.setLiturgiaFeria(feria.getDomainModel());
        if(today.getPrevioId()!=null){
            //theModel.setLiturgiaPrevio(previo.getDomainModel());
        }
        theModel.setFecha(String.valueOf(today.getHoy()));
        theModel.setColor(feria.getColorFK());
        theModel.setIdHour(3);
        theModel.setCalendarTime(feria.colorFK);
        theModel.setHasSaint(true);
        //theModel.setIdBreviario(feria.colorFK);
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


    public BiblicaBreve getBiblica(){
        return  biblica.getDomainModelBreve(today.getTiempoId());
    }


    public Santo getSanto(){
        return  santo.getDomainModel(false);
    }


    public Salmodia getSalmodia() {
        return salmodia.getDomainModel();
    }
}