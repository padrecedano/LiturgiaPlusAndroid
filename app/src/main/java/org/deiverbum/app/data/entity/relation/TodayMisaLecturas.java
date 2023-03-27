package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.LiturgyEntity;
import org.deiverbum.app.data.entity.MassReadingEntity;
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
            entity = MassReadingEntity.class,
            parentColumn = "massReadingFK",
            entityColumn = "liturgyFK")
    public List<MassReadingWithAll> lecturas;

    public Today getToday(){
        Today dm = new Today();
        dm.liturgyDay=feria.getDomainModel();
        dm.liturgyPrevious=today.previoId>1?previo.getDomainModel():null;
        dm.setTodayDate(today.getHoy());
        dm.setHasSaint(today.hasSaint);
        dm.setMLecturasFK(today.mLecturasFK);
        return dm;
    }

    public MassReadingList getDomainModel(){
        MassReadingList dm=new MassReadingList();
        dm.setToday(getToday());
        List<MassReading> listModel = new ArrayList<>();
        for (MassReadingWithAll item : lecturas) {
                listModel.add(item.getDomainModel());
        }
        dm.setLecturas(listModel);
        dm.sort();
        return dm;
    }

}
