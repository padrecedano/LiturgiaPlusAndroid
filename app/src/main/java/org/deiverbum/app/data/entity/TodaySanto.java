package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.SaintLife;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class TodaySanto {

    @Embedded
    public TodayEntity today;

    @Relation(
            entity = SaintEntity.class,
            parentColumn = "saintFK",
            entityColumn = "saintID" //liturgiaId
    )
    public SaintLifeWithAll santo;

    public SaintLife getDomainModel(){
        return santo.saintLife != null ? santo.getDomainModel() : null;
    }
}
