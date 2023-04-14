package org.deiverbum.app.data.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.data.entity.HomilyEntity;
import org.deiverbum.app.data.entity.LHOfficePatristicEntity;
import org.deiverbum.app.data.entity.LHResponsoryEntity;
import org.deiverbum.app.model.LHOfficePatristic;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
public class LHOfficePatristicWithAll {
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
            entity = LHResponsoryEntity.class
    )
    public LHResponsoryEntity lhResponsorio;


    public LHOfficePatristic getDomainModel(Integer timeId) {
        LHOfficePatristic theModel = homiliaAll.getPatristicaDomainModel();
        theModel.setTheme(lhPatristica.tema);
        theModel.setResponsorioLargo(lhResponsorio.getDomainModel(timeId));
        return theModel;
    }
}
