package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.LiturgyGroupEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
import org.deiverbum.app.data.entity.MassReadingWithAll;
import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.TodayEntity;
import org.deiverbum.app.model.MassReading;
import org.deiverbum.app.model.MassReadingList;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodayMisaLecturas {

    @Embedded
    public TodayEntity today;

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

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;

        dm.setTodayDate(today.getHoy());
        //dm.setCalendarTime(today.tiempoId);
        dm.setHasSaint(today.hasSaint);
        dm.setMLecturasFK(today.mLecturasFK);
        //dm.setPrevio(previo.getDomainModel());
        //dm.setTitulo(feria.getDomainModel().getNombre());
        return dm;
    }


//TODO Ordenar las lecturas con comparator (cf. LHPsalmody)
    public MassReadingList getDomainModel(){
        MassReadingList dm=new MassReadingList();
        dm.setToday(getToday());
        List<MassReading> listModel = new ArrayList<>();
        for (MassReadingWithAll item : lecturay) {
                listModel.add(item.getDomainModel());
        }

        //listModel.add(item.getDomainModel());

        dm.setLecturas(listModel);
        dm.sort();
        return dm;
    }



}
