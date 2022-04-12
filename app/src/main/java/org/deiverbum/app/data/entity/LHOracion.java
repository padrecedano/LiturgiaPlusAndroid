package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHOracion {
    @Embedded
    public LHOracionEntity lhOracionEntity;

    @Relation(
            parentColumn = "oracionFK",
            entityColumn = "oracionId",
            entity = OracionEntity.class
    )
    public OracionEntity oracionEntity;

}
