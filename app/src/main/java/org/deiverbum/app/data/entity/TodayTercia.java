package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaBreve;
import org.deiverbum.app.model.Himno;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.Intermedia;
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
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID" //liturgiaId
    )
    public SaintEntity santo;


    @Relation(
            entity = LHHymnJoinEntity.class,
            parentColumn = "tHymnFK",
            entityColumn = "groupID"
    )
    public LHHymnWithAll himno;

    @Relation(
            entity = LHReadingShortJoinEntity.class,
            parentColumn = "tBiblicalFK",
            entityColumn = "groupID"
    )
    public LHReadingShortAll biblica;

    @Relation(
            entity = LHPsalmodyJoinEntity.class,
            parentColumn = "tPsalmodyFK",
            entityColumn = "groupID"
    )
    public LHPsalmody salmodia;

    @Relation(
            entity = PsalmodyEntity.class,
            parentColumn = "tPsalmodyFK",
            entityColumn = "groupFK"
    )
    public List<PsalmodyWithPsalms> salmos;

    @Relation(
            entity = LHPrayerEntity.class,
            parentColumn = "tPrayerFK",
            entityColumn = "groupID"
    )
    public LHPrayerAll lhPrayerAll;



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


    public Hoy getToday(){
        Hoy dm = new Hoy();
        dm.setFeria(feria.getDomainModel());
        dm.setFecha(String.valueOf(today.getHoy()));
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        //dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

    public Intermedia getDomainModel(){
        Intermedia dm=new Intermedia();
        dm.setHoy(getToday());
        dm.setHourId(3);
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
        return lhPrayerAll.getDomainModel();
    }


}
