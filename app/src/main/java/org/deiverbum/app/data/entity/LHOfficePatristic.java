package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHOfficePatristic {
    @Embedded
    public LHOfficePatristicEntity lhPatristica;

    @Relation(
            parentColumn = "homilyFK",
            entityColumn = "homilyID",
            entity = HomilyEntity.class
    )
    public HomilyAll homiliaAll;

    @Relation(
            parentColumn = "responsoryFK",
            entityColumn = "responsoryID",
            entity = LHResponsorioLargoEntity.class
    )
    public LHResponsorioLargoEntity lhResponsorio;


    public org.deiverbum.app.model.LHOfficePatristic getDomainModel(Integer timeId) {
        org.deiverbum.app.model.LHOfficePatristic theModel= homiliaAll.getPatristicaDomainModel();
        theModel.setTema(lhPatristica.tema);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(timeId));
        return theModel;
    }
}
