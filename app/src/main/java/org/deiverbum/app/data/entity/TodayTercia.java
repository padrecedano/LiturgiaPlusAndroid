package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.model.Oracion;
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
            entity = LHHimnoJoinEntity.class,
            parentColumn = "tHimnoFK",
            entityColumn = "grupoId"
    )
    public HimnoWithAll himno;

    @Relation(
            entity = LHBiblicaBreveJoinEntity.class,
            parentColumn = "tBiblicaFK",
            entityColumn = "grupoId"
    )
    public BiblicaBreveAll biblica;

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
            parentColumn = "tOracionFK",
            entityColumn = "grupoId"
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
        return himno.getDomainModel();
    }



    public BiblicaBreve getBiblica(){
        return  biblica.getDomainModel(today.getTiempoId());
    }



    public Santo getSanto(){
        return  santo.getDomainModel(false);
    }


    public Salmodia getSalmodia() {

        return salmodia.getDomainModel();
    }

    public Oracion getOracion() {
        return lhOracion.getDomainModel();
    }

    public Intermedia getDomainModel(){
        Intermedia dm=new Intermedia();
        dm.setMetaLiturgia(getMetaLiturgia());
        //dm.setSanto(santo.getDomainModelLH());
        dm.setHimno(getHimno());

        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setOracion(getOracion());
        return dm;
    }
}
