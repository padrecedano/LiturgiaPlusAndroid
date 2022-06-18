package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class OficioVersoAll {
    @Embedded
    public LHOficioVersoJoinEntity theJoin;
    @Relation(
            parentColumn = "versoFK",
            entityColumn = "versoId",
            entity = LHOficioVersoEntity.class
    )
    public LHOficioVersoEntity theEntity;

    public String getDomainModel(){
        return theEntity.getResponsorio();
    }

}
