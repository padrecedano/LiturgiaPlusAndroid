package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.SaintEntity;
import org.deiverbum.app.data.entity.SaintLifeEntity;
import org.deiverbum.app.model.SaintLife;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class TodaySanto {

    @Embedded
    public SaintEntity saint;

    @Relation(
            entity = SaintLifeEntity.class,
            parentColumn = "saintID",
            entityColumn = "saintFK"
    )
    public SaintLife saintLife;

    public SaintLife getDomainModel(){
        SaintLife dm= new SaintLife();
        dm.setLongLife(saintLife.getLongLife());
        dm.setMartyrology(saintLife.getMartyrology());
        dm.setTheSource(saintLife.getTheSource());
        dm.setDia(String.valueOf(saint.getTheDay()));
        dm.setMes(String.valueOf(saint.getTheMonth()));
        dm.setName(saint.getTheName());
        return dm;
    }
}
