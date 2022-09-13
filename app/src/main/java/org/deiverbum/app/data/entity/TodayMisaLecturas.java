package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Hoy;
import org.deiverbum.app.model.MisaLecturas;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodayMisaLecturas {

    @Embedded
    public Today today;

    @Relation(
            entity = LiturgyEntity.class,
            parentColumn = "massReadingFK",
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
/*
    @Relation(
            entity = MisaLecturaEntity.class,
            parentColumn = "mLecturasFK",
            entityColumn = "liturgiaFK"
    )
    public List<MisaWithLecturas> lecturas;
*/
    @Relation(
            entity = LiturgyGroupEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "groupID"
    )
    public MisaWithLecturasBis lecturass;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK")
    public List<MassReadingWithAll> lecturax;

    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK")
    public List<MassReadingWithAll> lecturay;


    @Relation(
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK")
    public MassReadingWithAll lecturayy;

    public Hoy getToday(){
        Hoy dm = new Hoy();
        //dm.setFeria(feria.getDomainModel());
        dm.setFeria(feria.getDomainModel());

        dm.setFecha(String.valueOf(today.getHoy()));
        dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(true);
        dm.setMLecturasFK(today.mLecturasFK);
        dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }

//TODO Ordenar las lecturas con comparator (cf. Salmodia)
    public MisaLecturas getDomainModel(){
        MisaLecturas dm=new MisaLecturas();
        dm.setHoy(getToday());
        List<BiblicaMisa> listModel = new ArrayList<>();
        for (MassReadingWithAll item : lecturay) {
                listModel.add(item.getDomainModel());
        }

        for (MassReadingWithAll item : lecturax) {
            //listModel.add(item.getDomainModel());
        }

        dm.setLecturas(listModel);
        dm.sort();
        return dm;
    }



}
