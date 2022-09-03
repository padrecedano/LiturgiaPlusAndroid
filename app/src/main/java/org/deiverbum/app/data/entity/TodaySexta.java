package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Intermedia;
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
public class TodaySexta {

    @Embedded
    public Today today;

    @Relation(
            entity = SantoEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID"
    )
    public SantoEntity santo;


    @Relation(
            entity = LHHimnoJoinEntity.class,
            parentColumn = "sHymnFK",
            entityColumn = "groupID"
    )
    public HimnoWithAll himno;

    @Relation(
            entity = LHBiblicaBreveJoinEntity.class,
            parentColumn = "sBiblicalFK",
            entityColumn = "groupID"
    )
    public BiblicaBreveAll biblica;

    @Relation(
            entity = LHSalmodiaJoinEntity.class,
            parentColumn = "sPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHSalmodia salmodia;

    @Relation(
            entity = SalmodiaEntity.class,
            parentColumn = "sPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<SalmodiaWithSalmos> salmos;

    @Relation(
            entity = LHOracionEntity.class,
            parentColumn = "sPrayerFK",
            entityColumn = "groupID"
    )
    public LHOracion lhOracion;

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

    public Hoy getToday(){
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        //theModel.setColor(feria.getColorFK());
        dm.setIdHour(4);
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        //theModel.setIdBreviario(feria.colorFK);
        //theModel.setIdDia(feria.colorFK);
        //dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());//.setIdPrevio(1);
        //theModel.setIdSemana(1);
        //theModel.setIdTiempo(9);
        //theModel.setIdTiempoPrevio(1);
        dm.setTitulo(feria.getDomainModel().getNombre());
        //theModel.setLiturgiaFeria(feria.getDomainModel());
        return dm;
    }
    public Intermedia getDomainModel(){
        Intermedia dm=new Intermedia();
        dm.setHoy(getToday());
        dm.setTiempoFK(feria.getDomainModel().getTiempoFK());
        dm.setHourId(4);
        dm.setHimno(getHimno());
        dm.setSalmodia(getSalmodia());
        dm.setLecturaBreve(getBiblica());
        dm.setOracion(getOracion());
        return dm;
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


}
